package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.ui.theme.Blue
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class FavoriteButtonViewState(
    var isFavorite: Boolean,
)

@Composable
fun FavoriteButton(
    buttonViewState: FavoriteButtonViewState,
    modifier: Modifier = Modifier,
) {
    var toggleIsFavorite by remember {
        mutableStateOf(buttonViewState.isFavorite)
    }
    Surface(modifier = modifier
        .wrapContentSize()
        .clip(CircleShape),
        color = Blue.copy(alpha = 0.6F)
    ) {
        Image(painter = painterResource(id = if (toggleIsFavorite) R.drawable.favorite_fill else R.drawable.fovorite_outline),
            contentDescription = "favorite",
            modifier = Modifier
                .clickable {
                    toggleIsFavorite = toggleIsFavorite.not()
                }
                .size(40.dp, 40.dp)
                .padding(8.dp)
        )
    }
}

@Composable
@Preview
fun FavoriteButtonPreview() {
     FavoriteButton(FavoriteButtonViewState(true))
}
