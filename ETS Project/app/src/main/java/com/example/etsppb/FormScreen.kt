package com.example.etsppb

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

// untuk TAMBAH (member = null) dan EDIT (member berisi data lama)
@Composable
fun FormScreen(db: DbHelper, member: Member?, modifier: Modifier, onBack: () -> Unit) {
    val context = LocalContext.current

    // state buat tiap kolom; kalau edit, diisi data lama
    var nama by remember { mutableStateOf(member?.nama ?: "") }
    var induk by remember { mutableStateOf(member?.nomorInduk ?: "") }
    var prodi by remember { mutableStateOf(member?.prodi ?: "") }
    var alamat by remember { mutableStateOf(member?.alamat ?: "") }
    var telepon by remember { mutableStateOf(member?.telepon ?: "") }
    var email by remember { mutableStateOf(member?.email ?: "") }
    var jenis by remember { mutableStateOf(member?.jenis ?: "Mahasiswa") }
    var errors by remember { mutableStateOf(mapOf<String, String>()) }

    Column(
        modifier.padding(16.dp).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            if (member == null) "Formulir Pendaftaran" else "Edit Data Anggota",
            style = MaterialTheme.typography.headlineSmall
        )

        Field("Nama Lengkap", nama, { nama = it }, errors["nama"])
        Field("NIM / NIP", induk, { induk = it }, errors["induk"], KeyboardType.Number)
        Field("Program Studi / Unit", prodi, { prodi = it }, errors["prodi"])
        Field("Alamat", alamat, { alamat = it }, errors["alamat"])
        Field("Nomor Telepon", telepon, { telepon = it }, errors["telepon"], KeyboardType.Phone)
        Field("Email", email, { email = it }, errors["email"], KeyboardType.Email)

        //jenis keanggotaan
        Text("Jenis Keanggotaan")
        Row {
            listOf("Mahasiswa", "Dosen", "Staf").forEach { pilihan ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = jenis == pilihan, onClick = { jenis = pilihan })
                    Text(pilihan)
                    Spacer(Modifier.width(8.dp))
                }
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                val data = Member(
                    id = member?.id ?: 0, nama = nama.trim(), nomorInduk = induk.trim(),
                    prodi = prodi.trim(), alamat = alamat.trim(), telepon = telepon.trim(),
                    email = email.trim(), jenis = jenis
                )
                errors = validasi(data, db)            //validasi
                if (errors.isEmpty()) {                //true -> simpan
                    if (member == null) db.insert(data) else db.update(data)
                    Toast.makeText(
                        context,
                        if (member == null) "Pendaftaran berhasil" else "Data diperbarui",
                        Toast.LENGTH_SHORT
                    ).show()
                    onBack()
                }
            }) { Text(if (member == null) "Daftar" else "Simpan") }

            OutlinedButton(onClick = onBack) { Text("Batal") }
        }
    }
}

// Satu kolom input + pesan error
@Composable
fun Field(
    label: String, value: String, onChange: (String) -> Unit,
    error: String?, keyboard: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        isError = error != null,
        supportingText = { if (error != null) Text(error) },
        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = keyboard),
        modifier = Modifier.fillMaxWidth()
    )
}
