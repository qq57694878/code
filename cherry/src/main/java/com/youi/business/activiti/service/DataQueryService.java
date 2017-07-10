package com.youi.business.activiti.service;

import com.youi.business.activiti.util.TaskUtil;
import com.youi.business.common.Constants;
import com.youi.business.common.dao.SwDataQueryDao;
import com.youi.business.common.dao.SysActLogDao;
import com.youi.business.common.entity.SW_DATA_QUERY;
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
@Service("DataQueryService")
public class DataQueryService implements UserTaskService {
    private static Logger logger = LoggerFactory
            .getLogger(DataQueryService.class);
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
    private SwDataQueryDao dataQueryDao;
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
            SW_DATA_QUERY data_query = dataQueryDao.get(Long.parseLong(bussinessid));
            if (data_query != null) {
                result.put("title", data_query.getTitle());
                result.put("stime", data_query.getStime());
                result.put("etime", data_query.getEtime());
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
            SW_DATA_QUERY data_query = dataQueryDao.get(Long.parseLong(bussinessid));
            if (data_query != null) {
                result.put("title", data_query.getTitle());
            }

        } else if ("todo_detail".equals(bussiness_type)) {

            SW_DATA_QUERY data_query = dataQueryDao.get(Long.parseLong(bussinessid));
            if (data_query != null) {
                result.put("title", data_query.getTitle());
                result.put("etime", data_query.getEtime());
                result.put("description", data_query.getDescription());
                result.put("stime", data_query.getStime());
                Long business_app = data_query.getBusiness_app();
                result.put("business_app", business_app);
                String business_name = CodeTableUtil.code2mean("BUSINESSAPP",String.valueOf(business_app));
                result.put("business_name", business_name);
                result.put("id", data_query.getId());
                //附件
                String yw_type = task_type;
                String yw_id = String.valueOf(data_query.getId());
                List<StoreDTO> listStore = storeConnector.getStore(yw_type, yw_id);
                List<Map<String, Object>> attachments = new ArrayList<Map<String, Object>>();
                if (listStore != null) {
                    for (StoreDTO storeDTO : listStore) {
                        Map<String, Object> attachment = new HashMap<String, Object>();
                        attachment.put("file_name", storeDTO.getShow_name());
                        attachment.put("file_type", storeDTO.getFile_type());
                        attachment.put("url", storeDTO.getUrl());
                        attachment.put("file_id", storeDTO.getId());
                        attachments.add(attachment);
                    }
                }
                result.put("attachments", attachments);
                //cocompany
                Map<String, Object> cocompany = new HashMap<String, Object>();
                String company_name="";
                String leader_name="";
                if(data_query.getCompany_id()!=null){
                     company_name=  CodeTableUtil.code2mean("SYSORG",data_query.getCompany_id());
                     leader_name=  CodeTableUtil.code2mean("SYSUSER",data_query.getLeader_id());
                }
                cocompany.put("company_id", StringUtils.null2String(String.valueOf(data_query.getCompany_id())));
                cocompany.put("company_name", company_name);
                cocompany.put("leader_id", StringUtils.null2String(String.valueOf(data_query.getLeader_id())));
                cocompany.put("leader_name", leader_name);
                result.put("cocompany", cocompany);
                result.put("actually_stime", data_query.getActually_stime()==null?"":data_query.getActually_stime());
                result.put("actually_etime", data_query.getActually_etime()==null?"":data_query.getActually_etime());
                result.put("actually_sql", data_query.getActually_sql()==null?"":data_query.getActually_sql());
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
            SW_DATA_QUERY obj = null;
            if (!StringUtils.isEmpty(bussinesskey)) {
                obj = dataQueryDao.load(Long.parseLong(bussinesskey));
            } else {
                obj = new SW_DATA_QUERY();
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
            obj.setDescription((String) formData.get("description"));
            obj.setUserid(userid);
            obj.setInstanceid(instance_id);
            if (!StringUtils.isEmpty(bussinesskey)) {
                obj.setId(Long.parseLong(bussinesskey));
                dataQueryDao.update(obj);
            } else {
                obj.setUserid(userid);
                obj.setCdate(new Date());
                SYS_USER currentUser = UserHolder.getThreadLocalUser();
                obj.setC_org_id(currentUser.getOrg_id());
                dataQueryDao.save(obj);
                bussinesskey = String.valueOf(obj.getId());
                runtimeService.updateBusinessKey(instance_id, bussinesskey);
            }
        } else if ("step2".equals(node_id)) {

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
                SW_DATA_QUERY obj = dataQueryDao.load(Long.parseLong(bussinesskey));
                if (formData != null) {
                    Map<String, Object> cocpmpany = (Map<String, Object>) formData.get("cocompany");
                    obj.setCompany_id(Long.parseLong(String.valueOf(cocpmpany.get("company_id"))));
                    obj.setLeader_id(Long.parseLong(String.valueOf(cocpmpany.get("leader_id"))));
                    dataQueryDao.update(obj);
                }
            }
        } else if ("step4".equals(node_id)) {
          /*  boolean step4Approved = true;
            if ("false".equals(formData.get("step4Approved")) || "0".equals(formData.get("step4Approved")) || false == formData.get("step4Approved")) {
                step4Approved = false;
            }
            valables.put("step4Approved", step4Approved);*/

            //更新实际工作
            if (!StringUtils.isEmpty(bussinesskey)) {
                SW_DATA_QUERY obj = dataQueryDao.load(Long.parseLong(bussinesskey));
                if (formData != null) {
                    DateConverter d = new DateConverter();
                    if (formData.get("actually_stime") != null) {
                        obj.setActually_stime(d.convert((String) formData.get("actually_stime")));
                    }
                    if (formData.get("actually_etime") != null) {
                        obj.setActually_etime(d.convert((String) formData.get("actually_etime")));
                    }
                    if (formData.get("actually_sql") != null) {
                        obj.setActually_sql((String) formData.get("actually_sql"));
                    }

                    dataQueryDao.update(obj);
                }
            }
        } else if ("step5".equals(node_id)) {
         /*   boolean step5Approved = true;
            if ("false".equals(formData.get("step5Approved")) || "0".equals(formData.get("step5Approved")) || false == formData.get("step5Approved")) {
                step5Approved = false;
            }
            valables.put("step5Approved", step5Approved);*/
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
        if("step3".equals(node_id)){
            valables.put("step3Approved",true);
        }
        myTaskService.commitProcess(task_id, valables, endActivity.getId());
        return true;
    }

    @Override
    public boolean startTask(Map<String, Object> paramMap, MultiValueMap<String, MultipartFile> multipartFiles) throws Exception {
        SYS_USER currentUser = UserHolder.getThreadLocalUser();
        String userid = (String) paramMap.get("userid");
        String task_type = (String) paramMap.get("task_type");
        //Map<String,String> formData = (Map<String,String>)paramMap.get("form_data");
        //提交需求任务申请
        SW_DATA_QUERY obj = new SW_DATA_QUERY();
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
        obj.setDescription((String) paramMap.get("description"));
        obj.setUserid(userid);
        obj.setCdate(new Date());
        obj.setC_org_id(currentUser.getOrg_id());
        dataQueryDao.save(obj);

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
        dataQueryDao.update(obj);


        return true;
    }

}
