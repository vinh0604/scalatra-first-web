package com.example.app

import com.rockymadden.stringmetric.similarity.JaroMetric

/**
 * Created by vinhbachsy on 7/1/16.
 */
object BookSelector {
  def pickBest(books: List[Book], compared_title: String): Book = {
    val mapped_books = books.map(book => {
      (title_metric(book.title, compared_title),
        book.price.getAmount,
        book)
    })

    books.sortBy(book => (title_metric(book.title, compared_title), book.price.getAmount)).head
  }

  private def title_metric(title: String, compared_title: String): Double = {
    val normalized_title = title.takeWhile(_ != '(').trim
    val result = JaroMetric.compare(normalized_title, compared_title)
    result match {
      case Some(value) => - value
      case None => 0
    }
  }
}
