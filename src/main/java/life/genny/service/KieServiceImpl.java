package life.genny.service;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.kie.api.KieServices;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.server.api.exception.KieServicesHttpException;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.api.model.ServiceResponse.ResponseType;
import org.kie.server.api.model.definition.AssociatedEntitiesDefinition;
import org.kie.server.api.model.definition.ProcessDefinition;
import org.kie.server.api.model.definition.ServiceTasksDefinition;
import org.kie.server.api.model.definition.UserTaskDefinitionList;
import org.kie.server.api.model.instance.NodeInstance;
import org.kie.server.api.model.instance.ProcessInstance;
import org.kie.server.api.model.instance.VariableInstance;
import org.kie.server.api.model.instance.WorkItemInstance;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import life.genny.qwanda.entity.BaseEntity;
import life.genny.qwanda.message.QEventMessage;
import life.genny.qwandautils.KeycloakUtils;

public class KieServiceImpl implements KieService {

  static Gson gson = new GsonBuilder()
      .registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
        @Override
        public LocalDateTime deserialize(final JsonElement json, final Type type,
            final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
          return ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalDateTime();
        }

        public JsonElement serialize(final LocalDateTime date, final Type typeOfSrc,
            final JsonSerializationContext context) {
          return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)); // "yyyy-mm-dd"
        }
      }).create();

  @Override
  public void save(Handler<AsyncResult<JsonObject>> result) {
    BaseEntity person = new BaseEntity("PER039", "Adam");
    JsonObject ob = JsonObject.mapFrom(person);
    result.handle(Future.succeededFuture(ob));
    System.out.println("Save Done!");
  }

  @Override
  public void findActiveNodeInstances(String containerId, Long processInstanceId, int page,
      int pageSize, Handler<AsyncResult<JsonArray>> result) {
    // TODO Auto-generated method stub
    List<NodeInstance> nodeList = KieClient.getKieClient().processServicesClient
        .findActiveNodeInstances(containerId, processInstanceId, page, pageSize);
    JsonArray arr = new JsonArray();
    nodeList.stream().forEach(data -> {
      JsonObject ob = JsonObject.mapFrom(data);
      arr.add(ob);
    });
    result.handle(Future.succeededFuture(arr));
    System.out.println("Done findActiveNodeInstances!");
  }

  @Override
  public void findCompletedNodeInstances(String containerId, Long processInstanceId, int page,
      int pageSize, Handler<AsyncResult<JsonArray>> result) {
    // TODO Auto-generated method stub
    List<NodeInstance> nodeList = KieClient.getKieClient().processServicesClient
        .findCompletedNodeInstances(containerId, processInstanceId, page, pageSize);
    JsonArray arr = new JsonArray();
    nodeList.stream().forEach(data -> {
      JsonObject ob = JsonObject.mapFrom(data);
      arr.add(ob);
    });
    result.handle(Future.succeededFuture(arr));
    System.out.println("Done findCompletedNodeInstances!");
  }

  @Override
  public void findNodeInstances(String containerId, Long processInstanceId, int page, int pageSize,
      Handler<AsyncResult<JsonArray>> result) {
    // TODO Auto-generated method stub
    List<NodeInstance> nodeList = KieClient.getKieClient().processServicesClient
        .findNodeInstances(containerId, processInstanceId, page, pageSize);
    JsonArray arr = new JsonArray();
    nodeList.stream().forEach(data -> {
      JsonObject ob = JsonObject.mapFrom(data);
      arr.add(ob);
    });
    result.handle(Future.succeededFuture(arr));
    System.out.println("Done findNodeInstances!");
  }

  @Override
  public void findVariableHistory(String containerId, Long processInstanceId, String variableName,
      int page, int pageSize, Handler<AsyncResult<JsonArray>> result) {
    // TODO Auto-generated method stub
    List<VariableInstance> variableList = KieClient.getKieClient().processServicesClient
        .findVariableHistory(containerId, processInstanceId, variableName, page, pageSize);
    JsonArray arr = new JsonArray();
    variableList.stream().forEach(data -> {
      JsonObject ob = JsonObject.mapFrom(data);
      arr.add(ob);
    });
    result.handle(Future.succeededFuture(arr));
    System.out.println("Done findVariableHistory!");
  }

  @Override
  public void findVariablesCurrentState(String containerId, Long processInstanceId,
      Handler<AsyncResult<JsonArray>> result) {
    // TODO Auto-generated method stub
    List<VariableInstance> variableList = KieClient.getKieClient().processServicesClient
        .findVariablesCurrentState(containerId, processInstanceId);
    JsonArray arr = new JsonArray();
    variableList.stream().forEach(data -> {
      JsonObject ob = JsonObject.mapFrom(data);
      arr.add(ob);
    });
    result.handle(Future.succeededFuture(arr));
    System.out.println("Done findVariablesCurrentState!");
  }

  @Override
  public void getProcessDefinition(String containerId, String processId,
      Handler<AsyncResult<JsonObject>> result) {
    // TODO Auto-generated method stub
    ProcessDefinition process =
        KieClient.getKieClient().processServicesClient.getProcessDefinition(containerId, processId);
    JsonObject obj = JsonObject.mapFrom(process);
    result.handle(Future.succeededFuture(obj));
    System.out.println("Done getProcessDefinition!");
  }

  @Override
  public void getProcessInstance(String containerId, Long processInstanceId,
      Handler<AsyncResult<JsonObject>> result) {
    // TODO Auto-generated method stub
    ProcessInstance process = KieClient.getKieClient().processServicesClient
        .getProcessInstance(containerId, processInstanceId);
    JsonObject obj = JsonObject.mapFrom(process);
    result.handle(Future.succeededFuture(obj));
    System.out.println("Done getProcessDefinition!");
  }

  @Override
  public void getServiceTaskDefinitions(String containerId, String processId,
      Handler<AsyncResult<JsonObject>> result) {
    // TODO Auto-generated method stub
    ServiceTasksDefinition process = KieClient.getKieClient().processServicesClient
        .getServiceTaskDefinitions(containerId, processId);
    JsonObject obj = JsonObject.mapFrom(process);
    result.handle(Future.succeededFuture(obj));
    System.out.println("Done getProcessDefinition!");
  }

  @Override
  public void getUserTaskDefinitions(String containerId, String processId,
      Handler<AsyncResult<JsonObject>> result) {
    // TODO Auto-generated method stub
    UserTaskDefinitionList process = KieClient.getKieClient().processServicesClient
        .getUserTaskDefinitions(containerId, processId);
    JsonObject obj = JsonObject.mapFrom(process);
    result.handle(Future.succeededFuture(obj));
    System.out.println("Done getProcessDefinition!");
  }

  @Override
  public void getAssociatedEntityDefinitions(String containerId, String processId,
      Handler<AsyncResult<JsonObject>> result) {
    // TODO Auto-generated method stub
    AssociatedEntitiesDefinition process = KieClient.getKieClient().processServicesClient
        .getAssociatedEntityDefinitions(containerId, processId);
    JsonObject obj = JsonObject.mapFrom(process);
    result.handle(Future.succeededFuture(obj));
    System.out.println("Done getProcessDefinition!");
  }

  @Override
  public void getWorkItem(String containerId, Long processInstanceId, Long id,
      Handler<AsyncResult<JsonObject>> result) {
    // TODO Auto-generated method stub
    WorkItemInstance process = KieClient.getKieClient().processServicesClient
        .getWorkItem(containerId, processInstanceId, id);
    JsonObject obj = JsonObject.mapFrom(process);
    result.handle(Future.succeededFuture(obj));
    System.out.println("Done getProcessDefinition!");
  }

  @Override
  public void startProcess(String containerId, String processId,
      Handler<AsyncResult<JsonObject>> result) {
    // TODO Auto-generated method stub
    Long processInstanceId =
        KieClient.getKieClient().processServicesClient.startProcess(containerId, processId);
    ProcessInstance process = KieClient.getKieClient().processServicesClient
        .getProcessInstance(containerId, processInstanceId);
    JsonObject obj = JsonObject.mapFrom(process);
    result.handle(Future.succeededFuture(obj));
    System.out.println("Done getProcessDefinition!");
  }

  @Override
  public void startProcessWithVars(String containerId, String processId, JsonObject variables,
      Handler<AsyncResult<JsonObject>> result) {
    // TODO Auto-generated method stub
    Long processInstanceId = KieClient.getKieClient().processServicesClient
        .startProcess(containerId, processId, variables.getMap());
    ProcessInstance process = KieClient.getKieClient().processServicesClient
        .getProcessInstance(containerId, processInstanceId);
    JsonObject obj = JsonObject.mapFrom(process);
    result.handle(Future.succeededFuture(obj));
    System.out.println("Done getProcessDefinition!");
  }

  @Override
  public void completeWorkItem(String containerId, Long processInstanceId, Long id,
      JsonObject results, Handler<AsyncResult<JsonArray>> result) {
    // TODO Auto-generated method stub
    List<WorkItemInstance> workItems = null;
    try {
      KieClient.getKieClient().processServicesClient.completeWorkItem(containerId,
          processInstanceId, id, results.getMap());
      workItems = KieClient.getKieClient().processServicesClient
          .getWorkItemByProcessInstance(containerId, processInstanceId);
    } catch (KieServicesHttpException e) {
    }
    JsonArray arr = new JsonArray();
    try {
      workItems.stream().forEach(data -> {
        JsonObject ob = JsonObject.mapFrom(data);
        arr.add(ob);
      });
    } catch (NullPointerException e) {
    }
    result.handle(Future.succeededFuture(arr));
    System.out.println("Done getProcessDefinition!");
  }

  @Override
  public void getWorkItemByProcessInstance(String containerId, Long processInstanceId,
      Handler<AsyncResult<JsonArray>> result) {
    // TODO Auto-generated method stub
    List<WorkItemInstance> workItems = null;
    try {
      workItems = KieClient.getKieClient().processServicesClient
          .getWorkItemByProcessInstance(containerId, processInstanceId);
    } catch (KieServicesHttpException e) {
    }
    JsonArray arr = new JsonArray();
    workItems.stream().forEach(data -> {
      JsonObject ob = JsonObject.mapFrom(data);
      arr.add(ob);
    });
    result.handle(Future.succeededFuture(arr));
    System.out.println("Done getProcessDefinition!");
  }

  @Override
  public void fireRule(JsonObject results) {
    System.out.println("****************************\n\n\n***********************************");
    final String token = results.getString("token");

    System.out.println(results);
    final QEventMessage eventMsg = gson.fromJson(results.toString(), QEventMessage.class);

    final Map<String, Object> decodedToken = KeycloakUtils.getJsonMap(token);
    // Getting Set of User Roles from QwandaUtils
    final Set<String> userRoles =
        KeycloakUtils.getRoleSet(decodedToken.get("realm_access").toString());

    System.out.println("The Roles value are: " + userRoles.toString());
    
    /*
     * Getting Prj Realm name from KeyCloakUtils - Just cheating the keycloak realm names as we
     * can't add multiple realms in genny keyclaok as it is open-source
     */
    final String projectRealm = KeycloakUtils.getPRJRealmFromDevEnv();
    if ((projectRealm != null) && (!projectRealm.isEmpty())) {
      decodedToken.put("realm", projectRealm);
    } else {
      // Extracting realm name from iss value
      final String realm = (decodedToken.get("iss").toString()
          .substring(decodedToken.get("iss").toString().lastIndexOf("/") + 1));
      // Adding realm name to the decoded token
      decodedToken.put("realm", realm);
    }
    System.out.println("######  The realm name is:  #####  " + decodedToken.get("realm"));
    // Printing Decoded Token values
    for (final Map.Entry entry : decodedToken.entrySet()) {
      System.out.println(entry.getKey() + ", " + entry.getValue());
    }

    final Map<String, String> keyValue = new HashMap<String, String>();
    keyValue.put("token", token);

    // TODO Auto-generated method stub
    List<WorkItemInstance> workItems = null;
    KieCommands commandsFactory = KieServices.Factory.get().getCommands();
    KieServices.Factory.get().getCommands().newCompleteWorkItem(2L, new HashMap<String, Object>());
    Command<?> fireAllRules = commandsFactory.newFireAllRules();
//    Command<?> insert = commandsFactory.newInsert(results.getMap());
    Command<?> insert1 = commandsFactory.newInsert(keyValue);
    Command<?> insert2 = commandsFactory.newInsert(decodedToken);
    Command<?> insert3 = commandsFactory.newInsert(userRoles);
    Command<?> insert4 = commandsFactory.newInsert(results);
    List<Command<?>> insertGlobals = new ArrayList<Command<?>>();

    insertGlobals
        .add(commandsFactory.newSetGlobal("KEYPROTO", KieClient.getKieClient().keycloakProto));
    insertGlobals
        .add(commandsFactory.newSetGlobal("KEYPORT", KieClient.getKieClient().keycloakPort));
    insertGlobals
        .add(commandsFactory.newSetGlobal("KEYCLOAKIP", KieClient.getKieClient().keycloakIP));
    insertGlobals.add(commandsFactory.newSetGlobal("KEYURL", KieClient.getKieClient().keycloakUrl));
    insertGlobals
        .add(commandsFactory.newSetGlobal("KEYID", KieClient.getKieClient().keycloakClientId));
    insertGlobals
        .add(commandsFactory.newSetGlobal("KEYUSER", KieClient.getKieClient().keycloakUser));
    insertGlobals
        .add(commandsFactory.newSetGlobal("KEYPASS", KieClient.getKieClient().keycloakPassword));
    insertGlobals.add(commandsFactory.newSetGlobal("REALM", KieClient.getKieClient().realm));
    insertGlobals.add(
        commandsFactory.newSetGlobal("REACT_APP_VERTX_URL", KieClient.getKieClient().vertxUrl));
    insertGlobals.add(commandsFactory.newSetGlobal("REACT_APP_QWANDA_API_URL",
        KieClient.getKieClient().qwandaApiUrl));
    List<Command<?>> allCommands = new ArrayList<Command<?>>();
    allCommands.addAll(insertGlobals);

    allCommands.addAll(Arrays.asList(insert1, insert2, insert3, insert4, fireAllRules));
//    allCommands.add(insert);
//    allCommands.add(fireAllRules);
    System.out.println("*************000***************\n\n\n******************000*****************");

    Command<?> batchCommand = commandsFactory.newBatchExecution(allCommands);
    System.out.println("*************111***************\n\n\n******************111*****************");
    Vertx.factory.vertx().executeBlocking(data -> {
      ServiceResponse<String> executeResponse =
          KieClient.getKieClient().ruleServicesClient.executeCommands("genny", batchCommand);
      if (executeResponse.getType() == ResponseType.SUCCESS) {
        System.out.println("Commands executed with success! Response: ");
        System.out.println(executeResponse.getResult());
      } else {
        System.out.println("Error executing rules. Message: ");
        System.out.println(executeResponse.getMsg());
      }

      System.out.println("Done Rules Definition!");
    }, atad -> {

    });

  }
  // public void getWorkItemBrocessInstance(String containerId, Long processInstanceId,
  // Handler<AsyncResult<JsonArray>> result) {
  // // TODO Auto-generated method stub
  // List<WorkItemInstance> workItems = Test.getKieClient().processServicesClient.
  // JsonArray arr = new JsonArray();
  // workItems.stream().forEach(data -> {
  // JsonObject ob = JsonObject.mapFrom(data);
  // arr.add(ob);
  // });
  // result.handle(Future.succeededFuture(arr));
  // System.out.println("Done getProcessDefinition!");
  // }
}
