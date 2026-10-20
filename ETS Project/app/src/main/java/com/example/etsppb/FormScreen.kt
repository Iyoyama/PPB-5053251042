package com.example.etsppb

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

//TAMBAH (member = null) dan EDIT (member berisi data lama)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(db: DbHelper, member: Member?, onBack: () -> Unit) {
    val context = LocalContext.current
    val modeTambah = member == null

    var nama by remember { mutableStateOf(member?.nama ?: "") }
    var induk by remember { mutableStateOf(member?.nomorInduk ?: "") }
    var prodi by remember { mutableStateOf(member?.prodi ?: "") }
    var alamat by remember { mutableStateOf(member?.alamat ?: "") }
    var telepon by remember { mutableStateOf(member?.telepon ?: "") }
    var email by remember { mutableStateOf(member?.email ?: "") }
    var jenis by remember { mutableStateOf(member?.jenis ?: "Mahasiswa") }
    var errors by remember { mutableStateOf(mapOf<String, String>()) }

    Column(Modifier.fillMaxSize()) {
        Header(
            judul = if (modeTambah) "Formulir Pendaftaran" else "Edit Data Anggota",
            subjudul = "Isi data dengan lengkap dan benar",
            onBack = onBack
        )

        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(16.dp)
        ) {
            Bagian("Data Diri")
            Field("Nama Lengkap", nama, { nama = it }, errors["nama"], Icons.Default.Person)
            Field("NRP", induk, { induk = it }, errors["induk"], Icons.Default.AccountBox, KeyboardType.Number)
            Field("Program Studi / Unit", prodi, { prodi = it }, errors["prodi"], Icons.Default.Info)

            Bagian("Kontak")
            Field("Alamat", alamat, { alamat = it }, errors["alamat"], Icons.Default.LocationOn, minLines = 2)
            Field("Nomor Telepon", telepon, { telepon = it }, errors["telepon"], Icons.Default.Phone, KeyboardType.Phone)
            Field("Email", email, { email = it }, errors["email"], Icons.Default.Email, KeyboardType.Email)

            Bagian("Jenis Keanggotaan")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Mahasiswa", "Dosen", "Staf").forEach { pilihan ->
                    FilterChip(
                        selected = jenis == pilihan,
                        onClick = { jenis = pilihan },
                        label = { Text(pilihan) },
                        leadingIcon = if (jenis == pilihan) {
                            { Icon(Icons.Default.Check, contentDescription = null, Modifier.size(18.dp)) }
                        } else null
                    )
                }
            }

            Spacer(Modifier.height(24.dp))
            Button(
                onClick = {
                    val data = Member(
                        id = member?.id ?: 0, nama = nama.trim(), nomorInduk = induk.trim(),
                        prodi = prodi.trim(), alamat = alamat.trim(), telepon = telepon.trim(),
                        email = email.trim(), jenis = jenis
                    )
                    errors = validasi(data, db)           // 1) validasi
                    if (errors.isEmpty()) {               // 2) kalau lolos, simpan
                        if (modeTambah) db.insert(data) else db.update(data)
                        Toast.makeText(
                            context,
                            if (modeTambah) "Pendaftaran berhasil" else "Data diperbarui",
                            Toast.LENGTH_SHORT
                        ).show()
                        onBack()
                    }
                },
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth().height(52.dp)
            ) { Text(if (modeTambah) "Daftar" else "Simpan Perubahan", fontWeight = FontWeight.Bold) }

            Spacer(Modifier.height(8.dp))
            OutlinedButton(
                onClick = onBack,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth().height(52.dp)
            ) { Text("Batal") }

            Spacer(Modifier.navigationBarsPadding())
        }
    }
}

// Judul kecil pemisah kelompok isian
@Composable
private fun Bagian(judul: String) {
    Text(
        judul,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(top = 12.dp, bottom = 6.dp)
    )
}

//kolom input: ikon + label + pesan error
@Composable
fun Field(
    label: String, value: String, onChange: (String) -> Unit,
    error: String?, ikon: ImageVector,
    keyboard: KeyboardType = KeyboardType.Text, minLines: Int = 1
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        leadingIcon = { Icon(ikon, contentDescription = null) },
        isError = error != null,
        supportingText = { if (error != null) Text(error) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboard),
        minLines = minLines,
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.fillMaxWidth()
    )
}