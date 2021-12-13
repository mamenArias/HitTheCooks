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
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.appverse.hitthecooks.R
import com.appverse.hitthecooks.interfaces.RecyclerTransferItem
import com.appverse.hitthecooks.model.Item
import com.appverse.hitthecooks.model.Product
import com.appverse.hitthecooks.utils.FirestoreCollections
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.skydoves.balloon.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import kotlin.concurrent.thread
import kotlin.properties.Delegates
import kotlin.random.Random

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
class FoodListAdapter (private val activity:Activity, private val list:ArrayList<Item>, private val listId : String):RecyclerView.Adapter<FoodListHolder>() {

    private val transfer: RecyclerTransferItem by lazy { activity as RecyclerTransferItem }

    /** Objeto que contiene la instancia a base de datos de Firebase **/
    private val db= FirebaseFirestore.getInstance()


    /**
     * Función que infla el layout.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodListHolder {
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

        /**
         * Borra un elemento de la lista de la compra al pulsar sobre el
         */
        holder.imageFood.setOnClickListener {
            if(holder.absoluteAdapterPosition!=-1) {
                db.collection(FirestoreCollections.LISTS).document(listId).update("items",FieldValue.arrayRemove(list[holder.absoluteAdapterPosition])).addOnCompleteListener {  }

                list.removeAt(holder.absoluteAdapterPosition)
                notifyItemRemoved(holder.absoluteAdapterPosition)
            }
        }

        /**
         * Muestra las ofertas relativas al producto pulsado extraidas del "web scraping"
         */
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
                        val productsList = arrayListOf<Product>()
                    for (row in doc.select("div.product-list__item")) {
                        val productToList = Product()
                        var product = row.select("span.details").text()
                        if (product.lowercase().contains("${list[position].name}")) {
                            productToList.name = row.select("span.details").text()
                            productToList.imageUrl = row.select("img").attr("src")
                            productToList.price = row.select("p.price").text()
                            productsList.add(productToList)
                        }
                    }
                    activity.runOnUiThread {
                             var randomProduct : Product? = null
                             if(productsList.isNotEmpty()) {
                                  randomProduct = productsList[Random.nextInt(productsList.size)]
                             }

                            val view: View =
                                LayoutInflater.from(context).inflate(R.layout.balloon_layout, null)
                            val imageProduct = view.findViewById<ImageView>(R.id.imageProduct)
                            val nameProduct = view.findViewById<TextView>(R.id.nameProduct)
                            val diaLogo = view.findViewById<ImageView>(R.id.diaLogo)
                            val priceProduct = view.findViewById<TextView>(R.id.priceTextView)
                            val productNotFound =
                                view.findViewById<LottieAnimationView>(R.id.productNotFound)
                        if(randomProduct!=null) {
                            Glide.with(context).load(randomProduct.imageUrl).into(imageProduct)
                            nameProduct.text = randomProduct.name
                            priceProduct.text = randomProduct.price
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
                            setBackgroundColorResource(R.color.white)
                            setBalloonAnimation(BalloonAnimation.ELASTIC)
                            setLifecycleOwner(lifecycleOwner)
                        }
                        holder.cardView.showAlignTop(balloon)
                        balloon.dismissWithDelay(1500L)
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