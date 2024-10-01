package com.example.firstcompose.ui.activity

import android.content.Intent

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.firstcompose.R
import com.example.firstcompose.factory.EmployeeViewModelFactory
import com.example.firstcompose.model.DataX
import com.example.firstcompose.repository.EmployeeRepository
import com.example.firstcompose.retrofit.ResponseData
import com.example.firstcompose.utils.Constants
import com.example.firstcompose.utils.HandleApiResponse
import com.example.firstcompose.utils.PrefHelper
import com.example.firstcompose.viewmodel.EmployeeListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AdminDashboard : BaseActivity() {
    @Inject
    lateinit var employeeRepository: EmployeeRepository
    @Inject
    lateinit var handleApiResponse: HandleApiResponse
    private lateinit var employeeListViewModel: EmployeeListViewModel
    private var isShowLoading by mutableStateOf(false)
    lateinit var prefHelper: PrefHelper
    var empName = mutableStateListOf<DataX?>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        prefHelper = PrefHelper(this)
        setContent {
            prefHelper.getString(Constants.EMP_NAME)?.let { FormScreen(empName, it) }
        }
        val factory = EmployeeViewModelFactory(employeeRepository,handleApiResponse)
        employeeListViewModel = ViewModelProvider(this, factory)[EmployeeListViewModel::class.java]
        lifecycleScope.launch {
            employeeListViewModel.employeeState.collect {
                when (it) {
                    is ResponseData.Success -> {
                        isShowLoading = false
                        Log.e("employeeResponse",""+it.data)
                        if (it.data!!.status == 1) {
                            for (i in it.data.data) {
                                empName.clear()
                                empName.addAll((it.data.data))
                            }
                        }
                        else {

                        }
                    }
                    is ResponseData.Loading -> {
                        isShowLoading = true
                    }
                    is ResponseData.Error -> {
                        isShowLoading = false
                        Toast.makeText(this@AdminDashboard, it.error, Toast.LENGTH_SHORT).show()
                    }
                    is ResponseData.InternetConnection -> {
                        Toast.makeText(this@AdminDashboard, it.error, Toast.LENGTH_SHORT).show()
                    }
                    is ResponseData.Empty -> {
                        Log.e("employeeResponse","empty")
                    }
                }
            }
        }
        employeeListViewModel.employeeList(prefHelper.getString(Constants.EMP_ID).toString())
    }
}


@Composable
fun FormScreen(empName: SnapshotStateList<DataX?>, userName: String){
    val context = LocalContext.current
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
            onClick = {
                context.startActivity(Intent(context, AddEmploye::class.java))
            },
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
                text = "Register",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )
//            Image(painter = painterResource(id = R.drawable.add), contentDescription =""
//            , modifier = Modifier.size(20.dp))
        }
        displayList(empName)

    }
}

@Composable
fun displayList(empName: SnapshotStateList<DataX?>) {
    // on below line we arecreating a simple list
    // of strings and adding different programming
    // languages in it.
//    val languages = listOf(
//        "C++", "C", "C#", "Java", "Kotlin", "Dart", "Python", "Javascript", "SpringBoot",
//        "XML", "Dart", "Node JS", "Typescript", "Dot Net", "GoLang", "MongoDb",
//    )
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
            items(empName) { language ->
                // on below line we are specifying ui for each item of list view.
                // we are specifying a simple text for each item of our list view.
//                Text(language, modifier = Modifier.padding(15.dp))
                androidx.compose.material.Card(elevation = 8.dp , modifier = Modifier.padding(8.dp),) {
                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)) {
                        Image(painter = painterResource(R.drawable.baseline_supervised_user_circle_24), contentDescription = "",
                            modifier = Modifier.size(48.dp).padding(8.dp))
                        Column() {
                            language?.let {
                                Text(text = it.employee_name,
                                    style = MaterialTheme.typography.headlineMedium
                                )
                            }
                            language?.let {
                                Text(text = it.email,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Thin,
                                    fontSize = 12.sp)
                            }
                        }
                    }

                }
                // on below line we are specifying
                // divider for each list item
                Divider()
            }
          //  EmpList(empName, empId = empId)
        }
    }
}


//@Preview
//@Composable
//fun PreviewItem(){
//    Column {
//        EmpList(empName = empls)
//    }
//}

@Composable
fun EmpList(empName : ArrayList<String>,empId : ArrayList<String>){
    androidx.compose.material.Card(elevation = 8.dp , modifier = Modifier.padding(8.dp),) {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)) {
            Image(painter = painterResource(R.drawable.baseline_supervised_user_circle_24), contentDescription = "",
                modifier = Modifier.size(48.dp).padding(8.dp))
        }
        Column() {
            Text(text = "",
                style = MaterialTheme.typography.headlineLarge
            )
            Text(text = "",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Thin,
                fontSize = 12.sp)
        }
    }
}
