package com.example.listapp

import android.media.Image
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.intl.Locale
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
        mutableStateOf(false)
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
                item->
                if(item.isEditing){
                    ItemEditor(item = item, onEditComplete = {
                        editedName,editedQuantity->
                        sItems=sItems.map {  it.copy(isEditing = false)}
                        val editedItem= sItems.find { it.id ==item.id}
                        editedItem?.let {
                            it.itemName=editedName
                            it.quantity=editedQuantity
                        }
                    })
                }else{
                    ManageItems(item = item, onEditClick = {

                        sItems=sItems.map { it.copy(isEditing = it.id==item.id) }
                    }, onDeleteClick = {
                        sItems=sItems-item
                    })
                }

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
fun ItemEditor(item:ListItem,onEditComplete:(String,Int)->Unit){
    var editedName  by remember { mutableStateOf(item.itemName)}
    var editedQuantity  by remember { mutableStateOf(item.quantity.toString())}
    var isEditing by remember { mutableStateOf(item.isEditing)
    }

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        Column {
            BasicTextField(value = editedName, onValueChange = {editedName=it}, singleLine = true, modifier = Modifier
                .wrapContentSize()
                .padding(8.dp))
            BasicTextField(value = editedQuantity, onValueChange = {editedQuantity=it}, singleLine = true, modifier = Modifier
                .wrapContentSize()
                .padding(8.dp))
        }
        Button(onClick = {
            if(editedName.isNotBlank()){
            isEditing=false
            onEditComplete(editedName,editedQuantity.toIntOrNull()?:1)
            }
        }) {
            Text(text = "Save")
            
        }

    }

}


@Composable
fun ManageItems(
    item:ListItem,
    onEditClick:()->Unit,
    onDeleteClick:()->Unit){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(border = BorderStroke(2.dp, Color.Cyan), shape = RoundedCornerShape(20)),
        horizontalArrangement = Arrangement.SpaceEvenly

    ) {
        Text(text = item.itemName, modifier = Modifier.padding(8.dp))
        Text(text = " Qty: ${item.quantity} ", modifier = Modifier.padding(8.dp))
        Row (modifier =Modifier.padding(8.dp)){
            IconButton(onClick = { onEditClick() }) {
                Icon(imageVector= Icons.Default.Edit,contentDescription = null)

            }
            IconButton(onClick = { onDeleteClick() }) {
                Icon(imageVector= Icons.Default.Delete,contentDescription = null)

            }
        }


    }


}

@Composable
@Preview(showBackground = true)
fun LoadItemsPreview(){
    LoadItems()

}
