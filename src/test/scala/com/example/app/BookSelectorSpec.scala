package com.example.app

import java.util

import org.joda.money.{CurrencyUnit, Money}
import org.scalatest.{BeforeAndAfterEach, Matchers, FlatSpec}

import collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer

/**
 * Created by vinhbachsy on 7/1/16.
 */
class BookSelectorSpec extends FlatSpec with Matchers with BeforeAndAfterEach {
  override def beforeEach() {
    val countryCodes: java.util.List[String] = ArrayBuffer("vn")
//    CurrencyUnit.registerCurrency("VND", 704, 0, countryCodes)
  }

  it should "pick the most relevant book from the list" in {
    val books = List(
      Book("Tôi thấy hoa vàng trên cỏ xám", "Nguyễn Nhật Ánh", "", "", Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(10000).bigDecimal)),
      Book("Tôi thấy hoa vàng trên cỏ xanh", "Nguyễn Nhật Ánh", "", "", Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(10000).bigDecimal)),
      Book("Bồ câu không đưa thư", "Nguyễn Nhật Ánh", "", "", Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(10000).bigDecimal)),
      Book("Kính vạn hoa", "Nguyễn Nhật Ánh", "", "", Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(10000).bigDecimal))
    )

    val selectedBook = BookSelector.pickBest(books, "Tôi thấy hoa vàng trên cỏ xanh")
    assert(selectedBook == books(1))
  }

  it should "pick the cheaper relevant book from the list" in {
    val books = List(
      Book("Tôi thấy hoa vàng trên cỏ xanh", "Nguyễn Nhật Ánh", "", "", Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(10000).bigDecimal)),
      Book("Tôi thấy hoa vàng trên cỏ xanh", "Nguyễn Nhật Ánh", "", "", Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(8000).bigDecimal))
    )

    val selectedBook = BookSelector.pickBest(books, "Tôi thấy hoa vàng trên cỏ xanh")
    assert(selectedBook == books(1))
  }
}
