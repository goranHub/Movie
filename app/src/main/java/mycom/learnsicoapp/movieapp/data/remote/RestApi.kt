package mycom.learnsicoapp.movieapp.data.remote

import io.reactivex.Observable
import mycom.learnsicoapp.movieapp.data.remote.response.movie.MovieResponse
import mycom.learnsicoapp.movieapp.data.remote.response.movie.Movie
import mycom.learnsicoapp.movieapp.data.remote.response.multi.Multi
import mycom.learnsicoapp.movieapp.data.remote.response.tvShow.TvResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author ll4
 * @date 1/7/2021
 */
interface RestApi {

    @GET("movie/top_rated")
    fun getTopRated(
        @Query("api_key") apiKey: String?,
        @Query("page") page: String?
    ): Observable<Movie>

    @GET("movie/popular")
    fun getPopular(
        @Query("api_key") apiKey: String?,
        @Query("page") page: String?
    ): Observable<Movie>

    @GET("search/multi?")
    fun getMulti(
        @Query(value = "api_key") apiKey: String,
        @Query(value = "query") searchTitle: String
    ) : Observable<Multi>

    @GET("movie/{id}?&append_to_response=credits")
    fun getCrewByMovieId(
        @Path("id") id: Long,
        @Query("api_key") apiKey: String
    ): Observable<MovieResponse>

    @GET("tv/{id}")
    fun getTvShowById(
        @Path("id") id: Long,
        @Query("api_key") apiKey: String
    ): Observable<TvResponse>

    @GET("movie/{id}")
    fun getMovieByID(
        @Path("id") id: Long,
        @Query("api_key") apiKey: String
    ): Observable<MovieResponse>
}