package au.edu.jcu.assessment.utilityapp.data.model

data class City(
    val id: String,
    val name: String,
    val country: String,
    val latitude: Double,
    val longitude: Double,
    val description: String = "",
) {
    companion object {
        val DEFAULT_CITY = City(
            id = "townsville",
            name = "Townsville (JCU)",
            country = "Australia",
            latitude = -19.2589,
            longitude = 146.8169,
            description = "James Cook University Main Campus",
        )

        val PRESET_CITIES = listOf(
            DEFAULT_CITY,
            City("cairns", "Cairns (JCU)", "Australia", -16.9186, 145.7781, "JCU Nguma-bada Campus"),
            City("brisbane", "Brisbane", "Australia", -27.4705, 153.0260, "Queensland Capital"),
            City("sydney", "Sydney", "Australia", -33.8688, 151.2093, "Harbour City"),
            City("melbourne", "Melbourne", "Australia", -37.8136, 144.9631, "Cultural Capital"),
            City("singapore", "Singapore", "Singapore", 1.3521, 103.8198, "JCU Singapore Campus"),
            City("tokyo", "Tokyo", "Japan", 35.6762, 139.6503, "Greater Tokyo Area"),
            City("london", "London", "United Kingdom", 51.5074, -0.1278, "United Kingdom Capital"),
            City("new_york", "New York", "United States", 40.7128, -74.0060, "Big Apple"),
        )
    }
}
