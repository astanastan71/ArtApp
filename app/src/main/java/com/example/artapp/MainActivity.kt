package com.example.artapp

import android.app.ProgressDialog.show
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.layout.IntervalList
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.artapp.ui.theme.ArtAppTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtShow()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtShow() {
    val albumsSet: List<Albums> = listOf<Albums>(
        Albums(R.drawable.escapeland, "Escapeland", "Effe (2018)"),
        Albums(R.drawable.hurtbreak, "Hurtbreak wonderland", "World's end girlfriend (2007)"),
        Albums(R.drawable.smt, "日刊遺書", "ぬゆり (2018)"),
        Albums(R.drawable.nier, "Addendum", "NieR Orchestral Arrangement Album (2020)"),
        Albums(R.drawable.rered, "Re:Red", "Kashiwa Daisuke (2016)"),
        Albums(R.drawable.inrainbows, "In Rainbows", "Radiohead (2007)"),
        Albums(R.drawable.palemachine, "Pale Machine", "bo en (2013)"),
        Albums(
            R.drawable.trust,
            "Losing the internet and the trust i had in you",
            "Flatsound (2010)"
        ),
        Albums(R.drawable.whirpool, "Whirpool", "Kinoko Teikoku (2012)")
    )
    var searchValue by remember { mutableStateOf("") }
    var currentComp by remember { mutableStateOf((0..albumsSet.size).first) }
    var currentSet = albumsSet[0].imgSet
    var currentTitle = albumsSet[0].titleSet
    var currentDescription = albumsSet[0].authorYear
    if (searchValue != "") {
        var searchedIndex = albumsSet.find {
            it.titleSet.startsWith(searchValue)
        }
        if (currentComp != albumsSet.indexOf(searchedIndex))
            currentComp = albumsSet.indexOf(searchedIndex)
    }
    try {
        for (i in albumsSet.indices) {
            when (currentComp) {
                i -> {
                    currentSet = albumsSet[i].imgSet
                    currentTitle = albumsSet[i].titleSet
                    currentDescription = albumsSet[i].authorYear
                }
            }
        }
    } catch (e: Exception) {
        Toast.makeText(
            LocalContext.current,
            "Out of range for compositions",
            Toast.LENGTH_LONG
        ).show()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        ) {
            SearchBar(
                label = R.string.seartch_bar,
                value = searchValue,
                onValueChange = {
                    searchValue = it
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth()
            )
        }
        Row(
            modifier = Modifier
                .weight(7f)
                .padding(40.dp)
                .background(color = Color.LightGray)
                .shadow(5.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = currentSet),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(40.dp)
            )
        }
        Column(
            modifier = Modifier
                .weight(2f)
                .padding(20.dp)
                .background(color = Color.LightGray)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = currentTitle,
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center
            )
            Text(
                text = currentDescription,
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

        }
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ButtonNav(
                onClick = {
                    if (currentComp > 0 && currentComp <= albumsSet.size - 1)
                        currentComp--
                },
                text = stringResource(id = R.string.pre_button),
                modifier = Modifier
            )
            Spacer(modifier = Modifier.width(75.dp))
            ButtonNav(
                onClick = {
                    if (currentComp >= 0 && currentComp < albumsSet.size - 1)
                        currentComp++
                },
                text = stringResource(id = R.string.next_button),
                modifier = Modifier
            )


        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    @StringRes label: Int,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier
) {
    TextField(
        label = { Text(stringResource(label)) },
        singleLine = true,
        keyboardOptions = keyboardOptions,
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
    )
}

internal fun searchEngine(searchedTitle: String) {


}

@Composable
fun ButtonNav(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier
) {
    Button(
        onClick = onClick
    ) {
        Text(text = text)

    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ArtAppTheme {
        ArtShow()
    }
}