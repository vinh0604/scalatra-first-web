package com.example.app

import org.joda.money.{CurrencyUnit, Money}
import org.mockserver.client.server.MockServerClient
import org.mockserver.integration.ClientAndServer
import org.mockserver.matchers.Times
import org.mockserver.model.{HttpResponse, Parameter, HttpRequest}
import org.scalatest.{BeforeAndAfterEach, Matchers, FlatSpec}

import scala.io.Source

/**
 * Created by vinhbachsy on 12/1/16.
 */
class WebSoSanhAPISpec extends FlatSpec with Matchers with BeforeAndAfterEach {
  private var mockWebSoSanhClient:ClientAndServer = null

  override def beforeEach = {
    mockWebSoSanhClient = ClientAndServer.startClientAndServer(8080)

    val html = Source.fromURL(getClass.getResource("/websosanh.html")).getLines mkString "\n"
    mockWebSoSanhClient.when(
      new HttpRequest()
        .withMethod("GET")
        .withPath("/s/t%C3%B4i+th%E1%BA%A5y+hoa+v%C3%A0ng+tr%C3%AAn+c%E1%BB%8F+xanh/sort-price-1.htm")).respond(
      new HttpResponse()
        .withStatusCode(200)
        .withBody(html))
  }

  override def afterEach = {
    mockWebSoSanhClient.stop
  }

  it should "request to websosanh with correct parameters" in {
    val keyword = "Tôi thấy hoa vàng trên cỏ xanh"
    val items = WebSoSanhAPI.request(keyword).right.get
    val compared = List(
      Item("Combo - Sách Tôi Thấy Hoa Vàng Trên Cỏ Xanh",
        "fahasa.com",
        "http://www.fahasa.com/combo-sach-toi-thay-hoa-vang-tren-co-xanh.html",
        "http://img.websosanh.net/ThumbImages/Store/f/fahasa_com/com/combo-sach-toi-thay-hoa-vang-tren-co-xanh_140.jpg",
        Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(44100).bigDecimal)),
      Item("Bộ 2 Quyển Tôi Thấy Hoa Vàng Trên Cỏ Xanh - Cho Tôi Xin Một Vé Đi Tuổi Thơ",
        "fahasa.com",
        "http://www.fahasa.com/bo-2-quyen-toi-thay-hoa-vang-tren-co-xanh-cho-toi-xin-mot-ve-di-tuoi-tho.html",
        "http://img.websosanh.net/ThumbImages/Store/f/fahasa_com/bo2/bo-2-quyen-toi-thay-hoa-vang-tren-co-xanh-cho-toi-xin-mot-ve-di-tuoi-tho_140.jpg",
        Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(44100).bigDecimal)),
      Item("Tôi Thấy Hoa Vàng Trên Cỏ Xanh (Nguyễn Nhật Ánh)",
        "tiki.vn",
        "http://tiki.vn/toi-thay-hoa-vang-tren-co-xanh.html",
        "http://websosanh.vn/images/NoImages/no.jpg",
        Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(57000).bigDecimal)),
      Item("Sổ tay Tôi Thấy Hoa Vàng Trên Cỏ Xanh",
        "adayroi.com",
        "https://www.adayroi.com/so-tay-toi-thay-hoa-vang-tren-co-xanh-p-Lpqw4-f1-2?pi=VeMKK",
        "http://img.websosanh.net/ThumbImages/Store/a/adayroi_com/sot/so-tay-toi-thay-hoa-vang-tren-co-xanh_140.jpg",
        Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(57000).bigDecimal)),
      Item("Tôi Thấy Hoa Vàng Trên Cỏ Xanh (Bìa Mềm)",
        "nhasachphuongnam.com",
        "http://nhasachphuongnam.com/sach/tuoi-moi-lon/van-hoc-tuoi-moi-lon/toi-thay-hoa-vang-tren-co-xanh-bia-mem.html",
        "http://img.websosanh.net/ThumbImages/Store/n/nhasachphuongnam_com/toi/toi-thay-hoa-vang-tren-co-xanh-bia-mem_140.jpg",
        Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(61500).bigDecimal)),
      Item("Tôi Thấy Hoa Vàng Trên Cỏ Xanh Bìa Mềm - Sách Cho Tuổi Thần Tiên",
        "davidbooks.vn",
        "http://davibooks.vn/products/view/25397.toi-thay-hoa-vang-tren-co-xanh-bia-mem---nguyen-nhat-anh.html",
        "http://img.websosanh.net/ThumbImages/Store/d/davibooks_vn/toi/toi-thay-hoa-vang-tren-co-xanh-bia-mem-sach-cho-tuoi-than-tien_140.jpg",
        Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(61500).bigDecimal)),
      Item("tôi thấy hoa vàng trên cỏ xanh",
        "hpbook.vn",
        "http://hpbook.vn/toi-thay-hoa-vang-tren-co-xanh",
        "http://img.websosanh.net/ThumbImages/Store/h/hpbook_vn/toi/toi-thay-hoa-vang-tren-co-xanh_140.jpg",
        Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(65000).bigDecimal)),
      Item("Tôi thấy hoa vàng trên cỏ xanh",
        "vanlang.vn",
        "http://vanlang.vn/san-pham/toi-thay-hoa-vang-tren-co-xanh.html",
        "http://img.websosanh.net/ThumbImages/Store/v/vanlang_vn/toi/toi-thay-hoa-vang-tren-co-xanh_140.jpg",
        Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(65600).bigDecimal)),
      Item("Tôi thấy hoa vàng trên cỏ xanh",
        "sachkhaitam.com",
        "http://www.sachkhaitam.com/tu-sach/nguyen-nhat-anh/toi-thay-hoa-vang-tren-co-xanh",
        "http://img.websosanh.net/ThumbImages/Store/s/sachkhaitam_com/toi/toi-thay-hoa-vang-tren-co-xanh_140.jpg",
        Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(65600).bigDecimal)),
      Item("Tôi thấy hoa vàng trên cỏ xanh",
        "anybooks.vn",
        "http://anybooks.vn/book/Tieu_thuyet_viet_nam/Toi_Thay_Hoa_Vang_Tren_Co_Xanh.html",
        "http://img.websosanh.net/ThumbImages/Store/a/anybooks_vn/toi/toi-thay-hoa-vang-tren-co-xanh_140.jpg",
        Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(66000).bigDecimal)))
    assert(items == compared)
  }
}
