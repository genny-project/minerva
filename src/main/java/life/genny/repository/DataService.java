package life.genny.repository;

import static life.genny.repository.JanusClient.getJanusClient;
import static java.lang.System.out;
import org.apache.tinkerpop.gremlin.process.traversal.Order;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.step.util.BulkSet;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraph;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class DataService {


  JanusGraph g = getJanusClient().getGraph();
  GraphTraversalSource traverser = getJanusClient().getGraph().traversal();

  List<Map<String, Object>> attrs = new ArrayList<Map<String, Object>>();
  Map<String, Object> tree = new HashMap<String, Object>();

//  public Map<String, Object> findEntityWithAttr(String code) {
//    return (Map<String, Object>) g.newTransaction().traversal().V()
//        .union(
//            has("code", code).as("name", "code").select("name", "code").by("name").by("code")
//                .store("parent"),
//            has("code", code).out("EntityAttribute").as("name", "code", "value")
//                .select("name", "code", "value").by("name").by("code").by("value").store("child"))
//        .select("parent", "child").toList().stream().flatMap(o -> {
//          BulkSet p = (BulkSet) o.get("parent");
//          p.stream().forEach(data -> {
//            tree = (Map<String, Object>) data;
//          });
//          BulkSet l = (BulkSet) o.get("child");
//          l.stream().forEach(data -> {
//            attrs.add((Map<String, Object>) data);
//          });
//          tree.put("attributes", attrs);
//          // out.println(tree);
//          g.close();
//          return (Stream<? extends Object>) tree;
//        });
//
//  }

  public List<Map<String, Object>> findE(String code) {
    return g.newTransaction().traversal().V().has("code", code).project("name", "code", "parent")
        .by("name").by("code").by(out("EntityAttribute").project("name", "code", "value").by("name")
            .by("code").by("value").fold())
        .toList();
  }

  public void findM(String code) {
    g.newTransaction().traversal().V().has("code", code).project("name", "code", "parent")
        .by("name").by("code").by(out("EntityAttribute").project("name", "code", "value").by("name")
            .by("code").by("value").fold()).profile()
        .toList().forEach(out::println);;
  }
  
  public void findM2(String code) {
    g.newTransaction().traversal().V().has("code", code).valueMap(true).profile()
        .toList().forEach(out::println);;
  }

}
