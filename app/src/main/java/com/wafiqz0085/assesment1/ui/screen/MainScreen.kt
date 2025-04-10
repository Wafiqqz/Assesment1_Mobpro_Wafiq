package com.wafiqz0085.assesment1.ui.screen

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.wafiqz0085.assesment1.R
import com.wafiqz0085.assesment1.navigation.Screen
import com.wafiqz0085.assesment1.ui.theme.Assesment1Theme
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_name)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = {navController.navigate((Screen.About.route))}) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.tentang_aplikasi),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        ScreenContent(
            modifier = Modifier.padding(innerPadding).fillMaxSize())
    }
}

@SuppressLint("SimpleDateFormat", "StringFormatInvalid")
@Composable
fun ScreenContent(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy")

    var hariTanggal by rememberSaveable { mutableStateOf("") }
    var hariTanggalError by rememberSaveable { mutableStateOf(false) }

    var namaTempat by rememberSaveable { mutableStateOf("") }
    var namaTempatError by rememberSaveable { mutableStateOf(false) }

    var totalHarga by rememberSaveable { mutableStateOf("") }
    var totalHargaError by rememberSaveable { mutableStateOf(false) }

    var checkBoxError by rememberSaveable { mutableStateOf(false) }

    var diraChecked by rememberSaveable { mutableStateOf(false) }
    var radiChecked by rememberSaveable { mutableStateOf(false) }
    var iradChecked by rememberSaveable { mutableStateOf(false) }

    var showResult by rememberSaveable { mutableStateOf(false) }

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
                Checkbox(checked = diraChecked, onCheckedChange = { diraChecked = it })
                Text(text = stringResource(R.string.dira), modifier = Modifier.padding(start = 8.dp))
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = radiChecked, onCheckedChange = { radiChecked = it })
                Text(text = stringResource(R.string.radi), modifier = Modifier.padding(start = 8.dp))
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = iradChecked, onCheckedChange = { iradChecked = it })
                Text(text = stringResource(R.string.irad), modifier = Modifier.padding(start = 8.dp))
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
                checkBoxError = !(diraChecked || radiChecked || iradChecked)

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
                if (diraChecked) context.getString(R.string.dira) else null,
                if (radiChecked) context.getString(R.string.radi) else null,
                if (iradChecked) context.getString(R.string.irad) else null
            )

            val jumlahOrang = selectedNames.size
            val hargaPerOrang = totalHarga.toIntOrNull()?.div(jumlahOrang).takeIf { jumlahOrang > 0 }

            if (selectedNames.isNotEmpty() && hargaPerOrang != null) {
                selectedNames.forEach { nama ->
                    Text(text = stringResource(R.string.bayar_per_orang, nama, hargaPerOrang))
                }
            } else {
                Text(text = stringResource(R.string.bayar) + ": -")
            }

            val bayarList = if (selectedNames.isNotEmpty() && hargaPerOrang != null) {
                selectedNames.joinToString("\n") {
                    context.getString(R.string.bayar_per_orang, it, hargaPerOrang)
                }
            } else {
                context.getString(R.string.bayar) + ": -"
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedButton(
                    onClick = {
                        hariTanggal = ""
                        namaTempat = ""
                        totalHarga = ""
                        diraChecked = false
                        radiChecked = false
                        iradChecked = false
                        showResult = false
                        hariTanggalError = false
                        namaTempatError = false
                        totalHargaError = false
                        checkBoxError = false
                    }
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "Reset", modifier = Modifier.padding(end = 8.dp))
                    Text(text = stringResource(R.string.reset))
                }

                OutlinedButton(
                    onClick = {
                        val message = context.getString(
                            R.string.bagikan_template,
                            hariTanggal,
                            namaTempat,
                            totalHarga,
                            bayarList
                        )
                        shareDate(context, message)
                    }
                ) {
                    Icon(Icons.Default.Share, contentDescription = "Bagikan", modifier = Modifier.padding(end = 8.dp))
                    Text(text = stringResource(R.string.bagikan))
                }
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

@SuppressLint("QueryPermissionsNeeded")
private fun shareDate(context: Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT,message)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(shareIntent)
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    Assesment1Theme {
        MainScreen(rememberNavController())
    }
}
