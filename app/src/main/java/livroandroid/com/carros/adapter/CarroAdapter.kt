package livroandroid.com.carros.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import livroandroid.com.carros.R
import livroandroid.com.carros.domain.Carro
import livroandroid.com.carros.extensions.loadUrl
import kotlinx.android.synthetic.main.adapter_carro.view.*

/** Estou usando a Kotlin Extensions aqui tambem **/

// Define o construtor que recebe (carro, onClick)
class CarroAdapter(
    val carros: List<Carro>,
    val onClick: (Carro) -> Unit):
    RecyclerView.Adapter<CarroAdapter.CarrosViewHolder>() {

    // infla o layout do adapter e retorna o ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarrosViewHolder {
        // Infla a view do adapter
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_carro, parent, false)
        // Retorna o ViewHolder que contém todas as views
        return CarrosViewHolder(view)
    }

    // Faz o bind para atualizarv o valor das views com os dados dos carros
    override fun onBindViewHolder(holder: CarrosViewHolder, position: Int) {
        // Recupera o objeto Carro
        val carro = carros[position]

        // Declara a view para facilitar o acesso abaixo
        // A view contém as variáveis definidas no xml (lembrar o nome de cada id)
        val view = holder.itemView

        // Atualiza os dados do carro
        view.tNome.text = carro.nome

        // Faz o download da foto e mostra o progressBar
        view.img.loadUrl(carro.urlFoto, view.progress)

        // Adiciona o evento de click na linha
        view.setOnClickListener { onClick(carro) }
    }

    // Retorna a quantidade de carros na lista
    override fun getItemCount(): Int = this.carros.size

    // ViewHolder com aas viewa
    class CarrosViewHolder(view: View) : RecyclerView.ViewHolder(view){

        // Não é mais necessário declarar variáveis aqui :-)

        // Views do layuot
        /*var tNome: TextView = view.findViewById(R.id.tNome)
        var img: ImageView = view.findViewById(R.id.img)
        var progress: ProgressBar = view.findViewById(R.id.progress)
        var cardView: CardView = view.findViewById(R.id.card_view)*/
    }
}