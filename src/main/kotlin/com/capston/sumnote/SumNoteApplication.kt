package com.capston.sumnote

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class SumNoteApplication

fun main(args: Array<String>) {
	runApplication<SumNoteApplication>(*args)
}
