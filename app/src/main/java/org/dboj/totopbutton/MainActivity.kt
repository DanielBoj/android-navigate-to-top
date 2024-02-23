package org.dboj.totopbutton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.dboj.totopbutton.ui.theme.ToTopButtonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToTopButtonTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    ExampleLazyList()
                }
            }
        }
    }
}

@Composable
fun ExampleLazyList(
    modifier: Modifier = Modifier
){
    val state = rememberLazyListState()

    val isEnabled = remember {
        mutableStateOf(true)
    }

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            ScrollToTopButton(state = state)
        }
    ) {innerPadding ->
        LazyColumn(
            state = state,
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding
        ) {
            items(100) {
                Text(
                    text = "Item $it",
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            isEnabled.value = !isEnabled.value
                        }
                )
            }
        }
    }
}

/* Use derivedStateOf if the state change more often tha the composable recomposition */
@Composable
fun ScrollToTopButton(
    state: LazyListState
) {
    val scope: CoroutineScope = rememberCoroutineScope()

    val showScrollToTopButton by remember {
        derivedStateOf {
            state.firstVisibleItemIndex >= 5
        }
    }

    if (showScrollToTopButton) {
        FloatingActionButton(onClick = {
            scope.launch {
                state.animateScrollToItem(0)
            }
        }) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = null
            )
        }
    }
}

/* Use remember if the state change less often than the composable recomposition */
@Composable
fun ScrollToTopButtonVerTwo(
    state: LazyListState
) {
    val scope: CoroutineScope = rememberCoroutineScope()

    val showScrollToTopButton = remember(state.firstVisibleItemIndex) {
        state.firstVisibleItemIndex >= 5
    }

    if (showScrollToTopButton) {
        FloatingActionButton(onClick = {
            scope.launch {
                state.animateScrollToItem(0)
            }
        }) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = null
            )
        }
    }
}

/* More complex example  with a Key and a derivedStateOf */
@Composable
fun ScrollToTopButtonVerThree(
    state: LazyListState,
    isEnabled: Boolean
) {
    val scope: CoroutineScope = rememberCoroutineScope()

    val showScrollToTopButton by remember(isEnabled) {
        derivedStateOf {
            state.firstVisibleItemIndex >= 5 && isEnabled
        }
    }

    if (showScrollToTopButton) {
        FloatingActionButton(onClick = {
            scope.launch {
                state.animateScrollToItem(0)
            }
        }) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = null
            )
        }
    }
}
