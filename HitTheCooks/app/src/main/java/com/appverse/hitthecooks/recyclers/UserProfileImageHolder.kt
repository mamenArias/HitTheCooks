package com.appverse.hitthecooks.recyclers

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.appverse.hitthecooks.R

/**
 * Holder personalizado para el recycler de usuarios invitados
 * @author Miguel Àngel Arcos
 * @author Mamen Arias
 * @author Manuel Carrillo
 * @author Christian García
 * @author Sergio López
 * @since 1.1
 */
class UserProfileImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    /** Imagen del usuario invitado **/
    val userImageProfile : ImageView by lazy { itemView.findViewById(R.id.userImageProfile)  }

}