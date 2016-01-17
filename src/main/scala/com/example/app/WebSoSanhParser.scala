package com.example.app

import org.joda.money.{CurrencyUnit, Money}
import org.jsoup.nodes.{Element, Document}

/**
 * Created by vinhbachsy on 16/1/16.
 */
object WebSoSanhParser {
  def parse(doc: Document): List[Item] = {
    val itemsElems = doc.select(itemSelector)
    val elements = for(index <- 0 until itemsElems.size) yield itemsElems.get(index)
    pickTop10(List(), elements.toList)
  }

  private def pickTop10(items: List[Item], elements: List[Element]): List[Item] = {
    if (elements.isEmpty || items.size == 10) return items
    val item = extractItem(elements.head, propertyConfig)
    if (item.store.isEmpty ||
      items.indexWhere(isDuplicated(_, item)) > 0)
      return pickTop10(items, elements.tail)
    pickTop10(items :+ item, elements.tail)
  }

  private def isDuplicated(itemA: Item, itemB: Item): Boolean = {
    itemA.title == itemB.title &&
    itemA.store == itemB.store &&
    itemA.price == itemB.price
  }

  private def extractItem(item: Element, config: Map[String, Any]): Item = {
    Item(
      ItemPropertyHTMLExtractor.extract(item, config.getOrElse("title", Map())),
      ItemPropertyHTMLExtractor.extract(item, config.getOrElse("store", Map())),
      ItemPropertyHTMLExtractor.extract(item, config.getOrElse("link", Map())),
      ItemPropertyHTMLExtractor.extract(item, config.getOrElse("image", Map())),
      convertToMoney(ItemPropertyHTMLExtractor.extract(item, config.getOrElse("price", Map())))
    )
  }

  private def convertToMoney(priceText: String): Money = {
    val price = BigDecimal(priceText.replaceAll("[^0-9]", ""))
    Money.of(CurrencyUnit.getInstance("VND"), price.bigDecimal)
  }

  private val itemSelector = ".list-product .item2"
  private val propertyConfig = Map(
    "title" -> Map("selector" -> ".img-item2", "attr" -> "title"),
    "store" -> Map("selector" -> "cite.urlpro"),
    "image" -> Map("selector" -> ".img-item2 > img", "attr" -> "src"),
    "link" -> Map("selector" -> ".img-item2", "attr" -> "href"),
    "price" -> Map("selector" -> ".price > strong")
  )
}
