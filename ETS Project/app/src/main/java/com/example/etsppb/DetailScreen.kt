package com.example.etsppb

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

//show up seluruh data tiap anggota
@Composable
fun DetailScreen(m: Member, modifier: Modifier, onBack: () -> Unit, onEdit: () -> Unit) {
    Column(modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Detail Anggota", style = MaterialTheme.typography.headlineSmall)
        Baris("Nama Lengkap", m.nama)
        Baris("NIM / NIP", m.nomorInduk)
        Baris("Program Studi", m.prodi)
        Baris("Alamat", m.alamat)
        Baris("Nomor Telepon", m.telepon)
        Baris("Email", m.email)
        Baris("Jenis Keanggotaan", m.jenis)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onEdit) { Text("Edit") }
            OutlinedButton(onClick = onBack) { Text("Kembali") }
        }
    }
}

@Composable
private fun Baris(label: String, isi: String) {
    Column {
        Text(label, style = MaterialTheme.typography.labelMedium)
        Text(isi, fontWeight = FontWeight.Bold)
    }
}
