package com.example.app

import org.joda.money.{CurrencyUnit, Money}
import org.json4s._

/**
 * Created by vinhbachsy on 24/2/16.
 */
class JodaMoneySerializer extends CustomSerializer[Money](format => (
  {
    case JObject(JField("value", JDecimal(value)) :: JField("currency", JString(currency)) :: Nil) =>
      Money.of(CurrencyUnit.getInstance(currency), value.bigDecimal)
  },
  {
    case money: Money =>
      JObject(
          JField("value", JDecimal(BigDecimal(money.getAmount))) ::
          JField("currency", JString(money.getCurrencyUnit.getCurrencyCode)) ::
          JField("symbol", JString(CurrencySymbol.symbol(money.getCurrencyUnit.getCurrencyCode))) :: Nil)
  }
  ))
