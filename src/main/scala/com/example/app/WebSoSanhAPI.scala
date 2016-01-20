package com.example.app

import dispatch.{Http, url, as}
import org.jsoup.Jsoup

import scala.concurrent.{Future, Await}
import scala.util.{Success, Failure}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global


/**
 * Created by vinhbachsy on 12/1/16.
 */

object WebSoSanhAPI {
  def request(keyword: String): Either[Throwable, List[Item]] = {
//    val html = Http(url("http://websosanh.vn:80/s/t%c3%b4i+th%e1%ba%a5y+hoa+v%c3%a0ng+tr%c3%aan+c%e1%bb%8f+xanh/sort-price-1.htm") as_str)
    val domain = Config().getString("websosanh.domain")
    val req = Http(url(s"$domain/s/t%C3%B4i+th%E1%BA%A5y+hoa+v%C3%A0ng+tr%C3%AAn+c%E1%BB%8F+xanh/sort-price-1.htm") OK as.String)
    Await.ready(req, 30.seconds).value.get match {
      case Success(html) => {
        val doc = Jsoup.parse(html)
        Right(WebSoSanhParser.parse(doc))
      }
      case Failure(ex) => Left(ex)
    }
  }
}


