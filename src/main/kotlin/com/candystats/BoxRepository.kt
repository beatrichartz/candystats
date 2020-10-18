package com.candystats

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface BoxRepository: CrudRepository<Box, Long>
