package com.example.quizapp.adaptor

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.QuizActivity
import com.example.quizapp.databinding.CategoryitemBinding
import com.example.quizapp.model.categorymodelclass

  // category list define how many data will show in the recycler view,it is of array type

class categoryadaptor(var categoryList: ArrayList<categorymodelclass>, var requireActivity: FragmentActivity) : RecyclerView.Adapter<categoryadaptor.MycategoryViewHolder>() {
    class MycategoryViewHolder(var Binding:CategoryitemBinding) :RecyclerView.ViewHolder(Binding.root){

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MycategoryViewHolder {
       return MycategoryViewHolder(
           CategoryitemBinding.inflate(
               LayoutInflater.from(parent.context),parent,false
           )
       )
    }

    //it is for item count in RV

    override fun getItemCount() =categoryList.size

    override fun onBindViewHolder(holder: MycategoryViewHolder, position: Int) {
        var dataList =categoryList[position]
        holder.Binding.categoryimage.setImageResource(dataList.catImage)
        holder.Binding.category.text=dataList.catText
        holder.Binding.categorybtn.setOnClickListener {

            var intent = Intent(requireActivity,QuizActivity::class.java)
            intent.putExtra("categoryImg",dataList.catImage)
            intent.putExtra("categoryText",dataList.catText)
            requireActivity.startActivity(intent)
        }
    }
}