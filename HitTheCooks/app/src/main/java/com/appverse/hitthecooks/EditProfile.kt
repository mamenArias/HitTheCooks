package com.appverse.hitthecooks

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.appverse.hitthecooks.databinding.ActivityEditProfileBinding
import com.appverse.hitthecooks.databinding.ActivityMainBinding
import com.appverse.hitthecooks.utils.FirestoreCollections
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class EditProfile : SuperActivity() {

    private val binding by lazy { ActivityEditProfileBinding.inflate(layoutInflater) }
    private val db= FirebaseFirestore.getInstance()
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

        binding.goBackButton.setOnClickListener {
            val intent: Intent = Intent(this, PantallaPrincipal::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }

}