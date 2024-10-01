package com.example.firstcompose.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.firstcompose.R
import com.example.firstcompose.factory.AddEmployessFactory
import com.example.firstcompose.repository.AddEmployessRespository
import com.example.firstcompose.retrofit.ResponseData
import com.example.firstcompose.utils.HandleApiResponse
import com.example.firstcompose.viewmodel.AddEmployeeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AddEmploye : BaseActivity() {
    @Inject
    lateinit var addEmployessRespository: AddEmployessRespository
    @Inject
    lateinit var handleApiResponse: HandleApiResponse
    private lateinit var addEmployeeListViewModel: AddEmployeeViewModel
    private var isShowLoading by mutableStateOf(false)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
          AddButton()
        }
        val factory = AddEmployessFactory(addEmployessRespository,handleApiResponse)
        addEmployeeListViewModel = ViewModelProvider(this, factory)[AddEmployeeViewModel::class.java]
        lifecycleScope.launch {
            addEmployeeListViewModel.addEmployeeState.collect {
                when (it) {
                    is ResponseData.Success -> {
                        isShowLoading = false
                        Log.e("dataResponse",""+it.data)
                        if (it.data!!.status == 1) {
                            Toast.makeText(this@AddEmploye, it.data.msg, Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@AddEmploye, AdminDashboard::class.java)
                            intent.putExtra("Username", "John Doe")
                            startActivity(intent)
                        }
                        else {
                            Toast.makeText(this@AddEmploye, it.data.msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                    is ResponseData.Loading -> {
                        isShowLoading = true
                    }
                    is ResponseData.Error -> {
                        isShowLoading = false
                        Toast.makeText(this@AddEmploye, it.error, Toast.LENGTH_SHORT).show()
                    }
                    is ResponseData.InternetConnection -> {
                        Toast.makeText(this@AddEmploye, it.error, Toast.LENGTH_SHORT).show()
                    }
                    is ResponseData.Empty -> {}
                }
            }
        }
    }
}


@Composable
fun AddButton(){
    Column( modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        var expanded by remember { mutableStateOf(false) }
        val suggestions = listOf("Item1", "Item2", "Item3")
        var text by rememberSaveable { mutableStateOf("") }
        var text1 by rememberSaveable { mutableStateOf("") }
        var text2 by rememberSaveable { mutableStateOf("") }
        var text3 by rememberSaveable { mutableStateOf("") }
        var text4 by rememberSaveable { mutableStateOf("") }
        Text(text = "Employee Detail" , modifier = Modifier.padding(start = 16.dp),
            fontWeight = FontWeight.Bold , fontSize = 25.sp , color = colorResource(id = R.color.bluelight)
        )
        Spacer(modifier = Modifier.height(40.dp))
        OutlinedTextField(
            value = text,
            label = { Text(text = "Name") },
            onValueChange = {
                text = it
            },modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 0.dp, 30.dp, 0.dp),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = text1,
            label = { Text(text = "Email") },
            onValueChange = {
                text1 = it
            },modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 0.dp, 30.dp, 0.dp), singleLine = true
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = true,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            value = text2,
            label = { Text(text = "Mobile") },
            onValueChange = {
                text2 = it
            },modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 0.dp, 30.dp, 0.dp) ,  singleLine = true
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = true,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            value = text3,
            label = { Text(text = "Vehicle No") },
            onValueChange = {
                text3 = it
            },modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 0.dp, 30.dp, 0.dp) ,  singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = text4,
            label = { Text(text = "Type(Car/Bike)") },
            onValueChange = {
                text4 = it
            },modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 0.dp, 30.dp, 0.dp) ,  singleLine = true
        )
//        MainContent()

        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {
                Log.e("PRACHI",""+text)
                Log.e("PRACHI",""+text1)
                Log.e("PRACHI",""+text2)
                Log.e("PRACHI",""+text3)
                Log.e("PRACHI",""+text4)
               // addEmployeeListViewModel.employeeList(text,text1,text2,text3,text4)
            },
            modifier = Modifier.padding(2.dp),
            enabled = true,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color(red = 51, green = 206, blue = 255)
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
            border = BorderStroke(width = 2.dp, brush = SolidColor(Color(red = 51, green = 206, blue = 255))),
            contentPadding = PaddingValues(
                start = 20.dp,
                top = 12.dp,
                end = 20.dp,
                bottom = 12.dp
            ),
            interactionSource = remember { MutableInteractionSource() }
        ) {
            Text(
                text = "Save",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )
        }

    }
}


