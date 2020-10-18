package com.candystats

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository


@Repository
interface CandyRepository: CrudRepository<Candy, Long>
