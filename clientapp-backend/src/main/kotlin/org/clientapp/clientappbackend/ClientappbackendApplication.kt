package org.clientapp.clientappbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication class ClientappbackendApplication

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
  runApplication<ClientappbackendApplication>(*args)
}
