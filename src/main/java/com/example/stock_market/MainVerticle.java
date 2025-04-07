package com.example.stock_market;

import com.example.stock_market.Router.RouterFactory;
import com.example.stock_market.Router.RouterHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;

import java.util.Collections;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) {

    RouterHandler routerHandler = new RouterHandler(vertx);
    RouterFactory routerFactory = new RouterFactory(vertx, routerHandler);
    Future<Router> routerFuture = routerFactory.setupRoutes();

    Future.all(Collections.singletonList(routerFuture)).compose(result -> {
      Router router = result.resultAt(0);
      return VerticleDeployer.deployVerticle(vertx,router);
    }).onSuccess(v -> startPromise.complete())
      .onFailure(startPromise::fail);
  }
}
