package com.example.app

import org.joda.money.{CurrencyUnit, Money}
import org.jsoup.Jsoup
import org.scalatest.{Matchers, FlatSpec}

import scala.io.Source

/**
 * Created by vinhbachsy on 16/1/16.
 */
class WebSoSanhParserSpec extends FlatSpec with Matchers {
  it should "returns list of first 10 items" in {
    val html = Source.fromURL(getClass.getResource("/websosanh.html")).getLines mkString "\n"
    val doc = Jsoup.parse(html)
    val items = WebSoSanhParser.parse(doc)
    val compared = List(
      Item("Combo - Sách Tôi Thấy Hoa Vàng Trên Cỏ Xanh",
        "fahasa.com",
        "http://websosanh.vn/combo-sach-toi-thay-hoa-vang-tren-co-xanh/2124938920855436158/7671621125936703880/direct.htm",
        "http://img.websosanh.net/ThumbImages/Store/f/fahasa_com/com/combo-sach-toi-thay-hoa-vang-tren-co-xanh_140.jpg",
        Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(44100).bigDecimal)),
      Item("Bộ 2 Quyển Tôi Thấy Hoa Vàng Trên Cỏ Xanh - Cho Tôi Xin Một Vé Đi Tuổi Thơ",
        "fahasa.com",
        "http://websosanh.vn/bo-2-quyen-toi-thay-hoa-vang-tren-co-xanh-cho-toi-xin-mot-ve-di-tuoi-tho/7418160152229121335/7671621125936703880/direct.htm",
        "http://img.websosanh.net/ThumbImages/Store/f/fahasa_com/bo2/bo-2-quyen-toi-thay-hoa-vang-tren-co-xanh-cho-toi-xin-mot-ve-di-tuoi-tho_140.jpg",
        Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(44100).bigDecimal)),
      Item("Tôi Thấy Hoa Vàng Trên Cỏ Xanh (Nguyễn Nhật Ánh)",
        "tiki.vn",
        "http://websosanh.vn/toi-thay-hoa-vang-tren-co-xanh-nguyen-nhat-anh/8518395472630227179/2642071820174507179/direct.htm",
        "http://websosanh.vn/images/NoImages/no.jpg",
        Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(57000).bigDecimal)),
      Item("Sổ tay Tôi Thấy Hoa Vàng Trên Cỏ Xanh",
        "adayroi.com",
        "http://websosanh.vn/so-tay-toi-thay-hoa-vang-tren-co-xanh/2077693626094060653/3433481253691794480/direct.htm",
        "http://img.websosanh.net/ThumbImages/Store/a/adayroi_com/sot/so-tay-toi-thay-hoa-vang-tren-co-xanh_140.jpg",
        Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(57000).bigDecimal)),
      Item("Tôi Thấy Hoa Vàng Trên Cỏ Xanh (Bìa Mềm)",
        "nhasachphuongnam.com",
        "http://websosanh.vn/toi-thay-hoa-vang-tren-co-xanh-bia-mem/7485539241611762816/6785040059346326293/direct.htm",
        "http://img.websosanh.net/ThumbImages/Store/n/nhasachphuongnam_com/toi/toi-thay-hoa-vang-tren-co-xanh-bia-mem_140.jpg",
        Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(61500).bigDecimal)),
      Item("Tôi Thấy Hoa Vàng Trên Cỏ Xanh Bìa Mềm - Sách Cho Tuổi Thần Tiên",
        "davibooks.vn",
        "http://websosanh.vn/toi-thay-hoa-vang-tren-co-xanh-bia-mem-sach-cho-tuoi-than-tien/1417710514569746830/6809047793547679249/direct.htm",
        "http://img.websosanh.net/ThumbImages/Store/d/davibooks_vn/toi/toi-thay-hoa-vang-tren-co-xanh-bia-mem-sach-cho-tuoi-than-tien_140.jpg",
        Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(61500).bigDecimal)),
      Item("tôi thấy hoa vàng trên cỏ xanh",
        "hpbook.vn",
        "http://websosanh.vn/toi-thay-hoa-vang-tren-co-xanh/4685479742526381615/1719827751393038034/direct.htm",
        "http://img.websosanh.net/ThumbImages/Store/h/hpbook_vn/toi/toi-thay-hoa-vang-tren-co-xanh_140.jpg",
        Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(65000).bigDecimal)),
      Item("Tôi thấy hoa vàng trên cỏ xanh",
        "vanlang.vn",
        "http://websosanh.vn/toi-thay-hoa-vang-tren-co-xanh/7450433600575386282/4903996019511206423/direct.htm",
        "http://img.websosanh.net/ThumbImages/Store/v/vanlang_vn/toi/toi-thay-hoa-vang-tren-co-xanh_140.jpg",
        Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(65600).bigDecimal)),
      Item("Tôi Thấy Hoa Vàng Trên Cỏ Xanh",
        "sachkhaitam.com",
        "http://websosanh.vn/toi-thay-hoa-vang-tren-co-xanh/4192914723132321049/7107720495502665164/direct.htm",
        "http://img.websosanh.net/ThumbImages/Store/s/sachkhaitam_com/toi/toi-thay-hoa-vang-tren-co-xanh_140.jpg",
        Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(65600).bigDecimal)),
      Item("Tôi Thấy Hoa Vàng Trên Cỏ Xanh",
        "anybooks.vn",
        "http://websosanh.vn/toi-thay-hoa-vang-tren-co-xanh/9101486952071280930/2611815848034305866/direct.htm",
        "http://img.websosanh.net/ThumbImages/Store/a/anybooks_vn/toi/toi-thay-hoa-vang-tren-co-xanh_140.jpg",
        Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(66000).bigDecimal)))
    assert(items == compared)
  }
}
