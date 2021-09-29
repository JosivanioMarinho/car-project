package livroandroid.com.carros.adapter

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import livroandroid.com.carros.domain.TipoCarro
import livroandroid.com.carros.fragments.CarrosFragment
import livroandroid.com.carros.fragments.FavoritosFragment

class TabsAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm){
    // Qtde de Tabs
    override fun getCount(): Int = 4

    // Retorna o tipo pela posição
    private fun getTipoCarro(position: Int) = when(position) {
        0 -> TipoCarro.Classicos
        1 -> TipoCarro.Esportivos
        2 -> TipoCarro.Luxo
        else -> TipoCarro.Favoritos
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val tipo = getTipoCarro(position)
        return context.getString(tipo.string)
    }

    // Fragment que vai mostrar a lista de carros
    override fun getItem(position: Int): Fragment {
        if (position == 3) {
            // Favoritos
            return FavoritosFragment()
        }
        val tipo = getTipoCarro(position)
        val f: Fragment = CarrosFragment()
        val arguments: Bundle = Bundle()
        arguments.putSerializable("tipo", tipo)
        f.arguments = arguments
        return f
    }
}