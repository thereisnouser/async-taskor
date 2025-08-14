package com.thereisnouser.asynctaskor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AsyncTaskorApplication

fun main(args: Array<String>) {
    runApplication<AsyncTaskorApplication>(*args)
}
