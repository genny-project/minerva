package life.genny.service;

import java.util.Map;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

@VertxGen
@ProxyGen
public interface SomeDatabaseService {
	// A couple of factory methods to create an instance and a proxy
	static SomeDatabaseService create(Object vertx) {
		return new SomeDatabaseServiceImpl();
	}

	static SomeDatabaseService createProxy(Vertx vertx, String address) {
		return new SomeDatabaseServiceVertxEBProxy(vertx, address);
	}
	// service operations here...

	void save(Handler<AsyncResult<JsonObject>> result);

	void findActiveNodeInstances(String containerId, Long processInstanceId, int page, int pageSize,
			Handler<AsyncResult<JsonArray>> result);

	void findCompletedNodeInstances(String containerId, Long processInstanceId, int page, int pageSize,
			Handler<AsyncResult<JsonArray>> result);

	void findNodeInstances(String containerId, Long processInstanceId, int page, int pageSize,
			Handler<AsyncResult<JsonArray>> result);

	void findVariableHistory(String containerId, Long processInstanceId, String variableName, int page, int pageSize,
			Handler<AsyncResult<JsonArray>> result);

	void findVariablesCurrentState(String containerId, Long processInstanceId, Handler<AsyncResult<JsonArray>> result);

	void getProcessDefinition(String containerId, String processId, Handler<AsyncResult<JsonObject>> result);

	void getProcessInstance(String containerId, Long processInstanceId, Handler<AsyncResult<JsonObject>> result);

	void getServiceTaskDefinitions(String containerId, String processId, Handler<AsyncResult<JsonObject>> result);

	void getUserTaskDefinitions(String containerId, String processId, Handler<AsyncResult<JsonObject>> result);

	void getAssociatedEntityDefinitions(String containerId, String processId, Handler<AsyncResult<JsonObject>> result);

	void getWorkItem(String containerId, Long processInstanceId, Long id, Handler<AsyncResult<JsonObject>> result);

	void startProcess(String containerId, String processId, Handler<AsyncResult<JsonObject>> result);

	void startProcessWithVars(String containerId, String processId, JsonObject variables,
			Handler<AsyncResult<JsonObject>> result);

	void completeWorkItem(String containerId, Long processInstanceId, Long id, JsonObject results,
			Handler<AsyncResult<JsonArray>> result);

	void getWorkItemByProcessInstance(String containerId, Long processInstanceId,
			Handler<AsyncResult<JsonArray>> result);

}
