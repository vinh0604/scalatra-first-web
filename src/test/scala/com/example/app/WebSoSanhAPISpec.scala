package com.example.app

import org.mockserver.client.server.MockServerClient
import org.mockserver.model.{HttpResponse, Parameter, HttpRequest}
import org.mockserver.integration.ClientAndServer.startClientAndServer
import org.scalatest.{BeforeAndAfterEach, Matchers, FlatSpec}

import scala.io.Source

/**
 * Created by vinhbachsy on 12/1/16.
 */
class WebSoSanhAPISpec extends FlatSpec with Matchers with BeforeAndAfterEach {
  override def beforeEach() {
    val mockServer = startClientAndServer(1080)
  }

  it should "request to websosanh with correct parameters" in {
    val html = Source.fromURL(getClass.getResource("/websosanh.html")).getLines mkString "\n"
    val mockWebSoSanhClient = new MockServerClient("websosanh.vn", 80)
      .when(new HttpRequest()
        .withMethod("GET")
        .withPath("/s/tôi+thấy+hoa+vàng+trên+cỏ+xanh/sort-price-1.htm"))
      .respond(new HttpResponse()
        .withStatusCode(200)
        .withBody(html))

    val keyword = "Tôi thấy hoa vàng trên cỏ xanh"
    WebSoSanhAPI.request(keyword)
  }
}
