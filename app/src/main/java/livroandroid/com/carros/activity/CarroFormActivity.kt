package livroandroid.com.carros.activity

import android.os.Bundle
import livroandroid.com.carros.R
import livroandroid.com.carros.domain.Carro
import livroandroid.com.carros.extensions.setupToobar

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
}