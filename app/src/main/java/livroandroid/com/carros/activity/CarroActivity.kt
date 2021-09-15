package livroandroid.com.carros.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import livroandroid.com.carros.R
import kotlinx.android.synthetic.main.activity_carro.*
import kotlinx.android.synthetic.main.include_activity_carro.*
import livroandroid.com.carros.domain.Carro
import livroandroid.com.carros.extensions.loadUrl
import livroandroid.com.carros.extensions.setupToobar
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class CarroActivity : BaseActivity() {

    val carro by lazy { intent.getSerializableExtra("carro") as Carro }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carro)

        // Configura Toobar
        setupToobar(R.id.scroll_toobar, carro.nome,true)

        // Atualiza o Título da tela com o nome do carro
        supportActionBar?.title = carro.nome

        // Atualiza a descrição do carro
        tDesc.text = carro.desc

        // Moatra a foto do carro (deito na extensão Picasso.kt)
        appBarImg.loadUrl(carro.urlFoto)
    }

    // Infla o menu na toobar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_carro, menu)
        return true
    }
    // Trata is eventos de clique do menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            R.id.action_editar -> {
                // Abre a tela de editar passando o carro como parâmetro
                startActivity<CarroFormActivity>("carro" to carro)
                finish()
            }
            R.id.action_deletar -> {
                toast("Carro deletado")
            }
        }
        return super.onOptionsItemSelected(item)
    }
}