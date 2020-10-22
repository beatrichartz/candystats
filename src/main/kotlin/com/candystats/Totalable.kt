package com.candystats

interface Totalable {
    fun totals(): Totals
}

interface Filterable<ItemType> {
    fun filterBy(candyKind: CandyKind?): ItemType?
}
