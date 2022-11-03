package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.ui.theme.Blue
import agency.five.codebase.android.movieapp.ui.theme.Gray600
import agency.five.codebase.android.movieapp.ui.theme.Spacing
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

sealed class MovieCategoryLabelTextViewState()

data class MovieCategoryStringParam(val value: String) : MovieCategoryLabelTextViewState()
data class MovieCategoryStringResource(@StringRes val value: Int) :
    MovieCategoryLabelTextViewState()

data class MovieCategoryLabelViewState(
    val itemId: Int,
    val isSelected: Boolean,
    val categoryText: MovieCategoryLabelTextViewState,
)

@Composable
fun MovieCategoryLabel(
    movieCategoryLabelViewState: MovieCategoryLabelViewState,
    modifier: Modifier = Modifier,
    spacing: Spacing = Spacing(),
) {
    Column(modifier = modifier.width(intrinsicSize = IntrinsicSize.Max)) {
        Text(
            text = when (movieCategoryLabelViewState.categoryText) {
                is MovieCategoryStringParam -> movieCategoryLabelViewState.categoryText.value;
                is MovieCategoryStringResource -> stringResource(id = movieCategoryLabelViewState.categoryText.value);
            },
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = if (movieCategoryLabelViewState.isSelected) Blue else Gray600,
            textAlign = TextAlign.Center,
            modifier = Modifier.clickable { }
        )
        if (movieCategoryLabelViewState.isSelected) {
            Spacer(modifier = Modifier.size(spacing.extraSmall))
            Divider(color = Blue, thickness = 4.dp, modifier = modifier.fillMaxWidth())
        }
    }
}

@Composable
@Preview
fun MovieCategoryLabelPreview() {


    //MovieCategoryLabel(MovieCategoryLabelViewState(1, true, MovieCategoryStringResource(R.string.movies)))
}
