package com.appverse.hitthecooks.interfaces

import com.appverse.hitthecooks.model.Item

/**
 * Interfaz que contiene las funciones para comunicar dos Recycler
 * @author Miguel Àngel Arcos
 * @author Mamen Arias
 * @author Manuel Carrillo
 * @author Christian García
 * @author Sergio López
 * @since 1.4
 */
interface RecyclerTransferItem {

    /**
     * Función que pasa un Item de un Recycler a otro.
     * @param item Item (Producto) a pasar de un Recycler a otro
     */
    fun passItem(item: Item)

}