package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.ui.theme.Blue
import agency.five.codebase.android.movieapp.ui.theme.Gray600
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

sealed class MovieCategoryLabelTextViewState()

data class MovieCategoryStringParam(val value: String) : MovieCategoryLabelTextViewState()
data class MovieCategoryStringResource(@StringRes val value: Int) : MovieCategoryLabelTextViewState()

data class MovieCategoryLabelViewState(
    val itemId: Int,
    val isSelected: Boolean,
    val categoryText: MovieCategoryLabelTextViewState
)

@Composable
fun MovieCategoryLabel(
    movieCategoryLabelViewState: MovieCategoryLabelViewState,
    modifier: Modifier=Modifier
) {
    var isSelected by remember {
        mutableStateOf(movieCategoryLabelViewState.isSelected)
    }

    Column(modifier = Modifier.wrapContentWidth()) {
        Text(
            text = when(movieCategoryLabelViewState.categoryText){
                is MovieCategoryStringParam -> movieCategoryLabelViewState.categoryText.value;
                is MovieCategoryStringResource -> stringResource(id = movieCategoryLabelViewState.categoryText.value);
            },
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            style = if(isSelected) TextStyle(textDecoration = TextDecoration.Underline) else TextStyle(textDecoration = TextDecoration.None),
            color = if (isSelected) Blue else Gray600,
            textAlign = TextAlign.Center,
            modifier = modifier.clickable { isSelected = isSelected.not() }
        )
        /*if (isSelected) {
            Image(
                painter = painterResource(id = R.drawable.text_underline),
                contentDescription = "underline",
            )
        }*/
    }
}

@Composable
@Preview
fun MovieCategoryLabelPreview(){
    MovieCategoryLabel(MovieCategoryLabelViewState(1,true,MovieCategoryStringParam("Crime")))
}
