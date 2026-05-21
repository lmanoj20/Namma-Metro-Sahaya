package com.mindmatrix.nammametro.data

import com.mindmatrix.nammametro.util.FareCalculator
import java.util.ArrayDeque

/**
 * Graph-based shortest path (NFR-05). BFS over the unweighted station graph.
 * Returns the sequence of stations from source to destination and detects
 * whether the route requires changing lines at an interchange.
 */
object Pathfinder {

    fun findRoute(fromId: String, toId: String): Route? {
        if (fromId == toId) return null
        MetroNetwork.findById(fromId) ?: return null
        MetroNetwork.findById(toId) ?: return null

        val parent = mutableMapOf<String, String?>()
        parent[fromId] = null

        val queue = ArrayDeque<String>()
        queue.add(fromId)
        var found = false
        while (queue.isNotEmpty()) {
            val cur = queue.poll() ?: continue
            if (cur == toId) { found = true; break }
            val neighbors = MetroNetwork.adjacency[cur].orEmpty()
            for (n in neighbors) {
                if (!parent.containsKey(n)) {
                    parent[n] = cur
                    queue.add(n)
                }
            }
        }
        if (!found) return null

        // Reconstruct path
        val path = mutableListOf<Station>()
        var cur: String? = toId
        while (cur != null) {
            MetroNetwork.findById(cur)?.let { path.add(0, it) }
            cur = parent[cur]
        }

        // Detect interchange: line change while traversing
        var interchange: Station? = null
        var hasInterchange = false
        var lastLine: Line? = null
        for (s in path) {
            val effectiveLine = effectiveLineFor(s, path, lastLine)
            if ((lastLine != null) && (effectiveLine != lastLine)) {
                hasInterchange = true
                interchange = MetroNetwork.majestic
            }
            lastLine = effectiveLine
        }

        // Build route steps
        val steps = path.mapIndexed { idx, station ->
            val isI = station.isInterchange && hasInterchange && idx != 0 && idx != path.lastIndex
            RouteStep(
                station = station,
                isInterchange = isI,
                switchToLine = if (isI && idx + 1 < path.size) effectiveLineFor(path[idx + 1], path, null) else null,
            )
        }

        val totalStops = path.size - 1
        return Route(
            steps = steps,
            totalStops = totalStops,
            fare = FareCalculator.calculate(totalStops),
            travelTimeMinutes = FareCalculator.travelTime(totalStops),
            hasInterchange = hasInterchange,
            interchangeStation = interchange
        )
    }

    private fun effectiveLineFor(
        station: Station,
        path: List<Station>,
        lastLine: Line?
    ): Line {
        // Majestic is shared between Purple and Green. Resolve from neighboring path context.
        if (!station.isInterchange) return station.line
        val idx = path.indexOf(station)
        val neighbor = when {
            idx > 0 -> path[idx - 1]
            idx < path.lastIndex -> path[idx + 1]
            else -> return station.line
        }
        return if (!neighbor.isInterchange) neighbor.line else (lastLine ?: station.line)
    }
}
