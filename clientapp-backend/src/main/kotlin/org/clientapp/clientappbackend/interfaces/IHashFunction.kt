package org.clientapp.clientappbackend.interfaces

fun interface IHashFunction {
  fun hash(value: String): ByteArray
}
