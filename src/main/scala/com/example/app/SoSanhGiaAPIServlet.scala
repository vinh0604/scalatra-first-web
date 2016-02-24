package com.example.app

import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json.JacksonJsonSupport

/**
 * Created by vinhbachsy on 24/2/16.
 */
class SoSanhGiaAPIServlet extends ScalatraFirstWebStack with JacksonJsonSupport {
  before() {
    contentType = formats("json")
  }

  get("/products") {
    WebSoSanhAPI.request(params("title"), WebSoSanhRedirectingURL) match {
      case Left(error) => halt(500, Map("success" -> false, "error" -> error.getMessage))
      case Right(products) => Map("success" -> true, "data" -> products)
    }
  }

  protected implicit lazy val jsonFormats: Formats = DefaultFormats + new JodaMoneySerializer
}
