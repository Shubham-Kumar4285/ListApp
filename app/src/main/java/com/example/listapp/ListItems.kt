package com.example.listapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class ListItem(
    val id:Int,
    var itemName:String,
    var quantity:Int,
    var isEditing:Boolean=false
)


@Composable
fun LoadItems(){
    var sItems by remember { mutableStateOf(listOf<ListItem>()) }
    var showDialog by remember {
        mutableStateOf(true)
    }

    var itemName by remember {
        mutableStateOf("")
    }
    var quantity by remember {
        mutableStateOf("1")
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { showDialog=true }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Add Item")
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp)
        ){
            items(sItems){

            }

        }
    }
    if(showDialog){
       AlertDialog(
           onDismissRequest = { showDialog=false },
           confirmButton = {
               Row(modifier = Modifier
                   .fillMaxWidth()
                   .padding(8.dp),
                   horizontalArrangement = Arrangement.SpaceBetween){
                   Button(onClick = {
                       if(itemName.isNotBlank()){
                           val newItem=ListItem(
                               sItems.size+1,
                               itemName,
                               quantity.toIntOrNull()?:1,false)
                           sItems=sItems+newItem
                           showDialog=false;
                           itemName=""
                           quantity="1"

                       }
                   }) {
                       Text(text = "Save")
                   }
                   Button(onClick = {
                       showDialog=false
                       itemName=""
                       quantity="1"
                   }){
                       Text(text = "Cancel")
                   }
               }
           },
           title ={ Text(text = "Add List Item")},
           text = {
               Column {
                   OutlinedTextField(value = itemName, onValueChange = {itemName=it}, singleLine = true,modifier= Modifier
                       .fillMaxWidth()
                       .padding(8.dp), label = { Text(
                       text = "Item"
                   )})
                   OutlinedTextField(value = quantity, onValueChange = {quantity=it},singleLine = true,modifier= Modifier
                       .fillMaxWidth()
                       .padding(8.dp),label = { Text(
                       text = "Quantity")})



               }
           }

            )
    }

}

@Composable
@Preview(showBackground = true)
fun LoadItemsPreview(){
    LoadItems()
}