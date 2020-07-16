package dev.steelahhh.kogan.di

import com.squareup.moshi.Moshi
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

val appModule = DI.Module("appModule") {
    bind<Moshi>() with singleton { Moshi.Builder().build() }

    import(dataModule)
}
