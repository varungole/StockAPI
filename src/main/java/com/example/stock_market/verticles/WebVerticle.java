package com.example.stock_market.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;

import java.sql.RowId;

public class WebVerticle extends AbstractVerticle {

  private final Router router;

  public WebVerticle(Router router) {
    this.router = router;
  }
  @Override
  public void start(Promise<Void> startPromise) {
    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8080)
      .onSuccess(server -> {
        System.out.println("HTTP server running on port 8080");
        startPromise.complete();
      })
      .onFailure(startPromise::fail);
  }


}
