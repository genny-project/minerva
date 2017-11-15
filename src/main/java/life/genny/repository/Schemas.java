package life.genny.repository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
// import javax.validation.Validation;
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
// import com.sun.prism.PixelFormat.DataType;
import groovy.json.JsonOutput;
import io.vertx.core.json.JsonObject;
import life.genny.qwanda.entity.BaseEntity;
import life.genny.qwanda.entity.EntityEntity;
import life.genny.qwanda.entity.EntityEntityId;
import life.genny.qwanda.validation.Validation;
import org.apache.tinkerpop.gremlin.process.computer.traversal.step.VertexComputing;
import org.apache.tinkerpop.gremlin.process.traversal.step.*;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.*;
// import com.google.gson.JsonElement;
import life.genny.qwanda.*;
import life.genny.qwanda.attribute.Attribute;
import life.genny.qwanda.attribute.EntityAttribute;
import life.genny.qwanda.datatype.DataType;
import static java.lang.System.out;

public class Schemas {

  private final static String GET = "get";

  public static Map<Object, Field> classFields(Field[] classFields) {
    return Stream.of(classFields).collect(Collectors.toMap(
        f -> GET + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1), f -> f));
  }

  // public static Map<Object, Object> cla(Field[] classFields) {
  // return Stream.of(classFields)
  // .collect(Collectors.toMap(f -> GET + f.getType() + f.getName().substring(1), f -> f));
  // }

  public static Map<Object, Object> classMethods(Method[] classMethods) {
    return Stream.of(classMethods).map(s -> s.getName().toString()).distinct()
        .collect(Collectors.toMap(m -> m, m -> m));
  }

  // .collect(Collectors.toMap(k -> k.getKey(), f -> {
  // String plo =classFromGenerics(f.getValue(), packageName) ==null?classFromGenerics(f.getValue(),
  // packageName):f.getValue().getType().getName();
  //
  // return plo;
  // }
  public static Map<Object, Class> beanFields(Class objectType, String packageName) {
    return classFields(objectType.getDeclaredFields()).entrySet().stream()
        .filter(entry -> (classMethods(objectType.getMethods()).get(entry.getKey()) != null)
            && (genericParameters(entry.getValue(), packageName) != null)
            || entry.getValue().getType().getName().contains(packageName))
        .collect(Collectors.toMap(k -> k.getKey(),
            f -> genericParameters(f.getValue(), packageName) == null ? f.getValue().getType()
                : genericParameters(f.getValue(), packageName)));
  }

  public static Class genericParameters(Field field, String packageName) {
    try {
      ParameterizedType typeFromGenerics = (ParameterizedType) field.getGenericType();
      Class<?> classType = (Class<?>) typeFromGenerics.getActualTypeArguments()[0];
      if (classType.getPackage().getName().contains(packageName)) {
        // return true;
        // System.out.println(classType+"jskjksljldjskdj lsklds");
        // return typeFromGenerics.getActualTypeArguments()[0].getTypeName();
        return classType;
      } else
        // return false;
        return null;
    } catch (ClassCastException e) {
      // Keep traversing
      // return false;
      return null;
    }
  }

  // public static void runi() {
  // beanFields(Attribute.class, "life.genny.qwanda").entrySet().forEach(data -> {
  // // out.println(data.getValue());
  // Class clazz;
  // try {
  // Class clazzy = Class.forName(data.getValue());
  //
  // // if (EntityEntity.class == clazzy)
  // // out.println("yes");
  // out.println(clazzy.getSimpleName());
  // } catch (ClassNotFoundException e) {
  // // TODO Auto-generated catch block
  // e.printStackTrace();
  // }
  // });;
  // }

  // static Optional<Map<String, Object>> lok = null;

  public static boolean run(Class clazz, Set<Class> clazzes) {
    return beanFields(clazz, "life.genny.qwanda").entrySet().stream().map(map -> {
      if (clazzes.contains(map.getValue())) {
        out.println(clazz.getSimpleName() + "   " + map.getValue().getSimpleName() + " "
            + clazzes.contains(map.getValue()));
        return run(map.getValue(), clazzes);
      } else
        return false;
    }).reduce((identity, accumulator) -> identity).get();
  }

  static Set<Class> s = new HashSet<>();
  public static Integer tryTest(Class c, Set<Class> classes) {
    Map<Object, Class> p = beanFields(c, "life.genny.qwanda");
//    System.out.println(p.entrySet().stream().map(data -> 1)
//        .reduce((identity, accumulator) -> identity + accumulator).get());
    try{
      return p.values().stream().distinct().map(data -> {
//        if (classes.contains(data))
        System.out.println(data.getSimpleName()+"-----------"+c.getSimpleName());
        System.out.println(s.contains(data));
        if (classes.contains(data))
        { 
          if(s.contains(data))
          { 
//            out.println(data.getSimpleName());
            s = new HashSet<>();
            return 0;
          }
          s.add(data);
          return tryTest(data, classes) +1 ;
        }
        else
          return 0;
      }).reduce((identity, accumulator) -> identity + accumulator).get();
    }catch(NoSuchElementException e) {
      out.println("\n\n\n");
      return 0;
    }
    
  }

  public static void main(String... strings) throws IOException {

//     Map<String, String> map = new HashMap<String, String>();
    // map.put("key", "value");
    // System.out.println(map.get("keyi"));

    // classFields(Attribute.class.getDeclaredFields()).entrySet().stream().forEach(out::println);


    // Set<String> listClasses = new HashSet<String>();
    // System.out.println(listClasses.add("hola"));
    // System.out.println(listClasses.remove("hola"));
    // System.out.println(listClasses.add("hola"));
    // System.out.println(listClasses.add("hola"));
    Set<Class> classes = new HashSet<>();
    classes.add(Validation.class);
    classes.add(DataType.class);
    classes.add(Attribute.class);
    classes.add(BaseEntity.class);
    classes.add(EntityAttribute.class);
    classes.add(EntityEntity.class);
    classes.add(Question.class);
    classes.add(Ask.class);
    classes.add(EntityEntityId.class);
    // System.out.println(run(Validation.class, classes));


//    beanFields(Attribute.class, "life.genny.qwanda").entrySet().forEach(out::println);;

    
//     Set<String> listClasses = new HashSet<String>();
    
    
    
//     classes.stream().forEach(data->{
//     beanFields(data, "life.genny.qwanda").entrySet().stream().forEach(map -> {
//    // listClasses.add(map.getValue());
//     if(classes.contains(map.getValue()))
//     beanFields(map.getValue(), "life.genny.qwanda");
//    if(classes.contains(map.getValue()))
//     out.println(data.getSimpleName()+" "+map.getValue().getSimpleName() +" "+classes.contains(map.getValue()));
//     });
//     });
     
    out.println(tryTest( EntityEntityId.class, classes));
//     Map<String, Integer> ordered = new HashMap<String, Integer>();
//     Map<String, Integer> ord= new HashMap<String, Integer>();
//    classes.stream().peek(out::println).map(clazz->{
////      System.out.println(tryTest( clazz, classes));
//      ordered.put(clazz.getSimpleName(), tryTest( clazz, classes));
//      return tryTest( clazz, classes);
//    }).peek(out::println).sorted().forEach(p->p.intValue());;
//    
//    List<String> cs = new ArrayList<>();
//    List<Integer> c = new ArrayList<>();
//    ordered.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach(out::println);
//    System.out.println(ordered);
//    
    
//    ordered.values().stream().sorted().forEach(out::println);
    
//    System.out.println(tryTest( Ask.class, classes));
    // listClasses.forEach(out::println);
    // beanFields(Attribute.class, "life.genny.qwanda").entrySet().stream().forEach(out::println);
    // runi();

    // List<Class> classes = new ArrayList<>();
    // classes.add(Validation.class);
    // classes.add(DataType.class);
    // classes.add(Attribute.class);
    // classes.add(BaseEntity.class);
    // classes.add(EntityAttribute.class);
    // classes.add(EntityEntity.class);
    // classes.add(Question.class);
    // classes.add(Ask.class);
    //
    // Map<String,Object> mp = new HashMap<String,Object>();
    // List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
    // List<String> list1 = new ArrayList<String>();
    // classes.stream().forEach(clazz -> {
    // beanFields(clazz, "life.genny.qwanda").entrySet().stream().forEach(data -> {
    // try {
    // Class clazzy = Class.forName(data.getValue());
    //// System.out.println(clazz.getSimpleName()+" "+clazzy.getSimpleName());
    //// if (mp.containsKey(o.getSimpleName())
    //// && mp.get(o.getSimpleName()).equals(clazzy.getSimpleName()))
    //// System.out.println("error");
    //// else
    //// mp.put(clazzy.getSimpleName(), o.getSimpleName());
    // list1.add(clazz.getSimpleName()+"k");
    // list1.add(clazzy.getSimpleName()+"v");
    // } catch (ClassNotFoundException e) {
    // e.printStackTrace();
    // }
    // });
    // });
    //
    // list1.stream().sorted().distinct().forEach(out::println);
    // Map<String,Object> mp = new HashMap<String,Object>();
    // classes.stream().forEach(o->{
    // beanFields(o, "life.genny.qwanda").entrySet().stream().distinct().map(data->{
    // Class clazz;
    // try {
    // Class clazzy = Class.forName(data.getValue());
    // if(mp.containsKey(o.getSimpleName())&&mp.get(o.getSimpleName()).equals(clazzy.getSimpleName()))
    // System.out.println("error");
    // else
    // mp.put(clazzy.getSimpleName(),o.getSimpleName());
    // } catch (ClassNotFoundException e) {
    // e.printStackTrace();
    // }
    // System.out.println(mp);
    // return mp;
    // }).distinct().reduce((p,l)-> {p.putAll(l); return p;});
    // });
    // Map<String,Object> mpi = new HashMap<String,Object>();
    // mpi.put("Validation", "dd");
    // lok.get().putAll(mpi);
    // System.out.println(mp);
    // loadSimpleData();
    // classFields(BaseEntity.class.getFields()).entrySet().forEach(out::println);
    // out.println( beanFields(BaseEntity.class));
    //
    // out.println(beanFields(BaseEntity.class).get("getLinks"));
    //
    // out.println(classFields(BaseEntity.class.getFields()));

    // out.print(BaseEntity.class.getDeclaredFields()[4]);
    // System.out.println("fslkdfsj");
    // classFromGenerics(Person.class.getDeclaredFields()[0], Person.class.getPackage().getName());
    // ClassLoader l = null;



    // DataService service = new DataService();
    // service.findEntityWithAttr("PER_USER1").forEach(out::println);
    // service.findEntityWithAttr("PER_USER1").entrySet().forEach(out::println);
    // service.g.close();
    // service.g.newTransaction();
    // service.findE("PER_USER1").stream().forEach(out::println);
    // service.findE("PER_USER1").stream().forEach(out::println);

    // service.findM("PER_USER1");
    // service.findM2("PER_USER3");
    // service.findM("PER_USER1");
    // service.findM("PER_USER1");
    // service.findM2("PER_USER3");
    // loadSimpleData();
    // JanusClient graphClient = JanusClient.getJanusClient();
    // JanusGraph graph = graphClient.getGraph();
    // GraphTraversalSource traverser = graph.traversal();
    //
    // List<Vertex> baseAttributes = traverser.V().has("code", "PER_USER1").as("person1")
    // .valueMap(true).select("person1").out("EntityAttribute").toList();;


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

    // graph.close();

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
    // JanusGraphManagement.IndexBuilder nameIndexBuilder =
    // mgmt.buildIndex("name", Vertex.class).addKey(code).unique();
    // JanusGraphIndex namei = nameIndexBuilder.buildCompositeIndex();
    // mgmt.setConsistency(namei, ConsistencyModifier.LOCK);

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
    Vertex profession2 = tx.addVertex(T.label, "Attribute", "code", "PRI_PROFESSION2", "name",
        "Profession", "value", "Software Developer");
    Vertex profession3 = tx.addVertex(T.label, "Attribute", "code", "PRI_PROFESSION3", "name",
        "Profession", "value", "Software Developer");
    Vertex profession4 = tx.addVertex(T.label, "Attribute", "code", "PRI_PROFESSION4", "name",
        "Profession", "value", "Software Developer");
    Vertex profession5 = tx.addVertex(T.label, "Attribute", "code", "PRI_PROFESSION5", "name",
        "Profession", "value", "Software Developer");
    Vertex profession6 = tx.addVertex(T.label, "Attribute", "code", "PRI_PROFESSION6", "name",
        "Profession", "value", "Software Developer");
    Vertex profession7 = tx.addVertex(T.label, "Attribute", "code", "PRI_PROFESSION7", "name",
        "Profession", "value", "Software Developer");
    Vertex profession8 = tx.addVertex(T.label, "Attribute", "code", "PRI_PROFESSION8", "name",
        "Profession", "value", "Software Developer");
    Vertex profession9 = tx.addVertex(T.label, "Attribute", "code", "PRI_PROFESSION9", "name",
        "Profession", "value", "Software Developer");
    Vertex profession10 = tx.addVertex(T.label, "Attribute", "code", "PRI_PROFESSION10", "name",
        "Profession", "value", "Software Developer");
    Vertex profession11 = tx.addVertex(T.label, "Attribute", "code", "PRI_PROFESSION11", "name",
        "Profession", "value", "Software Developer");
    Vertex profession12 = tx.addVertex(T.label, "Attribute", "code", "PRI_PROFESSION12", "name",
        "Profession", "value", "Software Developer");
    Vertex profession13 = tx.addVertex(T.label, "Attribute", "code", "PRI_PROFESSION13", "name",
        "Profession", "value", "Software Developer");
    Vertex profession14 = tx.addVertex(T.label, "Attribute", "code", "PRI_PROFESSION14", "name",
        "Profession", "value", "Software Developer");
    Vertex profession15 = tx.addVertex(T.label, "Attribute", "code", "PRI_PROFESSION15", "name",
        "Profession", "value", "Software Developer");
    Vertex profession16 = tx.addVertex(T.label, "Attribute", "code", "PRI_PROFESSION16", "name",
        "Profession", "value", "Software Developer");
    Vertex profession17 = tx.addVertex(T.label, "Attribute", "code", "PRI_PROFESSION17", "name",
        "Profession", "value", "Software Developer");
    Vertex profession18 = tx.addVertex(T.label, "Attribute", "code", "PRI_PROFESSION18", "name",
        "Profession", "value", "Software Developer");
    Vertex profession19 = tx.addVertex(T.label, "Attribute", "code", "PRI_PROFESSION19", "name",
        "Profession", "value", "Software Developer");
    Vertex profession20 = tx.addVertex(T.label, "Attribute", "code", "PRI_PROFESSION20", "name",
        "Profession", "value", "Software Developer");
    Vertex profession21 = tx.addVertex(T.label, "Attribute", "code", "PRI_PROFESSION21", "name",
        "Profession", "value", "Software Developer");
    Vertex profession22 = tx.addVertex(T.label, "Attribute", "code", "PRI_PROFESSION22", "name",
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
    Vertex profession200 = tx.addVertex(T.label, "Attribute", "code", "PRI_PROFESSION200", "name",
        "Profession", "value", "Software Developer");

    Vertex person3 = tx.addVertex(T.label, "BaseEntity", "code", "PER_USER3", "namwe", "Person",
        "namfe", "Person", "namfse", "Person", "namae", "Person", "namde", "Person", "namsfse",
        "Person", "namxe", "Person", "namse", "Person", "namfse", "Person", "namfe", "Person",
        "namsfe", "Person", "nasfsme", "Person", "nameg", "Person", "namfse", "Person", "nafsme",
        "Person", "namghe", "Person", "nasme", "Person", "namse", "Person", "namhe", "Person",
        "namfse", "Person", "nafssme", "Person");

    person1.addEdge("EntityAttribute", nameAndres);
    person1.addEdge("EntityAttribute", nameByron);
    person1.addEdge("EntityAttribute", age127);
    person1.addEdge("EntityAttribute", nationality1);
    person1.addEdge("EntityAttribute", profession2);
    person1.addEdge("EntityAttribute", profession3);
    person1.addEdge("EntityAttribute", profession4);
    person1.addEdge("EntityAttribute", profession5);
    person1.addEdge("EntityAttribute", profession6);
    person1.addEdge("EntityAttribute", profession7);
    person1.addEdge("EntityAttribute", profession8);
    person1.addEdge("EntityAttribute", profession9);
    person1.addEdge("EntityAttribute", profession10);
    person1.addEdge("EntityAttribute", profession11);
    person1.addEdge("EntityAttribute", profession12);
    person1.addEdge("EntityAttribute", profession13);
    person1.addEdge("EntityAttribute", profession14);
    person1.addEdge("EntityAttribute", profession15);
    person1.addEdge("EntityAttribute", profession16);
    person1.addEdge("EntityAttribute", profession17);
    person1.addEdge("EntityAttribute", profession18);
    person1.addEdge("EntityAttribute", profession19);
    person1.addEdge("EntityAttribute", profession20);
    person1.addEdge("EntityAttribute", profession21);
    person1.addEdge("EntityAttribute", profession22);

    person2.addEdge("EntityAttribute", nameSudan);
    person2.addEdge("EntityAttribute", nameMachikna);
    person2.addEdge("EntityAttribute", age227);
    person2.addEdge("EntityAttribute", nationality2);
    person2.addEdge("EntityAttribute", profession200);

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
