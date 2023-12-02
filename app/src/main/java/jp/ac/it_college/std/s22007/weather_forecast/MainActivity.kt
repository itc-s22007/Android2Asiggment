package jp.ac.it_college.std.s22007.weather_forecast

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.ac.it_college.std.s22007.weather_forecast.api.Client
import jp.ac.it_college.std.s22007.weather_forecast.data.CityData
import jp.ac.it_college.std.s22007.weather_forecast.data.citiesList
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
private fun MyApp(modifier: Modifier = Modifier) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }
    Surface(modifier) {
        if (shouldShowOnboarding) {
            OnboardingScreen(
                onContinueClicked = {
                    shouldShowOnboarding = false
                })
        }
    }
}

@Composable
private fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCityId by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    val context = LocalContext.current
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "3時間毎5日間天気",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayMedium,
        )
        Text(
            "都道府県を選択",
        )
        Demo_ExposedDropdownMenuBox { cityId ->
            selectedCityId = cityId
        }

        Button(
            onClick = {
                selectedCityId?.let { id ->
                    Toast.makeText(
                        context,
                        "選択された都道府県のID: $id",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        ) {
            Text("次へ")
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Demo_ExposedDropdownMenuBox(onCitySelected: (String) -> Unit) {
    val context = LocalContext.current
    val citiesList = citiesList
    var expanded by remember { mutableStateOf(false) }
    var selectedCity by remember { mutableStateOf(citiesList[0]) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = selectedCity.name,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                citiesList.forEach { city ->
                    DropdownMenuItem(
                        text = { Text(text = city.name) },
                        onClick = {
                            selectedCity = city
                            expanded = false
                            onCitySelected(city.id)
                            Toast.makeText(context, city.name, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MyAppPreview() {
    Weather_forecastTheme {
        MyApp()
    }
}
