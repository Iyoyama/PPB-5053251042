package com.example.etsppb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

//Daftar halaman
sealed class Screen {
    object Daftar : Screen()
    object Tambah : Screen()
    class Detail(val m: Member) : Screen()
    class Edit(val m: Member) : Screen()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = DbHelper(this)
        setContent {
            MaterialTheme {
                Scaffold { padding -> LibraryRegApp(db, Modifier.padding(padding)) }
            }
        }
    }
}

//Pengatur navigasi scree yang aktif
@Composable
fun LibraryRegApp(db: DbHelper, modifier: Modifier) {
    var screen by remember { mutableStateOf<Screen>(Screen.Daftar) }
    val kembali = { screen = Screen.Daftar }

    //Tombol back ke screen daftar
    BackHandler(enabled = screen != Screen.Daftar) { kembali() }

    when (val s = screen) {
        Screen.Daftar -> DaftarScreen(
            db, modifier,
            onTambah = { screen = Screen.Tambah },
            onDetail = { screen = Screen.Detail(it) },
            onEdit = { screen = Screen.Edit(it) }
        )
        Screen.Tambah -> FormScreen(db, null, modifier, kembali)
        is Screen.Detail -> DetailScreen(s.m, modifier, kembali, onEdit = { screen = Screen.Edit(s.m) })
        is Screen.Edit -> FormScreen(db, s.m, modifier, kembali)
    }
}