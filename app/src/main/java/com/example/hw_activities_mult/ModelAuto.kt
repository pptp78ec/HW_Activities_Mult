package com.example.hw_activities_mult

import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDate

data class ModelAuto
    (
    var brand: String,
    var model: String,
    var year: LocalDate,
    var description: String,
    var cost: Int,
    val image: Int
) {
    companion object Factory {
        fun build(): ModelAuto {
            val models = arrayOf(
                "Supra",
                "Exacto",
                "Linebacker",
                "Peak",
                "Sunlight",
                "Evanesco",
                "Alpine",
                "Solarite",
                "Outlander",
                "Roadeater"
            )
            val brands = arrayOf(
                "Volkswagen",
                "Mercedes-Benz",
                "Dacia",
                "Hyundai",
                "Ford",
                "Renault",
                "Honda",
                "Volvo"
            )
            val description = arrayOf(
                "A sleek and sporty coupe with a powerful engine and a leather interior. This car is perfect for those who love speed and style.",
                "A spacious and comfortable sedan with a smooth ride and a high-tech dashboard. This car is ideal for families or business travelers who need reliability and convenience.",
                "A rugged and versatile SUV with a four-wheel drive and a spacious trunk. This car is great for adventurers who enjoy exploring off-road trails or camping trips.",
                "A compact and eco-friendly hatchback with a hybrid engine and a low fuel consumption. This car is suitable for those who care about the environment and saving money on gas.",
                "A classic and elegant convertible with a soft top and a vintage design. This car is a dream for those who appreciate nostalgia and romance.",
                "A modern and stylish crossover with a high ground clearance and a panoramic sunroof. This car is a good choice for those who want the best of both worlds: the agility of a sedan and the space of an SUV.",
                "A reliable and practical pickup truck with a powerful towing capacity and a durable bed. This car is a must-have for those who work hard or need to haul heavy loads.",
                "A luxurious and sophisticated limousine with a chauffeur and a minibar. This car is the ultimate indulgence for those who want to travel in style and comfort.",
                "A fun and quirky minivan with a colorful exterior and a spacious interior. This car is a blast for those who like to stand out from the crowd or travel with friends.",
                "A futuristic and innovative electric car with a sleek design and a long battery life. This car is the future for those who want to be ahead of the curve and enjoy cutting-edge technology."
            )
            val images = arrayOf(
                R.drawable.car1,
                R.drawable.car2,
                R.drawable.car3,
                R.drawable.car4,
                R.drawable.car5,
                R.drawable.car6,
                R.drawable.car7,
                R.drawable.car8,
                R.drawable.car9,
                R.drawable.car10
            )
            return ModelAuto(
                brands.random(),
                models.random(),
                LocalDate.of((1990..2023).random(), (1..12).random(), (1..30).random()),
                description.random(),
                (2000..30000).random(),
                images.random()
            )
        }
        fun generate100(): MutableList<ModelAuto>{
            val list = mutableListOf<ModelAuto>()
            for(x in 1..100){
                list.add(build())
            }
            return list
        }
    }

}

