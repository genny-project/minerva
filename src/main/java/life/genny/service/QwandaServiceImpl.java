package life.genny.service;

import org.kie.server.api.model.instance.WorkItemInstance;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class QwandaServiceImpl implements QwandaService {

  @Override
  public void saveBaseEntity(Handler<AsyncResult<JsonObject>> result) {
    // TODO Auto-generated method stub
    System.out.println("ok");
  }

  @Override
  public void getEntityAttr(Handler<AsyncResult<JsonObject>> result) {
    // TODO Auto-generated method stub
//    WorkItemInstance process = KieClient.getKieClient().processServicesClient
//        .getWorkItem(containerId, processInstanceId, id);
//    JsonObject obj = JsonObject.mapFrom(process);
//    result.handle(Future.succeededFuture(obj));
//    System.out.println("Done getProcessDefinition!");
  }

}
