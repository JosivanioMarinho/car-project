package livroandroid.com.carros.extensions

import android.app.ActionBar
import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

// findViewById + setOnClickListeber
fun AppCompatActivity.onClick(@IdRes  viewId: Int, onClick: (v: android.view.View? ) -> Unit ){
    val view = findViewById<View>(viewId)
    view.setOnClickListener {onClick}
}

// Mostra um Toast
fun Activity.toast(message: CharSequence, lenght: Int = Toast.LENGTH_SHORT ) =
    Toast.makeText(this, message, lenght).show()
fun Activity.toast(@StringRes message: Int, lenght: Int = Toast.LENGTH_SHORT ) =
    Toast.makeText(this, message, lenght).show()

// Configura a Toobar
fun AppCompatActivity.setupToobar(@IdRes id: Int, title: String? = null,
        upNavigation: Boolean = false): androidx.appcompat.app.ActionBar {
    val toobar = findViewById<Toolbar>(id)
    setSupportActionBar(toobar)
    if (title != null){
        supportActionBar?.title = title
    }
    supportActionBar?.setDisplayHomeAsUpEnabled(upNavigation)
    return supportActionBar!!
}

// Adiciona o fragment no layout
fun AppCompatActivity.addFragment(@IdRes layoutId: Int, fragment: Fragment){
    fragment.arguments = intent.extras
    val ft = supportFragmentManager.beginTransaction()
    ft.add(layoutId, fragment)
    ft.commit()
}