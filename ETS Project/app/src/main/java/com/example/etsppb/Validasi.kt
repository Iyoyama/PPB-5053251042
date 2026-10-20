package com.example.etsppb

import android.util.Patterns

//mengembalikan jika daftar error. kalau kosong = (data valid)
fun validasi(m: Member, db: DbHelper): Map<String, String> {
    val e = mutableMapOf<String, String>()

    // 1) Kolom wajib
    if (m.nama.isBlank()) e["nama"] = "Nama lengkap wajib diisi"
    if (m.prodi.isBlank()) e["prodi"] = "Program studi wajib diisi"
    if (m.alamat.isBlank()) e["alamat"] = "Alamat wajib diisi"
    if (m.telepon.isBlank()) e["telepon"] = "Nomor telepon wajib diisi"

    // 2) Format email
    if (m.email.isBlank()) e["email"] = "Email wajib diisi"
    else if (!Patterns.EMAIL_ADDRESS.matcher(m.email).matches())
        e["email"] = "Format email tidak valid"

    // 3) Nomor induk: wajib
    if (m.nomorInduk.isBlank()) e["induk"] = "NIM/NIP wajib diisi"
    else if (!m.nomorInduk.all { it.isDigit() } || m.nomorInduk.length !in 8..18)
        e["induk"] = "NIM/NIP harus berupa angka 8-18 digit"
    else if (db.nomorIndukSudahAda(m.nomorInduk, m.id))
        e["induk"] = "NIM/NIP sudah terdaftar"

    return e
}