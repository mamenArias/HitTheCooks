package recyclers

import android.app.Activity
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import classes.Student
import com.appverse.hitthecooks.R

/**
 * Adapter personalizado para el RecyclerView de alumnos
 * @param activity Actividad donde se implementa el RecyclerView
 * @param list ArrayList con los alumnos a introducir en el RecyclerView
 * @author
 * @since
 */
class AboutUsAdapter(val activity: Activity, val list: ArrayList<Student>) :
    RecyclerView.Adapter<AboutUsHolder>() {

    /**
     * Infla el layout del Recycler sobre nosotros
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AboutUsHolder {
        return AboutUsHolder(
            activity.layoutInflater.inflate(
                R.layout.recycler_about_us, parent, false
            )
        )
    }

    /**
     * Da valor a los elementos del holder, que serán mostrados en el RecyclerView
     */
    override fun onBindViewHolder(holder: AboutUsHolder, position: Int) {
        //Nombre del alumno
        holder.name.text = list[position].name
        //De momento, mostrará el icono de la app
        holder.image.setImageResource(
            activity.resources.getIdentifier(list[position].image, "drawable", activity.packageName)
        )
    }

    /**
     * Devuelve el número de objetos que contiene la lista
     */
    override fun getItemCount(): Int {
        return list.size
    }
}