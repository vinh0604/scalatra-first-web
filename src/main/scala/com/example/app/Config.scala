package com.example.app

import com.typesafe.config.ConfigFactory

/**
 * Created by vinhbachsy on 20/1/16.
 */
object Config {
  val env = if (System.getenv("SCALA_ENV") == null) "development" else System.getenv("SCALA_ENV")

  val conf = ConfigFactory.load()
  def apply() = conf.getConfig(env)
}
