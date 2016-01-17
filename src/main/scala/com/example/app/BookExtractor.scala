package com.example.app

import org.jsoup.nodes.{TextNode, Element, Document}

/**
  * Created by vinhbachsy on 1/1/16.
  */
object BookExtractor {
  def extract(doc: Document, config: Map[String, Any]): Option[List[Map[String, Any]]] = config.get("item") match {
    case Some(selector: String) =>
      val itemsElems = doc.select(selector)
      val items = for(index <- 0 until itemsElems.size) yield itemsElems.get(index)
      val results = items.toList.map(extractItem(_, config))
      Some(results)
    case _ => None
  }

  private def extractItem(item: Element, config: Map[String, Any]): Map[String, Any] = {
    Map(
      "title" -> ItemPropertyHTMLExtractor.extract(item, config.getOrElse("title", Map())),
      "author" -> ItemPropertyHTMLExtractor.extract(item, config.getOrElse("author", Map())),
      "image" -> ItemPropertyHTMLExtractor.extract(item, config.getOrElse("image", Map())),
      "link" -> ItemPropertyHTMLExtractor.extract(item, config.getOrElse("link", Map())),
      "price" -> ItemPropertyHTMLExtractor.extract(item, config.getOrElse("price", Map()))
    )
  }
}
