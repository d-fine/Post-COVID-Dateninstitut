package org.clientapp.clientappbackend.service.bloomfilter

fun getNGrams(value: String, length: Int): List<String> {
  val sb = StringBuilder()
  val headAndTail = " ".repeat(length - 1)
  sb.append(headAndTail).append(value).append(headAndTail)
  val result = (0 until value.length + 1).map { sb.substring(it, it + length) }
  return result
}
