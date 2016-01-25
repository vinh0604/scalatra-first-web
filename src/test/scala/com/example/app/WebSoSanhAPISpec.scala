package com.example.app

import org.joda.money.{CurrencyUnit, Money}
import org.mockserver.integration.ClientAndServer
import org.mockserver.model.{HttpResponse, HttpRequest, Header}
import org.scalamock.scalatest.{MockFactory}
import org.scalatest.{BeforeAndAfterEach, Matchers, FlatSpec}
import scala.concurrent.Future
import scala.io.Source
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by vinhbachsy on 12/1/16.
 */
class WebSoSanhAPISpec extends FlatSpec with Matchers with BeforeAndAfterEach with MockFactory{
  private var mockWebSoSanhClient:ClientAndServer = null

  override def beforeEach = {
    mockWebSoSanhClient = ClientAndServer.startClientAndServer(8080)

    val html = Source.fromURL(getClass.getResource("/websosanh.html")).getLines mkString "\n"
    mockWebSoSanhClient.when(
      new HttpRequest()
        .withMethod("GET")
        .withPath("/s/T%C3%B4i+th%E1%BA%A5y+hoa+v%C3%A0ng+tr%C3%AAn+c%E1%BB%8F+xanh/sort-price-1.htm")).respond(
      new HttpResponse()
        .withHeader(
          new Header("Content-Type", "text/html; charset=utf-8")
        )
        .withStatusCode(200)
        .withBody(html))
  }

  override def afterEach = {
    mockWebSoSanhClient.stop
  }

  it should "request to websosanh with correct parameters" in {
    val mockRedirectingURL = mock[RedirectingURL]
    (mockRedirectingURL.getRedirectedURL _).expects(*).anyNumberOfTimes.returning(Future { "http://tiki.vn" })
    val keyword = "Tôi thấy hoa vàng trên cỏ xanh"
    val items = WebSoSanhAPI.request(keyword, mockRedirectingURL).right.get
    val compared = List(
      Item("Combo - Sách Tôi Thấy Hoa Vàng Trên Cỏ Xanh",
        "fahasa.com",
        "http://tiki.vn",
        "http://img.websosanh.net/ThumbImages/Store/f/fahasa_com/com/combo-sach-toi-thay-hoa-vang-tren-co-xanh_140.jpg",
        Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(44100).bigDecimal)),
      Item("Bộ 2 Quyển Tôi Thấy Hoa Vàng Trên Cỏ Xanh - Cho Tôi Xin Một Vé Đi Tuổi Thơ",
        "fahasa.com",
        "http://tiki.vn",
        "http://img.websosanh.net/ThumbImages/Store/f/fahasa_com/bo2/bo-2-quyen-toi-thay-hoa-vang-tren-co-xanh-cho-toi-xin-mot-ve-di-tuoi-tho_140.jpg",
        Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(44100).bigDecimal)),
      Item("Tôi Thấy Hoa Vàng Trên Cỏ Xanh (Nguyễn Nhật Ánh)",
        "tiki.vn",
        "http://tiki.vn",
        "http://websosanh.vn/images/NoImages/no.jpg",
        Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(57000).bigDecimal)),
      Item("Sổ tay Tôi Thấy Hoa Vàng Trên Cỏ Xanh",
        "adayroi.com",
        "http://tiki.vn",
        "http://img.websosanh.net/ThumbImages/Store/a/adayroi_com/sot/so-tay-toi-thay-hoa-vang-tren-co-xanh_140.jpg",
        Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(57000).bigDecimal)),
      Item("Tôi Thấy Hoa Vàng Trên Cỏ Xanh (Bìa Mềm)",
        "nhasachphuongnam.com",
        "http://tiki.vn",
        "http://img.websosanh.net/ThumbImages/Store/n/nhasachphuongnam_com/toi/toi-thay-hoa-vang-tren-co-xanh-bia-mem_140.jpg",
        Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(61500).bigDecimal)),
      Item("Tôi Thấy Hoa Vàng Trên Cỏ Xanh Bìa Mềm - Sách Cho Tuổi Thần Tiên",
        "davibooks.vn",
        "http://tiki.vn",
        "http://img.websosanh.net/ThumbImages/Store/d/davibooks_vn/toi/toi-thay-hoa-vang-tren-co-xanh-bia-mem-sach-cho-tuoi-than-tien_140.jpg",
        Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(61500).bigDecimal)),
      Item("tôi thấy hoa vàng trên cỏ xanh",
        "hpbook.vn",
        "http://tiki.vn",
        "http://img.websosanh.net/ThumbImages/Store/h/hpbook_vn/toi/toi-thay-hoa-vang-tren-co-xanh_140.jpg",
        Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(65000).bigDecimal)),
      Item("Tôi thấy hoa vàng trên cỏ xanh",
        "vanlang.vn",
        "http://tiki.vn",
        "http://img.websosanh.net/ThumbImages/Store/v/vanlang_vn/toi/toi-thay-hoa-vang-tren-co-xanh_140.jpg",
        Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(65600).bigDecimal)),
      Item("Tôi Thấy Hoa Vàng Trên Cỏ Xanh",
        "sachkhaitam.com",
        "http://tiki.vn",
        "http://img.websosanh.net/ThumbImages/Store/s/sachkhaitam_com/toi/toi-thay-hoa-vang-tren-co-xanh_140.jpg",
        Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(65600).bigDecimal)),
      Item("Tôi Thấy Hoa Vàng Trên Cỏ Xanh",
        "anybooks.vn",
        "http://tiki.vn",
        "http://img.websosanh.net/ThumbImages/Store/a/anybooks_vn/toi/toi-thay-hoa-vang-tren-co-xanh_140.jpg",
        Money.of(CurrencyUnit.getInstance("VND"), BigDecimal(66000).bigDecimal)))
    assert(items == compared)
  }
}
