package cz.cvut.popovma1.bottomsheetdemo2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                    MainLayout()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MainLayout() {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    var currentImageInfo: ImageInfo? by remember { mutableStateOf(null) }

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
            /*
            TODO create more marketable example
            TrustVisionBottomSheet()
             */
            // "called during sheetState.show() and during recomposition"
            currentImageInfo?.apply {
                BottomSheetContent(onClosePressed = closeSheet) {
                    ImageInfoScreen(this)
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
        MainContent(openSheet)
    }
}

@Composable
fun MainContent(openSheet: (ImageInfo) -> Unit) {
    /*
    TODO User of library should see and write only this part !!
     */
    val imageInfoList: List<ImageInfo> = DummyData.getImageInfoList()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "This is Main Content")

        imageInfoList.forEach { imageInfo ->
            Button(onClick = { openSheet(imageInfo) }) {
                Text("Image ${imageInfo.id}")
            }
            /* TODO create "more marketable" example
            Text("...")
            Text("...")
            Text("...")
            TrustVisionImage(imageInfo1)
            Text("...")
            Text("...")
            TrustVisionImage(imageInfo2)
            Text("...")
            Text("...")
             */
        }
    }
}
