package livroandroid.com.carros.domain.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import livroandroid.com.carros.domain.Carro

// Define as classes que precisam ser persistidas e a versão do banco
@Database(entities = arrayOf(Carro::class), version = 1)
abstract class CarrosDatabase: RoomDatabase() {
    abstract fun carroDAO(): CarroDAO
}