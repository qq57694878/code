package com.youi.base;

import com.youi.core.spring.ApplicationContextHelper;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;

import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import static junit.framework.TestCase.assertNotNull;

/**
 * Created by jinliang on 2016/7/13.
 */
public class MainSpringTestDemo {
    private static Logger log = LoggerFactory
            .getLogger(MainSpringTestDemo.class);

    public static void init(){
        ApplicationContext ctx = new FileSystemXmlApplicationContext( "classpath*:spring/ctx*.xml");
        ApplicationContextHelper   helper = (ApplicationContextHelper) ctx.getBean("applicationContextHelper");
        assertNotNull(helper);
    }
   public static void main(String args[]){

       ApplicationContext ctx = new FileSystemXmlApplicationContext( "classpath*:spring/ctx*.xml");
       ApplicationContextHelper   helper = (ApplicationContextHelper) ctx.getBean("applicationContextHelper");
       assertNotNull(helper);

       RepositoryService repositoryService=(RepositoryService)ctx.getBean("repositoryService");
       String deployId=repositoryService.createDeployment().addClasspathResource("activiti/hello.bpmn20.xml").deploy().getId();
       log.debug("============>> "+deployId);

       Map<String, Object> variables = new HashMap<String, Object>();
       variables.put("employeeName", "Kermit");
       variables.put("numberOfDays", new Integer(4));
       variables.put("startDate", new Date());
       variables.put("vacationMotivation", "I'm really tired!");
       RuntimeService runtimeService = (RuntimeService)ctx.getBean("runtimeService");
       ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("hello", variables);

// Verify that we started a new process instance
       log.info("Number of process instances: " + runtimeService.createProcessInstanceQuery().count());

       TaskService taskService = (TaskService)ctx.getBean("taskService");
       List<Task> tasks = taskService.createTaskQuery().taskCandidateUser("wkq").list();
       for (Task task : tasks) {
           taskService.claim(task.getId(),"eric");

           Map<String, Object> taskVariables = new HashMap<String, Object>();
           taskVariables.put("vacationApproved", "false");
           taskVariables.put("managerMotivation", "We have a tight deadline!");
           taskService.complete(task.getId(), taskVariables);
           log.info("Task available: " + task.getName());
       }
       List<Task> tasks1 = taskService.createTaskQuery().taskCandidateUser("wkq").list();
       for (Task task : tasks1) {
           taskService.claim(task.getId(),"eric");

           Map<String, Object> taskVariables = new HashMap<String, Object>();
           //taskVariables.put("vacationApproved", "false");
           //taskVariables.put("managerMotivation", "We have a tight deadline!");
           taskService.complete(task.getId(), taskVariables);
           log.info("Task available: " + task.getName());
       }


   }
}

