package com.example.app

import org.jsoup.nodes.{Element, Document}

import scala.collection.JavaConverters._
import scala.collection.immutable.IndexedSeq

/**
  * Created by vinhbachsy on 1/1/16.
  */
object BookExtractor {
  def extract(doc: Document, config: Map[String, String]): Option[List[Map[String, Any]]] = {
    val items = doc.select(config.getOrElse("item", ""))
    if (items.isEmpty) return None
    val results = for {
      index <- 0 until items.size()
      item <- items.get(index)
    } yield extractItem(item, config)

    Some(results)
  }

  private def extractItem(item: Element, config: Map[String, String]): Map[String, Any] = {
    item.select(config.getOrElse("title", ""))
    Map("title" -> "", "author" -> "123")
  }
}
