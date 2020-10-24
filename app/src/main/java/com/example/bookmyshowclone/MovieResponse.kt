package com.example.bookmyshowclone

import androidx.room.*
@Entity(tableName = "tbl_movie_data")
data class MovieResponse(
    @PrimaryKey
    val page :Int=1,
    @ColumnInfo(name = "movie_response")
    @TypeConverters(MovieTypeConverter::class)
    val results: List<Movie>
)