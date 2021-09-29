package livroandroid.com.carros.domain.dao

import androidx.room.Room
import livroandroid.com.carros.CarrosApplication

object DatabaseManager {
    // Singleton do Room: banco de dados
    private var dbInstance: CarrosDatabase
    init {
        val appContext = CarrosApplication.getInstance().applicationContext
        // Configura o Room
        dbInstance = Room.databaseBuilder(
            appContext,
            CarrosDatabase::class.java,
            "carros.sqlite"
        ).build()
    }
    fun getCarroDAO(): CarroDAO {
        return dbInstance.carroDAO()
    }
}