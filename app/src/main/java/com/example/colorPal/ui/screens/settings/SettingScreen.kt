package com.example.colorPal.ui.screens.settings

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.colorPal.ui.component.bottomSheet.BottomSheet
import com.example.colorPal.ui.component.bottomSheet.BottomSheetModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen() {
    val padding = 16.dp

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Settings") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(start = padding, end = padding)
        ) {
            GeneratorCard()
            Spacer(modifier = Modifier.height(padding))
            AboutCard()
        }
    }


}

@Composable
fun AboutCard() {
    Column {
        Text(
            text = "Info", modifier = Modifier.padding(bottom = 8.dp), fontSize = 16.sp
        )

        Card(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable { }) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "About", fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
fun GeneratorCard() {
    var showSheetForHarmony by remember { mutableStateOf(false) }
    var showSheetForColorInfo by remember { mutableStateOf(false) }

    Column {
        Text(
            text = "Generator", modifier = Modifier.padding(bottom = 8.dp), fontSize = 16.sp
        )

        Card(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable { showSheetForHarmony = true }) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Color Harmony", fontWeight = FontWeight.Bold)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Monochrome",
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = LocalContentColor.current.copy(alpha = .6f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Card(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable { showSheetForColorInfo = true }) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Color Info", fontWeight = FontWeight.Medium)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Hex", modifier = Modifier.padding(end = 4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = LocalContentColor.current.copy(alpha = .6f)
                    )
                }
            }
        }
    }

    val harmony = listOfColorHarmony()
    val colorInfo = listOfColorInfo()
    val context = LocalContext.current

    if (showSheetForHarmony) BottomSheet(items = harmony,
        onItemClick = { _, item ->
            if (item != null) {
                harmonyItems(item, context)
            }
            showSheetForHarmony = false
        },
        onDismissSheet = { showSheetForHarmony = false })

    if (showSheetForColorInfo) BottomSheet(items = colorInfo,
        onItemClick = { _, item -> },
        onDismissSheet = { showSheetForColorInfo = false })
}

fun listOfColorHarmony() = listOf(
    BottomSheetModel(text = "Monochrome"),
    BottomSheetModel(text = "Monochrome dark"),
    BottomSheetModel(text = "Monochrome light"),
    BottomSheetModel(text = "Analogic"),
    BottomSheetModel(text = "Complement"),
    BottomSheetModel(text = "Analogic Complement"),
    BottomSheetModel(text = "Triad"),
    BottomSheetModel(text = "Quad")
)

fun listOfColorInfo() = listOf(
    BottomSheetModel(text = "Hex"),
    BottomSheetModel(text = "Rgb"),
    BottomSheetModel(text = "Hsl"),
    BottomSheetModel(text = "Hvl"),
    BottomSheetModel(text = "Name"),
    BottomSheetModel(text = "Cmyk"),
    BottomSheetModel(text = "XYZ"),
)

fun harmonyItems(item: BottomSheetModel, context: Context) {
    val sharedPreference = context.getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)
    sharedPreference.edit().clear().apply()

    val editor = sharedPreference.edit()

    editor.putString("harmony_item", item.text)
    editor.apply()
}

@Preview
@Composable
private fun PreviewCard() {
    //GeneralCard()
    //GeneratorCard()
    SettingScreen()
}