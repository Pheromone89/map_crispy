package id.go.bpkp.mobilemapbpkp.konfigurasi;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

/**
 * Created by ASUS on 19/01/2018.
 */

public class konfigurasi {

    // INFORMASI STRATEGIS
    public static final String URL_GET_REALISASIPKPT = "http://118.97.51.140:10001/map/api/pkpt_realisasi?api_token=";
    // indikator
    public static final String URL_GET_JUMLAHPP = "http://118.97.51.140:10001/map/api/pkpt_fokuspengawasan?api_token=";
    public static final String URL_GET_INDIKATORPEMDA = "http://118.97.51.140:10001/map/api/indikator_pemda?api_token=";
    public static final String URL_GET_INDIKATORKL = "http://118.97.51.140:10001/map/api/indikator_kl?api_token=";
    // pkpt
    // fp

    //ALL PEGAWAI LENGKAP
    public static final String URL_GET_ALL = "http://118.97.51.140:10001/map/api/pegawai?api_token=";
    //ALL PEGAWAI
    public static final String URL_GET_ALLSMALL = "http://118.97.51.140:10001/map/api/pegawaiSingkat?api_token=";
    public static final String URL_GET_ALLBYUNIT = "http://118.97.51.140:10001/map/api/pegawaiUnit/";
    public static final String URL_GET_ALLBYQUERY = "http://118.97.51.140:10001/map/api/pegawaiLikeName/";
    //GET ARRAY JENIS PENUGASAN
    public static final String URL_GET_DAFTARKONFIRMASIPENUGASAN = "http://118.97.51.140:10001/map/api/listkonfirmasipenugasan/";
    public static final String URL_GET_JENISPENUGASAN = "http://118.97.51.140:10001/map/api/jenispenugasan";
    //LOGIN
    public static final String URL_LOGIN = "http://118.97.51.140:10001/map/api/login";
    //LOGOUT
    public static final String URL_LOGOUT = "http://118.97.51.140:10001/map/api/logout?api_token=";
    // UPDATE NO HP
    public static final String URL_POST_PHONE = "http://118.97.51.140:10001/map/api/setnomorhp/";
    // KONTEN DASHBOARD
    public static final String URL_GET_DASHBOARD_CONTENT_ADMIN = "http://118.97.51.140:10001/map/api/dashboardAdmin?api_token=";
    public static final String URL_GET_DASHBOARD_CONTENT_USER = "http://118.97.51.140:10001/map/api/absensi/";
    // DETAIL PEGAWAI
    public static final String URL_GET_EMP = "http://118.97.51.140:10001/map/api/pegawaiByNIP/";
    public static final String URL_GET_EMP_DIKLAT = "http://118.97.51.140:10001/map/api/historiDiklat/";
    public static final String URL_GET_EMP_UNIT = "http://118.97.51.140:10001/map/api/historiUnit/";
    public static final String URL_GET_EMP_JABATAN = "http://118.97.51.140:10001/map/api/historiJabatan/";
    public static final String URL_GET_EMP_ATASAN1 = "http://118.97.51.140:10001/map/api/atasan1/";
    public static final String URL_GET_EMP_ATASAN2 = "http://118.97.51.140:10001/map/api/atasan2/";
    public static final String URL_GET_EMP_ATASAN3 = "http://118.97.51.140:10001/map/api/atasan3/";
    // UDPATE
    public static final String URL_UPDATE_EMP = "none";
    // HAPUS
    public static final String URL_DELETE_EMP = "none";
    // CUTI
    public static final String URL_GET_EMP_REKAPCUTI = "http://118.97.51.140:10001/map/api/rekapcuti/";
    public static final String URL_GET_EMP_CUTIBAWAHANLANGSUNG = "http://118.97.51.140:10001/map/api/transaksicuti/";
    public static final String URL_GET_EMP_CUTIBAWAHANLANGSUNGCOUNT = "http://118.97.51.140:10001/map/api/transaksicuticount/";
    public static final String URL_PERSETUJUANCUTI = "http://118.97.51.140:10001/map/api/persetujuancuti?api_token=";
    public static final String URL_PENGAJUANCUTI = "http://118.97.51.140:10001/map/api/setcuti?api_token=";
    public static final String URL_GET_ABSENBAWAHAN = "http://118.97.51.140:10001/map/api/listAbsenBawahan/";
    public static final String URL_GET_DAFTARCUTI = "http://118.97.51.140:10001/map/api/listcuti/";
    public static final String URL_POST_VALIDASIPRESENSI = "http://118.97.51.140:10001/map/api/cekmacaddress?api_token=";
    // IZIN KANTOR
    public static final String URL_PENGAJUANIZINKANTOR = "http://118.97.51.140:10001/map/api/setizin?api_token=";
    // IZIN KANTOR
    // list transaksi per pegawai
    public static final String URL_GET_DAFTARIZINKANTOR = "http://118.97.51.140:10001/map/api/listizin/";
    // melihat pengajuan bawahan
    public static final String URL_GET_EMP_IZINKANTORBAWAHANLANGSUNG = "http://118.97.51.140:10001/map/api/transaksiizin/";
    // melihat jumlah doang
    public static final String URL_GET_EMP_IZINKANTORBAWAHANLANGSUNGCOUNT = "http://118.97.51.140:10001/map/api/transaksiizincount/";
    // post persetujuan
    public static final String URL_PERSETUJUANIZINKANTOR = "http://118.97.51.140:10001/map/api/persetujuanizin?api_token=";
    public static final String URL_PENGAJUANKONFIRMASIPENUGASAN = "http://118.97.51.140:10001/map/api/setkonfirmasipenugasan?api_token=";
    // PRESENSI
    public static final String URL_GET_ABSEN = "http://118.97.51.140:10001/map/api/dashboardAbsen/";
    public static final String URL_GET_HOTSPOT = "http://118.97.51.140:10001/map/api/dashboardKehadiran?api_token=";
    public static final String URL_POST_ABSEN = "http://118.97.51.140:10001/map/api/dashboardAbsen?api_token=";
    public static final String TAG_ABSEN_DATANG = "datang";
    public static final String TAG_ABSEN_PULANG = "pulang";
    public static final String TAG_ABSEN_TANGGAL = "event_date";
    public static final String TAG_ABSEN_WAKTUKERJA = "waktuKerja";
    public static final String TAG_ABSEN_STATUSDATANG = "statusDatang";
    public static final String TAG_ABSEN_STATUSPULANG = "statusPulang";
    // HISTORI PRESENSI
    public static final String URL_GET_ABSENALL = "http://118.97.51.140:10001/map/api/dashboardAbsenRekap/";
    // TUKIN
    public static final String URL_GET_TUKIN = "http://118.97.51.140:10001/map/api/tukin/";
    public static final String URL_GET_HOTSPOTALL = "http://118.97.51.140:10001/map/api/listkehadiran?api_token=";
    public static final String URL_GET_HOTSPOTDETAIL = "http://118.97.51.140:10001/map/api/detailkehadiran/";
    public static final int DASHBOARD_PANEL_NOTIFIKASIATASAN = 6;


    // TAG YG DIDAPET PAS LOGIN
    // root
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_USERTOKEN = "api_token";
    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_PEGAWAI = "pegawai";
    public static final String TAG_ATASAN = "atasan";
    public static final String TAG_BROADCAST = "broadcast";
    // child message
    public static final String TAG_ID = "id";
    public static final String TAG_NAMA = "nama";
    public static final String TAG_NAME = "name";
    public static final String TAG_EMAIL = "email";
    public static final String TAG_USERNAME = "username";
    public static final String TAG_ROLEID = "role_id";
    public static final String TAG_USERNIP = "user_nip";
    public static final String TAG_CREATEDAT = "created_at";
    public static final String TAG_UPDATEDAT = "updated_at";
    public static final String TAG_NOHP = "nomorhp";
    public static final String TAG_AKTIF = "aktif";
    public static final String TAG_KEYSORTUNIT = "key_sort_unit";
    public static final String TAG_NIPBARU = "nipbaru";
    public static final String TAG_ISLDAP = "is_ldap";
    public static final String TAG_ISJAB = "is_jab";
    public static final String TAG_VERSION = "version";
    public static final String TAG_ISHUT = "is_hut";
    public static final String TAG_URLFOTO = "url_foto";
    public static final String TAG_ISATASAN = "is_atasan";
    // child pegawai
    // id
    public static final String TAG_NIPLAMA = "niplama";
    // nipbaru
    public static final String TAG_NIPBARUGABUNG = "nip";
    // nama
    public static final String TAG_NAMALENGKAP = "s_nama_lengkap";
    public static final String TAG_CARINAMANIP = "carinama_nip";
    public static final String TAG_TEMPATLAHIR = "s_tempat_lahir";
    public static final String TAG_TANGGALLAHIR = "d_tgl_lahir";
    public static final String TAG_JENISKELAMIN = "jenis_kelamin";
    public static final String TAG_AGAMA = "s_nama_agama";
    public static final String TAG_USIA = "usia";
    public static final String TAG_TMTPENSIUN = "tmtpensiun";
    public static final String TAG_GOLRUANG = "golruang";
    public static final String TAG_PANGKAT = "s_nama_pangkat";
    public static final String TAG_TMTSK = "d_tmt_sk";
    public static final String TAG_LAMATHKP = "lamath_kp";
    public static final String TAG_LAMABLKP = "lamabl_kp";
    public static final String TAG_JABATAN = "jabatan";
    public static final String TAG_JABATANSINGKAT = "s_nmjabdetailskt";
    public static final String TAG_TMTJABATAN = "tmt_jab";
    public static final String TAG_KDJABATANDETAIL = "s_kd_jabdetail";
    public static final String TAG_JENISJABATAN = "jenis_jab";
    public static final String TAG_JENISJABATANGRUP = "jenis_jab_group";
    public static final String TAG_LAMATHJABATAN = "lamath_jab";
    public static final String TAG_LAMABLJABATAN = "lamabl_jab";
    public static final String TAG_PERAN = "peran";
    public static final String TAG_UNITORG = "s_nama_instansiunitorg";
    public static final String TAG_UNITORGSINGKAT = "s_skt_instansiunitorg";
    public static final String TAG_UNITORGLENGKAP = "namaunit_lengkap";
    public static final String TAG_TMTUNIT = "tmt_unit";
    public static final String TAG_KDUNITORG = "s_kd_instansiunitorg";
    public static final String TAG_KDUNITORGKERJA = "s_kd_instansiunitkerjal1";
    public static final String TAG_LAMATHUNIT = "lamath_unit";
    public static final String TAG_LAMABLUNIT = "lamabl_unit";
    public static final String TAG_UNIT = "namaunit";
    public static final String TAG_NAMAUNIT = "unit";
    public static final String TAG_TANGGAL_HOTSPOT = "tgl";
    public static final String TAG_TANGGAL_HOTSPOT_PANJANG = "tanggal";
    public static final String TAG_DATANG = "datang";
    public static final String TAG_PULANG = "pulang";
    public static final String TAG_NAMANIP = "namanip";
    public static final String TAG_KODE_UNIT = "kode_unit";
    public static final String TAG_ACCTID = "acctid";
    public static final String TAG_IP = "ip";
    public static final String TAG_HOTSPOT_STATUSDATANG = "status_datang";
    public static final String TAG_HOTSPOT_STATUSPULANG = "status_pulang";

    // keysort unit
    public static final String TAG_PENDIDIKANSINGKAT = "s_skt_pendidikan";
    public static final String TAG_PENDIDIKANSTRATASINGKAT = "s_nama_strata_skt";
    public static final String TAG_PENDIDIKANFAKULTAS = "s_nama_fakultasbidang";
    public static final String TAG_PENDIDIKANJURUSAN = "s_nama_jurusan";
    public static final String TAG_PENDIDIKANLULUS = "d_tgl_lulus";
    public static final String TAG_TOTALPAK = "total_pak";
    public static final String TAG_TGLAWALXX = "d_tgl_awal";
    public static final String TAG_TGLAKHIRXX = "d_tgl_akhir";
    public static final String TAG_NOSKKGB = "s_no_sk_kgb";
    public static final String TAG_THKGB = "i_maker_th_kgb";
    public static final String TAG_BLKGB = "i_maker_bl_kgb";
    public static final String TAG_TMTKGB = "d_tmt_kgb";
    public static final String TAG_NAMADIKLATFUNG = "s_nama_diklatfung";
    public static final String TAG_NOSERTDIKLATFUNG = "s_nosert_diklatfung";
    public static final String TAG_TGLSERTDIKLATFUNG = "d_tglser_diklatfung";
    public static final String TAG_DIKLATSTRUK = "diklat_struk";
    public static final String TAG_NOSERTDIKLATSTRUK = "s_nosert_diklatstruk";
    public static final String TAG_TGLSERTDIKLATSTRUK = "d_tglser_diklatstruk";
    public static final String TAG_SERTJFA = "sert_jfa";
    public static final String TAG_NAMAPASANGAN = "nama_pasangan";
    public static final String TAG_UNITPASANGAN = "unit_pasangan";
    public static final String TAG_ALAMAT = "s_alamat";
    public static final String TAG_SERTPROFESI = "sert_profesi";
    public static final String TAG_KELJABATAN = "kel_jab";
    public static final String TAG_STATUS = "status";
    public static final String TAG_IDSORT = "id_sort";
    public static final String TAG_TGLUPDATE = "tgl_update";
    // child atasan
    public static final String TAG_NIPATASAN = "s_nip";
    public static final String TAG_NAMAATASAN = "nama";
    public static final String TAG_NAMAGELARATASAN = "nama_lengkap";
    // child atasan
    public static final String TAG_STATUSBROADCAST = "status";
    public static final String TAG_IMAGEBROADCAST = "images";
    public static final String TAG_TITLEBROADCAST = "title";
    public static final String TAG_MESSAGEBROADCAST = "message";

    // ???
    public static final String TAG_TIDAKPUNYAATASANLANGSUNG = "tidak_punya_atasan_langsung";
    public static final String TAG_FOTO = "foto";
    public static final String TAG_INSTANSISINGKAT = "s_skt_instansi";
    public static final String TAG_TOEFL = "scor";
    // TAG BUAT DIKLAT
    public static final String TAG_DIKLAT_NIPLAMA = "s_nip";
    public static final String TAG_DIKLAT_KODEJENISDIKLAT = "jenis_pelatihan";
    public static final String TAG_DIKLAT_JUMLAHDIKLAT = "jum_d";
    public static final String TAG_DIKLAT_TANGGALSERTIFIKAT = "d_tgl_sertifikat";
    public static final String TAG_DIKLAT_NOMORSERTIFIKAT = "s_no_sertifikat";
    public static final String TAG_DIKLAT_JENISDIKLAT = "s_skt_jns_diklat";
    public static final String TAG_DIKLAT_KODESUBGRUPDIKLAT = "s_kd_diklat_subgroup";
    public static final String TAG_DIKLAT_KODEGRUPDIKLAT = "s_kd_groupdiklat";
    public static final String TAG_DIKLAT_KOMPETENSI = "kompetensi";
    // TAG BUAT CUTI
    public static final String TAG_CUTI_PEGAWAI = "pegawai";
    public static final String TAG_CUTI_SALDO0 = "saldo0";
    public static final String TAG_CUTI_SALDO1 = "saldo1";
    public static final String TAG_CUTI_SALDO2 = "saldo2";
    public static final String TAG_CUTI_CUTI0 = "cuti0";
    public static final String TAG_CUTI_CUTI1 = "cuti1";
    public static final String TAG_CUTI_CUTI2 = "cuti2";
    // TAG BUAT HISTORI UNIT
    public static final String TAG_UNIT_NIPLAMAUNIT = "s_nip";
    public static final String TAG_UNIT_NOSK = "s_no_sk";
    public static final String TAG_UNIT_TMTSK = "d_tgl_sk";
    public static final String TAG_UNIT_TMTUNIT = "d_tmt_sk";
    public static final String TAG_UNIT_LAMAUNIT = "namaunit";
    public static final String TAG_UNIT_NAMAUNIT = "unit_singkat";
    public static final String TAG_UNIT_NAMAUNITSINGKAT = "unit";
    public static final String TAG_UNIT_NAMAUNITSEBELUMNYA = "ulalu";
    public static final String TAG_UNIT_KOTA = "skota";
    public static final String TAG_UNIT_KODEINSTANSIUNIT = "s_kd_instansiunitorg";
    public static final String TAG_UNIT_IDJENISPENEMPATAN = "i_id_jns_penempatan";
    public static final String TAG_UNIT_KODEJENISPENEMPATAN = "s_kd_jns_penempatan";
    public static final String TAG_UNIT_WILAYAH = "wil";
    public static final String TAG_UNIT_WILAYAH1 = "wil1";
    public static final String TAG_UNIT_WILAYAH2 = "wil2";
    public static final String TAG_UNIT_WILAYAH3 = "wil3";
    // TAG BUAT ATASAN LANGSUNG
    // TAG BUAT TUKIN
    public static final String TAG_TUNJANGAN_BULAN = "bulan";
    public static final String TAG_TUNJANGAN_TAHUN = "tahun";
    public static final String TAG_TUNJANGAN_GRADE = "grade";
    public static final String TAG_TUNJANGAN_PERSENPOTONGAN = "pot_tukin";
    public static final String TAG_TUNJANGAN_TUKINDASAR = "tukin_full";
    public static final String TAG_TUNJANGAN_POTONGAN = "potongan";
    public static final String TAG_TUNJANGAN_TUKINBERSIH = "tukin_net";
    ////    public static final String TAG_DIKLAT_NIPLAMA = "s_nip";
//    public static final String EMP_ID  = "id";
//    public static final String EMP_NIPLAMA  = "niplama";
//    public static final String EMP_NIPBARU  = "nipbaru";
//    public static final String EMP_NAMALENGKAP  = "s_nama_lengkap";
//    public static final String EMP_CARINAMANIP  = "carinama_nip";
//    public static final String EMP_TEMPATLAHIR  = "s_tempat_lahir";
//    public static final String EMP_TANGGALLAHIR  = "d_tgl_lahir";
//    public static final String EMP_JENISKELAMIN  = "jenis_kelamin";
//    public static final String EMP_AGAMA  = "s_nama_agama";
//    public static final String EMP_USIA  = "usia";
//    public static final String EMP_TMTPENSIUN  = "tmtpensiun";
//    public static final String EMP_GOLRUANG  = "golruang";
//    public static final String EMP_PANGKAT  = "s_nama_pangkat";
//    public static final String EMP_TMTSK  = "d_tmt_sk";
//    public static final String EMP_LAMATHKP  = "lamath_kp";
//    public static final String EMP_LAMABLKP  = "lamabl_kp";
//    public static final String EMP_JABATAN  = "jabatan";
//    public static final String EMP_JABATANSINGKAT  = "s_nmjabdetailskt";
//    public static final String EMP_TMTJABATAN  = "tmt_jab";
//    public static final String EMP_KDJABATANDETAIL  = "s_kd_jabdetail";
//    public static final String EMP_JENISJABATAN  = "jenis_jab";
//    public static final String EMP_JENISJABATANGRUP  = "jenis_jab_group";
//    public static final String EMP_LAMATHJABATAN  = "lamath_jab";
//    public static final String EMP_LAMABLJABATAN  = "lamabl_jab";
//    public static final String EMP_PERAN  = "peran";
//    public static final String EMP_UNITORG  = "s_nama_instansiunitorg";
//    public static final String EMP_UNITORGSINGKAT  = "s_skt_instansiunitorg";
//    public static final String EMP_UNITORGLENGKAP  = "namaunit_lengkap";
//    public static final String EMP_INSTANSISINGKAT  = "s_skt_instansi";
//    public static final String EMP_TMTUNIT  = "tmt_unit";
//    public static final String EMP_KDUNITORG  = "s_kd_instansiunitorg";
//    public static final String EMP_KDUNITORGKERJA  = "s_kd_instansiunitkerjal1";
//    public static final String EMP_LAMATHUNIT  = "lamath_unit";
//    public static final String EMP_LAMABLUNIT  = "lamabl_unit";
//    public static final String EMP_UNIT  = "namaunit";
//    public static final String EMP_KEYSORTUNIT  = "key_sort_unit";
//    public static final String EMP_PENDIDIKANSINGKAT  = "s_skt_pendidikan";
//    public static final String EMP_PENDIDIKANSTRATASINGKAT  = "s_nama_strata_skt";
//    public static final String EMP_PENDIDIKANFAKULTAS  = "s_nama_fakultasbidang";
//    public static final String EMP_PENDIDIKANJURUSAN  = "s_nama_jurusan";
//    public static final String EMP_PENDIDIKANLULUS  = "d_tgl_lulus";
//    public static final String EMP_TOTALPAK  = "total_pak";
//    public static final String EMP_TGLAWALXX  = "d_tgl_awal";
//    public static final String EMP_TGLAKHIRXX  = "d_tgl_akhir";
//    public static final String EMP_NOSKKGB  = "s_no_sk_kgb";
//    public static final String EMP_THKGB  = "i_maker_th_kgb";
//    public static final String EMP_BLKGB  = "i_maker_bl_kgb";
//    public static final String EMP_TMTKGB  = "d_tmt_kgb";
//    public static final String EMP_NAMADIKLATFUNG  = "s_nama_diklatfung";
//    public static final String EMP_NOSERTDIKLATFUNG  = "s_nosert_diklatfung";
//    public static final String EMP_TGLSERTDIKLATFUNG  = "d_tglser_diklatfung";
//    public static final String EMP_DIKLATSTRUK  = "diklat_struk";
//    public static final String EMP_NOSERTDIKLATSTRUK  = "s_nosert_diklatstruk";
//    public static final String EMP_TGLSERTDIKLATSTRUK  = "d_tglser_diklatstruk";
//    public static final String EMP_SERTJFA  = "sert_jfa";
//    public static final String EMP_NAMAPASANGAN  = "nama_pasangan";
//    public static final String EMP_UNITPASANGAN  = "unit_pasangan";
//    public static final String EMP_ALAMAT  = "s_alamat";
//    public static final String EMP_SERTPROFESI  = "sert_profesi";
//    public static final String EMP_KELJABATAN  = "kel_jab";
//    public static final String EMP_TGLUPDATE  = "tgl_update";
//    public static final String EMP_STATUS  = "status";
//    public static final String EMP_IDSORT  = "id_sort";
//    public static final String EMP_NAMA  = "nama";
//    public static final String EMP_NIPPISAH  = "nip";
    public static final String CUTI_TAHUNAN = "Cuti Tahunan";
    public static final String CUTI_BESAR = "Cuti Besar";
    public static final String CUTI_DILUARTANGGUNGANNEGARA = "Cuti Di Luar Tanggungan Negara";
    public static final String CUTI_ALASANPENTING = "Cuti Alasan Penting";
    public static final String CUTI_MELAHIRKAN = "Cuti Melahirkan";
    public static final String CUTI_SAKIT = "Cuti Sakit";

    public static final int KODE_CUTI_TAHUNAN = 201;
    public static final int KODE_CUTI_BESAR = 301;
    public static final int KODE_CUTI_DILUARTANGGUNGANNEGARA = 901;
    public static final int KODE_CUTI_ALASANPENTING = 601;
    public static final int KODE_CUTI_MELAHIRKAN = 501;
    public static final int KODE_CUTI_SAKIT = 401;
    public static final int KODE_IZIN_KANTOR_TIDAK_MASUK = 1;
    public static final int KODE_IZIN_KANTOR_TERLAMBAT = 2;
    public static final int KODE_IZIN_KANTOR_PULANG_CEPAT = 3;

    public static final int DASHBOARD_PANEL_ULTAH = 0;
    public static final int DASHBOARD_PANEL_PROFIL = 1;
    public static final int DASHBOARD_PANEL_PRESENSI = 2;
    public static final int DASHBOARD_PANEL_CUTI = 3;
    public static final int DASHBOARD_PANEL_JARINGAN = 4;
    public static final int DASHBOARD_PANEL_TUNJANGAN = 5;
    public static final int DASHBOARD_PANEL_HOTSPOT = 7;

    public static final long animationDurationShort = 500;

    public static void fadeAnimation(boolean isFadeIn, final View v, long duration) {
        YoYo.YoYoString ropeAnimation;
        Techniques techniques;
        if (isFadeIn) {
            techniques = Techniques.FadeIn;
            v.setVisibility(View.VISIBLE);
        } else {
            techniques = Techniques.FadeOut;
        }
        ropeAnimation = YoYo.with(techniques)
                .duration(duration)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(v);
        if (!isFadeIn) {
            v.postDelayed(new Runnable() {
                @Override
                public void run() {
                    v.setVisibility(View.GONE);
                }
            }, duration);
        }
    }

    //Dibawah ini merupakan Pengalamatan dimana Lokasi Skrip CRUD PHP disimpan
    //Pada tutorial Kali ini, karena kita membuat localhost maka alamatnya tertuju ke IP komputer
    //dimana File PHP tersebut berada
    //PENTING! JANGAN LUPA GANTI IP SESUAI DENGAN IP KOMPUTER DIMANA DATA PHP BERADA
    //ADD
    static final String URL_ADD = "none";
}
