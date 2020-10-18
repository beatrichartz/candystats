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
)

enum class CandyKind {
    CHOCOLATE,
    GUMMY,
    CARAMEL,
    HARD_CANDY,
    LICORICE
}
