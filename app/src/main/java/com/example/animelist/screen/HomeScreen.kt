package com.example.animelist.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Brush.Companion.verticalGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.request.ImageRequest
import com.example.animelist.R
import com.example.animelist.data.models.AnimeListEntry
import com.example.animelist.data.models.AnimeListViewModels
import com.example.animelist.navigation.ScreenList
import com.example.animelist.ui.theme.RobotoCondensed
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState

@Composable
fun HomeScreen(
    navController: NavController
) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(horizontal = 24.dp)
        ) {
            Spacer(Modifier.height(44.dp))
            Box(
                Modifier
                    .align(CenterHorizontally)
                    .fillMaxWidth()
            ){
                Image(
                    painter = painterResource(id = R.drawable.ghibli_logo_white),
                    contentDescription = "ghibli_logo",
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(Modifier.height(16.dp))
            SearchBar(
                hint = "Search...",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            AnimeList(navController = navController)
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
){
    var text by remember{
        mutableStateOf("")
    }

    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    Box(modifier = modifier){
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed = it.isFocused != true
                },
        )
        if(isHintDisplayed) {
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }
    }
}

@Composable
fun AnimeList(
    navController: NavController,
    viewModel: AnimeListViewModels = hiltViewModel()
){
    val animeList by remember {viewModel.animeList}
    val endReached by remember {viewModel.endReached}
    val isLoading by remember {viewModel.isLoading}
    val loadError by remember {viewModel.loadError}
    
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        val itemCount = if(animeList.size % 2 == 0) {
            animeList.size /2
        } else {
            animeList.size/2 + 1
        }
        items(itemCount){
            if(it >= itemCount - 1 && !endReached) {
                viewModel.loadAnimePaginated()
            }
            AnimeRow(rowIndex = it, dataEntries = animeList, navController = navController)
        }
    }
    Box(
        contentAlignment = Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if(isLoading){
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }
        if(loadError.isNotEmpty()){
            RetrySection(error = loadError) {
                viewModel.loadAnimePaginated()
            }
        }
    }
}

@Composable
fun AnimeEntry(
    animeDataEntry: AnimeListEntry,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AnimeListViewModels = hiltViewModel()
){
    val defaultDominantColor = MaterialTheme.colors.surface
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }
    
    Box(
        contentAlignment = Center,
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(verticalGradient(
                listOf(
                    dominantColor,
                    defaultDominantColor
                )
            ))
            .clickable {
                navController.navigate(
                    ScreenList.DetailScreen.route + "/${dominantColor.toArgb()}/${animeDataEntry.animeName}"
                )
            }
    ) {
        Column {
            val painter = rememberCoilPainter(
                fadeIn = true,
                request =ImageRequest.Builder(LocalContext.current)
                    .data(animeDataEntry.animeImageUrl)
                    .target {
                        viewModel.calcDominantColor(it) { color ->
                            dominantColor = color
                        }
                    }
                    .build()
            )

            Image(
                painter = painter,
                contentDescription = animeDataEntry.animeName,
                modifier = Modifier
                    .size(120.dp)
                    .align(CenterHorizontally)
            )

            Text(
                text = animeDataEntry.animeName,
                fontFamily = RobotoCondensed,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun AnimeRow(
    rowIndex: Int,
    dataEntries: List<AnimeListEntry>,
    navController: NavController
){
    Column{
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AnimeEntry(
                animeDataEntry = dataEntries[rowIndex * 2],
                navController = navController,
                modifier = Modifier.weight(1f)
            )
            if(dataEntries.size >= rowIndex * 2 + 2) {
                AnimeEntry(
                    animeDataEntry = dataEntries[rowIndex * 2 + 1],
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit
){
    Column {
        Text(error, color = Color.Red, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}