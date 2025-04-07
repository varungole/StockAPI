package com.example.stock_market.Router;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class RouterFactory {

  private final Vertx vertx;
  private final RouterHandler routerHandler;

  public RouterFactory(Vertx vertx,RouterHandler routerHandler) {
    this.vertx = vertx;
    this.routerHandler = routerHandler;
  }

  public Future<Router> setupRoutes() {
    Router router = Router.router(vertx);
    router.get("/api/stocks/:symbol").handler(routerHandler::fetchSingleStockPrice);
    router.get("/api/stocks/:symbol/history").handler(routerHandler::fetchStockHistory);
    return Future.succeededFuture(router);
  }
}
