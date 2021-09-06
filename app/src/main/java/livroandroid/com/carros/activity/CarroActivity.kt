package livroandroid.com.carros.activity

import android.os.Bundle
import livroandroid.com.carros.R
import kotlinx.android.synthetic.main.activity_carro.*
import kotlinx.android.synthetic.main.include_activity_carro.*
import livroandroid.com.carros.domain.Carro
import livroandroid.com.carros.extensions.loadUrl
import livroandroid.com.carros.extensions.setupToobar

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
}