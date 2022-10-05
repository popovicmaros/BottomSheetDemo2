package cz.cvut.popovma1.bottomsheetdemo2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cz.cvut.popovma1.bottomsheetdemo2.ui.theme.BottomSheetDemo2Theme
import cz.cvut.popovma1.bottomsheetdemo2.ui.theme.BottomSheetShape
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BottomSheetDemo2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TrustVisionLayout { openSheet ->
                        val imageInfoList: List<ImageInfo> = DummyData.getImageInfoList()
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(text = "This is Main Content", textAlign = TextAlign.Center)
                            Text("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc sed varius felis, facilisis ultricies ante. ", textAlign = TextAlign.Center)

                            TrustVisionImage(imageInfoList[0], openSheet)

                            Text("Nullam id dapibus ligula. Nunc faucibus urna vitae ornare dapibus. Maecenas eget orci non urna cursus laoreet nec quis sapien", textAlign = TextAlign.Center)
                            Text("...")

                            TrustVisionImage(imageInfoList[1], openSheet)

                            Text("Suspendisse efficitur ante magna, sit amet bibendum leo condimentum ut.", textAlign = TextAlign.Center)
                            Text("...")

                            TrustVisionImage(imageInfoList[2], openSheet)

                            Text("Suspendisse potenti. Duis vitae placerat lectus. Sed at dui nec nulla pretium fermentum vel vel purus. ", textAlign = TextAlign.Center)
                            Text("...")
                        }
                    }
//                    MainLayout(::MainContent)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun TrustVisionLayout(
    content: @Composable ((ImageInfo) -> Unit) -> Unit)
{
    /* TODO make stateless ?? https://www.youtube.com/watch?v=mymWGMy9pYI */
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    var currentImageInfo: ImageInfo? by rememberSaveable { mutableStateOf(null) } // TODO why does this set as null after screen rotation?

    BackHandler(sheetState.isVisible) {
        scope.launch {
            sheetState.hide()
        }
    }

    val closeSheet: () -> Unit = {
        scope.launch {
            sheetState.hide()
        }
    }

    val openSheet: (ImageInfo) -> Unit = {
        scope.launch {
            currentImageInfo = it
            sheetState.show()
        }
    }

    ModalBottomSheetLayout(
        sheetContent = {
            // "called during sheetState.show()
            currentImageInfo?.let {
                BottomSheetContent(onClosePressed = closeSheet) {
                    ImageInfoScreen(it)
                }
            } ?: run {
                Box(Modifier.fillMaxSize()) {
                    Text("No data")
                }
            }
        },
        sheetState = sheetState,
        sheetShape = BottomSheetShape
    ) {
        content(openSheet)
    }
}

@Composable
fun TrustVisionImage(
    imageInfo: ImageInfo,
    openSheet: (ImageInfo) -> Unit
) {
    Button(onClick = { openSheet(imageInfo) }) {
        Text("Image ${imageInfo.id}")
    }
}
