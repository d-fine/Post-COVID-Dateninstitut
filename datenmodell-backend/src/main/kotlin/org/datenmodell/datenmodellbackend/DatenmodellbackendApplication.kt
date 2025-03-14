package org.datenmodell.datenmodellbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DatenmodellbackendApplication

fun main(args: Array<String>) {
    runApplication<DatenmodellbackendApplication>(*args)
}
