package com.candystats

import io.micronaut.core.annotation.Introspected
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
@Introspected
class Box(
    @Id
    @GeneratedValue
    var id: Long = 0,
    @ManyToOne
    @JoinColumn(name = "box_id")
    var box: Box? = null,
    @OneToMany(mappedBy = "box", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    var innerBoxes: Set<Box> = emptySet(),
    @OneToMany(mappedBy = "box", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    var candies: Set<Candy> = emptySet()
) : Totalable, Filterable<Box> {
    override fun totals(): Totals {
        return (innerBoxes + candies).fold(ZeroTotals) { totals, candy ->
            totals + candy.totals()
        }
    }

    override fun filterBy(candyKind: CandyKind?): Box? {
        candies = candies.mapNotNull { it.filterBy(candyKind) }.toSet()
        innerBoxes = innerBoxes.mapNotNull { it.filterBy(candyKind) }.toSet()
        return this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Box

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Box(id=$id)"
    }
}
