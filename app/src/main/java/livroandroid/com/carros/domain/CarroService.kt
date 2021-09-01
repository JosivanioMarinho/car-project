package livroandroid.com.carros.domain

import android.content.Context

object CarroService {
        // Busca os carros por tipo (clássicos, esportivos ou luxo)
        fun getCarros(context: Context?, tipo: TipoCarro): List<Carro> {
            val tipoString = context?.getString(tipo.string)

            // Cria um array vazio de carros
            val carros = mutableListOf<Carro>()

            // Cria 20 carros
            for (i in 1..20) {
                val c = Carro()
                // Nome do carro dinâmico
                c.nome = "Carro $tipoString: $i"
                c.desc = "Desc $i"
                // Url da foto fixa por enquanto
                c.urlFoto = "http://s3-sa-east-1.amazonaws.com/videos.livetouchdev.com.br/luxo/Lamborghini_Reventon.png"
                carros.add(c)
            }
            return carros
        }
}