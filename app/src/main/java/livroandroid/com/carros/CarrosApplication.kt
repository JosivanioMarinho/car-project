package livroandroid.com.carros

import android.app.Application
import android.util.Log
import java.lang.IllegalStateException

class CarrosApplication : Application() {

    private val TAG = "CarrosApplication"

    override fun onCreate() {
        super.onCreate()
        // Salva a instancia para termos acesso como Singleton
        appInstance = this
    }

    companion object {
        // Singleton da classe Application
        private var appInstance: CarrosApplication? = null
        fun getInstance(): CarrosApplication {
            if (appInstance == null) {
                throw IllegalStateException("Configure a classe de Application no AndroidManifest.xml")
            }
            return appInstance!!
        }
    }
    // Chama quanndo o Android finaliza o processo do aplicativo
    override fun onTerminate() {
        super.onTerminate()
        Log.d(TAG, "CarrosApplication,onTerminate()")
    }
}