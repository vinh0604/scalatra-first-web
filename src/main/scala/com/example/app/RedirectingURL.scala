package com.example.app

import scala.concurrent.Future

/**
 * Created by vinhbachsy on 24/1/16.
 */
trait RedirectingURL {
  def getRedirectedURL(redirecting_url: String): Future[String]
}
