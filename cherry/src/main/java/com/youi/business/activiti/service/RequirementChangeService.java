package com.youi.business.activiti.service;

import com.youi.business.activiti.util.TaskUtil;
import com.youi.business.common.Constants;
import com.youi.business.common.dao.SwRequirementChangeDao;
import com.youi.business.common.dao.SysActLogDao;
import com.youi.business.common.entity.SW_REQUIREMENT_CHANGE;
import com.youi.business.common.entity.SYS_ACT_LOG;
import com.youi.business.common.entity.SYS_USER;
import com.youi.business.common.holder.UserHolder;
import com.youi.business.common.store.StoreConnector;
import com.youi.business.common.store.StoreDTO;
import com.youi.core.codetable.CodeTableUtil;
import com.youi.core.mapper.BeanMapper;
import com.youi.core.spring.DateConverter;
import com.youi.core.store.MultipartFileDataSource;
import com.youi.core.util.StringUtils;
import org.activiti.engine.*;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * Created by jinliang on 2016/8/13.
 */
@Transactional
@Service("RequirementChangeService")
public class RequirementChangeService implements UserTaskService {
    private static Logger logger = LoggerFactory
            .getLogger(RequirementChangeService.class);
    @Autowired
    private TaskService taskService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private FormService formService;
    @Autowired
    private SwRequirementChangeDao requirementChangeDao;
    @Autowired
    private BeanMapper beanMapper;
    @Autowired
    private StoreConnector storeConnector;
    @Autowired
    private MyTaskService myTaskService;
    @Autowired
    private SysActLogDao sysActLogDao;



    @Override
    public Map<String, Object> getTaskFormData(String task_id, String task_type, String bussinessid, String bussiness_type) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        if ("todo".equals(bussiness_type)) {
            TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(task_id).singleResult();
            String processInstanceId = task.getProcessInstanceId();
            // 取得流程实例
            // 从acticiti中读取数据
            result.put("instance_id", task.getProcessInstanceId());
            result.put("node_id", task.getTaskDefinitionKey());
            result.put("node_name", task.getName());
            List<SYS_ACT_LOG> logs = sysActLogDao.find("from SYS_ACT_LOG where task_def_key=? and biz_id=? order by date desc", new Object[]{task_type, bussinessid});
            String flag = Constants.TASK_FLAG_NORMAL;
            if (logs != null && logs.size() > 0) {
                SYS_ACT_LOG log = logs.get(0);
                flag = log.getType();
            }
            //设置当前状态
            result.put("flag", flag);
            SW_REQUIREMENT_CHANGE request_change = requirementChangeDao.get(Long.parseLong(bussinessid));
            if (request_change != null) {
                result.put("title", request_change.getTitle());
                result.put("stime", request_change.getStime());
                result.put("etime", request_change.getEtime());
            }
        } else if ("hasdo".equals(bussiness_type)) {
            HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(task_id).singleResult();
            String processInstanceId = historicTaskInstance.getProcessInstanceId();
            // 取得流程实例
            // 从acticiti中读取数据
            result.put("instance_id", historicTaskInstance.getProcessInstanceId());
            result.put("node_id", historicTaskInstance.getTaskDefinitionKey());
            result.put("node_name", historicTaskInstance.getName());
            result.put("stime", historicTaskInstance.getCreateTime());
            result.put("etime", historicTaskInstance.getEndTime());
            List<SYS_ACT_LOG> logs = sysActLogDao.find("from SYS_ACT_LOG where task_def_key=? and biz_id=? order by date desc", new Object[]{task_type, bussinessid});
            String flag = Constants.TASK_FLAG_NORMAL;
            if (logs != null && logs.size() > 0) {
                SYS_ACT_LOG log = logs.get(0);
                flag = log.getType();
            }
            //设置当前状态
            result.put("flag", flag);
            SW_REQUIREMENT_CHANGE request_change = requirementChangeDao.get(Long.parseLong(bussinessid));
            if (request_change != null) {
                result.put("title", request_change.getTitle());
            }

        } else if ("todo_detail".equals(bussiness_type)) {
          /*  TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(task_id).singleResult();
            String processInstanceId = task.getProcessInstanceId();
            // 取得流程实例
            // 从acticiti中读取数据
            result.put("instance_id",task.getProcessInstanceId());
            result.put("node_id",task.getTaskDefinitionKey());
            result.put("node_name",task.getName());
            result.put("stime",task.getCreateTime());
            result.put("flag",Constants.TASK_FLAG_NORMAL);*/
            SW_REQUIREMENT_CHANGE request_change = requirementChangeDao.get(Long.parseLong(bussinessid));
            if (request_change != null) {
                result.put("title", request_change.getTitle());
                result.put("etime", request_change.getEtime());
                result.put("change_type", request_change.getChange_type());
                result.put("description", request_change.getDescription());
                result.put("stime", request_change.getStime());
                Long business_app = request_change.getBusiness_app();
                result.put("business_app", business_app);
                String business_name = CodeTableUtil.code2mean("BUSINESSAPP",String.valueOf(business_app));
                result.put("business_name", business_name);
                result.put("id", request_change.getId());
                //附件
                String yw_type = task_type;
                String yw_id = String.valueOf(request_change.getId());
                List<StoreDTO> listStore = storeConnector.getStore(yw_type, yw_id);
                List<Map<String, Object>> attachments = new ArrayList<Map<String, Object>>();
                if (listStore != null) {
                    for (StoreDTO storeDTO : listStore) {
                        Map<String, Object> attachment = new HashMap<String, Object>();
                        attachment.put("file_name", storeDTO.getShow_name());
                        attachment.put("file_type", storeDTO.getFile_type());
                        attachment.put("url", storeDTO.getUrl());
                        attachment.put("file_id", storeDTO.getId());
                        /*   List<ACTE_NODE_ATTACHMENT>  r1 = acteNodeAttachmentDao.findBy("attachment_id",storeDTO.getId());
                            if(r1!=null&&r1.size()>0){
                                attachment.put("node_id", r1.get(0).getNode_id());
                            }*/
                        attachments.add(attachment);
                    }
                }
                result.put("attachments", attachments);
                //cocompany
                Map<String, Object> cocompany = new HashMap<String, Object>();
                String company_name="";
                String leader_name="";
                if(request_change.getCompany_id()!=null){
                    company_name=  CodeTableUtil.code2mean("SYSORG",request_change.getCompany_id());
                    leader_name=  CodeTableUtil.code2mean("SYSUSER",request_change.getLeader_id());
                }
                cocompany.put("company_id", StringUtils.null2String(String.valueOf(request_change.getCompany_id())));
                cocompany.put("company_name", company_name);
                cocompany.put("leader_id", StringUtils.null2String(String.valueOf(request_change.getLeader_id())));
                cocompany.put("leader_name", leader_name);
                result.put("cocompany", cocompany);
                result.put("actually_stime", request_change.getActually_stime()==null?"":request_change.getActually_stime());
                result.put("actually_etime", request_change.getActually_etime()==null?"":request_change.getActually_etime());
                result.put("actually_work", request_change.getActually_work()==null?"":request_change.getActually_work());

            }
        }
        return result;
    }

    @Override
    public boolean completeTaskFormData(String task_id, Map<String, Object> paramMap, MultiValueMap<String, MultipartFile> multipartFiles) throws Exception {
        String node_id = (String) paramMap.get("node_id");
        String userid = (String) paramMap.get("userid");
        String task_type = (String) paramMap.get("task_type");
        String instance_id = (String) paramMap.get("instance_id");
        Map<String, Object> formData = (Map<String, Object>) paramMap.get("form_data");
        String option = "";
        if (formData != null) {
            option = (String) formData.get("option");
        }
        Map<String, Object> valables = new HashMap<String, Object>();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(instance_id).singleResult();
        String bussinesskey = processInstance.getBusinessKey();
        //文件上传
        String yw_type = task_type;
        String yw_id = bussinesskey;
        if (multipartFiles != null) {
            for (Map.Entry<String, List<MultipartFile>> entry : multipartFiles.entrySet()) {
                List<MultipartFile> value = entry.getValue();
                if ((value == null) || (value.isEmpty())) {
                    continue;
                }
                MultipartFile multipartFile = value.get(0);
                if ((multipartFile.getName() == null)
                        || "".equals(multipartFile.getName().trim())) {
                    continue;
                }
                if (multipartFile.getSize() == 0) {
                    logger.info("ignore empty file");
                    continue;
                }
                StoreDTO storeDTO = storeConnector.saveStore(yw_type, yw_id, new MultipartFileDataSource(multipartFile));
                //保存附件和节点关系表
              /*  ACTE_NODE_ATTACHMENT acte_node_attachment = new ACTE_NODE_ATTACHMENT();
                acte_node_attachment.setBiz_id(Long.parseLong(yw_id));
                acte_node_attachment.setBiz_type(yw_type);
                acte_node_attachment.setAttachment_id(storeDTO.getId());
                acteNodeAttachmentDao.save(acte_node_attachment);*/

            }
        }

        //保存审批意见
        SYS_ACT_LOG act_log = new SYS_ACT_LOG();
        act_log.setBiz_id(bussinesskey);
        act_log.setBiz_table(TaskUtil.getBizTableByTaskType(task_type));
        act_log.setDate(new Date());
        act_log.setExecutor(userid);
        act_log.setInstance_id(instance_id);
        act_log.setTask_def_key(task_type);
        act_log.setTask_id(task_id);
        act_log.setNode_id(node_id);
        act_log.setOpinion(option);
        act_log.setType(Constants.TASK_FLAG_NORMAL);
        TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(task_id).singleResult();
        act_log.setTask_name(task.getName());
        sysActLogDao.save(act_log);

        //提交任务
        if ("step1".equals(node_id)) {
            //提交需求任务申请
            SW_REQUIREMENT_CHANGE obj = null;
            if (!StringUtils.isEmpty(bussinesskey)) {
                obj = requirementChangeDao.load(Long.parseLong(bussinesskey));
            } else {
                obj = new SW_REQUIREMENT_CHANGE();
            }
            if (formData.get("etime") != null) {
                DateConverter d = new DateConverter();
                obj.setEtime(d.convert((String) formData.get("etime")));
            }
            if (paramMap.get("stime") != null) {
                DateConverter d = new DateConverter();
                obj.setStime(d.convert((String) formData.get("stime")));
            }else{
                obj.setStime(new Date());
            }
            obj.setTitle((String) formData.get("title"));
            if (formData.get("business_app") != null) {
                obj.setBusiness_app(Long.parseLong(String.valueOf(formData.get("business_app"))));
            }
            obj.setChange_type(String.valueOf(formData.get("change_type")));
            obj.setDescription((String) formData.get("description"));
            obj.setUserid(userid);
            obj.setInstanceid(instance_id);
            if (!StringUtils.isEmpty(bussinesskey)) {
                obj.setId(Long.parseLong(bussinesskey));
                requirementChangeDao.update(obj);
            } else {
                obj.setUserid(userid);
                obj.setCdate(new Date());
                SYS_USER currentUser = UserHolder.getThreadLocalUser();
                obj.setC_org_id(currentUser.getOrg_id());
                requirementChangeDao.save(obj);
                bussinesskey = String.valueOf(obj.getId());
                runtimeService.updateBusinessKey(instance_id, bussinesskey);
            }
        } else if ("step2".equals(node_id)) {
        /*    ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(instance_id).singleResult();
            String bussinesskey = processInstance.getBusinessKey();
            REQUEST_CHANGE request_change = requestChangeDao.get(Long.parseLong(bussinesskey));*/
           /*
            beanMapper.copy(formData,valables);*/
            boolean step2Approved = true;
            if ("false".equals(formData.get("step2Approved")) || "0".equals(formData.get("step2Approved"))) {
                step2Approved = false;
            }
            valables.put("step2Approved", step2Approved);
        } else if ("step3".equals(node_id)) {
            boolean step3Approved = true;
            if ("false".equals(formData.get("step3Approved")) || "0".equals(formData.get("step3Approved"))) {
                step3Approved = false;
            }
            valables.put("step3Approved", step3Approved);
            //更新外协公司数据
            if (!StringUtils.isEmpty(bussinesskey)) {
                SW_REQUIREMENT_CHANGE obj = requirementChangeDao.load(Long.parseLong(bussinesskey));
                if (formData != null) {
                    Map<String, Object> cocpmpany = (Map<String, Object>) formData.get("cocompany");
                    obj.setCompany_id(Long.parseLong(String.valueOf(cocpmpany.get("company_id"))));
                    obj.setLeader_id(Long.parseLong(String.valueOf(cocpmpany.get("leader_id"))));
                    requirementChangeDao.update(obj);
                }
            }
        } else if ("step4".equals(node_id)) {
            boolean step4Approved = true;
            if ("false".equals(formData.get("step4Approved")) || "0".equals(formData.get("step4Approved"))) {
                step4Approved = false;
            }
            valables.put("step4Approved", step4Approved);

            //更新实际工作
            if (!StringUtils.isEmpty(bussinesskey)) {
                SW_REQUIREMENT_CHANGE obj = requirementChangeDao.load(Long.parseLong(bussinesskey));
                if (formData != null) {
                    DateConverter d = new DateConverter();
                    if (formData.get("actually_stime") != null) {
                        obj.setActually_stime(d.convert((String) formData.get("actually_stime")));
                    }
                    if (formData.get("actually_etime") != null) {
                        obj.setActually_etime(d.convert((String) formData.get("actually_etime")));
                    }
                    if (formData.get("actually_work") != null) {
                        obj.setActually_work((String) formData.get("actually_work"));
                    }
                    requirementChangeDao.update(obj);
                }
            }
        } else if ("step5".equals(node_id)) {
            boolean step5Approved = true;
            if ("false".equals(formData.get("step5Approved")) || "0".equals(formData.get("step5Approved"))) {
                step5Approved = false;
            }
            valables.put("step5Approved", step5Approved);
        } else if ("step6".equals(node_id)) {
            boolean step6Approved = true;
            if ("false".equals(formData.get("step6Approved")) || "0".equals(formData.get("step6Approved"))) {
                step6Approved = false;
            }
            valables.put("step6Approved", step6Approved);
        }
        taskService.complete(task_id, valables);
        return true;
    }

    @Override
    public boolean rejectTask(Map<String, Object> paramMap, MultiValueMap<String, MultipartFile> multipartFiles) throws Exception {
        String task_id = (String) paramMap.get("task_id");
        String task_type = (String) paramMap.get("task_type");
        String instance_id = (String) paramMap.get("instance_id");
        String node_id = (String) paramMap.get("node_id");
        String opinion = (String) paramMap.get("opinion");
        String userid = (String) paramMap.get("userid");
        Map<String, Object> valables = new HashMap<String, Object>();
        String bussinesskey = runtimeService.createProcessInstanceQuery().processInstanceId(instance_id).singleResult().getBusinessKey();
     /*   String yw_type = task_type;
        String yw_id = bussinesskey;
        if (multipartFiles != null&&!StringUtils.isEmpty(yw_id)) {
            for (Map.Entry<String, List<MultipartFile>> entry : multipartFiles.entrySet()) {
                List<MultipartFile> value = entry.getValue();
                if ((value == null) || (value.isEmpty())) {
                    continue;
                }
                MultipartFile multipartFile = value.get(0);
                if ((multipartFile.getName() == null)
                        || "".equals(multipartFile.getName().trim())) {
                    continue;
                }
                if (multipartFile.getSize() == 0) {
                    logger.info("ignore empty file");
                    continue;
                }
                storeConnector.saveStore(yw_type, yw_id, new MultipartFileDataSource(multipartFile));
            }
        }*/

        TaskFormData taskFormData = formService.getTaskFormData(task_id);
        List<FormProperty> formProperties = taskFormData.getFormProperties();
        if (formProperties != null && formProperties.size() > 0) {
            //增加驳回原因
            //保存审批意见
            SYS_ACT_LOG act_log = new SYS_ACT_LOG();
            act_log.setBiz_id(bussinesskey);
            act_log.setBiz_table(TaskUtil.getBizTableByTaskType(task_type));
            act_log.setDate(new Date());
            act_log.setExecutor(userid);
            act_log.setInstance_id(instance_id);
            act_log.setTask_def_key(task_type);
            act_log.setTask_id(task_id);
            act_log.setNode_id(node_id);
            act_log.setOpinion(opinion);
            act_log.setType(Constants.TASK_FLAG_REJECT);
            TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(task_id).singleResult();
            act_log.setTask_name(task.getName());
            sysActLogDao.save(act_log);
            //提交任务
            valables.put(formProperties.get(0).getId(), false);
            taskService.complete(task_id, valables);
            return true;
        }
        return false;
    }

    @Override
    public boolean stopTask(Map<String, Object> paramMap, MultiValueMap<String, MultipartFile> multipartFiles) throws Exception {
        String task_id = (String) paramMap.get("task_id");
        String task_type = (String) paramMap.get("task_type");
        String instance_id = (String) paramMap.get("instance_id");
        String node_id = (String) paramMap.get("node_id");
        String opinion = (String) paramMap.get("opinion");
        Map<String, Object> valables = new HashMap<String, Object>();
        ActivityImpl endActivity = myTaskService.findActivitiImpl(task_id, "end");
        myTaskService.commitProcess(task_id, null, endActivity.getId());
        return true;
    }

    @Override
    public boolean startTask(Map<String, Object> paramMap, MultiValueMap<String, MultipartFile> multipartFiles) throws Exception {
        SYS_USER currentUser = UserHolder.getThreadLocalUser();
        String userid = (String) paramMap.get("userid");
        String task_type = (String) paramMap.get("task_type");
        //Map<String,String> formData = (Map<String,String>)paramMap.get("form_data");
        //提交需求任务申请
        SW_REQUIREMENT_CHANGE obj = new SW_REQUIREMENT_CHANGE();
        if (paramMap.get("etime") != null) {
            DateConverter d = new DateConverter();
            obj.setEtime(d.convert((String) paramMap.get("etime")));
        }
        if (paramMap.get("stime") != null) {
            DateConverter d = new DateConverter();
            obj.setStime(d.convert((String) paramMap.get("stime")));
        }else{
            obj.setStime(new Date());
        }
        obj.setTitle((String) paramMap.get("title"));
        obj.setBusiness_app(Long.parseLong((String) paramMap.get("business_app")));
        obj.setChange_type((String) paramMap.get("change_type"));
        obj.setDescription((String) paramMap.get("description"));
        obj.setUserid(userid);
        obj.setCdate(new Date());
        obj.setC_org_id(currentUser.getOrg_id());
        requirementChangeDao.save(obj);

        //开启流程
        String businessKey = String.valueOf(obj.getId());
        String yw_type = task_type;
        String yw_id = businessKey;
        if (multipartFiles != null) {
            for (Map.Entry<String, List<MultipartFile>> entry : multipartFiles.entrySet()) {
                List<MultipartFile> value = entry.getValue();
                if ((value == null) || (value.isEmpty())) {
                    continue;
                }
                MultipartFile multipartFile = value.get(0);
                if ((multipartFile.getName() == null)
                        || "".equals(multipartFile.getName().trim())) {
                    continue;
                }
                if (multipartFile.getSize() == 0) {
                    logger.info("ignore empty file");
                    continue;
                }
                StoreDTO storeDTO = storeConnector.saveStore(yw_type, yw_id, new MultipartFileDataSource(multipartFile));
                //保存附件和节点关系表
               /* ACTE_NODE_ATTACHMENT acte_node_attachment = new ACTE_NODE_ATTACHMENT();
                acte_node_attachment.setBiz_id(Long.parseLong(yw_id));
                acte_node_attachment.setBiz_type(yw_type);
                acte_node_attachment.setAttachment_id(storeDTO.getId());
                acteNodeAttachmentDao.save(acte_node_attachment);*/
            }
        }
        Map<String, Object> valables = new HashMap<String, Object>();
        try {
            // 先设置登录用户
            identityService.setAuthenticatedUserId(userid);
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(task_type, businessKey, valables);
            obj.setInstanceid(processInstance.getId());


        } finally {
            identityService.setAuthenticatedUserId(null);
        }
        requirementChangeDao.update(obj);


        return true;
    }

   /* public Map<String, Object> getCommitVariables(String taskid, Map<String, Object> formData) throws Exception {
        Map<String,Object> valables = new HashMap<String,Object>();
        return valables;
    }*/

}
