package com.candystats

import io.micronaut.core.annotation.Introspected
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import javax.transaction.Transactional

@Controller("/")
class CandyStatsController(
    private val boxRepository: BoxRepository,
    private val candyRepository: CandyRepository
) {
    @Get("/totals")
    fun totals(@QueryValue boxIds: List<Long>, @QueryValue(defaultValue = "") candyIds: List<Long>, @QueryValue(defaultValue = "") kind: CandyKind?): HttpResponse<Any> {
        val boxes = boxIds.map { boxRepository.findById(it).get() }.toSet()
        val candies = candyIds.map { candyRepository.findById(it).get() }.toSet()
        return HttpResponse.ok(TotalsResponse(
            totals = Box(innerBoxes = boxes, candies = candies).filterBy(kind)!!.totals()
        ))
    }
}

data class Totals(
    val sugar: Double,
    val fat: Double,
    val protein: Double,
    val calories: Int
) {
    operator fun plus(candy: Candy): Totals {
        return Totals(
            sugar + candy.sugar,
            fat + candy.fat,
            protein + candy.protein,
            calories + candy.calories
        )
    }

    operator fun plus(other: Totals): Totals {
        return Totals(
            sugar + other.sugar,
            fat + other.fat,
            protein + other.protein,
            calories + other.calories
        )
    }
}

val ZeroTotals = Totals(0.0, 0.0, 0.0, 0)

@Introspected
data class TotalsResponse(
    val totals: Totals
)
