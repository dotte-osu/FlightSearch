package com.example.flightsearchapp.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.flightsearchapp.ui.theme.FlightSearchAppTheme
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.flightsearchapp.data.Airport

@Composable
fun AutoCompleteTextView(
    airports: List<Airport>,
    onAirportClick: ((String) -> Unit)? = null,
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
            AirportInfo(
                it.iataCode,
                it.name,
                modifier = Modifier.padding(3.dp)
                    .clickable(enabled = onAirportClick != null) {
                        onAirportClick?.invoke(it.iataCode)
                        Log.d("AirportInfo", "clicked")
                    }
                )
        }
    }
}


@Composable
fun AirportInfo(
    iataCode: String,
    name: String,
    modifier: Modifier = Modifier
){
    Box() {
        Row(
            modifier = modifier
                .fillMaxWidth()
        ) {
            Text(
                text = iataCode,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(end = 10.dp)
            )
            Text(
                text = name,
                modifier = Modifier
            )
        }
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
            AutoCompleteTextView(airports,{})
        }
    }
}

@Preview
@Composable
fun FlightSearchBarPreview(){
    FlightSearchAppTheme {
        Surface {
            SearchBar("test",{})
        }
    }
}