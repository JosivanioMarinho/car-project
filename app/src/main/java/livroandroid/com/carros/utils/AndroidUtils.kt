package livroandroid.com.carros.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import androidx.annotation.RequiresApi

object AndroidUtils {
    // Verifica se existe conexão com a internet
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun isNetworkAvaiable(context: Context?): Boolean {
        val connectivity = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networks = connectivity.allNetworks
        return networks
            .map { connectivity.getNetworkInfo(it) }
            .any { it?.state == NetworkInfo.State.CONNECTED }
    }
}