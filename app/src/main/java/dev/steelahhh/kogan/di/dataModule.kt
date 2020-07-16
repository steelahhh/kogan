package dev.steelahhh.kogan.di

import dev.steelahhh.kogan.data.KoganAPI
import dev.steelahhh.kogan.data.MoshiSerializer
import dev.steelahhh.kogan.data.ProductRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.logging.SIMPLE
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider
import org.kodein.di.singleton
import org.kodein.di.with

val dataModule = DI.Module("dataModule") {
    constant(BASE_URL) with "http://wp8m3he1wt.s3-website-ap-southeast-2.amazonaws.com"

    bind<HttpClient>() with singleton {
        HttpClient(engineFactory = Android) {
            install(feature = JsonFeature) {
                serializer = MoshiSerializer(instance())
            }
            install(feature = Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.BODY
            }
        }
    }

    bind<KoganAPI>() with singleton { KoganAPI(instance(), instance(BASE_URL)) }

    bind<ProductRepository>() with provider { ProductRepository(instance()) }
}

private const val BASE_URL = "BASE_URL"
