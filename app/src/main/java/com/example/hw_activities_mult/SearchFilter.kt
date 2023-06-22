package com.example.hw_activities_mult

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class SearchFilter : AppCompatActivity() {

    var objectsGot: MutableList<ModelAuto>? = null
    var objectsFiltered: MutableList<ModelAuto>?=null
    val instance = this

    private var model: AutoCompleteTextView? = null
    private var brand: AutoCompleteTextView? = null
    private var price_from: Spinner? = null
    private var price_to: Spinner? = null
    private var year_from: Spinner? = null
    private var year_to: Spinner? = null
    private var button_res: Button? = null


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_filter)
        button_res = findViewById(R.id.button_showresults)
        val bundle = intent.extras?.getBundle("BUNDLE")
        val objts = bundle?.getSerializable("objects", Array<ModelAuto>::class.java)
        objectsGot = objts?.toMutableList()
        model = findViewById(R.id.filter_model)
        brand = findViewById(R.id.filter_brand)
        price_from = findViewById(R.id.price_from)
        price_from?.adapter = PriceAdapter.build(this, R.id.price_from)
        price_to = findViewById(R.id.price_to)
        price_to?.adapter = PriceAdapter.build(this, R.id.price_to)
        year_from = findViewById(R.id.year_from)
        year_from?.adapter = YearsAdapter.build(this, R.id.year_from)
        year_to = findViewById(R.id.year_to)
        year_to?.adapter = YearsAdapter.build(this, R.id.year_to)
        addListeners()

    }


    fun filter(objects: MutableList<ModelAuto>): MutableList<ModelAuto> {
        var objectsHere = objects
        if (!model?.text.isNullOrEmpty()) {
            objectsHere = objects.filter { modelAuto ->
                modelAuto.model.lowercase(Locale.getDefault()).contains(
                    model?.text.toString().lowercase(
                        Locale.getDefault()
                    )
                )
            }.toMutableList()
        }
        if (!brand?.text.isNullOrEmpty()) {
            objectsHere = objects.filter { modelAuto ->
                modelAuto.brand.lowercase(Locale.getDefault()).contains(
                    brand?.text.toString()
                        .lowercase(Locale.getDefault())
                )
            }.toMutableList()
        }
        if (!price_from?.selectedItem?.toString()
                .isNullOrEmpty() && !price_to?.selectedItem?.toString().isNullOrEmpty()
        ) {
            objectsHere = objects.filter { modelAuto ->
                modelAuto.cost in (price_from?.selectedItem.toString()
                    .toInt()..price_to?.selectedItem.toString().toInt())
            }.toMutableList()
        }
        if (!year_from?.selectedItem?.toString()
                .isNullOrEmpty() && !year_to?.selectedItem?.toString().isNullOrEmpty()
        ) {
            objectsHere = objects.filter { modelAuto ->
                modelAuto.year.year in (year_from?.selectedItem.toString()
                    .toInt()..year_to?.selectedItem.toString().toInt())
            }.toMutableList()
        }

        return objectsHere
    }

    private fun filterToast() {
        if(!objectsGot.isNullOrEmpty()) {
            objectsFiltered = filter(objectsGot!!)
            Toast.makeText(instance, "Found results" + objectsFiltered?.size.toString(), Toast.LENGTH_LONG).show()
            button_res?.visibility = View.VISIBLE
        }
        else
            button_res?.visibility = View.INVISIBLE
    }
    private fun resClickListener() = View.OnClickListener {
        val data = Intent()
        val bundle = Bundle()
        bundle.putSerializable("retObjects", objectsFiltered?.toTypedArray())
        data.putExtra("BUNDLE", bundle)
        setResult(RESULT_OK,data)
        finish()
    }

    private fun itemSelectedListener() = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            filterToast()
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {

        }
    }
    private fun textChangedListener() = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            filterToast()
        }

        override fun afterTextChanged(s: Editable?) {

        }

    }
    private fun addListeners(){
        model?.addTextChangedListener(textChangedListener())
        brand?.addTextChangedListener(textChangedListener())
        price_to?.onItemSelectedListener = itemSelectedListener()
        price_from?.onItemSelectedListener = itemSelectedListener()
        year_from?.onItemSelectedListener = itemSelectedListener()
        year_to?.onItemSelectedListener = itemSelectedListener()
        button_res?.setOnClickListener(resClickListener())
    }


    class PriceAdapter(context: Context, resource: Int, objects: MutableList<Int>) :
        ArrayAdapter<Int>(context, resource, objects) {
        companion object Factory {
            fun build(context: Context, resource: Int): YearsAdapter {
                return YearsAdapter(context, resource, (2000..30000 step 1000).toMutableList())
            }
        }
    }
    class YearsAdapter(context: Context, resource: Int, objects: MutableList<Int>) :
        ArrayAdapter<Int>(context, resource, objects) {
        companion object Factory {
            fun build(context: Context, resource: Int): YearsAdapter {
                return YearsAdapter(context, resource, (1990..2023).toMutableList())
            }
        }
    }


}