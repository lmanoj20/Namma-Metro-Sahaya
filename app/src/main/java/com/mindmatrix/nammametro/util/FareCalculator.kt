package com.mindmatrix.nammametro.util

/**
 * Fare and travel-time estimates (FR-05). Tiered fare scale roughly matching
 * the published Namma Metro slabs; travel time is stops × per-stop minutes.
 */
object FareCalculator {

    private val fareSlabs = listOf(
        (0..2) to 10,
        (3..4) to 20,
        (5..7) to 30,
        (8..10) to 40,
        (11..14) to 50,
        (15..18) to 55,
        (19..23) to 60,
    )

    fun calculate(stops: Int): Int {
        for ((range, fare) in fareSlabs) {
            if (stops in range) return fare
        }
        return 70
    }

    fun travelTime(stops: Int): Int = (stops * 2.2).toInt().coerceAtLeast(2)
}
