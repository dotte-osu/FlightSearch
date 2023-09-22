package com.example.flightsearchapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsearchapp.data.Airport
import com.example.flightsearchapp.ui.theme.FlightSearchAppTheme


@Composable
fun FlightListScreen(
    dptAirport: Airport,
    airports: List<Airport>,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Flights from ${dptAirport.iataCode}",
            modifier = Modifier
                .padding(top = 5.dp, bottom = 5.dp)
        )
        LazyColumn(
            contentPadding = PaddingValues(0.dp),
        ) {
            items(
                items = airports,
                key = { airports -> airports.id }
            ) {
                FlightCard(
                    dptAirport.iataCode,
                    dptAirport.name,
                    it.iataCode,
                    it.name,
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                )
            }
        }
    }
}

@Composable
fun FlightCard(iataCodeDpt: String, nameDpt: String, iataCodeArr: String, nameArr: String, modifier: Modifier = Modifier){
    Card(
        modifier = modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ){
        Column {
            Airport("DEPART", iataCodeDpt, nameDpt, Modifier.padding(5.dp))
            Airport("ARRIVE", iataCodeArr, nameArr, Modifier.padding(5.dp))
        }
    }
}

@Composable
fun Airport(drection: String, iataCode: String, name: String, modifier: Modifier = Modifier){
    Column(modifier) {
        Text(text = drection, style = MaterialTheme.typography.bodyMedium, modifier = Modifier)
        Row {
            Text(
                text = iataCode,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 10.dp)
            )
            Text(text = name, style = MaterialTheme.typography.bodyLarge, modifier = Modifier)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FlightCardPreview(){
    FlightSearchAppTheme {
        Surface {
            FlightCard("dpt","dpt name", "arr", "arr name", Modifier.padding(5.dp))
        }
    }
}