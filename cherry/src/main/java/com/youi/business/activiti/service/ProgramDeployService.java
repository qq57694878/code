package com.youi.business.activiti.service;

import com.youi.business.activiti.util.TaskUtil;
import com.youi.business.common.Constants;
import com.youi.business.common.dao.ResVendorDao;
import com.youi.business.common.dao.SwProgramDeployDao;
import com.youi.business.common.dao.SwRequirementChangeDao;
import com.youi.business.common.dao.SysActLogDao;
import com.youi.business.common.entity.SW_PROGRAM_DEPLOY;
import com.youi.business.common.entity.SYS_ACT_LOG;
import com.youi.business.common.entity.SYS_USER;
import com.youi.business.common.holder.UserHolder;
import com.youi.business.common.store.StoreConnector;
import com.youi.business.common.store.StoreDTO;
import com.youi.business.common.util.DevelopKit;
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
@Service("ProgramDeployService")
public class ProgramDeployService implements UserTaskService {
    private static Logger logger = LoggerFactory
            .getLogger(ProgramDeployService.class);
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
    private SwProgramDeployDao programDeployDao;
    @Autowired
    private BeanMapper beanMapper;
    @Autowired
    private StoreConnector storeConnector;
    @Autowired
    private MyTaskService myTaskService;
    @Autowired
    private SysActLogDao sysActLogDao;
    @Autowired
    private SwRequirementChangeDao swRequirementChangeDao;

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
            SW_PROGRAM_DEPLOY program_deploy = programDeployDao.get(Long.parseLong(bussinessid));
            if (program_deploy != null) {
                result.put("title", program_deploy.getTitle());
                result.put("stime", program_deploy.getStime());
                result.put("etime", program_deploy.getEtime());
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
            SW_PROGRAM_DEPLOY program_deploy = programDeployDao.get(Long.parseLong(bussinessid));
            if (program_deploy != null) {
                result.put("title", program_deploy.getTitle());
            }

        } else if ("todo_detail".equals(bussiness_type)) {

            SW_PROGRAM_DEPLOY program_deploy = programDeployDao.get(Long.parseLong(bussinessid));
            if (program_deploy != null) {
                result.put("title", program_deploy.getTitle());
                result.put("etime", program_deploy.getEtime());
                result.put("description", program_deploy.getDescription());
                result.put("stime", program_deploy.getStime());
                Long business_app = program_deploy.getBusiness_app();
                result.put("business_app", business_app);
                String business_name = CodeTableUtil.code2mean("BUSINESSAPP",String.valueOf(business_app));
                result.put("business_name", business_name);
                result.put("id", program_deploy.getId());
                //附件
                String yw_type = task_type;
                String yw_id = String.valueOf(program_deploy.getId());
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
                String company_name ="";
                if(program_deploy.getCompany_id()!=null){
                    company_name =  CodeTableUtil.code2mean("SYSORG",program_deploy.getCompany_id());
                }
                result.put("company_id", StringUtils.null2String(String.valueOf(program_deploy.getCompany_id())));
                result.put("company_name", company_name);
                Long req_change_id = program_deploy.getReq_change_id();
                String req_change_title = "";
                if(req_change_id!=null){
                    req_change_title = swRequirementChangeDao.get(req_change_id).getTitle();
                }
                result.put("req_change_id", program_deploy.getReq_change_id()==null?"":program_deploy.getReq_change_id());
                result.put("req_change_title", req_change_title);
                result.put("description", program_deploy.getDescription()==null?"":program_deploy.getDescription());
                result.put("plan_deploy_time", program_deploy.getPlan_deploy_time()==null?"":program_deploy.getPlan_deploy_time());

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
            SW_PROGRAM_DEPLOY obj = null;
            if (!StringUtils.isEmpty(bussinesskey)) {
                obj = programDeployDao.load(Long.parseLong(bussinesskey));
            } else {
                obj = new SW_PROGRAM_DEPLOY();
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
            if(formData.get("req_change_id")!=null){
                obj.setReq_change_id(Long.parseLong(String.valueOf(formData.get("req_change_id"))));
            }
            if (paramMap.get("plan_deploy_time") != null) {
                DateConverter d = new DateConverter();
                obj.setPlan_deploy_time(d.convert((String) formData.get("plan_deploy_time")));
            }

            if (!StringUtils.isEmpty(bussinesskey)) {
                obj.setId(Long.parseLong(bussinesskey));
                programDeployDao.update(obj);
            } else {
                String sqlVendor ="SELECT ID FROM res_vendor T1 WHERE T1.sys_org_info_id = (SELECT ORG_ID FROM SYS_USER T2 WHERE  T2.USER_ID =?)";
                Long company_id = programDeployDao.getJdbcTemplate().queryForObject(sqlVendor,Long.class,new Object[]{Long.parseLong(userid)});
                if(company_id!=null){
                    obj.setCompany_id(company_id);
                }
                obj.setUserid(userid);
                obj.setCdate(new Date());
                SYS_USER currentUser = UserHolder.getThreadLocalUser();
                obj.setC_org_id(currentUser.getOrg_id());
                programDeployDao.save(obj);
                bussinesskey = String.valueOf(obj.getId());
                runtimeService.updateBusinessKey(instance_id, bussinesskey);
            }
        } else if ("step2".equals(node_id)) {

            boolean step2Approved = true;
            if ("false".equals(formData.get("step2Approved")) || "0".equals(formData.get("step2Approved"))) {
                step2Approved = false;
            }
            valables.put("step2Approved", step2Approved);
            //更新外协公司数据
            if (!StringUtils.isEmpty(bussinesskey)) {
                SW_PROGRAM_DEPLOY obj = programDeployDao.load(Long.parseLong(bussinesskey));
                if (formData != null) {
                    List orgIdList =  (List) formData.get("org_id_list");
                    if(orgIdList!=null&&orgIdList.size()>0){
                        obj.setOrg_id_list( DevelopKit.array2String(orgIdList.toArray()));
                    }
                    programDeployDao.update(obj);
                }
            }
        } else if ("step3".equals(node_id)) {
            boolean step3Approved = true;
            if ("false".equals(formData.get("step3Approved")) || "0".equals(formData.get("step3Approved"))) {
                step3Approved = false;
            }
            valables.put("step3Approved", step3Approved);

        } else if ("step4".equals(node_id)) {
          /*  boolean step4Approved = true;
            if ("false".equals(formData.get("step4Approved")) || "0".equals(formData.get("step4Approved")) || false == formData.get("step4Approved")) {
                step4Approved = false;
            }
            valables.put("step4Approved", step4Approved);*/

            //更新实际工作
            if (!StringUtils.isEmpty(bussinesskey)) {
                SW_PROGRAM_DEPLOY obj = programDeployDao.load(Long.parseLong(bussinesskey));
                if (formData != null) {
                    DateConverter d = new DateConverter();
                    if (formData.get("actual_deploy_time") != null) {
                        obj.setActual_deploy_time(d.convert((String) formData.get("actual_deploy_time")));
                    }

                    programDeployDao.update(obj);
                }
            }
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
        SW_PROGRAM_DEPLOY obj = new SW_PROGRAM_DEPLOY();
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
        if (paramMap.get("plan_deploy_time") != null) {
            DateConverter d = new DateConverter();
            obj.setPlan_deploy_time(d.convert((String) paramMap.get("plan_deploy_time")));
        }
        if(paramMap.get("req_change_id")!=null){
            obj.setReq_change_id(Long.parseLong(String.valueOf(paramMap.get("req_change_id"))));
        }

        obj.setUserid(userid);
        obj.setCdate(new Date());
        obj.setC_org_id(currentUser.getOrg_id());
       String sqlVendor ="SELECT id FROM res_vendor T1 WHERE T1.sys_org_info_id = (SELECT ORG_ID FROM SYS_USER T2 WHERE  T2.USER_ID =?)";
        List conmpanyList = programDeployDao.getJdbcTemplate().queryForList(sqlVendor,new Object[]{Long.parseLong(userid)});
        if(conmpanyList!=null&&conmpanyList.size()>0){
         Map<String,Object> m=(Map<String,Object>)conmpanyList.get(0);
            Long company_id = Long.parseLong(String.valueOf(m.get("id")));
             obj.setCompany_id(company_id);
        }

        programDeployDao.save(obj);

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
        programDeployDao.update(obj);


        return true;
    }

}
