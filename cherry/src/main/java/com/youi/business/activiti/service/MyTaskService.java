package com.youi.business.activiti.service;

import com.youi.business.activiti.parser.ExtensionOperation;
import com.youi.business.activiti.parser.ExtensionUserTaskParseHandler;
import com.youi.business.activiti.util.TaskUtil;
import com.youi.business.sysresource.vo.VBussinessApp;
import com.youi.business.activiti.vo.VTaskDetail;
import com.youi.business.activiti.vo.VTodoTaskInfo;
import com.youi.business.common.Constants;
import com.youi.business.common.dao.SysActLogDao;
import com.youi.business.common.dao.SysUserDao;
import com.youi.business.common.entity.RES_BUSSINESS_APP;
import com.youi.business.common.entity.SYS_ACT_LOG;
import com.youi.business.common.store.StoreConnector;
import com.youi.business.common.store.StoreDTO;
import com.youi.core.codetable.CodeTableUtil;
import com.youi.core.hibernate.HibernatePagingDao;
import com.youi.core.page.Page;
import com.youi.core.spring.ApplicationContextHelper;
import com.youi.core.store.MultipartFileDataSource;
import com.youi.core.util.StringUtils;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskInfo;
import org.activiti.image.ProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

/**
 * Created by jinliang on 2016/8/6.
 */
@Transactional
@Service
public class MyTaskService {
    @Autowired
    private HibernatePagingDao hibernatePagingDao;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private org.activiti.engine.TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private ApplicationContextHelper applicationContextHelper;
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private ProcessEngineConfiguration processEngineConfiguration;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private SysActLogDao sysActLogDao;
    @Autowired
    private StoreConnector storeConnector;


    /**
     * 获得代办任务列表
     *
     * @param userId
     * @return
     */
    public  Page getTodoTaskList(Long userId,Page page) throws Exception{
        int pageno = page.getPageNo();
        int pagesize = page.getPageSize();
        String sql_task ="SELECT v1.TASK_ID,t1.KEY_ TASK_TYPE,T2.BUSINESS_KEY_ BUSINESS_KEY,v1.PROC_INST_ID,V1.CREATE_TIME CTIME from v_ru_tasklist v1,act_re_procdef t1,ACT_RU_EXECUTION T2 " +
                " where  v1.PROC_DEF_ID=t1.ID_ AND v1.EXECUTION_ID=T2.ID_ " +
                "AND (v1.CANDIDATE =? or v1.ASSIGNEE=? )" ;
     /*   String sql_task ="SELECT v1.TASK_ID,t1.KEY_ TASK_TYPE,T2.BUSINESS_KEY_ BUSINESS_KEY,V1.CREATE_TIME CTIME from v_ru_tasklist v1,act_re_procdef t1,ACT_RU_EXECUTION T2 " +
                " where  v1.PROC_DEF_ID=t1.ID_ AND v1.PROC_INST_ID=T2.PROC_INST_ID_ AND T2.T2.BUSINESS_KEY_ IS NOT NULL" +
                "AND (v1.CANDIDATE =? or v1.ASSIGNEE=? )" ;*/
        Page page1 = hibernatePagingDao.pagedSQLQuery(sql_task,pageno,pagesize,new Object[]{String.valueOf(userId),String.valueOf(userId)});
        List<VTodoTaskInfo> results = new ArrayList<VTodoTaskInfo>();
        List<Map<String,Object>> list1 = (List<Map<String,Object>>)page1.getResult();
        if(list1!=null){
            for(Map<String,Object>m:list1){
                VTodoTaskInfo todoTaskInfo = new VTodoTaskInfo();
                String task_id =(String)m.get("task_id");
                String task_type=(String)m.get("task_type");
                todoTaskInfo.setTask_type(task_type);
                todoTaskInfo.setTask_id(task_id);
                String businessKey = (String)m.get("business_key");
                if(StringUtils.isEmpty(businessKey)){
                    String instance_id=(String)m.get("proc_inst_id");
                    ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(instance_id).singleResult();
                    businessKey = processInstance.getBusinessKey();
                }
                if (!StringUtils.isEmpty(businessKey)) {
                 String serviceName = TaskUtil.getMyTaskServiceName(task_type);
                 UserTaskService userTaskService =  (UserTaskService)applicationContextHelper.getBean(serviceName);
                 Map<String,Object> taskInfo = userTaskService.getTaskFormData(task_id,task_type,businessKey,"todo");
                todoTaskInfo.setTask_info(taskInfo);
               }
                results.add(todoTaskInfo);
            }
        }
        page1.setResult(results);
        return page1;
    }


    public Page getHasdoTaskList(Map<String,Object>param)throws Exception {
       String userid = String.valueOf(param.get("userid"));
        int pageno =Integer.parseInt(String.valueOf(param.get("pageno")));
        int pagesize =Integer.parseInt(String.valueOf(param.get("pagesize")));

        String biz_id = String.valueOf(param.get("biz_id"));
        String pram_task_type = String.valueOf(param.get("task_type"));
        String start = String.valueOf(param.get("start"));
        String end = String.valueOf(param.get("end"));
       StringBuilder sql = new StringBuilder();
        List args = new ArrayList();
        sql.append("SELECT T1.ID_ AS TASK_ID,T2.KEY_ AS TASK_TYPE,T3.BUSINESS_KEY_ AS BUSINESS_KEY from ACT_HI_TASKINST T1,act_re_procdef T2,act_hi_procinst T3");
        sql.append("  where  T1.PROC_DEF_ID_=T2.ID_ AND T1.PROC_INST_ID_=T3.ID_ AND T1.ASSIGNEE_=?");
        args.add(String.valueOf(userid));
        if(!StringUtils.isEmpty(pram_task_type)){
            sql.append(" AND  T2.KEY_=?");
            args.add(pram_task_type);
        }
        if(!StringUtils.isEmpty(start)){
            sql.append(" AND T1.START_TIME_>=?");
            args.add(start);
        }
        if(!StringUtils.isEmpty(end)){
            sql.append(" AND DATE_FORMAT(T1.START_TIME_,'%Y-%m-%d')<=?");
            args.add(end);
        }
        if(!StringUtils.isEmpty(biz_id)){
            if("sw_requirement_change".equals(pram_task_type)){
                sql.append(" AND EXISTS(SELECT 1 FROM sw_requirement_change T4 where T4.BUSINESS_APP=? and T4.ID=T3.BUSINESS_KEY_)");
                args.add(biz_id);
            }else if("sw_data_change".equals(pram_task_type)){
                sql.append(" AND EXISTS(SELECT 1 FROM sw_data_change T4 where T4.BUSINESS_APP=? and T4.ID=T3.BUSINESS_KEY_)");
                args.add(biz_id);
            }else if("sw_data_query".equals(pram_task_type)){
                sql.append(" AND EXISTS(SELECT 1 FROM sw_data_query T4 where T4.BUSINESS_APP=? and T4.ID=T3.BUSINESS_KEY_)");
                args.add(biz_id);
            }else if("sw_program_deploy".equals(pram_task_type)){
                sql.append(" AND EXISTS(SELECT 1 FROM sw_program_deploy T4 where T4.BUSINESS_APP=? and T4.ID=T3.BUSINESS_KEY_)");
                args.add(biz_id);
            }
        }

        Page page1 = hibernatePagingDao.pagedSQLQuery(sql.toString(),pageno,pagesize,args.toArray());
        List<VTodoTaskInfo> results = new ArrayList<VTodoTaskInfo>();
        List<Map<String,Object>> list1 = (List<Map<String,Object>>)page1.getResult();
        if(list1!=null){
            for(Map<String,Object>m:list1){
                VTodoTaskInfo todoTaskInfo = new VTodoTaskInfo();
                String task_id =(String)m.get("task_id");
                String task_type=(String)m.get("task_type");
                todoTaskInfo.setTask_type(task_type);
                todoTaskInfo.setTask_id(task_id);
                String businessKey = (String)m.get("business_key");
                if (businessKey != null) {
                    String serviceName = TaskUtil.getMyTaskServiceName(task_type);
                    UserTaskService userTaskService =  (UserTaskService)applicationContextHelper.getBean(serviceName);
                    Map<String,Object> taskInfo = userTaskService.getTaskFormData(task_id,task_type,businessKey,"hasdo");
                    todoTaskInfo.setTask_info(taskInfo);
                }
                results.add(todoTaskInfo);
            }
        }
        page1.setResult(results);
        return page1;
    }

    /**
     * 获得工作流的代办任务详细
     * @param param
     * @return
     * @throws Exception
     */
    public VTaskDetail getActivitiTaskDetail(Map<String,String>param)throws Exception{
        String task_id = param.get("task_id");
        String instance_id = param.get("instance_id");
        String task_type = param.get("task_type");
        String ctx = param.get("ctx");
       // TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(task_id).singleResult();
        HistoricTaskInstance task = historyService.createHistoricTaskInstanceQuery().taskId(task_id).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        // 取得流程实例
        HistoricProcessInstance processInstance =historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        ProcessDefinition processDefinition = getProcessDefinition(processInstance.getProcessDefinitionId());
        VTaskDetail taskDetail = new VTaskDetail();
        // 从acticiti中读取数据
        taskDetail.setTask_id(task.getId());
        taskDetail.setNode_id(task.getTaskDefinitionKey());
        taskDetail.setNode_name(task.getName());
        taskDetail.setInstance_id(task.getProcessInstanceId());
        taskDetail.setTask_type(task_type);
        //从业务表中读取的数据
        //获得业务数据
        String processDefinitionKey = processInstance.getProcessDefinitionKey();
        String taskDefinitionKey = task.getTaskDefinitionKey();
        String serviceName = TaskUtil.getMyTaskServiceName(task_type);
        UserTaskService userTaskService =  (UserTaskService)applicationContextHelper.getBean(serviceName);
        String businessKey = processInstance.getBusinessKey();
        if (businessKey != null) {
            Map<String,Object> formData = userTaskService.getTaskFormData(task.getId(),task_type,businessKey,"todo_detail");
            taskDetail.setForm_data(formData);
            List<SYS_ACT_LOG> logs =  sysActLogDao.find("from SYS_ACT_LOG where task_def_key=? and biz_id=? order by date desc",new Object[]{task_type,businessKey});
            String flag= Constants.TASK_FLAG_NORMAL;
            if(logs!=null&&logs.size()>0){
                SYS_ACT_LOG log =  logs.get(0);
                flag = log.getType();
                //设置审批意见或驳回原因
                formData.put("option",log.getOpinion());
            }
            //设置当前状态
            taskDetail.setFlag(flag);
        }


        //设置操作actions
/*        Map<String,Boolean >actions = new HashMap<String,Boolean>();
        actions.put("submit",true);
        actions.put("return",true);
        actions.put("stop",true);
        taskDetail.setActions(actions);*/
        ActivityImpl curActiviti =  findActivitiImpl(task_id,null);
        Map<String, ExtensionOperation> mapOperations =  (Map<String, ExtensionOperation>)curActiviti.getProperty(ExtensionUserTaskParseHandler.PROPERTY_OPERATIONS_DEFINITION);
        Map<String,Boolean >actions = new HashMap<String,Boolean>();
        if(mapOperations!=null){
            for(String key:mapOperations.keySet()){
                ExtensionOperation operation =  mapOperations.get(key);
                actions.put(key,operation.isEnable());
            }
        }
        taskDetail.setActions(actions);
        //TODO 历史
        Map<String,Object> history = new HashMap<String,Object>();
        history.put("flowchart_url",ctx+"/exclude/flowchart/"+processInstanceId+".do?t="+System.currentTimeMillis());
        //设置历史路径
        List<Map<String,Object>> hispath = new ArrayList<Map<String,Object>>();
    /*    List<HistoricTaskInstance> historyList =  historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).finished().orderByHistoricTaskInstanceEndTime().asc().list();
        if(historyList!=null){
            for(HistoricTaskInstance hi:historyList){
                String userid = hi.getAssignee();
                if(!StringUtils.isEmpty(userid)){
                    SYS_USER user1 = sysUserDao.get(Long.parseLong(userid));
                    Map<String,Object> h = new HashMap<String,Object>();
                    h.put("username",user1.getUsername());
                    h.put("endtime",hi.getEndTime());
                    h.put("do",hi.getName()+";"+StringUtils.null2String(hi.getDescription()));
                    hispath.add(h);
                }
            }
        }*/
        if(businessKey!=null){
            List<SYS_ACT_LOG> his =   sysActLogDao.find("from SYS_ACT_LOG where task_def_key=? and biz_id=? order by date ",new Object[]{task_type,businessKey});
            if(his!=null){
                for(SYS_ACT_LOG hi:his){
                    Map<String,Object> h = new HashMap<String,Object>();
                    h.put("task_id",hi.getTask_id());
                    h.put("node_id",hi.getNode_id());
                    h.put("node_name",hi.getTask_name());
                    h.put("ctime",hi.getDate());
                    h.put("flag",hi.getType());
                    h.put("opinion",hi.getOpinion());
                    h.put("operator",CodeTableUtil.code2mean("SYSUSER", hi.getExecutor()));
                    hispath.add(h);
                }
            }
        }

        history.put("hispath",hispath);
        taskDetail.setHistory(history);

        return taskDetail;
    }

    /**
     * 获得任务详细
     * @param param
     * @return
     */
    public Object getTaskDetail(Map<String,String>param)throws Exception {
        String task_id = param.get("task_id");
        String instance_id = param.get("instance_id");
        String task_type = param.get("task_type");
        Object result=null;
        if("other".equals(task_type)){
            result = new Object();
        }else{
            //默认任务代办详细
            result = getActivitiTaskDetail(param);
        }
        return result;
    }

    /**
     * 提交需求变更
     * @param paramMap
     * @return
     * @throws Exception
     */
    public boolean submitTask(Map<String, Object> paramMap, MultiValueMap<String, MultipartFile> multipartFiles) throws Exception{
        boolean f=true;
        String task_type = (String)paramMap.get("task_type");
        if("other".equals(task_type)){
            f=true;
        }else{
            //默认任务代办详细
            f = submitActivitiTask(paramMap,multipartFiles);
        }
        return f;
    }


    /**
     * 终止流程
     * @param paramMap
     * @return
     * @throws Exception
     */
    public boolean stopTask(Map<String, Object> paramMap, MultiValueMap<String, MultipartFile> multipartFiles) throws Exception{
        /*String task_id = (String)paramMap.get("task_id");
        String instance_id = (String)paramMap.get("instance_id");
        String task_type = (String)paramMap.get("task_type");
        String node_id = (String)paramMap.get("node_id");
        ActivityImpl endActivity = findActivitiImpl(task_id, "end");
        commitProcess(task_id, null, endActivity.getId());
        return true;*/
        boolean f=true;
        String task_type = (String)paramMap.get("task_type");
        if("other".equals(task_type)){
            f=true;
        }else{
            //默认任务代办详细
            f = stopActivitiTask(paramMap, multipartFiles);
        }
        return f;
    }
    private boolean stopActivitiTask(Map<String, Object> paramMap, MultiValueMap<String, MultipartFile> multipartFiles)  throws Exception{
     /*   String task_id = (String) paramMap.get("task_id");
        String instance_id = (String) paramMap.get("instance_id");
        String node_id = (String) paramMap.get("node_id");
        */
        String task_id = (String) paramMap.get("task_id");
        String userid = (String) paramMap.get("userid");
        String task_type = (String) paramMap.get("task_type");
        String serviceName = TaskUtil.getMyTaskServiceName(task_type);
        //先认领任务
        identityService.setAuthenticatedUserId(userid);
        taskService.claim(task_id,userid);
        UserTaskService userTaskService =  (UserTaskService)applicationContextHelper.getBean(serviceName);
        return userTaskService.stopTask(paramMap,multipartFiles);
    }

    /**
     * 驳回流程
     * @param paramMap
     * @return
     */
    public boolean rejectTask(Map<String, Object> paramMap, MultiValueMap<String, MultipartFile> multipartFiles) throws Exception {
        boolean f=true;
        String task_type = (String)paramMap.get("task_type");
        if("other".equals(task_type)){
            f=true;
        }else{
            //默认任务代办详细
            f = rejectActivitiTask(paramMap, multipartFiles);
        }
        return f;
    }

    /**
     * 启动流程
     * @param paramMap
     * @return
     */
    public boolean startTask(Map<String, Object> paramMap, MultiValueMap<String, MultipartFile> multipartFiles) throws Exception {
        boolean f=true;
        String task_type = (String)paramMap.get("task_type");
        if("other".equals(task_type)){
            f=true;
        }else{
            //默认任务代办详细
            f = startActivitiTask(paramMap,multipartFiles);
        }
        return f;
    }


/*
    public Page getBussinessProcessList(Long userid, Page page) throws Exception{
        int pageno = page.getPageNo();
        int pagesize = page.getPageSize();
        List<VBussinessApp> results = new ArrayList<VBussinessApp>();
        ProcessDefinitionQuery query =  repositoryService.createProcessDefinitionQuery().active();
        long totalCount = query.count();
        List<ProcessDefinition> list =   query.listPage(pageno,pagesize);
        if(list!=null){
            for(ProcessDefinition p:list){
                results.add(new VBussinessApp(p.getId(),p.getName(),p.getDescription()));
            }
        }
        page = new Page(results, totalCount);
        page.setPageNo(pageno);
        page.setPageSize(pagesize);
        return page;
    }*/




    private boolean startActivitiTask(Map<String, Object> paramMap,MultiValueMap<String, MultipartFile> multipartFiles)  throws Exception{
        String userid = (String)paramMap.get("userid");
        String task_type = (String)paramMap.get("task_type");
        String serviceName = TaskUtil.getMyTaskServiceName(task_type);
        UserTaskService userTaskService =  (UserTaskService)applicationContextHelper.getBean(serviceName);
        boolean f =  userTaskService.startTask(paramMap,multipartFiles);
        return f;
    }


    private boolean rejectTaskBak(Map<String, Object> paramMap, MultiValueMap<String, MultipartFile> multipartFiles) throws Exception{
        String task_id = (String)paramMap.get("task_id");
        // 取得当前任务
        HistoricTaskInstance currTask = historyService.createHistoricTaskInstanceQuery().taskId(task_id).singleResult();
        // 取得流程实例
        ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(currTask.getProcessInstanceId()).singleResult();
        if (instance == null) {
            return false;
        }
        Map<String, Object>variables = instance.getProcessVariables();
        // 取得流程定义
        ProcessDefinitionEntity definition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                .getDeployedProcessDefinition(currTask.getProcessDefinitionId());
        if (definition == null) {
            return false;
        }
        // 取得上一步活动
        ActivityImpl currActivity = ((ProcessDefinitionImpl) definition) .findActivity(currTask.getTaskDefinitionKey());
        List<PvmTransition> nextTransitionList = currActivity.getIncomingTransitions();
        // 清除当前活动的出口
        List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
        List<PvmTransition> pvmTransitionList = currActivity.getOutgoingTransitions();
        for (PvmTransition pvmTransition : pvmTransitionList) {
            oriPvmTransitionList.add(pvmTransition);
        }
        pvmTransitionList.clear();
        // 建立新出口
        for (PvmTransition nextTransition : nextTransitionList) {
            PvmActivity nextActivity = nextTransition.getSource();
            ActivityImpl nextActivityImpl = ((ProcessDefinitionImpl) definition).findActivity(nextActivity.getId());
            TransitionImpl newTransition = currActivity.createOutgoingTransition();
            newTransition.setDestination(nextActivityImpl);
        }
        // 完成任务
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(instance.getId()).taskDefinitionKey(currTask.getTaskDefinitionKey()).list();
        for (Task task : tasks) {
            taskService.complete(task.getId(), variables);
            historyService.deleteHistoricTaskInstance(task.getId());
        }
        // 恢复方向
        currActivity.getOutgoingTransitions().clear();
        for (PvmTransition pvmTransition : oriPvmTransitionList) {
            pvmTransitionList.add(pvmTransition);
        }
        return true;
    }
    private boolean rejectActivitiTask(Map<String, Object> paramMap, MultiValueMap<String, MultipartFile> multipartFiles)  throws Exception{
        String task_id = (String) paramMap.get("task_id");
        String task_type = (String) paramMap.get("task_type");
        String instance_id = (String) paramMap.get("instance_id");
        String node_id = (String) paramMap.get("node_id");
        String userid = (String) paramMap.get("userid");
        //先认领任务
        identityService.setAuthenticatedUserId(userid);
        taskService.claim(task_id,userid);

        String serviceName = TaskUtil.getMyTaskServiceName(task_type);
        UserTaskService userTaskService =  (UserTaskService)applicationContextHelper.getBean(serviceName);
        return userTaskService.rejectTask(paramMap,multipartFiles);
    }


    private boolean submitActivitiTask(Map<String, Object> paramMap, MultiValueMap<String, MultipartFile> multipartFiles)  throws Exception{
        String task_id = (String)paramMap.get("task_id");
        String userid = (String)paramMap.get("userid");
        String task_type = (String)paramMap.get("task_type");
         /*       String instance_id = (String)paramMap.get("instance_id");
        String instance_type = (String)paramMap.get("instance_type");
        String node_id = (String)paramMap.get("node_id");
        Map<String,Object> formData = (Map<String,Object>)paramMap.get("form_data");*/
        //先认领任务
        identityService.setAuthenticatedUserId(userid);
        taskService.claim(task_id,userid);
       /* TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(task_id).singleResult();
        // 取得流程实例
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(instance_id).singleResult();
        //获得业务数据
        String processDefinitionKey = processInstance.getProcessDefinitionKey();
        String taskDefinitionKey = task.getTaskDefinitionKey();*/
        String serviceName = TaskUtil.getMyTaskServiceName(task_type);
        UserTaskService userTaskService =  (UserTaskService)applicationContextHelper.getBean(serviceName);
        boolean f=  userTaskService.completeTaskFormData(task_id,paramMap,multipartFiles);
        identityService.setAuthenticatedUserId(null);
        return f;
    }


    /**
     * 读取流程图
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public InputStream readDiagram(String processInstanceId) throws Exception{
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        InputStream imageStream = null;
        if(processInstance!=null){
            ProcessDefinition pde = repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId());

            if (pde != null && pde.hasGraphicalNotation()) {
                BpmnModel bpmnModel = repositoryService.getBpmnModel(pde.getId());
                ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
                imageStream = diagramGenerator.generateDiagram(bpmnModel, "png", runtimeService.getActiveActivityIds(processInstance.getId()),
                        Collections.<String>emptyList(), processEngineConfiguration.getActivityFontName(), processEngineConfiguration.getLabelFontName(),
                        processEngineConfiguration.getAnnotationFontName(), processEngineConfiguration.getClassLoader(), 1.0);
            }
        }else{
            HistoricProcessInstance processInstance1 = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            ProcessDefinition pde = repositoryService.getProcessDefinition(processInstance1.getProcessDefinitionId());
            String diagramResourceName = pde.getDiagramResourceName();
             imageStream = repositoryService.getResourceAsStream(pde.getDeploymentId(), diagramResourceName);
        }
        return imageStream;
    }


    public List<StoreDTO> uploadAttachment(Map<String, Object> paramMap, MultiValueMap<String, MultipartFile> multipartFiles) throws Exception{
        List<StoreDTO> result = new ArrayList<StoreDTO>();
        String instance_id = (String)paramMap.get("instance_id");
        String userid = (String)paramMap.get("userid");
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(instance_id).singleResult();
        String bussinesskey = processInstance.getBusinessKey();
        String task_type = processInstance.getProcessDefinitionKey();
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
                    continue;
                }
                StoreDTO storeDTO = storeConnector.saveStore(yw_type, yw_id, new MultipartFileDataSource(multipartFile));
                result.add(storeDTO);
            }
        }
        return result;
    }

    /**
     * 获得文件下载信息
     * @param attachment_id
     * @return
     * @throws Exception
     */
    public StoreDTO getAttachment(Long attachment_id) throws Exception {
        return  storeConnector.getStore(attachment_id);
    }

    public void removeAttachment(Map<String, Object> param)throws Exception {
        storeConnector.removeStore(Long.parseLong(String.valueOf(param.get("file_id"))));
    }







    /**
     * 迭代循环流程树结构，查询当前节点可驳回的任务节点
     *
     * @param taskId
     *            当前任务ID
     * @param currActivity
     *            当前活动节点
     * @param rtnList
     *            存储回退节点集合
     * @param tempList
     *            临时存储节点集合（存储一次迭代过程中的同级userTask节点）
     * @return 回退节点集合
     */
    private List<ActivityImpl> iteratorBackActivity(String taskId,
                                                    ActivityImpl currActivity, List<ActivityImpl> rtnList,
                                                    List<ActivityImpl> tempList) throws Exception {
        // 查询流程定义，生成流程树结构
        ProcessInstance processInstance = findProcessInstanceByTaskId(taskId);

        // 当前节点的流入来源
        List<PvmTransition> incomingTransitions = currActivity
                .getIncomingTransitions();
        // 条件分支节点集合，userTask节点遍历完毕，迭代遍历此集合，查询条件分支对应的userTask节点
        List<ActivityImpl> exclusiveGateways = new ArrayList<ActivityImpl>();
        // 并行节点集合，userTask节点遍历完毕，迭代遍历此集合，查询并行节点对应的userTask节点
        List<ActivityImpl> parallelGateways = new ArrayList<ActivityImpl>();
        // 遍历当前节点所有流入路径
        for (PvmTransition pvmTransition : incomingTransitions) {
            TransitionImpl transitionImpl = (TransitionImpl) pvmTransition;
            ActivityImpl activityImpl = transitionImpl.getSource();
            String type = (String) activityImpl.getProperty("type");
            /**
             * 并行节点配置要求：<br>
             * 必须成对出现，且要求分别配置节点ID为:XXX_start(开始)，XXX_end(结束)
             */
            if ("parallelGateway".equals(type)) {// 并行路线
                String gatewayId = activityImpl.getId();
                String gatewayType = gatewayId.substring(gatewayId
                        .lastIndexOf("_") + 1);
                if ("START".equals(gatewayType.toUpperCase())) {// 并行起点，停止递归
                    return rtnList;
                } else {// 并行终点，临时存储此节点，本次循环结束，迭代集合，查询对应的userTask节点
                    parallelGateways.add(activityImpl);
                }
            } else if ("startEvent".equals(type)) {// 开始节点，停止递归
                return rtnList;
            } else if ("userTask".equals(type)) {// 用户任务
                tempList.add(activityImpl);
            } else if ("exclusiveGateway".equals(type)) {// 分支路线，临时存储此节点，本次循环结束，迭代集合，查询对应的userTask节点
                currActivity = transitionImpl.getSource();
                exclusiveGateways.add(currActivity);
            }
        }

        /**
         * 迭代条件分支集合，查询对应的userTask节点
         */
        for (ActivityImpl activityImpl : exclusiveGateways) {
            iteratorBackActivity(taskId, activityImpl, rtnList, tempList);
        }

        /**
         * 迭代并行集合，查询对应的userTask节点
         */
        for (ActivityImpl activityImpl : parallelGateways) {
            iteratorBackActivity(taskId, activityImpl, rtnList, tempList);
        }

        /**
         * 根据同级userTask集合，过滤最近发生的节点
         */
        currActivity = filterNewestActivity(processInstance, tempList);
        if (currActivity != null) {
            // 查询当前节点的流向是否为并行终点，并获取并行起点ID
            String id = findParallelGatewayId(currActivity);
            if (StringUtils.isEmpty(id)) {// 并行起点ID为空，此节点流向不是并行终点，符合驳回条件，存储此节点
                rtnList.add(currActivity);
            } else {// 根据并行起点ID查询当前节点，然后迭代查询其对应的userTask任务节点
                currActivity = findActivitiImpl(taskId, id);
            }

            // 清空本次迭代临时集合
            tempList.clear();
            // 执行下次迭代
            iteratorBackActivity(taskId, currActivity, rtnList, tempList);
        }
        return rtnList;
    }


    /**
     * 根据当前节点，查询输出流向是否为并行终点，如果为并行终点，则拼装对应的并行起点ID
     *
     * @param activityImpl
     *            当前节点
     * @return
     */
    private String findParallelGatewayId(ActivityImpl activityImpl) {
        List<PvmTransition> incomingTransitions = activityImpl
                .getOutgoingTransitions();
        for (PvmTransition pvmTransition : incomingTransitions) {
            TransitionImpl transitionImpl = (TransitionImpl) pvmTransition;
            activityImpl = transitionImpl.getDestination();
            String type = (String) activityImpl.getProperty("type");
            if ("parallelGateway".equals(type)) {// 并行路线
                String gatewayId = activityImpl.getId();
                String gatewayType = gatewayId.substring(gatewayId
                        .lastIndexOf("_") + 1);
                if ("END".equals(gatewayType.toUpperCase())) {
                    return gatewayId.substring(0, gatewayId.lastIndexOf("_"))
                            + "_start";
                }
            }
        }
        return null;
    }

    /**
     * 根据流入任务集合，查询最近一次的流入任务节点
     *
     * @param processInstance
     *            流程实例
     * @param tempList
     *            流入任务集合
     * @return
     */
    private ActivityImpl filterNewestActivity(ProcessInstance processInstance,
                                              List<ActivityImpl> tempList) {
        while (tempList.size() > 0) {
            ActivityImpl activity_1 = tempList.get(0);
            HistoricActivityInstance activityInstance_1 = findHistoricUserTask(
                    processInstance, activity_1.getId());
            if (activityInstance_1 == null) {
                tempList.remove(activity_1);
                continue;
            }

            if (tempList.size() > 1) {
                ActivityImpl activity_2 = tempList.get(1);
                HistoricActivityInstance activityInstance_2 = findHistoricUserTask(
                        processInstance, activity_2.getId());
                if (activityInstance_2 == null) {
                    tempList.remove(activity_2);
                    continue;
                }

                if (activityInstance_1.getEndTime().before(
                        activityInstance_2.getEndTime())) {
                    tempList.remove(activity_1);
                } else {
                    tempList.remove(activity_2);
                }
            } else {
                break;
            }
        }
        if (tempList.size() > 0) {
            return tempList.get(0);
        }
        return null;
    }






    /**
     * 查询流程定义对象
     * @param processDefinitionId 流程定义ID
     * @return
     */
    private ProcessDefinition getProcessDefinition(String processDefinitionId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        return processDefinition;
    }

    /**
     * @param taskId
     *            当前任务ID
     * @param variables
     *            流程变量
     * @param activityId
     *            流程转向执行任务节点ID<br>
     *            此参数为空，默认为提交操作
     * @throws Exception
     */
    public void commitProcess(String taskId, Map<String, Object> variables,
                               String activityId) throws Exception {
        if (variables == null) {
            variables = new HashMap<String, Object>();
        }
        // 跳转节点为空，默认提交操作
        if (StringUtils.isEmpty(activityId)) {
            taskService.complete(taskId, variables);
        } else {// 流程转向操作
            turnTransition(taskId, activityId, variables);
        }
    }

    /**
     * 清空指定活动节点流向
     *
     * @param activityImpl
     *            活动节点
     * @return 节点流向集合
     */
    public List<PvmTransition> clearTransition(ActivityImpl activityImpl) {
        // 存储当前节点所有流向临时变量
        List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
        // 获取当前节点所有流向，存储到临时变量，然后清空
        List<PvmTransition> pvmTransitionList = activityImpl
                .getOutgoingTransitions();
        for (PvmTransition pvmTransition : pvmTransitionList) {
            oriPvmTransitionList.add(pvmTransition);
        }
        pvmTransitionList.clear();

        return oriPvmTransitionList;
    }

    /**
     * 还原指定活动节点流向
     *
     * @param activityImpl
     *            活动节点
     * @param oriPvmTransitionList
     *            原有节点流向集合
     */
    public void restoreTransition(ActivityImpl activityImpl,
                                   List<PvmTransition> oriPvmTransitionList) {
        // 清空现有流向
        List<PvmTransition> pvmTransitionList = activityImpl
                .getOutgoingTransitions();
        pvmTransitionList.clear();
        // 还原以前流向
        for (PvmTransition pvmTransition : oriPvmTransitionList) {
            pvmTransitionList.add(pvmTransition);
        }
    }

    /**
     * 流程转向操作
     *
     * @param taskId
     *            当前任务ID
     * @param activityId
     *            目标节点任务ID
     * @param variables
     *            流程变量
     * @throws Exception
     */
    public void turnTransition(String taskId, String activityId,
                                Map<String, Object> variables) throws Exception {
        // 当前节点
        ActivityImpl currActivity = findActivitiImpl(taskId, null);
        // 清空当前流向
        List<PvmTransition> oriPvmTransitionList = clearTransition(currActivity);

        // 创建新流向
        TransitionImpl newTransition = currActivity.createOutgoingTransition();
        // 目标节点
        ActivityImpl pointActivity = findActivitiImpl(taskId, activityId);
        // 设置新流向的目标节点
        newTransition.setDestination(pointActivity);

        // 执行转向任务
        taskService.complete(taskId, variables);
        // 删除目标节点新流入
        pointActivity.getIncomingTransitions().remove(newTransition);

        // 还原以前流向
        restoreTransition(currActivity, oriPvmTransitionList);
    }


    /**
     * 反向排序list集合，便于驳回节点按顺序显示
     *
     * @param list
     * @return
     */
    private List<ActivityImpl> reverList(List<ActivityImpl> list) {
        List<ActivityImpl> rtnList = new ArrayList<ActivityImpl>();
        // 由于迭代出现重复数据，排除重复
        for (int i = list.size(); i > 0; i--) {
            if (!rtnList.contains(list.get(i - 1)))
                rtnList.add(list.get(i - 1));
        }
        return rtnList;
    }



    /**
     * 查询指定任务节点的最新记录
     *
     * @param processInstance
     *            流程实例
     * @param activityId
     * @return
     */
    public HistoricActivityInstance findHistoricUserTask(
            ProcessInstance processInstance, String activityId) {
        HistoricActivityInstance rtnVal = null;
        // 查询当前流程实例审批结束的历史节点
        List<HistoricActivityInstance> historicActivityInstances = historyService
                .createHistoricActivityInstanceQuery().activityType("userTask")
                .processInstanceId(processInstance.getId()).activityId(
                        activityId).finished()
                .orderByHistoricActivityInstanceEndTime().desc().list();
        if (historicActivityInstances.size() > 0) {
            rtnVal = historicActivityInstances.get(0);
        }

        return rtnVal;
    }



    /**
     * 根据任务ID获得任务实例
     *
     * @param taskId
     *            任务ID
     * @return
     * @throws Exception
     */
    public TaskInfo findTaskById(String taskId) throws Exception {
        TaskInfo task =  taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            task =historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        }
        if (task == null) {
            throw new Exception("任务实例未找到!");
        }
        return task;
    }

    /**
     * 根据流程实例ID和任务key值查询所有同级任务集合
     *
     * @param processInstanceId
     * @param key
     * @return
     */
    public List<Task> findTaskListByKey(String processInstanceId, String key) {
        return taskService.createTaskQuery().processInstanceId(
                processInstanceId).taskDefinitionKey(key).list();
    }

    /**
     * 根据任务ID获取对应的流程实例
     *
     * @param taskId
     *            任务ID
     * @return
     * @throws Exception
     */
    public ProcessInstance findProcessInstanceByTaskId(String taskId)
            throws Exception {
        // 找到流程实例
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(findTaskById(taskId).getProcessInstanceId()).singleResult();
        if (processInstance == null) {
            throw new Exception("流程实例未找到!");
        }
        return processInstance;
    }

    /**
     * 根据任务ID获取流程定义
     *
     * @param taskId
     *            任务ID
     * @return
     * @throws Exception
     */
    public ProcessDefinitionEntity findProcessDefinitionEntityByTaskId(
            String taskId) throws Exception {
        // 取得流程定义
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                .getDeployedProcessDefinition(findTaskById(taskId)
                        .getProcessDefinitionId());
        if (processDefinition == null) {
            throw new Exception("流程定义未找到!");
        }

        return processDefinition;
    }

    /**
     * 根据任务ID和节点ID获取活动节点 <br>
     *
     * @param taskId
     *            任务ID
     * @param activityId
     *            活动节点ID <br>
     *            如果为null或""，则默认查询当前活动节点 <br>
     *            如果为"end"，则查询结束节点 <br>
     *
     * @return
     * @throws Exception
     */
    public ActivityImpl findActivitiImpl(String taskId, String activityId)
            throws Exception {
        // 取得流程定义
        ProcessDefinitionEntity processDefinition = findProcessDefinitionEntityByTaskId(taskId);

        // 获取当前活动节点ID
        if (StringUtils.isEmpty(activityId)) {
            activityId = findTaskById(taskId).getTaskDefinitionKey();
        }

        // 根据流程定义，获取该流程实例的结束节点
        if (activityId.toUpperCase().equals("END")) {
            for (ActivityImpl activityImpl : processDefinition.getActivities()) {
                List<PvmTransition> pvmTransitionList = activityImpl
                        .getOutgoingTransitions();
                if (pvmTransitionList.isEmpty()) {
                    return activityImpl;
                }
            }
        }

        // 根据节点ID，获取对应的活动节点
        ActivityImpl activityImpl = ((ProcessDefinitionImpl) processDefinition)
                .findActivity(activityId);

        return activityImpl;
    }



}
