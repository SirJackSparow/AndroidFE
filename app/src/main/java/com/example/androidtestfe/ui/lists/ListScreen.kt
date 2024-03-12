@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.androidtestfe.ui.lists

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.androidtestfe.data.network.model.ListModelItem
import com.example.androidtestfe.ui.lists.ListState.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(navigationController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "List Screen") },
                navigationIcon = {
                    if (navigationController.previousBackStackEntry != null) {
                        IconButton(onClick = { navigationController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = null
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            ListContent()
        }
    }
}

@Composable
private fun ListContent(vm: ListViewModel = hiltViewModel()) {

    var itemss by remember { mutableStateOf<List<ListModelItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }

    val uiState by vm.uiState.observeAsState()

    LaunchedEffect(uiState) {
        when (val data = uiState) {
            is Success -> itemss = data.data
            is Failed -> {}
            else -> {}
        }
    }

    LazyColumn {
        items(itemss) { item ->
            ListItem(user = item)
        }

        item {
            if (isLoading) {
                LoadingIndicator()
            } else {
                val lastIndex = itemss.size - 1

                if (lastIndex >= 0) {
                    if (!isLoading) {
                        CoroutineScope(Dispatchers.IO).launch {
                            isLoading = true
                            delay(1000)
                            vm.getDataList()
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun LoadingIndicator() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(100.dp)
    )
}

@Composable
fun ListItem(
    user: ListModelItem,
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, top = 16.dp),
        color = Color.White,
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                AsyncImage(
                    model = user.thumbnailUrl,
                    contentScale = ContentScale.Crop,
                    contentDescription = "User Image",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                )
                AsyncImage(
                    model = user.url,
                    contentScale = ContentScale.Crop,
                    contentDescription = "User Image",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = user.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = user.url,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        }
    }
}