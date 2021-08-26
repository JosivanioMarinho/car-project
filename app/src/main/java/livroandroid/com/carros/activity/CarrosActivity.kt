package livroandroid.com.carros.activity

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

class CarrosActivity : BaseActivity() {

    private var tipo = TipoCarro.Classicos
    private var carros = listOf<Carro>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carros)

        // Configura a Toobar
        setupToobar(R.id.toolbar, title = "Lista de carros")

        // Liga o UpNavigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Le os tipos dos argumentoa
        this.tipo = intent.getSerializableExtra("tipo") as TipoCarro

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
        recyclerView.adapter = CarroAdapter( carros,
            { carro: Carro ->
                toast("@Clicou no carro ${carro.nome}")
            }
        )
    }
}