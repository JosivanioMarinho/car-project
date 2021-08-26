package livroandroid.com.carros.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import livroandroid.com.carros.R
import livroandroid.com.carros.domain.Carro

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
        val context = holder.itemView.context

        // Recupera o objeto Carro
        val carro = carros[position]

        // Atualiza os dados do carro
        holder.tNome.text = carro.nome
        holder.process.visibility = View.VISIBLE

        // Faz o download da foto e mostra o progressBar
        if (carro.urlFoto.trim().isEmpty()) {
            // Deixa a imagem vazia se não tiver foto
            holder.img.setImageBitmap(null)
        } else {
            // Faz o download da foto e mostra o ProgressBar
            Picasso.with(context).load(carro.urlFoto).fit().into(holder.img,
                object : com.squareup.picasso.Callback {
                    override fun onSuccess() {
                        // Download OK
                        holder.process.visibility = View.INVISIBLE
                    }
                    override fun onError() {
                        holder.process.visibility = View.GONE
                    }
                }
            )
        }
        // Adiciona o evento de click na linha
        holder.itemView.setOnClickListener { onClick(carro) }
    }

    // Retorna a quantidade de carros na lista
    override fun getItemCount(): Int = this.carros.size

    // ViewHolder com aas viewa
    class CarrosViewHolder(view: View) : RecyclerView.ViewHolder(view){

        // Views do layuot
        var tNome: TextView = view.findViewById(R.id.tNome)
        var img: ImageView = view.findViewById(R.id.img)
        var process: ProgressBar = view.findViewById(R.id.progress)
        var cardView: CardView = view.findViewById(R.id.card_view)
    }
}