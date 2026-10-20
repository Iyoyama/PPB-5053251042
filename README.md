#REPOSITORY TERBARU - "ETS PPB 2026"
----------------------------------------------
# LibraryReg

**Aplikasi Android untuk pendaftaran anggota perpustakaan.** Daftar lewat HP, nggak perlu lagi antre dan isi formulir kertas.

Dibuat dengan **Kotlin**, **Jetpack Compose**, dan **SQLite**.

---

## Daftar Isi
1. [Tentang Aplikasi](#tentang-aplikasi)
2. [Fitur](#fitur)
3. [Tampilan Aplikasi (UI)](#tampilan-aplikasi-ui)
4. [Alur Penggunaan](#alur-penggunaan)
5. [Penjelasan Kode](#penjelasan-kode)
6. [Cara Menjalankan](#cara-menjalankan)
7. [Batasan](#batasan)
8. [Rencana Pengembangan](#rencana-pengembangan)

---

## Tentang Aplikasi

### Masalahnya apa sih?
Di banyak perpustakaan kampus, daftar anggota masih pakai **formulir kertas**. Akibatnya:

- **Antre panjang**, karena satu per satu harus isi dan diproses.
- **Data sering salah atau tidak lengkap**, misalnya tulisan susah dibaca atau ada kolom yang terlewat.
- **Susah cari data anggota**, karena harus bongkar tumpukan kertas.
- ⏳ **Kartu anggota lama jadi**, karena datanya harus dicatat ulang dulu.

### Solusinya
**LibraryReg** memindahkan semua itu ke HP. Calon anggota tinggal isi formulir di aplikasi, lalu aplikasi langsung mengecek apakah datanya sudah benar. Petugas bisa melihat daftar anggota, mencari, mengubah, atau menghapus data kapan saja.

### Tujuan
- Pendaftaran jadi lebih cepat.
- Salah catat berkurang karena ada validasi otomatis.
- Data anggota gampang dicari dan dikelola.
- Sebagai bahan belajar membuat aplikasi Android dari kasus nyata, dengan fitur yang dikembangkan bertahap.

---

## Fitur

### A. Formulir Pendaftaran
Calon anggota mengisi:
- Nama lengkap
- NIM / NIP
- Program studi / unit
- Alamat
- Nomor telepon
- Email
- Jenis keanggotaan (Mahasiswa, Dosen, atau Staf)

### B. Validasi Data
Sebelum disimpan, aplikasi mengecek dulu:

| Yang dicek | Aturannya |
|---|---|
| Kolom wajib | Semua kolom harus diisi |
| Format email | Harus berbentuk email yang benar, contoh `nama@kampus.ac.id` |
| NIM / NIP | Hanya angka, panjang 8 sampai 18 digit |
| NIM / NIP unik | Tidak boleh sama dengan anggota lain yang sudah terdaftar |

Kalau ada yang salah, kolomnya jadi merah dan muncul pesan di bawahnya.

### C. Manajemen Anggota
- Lihat daftar anggota
- Lihat detail anggota
- Cari berdasarkan nama atau nomor induk
- Edit data
- Hapus data (ada konfirmasi dulu, jadi aman dari salah pencet)

---

## Tampilan Aplikasi (UI)

Aplikasi punya **3 halaman** dan **1 dialog**. Warnanya dominan biru, bersih, dan memakai komponen Material Design 3.

> Taruh screenshot di folder `screenshots/` lalu ganti nama file di bawah ini.

### 1. Halaman Daftar Anggota (halaman utama)
![Daftar](screenshots/daftar.png)

Ini halaman pertama yang muncul saat aplikasi dibuka.

| Bagian | Fungsinya |
|---|---|
| Header biru muda | Menampilkan nama aplikasi "LibraryReg" |
| Kolom pencarian | Ketik nama atau nomor induk, daftar langsung menyaring |
| Teks "n anggota" | Jumlah anggota yang sedang tampil |
| Kartu anggota | Berisi avatar huruf depan nama, nama, nomor induk, dan label jenis keanggotaan |
| Label jenis | Warnanya beda: biru untuk Mahasiswa, ungu untuk Dosen, oranye untuk Staf |
| Ikon pensil dan tempat sampah | Edit dan hapus anggota tersebut |
| Tombol **Daftar Baru** | Tombol melayang di pojok kanan bawah untuk menambah anggota |

**Tips:** ketuk kartunya untuk melihat detail anggota.
Kalau data masih kosong, muncul tulisan *"Belum ada data anggota"*.

### 2. Dialog Konfirmasi Hapus
![Hapus](screenshots/hapus.png)

Muncul waktu ikon tempat sampah ditekan. Isinya menyebut nama anggota yang mau dihapus, dengan pilihan **Hapus** atau **Batal**.

### 3. Halaman Formulir Pendaftaran
![Formulir](screenshots/formulir.png)

| Bagian | Fungsinya |
|---|---|
| Kelompok **Data Diri** | Nama lengkap, NIM/NIP, program studi |
| Kelompok **Kontak** | Alamat, nomor telepon, email |
| Chip **Jenis Keanggotaan** | Pilih salah satu: Mahasiswa, Dosen, atau Staf |
| Tombol **Daftar** | Cek validasi, lalu simpan kalau sudah benar |
| Tombol **Batal** | Kembali ke daftar tanpa menyimpan |
| Pesan error merah | Muncul di bawah kolom yang bermasalah |
| Toast | Notifikasi "Pendaftaran berhasil" setelah tersimpan |

Keyboard juga menyesuaikan: kolom NIM/NIP memunculkan keyboard angka, telepon keyboard nomor, dan email keyboard email.

### 4. Halaman Edit
Tampilannya sama dengan formulir, hanya saja judulnya "Edit Data Anggota", kolom sudah terisi data lama, dan tombolnya bernama **Simpan Perubahan**. Aturan validasinya sama persis.

### 5. Halaman Detail Anggota
![Detail](screenshots/detail.png)

Berisi kartu profil (avatar besar, nama, jenis keanggotaan) dan kartu informasi lengkap (NIM/NIP, prodi, alamat, telepon, email), plus tombol **Edit Data** dan **Kembali**.

---

## Alur Penggunaan

### Daftar sebagai anggota baru
1. Buka aplikasi, kamu masuk ke halaman **Daftar Anggota**.
2. Ketuk tombol **Daftar Baru**.
3. Isi semua kolom di formulir, lalu pilih jenis keanggotaan.
4. Ketuk **Daftar**.
5. Kalau ada yang salah, perbaiki sesuai pesan merah, lalu ketuk **Daftar** lagi.
6. Kalau sudah benar, muncul notifikasi "Pendaftaran berhasil" dan kamu kembali ke daftar. Data barumu sudah ada di sana.

### Cari anggota
1. Di halaman daftar, ketik nama atau nomor induk di kolom pencarian.
2. Daftar langsung menyaring setiap kali kamu mengetik.
3. Hapus tulisan di kolom pencarian untuk menampilkan semua anggota lagi.

### Lihat detail
1. Ketuk kartu anggota.
2. Semua datanya tampil. Ketuk **Kembali** (atau tombol Back di HP) untuk balik ke daftar.

### Edit data
1. Ketuk ikon pensil di kartu anggota (atau tombol **Edit Data** di halaman detail).
2. Ubah data yang perlu diganti.
3. Ketuk **Simpan Perubahan**.

### Hapus data
1. Ketuk ikon tempat sampah di kartu anggota.
2. Baca dialog konfirmasinya.
3. Ketuk **Hapus** kalau yakin, atau **Batal** kalau tidak jadi.

### Peta perpindahan halaman

```
Daftar Anggota ──(Daftar Baru)──► Formulir ──(Daftar / Batal)──► Daftar Anggota
      │
      ├──(ketuk kartu)──► Detail ──(Edit Data)──► Edit ──(Simpan / Batal)──► Daftar Anggota
      ├──(ikon pensil)──────► Edit
      └──(ikon tempat sampah)──────► Dialog Hapus ──► Daftar Anggota
```

Tombol **Back** di HP selalu membawa kamu kembali ke Daftar Anggota.

---

## Penjelasan Kode

### Struktur file

```
com.example.etsppb
├── MainActivity.kt → pintu masuk aplikasi + pengatur halaman
├── Member.kt       → bentuk data satu anggota
├── DbHelper.kt     → urusan database (simpan, ubah, hapus, cari)
├── Validasi.kt     → aturan pengecekan data
├── Komponen.kt     → tema warna + komponen UI yang dipakai ulang
├── DaftarScreen.kt → halaman daftar + pencarian + hapus
├── FormScreen.kt   → halaman formulir (tambah & edit)
└── DetailScreen.kt → halaman detail
```

### Cara kerja singkatnya
Datanya mengalir seperti ini:

```
Pengguna isi formulir → Validasi.kt cek data → DbHelper.kt simpan ke SQLite → DaftarScreen.kt tampilkan
```

### 1. `Member.kt` — bentuk data
Satu objek `Member` mewakili satu anggota.

```kotlin
data class Member(
    val id: Int = 0,
    val nama: String,
    val nomorInduk: String,
    val prodi: String,
    val alamat: String,
    val telepon: String,
    val email: String,
    val jenis: String
)
```

### 2. `DbHelper.kt` — database
Kelas ini mengurus database **SQLite** bawaan Android, jadi tidak perlu library tambahan. Ada satu tabel bernama `member`. Kolom `nomor_induk` diberi `UNIQUE` supaya tidak ada nomor yang kembar.

| Fungsi | Tugasnya |
|---|---|
| `onCreate()` | Membuat tabel `member` saat aplikasi pertama kali dijalankan |
| `insert(m)` | Menambah anggota baru |
| `update(m)` | Mengubah data anggota |
| `delete(id)` | Menghapus anggota |
| `getAll(keyword)` | Mengambil semua anggota, atau yang cocok dengan `keyword` |
| `nomorIndukSudahAda()` | Mengecek apakah nomor induk sudah dipakai orang lain |

Pencarian memakai query `LIKE`, jadi mengetik sebagian nama saja sudah cukup:

```kotlin
"SELECT * FROM member WHERE nama LIKE ? OR nomor_induk LIKE ? ORDER BY nama"
```

Kalau `keyword` kosong, semua baris cocok, jadi fungsi yang sama dipakai untuk menampilkan semua data.

### 3. `Validasi.kt` — pengecekan data
Fungsi `validasi()` menerima data anggota dan mengembalikan **daftar error**. Kalau daftarnya kosong, berarti data aman untuk disimpan.

```kotlin
//nomor induk wajib, angka 8-18 digit, dan unik
if (m.nomorInduk.isBlank()) e["induk"] = "NIM/NIP wajib diisi"
else if (!m.nomorInduk.all { it.isDigit() } || m.nomorInduk.length !in 8..18)
    e["induk"] = "NIM/NIP harus berupa angka 8-18 digit"
else if (db.nomorIndukSudahAda(m.nomorInduk, m.id))
    e["induk"] = "NIM/NIP sudah terdaftar"
```

Parameter `m.id` dipakai supaya saat **edit**, nomor induk milik anggota itu sendiri tidak dianggap duplikat.
Format email dicek memakai `Patterns.EMAIL_ADDRESS` bawaan Android.

### 4. `FormScreen.kt` — formulir tambah dan edit
Satu layar dipakai untuk dua keperluan:

- `member == null` → **mode tambah**, kolom kosong.
- `member` berisi data → **mode edit**, kolom terisi data lama.

Setiap kolom punya *state* sendiri (nilai yang berubah saat diketik):

```kotlin
var nama by remember { mutableStateOf(member?.nama ?: "") }
```

Saat tombol ditekan, urutannya begini:

```kotlin
errors = validasi(data, db) //1. cek dulu
if (errors.isEmpty()) { //2. kalau tidak ada error
    if (modeTambah) db.insert(data) else db.update(data) //3. simpan
    onBack() //4. kembali ke daftar
}
```

Fungsi `Field()` adalah kolom isian yang dipakai berulang (lengkap dengan ikon dan pesan error), jadi kodenya tidak diulang-ulang.

### 5. `DaftarScreen.kt` — daftar, cari, hapus
Ada tiga *state* utama:

```kotlin
var keyword by remember { mutableStateOf("") } //kata kunci pencarian
var daftar by remember { mutableStateOf(db.getAll()) } //data yang ditampilkan
var akanDihapus by remember { mutableStateOf<Member?>(null) } //anggota yang mau dihapus
```

- **Pencarian:** setiap huruf diketik, `daftar` langsung diisi ulang dari database.
  ```kotlin
  onValueChange = { keyword = it; daftar = db.getAll(it) }
  ```
- **Daftar:** memakai `LazyColumn`, yaitu daftar yang hanya menggambar item yang terlihat di layar, jadi tetap ringan walau datanya banyak.
- **Hapus:** ikon tempat sampah tidak langsung menghapus, tapi mengisi `akanDihapus`. Selama isinya tidak `null`, dialog konfirmasi tampil. Data baru benar-benar dihapus kalau pengguna menekan **Hapus** di dialog.

### 6. `DetailScreen.kt` — detail anggota
Menerima satu `Member` lalu menampilkannya. Fungsi `Baris()` dipakai berulang untuk setiap pasangan ikon, label, dan isi.

### 7. `Komponen.kt` — tema dan komponen bersama
Berisi bagian yang dipakai di banyak halaman supaya tampilannya seragam:

| Komponen | Fungsinya |
|---|---|
| `LibraryTheme` | Tema warna biru untuk seluruh aplikasi |
| `Header` | Header berlatar biru muda di bagian atas halaman |
| `Avatar` | Lingkaran berisi huruf depan nama |
| `JenisBadge` | Label jenis keanggotaan dengan warna berbeda |

### 8. `MainActivity.kt` — pengatur halaman
Aplikasi ini hanya punya **satu Activity**. Pindah halaman dilakukan dengan menyimpan "halaman yang sedang aktif" di satu variabel:

```kotlin
sealed class Screen {
    object Daftar : Screen()
    object Tambah : Screen()
    class Detail(val m: Member) : Screen()
    class Edit(val m: Member) : Screen()
}

var screen by remember { mutableStateOf<Screen>(Screen.Daftar) }
```

Setiap kali `screen` berubah, `when` memilih halaman yang cocok untuk ditampilkan. `Crossfade` memberi efek transisi halus, dan `BackHandler` membuat tombol Back HP kembali ke daftar, bukan langsung keluar aplikasi.

---

## Cara Menjalankan

**Yang dibutuhkan:** Android Studio versi terbaru, dan emulator atau HP Android.

1. Buat proyek baru di Android Studio: **New Project → Empty Activity**.
2. Isi nama proyek (di sini `etsppb`), bahasa **Kotlin**.
3. Salin semua file `.kt` dari repositori ini ke folder `app/src/main/java/com/example/etsppb/`. File `MainActivity.kt` bawaan boleh ditimpa.
4. Pastikan baris pertama setiap file sama dengan package proyekmu:
   ```kotlin
   package com.example.etsppb
   ```
5. Klik **Sync Project with Gradle Files** kalau muncul tawaran.
6. Tekan tombol **Run ▶** dan pilih emulator atau HP-mu.

Tidak perlu menambah dependency apa pun, semuanya sudah ada di template Empty Activity.

---

## Batasan

Biar jelas, ini yang **belum** ada di versi ini:

- Data tersimpan **lokal di HP** (SQLite), belum tersambung ke server. Jadi data tidak dibagikan antar perangkat.
- Belum ada **login**, jadi calon anggota dan petugas memakai tampilan yang sama.
- Belum ada fitur **cetak atau pembuatan kartu anggota**.

---

## Rencana Pengembangan

Ide untuk tahap berikutnya:

- [ ] Login untuk petugas
- [ ] Cetak / ekspor kartu anggota (PDF)
- [ ] Sinkronisasi ke server agar data tersimpan online
- [ ] Foto anggota di kartu
- [ ] Filter daftar berdasarkan jenis keanggotaan

---

## Teknologi

| Komponen | Teknologi |
|---|---|
| Bahasa | Kotlin |
| UI | Jetpack Compose + Material Design 3 |
| Database | SQLite (`SQLiteOpenHelper`) |
| IDE | Android Studio |

---

## Pembuat

**Nama kamu** — NIM kamu
Mata kuliah / tugas: *(isi di sini)*
