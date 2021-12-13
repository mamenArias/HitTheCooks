package com.appverse.hitthecooks

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

    private val STORAGE_PERMISSION_CODE=123

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
            binding.profileIcon.visibility = View.VISIBLE
        }
        /*auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")*/

        /**
         * Función que permite navegar a la actividad principal
         */
        binding.profileIcon.setOnClickListener{

                if (!binding.userName.text.toString().endsWith("gmail.com")) {
                    checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE)
                } else {
                    Toast.makeText(
                        this,
                        "No puedes cambiar la foto de un perfil de Gmail",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            
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

               /* PARTE BUENA*/
                Toast.makeText(this,auth.currentUser.toString(),Toast.LENGTH_LONG).show()
               reference.downloadUrl.addOnSuccessListener { url ->
                 var user = User(binding.userName.text.toString(), url.toString())
                    db.collection(FirestoreCollections.USERS).document(auth.currentUser!!.email.toString()).set(
                       user
                    )}.addOnFailureListener { it ->
                    Toast.makeText(this, "Fallo bbdd", Toast.LENGTH_SHORT).show()
                   Log.d("Fallo BD",""+it.stackTrace)
                }






            }else{
                Toast.makeText(this, "Cancelado", Toast.LENGTH_LONG).show()
            }
        }
    )

    private fun checkPermission(permission:String, requestCode:Int){
        //if(ContextCompat.checkSelfPermission(this, permission)==PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        //}else{
           // Toast.makeText(this, "El permiso ya ha sido otorgado", Toast.LENGTH_LONG).show()
        //}
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode==STORAGE_PERMISSION_CODE){
            if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permisos garantizados", Toast.LENGTH_LONG).show()
                selectImage()
            }else{
                Toast.makeText(this, "Permisos denegados", Toast.LENGTH_LONG).show()
            }
        }
    }
}