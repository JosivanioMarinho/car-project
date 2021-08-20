package livroandroid.com.carros.fragments

import android.content.Context
import androidx.fragment.app.Fragment

class BaseFragment : Fragment() {
    // Métodos comuns para todos os fragments aqui
    override fun getContext(): Context? {
        return super.getContext()
    }
}