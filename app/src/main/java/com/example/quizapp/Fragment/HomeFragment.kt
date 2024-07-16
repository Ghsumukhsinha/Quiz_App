package com.example.quizapp.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.quizapp.R
import com.example.quizapp.adaptor.categoryadaptor
import com.example.quizapp.databinding.FragmentHomeBinding
import com.example.quizapp.model.User
import com.example.quizapp.model.categorymodelclass
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {
   private  val binding: FragmentHomeBinding by lazy {
       FragmentHomeBinding.inflate(layoutInflater)
   }
    private var categoryList = ArrayList<categorymodelclass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Here we use category model class for allocating images and text
        // category list is just for counting no. of category goes in category model class

        categoryList.add(categorymodelclass(R.drawable.sciencelogo_removebg_preview,"Science"))
        categoryList.add(categorymodelclass(R.drawable._720458_removebg_preview,"Mathematics"))
        categoryList.add(categorymodelclass(R.drawable.gklogo,"General knowledge"))
        categoryList.add(categorymodelclass(R.drawable.cmptrivn_removebg_preview,"Computer"))
        categoryList.add(categorymodelclass(R.drawable.engicn_removebg_preview,"English"))
        categoryList.add(categorymodelclass(R.drawable.mathslpgo_removebg_preview,"Reasoning"))

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Firebase.database.reference.child("Users")
            .child(Firebase.auth.currentUser!!.uid)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var user=snapshot.getValue<User>()
                        binding.Name.text=user?.name
                      
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // TODO("Not yet implemented")
                    }

                }
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            // here we decide how many grids will be there

        binding.categoryRecylerview.layoutManager=GridLayoutManager(requireContext(),2)
        var adapter = categoryadaptor(categoryList,requireActivity())
        binding.categoryRecylerview.adapter =adapter
        binding.categoryRecylerview.setHasFixedSize(true)
    }
    companion object {

    }
}