package livroandroid.com.carros.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_carros.*
import kotlinx.android.synthetic.main.include_progress.*
import livroandroid.com.carros.R
import livroandroid.com.carros.activity.CarroActivity
import livroandroid.com.carros.adapter.CarroAdapter
import livroandroid.com.carros.domain.Carro
import livroandroid.com.carros.domain.CarroService
import livroandroid.com.carros.domain.RefreshListEvent
import livroandroid.com.carros.domain.TipoCarro
import livroandroid.com.carros.utils.AndroidUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread

open class CarrosFragment : BaseFragment() {

    private var tipo: TipoCarro? = TipoCarro.Classicos
    protected var carros = listOf<Carro>()

    // Cria a View do Fragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_carros, container, false)
        // Lê os tipos dos argumentos
        this.tipo = arguments?.getSerializable("tipo") as? TipoCarro
        return view
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Carrega a lista de carros
        taskCarros()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Views
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.setHasFixedSize(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            tipo = arguments?.getSerializable("tipo") as TipoCarro
            Log.d("isArgumetNull", "Arguments : ${tipo?.name}")
        }
        // Regista no bus de eventos
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cancela o registro no bus de eventos
        EventBus.getDefault().unregister(this)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMassageEvent(event: RefreshListEvent){
        // Recebeu o evento
        taskCarros()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    open fun taskCarros() {
        // Se existir conexão, a busca dos carros é feita
        val internetOk = AndroidUtils.isNetworkAvaiable(context)
        if (internetOk) {
            // Liga  a animação do progress
            progress.visibility = View.VISIBLE

            CarroService.getCarros(tipo!!) // Busca os carros
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    /** onNext **/
                    // Atualiza a interface
                    // it é o parâmetro padrão das lambdas, sendo que neste caso é a lista dos carros
                    recyclerView.adapter = CarroAdapter(it) { onClickCarro(it) }
                    progress.visibility = View.INVISIBLE
                },{
                    /** onerror **/
                    Toast.makeText(context, "Ocorreu um erro!", Toast.LENGTH_SHORT).show()
                    progress.visibility = View.INVISIBLE
                })

//            doAsync {
//                // Busca os carros
//                carros = CarroService.getCarros(tipo)
//                uiThread {
//                    // Atualiza a lista
//                    recyclerView.adapter = CarroAdapter(carros) { onClickCarro(it) }
//                    // Esconde o ProgressBar
//                    progress.visibility = View.INVISIBLE
//                }
//            }
        } else {
            // Se não existe conexão, é mostrado uma mensagem de erro
            networkText.text = "Erro na conexão :("
        }

    }

    // Trata o evento de click no carro
    open fun onClickCarro(carro: Carro) {
        activity?.startActivity<CarroActivity>("carro" to carro)
    }
}