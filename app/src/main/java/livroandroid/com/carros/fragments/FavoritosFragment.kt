package livroandroid.com.carros.fragments

import kotlinx.android.synthetic.main.fragment_carros.*
import livroandroid.com.carros.activity.CarroActivity
import livroandroid.com.carros.adapter.CarroAdapter
import livroandroid.com.carros.domain.Carro
import livroandroid.com.carros.domain.FavoritosService
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread

class FavoritosFragment : CarrosFragment() {
    override fun taskCarros() {
        doAsync {
            // Busca os carros
            carros = FavoritosService.getCarros()
            uiThread {
                recyclerView.adapter = CarroAdapter(carros) { onClickCarro(it) }
            }
        }
    }

    override fun onClickCarro(carro: Carro) {
        // Ao clicar no carro amos pra tela de detalhes
        activity?.startActivity<CarroActivity>("carro" to carro)
    }
}