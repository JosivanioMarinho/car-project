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
import livroandroid.com.carros.extensions.onClick
import livroandroid.com.carros.extensions.setupToobar
import livroandroid.com.carros.extensions.toast
import livroandroid.com.carros.fragments.CarrosFragment
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

        // Adicona o Fragment no layout
        if(savedInstanceState == null) {
            // Cria uma instancia do fragment, e configura os argumentos
            val frag = CarrosFragment()
            // Dentre os argumentos passados esta o tipo do carro
            frag.arguments = intent.extras
            // Adiciona o fragment no layout de marcaçao
            supportFragmentManager.beginTransaction().add(R.id.container, frag).commit()
        }
    }
}