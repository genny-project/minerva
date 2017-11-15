package life.genny.service;

import io.vertx.core.AbstractVerticle;
import life.genny.cluster.Cluster;

public class Service extends AbstractVerticle{
	
	@Override
	public void start() {
		Cluster.joinCluster(vertx);
	}

}
