package com.wafiqz0085.assesment1.ui.screen

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
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
        ScreenContent(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun ScreenContent(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy")

    var hariTanggal by remember { mutableStateOf("") }
    var hariTanggalError by remember { mutableStateOf(false) }

    var namaTempat by remember { mutableStateOf("") }
    var namaTempatError by remember { mutableStateOf(false) }

    var totalHarga by remember { mutableStateOf("") }
    var totalHargaError by remember { mutableStateOf(false) }

    var checkBoxError by remember { mutableStateOf(false) }

    var wafiqChecked by remember { mutableStateOf(false) }
    var zhafiraChecked by remember { mutableStateOf(false) }
    var bungaChecked by remember { mutableStateOf(false) }

    var showResult by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

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
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.aktivitas),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = hariTanggal,
            onValueChange = {},
            label = { Text(stringResource(R.string.hari_tanggal)) },
            readOnly = true,
            trailingIcon = { IconPicker(hariTanggalError) },
            supportingText = { ErrorHint(hariTanggalError) },
            isError = hariTanggalError,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() }
        )

        OutlinedTextField(
            value = namaTempat,
            onValueChange = { namaTempat = it },
            label = { Text(stringResource(R.string.tempat)) },
            trailingIcon = { IconPicker(namaTempatError) },
            supportingText = { ErrorHint(namaTempatError) },
            isError = namaTempatError,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = totalHarga,
            onValueChange = { totalHarga = it },
            label = { Text(stringResource(R.string.total)) },
            trailingIcon = { IconPicker(totalHargaError) },
            supportingText = { ErrorHint(totalHargaError) },
            isError = totalHargaError,
            leadingIcon = { Text("Rp") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = stringResource(R.string.yang_bayar),
            style = MaterialTheme.typography.titleMedium
        )

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = wafiqChecked, onCheckedChange = { wafiqChecked = it })
                Text(text = stringResource(R.string.wafiq), modifier = Modifier.padding(start = 8.dp))
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = zhafiraChecked, onCheckedChange = { zhafiraChecked = it })
                Text(text = stringResource(R.string.zhafira), modifier = Modifier.padding(start = 8.dp))
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = bungaChecked, onCheckedChange = { bungaChecked = it })
                Text(text = stringResource(R.string.bunga), modifier = Modifier.padding(start = 8.dp))
            }
        }

        if (checkBoxError) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 4.dp)
            ) {
                IconPicker(true, modifier = Modifier.padding(end = 8.dp))
                Text(
                    text = stringResource(R.string.checkbox_error),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Button(
            onClick = {
                hariTanggalError = hariTanggal.isBlank()
                namaTempatError = namaTempat.isBlank()
                totalHargaError = totalHarga.isBlank()
                checkBoxError = !(wafiqChecked || zhafiraChecked || bungaChecked)

                showResult = !(hariTanggalError || namaTempatError || totalHargaError || checkBoxError)
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Text(text = stringResource(R.string.hitung))
        }

        if (showResult) {
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            Text(text = stringResource(R.string.rekap_hasil), style = MaterialTheme.typography.titleMedium)
            Text("${stringResource(R.string.output_hariTanggal)} $hariTanggal")
            Text("${stringResource(R.string.output_tempat)} $namaTempat")
            Text("${stringResource(R.string.output_total)} Rp $totalHarga")

            val selectedNames = listOfNotNull(
                if (wafiqChecked) stringResource(R.string.wafiq) else null,
                if (zhafiraChecked) stringResource(R.string.zhafira) else null,
                if (bungaChecked) stringResource(R.string.bunga) else null
            )

            val jumlahOrang = selectedNames.size
            val hargaPerOrang = if (jumlahOrang > 0) totalHarga.toIntOrNull()?.div(jumlahOrang) else null

            if (selectedNames.isNotEmpty() && hargaPerOrang != null) {
                selectedNames.forEach { nama ->
                    Text("$nama bayar: Rp $hargaPerOrang")
                }
            } else {
                Text(stringResource(R.string.bayar) + ": -")
            }

            OutlinedButton(
                onClick = {
                    hariTanggal = ""
                    namaTempat = ""
                    totalHarga = ""
                    wafiqChecked = false
                    zhafiraChecked = false
                    bungaChecked = false
                    showResult = false
                    hariTanggalError = false
                    namaTempatError = false
                    totalHargaError = false
                    checkBoxError = false
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 12.dp)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "Reset", modifier = Modifier.padding(end = 8.dp))
                Text("Reset")
            }
        }
    }
}

@Composable
fun IconPicker(isError: Boolean, modifier: Modifier = Modifier) {
    if (isError) {
        Icon(
            imageVector = Icons.Filled.Warning,
            contentDescription = "Input Error",
            tint = MaterialTheme.colorScheme.error,
            modifier = modifier
        )
    }
}

@Composable
fun ErrorHint(isError: Boolean) {
    if (isError) {
        Text(text = stringResource(R.string.input_invalid))
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
