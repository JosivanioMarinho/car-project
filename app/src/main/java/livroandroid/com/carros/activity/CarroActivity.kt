package livroandroid.com.carros.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Schedulers.io
import livroandroid.com.carros.R
import kotlinx.android.synthetic.main.activity_carro.*
import kotlinx.android.synthetic.main.include_activity_carro.*
import livroandroid.com.carros.domain.Carro
import livroandroid.com.carros.domain.CarroService
import livroandroid.com.carros.domain.FavoritosService
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

        fab.setOnClickListener { onClickFavoritar(carro) }
    }

    // Adiciona ou remove o carro dos favoritos
    private fun onClickFavoritar(carro: Carro) {
        taskFavoritar(carro)
    }

    private fun taskFavoritar(carro: Carro) {
        doAsync {
            val favoritado = FavoritosService.favoritar(carro)
            uiThread {
                // Dispara o evento para atualizar a lista de carros
                EventBus.getDefault().post(RefreshListEvent())
                // Alerta de sucesso
                toast(if (favoritado) R.string.msg_carro_favoritado
                    else R.string.msg_carro_desfavoritado)
            }
        }
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
        Observable.fromCallable { CarroService.delete(carro) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({response ->
                /** onNext **/
                // Dispara evento para atualizar a lista de carros
                EventBus.getDefault().post(RefreshListEvent())
                toast("Carro com id ${response.id} excluido!")
                finish()
            },{
                /** onerror **/
                toast("Ocorreu um erro ao deletar um carro.")
            })
    }
}