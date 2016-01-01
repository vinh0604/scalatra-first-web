package com.example.app

import org.scalatra._
import scalate.ScalateSupport

class MyScalatraServlet extends ScalatraFirstWebStack with ScalateSupport {

  get("/") {
    contentType="text/html"
    jade("/hello-scalate", "name" -> "Vinh")
  }

}
