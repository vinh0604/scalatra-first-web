package com.example.app

import com.netaporter.uri.Uri
import com.netaporter.uri.config.UriConfig
import com.netaporter.uri.encoding._
import com.netaporter.uri.dsl._
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
  implicit val uriConfig = UriConfig(encoder = percentEncode + spaceAsPlus)
  def request(keyword: String, redirectingURL: RedirectingURL): Either[Throwable, List[Item]] = {
    val domain = Config().getString("websosanh.domain")
    var uri: Uri = s"$domain/s/$keyword/sort-price-1.htm"
    val req = Http(url(uri.toString) OK as.String)
    Await.ready(req, 30.seconds).value.get match {
      case Success(html) => {
        val doc = Jsoup.parse(html)
        Right(getItemsURL(WebSoSanhParser.parse(doc), redirectingURL))
      }
      case Failure(ex) => Left(ex)
    }
  }

  private def getItemsURL(items: List[Item], redirectingURL: RedirectingURL): List[Item] = {
    val links = items.map(item => redirectingURL.getRedirectedURL(item.link))
    Await.ready(Future.sequence(links), 30.seconds).value.get match {
      case Success(links) => {
        items.zip(links).map(el => {
          el._1.copy(el._1.title, el._1.store, el._2)
        })
      }
      case Failure(ex) => items
    }
  }
}


