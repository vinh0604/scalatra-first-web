package com.example.app

import org.mockserver.integration.ClientAndServer
import org.mockserver.model.{Header, HttpResponse, HttpRequest}
import org.scalatest.{BeforeAndAfterEach, Matchers, FlatSpec}

import scala.concurrent.Await
import scala.io.Source
import scala.concurrent.duration._
import scala.util.{Failure, Success}

/**
 * Created by vinhbachsy on 24/1/16.
 */
class WebSoSanhRedirectingURLSpec extends FlatSpec with Matchers with BeforeAndAfterEach {
  private var mockWebSoSanhClient:ClientAndServer = null

  override def beforeEach = {
    mockWebSoSanhClient = ClientAndServer.startClientAndServer(8080)

    val html = Source.fromURL(getClass.getResource("/websosanh_redirecting.html")).getLines mkString "\n"
    mockWebSoSanhClient.when(
      new HttpRequest()
        .withMethod("GET")
        .withPath("/so-tay-toi-thay-hoa-vang-tren-co-xanh/2077693626094060653/3433481253691794480/direct.htm")).respond(
      new HttpResponse()
        .withHeader(
          new Header("Content-Type", "text/html; charset=utf-8")
        )
        .withStatusCode(200)
        .withBody(html))
  }

  override def afterEach = {
    mockWebSoSanhClient.stop
  }

  it should "return the redirected link" in {
    val redirected_url = WebSoSanhRedirectingURL.getRedirectedURL("http://localhost:8080/so-tay-toi-thay-hoa-vang-tren-co-xanh/2077693626094060653/3433481253691794480/direct.htm")
    Await.ready(redirected_url, 30.seconds).value.get match {
      case Success(url) => assert(url == "https://www.adayroi.com/so-tay-toi-thay-hoa-vang-tren-co-xanh-p-Lpqw4-f1-2?pi=VeMKK")
      case Failure(ex) => assert(false)
    }
  }
}
