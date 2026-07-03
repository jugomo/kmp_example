package com.jugomo.kmp_example

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

import kmp_example.sharedui.generated.resources.Res
import kmp_example.sharedui.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        var catPhotos by remember { mutableStateOf<List<CatPhoto>>(emptyList()) }
        var catPhotosError by remember { mutableStateOf<String?>(null) }
        var selectedPhoto by remember { mutableStateOf<CatPhoto?>(null) }
        var isRefreshing by remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()

        suspend fun loadCatPhotos() {
            isRefreshing = true
            catPhotosError = try {
                catPhotos = getRandomCatPhotos()
                null
            } catch (e: Exception) {
                e.message ?: "Error al cargar los gatos"
            }
            isRefreshing = false
        }

        LaunchedEffect(Unit) { loadCatPhotos() }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .safeContentPadding()
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                /* BUTTON HERE!! */
                Button(onClick = { showContent = !showContent }) {
                    Text("Click me!")
                }
                AnimatedVisibility(showContent) {
                    val greeting = remember { Greeting().greet() }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Image(painterResource(Res.drawable.compose_multiplatform), null)
                        Text("Compose: $greeting")
                    }
                }

                PullToRefreshBox(
                    isRefreshing = isRefreshing,
                    onRefresh = { coroutineScope.launch { loadCatPhotos() } },
                    modifier = Modifier.fillMaxWidth().weight(1f),
                ) {
                    when {
                        catPhotosError != null -> Text("Error: $catPhotosError")
                        catPhotos.isEmpty() && !isRefreshing -> Text("Cargando gatos...")
                        else -> LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(catPhotos) { photo ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { selectedPhoto = photo }
                                        .padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    AsyncImage(
                                        model = photo.url,
                                        contentDescription = photo.id,
                                        modifier = Modifier.size(64.dp),
                                        contentScale = ContentScale.Crop,
                                    )
                                    Column(modifier = Modifier.padding(start = 8.dp)) {
                                        Text(photo.id)
                                        Text("${photo.width} x ${photo.height}")
                                    }
                                }
                            }
                        }
                    }
                }
            }

            selectedPhoto?.let { photo ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                ) {
                    Button(
                        onClick = { selectedPhoto = null },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Atrás")
                    }
                    AsyncImage(
                        model = photo.url,
                        contentDescription = photo.id,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit,
                    )
                }
            }
        }
    }
}