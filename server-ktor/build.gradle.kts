import org.jooq.meta.jaxb.ForcedType
import org.jooq.meta.jaxb.Logging
import org.jooq.meta.jaxb.Property

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val jackson_version: String by project
val koin_version: String by project
val hikaricp_version: String by project
val postgres_driver_version: String by project
val flyway_version: String by project
val kotest_version: String by project
val jooq_version: String by project

plugins {
    application
    kotlin("jvm") version "1.5.31"
    id("nu.studer.jooq") version "6.0.1"
}

group = "com.ken0105"
version = "0.0.1"
application {
    mainClass.set("com.ken0105.infra.ktor.ApplicationKt")
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

sourceSets {
    main {
        java{
            srcDir("build/generated-src/jooq/main")
        }
    }
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-jackson:$ktor_version")
    implementation("io.insert-koin:koin-ktor:$koin_version")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.12.5")
    implementation("com.github.papsign:Ktor-OpenAPI-Generator:0.2-beta.20")
    implementation("com.zaxxer:HikariCP:$hikaricp_version")
    implementation("org.postgresql:postgresql:$postgres_driver_version")
    implementation("org.flywaydb:flyway-core:$flyway_version")
    implementation("org.jooq:jooq:$jooq_version")
    jooqGenerator("org.postgresql:postgresql:$postgres_driver_version")
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlin_version")
    testImplementation("io.kotest:kotest-assertions-core-jvm:$kotest_version")
    testImplementation("io.kotest:kotest-framework-engine:$kotest_version")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotest_version")
    testImplementation("io.kotest:kotest-runner-console-jvm:4.1.3.2")
}

jooq {
    version.set(jooq_version)
    edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)
    configurations {
        create("main") {  // name of the jOOQ configuration
            generateSchemaSourceOnCompilation.set(true)  // default (can be omitted)

            jooqConfiguration.apply {
                logging = Logging.WARN
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = "jdbc:postgresql://localhost:5532/postgres"
                    user = "postgres"
                    password = "password"
//                    properties.add(Property().withKey("ssl").withValue("true"))
                }
                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = "public"
                        forcedTypes.addAll(
                            arrayOf(
                                ForcedType()
                                    .withName("varchar")
                                    .withIncludeExpression(".*")
                                    .withIncludeTypes("JSONB?"),
                                ForcedType()
                                    .withName("varchar")
                                    .withIncludeExpression(".*")
                                    .withIncludeTypes("INET")
                            ).toList()
                        )
                    }
                    generate.apply {
                        isDeprecated = false
                        isRecords = true
                        isImmutablePojos = true
                        isFluentSetters = true
                    }
                    target.apply {
                        packageName = "com.ken0105"
                        directory = "build/generated-src/jooq/main"
                    }
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                }
            }
        }
    }
}