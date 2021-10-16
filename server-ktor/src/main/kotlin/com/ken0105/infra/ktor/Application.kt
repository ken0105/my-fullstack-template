package com.ken0105.infra.ktor

import com.ken0105.infra.db.DatabaseFactory
import com.ken0105.infra.ktor.module.KoinModuleBuilder
import com.ken0105.infra.ktor.module.corsManager
import com.ken0105.infra.ktor.routes.route
import com.ken0105.plugins.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.HttpHeaders.Server
import io.ktor.jackson.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.ktor.ext.Koin

@SuppressWarnings("unused") // Referenced in application.conf
fun Application.main() {
    install(ContentNegotiation){
        jackson()
    }

    install(DefaultHeaders){
        header(Server, "Custom")
    }
    corsManager()
    install(Koin){
        modules(KoinModuleBuilder.modules())
    }

    DatabaseFactory.init()

    routing {
        route()
    }
}

fun main(args: Array<String>) {
    embeddedServer(Netty, commandLineEnvironment(args)).start(wait = true)
}
