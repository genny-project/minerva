package life.genny.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.core.marshalling.impl.ProtobufMessages.KnowledgeBase;
import org.kie.api.KieServices;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.io.ResourceType;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.api.model.ServiceResponse.ResponseType;
import org.kie.server.api.model.definition.ProcessDefinition;
import org.kie.server.api.model.definition.ServiceTasksDefinition;
import org.kie.server.api.model.definition.TaskInputsDefinition;
import org.kie.server.api.model.definition.TaskOutputsDefinition;
import org.kie.server.api.model.definition.UserTaskDefinitionList;
import org.kie.server.api.model.definition.VariablesDefinition;
import org.kie.server.api.model.instance.NodeInstance;
import org.kie.server.api.model.instance.ProcessInstance;
import org.kie.server.api.model.instance.VariableInstance;
import org.kie.server.api.model.instance.WorkItemInstance;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.ProcessServicesClient;
import org.kie.server.client.RuleServicesClient;
import org.kie.server.client.UserTaskServicesClient;

import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class KieClient {

	private static final String URL = System.getenv("KIE_SERVER") != null ? System.getenv("KIE_SERVER")
			: "http://127.0.0.1:8230/kie-server/services/rest/server";
	private static final String USER = System.getenv("KIE_USERNAME") != null ? System.getenv("KIE_USERNAME")
			: "kieserver";
	private static final String PASSWORD = System.getenv("KIE_PASSWORD") != null ? System.getenv("KIE_PASSWORD")
			: "kieserver1!";
	private KieServicesConfiguration conf;
	private KieServicesClient kieServicesClient;
	private static final Logger logger = LoggerFactory.getLogger(KieClient.class);
	private static final MarshallingFormat FORMAT = MarshallingFormat.JSON;
	private UserTaskServicesClient userTaskServicesClient;
	public ProcessServicesClient processServicesClient;
	private RuleServicesClient ruleServicesClient;
	private final String BUSINESSTYPE = "businessType";
	private final String BUSINESSEVENT = "businessEvent";
	  

	final String qwandaApiUrl = System.getenv("REACT_APP_QWANDA_API_URL");
	final String vertxUrl = System.getenv("REACT_APP_VERTX_URL");
	final String hostIp = System.getenv("HOSTIP");
	

	String keycloakProto = System.getenv("KEYCLOAK_PROTO")!=null?System.getenv("KEYCLOAK_PROTO"): "http://";
    String keycloakPort = System.getenv("KEYCLOAK_PORT")!=null?System.getenv("KEYCLOAK_PORT"): "8180";
    String keycloakIP = System.getenv("HOSTIP")!=null?System.getenv("HOSTIP"): "localhost";
	String keycloakUrl = keycloakProto+keycloakIP+":"+keycloakPort;
	
	
	String keycloakClientId = System.getenv("KEYCLOAK_CLIENTID")!=null?System.getenv("KEYCLOAK_CLIENTID"):"curl";
	String keycloakUser = System.getenv("KEYCLOAK_USERID")!=null?System.getenv("KEYCLOAK_USERID"):"user1";
	String keycloakPassword = System.getenv("KEYCLOAK_PASSWORD")!=null?System.getenv("KEYCLOAK_PASSWORD"):"password1";
	String realm = System.getenv("KEYCLOAK_REALM")!=null?System.getenv("KEYCLOAK_REALM"):"wildfly-swarm-keycloak-example"; 	
	String secret = System.getenv("KEYCLOAK_SECRET")!=null?System.getenv("KEYCLOAK_SECRET"):"056b73c1-7078-411d-80ec-87d41c55c3b4";  

	
	String qwandaServiceUrl = System.getenv("REACT_APP_QWANDA_API_URL")==null?System.getenv("REACT_APP_QWANDA_API_URL"):qwandaApiUrl;
	

	public void initialize() {
		conf = KieServicesFactory.newRestConfiguration(URL, USER, PASSWORD);
		conf.setMarshallingFormat(FORMAT);
		kieServicesClient = KieServicesFactory.newKieServicesClient(conf);
		processServicesClient = kieServicesClient.getServicesClient(ProcessServicesClient.class);
		// userTaskServicesClient =
		// kieServicesClient.getServicesClient(UserTaskServicesClient.class);
		ruleServicesClient = kieServicesClient.getServicesClient(RuleServicesClient.class);
	}

	private static KieClient kieclient = null;

	private KieClient() {
	}

	public static KieClient getKieClient() {
		if (kieclient == null) {
			kieclient = new KieClient();
			kieclient.initialize();
		}
		return kieclient;
	}
}