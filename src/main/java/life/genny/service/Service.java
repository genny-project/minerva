package life.genny.service;

import io.vertx.core.AbstractVerticle;
import life.genny.cluster.Cluster;

public class Service extends AbstractVerticle{
	
	@Override
	public void start() {
		KieClient.getKieClient();
		Cluster.joinCluster(vertx);
	}

}
