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
    val config = Map(
      "item" -> ".product-item",
      "title" -> "> a[title]",
      "author" -> ".author",
      "image" -> ".image > img[src]",
      "link" -> "> a[href]",
      "price" -> ".price-sale")
    val html = Source.fromURL(getClass.getResource("/tiki.html")).getLines mkString "\n"
    val doc = Jsoup.parse(html)
    val results = BookExtractor.extract(doc, config).getOrElse(Nil)
    assert(results.size == 4)
    assert(results.map(_.getOrElse("title", "")) ==
      List("Tôi Thấy Hoa Vàng Trên Cỏ Xanh (Nguyễn...",
        "Combo Tôi Thấy Hoa Vàng Trên Cỏ Xanh",
        "Tôi Thấy Hoa Vàng Trên Cỏ Xanh",
        "Tôi Thấy Hoa Vàng Trên Cỏ Xanh (Nguyễn Nhật Ánh)"))
  }
}
