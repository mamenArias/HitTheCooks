package com.appverse.hitthecooks.interfaces

import com.appverse.hitthecooks.model.Item

interface RecyclerTransferItem {
    /**
     * Función que pasa un Item de un Recycler a otro.
     */
    fun passItem(item: Item)

}