package com.example.movieappmad24

import android.annotation.SuppressLint
import coil.imageLoader
import android.graphics.Outline
import android.graphics.Rect
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.BeyondBoundsLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme
import org.w3c.dom.Text
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.IconButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppMAD24Theme {
                Scaffold(
                    topBar = { AppTopBar() },
                    bottomBar = { AppBottomBar() }
                ) {
                    MovieContent()
                }
            }
        }
    }
}




@Composable
fun MovieContent() {
    val movies = getMovies()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        items(movies) { movie ->
            MovieCard(movie = movie)
            Spacer(modifier = Modifier.height(16.dp)) // Add spacing between movie cards
        }
    }
}

@Composable
fun MovieCard(movie: Movie) {
    var expanded by remember { mutableStateOf(false) }
    val arrowRotation by animateDpAsState(targetValue = if (expanded) 180.dp else 0.dp)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        //elevation = 8.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {

            Box(modifier = Modifier)
            {
                Image(
                    painter = painterResource(id = R.drawable.movie_image), // Placeholder image
                    contentDescription = "Movie Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "likeBtn",
                    tint = Color.White,
                    modifier = Modifier
                        .size(27.dp)
                        .align(Alignment.TopEnd)
                        // add padding (don't know if this should be done this way)
                        .offset(x = (-15).dp, y = 15.dp)
                )
            }

            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Display movie title
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f)
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Expand/Collapse",
                    modifier = Modifier
                        .padding(8.dp)
                        .size(36.dp)
                        .clickable { expanded = !expanded }
                        /*.graphicsLayer {
                            rotationX = arrowRotation.180F
                        }*/
                )
            }
            // Display movie details if expanded
            AnimatedVisibility(visible = expanded, enter = expandVertically(), exit = shrinkVertically()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Director: ${movie.director}",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(4.dp)
                    )
                    Text(
                        text = "Released: ${movie.year}",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(4.dp)
                    )

                    Text(
                        text = "Genre: ${movie.genre}",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(4.dp)
                    )
                    Text(
                        text = "Actors: ${movie.actors}",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(4.dp)
                    )
                    Text(
                        text = "Rating: ${movie.rating}",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(4.dp)
                    )
                    Text(
                        text = "Plot: ${movie.plot}",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }

    }
}


@Composable
fun AppBottomBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(color = Color.LightGray),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            text = "Movie App",
            color = Color.Black,
            modifier = Modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
        )

    }
}

@Composable
fun AppTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(color = Color.LightGray),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            text = "Movie App",
            color = Color.Black,
            modifier = Modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
        )

    }
}





