package com.example.hw_activities_mult

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var carObjects = mutableListOf<ModelAuto>()
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    val result =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val bundle = it.data?.extras?.getBundle("BUNDLE")
                val objtsRet = bundle?.getSerializable("retObjects", Array<ModelAuto>::class.java)
                if (objtsRet != null) {
                    carObjects = objtsRet.toMutableList()
                }
                recreate()
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        if (carObjects.isEmpty())
            carObjects = ModelAuto.generate100()
        findViewById<ListView>(R.id.carlist).adapter =
            ModelListAdapter(this, R.layout.listitem,  carObjects)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuitem_filter) {
            val intent = Intent(this, SearchFilter::class.java)
            val bundle = Bundle()
            bundle.putSerializable("objects", carObjects.toTypedArray())
            intent.putExtra("BUNDLE", bundle )


            result.launch(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}