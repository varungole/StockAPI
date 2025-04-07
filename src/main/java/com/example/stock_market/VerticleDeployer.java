package com.example.stock_market;

import com.example.stock_market.RequestService.StockServiceVerticle;
import com.example.stock_market.verticles.WebVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

import java.util.Arrays;

public class VerticleDeployer {

  public static Future<Void> deployVerticle(Vertx vertx, Router router) {
    Promise<Void> promise = Promise.promise();

    Future<String> webVerticleDeployment = vertx.deployVerticle(new WebVerticle(router));
    Future<String> stockServiceDeployment = vertx.deployVerticle(new StockServiceVerticle());
    Future.all(Arrays.asList(webVerticleDeployment, stockServiceDeployment))
      .onSuccess(result -> {
        promise.complete();
      })
      .onFailure(promise::fail);
    return promise.future();
  }
}
