package com.example.firstcompose.ui.screens

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firstcompose.R
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewFunction(){
//    SimpleRadioButtonComponent()
//}
//
//@Composable
//fun SimpleRadioButtonComponent() {
//    val backgroundImage: Painter = painterResource(id = R.drawable.icon_login)
//    val radioOptions = listOf("Admin", "Employee")
//    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[1]) }
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .fillMaxHeight(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally,
//    ) {
//        Image(
//            painter = backgroundImage,
//            contentDescription = null,
//            contentScale = ContentScale.Crop
//        )
//        Spacer(modifier = Modifier.height(30.dp))
//        Text(text = "ParkPro" , modifier = Modifier.padding(start = 16.dp),
//            fontWeight = FontWeight.Bold , fontSize = 40.sp)
//        Spacer(modifier = Modifier.height(50.dp))
//
//        radioOptions.forEach { text ->
//            Row(
//                Modifier
//                    .fillMaxWidth()
//                    .selectable(
//                        selected = (text == selectedOption),
//                        onClick = { onOptionSelected(text) }
//                    )
//                    .padding(horizontal = 16.dp),
//                horizontalArrangement = Arrangement.Start,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                val context = LocalContext.current
//                RadioButton(
//                    selected = (text == selectedOption),
//                    onClick = {
//                        onOptionSelected(text)
//                       // Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
//                    },
//                    modifier = Modifier.padding(8.dp),
//                    enabled = true,
//                    colors = RadioButtonDefaults.colors(
//                        Color.Blue,
//                        Color.Blue
//                    ),
//                    interactionSource = remember { MutableInteractionSource() }
//                )
//                Text(
//                    text = text,
//                    modifier = Modifier.padding(start = 16.dp),
//                    fontWeight = FontWeight.Bold
//                )
//            }
//        }
//        Spacer(modifier = Modifier.height(25.dp))
//        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
//            SimpleTextField()
//        }
//        Spacer(modifier = Modifier.height(40.dp))
//        MaterialTheme {
//            Surface(color = MaterialTheme.colorScheme.background) {
//                MyButton()
//            }
//        }
//
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SimpleTextField() {
//    var text by remember { mutableStateOf(TextFieldValue("")) }
//    OutlinedTextField(
//        value = text,
//        label = { Text(text = "Id") },
//        onValueChange = {
//            text = it
//        }
//    )
//}
//
//
//@Composable
//fun MyButton() {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally,
//    ) {
//        val context = LocalContext.current
//
//        Button(
//            onClick = {
//                Toast.makeText(context, "", Toast.LENGTH_LONG).show()
//            },
//            modifier = Modifier.padding(5.dp),
//            enabled = true,
//            shape = RoundedCornerShape(12.dp),
//            colors = ButtonDefaults.buttonColors(
//                contentColor = Color.White,
//                containerColor = Color.Blue
//            ),
//            elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
//            border = BorderStroke(width = 2.dp, brush = SolidColor(Color.Blue)),
//            contentPadding = PaddingValues(
//                start = 20.dp,
//                top = 12.dp,
//                end = 20.dp,
//                bottom = 12.dp
//            ),
//            interactionSource = remember { MutableInteractionSource() }
//        ) {
//            Text(
//                text = "Login",
//                fontSize = 20.sp,
//                fontWeight = FontWeight.Bold,
//                fontFamily = FontFamily.Serif
//            )
//        }
//    }
//}
