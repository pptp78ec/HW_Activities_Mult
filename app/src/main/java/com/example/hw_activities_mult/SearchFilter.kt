package com.example.hw_activities_mult

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Spinner
import java.util.Locale

class SearchFilter : AppCompatActivity() {

    val objectsGot: MutableList<ModelAuto>? = null

    private var model: AutoCompleteTextView? = null
    private var brand: AutoCompleteTextView? = null
    private var price_from: Spinner? = null
    private var price_to: Spinner? = null
    private var year_from: Spinner? = null
    private var year_to: Spinner? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_filter)
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

    class YearsAdapter(val context: Context, val resource: Int, years: MutableList<Int>) :
        ArrayAdapter<Int>(context, resource, years) {
        companion object Factory {
            fun build(context: Context, resource: Int): YearsAdapter {
                return YearsAdapter(context, resource, (1990..2023).toMutableList())
            }
        }
    }

    class PriceAdapter(val context: Context, val resource: Int, years: MutableList<Int>) :
        ArrayAdapter<Int>(context, resource, years) {
        companion object Factory {
            fun build(context: Context, resource: Int): PriceAdapter {
                return PriceAdapter(context, resource, (2000..30000).toMutableList())
            }
        }
    }
}