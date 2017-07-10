package com.youi.business.activiti.parser;

import org.activiti.bpmn.model.ExtensionAttribute;

import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.handler.UserTaskParseHandler;
import org.activiti.engine.impl.pvm.process.ActivityImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtensionUserTaskParseHandler extends UserTaskParseHandler {
    public static final String PROPERTY_OPERATIONS_DEFINITION = "operations";
    public static final String EXTENSION_ELEMENT_OPERATIONS = "operations";

    @Override
    protected void executeParse(BpmnParse bpmnParse, UserTask userTask) {
        //调用上层的解析
        super.executeParse(bpmnParse, userTask);

        ActivityImpl activity = bpmnParse.getCurrentScope().findActivity(userTask.getId());
        Map<String, ExtensionOperation> operationMap = parseUserTaskOperations(bpmnParse, userTask);
        //将扩展属性设置给activity
        activity.setProperty(PROPERTY_OPERATIONS_DEFINITION, operationMap);
    }

    public Map<String, ExtensionOperation> parseUserTaskOperations(BpmnParse bpmnParse, UserTask userTask) {
        Map<String, ExtensionOperation> operationMap = new HashMap<String, ExtensionOperation>();
        //获取扩展属性标签元素
        if(userTask.getExtensionElements()!=null&&userTask.getExtensionElements().size()>0){
            List<ExtensionElement> operationParentElements = userTask.getExtensionElements().get(EXTENSION_ELEMENT_OPERATIONS);
            if (operationParentElements != null&&operationParentElements.size()>0) {
                ExtensionElement operationParentElement = operationParentElements.get(0);
                Map<String, List<ExtensionElement>> childElements = operationParentElement.getChildElements();
                if(childElements!=null&&childElements.size()>0){
                    for (String key: childElements.keySet()) {
                        List<ExtensionElement> operationElements =childElements.get(key);
                        ExtensionElement operationElement =  operationElements.get(0);
                        if (operationElement != null) {
                            ExtensionOperation userTaskOperation = new ExtensionOperation(operationElement.getName());
                            if(!operationElement.getAttributes().isEmpty()){
                                Map<String, List<ExtensionAttribute>> attributes = operationElement.getAttributes();
                                for(String attributeKey:attributes.keySet()){
                                    userTaskOperation.addProperty(attributeKey, attributes.get(attributeKey).get(0).getValue());
                                }
                            }
                            operationMap.put(operationElement.getName(), userTaskOperation);
                        }
                    }
                }
            }
        }

        return operationMap;
    }
}