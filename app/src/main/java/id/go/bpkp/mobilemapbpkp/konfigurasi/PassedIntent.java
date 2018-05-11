package id.go.bpkp.mobilemapbpkp.konfigurasi;

/**
 * Created by ASUS on 12/03/2018.
 */

public class PassedIntent {

    public static final String ISLOGGEDIN = "is_logged_in";
    public static final String INTENT_USERTOKEN = "user_token";
    // username yg dipake pas login
    public static final String INTENT_USERNAME = "username";
    public static final String INTENT_PASSWORD = "password";
    // nama pegawai
    public static final String INTENT_NAMA = "nama";
    // nip
    public static final String INTENT_NIPLAMA = "nip_lama";
    public static final String INTENT_NIPBARU = "nip_baru";
    // roleId
    public static final String INTENT_ROLEID = "role_id";
    public static final String INTENT_ROLEIDINT = "role_id_int";
    public static final String INTENT_LDAP = "ldap";
    public static final String INTENT_ISLDAP = "is_ldap";
    public static final String INTENT_ISJAB = "is_jab";
    // no hp
    public static final String INTENT_NOHP = "no_hp";
    public static final String INTENT_IMEI = "imei";
    // email
    public static final String INTENT_EMAIL = "email";
    // atasan langsung
    public static final String INTENT_TIDAKPUNYAATASANLANGSUNG = "tidak_punya_atasan_langsung";
    public static final String INTENT_NAMAATASANLANGSUNG = "nama_atasan_langsung";
    public static final String INTENT_NIPATASANLANGSUNG = "nip_atasan_langsung";
    // dashboard content
    public static final String INTENT_DASHBOARDCONTENT = "content_url";
    // fragment url
    public static final String INTENT_FRAGMENTCONTENT = "fragment_url";

    // foto
    public static final String INTENT_FOTO = "foto";
    public static final String INTENT_FOTOURL = "http://118.97.51.140:10000/Sispedap/showImage.do?jk=Laki-laki&id=";

    public static String getFoto(String nipLama) {
        return INTENT_FOTOURL + nipLama;
    }
}
