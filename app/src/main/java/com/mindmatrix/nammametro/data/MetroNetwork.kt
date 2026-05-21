package com.mindmatrix.nammametro.data

/**
 * In-memory graph of Namma Metro. Nodes = Stations, Edges = adjacency on a line
 * plus the interchange edge at Majestic (Nadaprabhu Kempegowda Station).
 * Data is bundled so the app works fully offline (FR-07, NFR-04).
 */
object MetroNetwork {

    // Purple line — east to west (Whitefield → Challaghatta), trimmed major stops
    val purpleStations: List<Station> = listOf(
        Station("p01", "Whitefield (Kadugodi)", "ವೈಟ್‌ಫೀಲ್ಡ್ (ಕಾಡುಗೋಡಿ)", Line.PURPLE),
        Station("p02", "Hoodi", "ಹೂಡಿ", Line.PURPLE),
        Station("p03", "Mahadevapura", "ಮಹದೇವಪುರ", Line.PURPLE),
        Station("p04", "Krishnarajapuram", "ಕೃಷ್ಣರಾಜಪುರಂ", Line.PURPLE),
        Station("p05", "Baiyappanahalli", "ಬೈಯಪ್ಪನಹಳ್ಳಿ", Line.PURPLE),
        Station("p06", "Indiranagar", "ಇಂದಿರಾನಗರ", Line.PURPLE),
        Station("p07", "Halasuru", "ಹಲಸೂರು", Line.PURPLE),
        Station("p08", "Trinity", "ಟ್ರಿನಿಟಿ", Line.PURPLE),
        Station("p09", "MG Road", "ಎಂ.ಜಿ ರಸ್ತೆ", Line.PURPLE),
        Station("p10", "Cubbon Park", "ಕಬ್ಬನ್ ಪಾರ್ಕ್", Line.PURPLE),
        Station("p11", "Vidhana Soudha", "ವಿಧಾನ ಸೌಧ", Line.PURPLE),
        Station("p12", "Sir M Visvesvaraya Station", "ಸರ್ ಎಂ. ವಿಶ್ವೇಶ್ವರಯ್ಯ", Line.PURPLE),
        Station("majestic", "Majestic (Kempegowda)", "ಮೆಜೆಸ್ಟಿಕ್ (ಕೆಂಪೇಗೌಡ)", Line.PURPLE, isInterchange = true),
        Station("p14", "City Railway Station", "ಸಿಟಿ ರೈಲ್ವೇ ನಿಲ್ದಾಣ", Line.PURPLE),
        Station("p15", "Vijayanagar", "ವಿಜಯನಗರ", Line.PURPLE),
        Station("p16", "Mysuru Road", "ಮೈಸೂರು ರಸ್ತೆ", Line.PURPLE),
        Station("p17", "Nayandahalli", "ನಾಯಂದಹಳ್ಳಿ", Line.PURPLE),
        Station("p18", "Kengeri", "ಕೆಂಗೇರಿ", Line.PURPLE),
        Station("p19", "Challaghatta", "ಚಲ್ಲಘಟ್ಟ", Line.PURPLE),
    )

    // Green line — north to south (Madavara → Silk Institute), trimmed major stops
    val greenStations: List<Station> = listOf(
        Station("g01", "Madavara", "ಮಾದವಾರ", Line.GREEN),
        Station("g02", "Nagasandra", "ನಾಗಸಂದ್ರ", Line.GREEN),
        Station("g03", "Jalahalli", "ಜಲಹಳ್ಳಿ", Line.GREEN),
        Station("g04", "Peenya", "ಪೀಣ್ಯ", Line.GREEN),
        Station("g05", "Yeshwantpur", "ಯಶವಂತಪುರ", Line.GREEN),
        Station("g06", "Mahalakshmi", "ಮಹಾಲಕ್ಷ್ಮಿ", Line.GREEN),
        Station("g07", "Rajajinagar", "ರಾಜಾಜಿನಗರ", Line.GREEN),
        Station("g08", "Srirampura", "ಶ್ರೀರಾಮಪುರ", Line.GREEN),
        Station("g09", "Mantri Sampige Road", "ಮಂತ್ರಿ ಸಂಪಿಗೆ ರಸ್ತೆ", Line.GREEN),
        // Majestic is the same physical interchange node; the Pathfinder treats it as a single station.
        Station("g11", "Chickpete", "ಚಿಕ್ಪೇಟೆ", Line.GREEN),
        Station("g12", "KR Market", "ಕೆ.ಆರ್ ಮಾರುಕಟ್ಟೆ", Line.GREEN),
        Station("g13", "National College", "ನ್ಯಾಷನಲ್ ಕಾಲೇಜು", Line.GREEN),
        Station("g14", "Lalbagh", "ಲಾಲ್ಬಾಗ್", Line.GREEN),
        Station("g15", "South End Circle", "ಸೌತ್ ಎಂಡ್ ವೃತ್ತ", Line.GREEN),
        Station("g16", "Jayanagar", "ಜಯನಗರ", Line.GREEN),
        Station("g17", "RV Road", "ಆರ್.ವಿ ರಸ್ತೆ", Line.GREEN),
        Station("g18", "Banashankari", "ಬನಶಂಕರಿ", Line.GREEN),
        Station("g19", "Jayaprakash Nagar", "ಜಯಪ್ರಕಾಶ್ ನಗರ", Line.GREEN),
        Station("g20", "Yelachenahalli", "ಎಲಚೇನಹಳ್ಳಿ", Line.GREEN),
        Station("g21", "Silk Institute", "ರೇಷ್ಮೆ ಸಂಸ್ಥೆ", Line.GREEN)
    )

    val majestic: Station = purpleStations.first { it.id == "majestic" }

    /** All unique stations (Majestic appears once as the shared interchange). */
    val allStations: List<Station> by lazy {
        (purpleStations + greenStations).sortedBy { it.name }
    }

    /** Adjacency map keyed by station id. Built from the two line orderings. */
    val adjacency: Map<String, List<String>> by lazy {
        val map = mutableMapOf<String, MutableList<String>>()

        // Purple line edges
        for (i in purpleStations.indices) {
            val s = purpleStations[i]
            map.getOrPut(s.id) { mutableListOf() }
            if (i > 0) map[s.id]!!.add(purpleStations[i - 1].id)
            if (i < (purpleStations.size - 1)) map[s.id]!!.add(purpleStations[i + 1].id)
        }

        // Green line edges — splice Majestic into the green sequence between g09 and g11
        val greenWithMajestic: List<Station> = greenStations.toMutableList().apply {
            val idx = indexOfFirst { it.id == "g11" }
            add(idx, majestic)
        }
        for (i in greenWithMajestic.indices) {
            val s = greenWithMajestic[i]
            map.getOrPut(s.id) { mutableListOf() }
            if (i > 0 && map[s.id]!!.none { it == greenWithMajestic[i - 1].id }) {
                map[s.id]!!.add(greenWithMajestic[i - 1].id)
            }
            if (i < greenWithMajestic.size - 1 && map[s.id]!!.none { it == greenWithMajestic[i + 1].id }) {
                map[s.id]!!.add(greenWithMajestic[i + 1].id)
            }
        }

        map
    }

    fun findById(id: String): Station? = allStations.firstOrNull { it.id == id }
    fun findByName(name: String): Station? =
        allStations.firstOrNull { it.name.equals(name, ignoreCase = true) }
}
