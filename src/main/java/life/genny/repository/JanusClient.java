package life.genny.repository;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import life.genny.service.KieClient;

public class JanusClient {

  private static JanusGraph graph;
  private GraphTraversalSource traverser;
  private static JanusClient janusClient;

  private JanusClient() {
    graph = JanusGraphFactory.open("janusgraph-cassandra-es.properties");
  }

  public static JanusClient getJanusClient() {
    if (janusClient == null) {
      janusClient = new JanusClient();
    }
    return janusClient;
  }

  /**
   * @return the graph
   */
  public JanusGraph getGraph() {
    return graph;
  }

  /**
   * @param graph the graph to set
   */
  public void setGraph(JanusGraph graph) {
    JanusClient.graph = graph;
  }


}
