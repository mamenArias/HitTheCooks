package com.appverse.hitthecooks

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.GravityCompat
import com.appverse.hitthecooks.databinding.ActivityEditProfileBinding
import com.appverse.hitthecooks.utils.FirestoreCollections
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import androidx.annotation.NonNull
import com.appverse.hitthecooks.model.User

import com.google.android.gms.tasks.OnCompleteListener

import com.google.firebase.auth.UserProfileChangeRequest

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.SetOptions

import com.bumptech.glide.load.engine.DiskCacheStrategy





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
    /** Objeto que permite obstener la referencia del storage de Firebase **/
    private lateinit var storageReference: StorageReference
    /** Ruta de la imagen **/
    private lateinit var image: Uri
    /** Variable que sirve para instanciar la clase StorageReference**/
    private lateinit var storageRef : StorageReference
    /** Constante que instancia un objeto de la clase FirebaseStorage**/
    val dbStorage = FirebaseStorage.getInstance()

    /**
     * Inicializa la actividad, infla el layout y carga el usuario logado
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        lateinit var email:String

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
        binding.profileIcon.setOnClickListener{
            if(!binding.userName.text.toString().endsWith("gmail.com")){
                selectImage()
            } else{
                Toast.makeText(this, "No puedes cambiar la foto de un perfil de Gmail", Toast.LENGTH_SHORT).show()
            }
            
        }

        binding.registerButton.setOnClickListener{
            uploadProfilePic()
        }

        binding.goBackButton.setOnClickListener {
            val intent: Intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        /**
         * Despliega el menú de navegación lateral
         */
        binding.menuButton.setOnClickListener {
            super.drawerLayout.openDrawer(GravityCompat.START)
        }

    }

    /**
     * Función para coger una foto de la galería
     */
    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        selectImageResultLauncher.launch(intent)




        //user.updateProfile()




    }

    /**
     * Función para actualizar la información de la imagen de perfil en base de datos
     */
    private fun updateImage(uploadedImageUrl: String) {
        val hashmap: HashMap<String, Any> = HashMap()
        hashmap["email"] = binding.userName
        if (image != null){
            hashmap["profileImage"] = uploadedImageUrl
        }

        val reference = FirebaseDatabase.getInstance().getReference("Users")
        reference.child(auth.uid!!).updateChildren(hashmap).addOnSuccessListener {
            Toast.makeText(this, "Imagen actualizada con éxito", Toast.LENGTH_LONG).show()

        }.addOnFailureListener{
            Toast.makeText(this, "Error al actualizar la imagen", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Función para subir la foto a Firebase
     */
    private fun uploadProfilePic(){
        val path = "imagenesPerfil/"+auth.uid
        storageReference = FirebaseStorage.getInstance().getReference(path)
        var reference = FirebaseDatabase.getInstance().getReference(FirestoreCollections.USERS)
        reference.child("imageProfile").setValue(Uri.parse(binding.profileIcon.toString()))


    }

    /**
     * Para usar los resultados del intent de la galería
     */
    private val selectImageResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult> { result ->
            if(result.resultCode == Activity.RESULT_OK){
                val data = result.data
                image = data!!.data!!
                auth= FirebaseAuth.getInstance()

                binding.profileIcon.setImageURI(image)

              val reference= FirebaseStorage.getInstance().getReference("imagenesPerfil/"+auth.currentUser!!.email.toString()+".jpg")

                reference.putFile(image).addOnSuccessListener {
                    Toast.makeText(this, "Exito", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "Fallo", Toast.LENGTH_SHORT).show()
                }

                /*val referenceImages = FirebaseStorage.getInstance().getReference("imagenesPerfil").child(""+"".startsWith(auth.currentUser!!.email.toString()))


                referenceImages.downloadUrl.addOnSuccessListener {url->
                    var userToInsert = User(auth.currentUser!!.email.toString(),url.toString())

                    db.collection( FirestoreCollections.USERS).document(auth.currentUser!!.email.toString()).
                }*/

                //storageRef =
                   // dbStorage.reference.child("imagenesPerfil").child(auth.currentUser!!.email.toString()+".jpg")


                reference.downloadUrl.addOnSuccessListener { url ->
                 var user = User(binding.userName.text.toString(), url.toString())
                    db.collection(FirestoreCollections.USERS).document(auth.currentUser!!.email.toString()).set(
                       user
                    )}.addOnFailureListener {
                    Toast.makeText(this, "Fallo", Toast.LENGTH_SHORT).show()
                }





            }else{
                Toast.makeText(this, "Cancelado", Toast.LENGTH_LONG).show()
            }
        }
    )

}