package com.example.flightsearchapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.flightsearchapp.ui.theme.FlightSearchAppTheme
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearchapp.data.Airport

@Composable
fun FlightSearchApp(
    viewModel: FlightSearchViewModel = viewModel(factory = FlightSearchViewModel.factory)
) {

    val userInput by viewModel.userInput.collectAsState()
    val airports by viewModel.getIataCodeByName(userInput).collectAsState(emptyList())

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
                onValueChange = { viewModel.update(it) },
                modifier = Modifier.padding(10.dp)
            )
            if(userInput.isNotBlank() && airports.isNotEmpty()){
                AutoCompleteTextView(airports)
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
fun AutoCompleteTextView(
    airports: List<Airport>,
    modifier: Modifier = Modifier
){
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(0.dp),
    ){
        items(
            items = airports,
            key = { airports -> airports.id }
        ){
            AirportInfo(it.iataCode, it.name)
        }
    }
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
        label = { Text("Search") },
        modifier = modifier
            .fillMaxWidth(),
    )
}

@Composable
fun AirportInfo(
    iataCode: String,
    name: String,
    modifier: Modifier = Modifier
){
    Row(modifier = modifier){
        Text(
            text = iataCode,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
        )
        Text(
            text = name,
            modifier = Modifier
        )
    }
}


@Preview
@Composable
fun AirportInfoPreview(){
    FlightSearchAppTheme {
        Surface {
            val airports = List(3){
                index->
                Airport(
                    index,
                    "abc",
                    "abc airport",
                    10
                )
            }
            ///AutoCompleteTextView(airports)
        }
    }
}