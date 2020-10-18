package com.candystats

import com.candystats.CandyFixtures.caramels
import com.candystats.CandyFixtures.caramilk
import com.candystats.CandyFixtures.jellyBeans
import com.candystats.CandyFixtures.licoriceTwists
import com.candystats.CandyFixtures.lolly
import com.candystats.CandyFixtures.mars
import com.candystats.CandyFixtures.marshmallow
import com.candystats.CandyFixtures.snakes
import com.candystats.CandyFixtures.softLicorice
import com.candystats.CandyFixtures.timtam
import com.candystats.CandyFixtures.toblerone
import com.candystats.CandyFixtures.twix
import com.candystats.CandyKind.*
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.data.Percentage
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject

@Client("/")
interface CandyStatsClient {
    @Get("/totals")
    fun totalsFor(@QueryValue boxIds: List<Long>): Single<HttpResponse<TotalsResponse>>

    @Get("/totals")
    fun totalsFor(@QueryValue boxIds: List<Long>, @QueryValue kind: CandyKind): Single<HttpResponse<TotalsResponse>>

    @Get("/totals")
    fun totalsFor(@QueryValue boxIds: List<Long>, @QueryValue candyIds: List<Long>): Single<HttpResponse<TotalsResponse>>
}

@MicronautTest(transactional = false)
class CandyStatsTest {

    private lateinit var looseCandy: List<Candy>
    private lateinit var firstBox: Box
    private lateinit var secondBox: Box
    private lateinit var thirdBox: Box

    @Inject
    lateinit var client: CandyStatsClient

    @Inject
    lateinit var boxRepository: BoxRepository

    @Inject
    lateinit var candyRepository: CandyRepository

    @BeforeEach
    fun setUp() {
        candyRepository.deleteAll()
        boxRepository.deleteAll()

        firstBox = box {
            candy(snakes)
            box {
                candy(toblerone, twix, mars)
            }
            box {
                candy(lolly, lolly, lolly, marshmallow)
                box {
                    candy(toblerone, twix, mars)
                }
                box {
                    candy(lolly, lolly, lolly, marshmallow)
                }
            }
        }

        secondBox = box {
            candy(toblerone, timtam, jellyBeans)
            box {
                candy(caramilk, licoriceTwists, mars, timtam)
            }
        }

        thirdBox = box {
            candy(caramels, snakes, softLicorice)
            box {
                candy(toblerone, timtam, twix, mars)

                box {
                    candy(lolly, licoriceTwists, mars, timtam)
                }
                box {
                    candy(toblerone, timtam, twix, mars)
                }
            }
            box {
                candy(lolly, twix, mars)
                box {
                    candy(snakes, snakes)
                }
            }
            box {
                candy(toblerone)

                box {
                    candy(jellyBeans, jellyBeans, jellyBeans, jellyBeans)
                }
            }
        }

        looseCandy = candyRepository.saveAll(listOf(
            toblerone, mars, twix, timtam, jellyBeans
        )).toList()
    }

    private val tolerance = Percentage.withPercentage(0.5)

    @Test
    fun `I can get totals for a box`() {
        val response = client.totalsFor(listOf(firstBox.id)).blockingGet()

        assertThat(response.status).isEqualTo(HttpStatus.OK)
        val totals = response.body()?.totals

        assertThat(totals?.calories).isEqualTo(1099)
        assertThat(totals?.sugar).isCloseTo(171.8, tolerance)
        assertThat(totals?.fat).isCloseTo(35.7, tolerance)
        assertThat(totals?.protein).isCloseTo(8.8, tolerance)
    }

    @Test
    fun `I can get totals for kinds of candy in a box`() {
        val response = client.totalsFor(listOf(thirdBox.id), CHOCOLATE).blockingGet()

        assertThat(response.status).isEqualTo(HttpStatus.OK)
        val totals = response.body()?.totals

        assertThat(totals?.calories).isEqualTo(1434)
        assertThat(totals?.sugar).isCloseTo(185.4, tolerance)
        assertThat(totals?.fat).isCloseTo(69.7, tolerance)
        assertThat(totals?.protein).isCloseTo(13.3, tolerance)
    }

    @Test
    fun `I can get totals for multiple boxes and loose candy`() {
        val response = client.totalsFor(
            listOf(thirdBox.id, *firstBox.innerBoxes.map { it.id }.toTypedArray()),
            looseCandy.map { it.id }
        ).blockingGet()

        assertThat(response.status).isEqualTo(HttpStatus.OK)
        val totals = response.body()?.totals

        assertThat(totals?.calories).isEqualTo(4064)
        assertThat(totals?.sugar).isCloseTo(568.8, tolerance)
        assertThat(totals?.fat).isCloseTo(157.4, tolerance)
        assertThat(totals?.protein).isCloseTo(32.7, tolerance)
    }

    private data class BoxBuilder(
        val buildBox: Box,
        var innerBoxes: MutableSet<Box> = mutableSetOf(),
        var candies: Set<Candy> = setOf()
    ) {
        fun build(): Box {
            buildBox.innerBoxes = innerBoxes.map { it.apply { box = buildBox } }.toSet()
            buildBox.candies = candies.map { it.apply { box = buildBox } }.toSet()
            return buildBox
        }
    }

    private fun box(init: BoxBuilder.() -> Unit): Box {
        val boxBuilder = BoxBuilder(boxRepository.save(Box()))
        boxBuilder.init()
        return boxRepository.update(boxBuilder.build())
    }

    private fun BoxBuilder.candy(vararg looseCandy: Candy) {
        candies = looseCandy.map { candyRepository.save(it) }.toSet()
    }

    private fun BoxBuilder.box(init: BoxBuilder.() -> Unit) {
        val innerBox = BoxBuilder(boxRepository.save(Box()))
        innerBox.init()
        innerBoxes.add(innerBox.build())
    }
}

