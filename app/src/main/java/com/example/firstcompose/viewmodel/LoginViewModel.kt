package com.example.firstcompose.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstcompose.model.Location
import com.example.firstcompose.model.LoginRequest
import com.example.firstcompose.model.LoginResponse
import com.example.firstcompose.repository.LoginRepository
import com.example.firstcompose.retrofit.ResponseData
import com.example.firstcompose.utils.HandleApiResponse
import com.example.firstcompose.utils.ResponseCodeCheck
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository,var handleApiResponse: HandleApiResponse) : ViewModel() {

//    @Inject
//    lateinit var handleApiResponse: HandleApiResponse

    val loginState: MutableStateFlow<ResponseData<LoginResponse>> = MutableStateFlow(ResponseData.Empty())
    fun login(
        id: String, type: String
    ) {
        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(
                    emp_Id = id,
                    emp_type = type
                )
                loginRepository.getData(loginRequest).onStart {
                    loginState.value = ResponseData.Loading()
                }.catch {
                  //  loginState.value = ResponseData.Error(null, context.failMsg(error = it.toString()))
                }.collect {
                    loginState.value = handleApiResponse.handleApiResponse(it)
                }
            } catch (ex: Exception) {
                ex.printStackTrace();
                //loginState.value = ResponseData.Error(ex.message)
            }
        }
    }
}