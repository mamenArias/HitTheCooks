package com.appverse.hitthecooks

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.appverse.hitthecooks.recyclers.ShoppingListAdapter

/**
 * Clase que contiene las funciones de borrado de listas al desplazarlas
 * @author Miguel Àngel Arcos
 * @author Mamen Arias
 * @author Manuel Carrillo
 * @author Christian García
 * @author Sergio López
 * @since 1.1
 * @param adapter Adapter del RecyclerView que contiene las listas del usuario
 * @param context Contexto de la actividad donde mostrar los avisos
 */
class SwipeToDelete(private var adapter: ShoppingListAdapter, private val context: Context) : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    /**
     * Comprueba que el layout no sea el layout de crear lista para evitar que se mueva
     */
     override fun getSwipeDirs(
         recyclerView: RecyclerView,
         viewHolder: RecyclerView.ViewHolder
     ): Int {
         if(viewHolder.itemViewType == R.layout.item_creation_list){
             return 0
         }
         return super.getSwipeDirs(recyclerView, viewHolder)
     }

    /**
     * Muestra un mensaje de advertencia, si el usuario acepta, elimina la lista del recycler
     * y la borra de la base de datos
     * @param viewHolder ViewHolder del Recycler de listas
     * @param direction Dirección
     */
     override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        var pos = viewHolder.absoluteAdapterPosition
        var builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.listDeletionQuestion)
        builder.setMessage(R.string.deletionConfirmation)
        builder.setPositiveButton(R.string.yes,DialogInterface.OnClickListener { dialogInterface, i ->
            //Borra la lista
            adapter.deleteItem(pos)
            dialogInterface.cancel()
        })
        //Cancela el borrado
        builder.setNegativeButton(R.string.no,DialogInterface.OnClickListener { dialogInterface, i ->
            dialogInterface.cancel()
            adapter.notifyDataSetChanged()
        })
        var alert = builder.create()
        alert.show()
    }
}