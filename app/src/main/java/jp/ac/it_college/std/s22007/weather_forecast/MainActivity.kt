package jp.ac.it_college.std.s22007.weather_forecast
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.ac.it_college.std.s22007.weather_forecast.ui.theme.Weather_forecastTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Weather_forecastTheme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
private fun MyApp(modifier: Modifier = Modifier){
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    Surface(modifier){
        if (shouldShowOnboarding){
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        }
    }
}
@Composable
private fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "3時間毎5日間天気",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayMedium,
        )
        Button(
            onClick = onContinueClicked
        ) {
            Text("都道府県選択")
        }
    }
}
@Preview
@Composable
fun MyAppPreview(){
    Weather_forecastTheme {
        MyApp(Modifier.fillMaxSize())
    }
}



