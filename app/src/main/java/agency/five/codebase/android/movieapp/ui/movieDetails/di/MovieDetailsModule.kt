package agency.five.codebase.android.movieapp.ui.movieDetails.di

import agency.five.codebase.android.movieapp.ui.movieDetails.DetailsScreenViewModel
import agency.five.codebase.android.movieapp.ui.movieDetails.mapper.MovieDetailsMapper
import agency.five.codebase.android.movieapp.ui.movieDetails.mapper.MovieDetailsMapperImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailsScreenModule = module {
    viewModel { (movieId: Int) ->
        DetailsScreenViewModel(
            movieRepository = get(),
            detailsMapper = get(),
            movieId = movieId
        )
    }
    single<MovieDetailsMapper> { MovieDetailsMapperImpl() }
}
