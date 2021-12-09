package recyclers

import android.app.Activity
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appverse.hitthecooks.R
import com.appverse.hitthecooks.model.Item

class FoodListAdapter (val activity:Activity, val list:ArrayList<Item>):RecyclerView.Adapter<FoodListHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodListHolder {
        return FoodListHolder(activity.layoutInflater.inflate(R.layout.recycler_food_list, parent, false))
    }

    override fun onBindViewHolder(holder: FoodListHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return list.size
    }
}