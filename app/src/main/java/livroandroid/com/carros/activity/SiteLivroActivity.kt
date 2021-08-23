package livroandroid.com.carros.activity

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import livroandroid.com.carros.R
import livroandroid.com.carros.extensions.setupToobar

class SiteLivroActivity : BaseActivity() {

    private val URL_SOBRE = "https://www.google.com"
    var webview: WebView? = null
    var progress: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site_livro)
        // Toobar
        setupToobar(R.id.toolbar, upNavigation = true, title = "WebView")

        // Views
        webview = findViewById(R.id.webview)
        progress = findViewById(R.id.progress)

        // Carrega a página
        setWebVdiewClient(webview)
        webview?.loadUrl(URL_SOBRE)
    }

    // Controla os eventos do WebView
    private fun setWebVdiewClient(webView: WebView?) {
        webView?.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                // Liga o Progress
                progress?.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // Desliga o Progress
                progress?.visibility = View.INVISIBLE
            }
        }
    }
}