package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.ui.theme.Gray600
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

data class ActorCardViewState(
    val name: String,
    val character: String,
    val imageUrl: String?,
)

@Composable
fun ActorCard(
    actorCardViewState: ActorCardViewState,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        elevation = 3.dp
    ) {
        Column {
            AsyncImage(
                model = actorCardViewState.imageUrl,
                contentDescription = "Actor",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(4.dp)
                    .clip(shapes.small)
            )
            Text(
                text = actorCardViewState.name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(4.dp),
                color = Color.Black
            )
            Text(
                text = actorCardViewState.character,
                fontSize = 16.sp,
                modifier = Modifier.padding(4.dp),
                color = Gray600
            )
        }
    }
}

@Composable
@Preview
fun ActorCardPreview() {
    val actor = ActorCardViewState(
        name = "Robert Downey Jr.",
        character = "Tony Stark/Iron Man",
        imageUrl = "https://www.themoviedb.org/t/p/w200/5qHNjhtjMD4YWH3UP0rm4tKwxCL.jpg"
    )
    ActorCard(
        actorCardViewState = actor,
        modifier = Modifier.width(120.dp)
    )
}
