package com.example.hw_activities_mult

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display.Mode
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    var carObjects = mutableListOf<ModelAuto>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        if (carObjects.isEmpty())
            carObjects = ModelAuto.generate100()
        findViewById<ListView>(R.id.carlist).adapter =
            ModelListAdapter(this, R.layout.listitem, carObjects)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuitem_filter) {
            val intent: Intent = Intent(this, MainActivity::class.java)
            val array = carObjects
            intent.putExtra("objects", carObjects.toTypedArray())

            val result =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                    if (it.resultCode == Activity.RESULT_OK) {
                        carObjects =
                            (it.data?.extras?.get("retObject") as Array<ModelAuto>).toMutableList()
                        recreate()
                    }
                }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}