package com.example.etsppb

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//TEMA
private val SkemaWarna = lightColorScheme(
    primary = Color(0xFF1E5AA8),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFDCE8FA),
    onPrimaryContainer = Color(0xFF0B2E5C),
    background = Color(0xFFF5F7FB),
    surface = Color.White,
    error = Color(0xFFC62828)
)

@Composable
fun LibraryTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = SkemaWarna, content = content)
}

//HEADER
@Composable
fun Header(
    judul: String,
    subjudul: String? = null,
    onBack: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit = {}
) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.primaryContainer,
                RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
            )
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (onBack != null) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                }
            }
            Column {
                Text(
                    judul,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                if (subjudul != null) {
                    Text(
                        subjudul,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
        content()
    }
}

//AVATAR by Huruf depan Nama
@Composable
fun Avatar(nama: String, ukuran: Dp = 44.dp) {
    Box(
        Modifier
            .size(ukuran)
            .clip(CircleShape) //lingkaran avatarnya
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            nama.firstOrNull()?.uppercase() ?: "?",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = (ukuran.value / 2.4f).sp
        )
    }
}

//LABEL WARNA KEANGGOTAAN
@Composable
fun JenisBadge(jenis: String) {
    val (latar, teks) = when (jenis) {
        "Dosen" -> Color(0xFFEDE7F6) to Color(0xFF5E35B1)
        "Staf" -> Color(0xFFFFF3E0) to Color(0xFFE65100)
        else -> Color(0xFFE3F2FD) to Color(0xFF1565C0)   // Mahasiswa
    }
    Text(
        jenis,
        color = teks,
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .background(latar, RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}