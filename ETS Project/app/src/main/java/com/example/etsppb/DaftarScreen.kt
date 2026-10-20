package com.example.etsppb

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DaftarScreen(
    db: DbHelper,
    onTambah: () -> Unit,
    onDetail: (Member) -> Unit,
    onEdit: (Member) -> Unit
) {
    //state kata kunci, daftar anggota, dan konfirmasi sebelum menghapus
    var keyword by remember { mutableStateOf("") }
    var daftar by remember { mutableStateOf(db.getAll()) }
    var akanDihapus by remember { mutableStateOf<Member?>(null) }

    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {

            Header("LibraryReg", "Pendaftaran Anggota Perpustakaan") {
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = keyword,
                    //filter setiap huruf diketik
                    onValueChange = { keyword = it; daftar = db.getAll(it) },
                    placeholder = { Text("Cari nama atau nomor induk") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Text(
                "${daftar.size} anggota",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
            )

            if (daftar.isEmpty()) {
                //tampilan saat data kosong
                Column(
                    Modifier.fillMaxSize().padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Icon(
                        Icons.Default.Person, contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text("Belum ada data anggota", fontWeight = FontWeight.Bold)
                    Text(
                        "Ketuk tombol Daftar Baru untuk menambah anggota",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 100.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(daftar, key = { it.id }) { m ->
                        //ketuk kartu untuk lihat detail
                        Card(
                            onClick = { onDetail(m) },
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Avatar(m.nama)
                                Spacer(Modifier.width(12.dp))
                                Column(Modifier.weight(1f)) {
                                    Text(
                                        m.nama, fontWeight = FontWeight.Bold,
                                        maxLines = 1, overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        m.nomorInduk,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    JenisBadge(m.jenis)
                                }
                                IconButton(onClick = { onEdit(m) }) {
                                    Icon(
                                        Icons.Default.Edit, contentDescription = "Edit",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                                IconButton(onClick = { akanDihapus = m }) {
                                    Icon(
                                        Icons.Default.Delete, contentDescription = "Hapus",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        //tombol melayang tambah anggota
        ExtendedFloatingActionButton(
            onClick = onTambah,
            icon = { Icon(Icons.Default.Add, contentDescription = null) },
            text = { Text("Daftar Baru") },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .navigationBarsPadding()
                .padding(16.dp)
        )
    }

    //dialog konfirmasi hapus
    akanDihapus?.let { m ->
        AlertDialog(
            onDismissRequest = { akanDihapus = null },
            title = { Text("Hapus anggota?") },
            text = { Text("Data ${m.nama} akan dihapus permanen.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        db.delete(m.id)
                        daftar = db.getAll(keyword)
                        akanDihapus = null
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) { Text("Hapus") }
            },
            dismissButton = { TextButton(onClick = { akanDihapus = null }) { Text("Batal") } }
        )
    }
}
