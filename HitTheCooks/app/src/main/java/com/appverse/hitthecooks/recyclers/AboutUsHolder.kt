package com.appverse.hitthecooks.recyclers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appverse.hitthecooks.R

/**
 * ViewHolder personalizado para el RecyclerView de About Us, contiene los elementos personalizables del layout del Recycler
 * @author Miguel Àngel Arcos
 * @author Mamen Arias
 * @author Manuel Carrillo
 * @author Christian Gracía
 * @author Sergio López
 * @since 1.1
 */
class AboutUsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    /** TextView que muestra el nombre del alumno **/
    val name: TextView by lazy { itemView.findViewById(R.id.studentName) }
    /** ImageView que muestra el la imagen del alumno **/
    val image: ImageView by lazy { itemView.findViewById(R.id.studentPicture) }

}