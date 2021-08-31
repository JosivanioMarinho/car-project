package livroandroid.com.carros.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_carros.*
import livroandroid.com.carros.R
import livroandroid.com.carros.adapter.CarroAdapter
import livroandroid.com.carros.domain.Carro
import livroandroid.com.carros.domain.CarroService
import livroandroid.com.carros.domain.TipoCarro
import livroandroid.com.carros.extensions.setupToobar
import livroandroid.com.carros.extensions.toast
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class CarrosActivity : BaseActivity() {

    private var tipo = TipoCarro.Classicos
    private var carros = listOf<Carro>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carros)

        // Configura a Toobar
        setupToobar(R.id.toolbar, upNavigation = true)

        // Le os tipos dos argumentoa
        this.tipo = intent.getSerializableExtra("tipo") as TipoCarro
        var titleToobar = context.getString(this.tipo.string)
        supportActionBar?.title = titleToobar

        // RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.setHasFixedSize(true)
    }

    override fun onResume() {
        super.onResume()
        taskCarros()
    }

    private fun taskCarros(){
        // Busca os carros
        this.carros = CarroService.getCarros(context, tipo)
        // Atualiza a lista
        recyclerView.adapter = CarroAdapter(carros) { onCliqueCarro(it) }
    }

    // Trata o evento de click do carro
    private fun onCliqueCarro(carro: Carro) {
        startActivity<CarroActivity>("carro" to carro)
    }
}