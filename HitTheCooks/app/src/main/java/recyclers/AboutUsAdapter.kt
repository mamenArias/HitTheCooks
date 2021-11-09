package recyclers

import android.app.Activity
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import classes.Student
import com.appverse.hitthecooks.R

class AboutUsAdapter(val activity: Activity, val list:ArrayList<Student>) : RecyclerView.Adapter<AboutUsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AboutUsHolder {
        return AboutUsHolder(activity.layoutInflater.inflate(R.layout.recycler_about_us, parent, false))
    }

    override fun onBindViewHolder(holder: AboutUsHolder, position: Int) {

        holder.name.text = list[position].name
        //De momento, mostrará el icono de la app
        holder.image.setImageResource(activity.resources.getIdentifier(list[position].image, "drawable", activity.packageName))
    }

    override fun getItemCount(): Int {
        return list.size
    }
}