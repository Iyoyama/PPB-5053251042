package com.example.etsppb

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

//show up seluruh data tiap anggota
@Composable
fun DetailScreen(m: Member, onBack: () -> Unit, onEdit: () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        Header(judul = "Detail Anggota", onBack = onBack)

        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            //id card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    Modifier.fillMaxWidth().padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Avatar(m.nama, ukuran = 72.dp)
                    Spacer(Modifier.height(10.dp))
                    Text(m.nama, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(6.dp))
                    JenisBadge(m.jenis)
                }
            }

            //detail id card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Baris(Icons.Default.AccountBox, "NRP", m.nomorInduk)
                    Baris(Icons.Default.Info, "Program Studi / Unit", m.prodi)
                    Baris(Icons.Default.LocationOn, "Alamat", m.alamat)
                    Baris(Icons.Default.Phone, "Nomor Telepon", m.telepon)
                    Baris(Icons.Default.Email, "Email", m.email)
                }
            }

            Button(
                onClick = onEdit,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth().height(52.dp)
            ) {
                Icon(Icons.Default.Edit, contentDescription = null, Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Edit Data", fontWeight = FontWeight.Bold)
            }
            OutlinedButton(
                onClick = onBack,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth().height(52.dp)
            ) { Text("Kembali") }

            Spacer(Modifier.navigationBarsPadding())
        }
    }
}

//barisinformasi: ikon + label kecil + isi
@Composable
private fun Baris(ikon: ImageVector, label: String, isi: String) {
    Row(verticalAlignment = Alignment.Top) {
        Icon(ikon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.width(12.dp))
        Column {
            Text(
                label, style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(isi, fontWeight = FontWeight.SemiBold)
        }
    }
}