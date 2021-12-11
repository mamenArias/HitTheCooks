package com.appverse.hitthecooks

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.appverse.hitthecooks.databinding.ActivityEditProfileBinding
import com.appverse.hitthecooks.utils.FirestoreCollections
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

/**
 * Activity que contiene la pantalla de editar perfil
 * @author Miguel Àngel Arcos
 * @author Mamen Arias
 * @author Manuel Carrillo
 * @author Christian García
 * @author Sergio López
 * @since 1.4
 */
class EditProfile : SuperActivity() {

    /** Objeto que permite enlazar con las vistas del layout **/
    private val binding by lazy { ActivityEditProfileBinding.inflate(layoutInflater) }
    /** Objeto que permite obstener la instancia a la base de datos **/
    private val db= FirebaseFirestore.getInstance()
    /** Objeto que permite obstener el autentificador de Firebase **/
    private lateinit var auth : FirebaseAuth
    /** Objeto que permite obstener la referencia de la base de datos **/
    private lateinit var databaseReference: DatabaseReference
    /** Objeto que permite obstener la referencia del storage de Firebase **/
    private lateinit var storageReference: StorageReference
    /** Ruta de la imagen **/
    private lateinit var image: Uri

    /**
     * Inicializa la actividad, infla el layout y carga el usuario logado
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Infla la vista en el layout de la actividad superior
        drawerLayout.addView(binding.root, 1)
        navigationView.setCheckedItem(R.id.nav_profile)
        //Aplica el modo oscuro si está activado
        applyDarkMode(binding.root)
        //Obtiene los datos del usuario actualmente logado
        db.collection(FirestoreCollections.USERS).document(Firebase.auth.currentUser!!.email.toString()).get().addOnSuccessListener {
            //Recupera el nombre y la imagen del usuario
            binding.userName.text = it.get("email").toString()
            Glide.with(this).load(it.get("profileImage")).circleCrop().into(binding.profileIcon as ImageView)
        }
        /*auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")*/

        /**
         * Función que permite navegar a la actividad principal
         */
        binding.goBackButton.setOnClickListener {
            val intent: Intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }

    /**
     * Función que recupera la imagen del usuario de Firebase
     */
    private fun uploadProfilePic(){
        image = Uri.parse("gs://hit-the-cooks.appspot.com/imagenesPerfil/default.png")
        storageReference = FirebaseStorage.getInstance().getReference("Users/"+auth.currentUser?.uid)
        storageReference.putFile(image).addOnSuccessListener {
            Toast.makeText(this@EditProfile, "Foto cambiada con éxito", Toast.LENGTH_LONG).show()
        }.addOnFailureListener{
            Toast.makeText(this@EditProfile, "Error al subir la foto", Toast.LENGTH_LONG).show()
        }
    }

}