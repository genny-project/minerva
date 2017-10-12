package life.genny.repository;

import static life.genny.repository.JanusClient.getJanusClient;
import static java.lang.System.out;
import org.apache.tinkerpop.gremlin.process.traversal.Order;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.step.util.BulkSet;
import org.janusgraph.core.JanusGraph;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class DataService {


  private JanusGraph g = getJanusClient().getGraph();
  private GraphTraversalSource traverser = getJanusClient().getGraph().traversal();
  
  List<Map<String, Object>> attrs = new ArrayList<Map<String, Object>>();
  Map<String, Object> tree = new HashMap<String, Object>();
  public Stream<Object> findEntityWithAttr(String code) { 
    return traverser.V()
        .union(
            has("code", code).as("name", "code").select("name", "code").by("name").by("code")
                .store("parent"),
            has("code", code).out("EntityAttribute").as("name", "code", "value")
                .select("name", "code", "value").by("name").by("code").by("value").store("child")
         )
        .select("parent", "child").toStream().map(o -> {
          BulkSet p = (BulkSet) o.get("parent");
          p.stream().forEach(data -> {
            tree = (Map<String, Object>) data;
          });
          BulkSet l = (BulkSet) o.get("child");
          l.stream().forEach(data -> {
            attrs.add((Map<String, Object>) data);
          });
          tree.put("attributes", attrs);
//          out.println(tree);
          return tree;
        }); 
  }
  
//public Stream<Object> findEntityWithAttr(String code) {
//    
//    return traverser.V()
//        .union(
//            has("code", code).as("name", "code").select("name", "code").by("name").by("code")
//                .store("parent"),
//            has("code", code).out("EntityAttribute").as("name", "code", "value")
//                .select("name", "code", "value").by("name").by("code").by("value").store("child"))
//  }

}
