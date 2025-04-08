package com.wafiqz0085.assesment1.ui.screen

import android.app.DatePickerDialog
import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wafiqz0085.assesment1.R
import com.wafiqz0085.assesment1.ui.theme.Assesment1Theme
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_name)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        ScreenContent(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun ScreenContent(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id", "ID"))

    var hariTanggal by remember { mutableStateOf("") }
    var namaTempat by remember { mutableStateOf("") }
    var totalHarga by remember { mutableStateOf("") }

    var wafiqChecked by remember { mutableStateOf(false) }
    var zhafiraChecked by remember { mutableStateOf(false) }
    var bungaChecked by remember { mutableStateOf(false) }

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            hariTanggal = dateFormat.format(calendar.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.aktivitas),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = namaTempat,
            onValueChange = { namaTempat = it },
            label = { Text(stringResource(R.string.tempat)) },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = totalHarga,
            onValueChange = { totalHarga = it },
            label = { Text(stringResource(R.string.total)) },
            leadingIcon = { Text("Rp") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = hariTanggal,
            onValueChange = {},
            label = { Text(stringResource(R.string.hari_tanggal)) },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() }
        )

        Text(
            text = stringResource(R.string.yang_bayar),
            style = MaterialTheme.typography.titleMedium
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = wafiqChecked, onCheckedChange = { wafiqChecked = it })
            Text(text = stringResource(R.string.wafiq))
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = zhafiraChecked, onCheckedChange = { zhafiraChecked = it })
            Text(text = stringResource(R.string.zhafira))
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = bungaChecked, onCheckedChange = { bungaChecked = it })
            Text(text = stringResource(R.string.bunga))
        }

        if (
            namaTempat.isNotEmpty() ||
            totalHarga.isNotEmpty() ||
            hariTanggal.isNotEmpty() ||
            wafiqChecked || zhafiraChecked || bungaChecked
        ) {
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            Text(
                text = stringResource(R.string.rekap_hasil),
                style = MaterialTheme.typography.titleMedium
            )
            Text("${stringResource(R.string.output_tempat)} $namaTempat")
            Text("${stringResource(R.string.output_total)} Rp $totalHarga")
            Text("${stringResource(R.string.output_hariTanggal)} $hariTanggal")

            val selectedNames = listOfNotNull(
                if (wafiqChecked) stringResource(R.string.wafiq) else null,
                if (zhafiraChecked) stringResource(R.string.zhafira) else null,
                if (bungaChecked) stringResource(R.string.bunga) else null
            )

            Text("${stringResource(R.string.bayar)}: ${if (selectedNames.isNotEmpty()) selectedNames.joinToString(", ") else "-"}")
        }
    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewScreenContent() {
    Assesment1Theme {
        ScreenContent()
    }
}
