package livroandroid.com.carros.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import livroandroid.com.carros.R
import livroandroid.com.carros.domain.TipoCarro
import livroandroid.com.carros.extensions.setupToobar

class CarrosActivity : BaseActivity() {

    private var tipo: TipoCarro = TipoCarro.Classicos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carros)

        // Configura a Toobar
        setupToobar(R.id.toolbar)

        // Liga o UpNavigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Le os tipos dos argumentoa
        this.tipo = intent.getSerializableExtra("tipo") as TipoCarro
        val s = context.getString(tipo.string)

        // Mostra o tipo do carro no titulo da toobar
        supportActionBar?.title = s

        // Mostra o tipo do carro na tela
        val text = findViewById<TextView>(R.id.text)
        text.text = "Carros $s"
    }
}