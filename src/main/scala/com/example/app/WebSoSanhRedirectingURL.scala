package com.example.app

import dispatch.{Http, as, url}
import org.jsoup.Jsoup

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by vinhbachsy on 24/1/16.
 */
object WebSoSanhRedirectingURL extends RedirectingURL {
  def getRedirectedURL(redirecting_url: String): Future[String] = {
    val req = Http(url(redirecting_url) OK as.String)
    req.map(extractRedirectedURLFromHTML(_))
  }

  private def extractRedirectedURLFromHTML(html: String): String = {
    val doc = Jsoup.parse(html)
    doc.select(".alink").get(0).attr("href")
  }
}
