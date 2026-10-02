package com.doma.garaza


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.doma.garaza.ui.theme.GarazaTheme
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainContent()
        }
    }
}


@Composable
fun IndeterminateLinearIndicator() {
    LinearProgressIndicator(
        modifier = Modifier.width(120.dp).height(5.dp),
        color = ButtonDefaults.buttonColors().containerColor,
        trackColor = ButtonDefaults.buttonColors().contentColor,
    )
}




//@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(viewModel: MainViewModel = viewModel()) {
    val status by viewModel.state.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.events.collectLatest { event ->
            when(event){
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    GarazaTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                CenterAlignedTopAppBar(
                    colors = topAppBarColors(
                        containerColor = ButtonDefaults.buttonColors().containerColor,
                        titleContentColor = ButtonDefaults.buttonColors().contentColor,
                    ),
                    title = {
                        Icon(
                            painter = painterResource(R.drawable.ic_garaza),
                            contentDescription = null,
                            modifier = Modifier.size(50.dp),
                            tint = ButtonDefaults.buttonColors().contentColor
                        )
                    }
                )
            }
        ) { padding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
//                status row
                Row(
                    modifier = Modifier.fillMaxWidth().height(20.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    when (status) {
                        is RequestState.Idle -> Text("podrsajte za preklop")
                        is RequestState.Sending -> IndeterminateLinearIndicator()
                        is RequestState.Success -> Text("preklop")
                        is RequestState.Error -> Text("napaka pri pošiljanju") // na roke cast?
                    }
                }
//                button
                SwipeableButton(
                    isActivated = false,
                    borderColor = ButtonDefaults.buttonColors().containerColor,
                    onExpanded = {
                        if (status !is RequestState.Sending) {
                            viewModel.sendRequest()
                        }
                    },
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(ButtonDefaults.buttonColors().containerColor)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.rounded_arrow_right_alt_24),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp),
                            tint = ButtonDefaults.buttonColors().contentColor
                        )
                    }
                }
            }
        }
    }
}
