package com.example.firstcompose.ui.activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.*
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.firstcompose.R
import com.example.firstcompose.factory.LoginViewModelFactory
import com.example.firstcompose.repository.LoginRepository
import com.example.firstcompose.retrofit.ResponseData
import com.example.firstcompose.utils.CommonTextField
import com.example.firstcompose.utils.HandleApiResponse
import com.example.firstcompose.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
@AndroidEntryPoint
class MainActivity : BaseActivity() {
    //Email
    private var email by mutableStateOf("")
    private var emailErrorState by mutableStateOf(false)
    private var validEmailErrorState = true
    private val emailFocusRequester = FocusRequester()

    //Password
    private var password by mutableStateOf("")
    private var passwordErrorState by mutableStateOf(false)
    private val passwordFocusRequester = FocusRequester()

    //Show loading
    private var isShowLoading by mutableStateOf(false)
    //Show dialog
    private var isShowDialog by mutableStateOf(false)
    private var message: String = ""
    private lateinit var loginViewModel: LoginViewModel

    @Inject
    lateinit var loginRepository: LoginRepository

    @Inject
    lateinit var handleApiResponse: HandleApiResponse


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FullScreenBackground()
        }
        val factory = LoginViewModelFactory(loginRepository,handleApiResponse)
        loginViewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]
        lifecycleScope.launch {
            loginViewModel.loginState.collect {
                when (it) {
                    is ResponseData.Success -> {
                        isShowLoading = false
//                        if (it.data!!.status_code == 1) {
//                            Toast.makeText(this@MainActivity, ""+it.data.message, Toast.LENGTH_SHORT).show()
//                        }
//                        else {
//                            isShowDialog = true
//                            message = it.data.message
//                            Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
//                        }
                    }
                    is ResponseData.Loading -> {
                        isShowLoading = true
                    }
                    is ResponseData.Error -> {
                        isShowLoading = false
                        Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_SHORT).show()
                    }
                    is ResponseData.InternetConnection -> {
                        Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_SHORT).show()
                    }
                    is ResponseData.Empty -> {}
                }
            }
        }
    }
    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    private fun FullScreenBackground() {
        val backgroundImage: Painter = painterResource(id = R.drawable.sign_background)
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
        {
            Image(
                painter = backgroundImage,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .background(Color.White),
            )
            {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Login",
                        fontSize = 24.sp,   // Set font size
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                    )
                    CommonTextField().commonTextField(textName = email,
                        label = "Email",
                        errorMSg = if (validEmailErrorState) "Please enter email" else "Please enter valid email",
                        isError = emailErrorState,
                        focusRequester = emailFocusRequester,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                        ),
                        modifierData = Modifier.padding(top = 25.dp),
                        onTextChanged = { email = it })
                    CommonTextField().commonTextField(textName = password,
                        label = "Password",
                        errorMSg = "please enter password",
                        isError = passwordErrorState,
                        focusRequester = passwordFocusRequester,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password, imeAction = ImeAction.Next
                        ),
                        modifierData = Modifier.padding(top = 10.dp),
                        onTextChanged = { password = it })
                    ElevatedButton(
                        onClick = {
                            emailErrorState = false
                            passwordErrorState = false
                            if (email.trim() == "") {
                                validEmailErrorState = true
                                emailErrorState = true
                            }
                            else if (password.trim() == "") {
                                passwordErrorState = true
                            } else {
                                if(isNetworkConnected()){
                                   // loginViewModel.login(email,password,"77.03033","77.287688","OnePlus","","Oneplus","Android 14")
                                }
                                else{
                                    showToast("Internet connection not available")
                                }
                            }
                        },
                        modifier = Modifier
                            .padding(top = 40.dp)
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(
                            text = "Login"
                        )
                    }
                }
            }
        }
    }
}
