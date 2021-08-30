package livroandroid.com.carros.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import livroandroid.com.carros.R
import kotlinx.android.synthetic.main.activity_carro.*
import livroandroid.com.carros.domain.Carro
import livroandroid.com.carros.extensions.loadUrl
import livroandroid.com.carros.extensions.setupToobar

class CarroActivity : AppCompatActivity() {

    var carro: Carro? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carro)

        // Configura Toobar
        setupToobar(R.id.toolbar, upNavigation = true)

        // Lê o objeto carro enviado por parâmetro
        carro = intent.getSerializableExtra("carro") as Carro

        // Atualiza o Título da tela com o nome do carro
        supportActionBar?.title = carro?.nome

        // Atualiza a descrição do carro
        tDesc.text = carro?.desc

        // Moatra a foto do carro (deito na extensão Picasso.kt)
        img.loadUrl(carro?.urlFoto)
    }
}