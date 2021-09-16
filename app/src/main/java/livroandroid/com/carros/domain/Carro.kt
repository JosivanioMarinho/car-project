package livroandroid.com.carros.domain

import java.io.Serializable

class Carro : Serializable {

    var id: Long? = null
    var tipo = ""
    var nome = ""
    var descricao = ""
    var urlFoto = ""
    var urlInfo = ""
    var urlVideo = ""
    var latitude = ""
    var longitude = ""

    override fun toString(): String {
        return "Carro(nome='$nome')"
    }
}