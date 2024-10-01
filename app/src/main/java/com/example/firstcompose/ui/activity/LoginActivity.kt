package com.example.firstcompose.ui.activity

import android.content.Intent
import android.graphics.ColorSpace.Model
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.firstcompose.R
import com.example.firstcompose.factory.LoginViewModelFactory
import com.example.firstcompose.repository.LoginRepository
import com.example.firstcompose.retrofit.ResponseData
import com.example.firstcompose.utils.CommonTextField
import com.example.firstcompose.utils.Constants
import com.example.firstcompose.utils.HandleApiResponse
import com.example.firstcompose.utils.PrefHelper
import com.example.firstcompose.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    private var empId by mutableStateOf("")
    @Inject
    lateinit var loginRepository: LoginRepository
    private var idErrorState by mutableStateOf(false)

    @Inject
    lateinit var handleApiResponse: HandleApiResponse
    private lateinit var loginViewModel: LoginViewModel
    private var isShowLoading by mutableStateOf(false)
    private var message: String = ""
    lateinit var prefHelper: PrefHelper
    private var empType = "Employee"
    private var empTypeToSend = "0"
    private var validIdErrorState = true
    private val idFocusRequester = FocusRequester()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        prefHelper = PrefHelper(this)
        setContent {
            PreviewFunction()
        }
        val factory = LoginViewModelFactory(loginRepository,handleApiResponse)
        loginViewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]
        lifecycleScope.launch {
            loginViewModel.loginState.collect {
                when (it) {
                    is ResponseData.Success -> {
                        isShowLoading = false
                        Log.e("dataResponse",""+it.data)
                        if (it.data!!.status == 1) {
                            prefHelper.put(Constants.EMP_ID, it.data.data[0].id.toString())
                            prefHelper.put(Constants.EMP_NAME, it.data.data[0].employee_name)
                            prefHelper.put(Constants.EMP_TYPE, it.data.data[0].employee_type)
//                            Log.e("qwqw",""+prefHelper.getString(Constants.EMP_ID))
//                            if (prefHelper.getString(Constants.EMP_TYPE).equals("Admin")){
//                                val intent = Intent(this@LoginActivity, AdminDashboard::class.java)
//                                startActivity(intent)
//                                finish()
//                            }else{
//                                val intent = Intent(this@LoginActivity, EmployeeDashboard::class.java)
//                                startActivity(intent)
//                                finish()
//                            }
                            val intent = if (prefHelper.getString(Constants.EMP_TYPE).equals("Admin")) {
                                Intent(this@LoginActivity, AdminDashboard::class.java)
                            } else {
                                Intent(this@LoginActivity, EmployeeDashboard::class.java)
                            }
                            startActivity(intent)
                            finish()
                        }
                        else {
                            message = it.data.msg
                            Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                    is ResponseData.Loading -> {
                        isShowLoading = true
                    }
                    is ResponseData.Error -> {
                        isShowLoading = false
                        Toast.makeText(this@LoginActivity, it.error, Toast.LENGTH_SHORT).show()
                    }
                    is ResponseData.InternetConnection -> {
                        Toast.makeText(this@LoginActivity, it.error, Toast.LENGTH_SHORT).show()
                    }
                    is ResponseData.Empty -> {}
                }
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun PreviewFunction(){
        SimpleRadioButtonComponent()
    }

    @Composable
    fun SimpleRadioButtonComponent() {
        val backgroundImage: Painter = painterResource(id = R.drawable.icon_login)
        val radioOptions = listOf("Admin", "Employee")
        val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[1]) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = backgroundImage,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = "Park Pro" , modifier = Modifier.padding(start = 16.dp),
                fontWeight = FontWeight.Bold , fontSize = 40.sp)
            Spacer(modifier = Modifier.height(50.dp))

            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = { onOptionSelected(text) }
                        )
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val context = LocalContext.current
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                            empType = text
                            // Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.padding(8.dp),
                        enabled = true,
                        colors = RadioButtonDefaults.colors(
                            Color(red = 51, green = 206, blue = 255),
                            Color(red = 51, green = 206, blue = 255)
                        ),
                        interactionSource = remember { MutableInteractionSource() }
                    )
                    Text(
                        text = text,
                        modifier = Modifier.padding(start = 16.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(25.dp))
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                SimpleTextField()
            }
            Spacer(modifier = Modifier.height(40.dp))
            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    MyButton()
                }
            }

        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SimpleTextField() {
        CommonTextField().commonTextField(textName = empId,
            label = "Id",
            errorMSg = "Please enter Emp Id",
            isError = idErrorState,
            focusRequester = idFocusRequester,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
            ),
            modifierData = Modifier.padding(top = 10.dp, start = 20.dp, end = 20.dp),
            onTextChanged = { empId = it })
    }


    @Composable
    fun MyButton() {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val context = LocalContext.current

            Button(
                onClick = {
                    if (empId.trim() == ""){
                        Toast.makeText(context, "Please enter Employee Id", Toast.LENGTH_LONG).show()
                    }else{
                        if(isNetworkConnected()){
                            empTypeToSend = if (empType == "Admin") "1" else "0"
                         //   Toast.makeText(context, ""+empTypeToSend+empId, Toast.LENGTH_LONG).show()
                            loginViewModel.login(empId ,empTypeToSend)
                        }
                        else{
                            showToast("Internet connection not available")
                        }
                    }
                },
                modifier = Modifier.padding(5.dp),
                enabled = true,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color(red = 51, green = 206, blue = 255)
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
                border = BorderStroke(width = 2.dp, brush = SolidColor( Color(red = 51, green = 206, blue = 255))),
                contentPadding = PaddingValues(
                    start = 20.dp,
                    top = 12.dp,
                    end = 20.dp,
                    bottom = 12.dp
                ),
                interactionSource = remember { MutableInteractionSource() }
            ) {
                Text(
                    text = "Login",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
            }
        }
    }
}
