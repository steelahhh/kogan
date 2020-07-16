package dev.steelahhh.kogan

import android.app.Application
import dev.steelahhh.kogan.di.appModule
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.androidXModule

@Suppress("unused")
class KoganApp : Application(), DIAware {
    override val di: DI by lazy {
        DI {
            import(androidXModule(this@KoganApp))
            import(appModule)
        }
    }
}
