package agency.five.codebase.android.movieapp.ui.movieDetails.mapper

import agency.five.codebase.android.movieapp.model.MovieDetails
import agency.five.codebase.android.movieapp.ui.component.CrewItemViewState
import agency.five.codebase.android.movieapp.ui.movieDetails.ActorViewState
import agency.five.codebase.android.movieapp.ui.movieDetails.CrewmanViewState
import agency.five.codebase.android.movieapp.ui.movieDetails.MovieDetailsViewState

class MovieDetailsMapperImpl : MovieDetailsMapper {
    override fun toMovieDetailsViewState(movieDetails: MovieDetails) =
        MovieDetailsViewState(movieDetails.movie.id,
            movieDetails.movie.imageUrl.toString(),
            movieDetails.voteAverage,
            movieDetails.movie.title,
            movieDetails.movie.overview,
            movieDetails.movie.isFavorite,
            crewmanViewStates(movieDetails),
            actorViewStates(movieDetails)
        )

    private fun crewmanViewStates(movieDetails: MovieDetails) =
        movieDetails.crew.map { crewman ->
            CrewmanViewState(crewman.id,
                crewman.name,
                crewman.job)
        }

    private fun actorViewStates(movieDetails: MovieDetails) =
        movieDetails.cast.map { actor ->
            ActorViewState(actor.id,
                actor.name,
                actor.character,
                actor.imageUrl)
        }
}
