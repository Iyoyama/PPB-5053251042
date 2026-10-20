package com.example.etsppb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

//daftar halaman aplikasi
sealed class Screen {
    object Daftar : Screen()
    object Tambah : Screen()
    class Detail(val m: Member) : Screen()
    class Edit(val m: Member) : Screen()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //status bar dan navigation bar transparan
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT
            )
        )
        val db = DbHelper(this)
        setContent {
            LibraryTheme {
                Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    LibraryRegApp(db)
                }
            }
        }
    }
}

@Composable
fun LibraryRegApp(db: DbHelper) {
    //halaman aktif disimpan di variabel screen
    var screen by remember { mutableStateOf<Screen>(Screen.Daftar) }
    val kembali = { screen = Screen.Daftar }

    //tombol back HP kembali ke daftar
    BackHandler(enabled = screen != Screen.Daftar) { kembali() }

    //transisi halus antar halaman
    Crossfade(targetState = screen, label = "halaman") { s ->
        when (s) {
            Screen.Daftar -> DaftarScreen(
                db,
                onTambah = { screen = Screen.Tambah },
                onDetail = { screen = Screen.Detail(it) },
                onEdit = { screen = Screen.Edit(it) }
            )
            Screen.Tambah -> FormScreen(db, null, kembali)
            is Screen.Detail -> DetailScreen(s.m, kembali, onEdit = { screen = Screen.Edit(s.m) })
            is Screen.Edit -> FormScreen(db, s.m, kembali)
        }
    }
}
