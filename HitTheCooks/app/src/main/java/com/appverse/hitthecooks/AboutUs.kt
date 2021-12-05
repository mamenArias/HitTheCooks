package com.appverse.hitthecooks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import classes.Student
import com.appverse.hitthecooks.databinding.ActivityAboutUsBinding
import recyclers.AboutUsAdapter

/**
 * Clase que contiene el reclicler con los miembros del grupo
 * @author
 * @since
 */
class AboutUs : SuperActivity() {

    /** Constante que permite enlazar directamente con las vistas del layout **/
    private val binding by lazy { ActivityAboutUsBinding.inflate(layoutInflater) }

    /**
     * Inicializa las vistas
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Crea el ArrayList de alumnos
        val students : ArrayList<Student> = arrayListOf<Student>()
        students.add(Student("Miguel Angel Arcos", "icono"))
        students.add(Student("Mamen Arias", "icono"))
        students.add(Student("Manuel Carrillo", "icono"))
        students.add(Student("Christian García", "icono"))
        students.add(Student("Sergio Lopez", "icono"))

        //Crea el adapter y lo aplica al RecyclerView
        val adapter : AboutUsAdapter = AboutUsAdapter(this, students)
        binding.recyclerStudents.adapter = adapter
        binding.recyclerStudents.layoutManager = LinearLayoutManager(this)

    }

    /**
     * Función que permite navegar a la pantalla principal
     */
    fun navigateToMain(view: android.view.View) {
        startActivity(Intent(this, PantallaPrincipal::class.java))
    }
}