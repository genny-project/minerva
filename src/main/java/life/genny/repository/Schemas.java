package life.genny.repository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.tinkerpop.gremlin.process.traversal.Order;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.step.util.Tree;
import org.apache.tinkerpop.gremlin.structure.Direction;
import org.apache.tinkerpop.gremlin.structure.Element;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.io.IoCore;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphTransaction;
import org.janusgraph.core.Multiplicity;
import org.janusgraph.core.PropertyKey;
import org.janusgraph.core.schema.ConsistencyModifier;
import org.janusgraph.core.schema.JanusGraphIndex;
import org.janusgraph.core.schema.JanusGraphManagement;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.mortbay.util.ajax.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import groovy.json.JsonOutput;
import io.vertx.core.json.JsonObject;
import org.apache.tinkerpop.gremlin.process.computer.traversal.step.VertexComputing;
import org.apache.tinkerpop.gremlin.process.traversal.step.*;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.*;
// import com.google.gson.JsonElement;
import static java.lang.System.out;

public class Schemas {

  public static void main(String... strings) throws IOException {
    
    DataService service = new DataService();
    service.findEntityWithAttr("PER_USER1").forEach(out::println);;
    
    

//     loadSimpleData();
//    JanusClient graphClient = JanusClient.getJanusClient();
//    JanusGraph graph = graphClient.getGraph();
//    GraphTraversalSource traverser = graph.traversal();
//
//    List<Vertex> baseAttributes = traverser.V().has("code", "PER_USER1").as("person1")
//        .valueMap(true).select("person1").out("EntityAttribute").toList();;
        
    
    // traverser.V().has("code", "PER_USER1").as("person1")
    // .local(as("name", "code").select("code", "name").by("code").by("name"))
    // .out("EntityAttribute").as("tree").select("person1").by().toStream()
    // .forEach(out::println);;


    // traverser.V().has("code", "PER_USER1").as("person1")
    // .local(as("name", "code").select("code", "name").by("code").by("name")).select("person1")
    // .out("EntityAttribute").as("base").select("person1","base").by("name").by("value").tree().toStream()
    // .forEach(out::println);;

    // traverser.V().has("code", "PER_USER1").as("dif")
    // .local(as("name", "code").select("code", "name").by("code").by("name")).as("person1")
    // .select("dif").out("EntityAttribute")
    // .local(as("name", "value").select("name",
    // "value").by("name").by("value")).as("lo")
    // .select("person1", "lo").by().tree().toStream().forEach(out::println);

    // out.println(traverser.V().has("code", "PER_USER1").out().as("pp").local(as("name",
    // "code").select("code", "name").by("code").by("name")).select("pp").tree().next());



    // lo.toStream().forEach(o->o.entrySet().stream().forEach(p->{ ((HashMap) p).<HashMap<String,
    // Object>>get("");}));

    // Tree tree = traverser.V().has("code", "PER_USER2").out().tree().next();

    // tree.values().stream().forEach(out::println);

    // out.println(traverser.V().has("code", "PER_USER2")
    // .sideEffect(as("name", "code").select("name", "code").by("name").by("code").store("o"))
    // .sideEffect(out("EntityAttribute").valueMap().tree().store("i")).cap("o", "i").explain());

    // List<Map<String, Object>> lo = traverser.V().properties().valueMap(true).toList();

    // lo.forEach(map -> map.keySet().forEach(a -> out.println(a.toString())));
    // traverser.V().has("code", "PER_USER2").as("per").local(as("name", "code").select("code",
    // "name").by("code").by("name")).select("per").
    // out("EntityAttribute").as("name", "value").select("name", "value",
    // "per").by("name").by("value").tree().toStream().forEach(out::println);
    // traverser.V().has("code", "PER_USER2")

    // .union(as("name", "code").select("name", "code").by("name").by("code"),
    // out("EntityAttribute").tree().value())
    // .toStream().forEach(out::println);;

    // traverser.V().has("code", "PER_USER1").as("la")
    // .local(as("name", "code").select("name", "code").by("name").by("code")).select("la")
    // .out("EntityAttribute").as("lo")
    // .local(as("name", "value").select("name", "value").by("name").by("value")).select("lo")
    // .select("la", "lo").by().toStream().forEach(out::println);

    // traverser.V().has("code", "PER_USER1").out("EntityAttribute")
    // .local(as("name", "value").select("name", "value").by("name").by("value"))
    // .tree().toStream().forEach(out::println);
    // traverser.V().as("person").local(values("name").order().by("name",Order.decr)).select("person").by("code").toStream().forEach(out::println);
    // System.out.println(JsonOutput.toJson(traverser.V().has("code",
    // "PER_USER1").out().valueMap().tree().next()));
    // ByteArrayOutputStream f = new ByteArrayOutputStream();
    // graph.io(IoCore.graphson()).writer().create().writeObject(f, traverser.V().has("code",
    // "PER_USER1").out().tree().next());
    // graph.traversal().V().out().valueMap().toStream().forEach(out::println);
    // graph.traversal().V().values("name", "code").toStream().forEach(out::println);;

    // GraphTraversalSource kdsl= graph.traversal();
    //
    //
    // Gson gsson = new Gson();
    // //
    // // JsonElement k = JSON.parse(f.toString());
    // JSONParser parser = new JSONParser();
    // try {
    // JSONObject json = (JSONObject) parser.parse(f.toString());
    // out.println(json);
    //
    // ;
    // out.println(gsson.toJsonTree(json));
    //
    // Gson gson = new GsonBuilder().setPrettyPrinting().create();
    // JsonParser jp = new JsonParser();
    // JsonElement je = jp.parse(f.toString());
    // com.google.gson.JsonObject ob = je.getAsJsonObject();
    // out.println(ob.get("4208"));
    // String prettyJsonString = gson.toJson(je);
    //// System.out.print(prettyJsonString);
    // } catch (ParseException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }

//    graph.close();

    // graph.traversal().V().filter(op ->
    // op.get().label().equals("BaseEntity")).toStream().forEach(out::println);;
    // graph.traversal().V().sideEffect(pp->((TraversalSource)
    // pp).<TraversalSource>outE().count().store("o")).cap("o").toStream().forEach(out::println);;
    // graph.traversal().V().has("code",
    // "GRP_ROOT").outE().count().toStream().forEach(out::println);
    // graph.traversal().V().sideEffect(kk).cap("o").toStream().forEach(out::println);
    // graph.traversal().V().sideEffect(kkl.hasLabel("parent").store("o")).cap("o").sideEffect(kk.count().store("i")).cap("o",
    // "i").toStream().forEach(out::println);

    // out.println(graph.traversal().V().choose(p -> p.get).next());

    // out.println(graph.traversal().V().has("code","GRP_ROOT").out("parent").valueMap().aggregate("x").cap("x").next());
    // out.println(graph.traversal().V().has("code","GRP_ROOT").out("parent").as("name","code").select("code","name").by("code").by("name").next());

    // graph.traversal().V().values().group().toStream().forEach(out::println);

    // graph.traversal().V().has("code","GRP_ROOT").out("parent").project("name","code").tree().by("name").by("code").toStream().forEach(out::println);;

    // graph.traversal().V().has("code","GRP_ROOT").by("name").by("code").toStream().forEach(out::println);

    // out.println(graph.traversal().V().has("code","GRP_ROOT").properties("name",
    // "code").valueMap(true).profile().next());
    // JanusGraphVertex bas = (JanusGraphVertex)
    // graph.traversal().V().has("code","GRP_ROOT").next();
    // out.println(graph.traversal().V().properties().next());



    // System.out.println(kj);
    // Map k = (Map) graph.traversal().V().out("parent").valueMap().next();;
    //
    // System.out.println(k);
  }

  public static void loadSimpleData() {
    JanusClient graphClient = JanusClient.getJanusClient();
    JanusGraph graph = graphClient.getGraph();
    JanusGraphManagement mgmt = graph.openManagement();

    final PropertyKey code = mgmt.makePropertyKey("code").dataType(String.class).make();
//    JanusGraphManagement.IndexBuilder nameIndexBuilder =
//        mgmt.buildIndex("name", Vertex.class).addKey(code).unique();
//    JanusGraphIndex namei = nameIndexBuilder.buildCompositeIndex();
//    mgmt.setConsistency(namei, ConsistencyModifier.LOCK);

    mgmt.makeVertexLabel("BaseEntity").make();
    mgmt.makeVertexLabel("Attribute").make();

    mgmt.makeEdgeLabel("EntityAttribute").multiplicity(Multiplicity.MULTI).make();
    mgmt.makeEdgeLabel("EntityEntity").multiplicity(Multiplicity.MULTI).make();

    mgmt.commit();

    JanusGraphTransaction tx = graph.newTransaction();

   
    Vertex person1 = tx.addVertex(T.label, "BaseEntity", "code", "PER_USER1", "name", "Person");
    Vertex nameAndres = tx.addVertex(T.label, "Attribute", "code", "PRI_FIRSTNAME1", "name",
        "First Name", "value", "Byron");
    Vertex nameByron = tx.addVertex(T.label, "Attribute", "code", "PRI_MIDDLENAME1", "name",
        "Middle Name", "value", "Andres");
    Vertex age127 =
        tx.addVertex(T.label, "Attribute", "code", "PRI_AGE1", "name", "age", "value", 27);
    Vertex nationality1 = tx.addVertex(T.label, "Attribute", "code", "PRI_NATIONALITY1", "name",
        "Nationality", "value", "Colombia");
    Vertex profession1 = tx.addVertex(T.label, "Attribute", "code", "PRI_PROFESSION1", "name",
        "Profession", "value", "Software Developer");

    Vertex person2 = tx.addVertex(T.label, "BaseEntity", "code", "PER_USER2", "name", "Person");
    Vertex nameSudan = tx.addVertex(T.label, "Attribute", "code", "PRI_FIRSTNAME2", "name",
        "First Name", "value", "Sudan");
    Vertex nameMachikna = tx.addVertex(T.label, "Attribute", "code", "PRI_MIDDLENAME2", "name",
        "Middle Name", "value", "Nora");
    Vertex age227 =
        tx.addVertex(T.label, "Attribute", "code", "PRI_AGE2", "name", "age", "value", 27);
    Vertex nationality2 = tx.addVertex(T.label, "Attribute", "code", "PRI_NATIONALITY2", "name",
        "Nationality", "value", "Nepal");
    Vertex profession2 = tx.addVertex(T.label, "Attribute", "code", "PRI_PROFESSION2", "name",
        "Profession", "value", "Software Developer");

    person1.addEdge("EntityAttribute", nameAndres);
    person1.addEdge("EntityAttribute", nameByron);
    person1.addEdge("EntityAttribute", age127);
    person1.addEdge("EntityAttribute", nationality1);
    person1.addEdge("EntityAttribute", profession1);

    person2.addEdge("EntityAttribute", nameSudan);
    person2.addEdge("EntityAttribute", nameMachikna);
    person2.addEdge("EntityAttribute", age227);
    person2.addEdge("EntityAttribute", nationality2);
    person2.addEdge("EntityAttribute", profession2);
    
    person1.addEdge("EntityEntity", person2, "relationship", "friend");

    tx.commit();

  }

  public static void loadDataTreeView() {
    JanusClient graphClient = JanusClient.getJanusClient();
    JanusGraph graph = graphClient.getGraph();
    JanusGraphManagement mgmt = graph.openManagement();

    final PropertyKey code = mgmt.makePropertyKey("code").dataType(String.class).make();
    JanusGraphManagement.IndexBuilder nameIndexBuilder =
        mgmt.buildIndex("name", Vertex.class).addKey(code).unique();
    JanusGraphIndex namei = nameIndexBuilder.buildCompositeIndex();
    mgmt.setConsistency(namei, ConsistencyModifier.LOCK);

    // final PropertyKey name = mgmt.makePropertyKey("name").dataType(String.class).make();

    // mgmt.makeEdgeLabel("parent").multiplicity(Multiplicity.MULTI).make();
    // mgmt.makeEdgeLabel("owned").multiplicity(Multiplicity.MULTI).make();

    mgmt.makeVertexLabel("BaseEntity").make();
    mgmt.makeVertexLabel("Attribute").make();

    mgmt.commit();

    JanusGraphTransaction tx = graph.newTransaction();

    Vertex attributeImageUrl = tx.addVertex(T.label, "Attribute", "code", "PRI_IMAGE_URL", "name",
        "Image Url", "value", "dir-ico");

    Vertex root = tx.addVertex(T.label, "BaseEntity", "code", "GRP_ROOT", "name", "Root");
    root.addEdge("owned", attributeImageUrl);

    Vertex dashboard =
        tx.addVertex(T.label, "BaseEntity", "code", "GRP_DASHBOARD", "name", "Dashboard");
    dashboard.addEdge("owned", attributeImageUrl);

    Vertex driverView =
        tx.addVertex(T.label, "BaseEntity", "code", "GRP_DRIVER_VIEW", "name", "Driver View");
    driverView.addEdge("owned", attributeImageUrl);

    Vertex ownerView =
        tx.addVertex(T.label, "BaseEntity", "code", "GRP_OWNER_VIEW", "name", "Owner View");
    ownerView.addEdge("owned", attributeImageUrl);

    Vertex loads = tx.addVertex(T.label, "BaseEntity", "code", "GRP_LOADS", "name", "Loads");
    loads.addEdge("owned", attributeImageUrl);

    Vertex contacts =
        tx.addVertex(T.label, "BaseEntity", "code", "GRP_CONTACTS", "name", "Contacts");
    contacts.addEdge("owned", attributeImageUrl);

    Vertex people = tx.addVertex(T.label, "BaseEntity", "code", "GRP_PEOPLE", "name", "people");
    people.addEdge("owned", attributeImageUrl);

    Vertex companies =
        tx.addVertex(T.label, "BaseEntity", "code", "GRP_COMPANYS", "name", "Companies");
    companies.addEdge("owned", attributeImageUrl);

    Vertex users = tx.addVertex(T.label, "BaseEntity", "code", "GRP_USERS", "name", "Users");
    users.addEdge("owned", attributeImageUrl);

    Vertex settings =
        tx.addVertex(T.label, "BaseEntity", "code", "GRP_SETTINGS", "name", "Settings");
    settings.addEdge("owned", attributeImageUrl);

    Vertex available =
        tx.addVertex(T.label, "BaseEntity", "code", "GRP_AVAILABLE", "name", "Available");
    available.addEdge("owned", attributeImageUrl);

    Vertex pending = tx.addVertex(T.label, "BaseEntity", "code", "GRP_PENDING", "name", "Pending");
    pending.addEdge("owned", attributeImageUrl);

    Vertex quote = tx.addVertex(T.label, "BaseEntity", "code", "GRP_QUOTE", "name", "Quote");
    quote.addEdge("owned", attributeImageUrl);

    Vertex accepted =
        tx.addVertex(T.label, "BaseEntity", "code", "GRP_ACCEPTED", "name", "Accepted");
    accepted.addEdge("owned", attributeImageUrl);

    Vertex dispatched =
        tx.addVertex(T.label, "BaseEntity", "code", "GRP_DISPATCHED", "name", "Dispatched");
    dispatched.addEdge("owned", attributeImageUrl);

    Vertex inTransit =
        tx.addVertex(T.label, "BaseEntity", "code", "GRP_IN-TRANSIT", "name", "In-Transit");
    inTransit.addEdge("owned", attributeImageUrl);

    Vertex atDestination =
        tx.addVertex(T.label, "BaseEntity", "code", "GRP_AT-DESTINATION", "name", "At-Destination");
    atDestination.addEdge("owned", attributeImageUrl);

    Vertex delivered =
        tx.addVertex(T.label, "BaseEntity", "code", "GRP_DELIVERED", "name", "Delivered");
    delivered.addEdge("owned", attributeImageUrl);

    Vertex yourDetails =
        tx.addVertex(T.label, "BaseEntity", "code", "GRP_YOUR_DETAILS", "name", "Your-Details");
    yourDetails.addEdge("owned", attributeImageUrl);

    Vertex loadTypes =
        tx.addVertex(T.label, "BaseEntity", "code", "GRP_LOAD_TYPES", "name", "Load-Types");
    loadTypes.addEdge("owned", attributeImageUrl);

    Vertex truckSpecification = tx.addVertex(T.label, "BaseEntity", "code",
        "GRP_TRUCK_SPECIFICATION", "name", "Truck-Specification");
    truckSpecification.addEdge("owned", attributeImageUrl);

    Vertex documents =
        tx.addVertex(T.label, "BaseEntity", "code", "GRP_DOCUMENTS", "name", "Documents");
    documents.addEdge("owned", attributeImageUrl);

    Vertex linfox = tx.addVertex(T.label, "BaseEntity", "code", "GRP_LINFOX", "name", "linfox");
    linfox.addEdge("owned", attributeImageUrl);

    Vertex updatePassword = tx.addVertex(T.label, "BaseEntity", "code", "GRP_UPDATE_PASSWORD",
        "name", "Update-Password");
    updatePassword.addEdge("owned", attributeImageUrl);

    Vertex paymentDetails = tx.addVertex(T.label, "BaseEntity", "code", "GRP_PAYMENT_DETAILS",
        "name", "Payment-Details");
    paymentDetails.addEdge("owned", attributeImageUrl);

    Vertex viewLoads =
        tx.addVertex(T.label, "BaseEntity", "code", "GRP_VIEW_LOADS", "name", "View-Loads");
    viewLoads.addEdge("owned", attributeImageUrl);

    Vertex postLoads =
        tx.addVertex(T.label, "BaseEntity", "code", "GRP_POST_LOADS", "name", "Post-Loads");
    postLoads.addEdge("owned", attributeImageUrl);

    Vertex admin = tx.addVertex(T.label, "BaseEntity", "code", "GRP_ADMIN", "name", "Admin");
    admin.addEdge("owned", attributeImageUrl);

    Vertex pacificDriver =
        tx.addVertex(T.label, "BaseEntity", "code", "GRP_DRIVER", "name", "Pacific-Driver");
    pacificDriver.addEdge("owned", attributeImageUrl);

    Vertex loadOwner =
        tx.addVertex(T.label, "BaseEntity", "code", "GRP_LOAD_OWNER", "name", "Load-Owner");
    loadOwner.addEdge("owned", attributeImageUrl);

    Vertex aurizon = tx.addVertex(T.label, "BaseEntity", "code", "GRP_AURIZON", "name", "Aurizon");
    aurizon.addEdge("owned", attributeImageUrl);

    Vertex pacificNational = tx.addVertex(T.label, "BaseEntity", "code", "GRP_PACIFIC_NATIONAL",
        "name", "Pacific-National");
    pacificNational.addEdge("owned", attributeImageUrl);

    Vertex sctLogistics =
        tx.addVertex(T.label, "BaseEntity", "code", "GRP_SCT_LOGISTICS", "name", "SCT-Logistics");
    sctLogistics.addEdge("owned", attributeImageUrl);

    Vertex glenCameronGroup = tx.addVertex(T.label, "BaseEntity", "code", "GRP_GLEN_CAMERON_GROUP",
        "name", "Glen-Cameron-Group");
    glenCameronGroup.addEdge("owned", attributeImageUrl);


    root.addEdge("parent", dashboard);

    root.addEdge("parent", loads);

    root.addEdge("parent", contacts);

    root.addEdge("parent", settings);

    dashboard.addEdge("parent", driverView);

    dashboard.addEdge("parent", ownerView);

    contacts.addEdge("parent", people);

    contacts.addEdge("parent", companies);

    contacts.addEdge("parent", users);

    driverView.addEdge("parent", available);

    driverView.addEdge("parent", quote);

    driverView.addEdge("parent", accepted);

    driverView.addEdge("parent", dispatched);

    driverView.addEdge("parent", inTransit);

    driverView.addEdge("parent", atDestination);

    driverView.addEdge("parent", delivered);

    ownerView.addEdge("parent", pending);

    ownerView.addEdge("parent", quote);

    ownerView.addEdge("parent", accepted);

    ownerView.addEdge("parent", dispatched);

    ownerView.addEdge("parent", inTransit);

    ownerView.addEdge("parent", atDestination);

    ownerView.addEdge("parent", delivered);

    settings.addEdge("parent", yourDetails);

    settings.addEdge("parent", loadTypes);

    settings.addEdge("parent", truckSpecification);

    settings.addEdge("parent", documents);

    settings.addEdge("parent", updatePassword);

    settings.addEdge("parent", paymentDetails);

    loads.addEdge("parent", viewLoads);

    loads.addEdge("parent", postLoads);


    users.addEdge("parent", admin);

    users.addEdge("parent", pacificDriver);

    users.addEdge("parent", loadOwner);

    companies.addEdge("parent", aurizon);

    companies.addEdge("parent", pacificNational);

    companies.addEdge("parent", linfox);

    companies.addEdge("parent", sctLogistics);

    companies.addEdge("parent", glenCameronGroup);

    tx.commit();

  }
}
