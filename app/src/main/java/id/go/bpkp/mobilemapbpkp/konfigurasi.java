package id.go.bpkp.mobilemapbpkp;

/**
 * Created by ASUS on 19/01/2018.
 */

public class konfigurasi {

    //Dibawah ini merupakan Pengalamatan dimana Lokasi Skrip CRUD PHP disimpan
    //Pada tutorial Kali ini, karena kita membuat localhost maka alamatnya tertuju ke IP komputer
    //dimana File PHP tersebut berada
    //PENTING! JANGAN LUPA GANTI IP SESUAI DENGAN IP KOMPUTER DIMANA DATA PHP BERADA
    //ADD
    public static final String URL_ADD = "none";
    //ALL PEGAWAI
    public static final String URL_GET_ALL = "http://118.97.51.140:10001/map/api/pegawai?api_token=";
    //LOGIN
    public static final String URL_LOGIN = "http://118.97.51.140:10001/map/api/login";
    //LOGOUT
    public static final String URL_LOGOUT = "http://118.97.51.140:10001/map/api/logout?";

    public static final String URL_GET_EMP = "http://118.97.51.140:10001/map/api/userByUsername/";
    //UDPATE
    public static final String URL_UPDATE_EMP = "none";
    //HAPUS
    public static final String URL_DELETE_EMP = "none";

    public static final String KEY_EMP_ID = "id";
    public static final String KEY_EMP_NIPLAMA = "niplama";
    public static final String KEY_EMP_NIPBARU = "nipbaru";
    public static final String KEY_EMP_NAMALENGKAP = "s_nama_lengkap";
    public static final String KEY_EMP_CARINAMANIP = "carinama_nip";
    public static final String KEY_EMP_TEMPATLAHIR = "s_tempat_lahir";
    public static final String KEY_EMP_TANGGALLAHIR = "d_tgl_lahir";
    public static final String KEY_EMP_JENISKELAMIN = "jenis_kelamin";
    public static final String KEY_EMP_AGAMA = "s_nama_agama";
    public static final String KEY_EMP_USIA = "usia";
    public static final String KEY_EMP_TMTPENSIUN = "tmtpensiun";
    public static final String KEY_EMP_GOLRUANG = "golruang";
    public static final String KEY_EMP_PANGKAT = "s_nama_pangkat";
    public static final String KEY_EMP_TMTSK = "d_tmt_sk";
    public static final String KEY_EMP_LAMATHKP = "lamath_kp";
    public static final String KEY_EMP_LAMABLKP = "lamabl_kp";
    public static final String KEY_EMP_JABATAN = "jabatan";
    public static final String KEY_EMP_JABATANSINGKAT = "s_nmjabdetailskt";
    public static final String KEY_EMP_TMTJABATAN = "tmt_jab";
    public static final String KEY_EMP_KDJABATANDETAIL = "s_kd_jabdetail";
    public static final String KEY_EMP_JENISJABATAN = "jenis_jab";
    public static final String KEY_EMP_JENISJABATANGRUP = "jenis_jab_group";
    public static final String KEY_EMP_LAMATHJABATAN = "lamath_jab";
    public static final String KEY_EMP_LAMABLJABATAN = "lamabl_jab";
    public static final String KEY_EMP_PERAN = "peran";
    public static final String KEY_EMP_UNITORG = "s_nama_instansiunitorg";
    public static final String KEY_EMP_UNITORGSINGKAT = "s_skt_instansiunitorg";
    public static final String KEY_EMP_UNITORGLENGKAP = "namaunit_lengkap";
    public static final String KEY_EMP_INSTANSISINGKAT = "s_skt_instansi";
    public static final String KEY_EMP_TMTUNIT = "tmt_unit";
    public static final String KEY_EMP_KDUNITORG = "s_kd_instansiunitorg";
    public static final String KEY_EMP_KDUNITORGKERJA = "s_kd_instansiunitkerjal1";
    public static final String KEY_EMP_LAMATHUNIT = "lamath_unit";
    public static final String KEY_EMP_LAMABLUNIT = "lamabl_unit";
    public static final String KEY_EMP_UNIT = "namaunit";
    public static final String KEY_EMP_KEYSORTUNIT = "key_sort_unit";
    public static final String KEY_EMP_PENDIDIKANSINGKAT = "s_skt_pendidikan";
    public static final String KEY_EMP_PENDIDIKANSTRATASINGKAT = "s_nama_strata_skt";
    public static final String KEY_EMP_PENDIDIKANFAKULTAS = "s_nama_fakultasbidang";
    public static final String KEY_EMP_PENDIDIKANJURUSAN = "s_nama_jurusan";
    public static final String KEY_EMP_PENDIDIKANLULUS = "d_tgl_lulus";
    public static final String KEY_EMP_TOTALPAK = "total_pak";
    public static final String KEY_EMP_TGLAWALXX = "d_tgl_awal";
    public static final String KEY_EMP_TGLAKHIRXX = "d_tgl_akhir";
    public static final String KEY_EMP_NOSKKGB = "s_no_sk_kgb";
    public static final String KEY_EMP_THKGB = "i_maker_th_kgb";
    public static final String KEY_EMP_BLKGB = "i_maker_bl_kgb";
    public static final String KEY_EMP_TMTKGB = "d_tmt_kgb";
    public static final String KEY_EMP_NAMADIKLATFUNG = "s_nama_diklatfung";
    public static final String KEY_EMP_NOSERTDIKLATFUNG = "s_nosert_diklatfung";
    public static final String KEY_EMP_TGLSERTDIKLATFUNG = "d_tglser_diklatfung";
    public static final String KEY_EMP_DIKLATSTRUK = "diklat_struk";
    public static final String KEY_EMP_NOSERTDIKLATSTRUK = "s_nosert_diklatstruk";
    public static final String KEY_EMP_TGLSERTDIKLATSTRUK = "d_tglser_diklatstruk";
    public static final String KEY_EMP_SERTJFA = "sert_jfa";
    public static final String KEY_EMP_NAMAPASANGAN = "nama_pasangan";
    public static final String KEY_EMP_UNITPASANGAN = "unit_pasangan";
    public static final String KEY_EMP_ALAMAT = "s_alamat";
    public static final String KEY_EMP_SERTPROFESI = "sert_profesi";
    public static final String KEY_EMP_KELJABATAN = "kel_jab";
    public static final String KEY_EMP_TGLUPDATE = "tgl_update";
    public static final String KEY_EMP_STATUS = "status";
    public static final String KEY_EMP_IDSORT = "id_sort";
    public static final String KEY_EMP_NAMA = "nama";
    public static final String KEY_EMP_NIPPISAH = "nip";

    //JSON tags
    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_ID = "id";
    public static final String TAG_NIPLAMA = "niplama";
    public static final String TAG_NIPBARU = "nipbaru";
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
    public static final String TAG_INSTANSISINGKAT = "s_skt_instansi";
    public static final String TAG_TMTUNIT = "tmt_unit";
    public static final String TAG_KDUNITORG = "s_kd_instansiunitorg";
    public static final String TAG_KDUNITORGKERJA = "s_kd_instansiunitkerjal1";
    public static final String TAG_LAMATHUNIT = "lamath_unit";
    public static final String TAG_LAMABLUNIT = "lamabl_unit";
    public static final String TAG_UNIT = "namaunit";
    public static final String TAG_KEYSORTUNIT = "key_sort_unit";
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
    public static final String TAG_TGLUPDATE = "tgl_update";
    public static final String TAG_STATUS = "status";
    public static final String TAG_IDSORT = "id_sort";
    public static final String TAG_NAMA = "nama";
    public static final String TAG_NIPPISAH = "nip";
    public static final String TAG_FOTO = "foto";

    public static final String EMP_ID  = "id";
    public static final String EMP_NIPLAMA  = "niplama";
    public static final String EMP_NIPBARU  = "nipbaru";
    public static final String EMP_NAMALENGKAP  = "s_nama_lengkap";
    public static final String EMP_CARINAMANIP  = "carinama_nip";
    public static final String EMP_TEMPATLAHIR  = "s_tempat_lahir";
    public static final String EMP_TANGGALLAHIR  = "d_tgl_lahir";
    public static final String EMP_JENISKELAMIN  = "jenis_kelamin";
    public static final String EMP_AGAMA  = "s_nama_agama";
    public static final String EMP_USIA  = "usia";
    public static final String EMP_TMTPENSIUN  = "tmtpensiun";
    public static final String EMP_GOLRUANG  = "golruang";
    public static final String EMP_PANGKAT  = "s_nama_pangkat";
    public static final String EMP_TMTSK  = "d_tmt_sk";
    public static final String EMP_LAMATHKP  = "lamath_kp";
    public static final String EMP_LAMABLKP  = "lamabl_kp";
    public static final String EMP_JABATAN  = "jabatan";
    public static final String EMP_JABATANSINGKAT  = "s_nmjabdetailskt";
    public static final String EMP_TMTJABATAN  = "tmt_jab";
    public static final String EMP_KDJABATANDETAIL  = "s_kd_jabdetail";
    public static final String EMP_JENISJABATAN  = "jenis_jab";
    public static final String EMP_JENISJABATANGRUP  = "jenis_jab_group";
    public static final String EMP_LAMATHJABATAN  = "lamath_jab";
    public static final String EMP_LAMABLJABATAN  = "lamabl_jab";
    public static final String EMP_PERAN  = "peran";
    public static final String EMP_UNITORG  = "s_nama_instansiunitorg";
    public static final String EMP_UNITORGSINGKAT  = "s_skt_instansiunitorg";
    public static final String EMP_UNITORGLENGKAP  = "namaunit_lengkap";
    public static final String EMP_INSTANSISINGKAT  = "s_skt_instansi";
    public static final String EMP_TMTUNIT  = "tmt_unit";
    public static final String EMP_KDUNITORG  = "s_kd_instansiunitorg";
    public static final String EMP_KDUNITORGKERJA  = "s_kd_instansiunitkerjal1";
    public static final String EMP_LAMATHUNIT  = "lamath_unit";
    public static final String EMP_LAMABLUNIT  = "lamabl_unit";
    public static final String EMP_UNIT  = "namaunit";
    public static final String EMP_KEYSORTUNIT  = "key_sort_unit";
    public static final String EMP_PENDIDIKANSINGKAT  = "s_skt_pendidikan";
    public static final String EMP_PENDIDIKANSTRATASINGKAT  = "s_nama_strata_skt";
    public static final String EMP_PENDIDIKANFAKULTAS  = "s_nama_fakultasbidang";
    public static final String EMP_PENDIDIKANJURUSAN  = "s_nama_jurusan";
    public static final String EMP_PENDIDIKANLULUS  = "d_tgl_lulus";
    public static final String EMP_TOTALPAK  = "total_pak";
    public static final String EMP_TGLAWALXX  = "d_tgl_awal";
    public static final String EMP_TGLAKHIRXX  = "d_tgl_akhir";
    public static final String EMP_NOSKKGB  = "s_no_sk_kgb";
    public static final String EMP_THKGB  = "i_maker_th_kgb";
    public static final String EMP_BLKGB  = "i_maker_bl_kgb";
    public static final String EMP_TMTKGB  = "d_tmt_kgb";
    public static final String EMP_NAMADIKLATFUNG  = "s_nama_diklatfung";
    public static final String EMP_NOSERTDIKLATFUNG  = "s_nosert_diklatfung";
    public static final String EMP_TGLSERTDIKLATFUNG  = "d_tglser_diklatfung";
    public static final String EMP_DIKLATSTRUK  = "diklat_struk";
    public static final String EMP_NOSERTDIKLATSTRUK  = "s_nosert_diklatstruk";
    public static final String EMP_TGLSERTDIKLATSTRUK  = "d_tglser_diklatstruk";
    public static final String EMP_SERTJFA  = "sert_jfa";
    public static final String EMP_NAMAPASANGAN  = "nama_pasangan";
    public static final String EMP_UNITPASANGAN  = "unit_pasangan";
    public static final String EMP_ALAMAT  = "s_alamat";
    public static final String EMP_SERTPROFESI  = "sert_profesi";
    public static final String EMP_KELJABATAN  = "kel_jab";
    public static final String EMP_TGLUPDATE  = "tgl_update";
    public static final String EMP_STATUS  = "status";
    public static final String EMP_IDSORT  = "id_sort";
    public static final String EMP_NAMA  = "nama";
    public static final String EMP_NIPPISAH  = "nip";
}
