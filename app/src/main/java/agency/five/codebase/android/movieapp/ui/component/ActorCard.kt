package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.mock.MoviesMock.getActor
import agency.five.codebase.android.movieapp.model.Actor
import agency.five.codebase.android.movieapp.ui.theme.Gray600
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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

@Composable
fun ActorCard(
    actorCardViewState: Actor,
    modifier: Modifier = Modifier,
 ) {
    Card( modifier = modifier.width(150.dp).wrapContentHeight(), elevation = 3.dp) {
        Column {
            AsyncImage(
                model = actorCardViewState.imageUrl,
                contentDescription = "Actor",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(5.dp))
            )

            Text(text = actorCardViewState.name, fontWeight = FontWeight.Bold, fontSize = 24.sp, modifier = Modifier.padding(4.dp), color = Color.Black)
            Text(text = actorCardViewState.character, fontSize = 20.sp,modifier = Modifier.padding(4.dp), color = Gray600)
        }
    }
}

@Composable
@Preview
fun ActorCardPreview(){
    ActorCard(getActor())
}