package livroandroid.com.carros.activity

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import livroandroid.com.carros.R
import livroandroid.com.carros.activity.dialogs.AboutDialog
import livroandroid.com.carros.extensions.setupToobar
import kotlinx.android.synthetic.main.activity_site_livro.*

/** O site do livro está indisponível, por isso usei o link da página do google pra exemplificar **/
/** Estou usando o kotlin extensions, por isso nao preciso usar o findViewById() nem setar as variaveis **/

class SiteLivroActivity : BaseActivity() {

    private val URL_SOBRE = "https://www.google.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site_livro)
        // Toobar
        setupToobar(R.id.toolbar, upNavigation = true, title = "WebView")

        // Carrega a página
        setWebVdiewClient(webview)
        webview.loadUrl(URL_SOBRE)

        // Swipe to refresh
        swipeToRefresh.setOnRefreshListener {
            webview.reload()
        }

        // Cores da animação
        swipeToRefresh.setColorSchemeResources(
            R.color.refresh_progress_1,
            R.color.refresh_progress_2,
            R.color.refresh_progress_3
        )
    }

    // Controla os eventos do WebView
    private fun setWebVdiewClient(webView: WebView?) {
        webView?.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                // Liga o Progress
                progress.visibility = View.VISIBLE
            }
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // Desliga o Progress
                progress.visibility = View.INVISIBLE
                // Termina a animação do swipe to refresh
                swipeToRefresh.isRefreshing = false
            }

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val url = request?.url.toString()
                if (url.endsWith(".com")) {
                    // Mostra o dialog
                    AboutDialog.showAbout(supportFragmentManager)
                    // Retorna true para informar interceptamos o evento
                    return true
                }
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
    }
}