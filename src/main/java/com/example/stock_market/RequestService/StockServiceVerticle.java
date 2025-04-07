package com.example.stock_market.RequestService;

import static com.example.stock_market.Util.VertxConstants.*;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;

public class StockServiceVerticle extends AbstractVerticle {

  @Override
  public void start() {
    vertx.eventBus().consumer(FETCH_STOCK, this::fetchStock);
  }

  public void fetchStock(Message<Object> message) {
    String stockId = message.body().toString();
    String jsonResponse = String.format("{\"stockId\": \"%s\", \"price\": 123.45}", stockId);
    message.reply(jsonResponse);
  }
}
