package com.appverse.hitthecooks

import android.app.Activity
import android.app.usage.ExternalStorageStats
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.appverse.hitthecooks.databinding.ActivityEditProfileBinding
import com.appverse.hitthecooks.databinding.ActivityMainBinding
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
import java.lang.ref.PhantomReference

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
        binding.profileIcon.setOnClickListener{
            selectImage()
        }

        binding.registerButton.setOnClickListener{
            uploadProfilePic()
            updateImage("")
        }

        binding.goBackButton.setOnClickListener {
            val intent: Intent = Intent(this, PantallaPrincipal::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }

    /**
     * Función para coger una foto de la galería
     */
    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        selectImageResultLauncher.launch(intent)
    }

    /**
     * Función para actualizar la información de la imagen de perfil en base de datos
     */
    private fun updateImage(uploadedImageUrl: String) {
        val hashmap: HashMap<String, Any> = HashMap()
        hashmap["name"] = "$name"
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
        image = Uri.parse("imagenesPerfil/"+auth.uid)
        storageReference = FirebaseStorage.getInstance().getReference("Users/"+auth.currentUser?.uid)
        storageReference.putFile(image).addOnSuccessListener { taskSnapshot ->
            val uriTask: Task<Uri> = taskSnapshot.storage.downloadUrl
            while (!uriTask.isSuccessful);
            val imageURL = "${uriTask.result}"

            updateImage(imageURL)
            Toast.makeText(this@EditProfile, "Foto cambiada con éxito", Toast.LENGTH_LONG).show()
        }.addOnFailureListener{
            Toast.makeText(this@EditProfile, "Error al subir la foto", Toast.LENGTH_LONG).show()
        }
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

                binding.profileIcon.setImageURI(image)
            }else{
                Toast.makeText(this, "Cancelado", Toast.LENGTH_LONG).show()
            }
        }
    )

}