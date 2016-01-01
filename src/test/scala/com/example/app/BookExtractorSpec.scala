package com.example.app

import org.jsoup.Jsoup
import org.scalatest.{Matchers, FlatSpec}
import org.yaml.snakeyaml.Yaml

import scala.io.Source

/**
  * Created by vinhbachsy on 1/1/16.
  */
class BookExtractorSpec extends FlatSpec with Matchers {
  it should "return list of items if selector exists" in {
    val config = Map("item" -> "", "title" -> "", "author" -> "", "image" -> "", "link" -> "", "price" -> "")
    val html = Source.fromURL(getClass.getResource("/tiki.html")).getLines mkString "\n"
    val doc = Jsoup.parse(html)
    assert(BookExtractor.extract(doc, config) == Some(Nil))
  }
}
