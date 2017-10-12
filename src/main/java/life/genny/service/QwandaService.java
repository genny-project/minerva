package life.genny.service;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

@VertxGen
@ProxyGen
public interface QwandaService {
	
	static QwandaService create(Object vertx) {
		return new QwandaServiceImpl();
	}

	static QwandaService createProxy(Vertx vertx, String address) {
		return new QwandaServiceVertxEBProxy(vertx, address);
	}

	void saveBaseEntity(Handler<AsyncResult<JsonObject>> result);
	
	void getEntityAttr(Handler<AsyncResult<JsonObject>> result);
}
