package com.appverse.hitthecooks

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import java.lang.Exception

/**
 * Actividad abstracta superior de la que heredan el resto de actividades excepto el login.
 * Contiene el menú desplegable que comparten el resto de actividades, el archivo de preferencias
 * y las funciones del modo oscuro
 */
abstract class SuperActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    /** Layout del menú desplegable **/
    val drawerLayout : DrawerLayout by lazy { findViewById(R.id.drawer_layout) }
    /** Menú que contiene los diferentes items de navegación **/
    val navigationView : NavigationView by lazy { findViewById(R.id.navigation_view) }

    /** Gestiona las preferencias de la app **/
    lateinit var preferences : SharedPreferences

    /**
     * Inicializa la actividad
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_super)
        //Establece esta actividad como el listener del menú de navegación
        navigationView.setNavigationItemSelectedListener(this)
    }

    /**
     * Función que asigna un intent de navegación dependiendo del elemento del menú seleccionado
     * @return Boolean, true si se ha realizado correctamente
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_main -> {
                val intent : Intent = Intent(applicationContext, PantallaPrincipal::class.java)
                startActivity(intent)
            }
            R.id.nav_lists -> {
                val intent : Intent = Intent(applicationContext, ShoppingListActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_new_list-> {
                val intent : Intent = Intent(applicationContext, FoodList::class.java)
                startActivity(intent)
            }
            R.id.nav_configuration-> {
                val intent : Intent = Intent(applicationContext, ConfigurationActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_profile-> {
                val intent : Intent = Intent(applicationContext, EditProfile::class.java)
                startActivity(intent)
            }
            R.id.nav_about_us-> {
                val intent : Intent = Intent(applicationContext, AboutUs::class.java)
                startActivity(intent)
            }

        }

        drawerLayout.closeDrawer(GravityCompat.START)

        return true
    }

    /**
     * Función que aplica el modo oscuro en el caso de que el valor del mismo en el archivo de preferencias
     * sea verdadero. Cambia el color de fondo de la vista raiz del layout y pone los textos en color blanco
     */
    fun applyDarkMode(rootView : ViewGroup) : Unit {
        //Inicializa el archivo de preferencias
        preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        //Consulta el valor del modo oscuro
        val darkMode : Boolean = preferences.getBoolean("darkMode", false)
        if(darkMode){
            //Cambia el color de fondo y los textos del menú de navegación
            navigationView.setBackgroundColor(ContextCompat.getColor(this, R.color.darkGrey))
            navigationView.itemTextColor = ColorStateList.valueOf(Color.WHITE);
            //Cambia el color de fondo de la vista raiz
            rootView.setBackgroundColor(ContextCompat.getColor(this, R.color.darkBlue))
            if(rootView.context is ConfigurationActivity){
                //Cambia el color del titulo y el icono del modo dia/noche si es la actividad de configuración
                val text : TextView = findViewById(R.id.labelConfiguration)
                text.setTextColor(ContextCompat.getColor(this, R.color.whiteEgg))
                val switchText : TextView = findViewById(R.id.labelTheme)
                switchText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sun_icon, 0, 0, 0)
            } else if(rootView.context is PantallaPrincipal){
                //Cambia el logo de la app si es la actividad principal
                val logo : ImageView = findViewById(R.id.appLogo)
                logo.setImageResource(R.drawable.logo_hit_the_cook_blanco)
            }
        }
    }

    /**
     * Función que sobreescribe la accion de pulsar atrás en el movil.
     * Si la barra de navegación esta desplegada, la cerrará
     */
    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }
    }


}