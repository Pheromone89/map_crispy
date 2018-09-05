package id.go.bpkp.mobilemapbpkp.konfigurasi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class FragmentBundles {

    public static final String loginPrefix = "login_";
    public static final String messageSuffix = "_message";
    public static final String pegawaiSuffix = "_pegawai";
    public static final String atasanSuffix = "_atasan";
    public static final String broadcastSuffix = "_broadcast";

    //
    public static final String BUNDLE_SUCCESS = loginPrefix + "success";
    public static final String BUNDLE_USERTOKEN = loginPrefix + "api_token";
    public static final String BUNDLE_MESSAGE = loginPrefix + "message";
    public static final String BUNDLE_ID_MESSAGE = loginPrefix + "id" + messageSuffix;
    public static final String BUNDLE_NAME_MESSAGE = loginPrefix + "name" + messageSuffix;
    public static final String BUNDLE_EMAIL_MESSAGE = loginPrefix + "email" + messageSuffix;
    public static final String BUNDLE_USERNAME_MESSAGE = loginPrefix + "username" + messageSuffix;
    public static final String BUNDLE_ROLEID_MESSAGE = loginPrefix + "role_id" + messageSuffix;
    public static final String BUNDLE_USERNIP_MESSAGE = loginPrefix + "user_nip" + messageSuffix;
    public static final String BUNDLE_CREATEDAT_MESSAGE = loginPrefix + "created_at" + messageSuffix;
    public static final String BUNDLE_UPDATEDAT_MESSAGE = loginPrefix + "updated_at" + messageSuffix;
    public static final String BUNDLE_NOHP_MESSAGE = loginPrefix + "nomorhp" + messageSuffix;
    public static final String BUNDLE_AKTIF_MESSAGE = loginPrefix + "aktif" + messageSuffix;
    public static final String BUNDLE_KEYSORTUNIT_MESSAGE = loginPrefix + "key_sort_unit" + messageSuffix;
    public static final String BUNDLE_NIPBARU_MESSAGE = loginPrefix + "nipbaru" + messageSuffix;
    public static final String BUNDLE_ISLDAP_MESSAGE = loginPrefix + "is_ldap" + messageSuffix;
    public static final String BUNDLE_ISJAB_MESSAGE = loginPrefix + "is_jab" + messageSuffix;
    public static final String BUNDLE_VERSION_MESSAGE = loginPrefix + "version" + messageSuffix;
    public static final String BUNDLE_ISHUT_MESSAGE = loginPrefix + "is_hut" + messageSuffix;
    public static final String BUNDLE_URLFOTO_MESSAGE = loginPrefix + "url_foto" + messageSuffix;
    public static final String BUNDLE_ISATASAN_MESSAGE = loginPrefix + "is_atasan" + messageSuffix;
    public static final String BUNDLE_PEGAWAI = loginPrefix + "pegawai";
    public static final String BUNDLE_ID_PEGAWAI = loginPrefix + "id" + pegawaiSuffix;
    public static final String BUNDLE_NIPLAMA_PEGAWAI = loginPrefix + "niplama" + pegawaiSuffix;
    public static final String BUNDLE_NIPBARU_PEGAWAI = loginPrefix + "nipbaru" + pegawaiSuffix;
    public static final String BUNDLE_NIPBARUGABUNG_PEGAWAI = loginPrefix + "nip" + pegawaiSuffix;
    public static final String BUNDLE_NAMA_PEGAWAI = loginPrefix + "nama" + pegawaiSuffix;
    public static final String BUNDLE_NAMALENGKAP_PEGAWAI = loginPrefix + "s_nama_lengkap" + pegawaiSuffix;
    public static final String BUNDLE_CARINAMANIP_PEGAWAI = loginPrefix + "carinama_nip" + pegawaiSuffix;
    public static final String BUNDLE_TEMPATLAHIR_PEGAWAI = loginPrefix + "s_tempat_lahir" + pegawaiSuffix;
    public static final String BUNDLE_TANGGALLAHIR_PEGAWAI = loginPrefix + "d_tgl_lahir" + pegawaiSuffix;
    public static final String BUNDLE_JENISKELAMIN_PEGAWAI = loginPrefix + "jenis_kelamin" + pegawaiSuffix;
    public static final String BUNDLE_AGAMA_PEGAWAI = loginPrefix + "s_nama_agama" + pegawaiSuffix;
    public static final String BUNDLE_USIA_PEGAWAI = loginPrefix + "usia" + pegawaiSuffix;
    public static final String BUNDLE_TMTPENSIUN_PEGAWAI = loginPrefix + "tmtpensiun" + pegawaiSuffix;
    public static final String BUNDLE_GOLRUANG_PEGAWAI = loginPrefix + "golruang" + pegawaiSuffix;
    public static final String BUNDLE_PANGKAT_PEGAWAI = loginPrefix + "s_nama_pangkat" + pegawaiSuffix;
    public static final String BUNDLE_TMTSK_PEGAWAI = loginPrefix + "d_tmt_sk" + pegawaiSuffix;
    public static final String BUNDLE_LAMATHKP_PEGAWAI = loginPrefix + "lamath_kp" + pegawaiSuffix;
    public static final String BUNDLE_LAMABLKP_PEGAWAI = loginPrefix + "lamabl_kp" + pegawaiSuffix;
    public static final String BUNDLE_JABATAN_PEGAWAI = loginPrefix + "jabatan" + pegawaiSuffix;
    public static final String BUNDLE_JABATANSINGKAT_PEGAWAI = loginPrefix + "s_nmjabdetailskt" + pegawaiSuffix;
    public static final String BUNDLE_TMTJABATAN_PEGAWAI = loginPrefix + "tmt_jab" + pegawaiSuffix;
    public static final String BUNDLE_KDJABATANDETAIL_PEGAWAI = loginPrefix + "s_kd_jabdetail" + pegawaiSuffix;
    public static final String BUNDLE_JENISJABATAN_PEGAWAI = loginPrefix + "jenis_jab" + pegawaiSuffix;
    public static final String BUNDLE_JENISJABATANGRUP_PEGAWAI = loginPrefix + "jenis_jab_group" + pegawaiSuffix;
    public static final String BUNDLE_LAMATHJABATAN_PEGAWAI = loginPrefix + "lamath_jab" + pegawaiSuffix;
    public static final String BUNDLE_LAMABLJABATAN_PEGAWAI = loginPrefix + "lamabl_jab" + pegawaiSuffix;
    public static final String BUNDLE_PERAN_PEGAWAI = loginPrefix + "peran" + pegawaiSuffix;
    public static final String BUNDLE_UNITORG_PEGAWAI = loginPrefix + "s_nama_instansiunitorg" + pegawaiSuffix;
    public static final String BUNDLE_UNITORGSINGKAT_PEGAWAI = loginPrefix + "s_skt_instansiunitorg" + pegawaiSuffix;
    public static final String BUNDLE_UNITORGLENGKAP_PEGAWAI = loginPrefix + "namaunit_lengkap" + pegawaiSuffix;
    public static final String BUNDLE_TMTUNIT_PEGAWAI = loginPrefix + "tmt_unit" + pegawaiSuffix;
    public static final String BUNDLE_KDUNITORG_PEGAWAI = loginPrefix + "s_kd_instansiunitorg" + pegawaiSuffix;
    public static final String BUNDLE_KDUNITORGKERJA_PEGAWAI = loginPrefix + "s_kd_instansiunitkerjal1" + pegawaiSuffix;
    public static final String BUNDLE_LAMATHUNIT_PEGAWAI = loginPrefix + "lamath_unit" + pegawaiSuffix;
    public static final String BUNDLE_LAMABLUNIT_PEGAWAI = loginPrefix + "lamabl_unit" + pegawaiSuffix;
    public static final String BUNDLE_UNIT_PEGAWAI = loginPrefix + "namaunit" + pegawaiSuffix;
    public static final String BUNDLE_KEYSORTUNIT_PEGAWAI = loginPrefix + "key_sort_unit" + pegawaiSuffix;
    public static final String BUNDLE_PENDIDIKANSINGKAT_PEGAWAI = loginPrefix + "s_skt_pendidikan" + pegawaiSuffix;
    public static final String BUNDLE_PENDIDIKANSTRATASINGKAT_PEGAWAI = loginPrefix + "s_nama_strata_skt" + pegawaiSuffix;
    public static final String BUNDLE_PENDIDIKANFAKULTAS_PEGAWAI = loginPrefix + "s_nama_fakultasbidang" + pegawaiSuffix;
    public static final String BUNDLE_PENDIDIKANJURUSAN_PEGAWAI = loginPrefix + "s_nama_jurusan" + pegawaiSuffix;
    public static final String BUNDLE_PENDIDIKANLULUS_PEGAWAI = loginPrefix + "d_tgl_lulus" + pegawaiSuffix;
    public static final String BUNDLE_TOTALPAK_PEGAWAI = loginPrefix + "total_pak" + pegawaiSuffix;
    public static final String BUNDLE_TGLAWALXX_PEGAWAI = loginPrefix + "d_tgl_awal" + pegawaiSuffix;
    public static final String BUNDLE_TGLAKHIRXX_PEGAWAI = loginPrefix + "d_tgl_akhir" + pegawaiSuffix;
    public static final String BUNDLE_NOSKKGB_PEGAWAI = loginPrefix + "s_no_sk_kgb" + pegawaiSuffix;
    public static final String BUNDLE_THKGB_PEGAWAI = loginPrefix + "i_maker_th_kgb" + pegawaiSuffix;
    public static final String BUNDLE_BLKGB_PEGAWAI = loginPrefix + "i_maker_bl_kgb" + pegawaiSuffix;
    public static final String BUNDLE_TMTKGB_PEGAWAI = loginPrefix + "d_tmt_kgb" + pegawaiSuffix;
    public static final String BUNDLE_NAMADIKLATFUNG_PEGAWAI = loginPrefix + "s_nama_diklatfung" + pegawaiSuffix;
    public static final String BUNDLE_NOSERTDIKLATFUNG_PEGAWAI = loginPrefix + "s_nosert_diklatfung" + pegawaiSuffix;
    public static final String BUNDLE_TGLSERTDIKLATFUNG_PEGAWAI = loginPrefix + "d_tglser_diklatfung" + pegawaiSuffix;
    public static final String BUNDLE_DIKLATSTRUK_PEGAWAI = loginPrefix + "diklat_struk" + pegawaiSuffix;
    public static final String BUNDLE_NOSERTDIKLATSTRUK_PEGAWAI = loginPrefix + "s_nosert_diklatstruk" + pegawaiSuffix;
    public static final String BUNDLE_TGLSERTDIKLATSTRUK_PEGAWAI = loginPrefix + "d_tglser_diklatstruk" + pegawaiSuffix;
    public static final String BUNDLE_SERTJFA_PEGAWAI = loginPrefix + "sert_jfa" + pegawaiSuffix;
    public static final String BUNDLE_NAMAPASANGAN_PEGAWAI = loginPrefix + "nama_pasangan" + pegawaiSuffix;
    public static final String BUNDLE_UNITPASANGAN_PEGAWAI = loginPrefix + "unit_pasangan" + pegawaiSuffix;
    public static final String BUNDLE_ALAMAT_PEGAWAI = loginPrefix + "s_alamat" + pegawaiSuffix;
    public static final String BUNDLE_SERTPROFESI_PEGAWAI = loginPrefix + "sert_profesi" + pegawaiSuffix;
    public static final String BUNDLE_KELJABATAN_PEGAWAI = loginPrefix + "kel_jab" + pegawaiSuffix;
    public static final String BUNDLE_STATUS_PEGAWAI = loginPrefix + "status" + pegawaiSuffix;
    public static final String BUNDLE_IDSORT_PEGAWAI = loginPrefix + "id_sort" + pegawaiSuffix;
    public static final String BUNDLE_TGLUPDATE_PEGAWAI = loginPrefix + "tgl_update" + pegawaiSuffix;
    public static final String BUNDLE_ATASAN = loginPrefix + "atasan";
    public static final String BUNDLE_NIPATASAN_ATASAN = loginPrefix + "s_nip" + atasanSuffix;
    public static final String BUNDLE_NAMAATASAN_ATASAN = loginPrefix + "nama" + atasanSuffix;
    public static final String BUNDLE_NAMAGELARATASAN_ATASAN = loginPrefix + "nama_lengkap" + atasanSuffix;
    public static final String BUNDLE_BROADCAST = loginPrefix + "broadcast";
    public static final String BUNDLE_STATUSBROADCAST_BROADCAST = loginPrefix + "status" + broadcastSuffix;
    public static final String BUNDLE_IMAGEBROADCAST_BROADCAST = loginPrefix + "images" + broadcastSuffix;
    public static final String BUNDLE_TITLEBROADCAST_BROADCAST = loginPrefix + "title" + broadcastSuffix;
    public static final String BUNDLE_MESSAGEBROADCAST_BROADCAST = loginPrefix + "message" + broadcastSuffix;
    // tambahan
    public static final String BUNDLE_TIDAKPUNYAATASANLANGSUNG_ATASAN = loginPrefix + "tidak_punya_atasan_langsung" + atasanSuffix;


    // method ini mengkonversi JSON Login ke savedpreference untuk kemudian dipanggil di bundle
    public static void saveBundle(Context context, SharedPreferences sharedPreferences, JSONObject jsonObject) {

        // set up setting
        SharedPreferences.Editor editor = sharedPreferences.edit();

        try {
//            JSONObject jsonObject = new JSONObject(JSONSTRING);
            if (jsonObject.getString(konfigurasi.TAG_SUCCESS).equals("true")) {

                // RESPONS API
                // api token
                String apiToken = jsonObject.getString(konfigurasi.TAG_USERTOKEN);
                // message
                JSONObject message = jsonObject.getJSONObject(konfigurasi.TAG_MESSAGE);
                String id = message.getString(konfigurasi.TAG_ID);
                String nama = message.getString(konfigurasi.TAG_NAME);
                String email = message.getString(konfigurasi.TAG_EMAIL);
                String username = message.getString(konfigurasi.TAG_USERNAME);
                int roleId = Integer.parseInt(message.getString(konfigurasi.TAG_ROLEID));
                String userNip = message.getString(konfigurasi.TAG_USERNIP);
                String createdAt = message.getString(konfigurasi.TAG_CREATEDAT);
                String updatedAt = message.getString(konfigurasi.TAG_UPDATEDAT);
                String nomorHp = message.getString(konfigurasi.TAG_NOHP);
                String aktif = message.getString(konfigurasi.TAG_AKTIF);
                String keySortUnit = message.getString(konfigurasi.TAG_KEYSORTUNIT);
                String nipBaru = message.getString(konfigurasi.TAG_NIPBARU);
                boolean isLdap = message.getString(konfigurasi.TAG_ISLDAP).equals("true");
                boolean isJab = message.getString(konfigurasi.TAG_ISJAB).equals("true");
                int version = Integer.parseInt(message.getString(konfigurasi.TAG_VERSION));
                boolean isHut = message.getString(konfigurasi.TAG_ISHUT).equals("true");
                String urlFoto = message.getString(konfigurasi.TAG_URLFOTO);
                boolean isAtasan = message.getString(konfigurasi.TAG_ISATASAN).equals("true");
                // pegawai
                String idPegawai, nipLamaPegawai, nipBaruPegawai, nipBaruGabungPegawai, namaPegawai, namaGelarPegawai, nipBaruNamaPegawai, tempatLahirPegawai,
                        tanggalLahirPegawai, jenisKelaminPegawai, agamaPegawai, tmtPensiunPegawai, golRuangPegawai, pangkatPegawai, tmtSkPegawai, lamaThKpPegawai,
                        lamaBlKpPegawai, jabatanPegawai, namaJabatanDetailSingkatPegawai, tmtJabatanPegawai, kodeJabatanDetailPegawai,
                        jenisJabatanPegawai, jenisJabatanGrupPegawai, lamaThJabatanPegawai, lamaBLJabatanPegawai, peranPegawai, namaInstansiUnitOrganisasiPegawai,
                        namaInstansiUnitOrganisasiSingkatPegawai, namaUnitLengkapPegawai, tmtUnitPegawai, kodeInstansiUnitKerjaPegawai, kodeInstansiUnitOrganisasiPegawai,
                        lamaThUnitPegawai, lamaBlUnitPegawai, namaUnitPegawai, keySortUnitPegawai, pendidikanSingkatPegawai, namaStrataSingkatPegawai,
                        namaFakultasPegawai, namaJurusanPegawai, tanggalLulusPegawai, totalPakPegawai, tanggalAwalPegawai, tanggalAkhirPegawai, noSkKgbPegawai,
                        thKgbPegawai, blKgbPegawai, tmtKgbPegawai, namaDiklatFungsionalPegawai, nomorSertifikatDiklatFungsionalPegawai, tanggalDiklatFungsionalPegawai,
                        diklatStrukturalPegawai, nomorDiklatStrukturalPegawai, tanggalSertifikatDiklatStrukturalPegawai, sertifikatJfaPegawai,
                        namaPasanganPegawai, unitPasanganPegawai, alamatPegawai, sertifikatProfesiPegawai, kelompokJabatanPegawai,
                        statusPegawai, idSortPegawai, tanggalUpdatePegawai;

                idPegawai = nipLamaPegawai = nipBaruPegawai = nipBaruGabungPegawai = namaPegawai = namaGelarPegawai = nipBaruNamaPegawai = tempatLahirPegawai =
                        tanggalLahirPegawai = jenisKelaminPegawai = agamaPegawai = tmtPensiunPegawai = golRuangPegawai = pangkatPegawai = tmtSkPegawai = lamaThKpPegawai =
                                lamaBlKpPegawai = jabatanPegawai = namaJabatanDetailSingkatPegawai = tmtJabatanPegawai = kodeJabatanDetailPegawai =
                                        jenisJabatanPegawai = jenisJabatanGrupPegawai = lamaThJabatanPegawai = lamaBLJabatanPegawai = peranPegawai = namaInstansiUnitOrganisasiPegawai =
                                                namaInstansiUnitOrganisasiSingkatPegawai = namaUnitLengkapPegawai = tmtUnitPegawai = kodeInstansiUnitKerjaPegawai = kodeInstansiUnitOrganisasiPegawai =
                                                        lamaThUnitPegawai = lamaBlUnitPegawai = namaUnitPegawai = keySortUnitPegawai = pendidikanSingkatPegawai = namaStrataSingkatPegawai =
                                                                namaFakultasPegawai = namaJurusanPegawai = tanggalLulusPegawai = totalPakPegawai = tanggalAwalPegawai = tanggalAkhirPegawai = noSkKgbPegawai =
                                                                        thKgbPegawai = blKgbPegawai = tmtKgbPegawai = namaDiklatFungsionalPegawai = nomorSertifikatDiklatFungsionalPegawai = tanggalDiklatFungsionalPegawai =
                                                                                diklatStrukturalPegawai = nomorDiklatStrukturalPegawai = tanggalSertifikatDiklatStrukturalPegawai = sertifikatJfaPegawai =
                                                                                        namaPasanganPegawai = unitPasanganPegawai = alamatPegawai = sertifikatProfesiPegawai = kelompokJabatanPegawai =
                                                                                                statusPegawai = idSortPegawai = tanggalUpdatePegawai = null;

                int usiaPegawai = 0;

                if (!message.getString(konfigurasi.TAG_PEGAWAI).equals("null")) {
                    JSONObject pegawai = message.getJSONObject(konfigurasi.TAG_PEGAWAI);
                    idPegawai = pegawai.getString(konfigurasi.TAG_ID);
                    nipLamaPegawai = pegawai.getString(konfigurasi.TAG_NIPLAMA);
                    nipBaruPegawai = pegawai.getString(konfigurasi.TAG_NIPBARU);
                    nipBaruGabungPegawai = pegawai.getString(konfigurasi.TAG_NIPBARUGABUNG);
                    namaPegawai = pegawai.getString(konfigurasi.TAG_NAMA);
                    namaGelarPegawai = pegawai.getString(konfigurasi.TAG_NAMALENGKAP);
                    nipBaruNamaPegawai = pegawai.getString(konfigurasi.TAG_CARINAMANIP);
                    tempatLahirPegawai = pegawai.getString(konfigurasi.TAG_TEMPATLAHIR);
                    tanggalLahirPegawai = pegawai.getString(konfigurasi.TAG_TANGGALLAHIR);
                    jenisKelaminPegawai = pegawai.getString(konfigurasi.TAG_JENISKELAMIN);
                    agamaPegawai = pegawai.getString(konfigurasi.TAG_AGAMA);
                    usiaPegawai = Integer.parseInt(pegawai.getString(konfigurasi.TAG_USIA));
                    tmtPensiunPegawai = pegawai.getString(konfigurasi.TAG_TMTPENSIUN);
                    golRuangPegawai = pegawai.getString(konfigurasi.TAG_GOLRUANG);
                    pangkatPegawai = pegawai.getString(konfigurasi.TAG_PANGKAT);
                    tmtSkPegawai = pegawai.getString(konfigurasi.TAG_TMTSK);
                    lamaThKpPegawai = pegawai.getString(konfigurasi.TAG_LAMATHKP);
                    lamaBlKpPegawai = pegawai.getString(konfigurasi.TAG_LAMABLKP);
                    jabatanPegawai = pegawai.getString(konfigurasi.TAG_JABATAN);
                    namaJabatanDetailSingkatPegawai = pegawai.getString(konfigurasi.TAG_JABATANSINGKAT);
                    tmtJabatanPegawai = pegawai.getString(konfigurasi.TAG_TMTJABATAN);
                    kodeJabatanDetailPegawai = pegawai.getString(konfigurasi.TAG_KDJABATANDETAIL);
                    jenisJabatanPegawai = pegawai.getString(konfigurasi.TAG_JENISJABATAN);
                    jenisJabatanGrupPegawai = pegawai.getString(konfigurasi.TAG_JENISJABATANGRUP);
                    lamaThJabatanPegawai = pegawai.getString(konfigurasi.TAG_LAMATHJABATAN);
                    lamaBLJabatanPegawai = pegawai.getString(konfigurasi.TAG_LAMABLJABATAN);
                    peranPegawai = pegawai.getString(konfigurasi.TAG_PERAN);
                    namaInstansiUnitOrganisasiPegawai = pegawai.getString(konfigurasi.TAG_UNITORG);
                    namaInstansiUnitOrganisasiSingkatPegawai = pegawai.getString(konfigurasi.TAG_UNITORGSINGKAT);
                    namaUnitLengkapPegawai = pegawai.getString(konfigurasi.TAG_UNITORGLENGKAP);
                    tmtUnitPegawai = pegawai.getString(konfigurasi.TAG_TMTUNIT);
                    kodeInstansiUnitOrganisasiPegawai = pegawai.getString(konfigurasi.TAG_UNIT_KODEINSTANSIUNIT);
                    kodeInstansiUnitKerjaPegawai = pegawai.getString(konfigurasi.TAG_KDUNITORGKERJA);
                    lamaThUnitPegawai = pegawai.getString(konfigurasi.TAG_LAMATHUNIT);
                    lamaBlUnitPegawai = pegawai.getString(konfigurasi.TAG_LAMABLUNIT);
                    namaUnitPegawai = pegawai.getString(konfigurasi.TAG_UNIT);
                    keySortUnitPegawai = pegawai.getString(konfigurasi.TAG_KEYSORTUNIT);
                    pendidikanSingkatPegawai = pegawai.getString(konfigurasi.TAG_PENDIDIKANSINGKAT);
                    namaStrataSingkatPegawai = pegawai.getString(konfigurasi.TAG_PENDIDIKANSTRATASINGKAT);
                    namaFakultasPegawai = pegawai.getString(konfigurasi.TAG_PENDIDIKANFAKULTAS);
                    namaJurusanPegawai = pegawai.getString(konfigurasi.TAG_PENDIDIKANJURUSAN);
                    tanggalLulusPegawai = pegawai.getString(konfigurasi.TAG_PENDIDIKANLULUS);
                    totalPakPegawai = pegawai.getString(konfigurasi.TAG_TOTALPAK);
                    tanggalAwalPegawai = pegawai.getString(konfigurasi.TAG_TGLAWALXX);
                    tanggalAkhirPegawai = pegawai.getString(konfigurasi.TAG_TGLAKHIRXX);
                    noSkKgbPegawai = pegawai.getString(konfigurasi.TAG_NOSKKGB);
                    thKgbPegawai = pegawai.getString(konfigurasi.TAG_THKGB);
                    blKgbPegawai = pegawai.getString(konfigurasi.TAG_BLKGB);
                    tmtKgbPegawai = pegawai.getString(konfigurasi.TAG_TMTKGB);
                    namaDiklatFungsionalPegawai = pegawai.getString(konfigurasi.TAG_NAMADIKLATFUNG);
                    nomorSertifikatDiklatFungsionalPegawai = pegawai.getString(konfigurasi.TAG_NOSERTDIKLATFUNG);
                    tanggalDiklatFungsionalPegawai = pegawai.getString(konfigurasi.TAG_TGLSERTDIKLATFUNG);
                    diklatStrukturalPegawai = pegawai.getString(konfigurasi.TAG_DIKLATSTRUK);
                    nomorDiklatStrukturalPegawai = pegawai.getString(konfigurasi.TAG_NOSERTDIKLATSTRUK);
                    tanggalSertifikatDiklatStrukturalPegawai = pegawai.getString(konfigurasi.TAG_TGLSERTDIKLATSTRUK);
                    sertifikatJfaPegawai = pegawai.getString(konfigurasi.TAG_SERTJFA);
                    namaPasanganPegawai = pegawai.getString(konfigurasi.TAG_NAMAPASANGAN);
                    unitPasanganPegawai = pegawai.getString(konfigurasi.TAG_UNITPASANGAN);
                    alamatPegawai = pegawai.getString(konfigurasi.TAG_ALAMAT);
                    sertifikatProfesiPegawai = pegawai.getString(konfigurasi.TAG_SERTPROFESI);
                    kelompokJabatanPegawai = pegawai.getString(konfigurasi.TAG_KELJABATAN);
                    statusPegawai = pegawai.getString(konfigurasi.TAG_STATUS);
                    idSortPegawai = pegawai.getString(konfigurasi.TAG_IDSORT);
                    tanggalUpdatePegawai = pegawai.getString(konfigurasi.TAG_TGLUPDATE);
                }
                // atasan
                boolean tidakPunyaAtasanLangsung = jsonObject.getString(konfigurasi.TAG_ATASAN).equals("null");
                String nipAtasan, namaAtasan, namaGelarAtasan;
                nipAtasan = namaAtasan = namaGelarAtasan = null;
                if (!tidakPunyaAtasanLangsung) {
                    JSONObject atasan = jsonObject.getJSONObject(konfigurasi.TAG_ATASAN);
                    nipAtasan = atasan.getString(konfigurasi.TAG_NIPATASAN);
                    namaAtasan = atasan.getString(konfigurasi.TAG_NAMAATASAN);
                    namaGelarAtasan = atasan.getString(konfigurasi.TAG_NAMAGELARATASAN);
                }
                // broadcast
                JSONObject broadcast = jsonObject.getJSONObject(konfigurasi.TAG_BROADCAST);
                String statusBroadcast = broadcast.getString(konfigurasi.TAG_STATUSBROADCAST);
                String imageBroadcast = broadcast.getString(konfigurasi.TAG_IMAGEBROADCAST);
                String titleBroadcast = broadcast.getString(konfigurasi.TAG_TITLEBROADCAST);
                String messageBroadcast = broadcast.getString(konfigurasi.TAG_MESSAGEBROADCAST);

                // SIMPAN KE SHAREDPREF
                // message
                editor.putString(BUNDLE_USERTOKEN, apiToken);
                editor.putString(BUNDLE_ID_MESSAGE, id);
                editor.putString(BUNDLE_NAME_MESSAGE, nama);
                editor.putString(BUNDLE_EMAIL_MESSAGE, email);
                editor.putString(BUNDLE_USERNAME_MESSAGE, username);
                editor.putInt(BUNDLE_ROLEID_MESSAGE, roleId);
                editor.putString(BUNDLE_USERNIP_MESSAGE, userNip);
                editor.putString(BUNDLE_CREATEDAT_MESSAGE, createdAt);
                editor.putString(BUNDLE_UPDATEDAT_MESSAGE, updatedAt);
                editor.putString(BUNDLE_NOHP_MESSAGE, nomorHp);
                editor.putString(BUNDLE_AKTIF_MESSAGE, aktif);
                editor.putString(BUNDLE_KEYSORTUNIT_MESSAGE, keySortUnit);
                editor.putString(BUNDLE_NIPBARU_MESSAGE, nipBaru);
                editor.putBoolean(BUNDLE_ISLDAP_MESSAGE, isLdap);
                editor.putBoolean(BUNDLE_ISJAB_MESSAGE, isJab);
                editor.putInt(BUNDLE_VERSION_MESSAGE, version);
                editor.putBoolean(BUNDLE_ISHUT_MESSAGE, isHut);
                editor.putString(BUNDLE_URLFOTO_MESSAGE, urlFoto);
                editor.putBoolean(BUNDLE_ISATASAN_MESSAGE, isAtasan);
                // pegawai
                // atasan
                editor.putBoolean(BUNDLE_TIDAKPUNYAATASANLANGSUNG_ATASAN, tidakPunyaAtasanLangsung);
                // broadcast
                editor.putString(BUNDLE_STATUSBROADCAST_BROADCAST, statusBroadcast);
                editor.putString(BUNDLE_IMAGEBROADCAST_BROADCAST, imageBroadcast);
                editor.putString(BUNDLE_TITLEBROADCAST_BROADCAST, titleBroadcast);
                editor.putString(BUNDLE_MESSAGEBROADCAST_BROADCAST, messageBroadcast);

                switch (roleId) {
                    case UserRole.USER_ROLE_SUPERADMIN:
                        // pegawai null
                        // atasan null
                        break;
                    case UserRole.USER_ROLE_ADMINPUSAT:
                        // pegawai null
                        // atasan null
                        break;
                    case UserRole.USER_ROLE_ADMINUNIT:
                        // pegawai null
                        // atasan null
                        break;
                    case UserRole.USER_ROLE_PEGAWAI:
                        // pegawai
                        editor.putString(BUNDLE_ID_PEGAWAI, idPegawai);
                        editor.putString(BUNDLE_NIPLAMA_PEGAWAI, nipLamaPegawai);
                        editor.putString(BUNDLE_NIPBARU_PEGAWAI, nipBaruPegawai);
                        editor.putString(BUNDLE_NIPBARUGABUNG_PEGAWAI, nipBaruGabungPegawai);
                        editor.putString(BUNDLE_NAMA_PEGAWAI, namaPegawai);
                        editor.putString(BUNDLE_NAMALENGKAP_PEGAWAI, namaGelarPegawai);
                        editor.putString(BUNDLE_CARINAMANIP_PEGAWAI, nipBaruNamaPegawai);
                        editor.putString(BUNDLE_TEMPATLAHIR_PEGAWAI, tempatLahirPegawai);
                        editor.putString(BUNDLE_TANGGALLAHIR_PEGAWAI, tanggalLahirPegawai);
                        editor.putString(BUNDLE_JENISKELAMIN_PEGAWAI, jenisKelaminPegawai);
                        editor.putString(BUNDLE_AGAMA_PEGAWAI, agamaPegawai);
                        editor.putInt(BUNDLE_USIA_PEGAWAI, usiaPegawai);
                        editor.putString(BUNDLE_TMTPENSIUN_PEGAWAI, tmtPensiunPegawai);
                        editor.putString(BUNDLE_GOLRUANG_PEGAWAI, golRuangPegawai);
                        editor.putString(BUNDLE_PANGKAT_PEGAWAI, pangkatPegawai);
                        editor.putString(BUNDLE_TMTSK_PEGAWAI, tmtSkPegawai);
                        editor.putString(BUNDLE_LAMATHKP_PEGAWAI, lamaThKpPegawai);
                        editor.putString(BUNDLE_LAMABLKP_PEGAWAI, lamaBlKpPegawai);
                        editor.putString(BUNDLE_JABATAN_PEGAWAI, jabatanPegawai);
                        editor.putString(BUNDLE_JABATANSINGKAT_PEGAWAI, namaJabatanDetailSingkatPegawai);
                        editor.putString(BUNDLE_TMTJABATAN_PEGAWAI, tmtJabatanPegawai);
                        editor.putString(BUNDLE_KDJABATANDETAIL_PEGAWAI, kodeJabatanDetailPegawai);
                        editor.putString(BUNDLE_JENISJABATAN_PEGAWAI, jenisJabatanPegawai);
                        editor.putString(BUNDLE_JENISJABATANGRUP_PEGAWAI, jenisJabatanGrupPegawai);
                        editor.putString(BUNDLE_LAMATHJABATAN_PEGAWAI, lamaThJabatanPegawai);
                        editor.putString(BUNDLE_LAMABLJABATAN_PEGAWAI, lamaBLJabatanPegawai);
                        editor.putString(BUNDLE_PERAN_PEGAWAI, peranPegawai);
                        editor.putString(BUNDLE_UNITORG_PEGAWAI, namaInstansiUnitOrganisasiPegawai);
                        editor.putString(BUNDLE_UNITORGSINGKAT_PEGAWAI, namaInstansiUnitOrganisasiSingkatPegawai);
                        editor.putString(BUNDLE_UNITORGLENGKAP_PEGAWAI, namaUnitLengkapPegawai);
                        editor.putString(BUNDLE_TMTUNIT_PEGAWAI, tmtUnitPegawai);
                        editor.putString(BUNDLE_KDUNITORG_PEGAWAI, kodeInstansiUnitOrganisasiPegawai);
                        editor.putString(BUNDLE_KDUNITORGKERJA_PEGAWAI, kodeInstansiUnitKerjaPegawai);
                        editor.putString(BUNDLE_LAMATHUNIT_PEGAWAI, lamaThUnitPegawai);
                        editor.putString(BUNDLE_LAMABLUNIT_PEGAWAI, lamaBlUnitPegawai);
                        editor.putString(BUNDLE_UNIT_PEGAWAI, namaUnitPegawai);
                        editor.putString(BUNDLE_KEYSORTUNIT_PEGAWAI, keySortUnitPegawai);
                        editor.putString(BUNDLE_PENDIDIKANSINGKAT_PEGAWAI, pendidikanSingkatPegawai);
                        editor.putString(BUNDLE_PENDIDIKANSTRATASINGKAT_PEGAWAI, namaStrataSingkatPegawai);
                        editor.putString(BUNDLE_PENDIDIKANFAKULTAS_PEGAWAI, namaFakultasPegawai);
                        editor.putString(BUNDLE_PENDIDIKANJURUSAN_PEGAWAI, namaJurusanPegawai);
                        editor.putString(BUNDLE_PENDIDIKANLULUS_PEGAWAI, tanggalLulusPegawai);
                        editor.putString(BUNDLE_TOTALPAK_PEGAWAI, totalPakPegawai);
                        editor.putString(BUNDLE_TGLAWALXX_PEGAWAI, tanggalAwalPegawai);
                        editor.putString(BUNDLE_TGLAKHIRXX_PEGAWAI, tanggalAkhirPegawai);
                        editor.putString(BUNDLE_NOSKKGB_PEGAWAI, noSkKgbPegawai);
                        editor.putString(BUNDLE_THKGB_PEGAWAI, thKgbPegawai);
                        editor.putString(BUNDLE_BLKGB_PEGAWAI, blKgbPegawai);
                        editor.putString(BUNDLE_TMTKGB_PEGAWAI, tmtKgbPegawai);
                        editor.putString(BUNDLE_NAMADIKLATFUNG_PEGAWAI, namaDiklatFungsionalPegawai);
                        editor.putString(BUNDLE_NOSERTDIKLATFUNG_PEGAWAI, nomorSertifikatDiklatFungsionalPegawai);
                        editor.putString(BUNDLE_TGLSERTDIKLATFUNG_PEGAWAI, tanggalDiklatFungsionalPegawai);
                        editor.putString(BUNDLE_DIKLATSTRUK_PEGAWAI, diklatStrukturalPegawai);
                        editor.putString(BUNDLE_NOSERTDIKLATSTRUK_PEGAWAI, nomorDiklatStrukturalPegawai);
                        editor.putString(BUNDLE_TGLSERTDIKLATSTRUK_PEGAWAI, tanggalSertifikatDiklatStrukturalPegawai);
                        editor.putString(BUNDLE_SERTJFA_PEGAWAI, sertifikatJfaPegawai);
                        editor.putString(BUNDLE_NAMAPASANGAN_PEGAWAI, namaPasanganPegawai);
                        editor.putString(BUNDLE_UNITPASANGAN_PEGAWAI, unitPasanganPegawai);
                        editor.putString(BUNDLE_ALAMAT_PEGAWAI, alamatPegawai);
                        editor.putString(BUNDLE_SERTPROFESI_PEGAWAI, sertifikatProfesiPegawai);
                        editor.putString(BUNDLE_KELJABATAN_PEGAWAI, kelompokJabatanPegawai);
                        editor.putString(BUNDLE_STATUS_PEGAWAI, statusPegawai);
                        editor.putString(BUNDLE_IDSORT_PEGAWAI, idSortPegawai);
                        editor.putString(BUNDLE_TGLUPDATE_PEGAWAI, tanggalUpdatePegawai);

                        // atasan
                        editor.putString(BUNDLE_NIPATASAN_ATASAN, nipAtasan);
                        editor.putString(BUNDLE_NAMAATASAN_ATASAN, namaAtasan);
                        editor.putString(BUNDLE_NAMAGELARATASAN_ATASAN, namaGelarAtasan);
                        break;
                    case UserRole.USER_ROLE_BAPERJAKAT:
                        // pegawai
                        editor.putString(BUNDLE_ID_PEGAWAI, idPegawai);
                        editor.putString(BUNDLE_NIPLAMA_PEGAWAI, nipLamaPegawai);
                        editor.putString(BUNDLE_NIPBARU_PEGAWAI, nipBaruPegawai);
                        editor.putString(BUNDLE_NIPBARUGABUNG_PEGAWAI, nipBaruGabungPegawai);
                        editor.putString(BUNDLE_NAMA_PEGAWAI, namaPegawai);
                        editor.putString(BUNDLE_NAMALENGKAP_PEGAWAI, namaGelarPegawai);
                        editor.putString(BUNDLE_CARINAMANIP_PEGAWAI, nipBaruNamaPegawai);
                        editor.putString(BUNDLE_TEMPATLAHIR_PEGAWAI, tempatLahirPegawai);
                        editor.putString(BUNDLE_TANGGALLAHIR_PEGAWAI, tanggalLahirPegawai);
                        editor.putString(BUNDLE_JENISKELAMIN_PEGAWAI, jenisKelaminPegawai);
                        editor.putString(BUNDLE_AGAMA_PEGAWAI, agamaPegawai);
                        editor.putInt(BUNDLE_USIA_PEGAWAI, usiaPegawai);
                        editor.putString(BUNDLE_TMTPENSIUN_PEGAWAI, tmtPensiunPegawai);
                        editor.putString(BUNDLE_GOLRUANG_PEGAWAI, golRuangPegawai);
                        editor.putString(BUNDLE_PANGKAT_PEGAWAI, pangkatPegawai);
                        editor.putString(BUNDLE_TMTSK_PEGAWAI, tmtSkPegawai);
                        editor.putString(BUNDLE_LAMATHKP_PEGAWAI, lamaThKpPegawai);
                        editor.putString(BUNDLE_LAMABLKP_PEGAWAI, lamaBlKpPegawai);
                        editor.putString(BUNDLE_JABATAN_PEGAWAI, jabatanPegawai);
                        editor.putString(BUNDLE_JABATANSINGKAT_PEGAWAI, namaJabatanDetailSingkatPegawai);
                        editor.putString(BUNDLE_TMTJABATAN_PEGAWAI, tmtJabatanPegawai);
                        editor.putString(BUNDLE_KDJABATANDETAIL_PEGAWAI, kodeJabatanDetailPegawai);
                        editor.putString(BUNDLE_JENISJABATAN_PEGAWAI, jenisJabatanPegawai);
                        editor.putString(BUNDLE_JENISJABATANGRUP_PEGAWAI, jenisJabatanGrupPegawai);
                        editor.putString(BUNDLE_LAMATHJABATAN_PEGAWAI, lamaThJabatanPegawai);
                        editor.putString(BUNDLE_LAMABLJABATAN_PEGAWAI, lamaBLJabatanPegawai);
                        editor.putString(BUNDLE_PERAN_PEGAWAI, peranPegawai);
                        editor.putString(BUNDLE_UNITORG_PEGAWAI, namaInstansiUnitOrganisasiPegawai);
                        editor.putString(BUNDLE_UNITORGSINGKAT_PEGAWAI, namaInstansiUnitOrganisasiSingkatPegawai);
                        editor.putString(BUNDLE_UNITORGLENGKAP_PEGAWAI, namaUnitLengkapPegawai);
                        editor.putString(BUNDLE_TMTUNIT_PEGAWAI, tmtUnitPegawai);
                        editor.putString(BUNDLE_KDUNITORG_PEGAWAI, kodeInstansiUnitOrganisasiPegawai);
                        editor.putString(BUNDLE_KDUNITORGKERJA_PEGAWAI, kodeInstansiUnitKerjaPegawai);
                        editor.putString(BUNDLE_LAMATHUNIT_PEGAWAI, lamaThUnitPegawai);
                        editor.putString(BUNDLE_LAMABLUNIT_PEGAWAI, lamaBlUnitPegawai);
                        editor.putString(BUNDLE_UNIT_PEGAWAI, namaUnitPegawai);
                        editor.putString(BUNDLE_KEYSORTUNIT_PEGAWAI, keySortUnitPegawai);
                        editor.putString(BUNDLE_PENDIDIKANSINGKAT_PEGAWAI, pendidikanSingkatPegawai);
                        editor.putString(BUNDLE_PENDIDIKANSTRATASINGKAT_PEGAWAI, namaStrataSingkatPegawai);
                        editor.putString(BUNDLE_PENDIDIKANFAKULTAS_PEGAWAI, namaFakultasPegawai);
                        editor.putString(BUNDLE_PENDIDIKANJURUSAN_PEGAWAI, namaJurusanPegawai);
                        editor.putString(BUNDLE_PENDIDIKANLULUS_PEGAWAI, tanggalLulusPegawai);
                        editor.putString(BUNDLE_TOTALPAK_PEGAWAI, totalPakPegawai);
                        editor.putString(BUNDLE_TGLAWALXX_PEGAWAI, tanggalAwalPegawai);
                        editor.putString(BUNDLE_TGLAKHIRXX_PEGAWAI, tanggalAkhirPegawai);
                        editor.putString(BUNDLE_NOSKKGB_PEGAWAI, noSkKgbPegawai);
                        editor.putString(BUNDLE_THKGB_PEGAWAI, thKgbPegawai);
                        editor.putString(BUNDLE_BLKGB_PEGAWAI, blKgbPegawai);
                        editor.putString(BUNDLE_TMTKGB_PEGAWAI, tmtKgbPegawai);
                        editor.putString(BUNDLE_NAMADIKLATFUNG_PEGAWAI, namaDiklatFungsionalPegawai);
                        editor.putString(BUNDLE_NOSERTDIKLATFUNG_PEGAWAI, nomorSertifikatDiklatFungsionalPegawai);
                        editor.putString(BUNDLE_TGLSERTDIKLATFUNG_PEGAWAI, tanggalDiklatFungsionalPegawai);
                        editor.putString(BUNDLE_DIKLATSTRUK_PEGAWAI, diklatStrukturalPegawai);
                        editor.putString(BUNDLE_NOSERTDIKLATSTRUK_PEGAWAI, nomorDiklatStrukturalPegawai);
                        editor.putString(BUNDLE_TGLSERTDIKLATSTRUK_PEGAWAI, tanggalSertifikatDiklatStrukturalPegawai);
                        editor.putString(BUNDLE_SERTJFA_PEGAWAI, sertifikatJfaPegawai);
                        editor.putString(BUNDLE_NAMAPASANGAN_PEGAWAI, namaPasanganPegawai);
                        editor.putString(BUNDLE_UNITPASANGAN_PEGAWAI, unitPasanganPegawai);
                        editor.putString(BUNDLE_ALAMAT_PEGAWAI, alamatPegawai);
                        editor.putString(BUNDLE_SERTPROFESI_PEGAWAI, sertifikatProfesiPegawai);
                        editor.putString(BUNDLE_KELJABATAN_PEGAWAI, kelompokJabatanPegawai);
                        editor.putString(BUNDLE_STATUS_PEGAWAI, statusPegawai);
                        editor.putString(BUNDLE_IDSORT_PEGAWAI, idSortPegawai);
                        editor.putString(BUNDLE_TGLUPDATE_PEGAWAI, tanggalUpdatePegawai);

                        // atasan
                        editor.putString(BUNDLE_NIPATASAN_ATASAN, nipAtasan);
                        editor.putString(BUNDLE_NAMAATASAN_ATASAN, namaAtasan);
                        editor.putString(BUNDLE_NAMAGELARATASAN_ATASAN, namaGelarAtasan);
                        break;
                    case UserRole.USER_ROLE_MAC:
                        // pegawai
                        editor.putString(BUNDLE_ID_PEGAWAI, idPegawai);
                        editor.putString(BUNDLE_NIPLAMA_PEGAWAI, nipLamaPegawai);
                        editor.putString(BUNDLE_NIPBARU_PEGAWAI, nipBaruPegawai);
                        editor.putString(BUNDLE_NIPBARUGABUNG_PEGAWAI, nipBaruGabungPegawai);
                        editor.putString(BUNDLE_NAMA_PEGAWAI, namaPegawai);
                        editor.putString(BUNDLE_NAMALENGKAP_PEGAWAI, namaGelarPegawai);
                        editor.putString(BUNDLE_CARINAMANIP_PEGAWAI, nipBaruNamaPegawai);
                        editor.putString(BUNDLE_TEMPATLAHIR_PEGAWAI, tempatLahirPegawai);
                        editor.putString(BUNDLE_TANGGALLAHIR_PEGAWAI, tanggalLahirPegawai);
                        editor.putString(BUNDLE_JENISKELAMIN_PEGAWAI, jenisKelaminPegawai);
                        editor.putString(BUNDLE_AGAMA_PEGAWAI, agamaPegawai);
                        editor.putInt(BUNDLE_USIA_PEGAWAI, usiaPegawai);
                        editor.putString(BUNDLE_TMTPENSIUN_PEGAWAI, tmtPensiunPegawai);
                        editor.putString(BUNDLE_GOLRUANG_PEGAWAI, golRuangPegawai);
                        editor.putString(BUNDLE_PANGKAT_PEGAWAI, pangkatPegawai);
                        editor.putString(BUNDLE_TMTSK_PEGAWAI, tmtSkPegawai);
                        editor.putString(BUNDLE_LAMATHKP_PEGAWAI, lamaThKpPegawai);
                        editor.putString(BUNDLE_LAMABLKP_PEGAWAI, lamaBlKpPegawai);
                        editor.putString(BUNDLE_JABATAN_PEGAWAI, jabatanPegawai);
                        editor.putString(BUNDLE_JABATANSINGKAT_PEGAWAI, namaJabatanDetailSingkatPegawai);
                        editor.putString(BUNDLE_TMTJABATAN_PEGAWAI, tmtJabatanPegawai);
                        editor.putString(BUNDLE_KDJABATANDETAIL_PEGAWAI, kodeJabatanDetailPegawai);
                        editor.putString(BUNDLE_JENISJABATAN_PEGAWAI, jenisJabatanPegawai);
                        editor.putString(BUNDLE_JENISJABATANGRUP_PEGAWAI, jenisJabatanGrupPegawai);
                        editor.putString(BUNDLE_LAMATHJABATAN_PEGAWAI, lamaThJabatanPegawai);
                        editor.putString(BUNDLE_LAMABLJABATAN_PEGAWAI, lamaBLJabatanPegawai);
                        editor.putString(BUNDLE_PERAN_PEGAWAI, peranPegawai);
                        editor.putString(BUNDLE_UNITORG_PEGAWAI, namaInstansiUnitOrganisasiPegawai);
                        editor.putString(BUNDLE_UNITORGSINGKAT_PEGAWAI, namaInstansiUnitOrganisasiSingkatPegawai);
                        editor.putString(BUNDLE_UNITORGLENGKAP_PEGAWAI, namaUnitLengkapPegawai);
                        editor.putString(BUNDLE_TMTUNIT_PEGAWAI, tmtUnitPegawai);
                        editor.putString(BUNDLE_KDUNITORG_PEGAWAI, kodeInstansiUnitOrganisasiPegawai);
                        editor.putString(BUNDLE_KDUNITORGKERJA_PEGAWAI, kodeInstansiUnitKerjaPegawai);
                        editor.putString(BUNDLE_LAMATHUNIT_PEGAWAI, lamaThUnitPegawai);
                        editor.putString(BUNDLE_LAMABLUNIT_PEGAWAI, lamaBlUnitPegawai);
                        editor.putString(BUNDLE_UNIT_PEGAWAI, namaUnitPegawai);
                        editor.putString(BUNDLE_KEYSORTUNIT_PEGAWAI, keySortUnitPegawai);
                        editor.putString(BUNDLE_PENDIDIKANSINGKAT_PEGAWAI, pendidikanSingkatPegawai);
                        editor.putString(BUNDLE_PENDIDIKANSTRATASINGKAT_PEGAWAI, namaStrataSingkatPegawai);
                        editor.putString(BUNDLE_PENDIDIKANFAKULTAS_PEGAWAI, namaFakultasPegawai);
                        editor.putString(BUNDLE_PENDIDIKANJURUSAN_PEGAWAI, namaJurusanPegawai);
                        editor.putString(BUNDLE_PENDIDIKANLULUS_PEGAWAI, tanggalLulusPegawai);
                        editor.putString(BUNDLE_TOTALPAK_PEGAWAI, totalPakPegawai);
                        editor.putString(BUNDLE_TGLAWALXX_PEGAWAI, tanggalAwalPegawai);
                        editor.putString(BUNDLE_TGLAKHIRXX_PEGAWAI, tanggalAkhirPegawai);
                        editor.putString(BUNDLE_NOSKKGB_PEGAWAI, noSkKgbPegawai);
                        editor.putString(BUNDLE_THKGB_PEGAWAI, thKgbPegawai);
                        editor.putString(BUNDLE_BLKGB_PEGAWAI, blKgbPegawai);
                        editor.putString(BUNDLE_TMTKGB_PEGAWAI, tmtKgbPegawai);
                        editor.putString(BUNDLE_NAMADIKLATFUNG_PEGAWAI, namaDiklatFungsionalPegawai);
                        editor.putString(BUNDLE_NOSERTDIKLATFUNG_PEGAWAI, nomorSertifikatDiklatFungsionalPegawai);
                        editor.putString(BUNDLE_TGLSERTDIKLATFUNG_PEGAWAI, tanggalDiklatFungsionalPegawai);
                        editor.putString(BUNDLE_DIKLATSTRUK_PEGAWAI, diklatStrukturalPegawai);
                        editor.putString(BUNDLE_NOSERTDIKLATSTRUK_PEGAWAI, nomorDiklatStrukturalPegawai);
                        editor.putString(BUNDLE_TGLSERTDIKLATSTRUK_PEGAWAI, tanggalSertifikatDiklatStrukturalPegawai);
                        editor.putString(BUNDLE_SERTJFA_PEGAWAI, sertifikatJfaPegawai);
                        editor.putString(BUNDLE_NAMAPASANGAN_PEGAWAI, namaPasanganPegawai);
                        editor.putString(BUNDLE_UNITPASANGAN_PEGAWAI, unitPasanganPegawai);
                        editor.putString(BUNDLE_ALAMAT_PEGAWAI, alamatPegawai);
                        editor.putString(BUNDLE_SERTPROFESI_PEGAWAI, sertifikatProfesiPegawai);
                        editor.putString(BUNDLE_KELJABATAN_PEGAWAI, kelompokJabatanPegawai);
                        editor.putString(BUNDLE_STATUS_PEGAWAI, statusPegawai);
                        editor.putString(BUNDLE_IDSORT_PEGAWAI, idSortPegawai);
                        editor.putString(BUNDLE_TGLUPDATE_PEGAWAI, tanggalUpdatePegawai);

                        // atasan
                        editor.putString(BUNDLE_NIPATASAN_ATASAN, nipAtasan);
                        editor.putString(BUNDLE_NAMAATASAN_ATASAN, namaAtasan);
                        editor.putString(BUNDLE_NAMAGELARATASAN_ATASAN, namaGelarAtasan);
                        break;
                    case UserRole.USER_ROLE_ADMINDUA:
                        // pegawai
                        editor.putString(BUNDLE_ID_PEGAWAI, idPegawai);
                        editor.putString(BUNDLE_NIPLAMA_PEGAWAI, nipLamaPegawai);
                        editor.putString(BUNDLE_NIPBARU_PEGAWAI, nipBaruPegawai);
                        editor.putString(BUNDLE_NIPBARUGABUNG_PEGAWAI, nipBaruGabungPegawai);
                        editor.putString(BUNDLE_NAMA_PEGAWAI, namaPegawai);
                        editor.putString(BUNDLE_NAMALENGKAP_PEGAWAI, namaGelarPegawai);
                        editor.putString(BUNDLE_CARINAMANIP_PEGAWAI, nipBaruNamaPegawai);
                        editor.putString(BUNDLE_TEMPATLAHIR_PEGAWAI, tempatLahirPegawai);
                        editor.putString(BUNDLE_TANGGALLAHIR_PEGAWAI, tanggalLahirPegawai);
                        editor.putString(BUNDLE_JENISKELAMIN_PEGAWAI, jenisKelaminPegawai);
                        editor.putString(BUNDLE_AGAMA_PEGAWAI, agamaPegawai);
                        editor.putInt(BUNDLE_USIA_PEGAWAI, usiaPegawai);
                        editor.putString(BUNDLE_TMTPENSIUN_PEGAWAI, tmtPensiunPegawai);
                        editor.putString(BUNDLE_GOLRUANG_PEGAWAI, golRuangPegawai);
                        editor.putString(BUNDLE_PANGKAT_PEGAWAI, pangkatPegawai);
                        editor.putString(BUNDLE_TMTSK_PEGAWAI, tmtSkPegawai);
                        editor.putString(BUNDLE_LAMATHKP_PEGAWAI, lamaThKpPegawai);
                        editor.putString(BUNDLE_LAMABLKP_PEGAWAI, lamaBlKpPegawai);
                        editor.putString(BUNDLE_JABATAN_PEGAWAI, jabatanPegawai);
                        editor.putString(BUNDLE_JABATANSINGKAT_PEGAWAI, namaJabatanDetailSingkatPegawai);
                        editor.putString(BUNDLE_TMTJABATAN_PEGAWAI, tmtJabatanPegawai);
                        editor.putString(BUNDLE_KDJABATANDETAIL_PEGAWAI, kodeJabatanDetailPegawai);
                        editor.putString(BUNDLE_JENISJABATAN_PEGAWAI, jenisJabatanPegawai);
                        editor.putString(BUNDLE_JENISJABATANGRUP_PEGAWAI, jenisJabatanGrupPegawai);
                        editor.putString(BUNDLE_LAMATHJABATAN_PEGAWAI, lamaThJabatanPegawai);
                        editor.putString(BUNDLE_LAMABLJABATAN_PEGAWAI, lamaBLJabatanPegawai);
                        editor.putString(BUNDLE_PERAN_PEGAWAI, peranPegawai);
                        editor.putString(BUNDLE_UNITORG_PEGAWAI, namaInstansiUnitOrganisasiPegawai);
                        editor.putString(BUNDLE_UNITORGSINGKAT_PEGAWAI, namaInstansiUnitOrganisasiSingkatPegawai);
                        editor.putString(BUNDLE_UNITORGLENGKAP_PEGAWAI, namaUnitLengkapPegawai);
                        editor.putString(BUNDLE_TMTUNIT_PEGAWAI, tmtUnitPegawai);
                        editor.putString(BUNDLE_KDUNITORG_PEGAWAI, kodeInstansiUnitOrganisasiPegawai);
                        editor.putString(BUNDLE_KDUNITORGKERJA_PEGAWAI, kodeInstansiUnitKerjaPegawai);
                        editor.putString(BUNDLE_LAMATHUNIT_PEGAWAI, lamaThUnitPegawai);
                        editor.putString(BUNDLE_LAMABLUNIT_PEGAWAI, lamaBlUnitPegawai);
                        editor.putString(BUNDLE_UNIT_PEGAWAI, namaUnitPegawai);
                        editor.putString(BUNDLE_KEYSORTUNIT_PEGAWAI, keySortUnitPegawai);
                        editor.putString(BUNDLE_PENDIDIKANSINGKAT_PEGAWAI, pendidikanSingkatPegawai);
                        editor.putString(BUNDLE_PENDIDIKANSTRATASINGKAT_PEGAWAI, namaStrataSingkatPegawai);
                        editor.putString(BUNDLE_PENDIDIKANFAKULTAS_PEGAWAI, namaFakultasPegawai);
                        editor.putString(BUNDLE_PENDIDIKANJURUSAN_PEGAWAI, namaJurusanPegawai);
                        editor.putString(BUNDLE_PENDIDIKANLULUS_PEGAWAI, tanggalLulusPegawai);
                        editor.putString(BUNDLE_TOTALPAK_PEGAWAI, totalPakPegawai);
                        editor.putString(BUNDLE_TGLAWALXX_PEGAWAI, tanggalAwalPegawai);
                        editor.putString(BUNDLE_TGLAKHIRXX_PEGAWAI, tanggalAkhirPegawai);
                        editor.putString(BUNDLE_NOSKKGB_PEGAWAI, noSkKgbPegawai);
                        editor.putString(BUNDLE_THKGB_PEGAWAI, thKgbPegawai);
                        editor.putString(BUNDLE_BLKGB_PEGAWAI, blKgbPegawai);
                        editor.putString(BUNDLE_TMTKGB_PEGAWAI, tmtKgbPegawai);
                        editor.putString(BUNDLE_NAMADIKLATFUNG_PEGAWAI, namaDiklatFungsionalPegawai);
                        editor.putString(BUNDLE_NOSERTDIKLATFUNG_PEGAWAI, nomorSertifikatDiklatFungsionalPegawai);
                        editor.putString(BUNDLE_TGLSERTDIKLATFUNG_PEGAWAI, tanggalDiklatFungsionalPegawai);
                        editor.putString(BUNDLE_DIKLATSTRUK_PEGAWAI, diklatStrukturalPegawai);
                        editor.putString(BUNDLE_NOSERTDIKLATSTRUK_PEGAWAI, nomorDiklatStrukturalPegawai);
                        editor.putString(BUNDLE_TGLSERTDIKLATSTRUK_PEGAWAI, tanggalSertifikatDiklatStrukturalPegawai);
                        editor.putString(BUNDLE_SERTJFA_PEGAWAI, sertifikatJfaPegawai);
                        editor.putString(BUNDLE_NAMAPASANGAN_PEGAWAI, namaPasanganPegawai);
                        editor.putString(BUNDLE_UNITPASANGAN_PEGAWAI, unitPasanganPegawai);
                        editor.putString(BUNDLE_ALAMAT_PEGAWAI, alamatPegawai);
                        editor.putString(BUNDLE_SERTPROFESI_PEGAWAI, sertifikatProfesiPegawai);
                        editor.putString(BUNDLE_KELJABATAN_PEGAWAI, kelompokJabatanPegawai);
                        editor.putString(BUNDLE_STATUS_PEGAWAI, statusPegawai);
                        editor.putString(BUNDLE_IDSORT_PEGAWAI, idSortPegawai);
                        editor.putString(BUNDLE_TGLUPDATE_PEGAWAI, tanggalUpdatePegawai);

                        // atasan
                        editor.putString(BUNDLE_NIPATASAN_ATASAN, nipAtasan);
                        editor.putString(BUNDLE_NAMAATASAN_ATASAN, namaAtasan);
                        editor.putString(BUNDLE_NAMAGELARATASAN_ATASAN, namaGelarAtasan);
                        break;
                }
                editor.apply();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //

    public static void clearLogin(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(BUNDLE_USERTOKEN, null);
        editor.putString(BUNDLE_ID_MESSAGE, null);
        editor.putString(BUNDLE_NAME_MESSAGE, null);
        editor.putString(BUNDLE_EMAIL_MESSAGE, null);
        editor.putString(BUNDLE_USERNAME_MESSAGE, null);
        editor.putInt(BUNDLE_ROLEID_MESSAGE, 9999);
        editor.putString(BUNDLE_USERNIP_MESSAGE, null);
        editor.putString(BUNDLE_CREATEDAT_MESSAGE, null);
        editor.putString(BUNDLE_UPDATEDAT_MESSAGE, null);
        editor.putString(BUNDLE_NOHP_MESSAGE, null);
        editor.putString(BUNDLE_AKTIF_MESSAGE, null);
        editor.putString(BUNDLE_KEYSORTUNIT_MESSAGE, null);
        editor.putString(BUNDLE_NIPBARU_MESSAGE, null);
        editor.putBoolean(BUNDLE_ISLDAP_MESSAGE, false);
        editor.putBoolean(BUNDLE_ISJAB_MESSAGE, false);
        editor.putInt(BUNDLE_VERSION_MESSAGE, 9999);
        editor.putBoolean(BUNDLE_ISHUT_MESSAGE, false);
        editor.putString(BUNDLE_URLFOTO_MESSAGE, null);
        editor.putBoolean(BUNDLE_ISATASAN_MESSAGE, false);
        // pegawai
        // atasan
        editor.putBoolean(BUNDLE_TIDAKPUNYAATASANLANGSUNG_ATASAN, false);
        // broadcast
        editor.putString(BUNDLE_STATUSBROADCAST_BROADCAST, null);
        editor.putString(BUNDLE_IMAGEBROADCAST_BROADCAST, null);
        editor.putString(BUNDLE_TITLEBROADCAST_BROADCAST, null);
        editor.putString(BUNDLE_MESSAGEBROADCAST_BROADCAST, null);

        editor.putString(BUNDLE_ID_PEGAWAI, null);
        editor.putString(BUNDLE_NIPLAMA_PEGAWAI, null);
        editor.putString(BUNDLE_NIPBARU_PEGAWAI, null);
        editor.putString(BUNDLE_NIPBARUGABUNG_PEGAWAI, null);
        editor.putString(BUNDLE_NAMA_PEGAWAI, null);
        editor.putString(BUNDLE_NAMALENGKAP_PEGAWAI, null);
        editor.putString(BUNDLE_CARINAMANIP_PEGAWAI, null);
        editor.putString(BUNDLE_TEMPATLAHIR_PEGAWAI, null);
        editor.putString(BUNDLE_TANGGALLAHIR_PEGAWAI, null);
        editor.putString(BUNDLE_JENISKELAMIN_PEGAWAI, null);
        editor.putString(BUNDLE_AGAMA_PEGAWAI, null);
        editor.putInt(BUNDLE_USIA_PEGAWAI, 9999);
        editor.putString(BUNDLE_TMTPENSIUN_PEGAWAI, null);
        editor.putString(BUNDLE_GOLRUANG_PEGAWAI, null);
        editor.putString(BUNDLE_PANGKAT_PEGAWAI, null);
        editor.putString(BUNDLE_TMTSK_PEGAWAI, null);
        editor.putString(BUNDLE_LAMATHKP_PEGAWAI, null);
        editor.putString(BUNDLE_LAMABLKP_PEGAWAI, null);
        editor.putString(BUNDLE_JABATAN_PEGAWAI, null);
        editor.putString(BUNDLE_JABATANSINGKAT_PEGAWAI, null);
        editor.putString(BUNDLE_TMTJABATAN_PEGAWAI, null);
        editor.putString(BUNDLE_KDJABATANDETAIL_PEGAWAI, null);
        editor.putString(BUNDLE_JENISJABATAN_PEGAWAI, null);
        editor.putString(BUNDLE_JENISJABATANGRUP_PEGAWAI, null);
        editor.putString(BUNDLE_LAMATHJABATAN_PEGAWAI, null);
        editor.putString(BUNDLE_LAMABLJABATAN_PEGAWAI, null);
        editor.putString(BUNDLE_PERAN_PEGAWAI, null);
        editor.putString(BUNDLE_UNITORG_PEGAWAI, null);
        editor.putString(BUNDLE_UNITORGSINGKAT_PEGAWAI, null);
        editor.putString(BUNDLE_UNITORGLENGKAP_PEGAWAI, null);
        editor.putString(BUNDLE_TMTUNIT_PEGAWAI, null);
        editor.putString(BUNDLE_KDUNITORG_PEGAWAI, null);
        editor.putString(BUNDLE_KDUNITORGKERJA_PEGAWAI, null);
        editor.putString(BUNDLE_LAMATHUNIT_PEGAWAI, null);
        editor.putString(BUNDLE_LAMABLUNIT_PEGAWAI, null);
        editor.putString(BUNDLE_UNIT_PEGAWAI, null);
        editor.putString(BUNDLE_KEYSORTUNIT_PEGAWAI, null);
        editor.putString(BUNDLE_PENDIDIKANSINGKAT_PEGAWAI, null);
        editor.putString(BUNDLE_PENDIDIKANSTRATASINGKAT_PEGAWAI, null);
        editor.putString(BUNDLE_PENDIDIKANFAKULTAS_PEGAWAI, null);
        editor.putString(BUNDLE_PENDIDIKANJURUSAN_PEGAWAI, null);
        editor.putString(BUNDLE_PENDIDIKANLULUS_PEGAWAI, null);
        editor.putString(BUNDLE_TOTALPAK_PEGAWAI, null);
        editor.putString(BUNDLE_TGLAWALXX_PEGAWAI, null);
        editor.putString(BUNDLE_TGLAKHIRXX_PEGAWAI, null);
        editor.putString(BUNDLE_NOSKKGB_PEGAWAI, null);
        editor.putString(BUNDLE_THKGB_PEGAWAI, null);
        editor.putString(BUNDLE_BLKGB_PEGAWAI, null);
        editor.putString(BUNDLE_TMTKGB_PEGAWAI, null);
        editor.putString(BUNDLE_NAMADIKLATFUNG_PEGAWAI, null);
        editor.putString(BUNDLE_NOSERTDIKLATFUNG_PEGAWAI, null);
        editor.putString(BUNDLE_TGLSERTDIKLATFUNG_PEGAWAI, null);
        editor.putString(BUNDLE_DIKLATSTRUK_PEGAWAI, null);
        editor.putString(BUNDLE_NOSERTDIKLATSTRUK_PEGAWAI, null);
        editor.putString(BUNDLE_TGLSERTDIKLATSTRUK_PEGAWAI, null);
        editor.putString(BUNDLE_SERTJFA_PEGAWAI, null);
        editor.putString(BUNDLE_NAMAPASANGAN_PEGAWAI, null);
        editor.putString(BUNDLE_UNITPASANGAN_PEGAWAI, null);
        editor.putString(BUNDLE_ALAMAT_PEGAWAI, null);
        editor.putString(BUNDLE_SERTPROFESI_PEGAWAI, null);
        editor.putString(BUNDLE_KELJABATAN_PEGAWAI, null);
        editor.putString(BUNDLE_STATUS_PEGAWAI, null);
        editor.putString(BUNDLE_IDSORT_PEGAWAI, null);
        editor.putString(BUNDLE_TGLUPDATE_PEGAWAI, null);

        // atasan
        editor.putString(BUNDLE_NIPATASAN_ATASAN, null);
        editor.putString(BUNDLE_NAMAATASAN_ATASAN, null);
        editor.putString(BUNDLE_NAMAGELARATASAN_ATASAN, null);

        editor.apply();
    }
}
