package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.mock.MoviesMock.getCrewman
import agency.five.codebase.android.movieapp.model.Crewman
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CrewItem(
    crewItemViewState:Crewman,
    modifier: Modifier=Modifier
){
    Column(modifier = modifier.background(color = Color.White).clip(RoundedCornerShape(5.dp)).padding(4.dp)){
        Text(text = crewItemViewState.name, fontWeight = FontWeight.Bold)
        Text(text = crewItemViewState.job)
    }
}

@Composable
@Preview
fun CrewItemPreview(){
    CrewItem(getCrewman())
}