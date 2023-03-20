package com.example.steam_guessing_game.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReviewResults (
        val success: Int,
        val query_summary: QuerySummary,
        val reviews: List<Review>
)

@JsonClass(generateAdapter = true)
data class QuerySummary(
        val num_reviews: Int,
        val review_score: Int,
        val total_reviews: Long
) : java.io.Serializable

@JsonClass(generateAdapter = true)
data class Review(
        val review: String
) : java.io.Serializable
