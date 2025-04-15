package com.example.stock_market.ApiClient;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;

import static com.example.stock_market.Util.VertxConstants.API_KEY;

public class StockAPIClient {

  private final WebClient client;

  public StockAPIClient(WebClient client) {
    this.client = client;
  }

  public Future<String> callAlphaVantage(String symbol) {
    Promise<String> promise = Promise.promise();
    String URL_PATH = "https://www.alphavantage.co/query" +
      "?function=TIME_SERIES_DAILY" +
      "&symbol=" + symbol +
      "&outputsize=compact" +
      "&apikey=" + API_KEY;

    client.getAbs(URL_PATH).ssl(true)
      .send(ar -> {
        if(ar.succeeded()) {
          HttpResponse<Buffer> response = ar.result();
          promise.complete(response.bodyAsString());
        } else {
          promise.fail(ar.cause());
        }
      });

    return promise.future();
  }

}
