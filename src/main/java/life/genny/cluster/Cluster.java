package life.genny.cluster;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.serviceproxy.ProxyHelper;
import life.genny.service.SomeDatabaseService;
import life.genny.service.SomeDatabaseServiceImpl;

public class Cluster {
	static SomeDatabaseService service = new SomeDatabaseServiceImpl();
	static Handler<AsyncResult<Vertx>> registerAllChannels = vertx -> {
//		EventBus eb = vertx.result();
//		EBConsumers.registerAllConsumer(eb);
//		EBProducers.registerAllProducers(eb);
//		EBCHandlers.registerHandlers();
		ProxyHelper.registerService(SomeDatabaseService.class, vertx.result(), service,
			    "database-service-address");
	};

	public static Future<Void> joinCluster(Vertx vertx) {
		Future<Void> fut = Future.future();
		vertx.factory.clusteredVertx(ClusterConfig.configCluster(),registerAllChannels);
		fut.complete();
		return fut;
	}

}
