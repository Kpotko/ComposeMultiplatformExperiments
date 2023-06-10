import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RangeSlider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun App() {
    MaterialTheme {
        val minValue = -100f
        val maxValue = 200f
        val steps = 2

        RangedSliderComponent(minValue, maxValue, steps)
       /* RangeSlider(
            steps = steps,
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
            },
            valueRange = 0f..100f,
            onValueChangeFinished = {
                // launch some business logic update with the state you hold
                // viewModel.updateSelectedSliderValue(sliderPosition)
            },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colors.secondary,
                activeTrackColor = MaterialTheme.colors.secondary
            )
        )*/
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RangedSliderComponent(minValue: Float, maxValue: Float, steps: Int) {
    var sliderPosition by remember {
        mutableStateOf(minValue..maxValue)
    }
/*
    var counter by remember {
        mutableStateOf(0)
    }

    var secondCounter by remember {
        mutableStateOf(0)
    }
*/
    val state = mutableStateOf(0.5f..1f)
    var counter  by remember {mutableStateOf(0)}
    var secondCounter = 0

    Column {
        Text(text = sliderPosition.toString())
        Text(text = state.value.toString())
        Text(text = secondCounter.toString())

        RangeSlider(
            modifier = Modifier.testTag("slider"),
            steps = 0,
            enabled = true,
            value = sliderPosition,
            onValueChange = {
                state.value = it
                sliderPosition = it
                counter += 1
            },
            valueRange = 0f..100f,
            onValueChangeFinished = {
                //counter.setValue(counter.getValue() + 1)
            },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colors.secondary,
                activeTrackColor = MaterialTheme.colors.secondary
            )
        )
        Button(onClick = {
            sliderPosition = -10000f..100000f
        }){
            // add button content here.
            // all the items are added in a row.
            Text(text = "Click Me")
        }
    }
}

expect fun getPlatformName(): String