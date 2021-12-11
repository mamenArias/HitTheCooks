package com.appverse.hitthecooks.recyclers

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.appverse.hitthecooks.R
import com.appverse.hitthecooks.interfaces.RecyclerTransferData

/**
 * Adapter personalizado para el RecyclerView de creación de listas
 * @author Miguel Àngel Arcos
 * @author Mamen Arias
 * @author Manuel Carrillo
 * @author Christian García
 * @author Sergio López
 * @since 1.4
 * @param activity Actividad de referencia donde implementar el recycler
 * @param backgroundsList Lista de imagenes a seleccionar como fondo
 */
class ListCreationAdapter(
    private val activity: Activity,
    private val backgroundsList: ArrayList<Int>
) : RecyclerView.Adapter<ListCreationHolder>() {

    private val transfer: RecyclerTransferData by lazy { activity as RecyclerTransferData }

    /** Ultima animación de seleccion elegida **/
    private var lastCheckAnimationView: LottieAnimationView = LottieAnimationView(activity)

    /** Ultimo CardView seleccionado **/
    private var lastClickedCardView: CardView = CardView(activity)

    /**
     * Infla el layout del Recycler de creación de listas
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCreationHolder {
        return ListCreationHolder(
            activity.layoutInflater.inflate(
                R.layout.item_background_lists,
                parent,
                false
            )
        )
    }

    /**
     * Da valor a los elementos del holder que serán mostrados en el RecyclerView
     */
    override fun onBindViewHolder(holder: ListCreationHolder, position: Int) {
        //Muestra las imagenes en cada posición
        holder.backgroundImage.setImageResource(backgroundsList[position])
        /**
         * Al hacer click en un CardView, muestra la animación de seleccion sobre la misma,
         * ocultando la animación del cardview anteriormente elegido
         */
        holder.cardViewBackground.setOnClickListener {
            var check: Boolean = false
            //Oculta la animación anterior
            lastCheckAnimationView.visibility = View.INVISIBLE
            //El cardview anteriormente elegido vuelve a estar disponible para elegir
            lastClickedCardView.isClickable = true
            //Muestra la animación en la posición actualmente elegida
            holder.checkAnimation.visibility = View.VISIBLE
            transfer?.passData(backgroundsList[position])
            //Realiza la animación
            check = checkAnimation(holder.checkAnimation, check)
            holder.cardViewBackground.isClickable = false
            //Cambia los valores de la referencia de la ultima posicion a la actual
            lastClickedCardView = holder.cardViewBackground
            lastCheckAnimationView = holder.checkAnimation
        }

    }

    /**
     * Devuelve el número de objetos que contiene la lista de imágenes
     */
    override fun getItemCount(): Int {
        return backgroundsList.size
    }

    /**
     * Función que ejecuta la animación de selección sobre el cardview actualmente elegido
     */
    private fun checkAnimation(imageView: LottieAnimationView?, check: Boolean?): Boolean {
        if (!check!!) {
            imageView?.playAnimation()
        }
        return !check
    }
}