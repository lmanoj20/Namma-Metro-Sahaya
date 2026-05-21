package com.mindmatrix.nammametro.data

enum class Line(val displayName: String, val colorHex: String) {
    PURPLE("Purple Line", "#7B2CBF"),
    GREEN("Green Line", "#06A77D")
}

data class Station(
    val id: String,
    val name: String,
    val nameKn: String,
    val line: Line,
    val isInterchange: Boolean = false,
)

data class RouteStep(
    val station: Station,
    val isInterchange: Boolean,
    val switchToLine: Line? = null
)

data class Route(
    val steps: List<RouteStep>,
    val totalStops: Int,
    val fare: Int,
    val travelTimeMinutes: Int,
    val hasInterchange: Boolean,
    val interchangeStation: Station?
)
