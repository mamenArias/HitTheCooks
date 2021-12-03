package recyclers

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.appverse.hitthecooks.R

class ShoppingListHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val cardViewList : CardView by lazy { itemView.findViewById(R.id.shoppingList) }
    val textViewShoppingListName : TextView by lazy { itemView.findViewById(R.id.shoppingListName) }
}