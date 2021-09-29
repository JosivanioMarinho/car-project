package livroandroid.com.carros.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "carro")
class Carro : Serializable {

    @PrimaryKey
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