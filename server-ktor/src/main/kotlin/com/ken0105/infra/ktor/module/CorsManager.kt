package com.ken0105.infra.ktor.module

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*

fun Application.corsManager() {
    install(CORS){
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Patch)
        method(HttpMethod.Delete)
        header(HttpHeaders.ContentType)
        host("localhost:3000")
    }
}