package com.example.quizapp.Fragment

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.quizapp.MainActivity
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentProfileBinding
import com.example.quizapp.model.User
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.math.log


class ProfileFragment : Fragment() {
    val binding by lazy {
        FragmentProfileBinding.inflate(layoutInflater)
    }
    var isExpand = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding.imageButton.setOnClickListener {
            if (isExpand) {
                binding.expandableconstraintlayout.visibility = View.VISIBLE
                binding.imageButton.setImageResource(R.drawable.arrowup)
            } else {
                binding.expandableconstraintlayout.visibility = View.GONE
                binding.imageButton.setImageResource(R.drawable.downarrow)

            }
            isExpand = !isExpand
        }

    Firebase.database.reference.child("Users")
    .child(Firebase.auth.currentUser!!.uid)
    .addListenerForSingleValueEvent(
    object : ValueEventListener {


        override fun onDataChange(snapshot: DataSnapshot) {
            var user = snapshot.getValue<User>()
            binding.Name.text = user?.name
            binding.nameUp.text = user?.name
            binding.Password.text = user?.password
            binding.Email.text = user?.email
            binding.Age.text = user?.age.toString()

        }

        override fun onCancelled(error: DatabaseError) {

            // TODO("Not yet implemented")
        }

    }
    )

    return binding.root

}
}