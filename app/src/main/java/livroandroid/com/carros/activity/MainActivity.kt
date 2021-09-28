package livroandroid.com.carros.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import livroandroid.com.carros.R
import livroandroid.com.carros.adapter.TabsAdapter
import livroandroid.com.carros.domain.TipoCarro
import livroandroid.com.carros.extensions.setupToobar
import livroandroid.com.carros.extensions.toast
import livroandroid.com.carros.utils.Prefs
import org.jetbrains.anko.colorAttr
import org.jetbrains.anko.startActivity

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Toobar
        setupToobar(R.id.toolbar)

        // Menu lateral
        setUpNavDrawer()

        // Tabs
        setupViewPagerTabs()

        // FAB
        fab.setOnClickListener {
            // Abre a tela de cadastro
            startActivity<CarroFormActivity>()
        }
    }

    private fun setupViewPagerTabs() {
        // Configura o ViewPager + Tabs
        // As Variáveis ViewPager e Tabs são criadas automaticamente pelas kotlinextenss
        viewPager.offscreenPageLimit = 2
        viewPager.adapter = TabsAdapter(context, supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
        // Cor branca nos textos (o fundo azul é definido no layout)
        val cor = ContextCompat.getColor(context, R.color.white)
        tabLayout.setTabTextColors(cor, cor)

        // salva e recupera a ultimma Tab acessada
        val tabIdx = Prefs.tabIdx
        viewPager.currentItem = tabIdx
        viewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageSelected(position: Int) {
                // Salva o índice da página/tab selecionada
                Prefs.tabIdx = position
            }
        })
    }

    // Configuração do menu lateral
    private fun setUpNavDrawer(){
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        val toobar = findViewById<Toolbar>(R.id.toolbar)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toobar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
    }

    // Trata os eventos do menu lateral
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_item_carros_todos -> {
                toast("Clicou em carros")
            }
            R.id.nav_item_carros_classicos -> {
                startActivity<CarrosActivity>("tipo" to TipoCarro.Classicos)
            }
            R.id.nav_item_carros_esportivos -> {
                startActivity<CarrosActivity>("tipo" to TipoCarro.Esportivos)
            }
            R.id.nav_item_carros_luxo -> {
                startActivity<CarrosActivity>("tipo" to TipoCarro.Luxo)
            }
            R.id.nav_item_site_livro -> {
                startActivity<SiteLivroActivity>()
            }
            R.id.nav_item_settings -> {
                toast("Clicou em configurações")
            }
        }
        // Fecha o menu após tratar o evento
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}