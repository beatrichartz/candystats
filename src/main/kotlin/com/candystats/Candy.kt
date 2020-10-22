package com.candystats

import io.micronaut.core.annotation.Introspected
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
@Introspected
data class Candy(
    @Id
    @GeneratedValue
    var id: Long = 0,
    @ManyToOne
    @JoinColumn(name = "box_id")
    var box: Box? = null,
    val name: String,
    val kind: CandyKind,
    val sugar: Double,
    val fat: Double,
    val protein: Double,
    val calories: Int
): Totalable, Filterable<Candy> {
    override fun totals(): Totals = Totals(sugar, fat, protein, calories)
    override fun filterBy(candyKind: CandyKind?): Candy? {
        return if (candyKind == null || candyKind == kind) this
        else null
    }
}

enum class CandyKind {
    CHOCOLATE,
    GUMMY,
    CARAMEL,
    HARD_CANDY,
    LICORICE
}
