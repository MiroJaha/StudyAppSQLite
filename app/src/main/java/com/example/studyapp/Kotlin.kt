package com.example.studyapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.FieldPosition

class Kotlin : AppCompatActivity() {

    private val dbHelper by lazy { DBHelper(applicationContext,tableName = "Kotlin") }

    private lateinit var recyclerView : RecyclerView
    private lateinit var list: ArrayList<Data>
    private lateinit var addButton: FloatingActionButton
    private lateinit var adapter: RecyclerViewAdapter

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val item1=menu!!.getItem(0)
        val item2=menu.getItem(1)
        item1.title="Main Menu"
        item2.title="Android Studio Review"
        return super.onPrepareOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.kotlinChoice -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.androidChoice -> {
                startActivity(Intent(this, Android::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.study_list)

        title="Kotlin Review"

        recyclerView=findViewById(R.id.listStudy)
        addButton= findViewById(R.id.addNew)

        list= dbHelper.gettingData()

        val dialogBuilder = AlertDialog.Builder(this)

        val listTitle= listOf(
            "var and val",
            "try and catch",
            "when"
        )
        val listMaterials= listOf(
            "Use to Initialize Variables",
            "Use To Catch Errors",
            "Use for Multiple Choices"
        )

        val listDetails= listOf(
            "The Value of Variable (var) Can be Change\nThe Value of Variable (val) can't be Change",
            "You also use it to let the user enter only specific type of data",
            "Has the same use as if/else but more shorter"
        )
        for (int in listTitle.indices) {
            var check= false
            for (i in list) {
                if (i.title.contains(listTitle[int]))
                    check= true
            }
            if (!check)
                dbHelper.saveData(Data(int,listTitle[int],listMaterials[int],listDetails[int]))
        }
        list.clear()
        list.addAll(dbHelper.gettingData())

        adapter= RecyclerViewAdapter(this,1,list)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter.setOnItemClickListener(object : RecyclerViewAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {

                dialogBuilder.setMessage("Details:\n${list[position].details}")
                    .setCancelable(false)
                    .setPositiveButton("OK") { dialog, _ -> dialog.cancel() }

                val alert=dialogBuilder.create()
                alert.setTitle(list[position].title)
                alert.show()
            }
        })
        addButton.setOnClickListener{
            val intent= Intent(this,AddCelebrity::class.java)
            intent.putExtra("tableName","Kotlin")
            startActivity(intent)
        }

    }

    override fun onResume() {
        list.clear()
        list.addAll(dbHelper.gettingData())
        adapter.notifyDataSetChanged()
        super.onResume()
    }
}