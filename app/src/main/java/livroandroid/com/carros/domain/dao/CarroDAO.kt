package livroandroid.com.carros.domain.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import livroandroid.com.carros.domain.Carro

@Dao
interface CarroDAO {
    @Query("SELECT * FROM carro WHERE id = :id")
    fun getById(id: Long?): Carro?
    @Query("SELECT * FROM carro")
    fun findAll(): List<Carro>
    @Insert
    fun insert(carro: Carro)
    @Delete
    fun delete(carro: Carro)
}