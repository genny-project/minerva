package life.genny.cluster;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.serviceproxy.ProxyHelper;
import life.genny.service.KieService;
import life.genny.service.KieServiceImpl;
import life.genny.service.QwandaService;
import life.genny.service.QwandaServiceImpl;

public class Cluster {
	static KieService kieService = new KieServiceImpl();
	static QwandaService qwandaService = new QwandaServiceImpl();
	
	static Handler<AsyncResult<Vertx>> registerAllChannels = vertx -> {
//		EventBus eb = vertx.result();
//		EBConsumers.registerAllConsumer(eb);
//		EBProducers.registerAllProducers(eb);
//		EBCHandlers.registerHandlers();
		ProxyHelper.registerService(KieService.class, vertx.result(), kieService,
			    "kie-service-address");
		ProxyHelper.registerService(QwandaService.class, vertx.result(), qwandaService,
			    "qwanda-service-address");
	};

	public static Future<Void> joinCluster(Vertx vertx) {
		Future<Void> fut = Future.future();
		vertx.factory.clusteredVertx(ClusterConfig.configCluster(),registerAllChannels);
		fut.complete();
		return fut;
	}

}
