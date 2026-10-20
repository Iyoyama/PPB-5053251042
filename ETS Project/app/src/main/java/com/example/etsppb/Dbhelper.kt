package com.example.etsppb

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//Class untuk SQLite (buat tabel + pencarian + Create, Read, update, delete)
class DbHelper(context: Context) : SQLiteOpenHelper(context, "libraryreg.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """CREATE TABLE member (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nama TEXT NOT NULL,
                nomor_induk TEXT NOT NULL UNIQUE,
                prodi TEXT NOT NULL,
                alamat TEXT NOT NULL,
                telepon TEXT NOT NULL,
                email TEXT NOT NULL,
                jenis TEXT NOT NULL)"""
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS member")
        onCreate(db)
    }

    //mengubah Member menjadi pasangan kolom-nilai untuk disimpan
    private fun toValues(m: Member) = ContentValues().apply {
        put("nama", m.nama)
        put("nomor_induk", m.nomorInduk)
        put("prodi", m.prodi)
        put("alamat", m.alamat)
        put("telepon", m.telepon)
        put("email", m.email)
        put("jenis", m.jenis)
    }

    // CREATE
    fun insert(m: Member): Boolean =
        writableDatabase.insert("member", null, toValues(m)) != -1L

    // UPDATE
    fun update(m: Member): Boolean =
        writableDatabase.update("member", toValues(m), "id=?", arrayOf(m.id.toString())) > 0

    // DELETE
    fun delete(id: Int): Boolean =
        writableDatabase.delete("member", "id=?", arrayOf(id.toString())) > 0

    // READ semua data, atau SEARCH berdasarkan nama / nomor induk
    fun getAll(keyword: String = ""): List<Member> {
        val hasil = mutableListOf<Member>()
        val cursor = readableDatabase.rawQuery(
            "SELECT * FROM member WHERE nama LIKE ? OR nomor_induk LIKE ? ORDER BY nama",
            arrayOf("%$keyword%", "%$keyword%")
        )
        cursor.use {
            while (it.moveToNext()) {
                hasil.add(
                    Member(
                        id = it.getInt(0), nama = it.getString(1), nomorInduk = it.getString(2),
                        prodi = it.getString(3), alamat = it.getString(4), telepon = it.getString(5),
                        email = it.getString(6), jenis = it.getString(7)
                    )
                )
            }
        }
        return hasil
    }

    // Cek jika nomor induk sudah dipakai anggota lain (unique validation)
    fun nomorIndukSudahAda(nomor: String, kecualiId: Int = 0): Boolean {
        val c = readableDatabase.rawQuery(
            "SELECT 1 FROM member WHERE nomor_induk=? AND id<>?",
            arrayOf(nomor, kecualiId.toString())
        )
        return c.use { it.count > 0 }
    }
}
