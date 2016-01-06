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
      "title" -> extractAttr(item, config.getOrElse("title", Map())),
      "author" -> extractAttr(item, config.getOrElse("author", Map())),
      "image" -> extractAttr(item, config.getOrElse("image", Map())),
      "link" -> extractAttr(item, config.getOrElse("link", Map())),
      "price" -> extractAttr(item, config.getOrElse("price", Map()))
    )
  }

  private def extractAttr(item: Element, config: Any): String = config match {
    case SingleSelector(selector) =>
      item.select(selector).first match {
        case null => ""
        case elem => elem.text.trim
      }
    case SingleSelectorWithAttr(selector, attr) =>
      item.select(selector).first match {
        case null => ""
        case elem => extractAttrValue(elem, attr)
      }
    case MultipleSelector(configs) =>
      configs.map(extractAttr(item, _)).dropWhile(_.isEmpty).headOption.getOrElse("")
    case _ => ""
  }

  private def extractAttrValue(elem: Element, attr: String): String = attr match {
    case "textNode" => extractTextNodeValue(elem)
    case `attr` => elem.attr(attr).trim
  }

  private def extractTextNodeValue(elem: Element): String = elem.childNode(0) match {
    case node: TextNode => node.text.trim
    case _ => ""
  }

  private object SingleSelector {
    def unapply(config: Map[String, String]): Option[(String)] = {
      val selector = config.getOrElse("selector", "")
      if (config.isDefinedAt("attr")) return None
      Some(selector)
    }
  }

  private object SingleSelectorWithAttr {
    def unapply(config: Map[String, String]): Option[(String, String)] = {
      val selector = config.getOrElse("selector", "")
      if (config.isDefinedAt("attr")) return Some((selector, config.getOrElse("attr", "")))
      None
    }
  }

  private object MultipleSelector {
    def unapply(config: List[Map[String, String]]): Option[List[Map[String, String]]] = {
      Some(config)
    }
  }
}
