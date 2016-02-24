package com.example.app

import org.joda.money.CurrencyUnit
import scala.collection.JavaConverters._
/**
 * Created by vinhbachsy on 24/2/16.
 */
object CurrencySymbol {
  def symbol(currencyCode: String) = currencySymbols.get(currencyCode) match {
    case Some(symbol) => symbol
    case None => CurrencyUnit.getInstance(currencyCode).getSymbol
  }

  protected val currencySymbols = Config().getAnyRef("currencySymbols").asInstanceOf[java.util.HashMap[String, String]].asScala
}
