package com.doma.garaza

//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import com.example.garaza.ui.theme.GarazaTheme
//import androidx.compose.material3.Button
//import androidx.compose.material3.Divider
//import androidx.compose.material3.HorizontalDivider
//import androidx.compose.material3.Icon
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.OutlinedTextFieldDefaults
//import androidx.compose.material3.TextField
//import androidx.compose.material3.TextFieldColors
//import androidx.compose.material3.TextFieldDefaults
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableIntStateOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.Font
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//
//class Other : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            GarazaTheme {
//                Screen1()
//            }
//        }
//    }
//}
//
//@Composable
//fun Screen1(){
//    var name by remember { mutableStateOf("") }
//    var names by remember { mutableStateOf(listOf<String>()) }
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(top = 30.dp)
//            .padding(16.dp)
//    ) {
//        Row(
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            OutlinedTextField(
//                value = name,
//                onValueChange = {
//                        text -> name = text // 2-way binding
//                },
//                modifier = Modifier.weight(1f),
//                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.Black)
//            )
//            Spacer(modifier=Modifier.width(16.dp))
//            Button(
//                onClick = {
//                    if(name.isNotBlank()) {
//                        names = names + name
//                        name = ""
//                    }
//                }
//            ) {
//                Text(text = "Add")
//            }
//
//        }
//        LazyColumn {
//            items(names) {
//                    currentName ->
//                Text(
//                    text = currentName,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp)
//                )
//                HorizontalDivider()
//            }
//        }
//    }
//}
//
//@Composable
//fun Screen2(){
////    state
//    var count by remember {
//        mutableIntStateOf(0)
//    }
//    val buttonWidth = 150.dp
//    val buttonHeight = 60.dp
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center,
//        modifier = Modifier.fillMaxSize()
//    ) {
//        Text(text = count.toString(), fontSize = 30.sp)
//        Button(onClick = {
//            count++
//        },
//            modifier = Modifier.size(buttonWidth,buttonHeight)
//        ) {
//            Text(text = "$count +1")
//        }
//        Spacer(modifier=Modifier.size(5.dp))
//        Button(onClick = {
//            count = 0
//        },
//            modifier = Modifier.size(buttonWidth,buttonHeight)
//        ) {
//            Text(text = "Reset")
//        }
//    }
//}
//
//
//
////sample 1
//@Composable
//fun Greeting2(name: String, modifier: Modifier = Modifier) {
////    Row(
////        horizontalArrangement = Arrangement.End,
////        modifier = Modifier
////            .background(Color.Blue)
////            .size(400.dp)
////    )
////    Column(
////        horizontalAlignment = Alignment.CenterHorizontally,
////        verticalArrangement = Arrangement.Center,
////        modifier = Modifier
////            .background(Color.Blue)
////            .fillMaxSize()
////    )
//    Box(
//        modifier = Modifier.size(400.dp),
////        contentAlignment = Alignment.Center
//    )
//    {
//        Text(text = "Hello $name!",
//            color=Color.Red,
//            fontSize = 30.sp,
//            modifier = Modifier.align(Alignment.BottomEnd)
//        )
//        Text(text = "Some other text",
//            color=Color.Green,
//            fontSize = 30.sp,
//        )
//    }
//
//}
////sample 2
//@Composable
//fun Greeting1(name: String, modifier: Modifier = Modifier) {
////    Image(
////        painter = painterResource(id = R.drawable.ic_launcher_foreground),
////        contentDescription = null,
////        modifier = Modifier.background(Color.Black)
////    )
//    Column() {
//        for (i in 1..10){
//            Icon(
//                painter = painterResource(R.drawable.ic_garaza),
//                contentDescription = null)
//        }
//    }
//}
//
//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
////    LazyColumn(modifier = Modifier.fillMaxSize()){
//    LazyRow(modifier = Modifier.fillMaxSize()){
//        items(15){i ->
//            Icon(
//                painter = painterResource(R.drawable.ic_garaza),
//                contentDescription = null,
//                modifier = Modifier.size(100.dp)
//
//            )
//        }
//    }
//}
//
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    GarazaTheme {
////        Greeting("Gašper")
//        Screen1()
//    }
//
//}