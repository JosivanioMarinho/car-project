package livroandroid.com.carros.domain.retrofit

import livroandroid.com.carros.domain.Carro
import livroandroid.com.carros.domain.Response
import retrofit2.Call
import retrofit2.http.*

interface CarrosAPI {
    @GET("tipo/{tipo}")
    fun getCarros(@Path("tipo") tipo: String): Call<List<Carro>>

    @POST("./")
    fun save(@Body carro: Carro): Call<Response>

    @DELETE("{id}")
    fun delete(@Path("id") id: Long?): Call<Response>
}