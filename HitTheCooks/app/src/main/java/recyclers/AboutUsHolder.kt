package recyclers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appverse.hitthecooks.R

class AboutUsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val name:TextView by lazy { itemView.findViewById(R.id.studentName) }
    val image:ImageView by lazy { itemView.findViewById(R.id.studentPicture) }

}