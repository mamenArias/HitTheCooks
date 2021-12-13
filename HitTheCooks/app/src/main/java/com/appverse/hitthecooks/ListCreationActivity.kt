package com.appverse.hitthecooks

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.appverse.hitthecooks.databinding.ActivityListCreationBinding
import com.appverse.hitthecooks.interfaces.RecyclerTransferData
import com.appverse.hitthecooks.model.ShoppingList
import com.appverse.hitthecooks.model.User
import com.appverse.hitthecooks.utils.FirestoreCollections
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.appverse.hitthecooks.recyclers.ListCreationAdapter
import com.google.firebase.auth.FirebaseAuth

/**
 * Actividad que contiene la pantalla de creación de listas
 * @author Miguel Àngel Arcos
 * @author Mamen Arias
 * @author Manuel Carrillo
 * @author Christian García
 * @author Sergio López
 * @since 1.4
 */
class ListCreationActivity : SuperActivity(),RecyclerTransferData {
    /** Referencia de la imagen seleccionada por el usuario **/
    private var imageId : Int? = null
    /** Objeto que permite enlazar con el autentificador de usuarios de Firebase **/
    private lateinit var auth :FirebaseAuth
    /** Objeto que permite enlazar las vistas del layout con la actividad **/
    private val binding by lazy { ActivityListCreationBinding.inflate(layoutInflater) }

    /**
     * Inicializa la actividad
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Infla la vista en el layout de la actividad super
        drawerLayout.addView(binding.root, 1)
        //Aplica el modo oscuro si está activo
        applyDarkMode(binding.root)
        //Instancia los objetos de autentificador y base de datos de Firebase
        auth = FirebaseAuth.getInstance()
        val db = Firebase.firestore

        //Lista de imagenes a seleccionar por el usuario como fondo
        val backgroundsList = arrayListOf<Int>(R.drawable.background_list_2,
         R.drawable.background_list_4,R.drawable.background_list_1,R.drawable.background_list_3,
            R.drawable.background_list_5,R.drawable.background_list_6,R.drawable.background_list_7,R.drawable.background_list_8)

        //Configura el recycler
        binding.recyclerViewBackgroundLists.adapter = ListCreationAdapter(this,backgroundsList)
        binding.recyclerViewBackgroundLists.layoutManager = GridLayoutManager(this,2,
            LinearLayoutManager.VERTICAL,false)

        /**
         * Comprueba que el usuario ha seleccionado un nombre y una imagen para la lista,
         * si es así, añade la lista con la referencia del usuario a la base de datos.
         * En caso contrario, mostrará un mensaje de advertencia al usuario
         */
        binding.nextButton.setOnClickListener {
            if(binding.nameListTextView.text.isNotEmpty() && imageId != null) {
                val ref = db.collection(FirestoreCollections.LISTS).document()
                var user : User ?=null
                db.collection(FirestoreCollections.USERS).document(auth.currentUser?.email.toString()).get().addOnCompleteListener {
                    if(it.isSuccessful){
                        user = it.result.toObject(User::class.java)!!
                    }
                }.addOnSuccessListener {
                    user?.listsIds?.add(ref.id)
                    user?.email?.let { email -> db.collection(FirestoreCollections.USERS).document(email).set(user!!) }
                    ref.set(
                        ShoppingList(
                            binding.nameListTextView.text.toString(),
                            imageId!!,
                            ref.id,
                            arrayListOf(user?.email?:""))
                        )
                }.addOnSuccessListener {
                    //Pasa por bundle el valor de referencia de la lista creada a la actividad de invitación
                    val bundle = Bundle()
                    bundle.putString("listId",ref.id)
                    startActivity(Intent(this, InvitationActivity::class.java).putExtras(bundle))
                }

                //Ir a la pantalla de invitación de usuarios que genera un enlace de Dynamics Links
            }else{
                if(imageId == null){
                    Toast.makeText(this, R.string.imageListNotSelected, Toast.LENGTH_SHORT).show()
                }
                if(binding.nameListTextView.text.isEmpty()){
                    Toast.makeText(this, R.string.nameListIsEmpty, Toast.LENGTH_SHORT).show()
                }
            }
        }

        /**
         * Despliega el menú de navegación lateral
         */
        binding.menuButton.setOnClickListener {
            super.drawerLayout.openDrawer(GravityCompat.START)
        }

    }

    /**
     * Función que permite recoger el valor de referencia de la imagen seleccionada en el recycler
     * @param value ID de la imagen recogido del adapter
     */
    override fun passData(value: Int ) {
        imageId=  value
    }


}