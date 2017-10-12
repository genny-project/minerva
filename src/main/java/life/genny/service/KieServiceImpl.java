package life.genny.service;

import java.util.List;
import java.util.Map;

import org.kie.server.api.exception.KieServicesHttpException;
import org.kie.server.api.model.definition.AssociatedEntitiesDefinition;
import org.kie.server.api.model.definition.ProcessDefinition;
import org.kie.server.api.model.definition.ServiceTasksDefinition;
import org.kie.server.api.model.definition.UserTaskDefinitionList;
import org.kie.server.api.model.instance.NodeInstance;
import org.kie.server.api.model.instance.ProcessInstance;
import org.kie.server.api.model.instance.VariableInstance;
import org.kie.server.api.model.instance.WorkItemInstance;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import life.genny.qwanda.entity.BaseEntity;

public class KieServiceImpl implements KieService{

	@Override
	public void save(Handler<AsyncResult<JsonObject>> result) {
		BaseEntity person = new BaseEntity("PER039", "Adam");
		JsonObject ob = JsonObject.mapFrom(person);
		result.handle(Future.succeededFuture(ob));
		System.out.println("Save Done!");
	}
	
	@Override
	public void findActiveNodeInstances(String containerId, Long processInstanceId, int page, int pageSize, Handler<AsyncResult<JsonArray>> result) {
		// TODO Auto-generated method stub
		List<NodeInstance> nodeList =KieClient.getKieClient().processServicesClient.findActiveNodeInstances(containerId, processInstanceId, page, pageSize);
		JsonArray arr = new JsonArray();
		nodeList.stream().forEach(data -> {
			JsonObject ob = JsonObject.mapFrom(data);
			arr.add(ob);
		});
		result.handle(Future.succeededFuture(arr));
		System.out.println("Done findActiveNodeInstances!");
	}
	
	@Override
	public void findCompletedNodeInstances(String containerId, Long processInstanceId, int page, int pageSize, Handler<AsyncResult<JsonArray>> result) {
		// TODO Auto-generated method stub
		List<NodeInstance> nodeList =KieClient.getKieClient().processServicesClient.findCompletedNodeInstances(containerId, processInstanceId, page, pageSize);
		JsonArray arr = new JsonArray();
		nodeList.stream().forEach(data -> {
			JsonObject ob = JsonObject.mapFrom(data);
			arr.add(ob);
		});
		result.handle(Future.succeededFuture(arr));
		System.out.println("Done findCompletedNodeInstances!");
	}
	
	@Override
	public void findNodeInstances(String containerId, Long processInstanceId, int page, int pageSize, Handler<AsyncResult<JsonArray>> result) {
		// TODO Auto-generated method stub
		List<NodeInstance> nodeList =KieClient.getKieClient().processServicesClient.findNodeInstances(containerId, processInstanceId, page, pageSize);
		JsonArray arr = new JsonArray();
		nodeList.stream().forEach(data -> {
			JsonObject ob = JsonObject.mapFrom(data);
			arr.add(ob);
		});
		result.handle(Future.succeededFuture(arr));
		System.out.println("Done findNodeInstances!");
	}
	
	@Override
	public void findVariableHistory(String containerId, Long processInstanceId,String variableName, int page, int pageSize, Handler<AsyncResult<JsonArray>> result) {
		// TODO Auto-generated method stub
		List<VariableInstance> variableList =KieClient.getKieClient().processServicesClient.findVariableHistory(containerId, processInstanceId, variableName, page, pageSize);
		JsonArray arr = new JsonArray();
		variableList.stream().forEach(data -> {
			JsonObject ob = JsonObject.mapFrom(data);
			arr.add(ob);
		});
		result.handle(Future.succeededFuture(arr));
		System.out.println("Done findVariableHistory!");
	}
	
	@Override
	public void findVariablesCurrentState(String containerId, Long processInstanceId, Handler<AsyncResult<JsonArray>> result) {
		// TODO Auto-generated method stub
		List<VariableInstance> variableList =KieClient.getKieClient().processServicesClient.findVariablesCurrentState(containerId, processInstanceId);
		JsonArray arr = new JsonArray();
		variableList.stream().forEach(data -> {
			JsonObject ob = JsonObject.mapFrom(data);
			arr.add(ob);
		});
		result.handle(Future.succeededFuture(arr));
		System.out.println("Done findVariablesCurrentState!");
	}
	
	@Override
	public void getProcessDefinition(String containerId, String processId, Handler<AsyncResult<JsonObject>> result) {
		// TODO Auto-generated method stub
		ProcessDefinition process =KieClient.getKieClient().processServicesClient.getProcessDefinition(containerId, processId);
		JsonObject obj = JsonObject.mapFrom(process);
		result.handle(Future.succeededFuture(obj));
		System.out.println("Done getProcessDefinition!");
	}
	
	@Override
	public void getProcessInstance(String containerId, Long processInstanceId, Handler<AsyncResult<JsonObject>> result) {
		// TODO Auto-generated method stub
		ProcessInstance process =KieClient.getKieClient().processServicesClient.getProcessInstance(containerId, processInstanceId);
		JsonObject obj = JsonObject.mapFrom(process);
		result.handle(Future.succeededFuture(obj));
		System.out.println("Done getProcessDefinition!");
	}
	
	@Override
	public void getServiceTaskDefinitions(String containerId, String processId,Handler<AsyncResult<JsonObject>> result) {
		// TODO Auto-generated method stub
		ServiceTasksDefinition process =KieClient.getKieClient().processServicesClient.getServiceTaskDefinitions(containerId, processId);
		JsonObject obj = JsonObject.mapFrom(process);
		result.handle(Future.succeededFuture(obj));
		System.out.println("Done getProcessDefinition!");
	}
	
	@Override
	public void getUserTaskDefinitions(String containerId, String processId,Handler<AsyncResult<JsonObject>> result) {
		// TODO Auto-generated method stub
		UserTaskDefinitionList process =KieClient.getKieClient().processServicesClient.getUserTaskDefinitions(containerId, processId);
		JsonObject obj = JsonObject.mapFrom(process);
		result.handle(Future.succeededFuture(obj));
		System.out.println("Done getProcessDefinition!");
	}
	
	@Override
	public void getAssociatedEntityDefinitions(String containerId, String processId,Handler<AsyncResult<JsonObject>> result) {
		// TODO Auto-generated method stub
		AssociatedEntitiesDefinition process =KieClient.getKieClient().processServicesClient.getAssociatedEntityDefinitions(containerId, processId);
		JsonObject obj = JsonObject.mapFrom(process);	
		result.handle(Future.succeededFuture(obj));
		System.out.println("Done getProcessDefinition!");
	}
	
	@Override
	public void getWorkItem(String containerId, Long processInstanceId, Long id, Handler<AsyncResult<JsonObject>> result) {
		// TODO Auto-generated method stub
		WorkItemInstance process =KieClient.getKieClient().processServicesClient.getWorkItem(containerId, processInstanceId, id);
		JsonObject obj = JsonObject.mapFrom(process);	
		result.handle(Future.succeededFuture(obj));
		System.out.println("Done getProcessDefinition!");
	}
	
	@Override
	public void startProcess(String containerId, String processId, Handler<AsyncResult<JsonObject>> result) {
		// TODO Auto-generated method stub
		Long processInstanceId =KieClient.getKieClient().processServicesClient.startProcess(containerId, processId);
		ProcessInstance process =KieClient.getKieClient().processServicesClient.getProcessInstance(containerId, processInstanceId);
		JsonObject obj = JsonObject.mapFrom(process);	
		result.handle(Future.succeededFuture(obj));
		System.out.println("Done getProcessDefinition!");
	}
	
	@Override
	public void startProcessWithVars(String containerId, String processId, JsonObject variables, Handler<AsyncResult<JsonObject>> result) {
		// TODO Auto-generated method stub
		Long processInstanceId =KieClient.getKieClient().processServicesClient.startProcess(containerId, processId, variables.getMap());
		ProcessInstance process =KieClient.getKieClient().processServicesClient.getProcessInstance(containerId, processInstanceId);
		JsonObject obj = JsonObject.mapFrom(process);	
		result.handle(Future.succeededFuture(obj));
		System.out.println("Done getProcessDefinition!");
	}
	
	@Override
	public void completeWorkItem(String containerId,  Long processInstanceId, Long id,  JsonObject results, Handler<AsyncResult<JsonArray>> result) {
		// TODO Auto-generated method stub	
		List<WorkItemInstance> workItems = null;
		try {
			KieClient.getKieClient().processServicesClient.completeWorkItem(containerId, processInstanceId, id, results.getMap()) ;
			workItems = KieClient.getKieClient().processServicesClient.getWorkItemByProcessInstance(containerId, processInstanceId);
		}catch(KieServicesHttpException e) {}
		JsonArray arr = new JsonArray();
		try {
			workItems.stream().forEach(data -> {
				JsonObject ob = JsonObject.mapFrom(data);
				arr.add(ob);
			});
		}catch(NullPointerException e){}
		result.handle(Future.succeededFuture(arr));
		System.out.println("Done getProcessDefinition!");
	}
	
	@Override
	public void getWorkItemByProcessInstance(String containerId,  Long processInstanceId, Handler<AsyncResult<JsonArray>> result) {
		// TODO Auto-generated method stub
		List<WorkItemInstance> workItems = null;
		try {
			workItems = KieClient.getKieClient().processServicesClient.getWorkItemByProcessInstance(containerId, processInstanceId);
		}catch(KieServicesHttpException e) {}
		JsonArray arr = new JsonArray();
		workItems.stream().forEach(data -> {
			JsonObject ob = JsonObject.mapFrom(data);
			arr.add(ob);
		});
		result.handle(Future.succeededFuture(arr));
		System.out.println("Done getProcessDefinition!");
	}
	
//	public void getWorkItemBrocessInstance(String containerId,  Long processInstanceId, Handler<AsyncResult<JsonArray>> result) {
//		// TODO Auto-generated method stub
//		List<WorkItemInstance> workItems = Test.getKieClient().processServicesClient.
//		JsonArray arr = new JsonArray();
//		workItems.stream().forEach(data -> {
//			JsonObject ob = JsonObject.mapFrom(data);
//			arr.add(ob);
//		});
//		result.handle(Future.succeededFuture(arr));
//		System.out.println("Done getProcessDefinition!");
//	}
}
