package com.appverse.hitthecooks.recyclers

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.appverse.hitthecooks.R
import com.appverse.hitthecooks.interfaces.RecyclerTransferItem
import com.appverse.hitthecooks.model.Item
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.skydoves.balloon.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import kotlin.concurrent.thread

/**
 * Adapter para el RecylerView de los alimentos a agregar a la lista de la compra.
 * @author Miguel Àngel Arcos
 * @author Mamen Arias
 * @author Manuel Carrillo
 * @author Christian García
 * @author Sergio López
 * @since 1.1
 * @param activity Actividad donde se implementa el Recycler.
 * @param list ArrayList con los alimentos que se pueden agregar a la lista de la compra.
 */
class FoodListAdapter (val activity:Activity, val list:ArrayList<Item>):RecyclerView.Adapter<FoodListHolder>() {

    private val transfer: RecyclerTransferItem by lazy { activity as RecyclerTransferItem }

    /** Objeto que contiene la instancia a base de datos de Firebase **/
    private val db= FirebaseFirestore.getInstance()
    /**
     * Función que infla el layout.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodListHolder {
        //return FoodListHolder(activity.layoutInflater.inflate(R.layout.recycler_food_list, parent, false))
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_food_list,parent,false)
        return FoodListHolder(view)
    }

    /**
     * Función que asigna valor a los elementos del ViewHolder, que se van a mostrar en el Recycler.
     */
    override fun onBindViewHolder(holder: FoodListHolder, position: Int) {
        val context: Context = holder.imageFood.context
        Glide.with(context).load(list[position].picUrl).into(holder.imageFood)
        holder.textFood.text = list[position].name

        holder.imageFood.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.borrarElemento)
            builder.setMessage(R.string.borrarElementoConfirmacion)
            builder.setPositiveButton(R.string.yes,
                DialogInterface.OnClickListener { dialogInterface, i ->
                //Borra el elemento del recycler
                    transfer.deleteItem(list[position])
                dialogInterface.cancel()
            })
            //Cancela el borrado del elemento
            builder.setNegativeButton(R.string.no,
                DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.cancel()
            })
            var alert = builder.create()
            alert.show()

        }
        
        holder.imageFood.setOnLongClickListener {
                thread {
                    val doc: Document =
                        Jsoup.connect(
                            "https://www.dia.es/compra-online/search?text=${
                                list[position].name.replace(
                                    " ",
                                    "+"
                                )
                            }"
                        ).get()
                    var title = ""
                    var image = ""
                    var price = ""
                    for (row in doc.select("div.product-list__item")) {
                        var product = row.select("span.details").text()
                        if (product.lowercase().contains("${list[position].name}")) {
                            println(row.select("span.details").text())
                            title = row.select("span.details").text()
                            image = row.select("img").attr("src")
                            price = row.select("p.price").text()
                        }
                    }
                    activity.runOnUiThread {
                        val view : View = LayoutInflater.from(context).inflate(R.layout.balloon_layout,null)
                        val imageProduct = view.findViewById<ImageView>(R.id.imageProduct)
                        val nameProduct = view.findViewById<TextView>(R.id.nameProduct)
                        val diaLogo = view.findViewById<ImageView>(R.id.diaLogo)
                        val priceProduct = view.findViewById<TextView>(R.id.priceTextView)
                        val productNotFound = view.findViewById<LottieAnimationView>(R.id.productNotFound)
                        if(title.isNotEmpty()) {
                            Glide.with(context).load(image).into(imageProduct)
                            nameProduct.text = title
                            priceProduct.text = price
                            diaLogo.visibility = View.VISIBLE
                        }else{
                            productNotFound.visibility = View.VISIBLE
                            nameProduct.text = context.resources.getString(R.string.productNotFound)
                        }

                        val balloon = createBalloon(context) {
                            setLayout(view)
                            setArrowSize(10)
                            setWidth(150)
                            setHeight(150)
                            setArrowPosition(0.7f)
                            setCornerRadius(4f)
                            setAlpha(0.9f)
                            setTextColorResource(R.color.black)
                            setTextIsHtml(true)
                            setIconDrawable(ContextCompat.getDrawable(context, R.drawable.add_icon))
                            setBackgroundColorResource(R.color.white)
                            setBalloonAnimation(BalloonAnimation.ELASTIC)
                            setLifecycleOwner(lifecycleOwner)
                        }
                        holder.cardView.showAlignTop(balloon)
                }
        }
            return@setOnLongClickListener true
        }
}
    /**
     * Función que devuelve el número de objetos que tiene el ArrayList
     */
    override fun getItemCount(): Int {
        return list.size
    }
}