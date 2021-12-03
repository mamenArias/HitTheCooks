package recyclers

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.appverse.hitthecooks.R
import com.appverse.hitthecooks.interfaces.RecyclerTransferData


class ListCreationAdapter(private val activity : Activity, private val backgroundsList : ArrayList<Int>): RecyclerView.Adapter<ListCreationHolder>() {
    private val  transfer : RecyclerTransferData by lazy { activity as RecyclerTransferData}
    private var lastCheckAnimationView : LottieAnimationView = LottieAnimationView(activity)
    private var lastClickedCardView : CardView = CardView(activity)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCreationHolder {
        return ListCreationHolder(activity.layoutInflater.inflate(R.layout.item_background_lists,parent,false))
    }

    override fun onBindViewHolder(holder: ListCreationHolder, position: Int) {
       holder.backgroundImage.setImageResource(backgroundsList[position])
        holder.cardViewBackground.setOnClickListener {
            var check : Boolean = false
            lastCheckAnimationView.visibility = View.INVISIBLE
            lastClickedCardView.isClickable = true
            holder.checkAnimation.visibility = View.VISIBLE
            transfer?.passData(backgroundsList[position])
            check = checkAnimation(holder.checkAnimation, check)
            holder.cardViewBackground.isClickable = false
            lastClickedCardView = holder.cardViewBackground
            lastCheckAnimationView = holder.checkAnimation
        }

    }

    override fun getItemCount(): Int {
        return backgroundsList.size
    }

    private fun checkAnimation(imageView: LottieAnimationView?,  check: Boolean?) : Boolean {
        if (!check!!) {
            imageView?.playAnimation()
        }
        return !check
    }
}