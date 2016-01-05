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
      "title" -> Map("selector" -> "> a", "attr" -> "title"),
      "author" -> Map("selector" -> ".author"),
      "image" -> Map("selector" -> ".image > img", "attr" -> "src"),
      "link" -> Map("selector" -> "> a", "attr" -> "href"),
      "price" -> List(
        Map("selector" -> ".price-sale"),
        Map("selector" -> ".price-regular")
      ))
    val html = Source.fromURL(getClass.getResource("/tiki.html")).getLines mkString "\n"
    val doc = Jsoup.parse(html)
    val results = BookExtractor.extract(doc, config).getOrElse(Nil)
    assert(results.size == 4)
    assert(results.map(_.getOrElse("title", "")) ==
      List("Tôi Thấy Hoa Vàng Trên Cỏ Xanh (Nguyễn Nhật Ánh)",
        "Combo Tôi Thấy Hoa Vàng Trên Cỏ Xanh",
        "Tôi Thấy Hoa Vàng Trên Cỏ Xanh",
        "Tôi Thấy Hoa Vàng Trên Cỏ Xanh (Nguyễn Nhật Ánh)"))
    assert(results.map(_.getOrElse("author", "")) ==
      List("Nguyễn Nhật Ánh",
        "Nguyễn Nhật Ánh",
        "Nguyễn Nhật Ánh",
        "Nguyễn Nhật Ánh"))
    assert(results.map(_.getOrElse("image", "")) ==
      List("https://tala.tikicdn.com/cache/250x250/media/catalog/product/t/o/toi_thay_hoa_vang.jpg",
        "https://tala.tikicdn.com/cache/250x250/media/catalog/product/t/o/toi-thay-hoa-vang-tren-co-xanh-combo.jpg",
        "https://tala.tikicdn.com/cache/250x250/media/catalog/product/t/o/toi_thay_hoa_vang_1.jpg",
        "https://tala.tikicdn.com/cache/250x250/media/catalog/product/t/o/toi_thay_hoa_vang_2.jpg"))
    assert(results.map(_.getOrElse("link", "")) ==
      List("http://tiki.vn/toi-thay-hoa-vang-tren-co-xanh.html?src=tôi thấy hoa vàng trên cỏ xanh&ref=c316.c393.c714.c3130.c3259.c4654.c531.c835.c1754.c2274.c2766.c3133.c3327.c3352.c3353.c3355.c3378.c3400.c3452.c4125.c4291.c4661.c4758.c2288.c2497.c2521.c2793.c2817.c3141.c3311.c3328.c3384.c3535.c4725.c3503.",
        "http://tiki.vn/combo-toi-thay-hoa-vang-tren-co-xanh-p158957.html?src=tôi thấy hoa vàng trên cỏ xanh&ref=c316.c714.c3550.c531.",
        "http://tiki.vn/toi-thay-hoa-vang-tren-co-xanh-bia-cung.html?src=tôi thấy hoa vàng trên cỏ xanh&ref=c316.c393.c3130.c3259.c1754.c2766.c3133.c3327.c3378.c3400.c3452.c4291.c2288.c2501.c2797.c3141.c3310.c3311.c3328.c3384.c3535.",
        "http://tiki.vn/toi-thay-hoa-vang-tren-co-xanh-nguyen-nhat-anh-p69704.html?src=tôi thấy hoa vàng trên cỏ xanh&ref=c316.c3130.c3259.c2766.c3133.c3327.c3452.c4291.c2288.c2497.c2793.c3141.c3311.c3328.c3535."))
    assert(results.map(_.getOrElse("price", "")) ==
      List("62.000 ₫",
        "",
        "66.000 ₫",
        "110.000 ₫"))
  }
}
