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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.ac.it_college.std.s22007.weather_forecast.data.citiesList
import jp.ac.it_college.std.s22007.weather_forecast.ui.theme.Weather_forecastTheme

enum class ScreenState {
    Onboarding, NextScreen
}
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Weather_forecastTheme {
                var currentScreen by rememberSaveable { mutableStateOf(ScreenState.Onboarding) }
                var selectedCityId by rememberSaveable { mutableStateOf<String?>(null) }

                when (currentScreen){
                    ScreenState.Onboarding -> OnboardingScreen {
                        currentScreen = ScreenState.NextScreen
                    }
                    ScreenState.NextScreen -> {
                        NextScreen(selectedCityId = selectedCityId)
                    }
                }
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
                modifier = Modifier,
                onContinueClicked = {
                    shouldShowOnboarding = false
                    var currentScreen = ScreenState.NextScreen

                }
            )
        }
    }
}

@Composable
private fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onContinueClicked: () -> Unit
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
        Text("都道府県を選択",)
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
                    onContinueClicked()
                }
            },
            enabled = selectedCityId != null
        ) {
            Text(
                "検索",
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun NextScreen(
    modifier: Modifier = Modifier,
    selectedCityId: String?
) {
    var selectedCityName by remember{ mutableStateOf("")}

    LaunchedEffect(selectedCityId){
        selectedCityId?.let {
            val selectedCity = citiesList.find { city -> city.id == it }
            selectedCityName = selectedCity?.name ?: ""
        }
    }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "天気",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayMedium,
        )
        Column (
            modifier = modifier.fillMaxSize()
        ) {
            Text(text = "場所:$selectedCityName")
            Text(text = stringResource(id = R.string.weather))
            Text(text = stringResource(id = R.string.temperature))
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

@Preview
@Composable
fun NextScreenView(){
    Weather_forecastTheme {
        NextScreen(selectedCityId = "")
    }
}