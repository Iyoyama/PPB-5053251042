package com.example.etsppb

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

// Homescreen: daftar anggota + pencarian + tombol tambah/detail/edit/hapus
@Composable
fun DaftarScreen(
    db: DbHelper, modifier: Modifier,
    onTambah: () -> Unit, onDetail: (Member) -> Unit, onEdit: (Member) -> Unit
) {
    var keyword by remember { mutableStateOf("") }
    var daftar by remember { mutableStateOf(db.getAll()) }
    var akanDihapus by remember { mutableStateOf<Member?>(null) }

    Column(modifier.padding(16.dp)) {
        Text("LibraryReg", style = MaterialTheme.typography.headlineMedium)
        Text("Daftar Anggota Perpustakaan")
        Spacer(Modifier.height(8.dp))

        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            //filter setiap huruf diketik
            OutlinedTextField(
                value = keyword,
                onValueChange = { keyword = it; daftar = db.getAll(it) },
                label = { Text("Cari nama atau nomor induk") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            Button(onClick = onTambah) { Text("+ Tambah") }
        }

        Text("Total: ${daftar.size} anggota", Modifier.padding(vertical = 8.dp))
        if (daftar.isEmpty()) Text("Belum ada data anggota.")

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(daftar, key = { it.id }) { m ->
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(12.dp)) {
                        Text(m.nama, fontWeight = FontWeight.Bold)
                        Text("${m.nomorInduk} • ${m.jenis}")
                        Row {
                            TextButton(onClick = { onDetail(m) }) { Text("Detail") }
                            TextButton(onClick = { onEdit(m) }) { Text("Edit") }
                            TextButton(onClick = { akanDihapus = m }) { Text("Hapus") }
                        }
                    }
                }
            }
        }
    }

    //State konfirmasi sebelum menghapus
    akanDihapus?.let { m ->
        AlertDialog(
            onDismissRequest = { akanDihapus = null },
            title = { Text("Hapus anggota?") },
            text = { Text("Data ${m.nama} akan dihapus permanen.") },
            confirmButton = {
                TextButton(onClick = {
                    db.delete(m.id)
                    daftar = db.getAll(keyword)
                    akanDihapus = null
                }) { Text("Hapus") }
            },
            dismissButton = { TextButton(onClick = { akanDihapus = null }) { Text("Batal") } }
        )
    }
}