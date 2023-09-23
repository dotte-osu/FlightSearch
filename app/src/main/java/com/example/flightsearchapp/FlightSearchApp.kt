package com.example.flightsearchapp.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope

enum class FlightSearchScreens{
    FlightSearch,
    FlightList,
    Favorite
}
@Composable
fun FlightSearchApp(
    viewModel: FlightSearchViewModel = viewModel(factory = FlightSearchViewModel.factory)
) {

    val navController = rememberNavController()
    val userInput by viewModel.userInput.collectAsState()
    val airports by viewModel.getIataCodeByName(userInput).collectAsState(emptyList())
    val favorites = viewModel.getAllFavorites().collectAsState(emptyList())
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            FlightSearchAppBar(
                modifier = Modifier
            )
        },
    ) {
            innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ){
            SearchBar(
                userInput = userInput,
                onValueChange = {
                    viewModel.update(it)
                    navController.navigate(FlightSearchScreens.FlightSearch.name)
                                },
                modifier = Modifier.padding(15.dp)
            )
            NavHost(
                navController = navController,
                startDestination = FlightSearchScreens.FlightSearch.name
            ){
                composable(
                    route = FlightSearchScreens.FlightSearch.name
                    //route = if (userInput.isNullOrEmpty()) FlightSearchScreens.FlightSearch.name else FlightSearchScreens.Favorite.name
                ){
                    if(userInput.isNotBlank() && airports.isNotEmpty()){
                        AutoCompleteTextView(
                            airports = airports,
                            onAirportClick = { airportName->
                                navController.navigate(
                                    "${FlightSearchScreens.FlightList.name}/$airportName"
                                )
                                viewModel.update(airportName)
                            },
                            modifier = Modifier.padding(start=15.dp, top =0.dp, end=15.dp)
                        )
                    }else{
                        if(favorites.value.isNotEmpty()){
                            FavoriteListScreen(
                                favorites.value,
                                onDeleteAction = {departureCode, destinationCode->
                                    coroutineScope.launch {
                                        viewModel.removeFavorite(departureCode, destinationCode)
                                    }
                                },
                                modifier = Modifier.padding(start=15.dp, top =0.dp, end=15.dp)
                            )
                        }
                    }
                }
                val flightArgument = "selectedAirport"
                composable(
                    route = FlightSearchScreens.FlightList.name + "/{$flightArgument}",
                    arguments = listOf(navArgument(flightArgument) { type = NavType.StringType })
                ){  backStackEntry->
                    val flightName = backStackEntry.arguments?.getString(flightArgument)
                        ?: error("busRouteArgument cannot be null")

                    Log.d("Log", "$flightName")
                    val airport by viewModel.getAirportByName(flightName).collectAsState(null)
                    val flights by viewModel.getFlightListByAirport(flightName).collectAsState(emptyList())
                    airport?.let {
                        FlightListScreen(
                            dptAirport =  it,
                            airports = flights,
                            favorites = favorites.value,
                            onDeleteAction = {departureCode, destinationCode->
                                coroutineScope.launch {
                                    viewModel.removeFavorite(departureCode, destinationCode)
                                }
                            },
                            onAddAction = {departureCode, destinationCode->
                                coroutineScope.launch {
                                    viewModel.saveFavorite(departureCode, destinationCode)
                                }
                            },
                            modifier = Modifier.padding(start=15.dp, top =0.dp, end=15.dp))
                    }
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchAppBar(
    modifier: Modifier = Modifier,
){
    TopAppBar(
        title = {
            Text(
                text = "Flight Search"
            )},
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier
    )
}

@Composable
fun SearchBar(
    userInput: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
){
    TextField(
        value = userInput,
        onValueChange = onValueChange,
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
        //trailingIcon = { Icon(Icons.Filled.Mic, contentDescription = null) },
        label = { Text("Search") },
        shape = MaterialTheme.shapes.large,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        modifier = modifier
            .fillMaxWidth()
    )
}