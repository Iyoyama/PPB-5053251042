package com.example.etsppb

import android.util.Patterns

//kembalikan daftar error, kosong berarti data valid
fun validasi(m: Member, db: DbHelper): Map<String, String> {
    val e = mutableMapOf<String, String>()

    //kolom wajib
    if (m.nama.isBlank()) e["nama"] = "Nama lengkap wajib diisi"
    if (m.prodi.isBlank()) e["prodi"] = "Program studi wajib diisi"
    if (m.alamat.isBlank()) e["alamat"] = "Alamat wajib diisi"
    if (m.telepon.isBlank()) e["telepon"] = "Nomor telepon wajib diisi"

    //format email
    if (m.email.isBlank()) e["email"] = "Email wajib diisi"
    else if (!Patterns.EMAIL_ADDRESS.matcher(m.email).matches())
        e["email"] = "Format email tidak valid"

    //nomor induk wajib, angka 8-18 digit, dan unik
    if (m.nomorInduk.isBlank()) e["induk"] = "NIM/NIP wajib diisi"
    else if (!m.nomorInduk.all { it.isDigit() } || m.nomorInduk.length !in 8..18)
        e["induk"] = "NIM/NIP harus berupa angka 8-18 digit"
    else if (db.nomorIndukSudahAda(m.nomorInduk, m.id))
        e["induk"] = "NIM/NIP sudah terdaftar"

    return e
}
