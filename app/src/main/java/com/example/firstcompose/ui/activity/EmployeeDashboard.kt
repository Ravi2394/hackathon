package com.example.firstcompose.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.firstcompose.ui.activity.ui.theme.FirstComposeTheme
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text

import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firstcompose.R
import com.example.firstcompose.utils.Constants
import com.example.firstcompose.utils.PrefHelper

class EmployeeDashboard : BaseActivity() {
    lateinit var prefHelper: PrefHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        prefHelper = PrefHelper(this)
        setContent {
            prefHelper.getString(Constants.EMP_NAME)?.let { FormScreen( it) }
        }
    }
}

@Composable
fun FormScreen(userName: String){
    Column( modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        verticalArrangement = Arrangement.Top) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
                Image(painter = painterResource(id = R.drawable.namaste), contentDescription ="" ,
            modifier = Modifier
                .size(90.dp)
                .padding(16.dp, 10.dp, 0.dp, 0.dp))
        Column() {
            Text(
                text = "Namaste",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif ,
                modifier = Modifier.padding(20.dp , 25.dp , 0.dp , 0.dp)
            )
            Text(
                text = userName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif ,
                modifier = Modifier.padding(20.dp , 10.dp , 0.dp , 0.dp)
            )

        }
        }
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {},
//            modifier = Modifier.padding(16.dp).align(Alignment.TopEnd),
            modifier = Modifier.align(Alignment.End).padding(16.dp),
            enabled = true,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color(red = 51, green = 206, blue = 255)
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
            contentPadding = PaddingValues(
                start = 20.dp,
                top = 12.dp,
                end = 20.dp,
                bottom = 12.dp
            ),
            interactionSource = remember { MutableInteractionSource() }
        ) {
            Text(
                text = "Add Receipt",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )
//            Image(painter = painterResource(id = R.drawable.add), contentDescription =""
//            , modifier = Modifier.size(20.dp))
        }
        displayList()

    }
}


@Composable
fun displayList() {
    // on below line we arecreating a simple list
    // of strings and adding different programming
    // languages in it.
    val languages = listOf(
        "January", "February", "March", "April", "May", "June", "July", "August",
        "September", "October", "November", "December",
    )
    // on below line we are
    // creating a simple column
    Column(
        // inside this column we are specifying modifier
        // to specify max width and max height
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight().padding(10.dp,0.dp,10.dp,0.dp),
        // on below line we are specifying horizontal alignment
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // on below line we are creating a simple text
        // view for displaying heading for our application
        // on below line we are calling lazy column
        // for displaying listview.
        LazyColumn {
            // on below line we are populating
            // items for listview.
            items(languages) { language ->
                // on below line we are specifying ui for each item of list view.
                // we are specifying a simple text for each item of our list view.
                Text(language, modifier = Modifier.padding(15.dp))
                // on below line we are specifying
                // divider for each list item
                Divider()
            }
        }
    }
}