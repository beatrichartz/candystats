package com.candystats

object CandyFixtures {
    val toblerone
        get() = Candy(
            name = "Toblerone",
            kind = CandyKind.CHOCOLATE,
            calories = 132,
            fat = 7.3,
            sugar = 15.0,
            protein = 1.4
        )

    val mars
        get() = Candy(
            name = "Mars",
            kind = CandyKind.CHOCOLATE,
            calories = 84,
            fat = 3.1,
            sugar = 12.9,
            protein = 0.7
        )

    val twix
        get() = Candy(
            name = "Twix",
            kind = CandyKind.CHOCOLATE,
            calories = 139,
            fat = 6.9,
            sugar = 17.8,
            protein = 1.2
        )

    val timtam
        get() = Candy(
            name = "TimTam",
            kind = CandyKind.CHOCOLATE,
            calories = 95,
            fat = 4.9,
            sugar = 11.8,
            protein = 0.9
        )

    val marshmallow
        get() = Candy(
            name = "Marshmallow",
            kind = CandyKind.GUMMY,
            calories = 81,
            fat = 0.0,
            sugar = 19.3,
            protein = 0.6
        )

    val snakes
        get() = Candy(
            name = "Snakes",
            kind = CandyKind.GUMMY,
            calories = 83,
            fat = 1.0,
            sugar = 19.2,
            protein = 1.0
        )

    val jellyBeans
        get() = Candy(
            name = "Jelly Beans",
            kind = CandyKind.GUMMY,
            calories = 41,
            fat = 0.01,
            sugar = 7.7,
            protein = 0.0
        )

    val lolly
        get() = Candy(
            name = "Lolly",
            kind = CandyKind.HARD_CANDY,
            calories = 24,
            fat = 0.01,
            sugar = 3.77,
            protein = 0.0
        )

    val caramels
        get() = Candy(
            name = "Caramels",
            kind = CandyKind.CARAMEL,
            calories = 497,
            fat = 25.8,
            sugar = 51.2,
            protein = 2.4
        )

    val caramilk
        get() = Candy(
            name = "Caramilk",
            kind = CandyKind.CARAMEL,
            calories = 139,
            fat = 8.1,
            sugar = 14.7,
            protein = 1.8
        )

    val licoriceTwists
        get() = Candy(
            name = "Licorice Twists",
            kind = CandyKind.LICORICE,
            calories = 84,
            fat = 1.0,
            sugar = 9.0,
            protein = 1.0
        )

    val softLicorice
        get() = Candy(
            name = "Soft Licorice",
            kind = CandyKind.LICORICE,
            calories = 81,
            fat = 1.0,
            sugar = 9.4,
            protein = 1.0
        )
}
