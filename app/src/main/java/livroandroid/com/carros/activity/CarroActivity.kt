package livroandroid.com.carros.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import livroandroid.com.carros.R
import kotlinx.android.synthetic.main.activity_carro.*
import kotlinx.android.synthetic.main.include_activity_carro.*
import livroandroid.com.carros.domain.Carro
import livroandroid.com.carros.domain.CarroService
import livroandroid.com.carros.domain.RefreshListEvent
import livroandroid.com.carros.extensions.loadUrl
import livroandroid.com.carros.extensions.setupToobar
import livroandroid.com.carros.extensions.toast
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.*

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
        tDesc.text = carro.descricao

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
        when(item.itemId) {
            R.id.action_editar -> {
                // Abre a tela de editar passando o carro como parâmetro
                startActivity<CarroFormActivity>("carro" to carro)
                finish()
            }
            R.id.action_deletar -> {
                // Mostra o alerta de confirmaçao
                alert("Deseja excluir este carro?") {
                    title = "Excluir"
                    positiveButton(R.string.sim) { taskDeletar() }
                    negativeButton(R.string.nao) {}
                }.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Deleta um carro
    private fun taskDeletar(){
        doAsync {
            val response = CarroService.delete(carro)
            uiThread {
                // Dispara evento para atualizar a lista de carros
                EventBus.getDefault().post(RefreshListEvent())
                toast("Carro com id ${response.id} excluido!")
                finish()
            }
        }
    }
}