package com.appverse.hitthecooks

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.appverse.hitthecooks.model.Student
import com.appverse.hitthecooks.databinding.ActivityAboutUsBinding
import com.appverse.hitthecooks.recyclers.AboutUsAdapter

/**
 * Clase que contiene el reclicler con los miembros del grupo
 * @author Miguel Àngel Arcos
 * @author Mamen Arias
 * @author Manuel Carrillo
 * @author Christian García
 * @author Sergio López
 * @since 1.4
 */
class AboutUs : SuperActivity() {

    /** Constante que permite enlazar directamente con las vistas del layout **/
    private val binding by lazy { ActivityAboutUsBinding.inflate(layoutInflater) }

    /**
     * Inicializa las vistas
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Infla la vista en el layout de la actividad superior para compartir la barra de navegación
        drawerLayout.addView(binding.root, 1)
        navigationView.setCheckedItem(R.id.nav_about_us)
        //Aplica el modo oscuro en el caso de que esté activado
        applyDarkMode(binding.root)

        //Crea el ArrayList de alumnos
        val students : ArrayList<Student> = arrayListOf<Student>()
        students.add(Student(resources.getString(R.string.miguelAngelArcos),resources.getString(R.string.moneite), resources.getString(R.string.icono)))
        students.add(Student(resources.getString(R.string.mamenArias),resources.getString(R.string.mamenCorreo), resources.getString(R.string.icono)))
        students.add(Student(resources.getString(R.string.manuelCarrillo),resources.getString(R.string.manuelCorreo), resources.getString(R.string.icono)))
        students.add(Student(resources.getString(R.string.christianGarcia),resources.getString(R.string.christianCorreo), resources.getString(R.string.icono)))
        students.add(Student(resources.getString(R.string.sergioLopez),resources.getString(R.string.sergioCorreo), resources.getString(R.string.icono)))

        //Crea el adapter y lo aplica al RecyclerView
        val adapter : AboutUsAdapter = AboutUsAdapter(this, students)
        binding.recyclerStudents.adapter = adapter
        binding.recyclerStudents.layoutManager = LinearLayoutManager(this)

        /**
         * Función que permite navegar a la pantalla principal
         */
        binding.backButton?.setOnClickListener {
            val intent:Intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

    }

}