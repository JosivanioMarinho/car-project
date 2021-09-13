package livroandroid.com.carros.domain

import android.content.Context
import livroandroid.com.carros.R
import livroandroid.com.carros.extensions.fromJson
import livroandroid.com.carros.utils.HttpHelper
import org.json.JSONArray

object CarroService {

    private const val BASE_URL = "https://carros-springboot.herokuapp.com/api/v1/carros"

    // Busca os carros por tipo (clássicos, esportivos ou luxo)
    fun getCarros(tipo: TipoCarro): List<Carro> {
       // Cria a url para o tipo informado
        val url = "$BASE_URL/tipo/${tipo.name}"
        // Faz a requisição GET  no  web service
        val json = HttpHelper.get(url)
        // Cria a lista de carros a partir do json
        val carros = fromJson<List<Carro>>(json)
        return carros
    }
    // Retorna o arquivo que temos que ler para o tipo informado
    private fun getArquivoRaw(tipo: TipoCarro) = when(tipo) {
        TipoCarro.Classicos -> R.raw.carros_classicos
        TipoCarro.Esportivos -> R.raw.carros_esportivos
        else -> R.raw.carros_luxo
    }
}