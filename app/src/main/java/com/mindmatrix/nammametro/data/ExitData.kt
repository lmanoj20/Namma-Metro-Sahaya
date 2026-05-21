package com.mindmatrix.nammametro.data

/**
 * Exit Finder data (FR-04). Bundled per station so it works offline.
 * Each station lists its named exits and the major destinations served.
 */
data class Exit(
    val gateLabel: String,
    val destinations: List<String>,
    val landmark: String
)

object ExitData {

    private val data: Map<String, List<Exit>> = mapOf(
        "majestic" to listOf(
            Exit("Gate 1", listOf("KSRTC Bus Stand"), "Bus terminus — long distance"),
            Exit("Gate 2", listOf("BMTC City Bus Stand"), "Local Bengaluru city buses"),
            Exit("Gate 3", listOf("City Railway Station"), "Outstation trains"),
            Exit("Gate 4", listOf("SBS Road", "Subhash Nagar"), "Hotels, food court")
        ),
        "p09" to listOf(
            Exit("Gate A", listOf("Brigade Road", "Church Street"), "Shopping & restaurants"),
            Exit("Gate B", listOf("Anil Kumble Circle"), "Police HQ side"),
            Exit("Gate C", listOf("Mayo Hall"), "Heritage building"),
            Exit("Gate D", listOf("Trinity-side connector"), "Walk to Trinity station")
        ),
        "p10" to listOf(
            Exit("Gate 1", listOf("Cubbon Park (Main)"), "Park entry & jogging track"),
            Exit("Gate 2", listOf("High Court"), "Karnataka High Court"),
            Exit("Gate 3", listOf("Press Club"), "Cubbon Park library side")
        ),
        "p11" to listOf(
            Exit("Gate 1", listOf("Vidhana Soudha"), "State legislature"),
            Exit("Gate 2", listOf("Vikasa Soudha"), "Government offices"),
            Exit("Gate 3", listOf("Raj Bhavan"), "Governor's residence side")
        ),
        "g14" to listOf(
            Exit("Gate 1", listOf("Lalbagh West Gate"), "Botanical Garden main entry"),
            Exit("Gate 2", listOf("Double Road"), "KH Road bus stop"),
            Exit("Gate 3", listOf("Lalbagh East Gate"), "Glass House side")
        ),
        "g13" to listOf(
            Exit("Gate 1", listOf("National College"), "College campus"),
            Exit("Gate 2", listOf("Basavanagudi"), "Bull Temple Road"),
            Exit("Gate 3", listOf("NIMHANS"), "Hospital — autorickshaw stand at Gate 3")
        ),
        "p06" to listOf(
            Exit("Gate 1", listOf("CMH Road"), "Restaurants & shopping"),
            Exit("Gate 2", listOf("100 Feet Road"), "Indiranagar main street"),
            Exit("Gate 3", listOf("Domlur"), "ORR connector")
        ),
        "p04" to listOf(
            Exit("Gate 1", listOf("KR Puram Railway Station"), "Outstation trains"),
            Exit("Gate 2", listOf("Old Madras Road"), "BMTC buses to Whitefield"),
            Exit("Gate 3", listOf("Tin Factory"), "Auto stand & food street")
        ),
        "g05" to listOf(
            Exit("Gate 1", listOf("Yeshwantpur Railway Station"), "Long-distance trains"),
            Exit("Gate 2", listOf("Yeshwantpur Market"), "Wholesale market"),
            Exit("Gate 3", listOf("Tumkur Road"), "Bus stop")
        ),
        "g16" to listOf(
            Exit("Gate 1", listOf("Jayanagar 4th Block"), "Shopping complex"),
            Exit("Gate 2", listOf("Jayanagar Bus Stand"), "BMTC buses"),
            Exit("Gate 3", listOf("Madhavan Park"), "Walking park")
        )
    )

    fun forStation(id: String): List<Exit> = data[id] ?: listOf(
        Exit("Gate 1", listOf("Main road exit"), "Look for the exit board with arrow"),
        Exit("Gate 2", listOf("Side road exit"), "Less crowded — use during peak")
    )
}
