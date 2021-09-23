package livroandroid.com.carros.domain

import android.content.Context
import android.util.Log
import livroandroid.com.carros.R
import livroandroid.com.carros.domain.retrofit.CarrosAPI
import livroandroid.com.carros.extensions.fromJson
import livroandroid.com.carros.extensions.toJson
import livroandroid.com.carros.utils.HttpHelper
import okhttp3.Response
import org.json.JSONArray
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CarroService {

    private const val BASE_URL = "https://carros-springboot.herokuapp.com/api/v1/carros/"
    private var service: CarrosAPI

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(CarrosAPI::class.java)
    }

    // Busca os carros por tipo (clássicos, esportivos ou luxo)
    fun getCarros(tipo: TipoCarro): List<Carro> {
       val call = service.getCarros(tipo.name)
       val carros = call.execute().body()
       return carros ?: listOf()
    }

    // Salva um carro
    fun save(carro: Carro): livroandroid.com.carros.domain.Response {
        val call = service.save(carro)
        val response = call.execute().body()
        return response ?: livroandroid.com.carros.domain.Response.error()
    }

    // Deleta um carro
    fun delete(carro: Carro): livroandroid.com.carros.domain.Response{
        val call = service.delete(carro.id)
        val response = call.execute().body()
        return response ?: livroandroid.com.carros.domain.Response.error()
    }

    // Retorna o arquivo que temos que ler para o tipo informado
    private fun getArquivoRaw(tipo: TipoCarro) = when(tipo) {
        TipoCarro.Classicos -> R.raw.carros_classicos
        TipoCarro.Esportivos -> R.raw.carros_esportivos
        else -> R.raw.carros_luxo
    }
}