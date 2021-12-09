package com.appverse.hitthecooks

import android.annotation.SuppressLint
import android.app.usage.ExternalStorageStats
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.appverse.hitthecooks.databinding.ActivityEditProfileBinding
import com.appverse.hitthecooks.databinding.ActivityMainBinding
import com.appverse.hitthecooks.utils.FirestoreCollections
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.lang.ref.PhantomReference

class EditProfile : SuperActivity() {

    private val binding by lazy { ActivityEditProfileBinding.inflate(layoutInflater) }
    private val db= FirebaseFirestore.getInstance()
    private lateinit var auth : FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var image: Uri

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(binding.root)
        drawerLayout.addView(binding.root, 1)
        navigationView.setCheckedItem(R.id.nav_profile)

        applyDarkMode(binding.root)

        db.collection(FirestoreCollections.USERS).document(Firebase.auth.currentUser!!.email.toString()).get().addOnSuccessListener {

            binding.userName.text = it.get("email").toString()
            Glide.with(this).load(it.get("profileImage")).circleCrop().into(binding.profileIcon as ImageView)
        }
        /*auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")*/


        binding.goBackButton.setOnClickListener {
            val intent: Intent = Intent(this, PantallaPrincipal::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }

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