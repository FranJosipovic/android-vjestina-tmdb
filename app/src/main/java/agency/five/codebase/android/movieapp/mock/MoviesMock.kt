package agency.five.codebase.android.movieapp.mock

import agency.five.codebase.android.movieapp.model.Actor
import agency.five.codebase.android.movieapp.model.Crewman
import agency.five.codebase.android.movieapp.model.Movie
import agency.five.codebase.android.movieapp.model.MovieDetails

object MoviesMock {

    fun getMoviesList(): List<Movie> = listOf(
        Movie(
            id = 1,
            title = "Venom: Let There Be Carnage",
            overview = "After finding a host body in investigative reporter Eddie Brock, the alien symbiote must face a new enemy, Carnage, the alter ego of serial killer Cletus Kasady.",
            imageUrl = "https://image.tmdb.org/t/p/w500/rjkmN1dniUHVYAtwuV3Tji7FsDO.jpg",
            isFavorite = false,
        ),
        Movie(
            id = 2,
            title = "Iron Man",
            overview = "After being held captive in an Afghan cave, billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.",
            imageUrl = "https://image.tmdb.org/t/p/w500/78lPtwv72eTNqFW9COBYI0dWDJa.jpg",
            isFavorite = true,
        ),
        Movie(
            id = 3,
            title = "Interstellar",
            overview = "The adventures of a group of explorers who make use of a newly discovered wormhole to surpass the limitations on human space travel and conquer the vast distances involved in an interstellar voyage.",
            imageUrl = "https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg",
            isFavorite = false,
        ),
        Movie(
            id = 4,
            title = "Inception",
            overview = "Cobb, a skilled thief who commits corporate espionage by infiltrating the subconscious of his targets is offered a chance to regain his old life as payment for a task considered to be impossible: \"inception\", the implantation of another person's idea into a target's subconscious.",
            imageUrl = "https://image.tmdb.org/t/p/w500/edv5CZvWj09upOsy2Y6IwDhK8bt.jpg",
            isFavorite = true,
        ),
        Movie(
            id = 5,
            title = "Spider-Man: No Way Home",
            overview = "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.",
            imageUrl = "https://image.tmdb.org/t/p/w500/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
            isFavorite = false,
        ),
    )

    fun getMovieDetails(): MovieDetails = MovieDetails(
        movie = Movie(
            id = 5,
            title = "Spider-Man: No Way Home",
            overview = "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.",
            imageUrl = "https://image.tmdb.org/t/p/w500/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
            isFavorite = false,
        ),
        voteAverage = 0.81f,
        releaseDate = "17/12/2021",
        language = "US",
        runtime = 148,
        crew = List(6) {
            Crewman(
                id = it,
                name = "Jon Watts",
                job = "Director",
            )
        },
        cast = List(6) {
            Actor(
                id = it,
                name = "Tom Holland",
                character = "Peter Parker / Spider-Man",
                imageUrl = "https://image.tmdb.org/t/p/w200/bBRlrpJm9XkNSg0YT5LCaxqoFMX.jpg"
            )
        },
    )

    fun getCrewman(): Crewman = Crewman(
        id = 1,
        name = "Jon Favreau",
        job = "Director"
    )

    fun getActor(): Actor = Actor(
        id = 1,
        name = "Robert Downey Jr.",
        character = "Tony Stark/Iron Man",
        imageUrl = "https://www.themoviedb.org/t/p/w200/5qHNjhtjMD4YWH3UP0rm4tKwxCL.jpg"
    )
}
