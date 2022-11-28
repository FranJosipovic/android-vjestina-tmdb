package agency.five.codebase.android.movieapp.ui.movieDetails.mapper

import agency.five.codebase.android.movieapp.model.MovieDetails
import agency.five.codebase.android.movieapp.ui.movieDetails.ActorViewState
import agency.five.codebase.android.movieapp.ui.movieDetails.CrewmanViewState
import agency.five.codebase.android.movieapp.ui.movieDetails.MovieDetailsViewState

class MovieDetailsMapperImpl : MovieDetailsMapper {
    override fun toMovieDetailsViewState(movieDetails: MovieDetails) =
        MovieDetailsViewState(
            id = movieDetails.movie.id,
            imageUrl = movieDetails.movie.imageUrl.toString(),
            voteAverage = movieDetails.voteAverage,
            title = movieDetails.movie.title,
            overview = movieDetails.movie.overview,
            isFavorite = movieDetails.movie.isFavorite,
            crew = crewmanViewStates(movieDetails),
            cast = actorViewStates(movieDetails)
        )

    private fun crewmanViewStates(movieDetails: MovieDetails) =
        movieDetails.crew.map { crewman ->
            CrewmanViewState(
                id = crewman.id,
                name = crewman.name,
                job = crewman.job
            )
        }

    private fun actorViewStates(movieDetails: MovieDetails) =
        movieDetails.cast.map { actor ->
            ActorViewState(
                id = actor.id,
                name = actor.name,
                character = actor.character,
                imageUrl = actor.imageUrl
            )
        }
}
