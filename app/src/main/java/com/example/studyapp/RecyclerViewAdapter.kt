package com.example.studyapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import io.github.muddz.styleabletoast.StyleableToast
import kotlinx.android.synthetic.main.items_view.view.*

class RecyclerViewAdapter (private val context: Context,private val location: Int, private val list:ArrayList<Data>): RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>(){

    private lateinit var myListener: onItemClickListener
    private val dbHelper by lazy {
        var tableName=""
        tableName = if (location==1)
            "Kotlin"
        else
            "Android"
        DBHelper(context,tableName= tableName)
    }

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener:onItemClickListener ){
        myListener=listener
    }

    class ItemViewHolder (itemView : View,listener: onItemClickListener): RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.items_view,
                parent,
                false
            )
        ,myListener
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val subject=list[position]


        holder.itemView.apply {
            Title.text= subject.title
            Material.text= subject.material

            deleteImage.setOnClickListener{
                val check= dbHelper.deleteCelebrity(subject.pk)
                if (check!=0)
                    StyleableToast.makeText(context,"Material Deleted Successfully!!",R.style.mytoast).show()
                else
                    StyleableToast.makeText(context,"Something Went Wrong!!",R.style.mytoast).show()
                list.clear()
                list.addAll(dbHelper.gettingData())
                notifyDataSetChanged()
            }
            editImage.setOnClickListener{
                val tableName= if (location==1)
                    "Kotlin"
                else
                    "Android"
                val intent= Intent(context,EditCelebrity::class.java)
                intent.putExtra("tableName",tableName)
                intent.putExtra("pk",subject.pk)
                startActivity(context,intent,null)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}