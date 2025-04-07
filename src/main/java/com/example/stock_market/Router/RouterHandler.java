package com.example.stock_market.Router;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.ext.web.RoutingContext;

import static com.example.stock_market.Util.VertxConstants.*;

public class RouterHandler {

  private final Vertx vertx;

  public RouterHandler(Vertx vertx) {
    this.vertx = vertx;
  }

  public void fetchSingleStockPrice(RoutingContext ctx) {
    sendRequest(ctx, FETCH_STOCK, ctx.pathParam("symbol"));
  }

  public void fetchStockHistory(RoutingContext ctx) {
    sendRequest(ctx, FETCH_STOCK_HISTORY, "");
  }

  private void sendRequest(RoutingContext ctx, String action, Object message) {
    vertx.eventBus().<String>request(action,message)
      .map(Message::body)
      .onSuccess(result -> {
        ctx.response()
          .putHeader("content-type", "application/json")
          .end(result);
      })
      .onFailure(throwable -> {
        System.out.println(throwable.getMessage());
      });
  }


}
