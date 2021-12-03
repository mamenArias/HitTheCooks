package com.appverse.hitthecooks

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import recyclers.ShoppingListAdapter

class SwipeToDelete(private var adapter: ShoppingListAdapter, private val context: Context) : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

     override fun getSwipeDirs(
         recyclerView: RecyclerView,
         viewHolder: RecyclerView.ViewHolder
     ): Int {
         if(viewHolder.itemViewType == R.layout.item_creation_list){
             return 0
         }
         return super.getSwipeDirs(recyclerView, viewHolder)
     }

     override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        var pos = viewHolder.absoluteAdapterPosition

        var builder = AlertDialog.Builder(context)

        builder.setMessage(R.string.deletionConfirmation)
        builder.setPositiveButton(R.string.yes,DialogInterface.OnClickListener { dialogInterface, i ->

            adapter.deleteItem(pos)
            dialogInterface.cancel()
        })

        builder.setNegativeButton(R.string.no,DialogInterface.OnClickListener { dialogInterface, i ->
            dialogInterface.cancel()
            adapter.notifyDataSetChanged()
        })
        var alert = builder.create()
        alert.show()
    }
}