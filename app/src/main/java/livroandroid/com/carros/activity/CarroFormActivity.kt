package livroandroid.com.carros.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import livroandroid.com.carros.R
import livroandroid.com.carros.domain.Carro
import livroandroid.com.carros.extensions.setupToobar
import org.jetbrains.anko.doAsync
import kotlinx.android.synthetic.main.activity_carro_form_contents.*
import livroandroid.com.carros.domain.CarroService
import livroandroid.com.carros.domain.RefreshListEvent
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class CarroFormActivity : BaseActivity() {

    val carro: Carro? by lazy { intent.getSerializableExtra("carro") as Carro? }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carro_form)
        // Configura a toobar
        val title = carro?.nome ?: getString(R.string.novo_carro)
        setupToobar(R.id.toolbar, title, true)
        // Liga o upNvigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Menu com o botão salvar na toobar
        menuInflater.inflate(R.menu.menu_carro_form, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_salvar -> {
                taskSalvar()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("CheckResult")
    private fun taskSalvar() {

        Observable.fromCallable {
            // Salva o carro no servidor
            val c = createCarro()
            CarroService.save(c)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ response ->
                /** onNext **/
                // Dispara evento para atualizar a lista de carros
                EventBus.getDefault().post(RefreshListEvent())
                toast("Carro criado com o id: ${response.id}")
                finish()
            },{
                /** onError **/
                toast("Ocorreu um erro ao salvar um erro.")
            })

        // Cria a Thread para salvar um carro
//        doAsync {
//            val c = createCarro()
//            // Salva o carro
//            val response = CarroService.save(c)
//            uiThread {
//                // Dispara evento para atualizar a lista de carros
//                EventBus.getDefault().post(RefreshListEvent())
//                toast("Carro criado com o id: ${response.id}")
//                finish()
//            }
//        }
    }

    // Cria um carro com os valores do formulãrio
    fun createCarro(): Carro {
        val c = carro ?: Carro()
        c.tipo = getTipo()
        c.nome = tNome.text.toString()
        c.descricao = tDesc.text.toString()
        return c
    }

    // Converte o valor do Radius para string
    fun getTipo(): String {
        when (radioTipo.checkedRadioButtonId) {
            R.id.tipoEsportivo -> return "esportivos"
            R.id.tipoClassico -> return "classicos"
        }
        return "luxo"
    }
}