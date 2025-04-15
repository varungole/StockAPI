package com.example.stock_market.RequestService;

import static com.example.stock_market.Util.VertxConstants.*;

import com.example.stock_market.ApiClient.StockAPIClient;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.ext.web.client.WebClient;

public class StockServiceVerticle extends AbstractVerticle {

  private WebClient client;
  private StockAPIClient stockAPIClient;

  @Override
  public void start() {
    client = WebClient.create(vertx);
    vertx.eventBus().consumer(FETCH_STOCK, this::fetchStockFromApi);
    this.stockAPIClient = new StockAPIClient(client);
  }

  public void fetchStockFromApi(Message<Object> message) {
    String stockId = message.body().toString();
    stockAPIClient.callAlphaVantage(stockId)
      .onSuccess(message::reply)
      .onFailure(err -> {
        System.err.println("API error " + err.getMessage());
        message.fail(500, "API Failure");
      });
  }

}
