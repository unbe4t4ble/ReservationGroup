package kidzania.reservationgroup.Misc;

import android.database.Cursor;

import java.util.ArrayList;

import kidzania.reservationgroup.SQLite.DataSQLlite;

public class VarGlobal {

    //Jenis jenis STS_MODIF
    // 2 (Data / Available)
    // 0 (Draft)
    // 1 (Checking)

    public static final ArrayList<String> APIValueParams = new ArrayList<>();
    public static final ArrayList<String> APIParameters = new ArrayList<>();

    public static DataSQLlite dbHelper;
    public static Cursor cursor;

    public static int POSITION_DATA;

    //SENDER NAME CLASS
    public static String SENDER_CLASS;

    //no respon json
    public static final String GROUP_TIMEOUT = "GROUP_NOT_RESPON";
    public static final String GROUP_TRY_AGAIN = "GROUP_TRY_AGAIN";
    public static final String RESV_EMPTY_DATA = "EMPTY_DATA";

    //broadcast from search
    public static final String GET_TEXT_SEARCH = "GET_TEXT_SEARCH";
    public static final String GET_SELECT_GROUP = "GET_SELECT_GROUP";

    //broadcast from draft
    public static final String GET_DETAIL_DATA_GROUP = "GET_DETAIL_DATA_GROUP";
    public static final String TAKE_PHOTO_GROUP = "TAKE_PHOTO_GROUP";
    public static final String POSTING_GROUP = "POSTING_GROUP";

    //broadcast from data
    public static final String SEND_DRAFT = "SEND_DRAFT";

    //broadcast from modif
    public static final String NOTIF_FINISH = "FINISH";

    //status edit atau insert
    public static boolean IS_EDIT = false;
    public static boolean IS_PERSONAL = false;

    //header json
    public static final String HEAD_LOGIN = "LOGIN";
    public static final String HEAD_GROUP_AVAIL = "GROUP_AVAIL";
    public static final String HEAD_GROUP_DATA = "GROUP_DATA";
    public static final String HEAD_GROUP_DRAFT = "GROUP_DRAFT";
    public static final String HEAD_GROUP_OWNER = "GROUP_OWNER";
    public static final String HEAD_GROUP_PERSONAL = "GROUP_PERSONAL";
    public static final String HEAD_STATUS = "ISSUCCESS";
    public static final String HEAD_SEARCH_PROVINCE = "SEARCH_PROVINCE";
    public static final String HEAD_SEARCH_GROUPTYPE = "SEARCH_GROUPTYPE";
    public static final String HEAD_SEARCH_CITY = "SEARCH_CITY";
    public static final String HEAD_SEARCH_DISTRICT = "SEARCH_DISTRICT";
    public static final String HEAD_SEARCH_DISTRICT_AREA = "SEARCH_DISTRICT_AREA";
    public static final String HEAD_GET_DETAIL_GROUP = "GET_DATA_GROUP";
    public static final String HEAD_GET_QUOTA_REGISTER = "QUOTA_REGISTERED";
    public static final String HEAD_GET_QUOTA_RESERVATION = "QUOTA_RESERVATION";
    public static final String HEAD_GET_ALL_QUOTA = "QUOTA";
    public static final String HEAD_GET_POPULASI_FAMILY1 = "POPULASI_FAMILY1";
    public static final String HEAD_GET_POPULASI_FAMILY2 = "POPULASI_FAMILY2";
    public static final String HEAD_GET_POPULASI_GROUP = "POPULASI_GROUP";
    public static final String HEAD_GET_POPULASI_PARTY = "POPULASI_PARTY";
    public static final String HEAD_GET_DATA_BOOKING = "DATA_BOOKING";
    public static final String HEAD_GET_DATA_RESERVATION = "DATA_RESERVATION";
    public static final String HEAD_GET_DATA_SUPPORTER = "SEARCH_SUPPORTER";
    public static final String HEAD_GET_DATA_TICKET_PACK = "SEARCH_TICKET";
    public static final String HEAD_GET_DATA_HARGA_TICKET_PACK = "DATA_HARGA";
    public static final String HEAD_GET_SPECIAL_QUOTA = "DATA_SPECIAL_QUOTA";
    public static final String HEAD_GET_DATA_PROMOTOR = "SEARCH_PROMOTOR";
    public static final String HEAD_GET_DATA_RESPONSIBLE = "SEARCH_RESPONSIBLE";
    public static final String HEAD_GET_DATA_PACKAGE = "SEARCH_PACKAGE";
    public static final String HEAD_GET_DATA_HARGA_PACKAGE = "DATA_HARGA_PACKAGE";
    static final String HEAD_GET_DATA_PACKAGE_RESERV = "DATA_PACKAGE_RESERV";
    public static final String HEAD_GET_DATA_PERSONAL_ID = "GET_DATA_PERSONAL";
    public static final String HEAD_GET_DATA_HISTORY = "DATA_HISTORY";

    //String LOGIN
    public static String ID_USER, U_LOGIN, U_PASS, GRPRSVMOD, FLAG;

    //String Data Group yang di save di DB
    public static String ID_NUM_ESC = ""; //ID Group
    public static String GRPNAME = ""; //Nama Group
    public static String FECHA_ALTA = ""; //Tanggal pembuatan
    public static String STATUS_GROUP = "";
    public static String GTYPE = ""; //Tipe Group
    public static String GRADE = ""; //Tingkat SD/SMP/SMA
    public static String GRADE_TYP = ""; // Tipe Tingkat SD Negeri/SD Swasta dll.
    public static String SCH_TYPE = ""; // Tipe Tingkat SD Negeri/SD Swasta dll.
    public static String ADDR = ""; //Alamat Sekolah/Group
    public static String PROVINCE = ""; // Provinci
    public static String CITY = ""; // Kota/Kabupaten.
    public static String DISTRICT = ""; //Kecamatan
    public static String AREA = ""; //Kelurahan
    public static String ZIPCODE = ""; // Kodepos
    public static String PHONE = ""; // telepon sekolah
    public static String FAX = ""; //Fax sekolah
    public static String EMAIL = ""; //email sekolah
    public static String PRINCIPAL = ""; // kepala sekolah/group
    public static String PRINC_HP = ""; // no handphone kepala sekolah
    public static String PIC = ""; // PIC
    public static String NO_HP = ""; // no handphone PIC
    public static String AMNT_T = ""; // jumlah anak dibawah 5 tahun
    public static String AMNT_C = ""; // jumlah anak anak
    public static String AMNT_A = ""; // jumlah guru/dewasa.
    public static String BGT_TRIP = ""; //Bugdet trip
    public static String AMNT_FILTRP = ""; // frekuensi trip
    public static String FILTRP = ""; // field trip/jadwal wisata
    public static String PLC_TRIP = ""; // place trip/tempat wisata
    public static String IDUSR_OWN = ""; // pemilik group/ id Sales
    public static String PIC_ID = ""; // pemilik group/ id Sales

    //String get ID dari class search;
    public static String ID_PROVINCE; // Provinci
    public static String ID_CITY; // Kota/Kabupaten.
    public static String ID_DISTRICT; //Kecamatan

    //String field Reservation_esc
    public static String ID_NUM_RESER; // id reservasi
    public static String FECHA_VISITA; //tanggal kunjungan
    public static int TURNO; // shift
    public static String FECHA_CREACION; // tanggal pembuatan
    public static String USUARIO_ALTA; //ID SUPPORTER
    public static String ID_NUM_ESC_RESERVATION; //ID GROUP
    public static String ID_RESP_ESC; //ID PROMOTOR
    public static String ID_NUM_AGE; //
    public static String ID_RESP_AGE; //ID RESPONSIBLE
    public static String ID_PAQ; //
    public static String BEBE;
    public static String INFANTE;
    public static String NINO;
    public static String NDISC;
    public static String ADULTO;
    public static String ADISC;
    public static String INSEN;
    public static String SENIOR;
    public static String HANDICAP;
    public static String PROM;
    public static String ID_LUNCH;
    public static String CANT_LUNCH;
    public static String ID_LUNCH1;
    public static String DOC_NO;
    public static String ID_SOUV;
    public static String SETTLED;
    public static String ARR_TIME; //Waktu kedatangan
    public static String TOTAL_APAGAR;
    public static String STATUS_RESERV;
    public static String ADD_T5;
    public static String ADD_C5;
    public static String ADD_A5;
    public static String ADD_T7;
    public static String ADD_C7;
    public static String ADD_A7;
    public static String COMPLIMENT5;
    public static String COMPLIMENT7;
    public static String STATUS_RESERVATION;
    public static String HORA_SALIDA; //Waktu pulang
    public static String GPO;
    public static String OTH_BUS;
    public static String PRIV_CAR;
    public static String COMIDA;
    public static String SVR_ADICIONAL;
    public static String CANT_SVRADI;
    public static String PROMOTOR_CODE;
    public static String TAX;
    public static String RSV_NO;
    public static String CB_NAME;
    public static String CB_BNAME;
    public static String CB_ACCNO;
    public static String CB_HP;
    public static String CB_TODDLER;
    public static String CB_CHILD;
    public static String CB_ADULT;
    public static String PC;
    public static String GRPVOU;
    public static String RSV_T5;
    public static String RSV_C5;
    public static String RSV_A5;
    public static String RSV_T7;
    public static String RSV_C7;
    public static String RSV_A7;
    public static String RSV_OTH;
    public static String RSV_SEN;
    public static String RSV_HAN;
    public static String IDPACK_ADD;
    public static String NOTE;

    //variable terkait quota
    /*Get reservation quota*/
    public static int G_RsvCQuota = 0;
    public static int G_RsvAQuota = 0;
    public static int G_SQuotaApply = 0;

    /*Get presale quota*/
    public static int G_MPopChild = 0;
    public static int G_MPSChild = 0;
    public static int G_MAdult = 0;

    /*Get child and adult remaining quota*/
    public static int L_TChild = 0;
    public static int L_TAdult = 0;

    /*Get special quota setting*/
    public static int L_CXQuota;
    public static int L_AXQuota;

    //variable untuk menampung value dari ID di database
    public static String STR_ID_NUM_ESC;
    public static String STR_USUARIO_ALTA;
    public static String STR_ID_PAQ;
    public static String STR_ID_RESP_ESC;
    public static String STR_ID_RESP_AGE;
    public static String STR_IDPACK_ADD;
    public static String PriceTodd, PriceChild, PriceAdult, PriceAddTodd, PriceAddChild, PriceAddAdult, SumPrice;
    public static String AmountTodd, AmountChild, AmountAdult, SumAmountTodd, SumAmountChild, SumAmountAdult;
    public static String TempTodd, TempChild, TempAdult, TempBaby, TempSenior, TempHandyCap, TempAddTodd, TempAddChild, TempAddAdult;
    public static String TAG_PACK;
    public static String ID_PACK, STR_PACK, HARGA_PACKAGE;
    public static int AllAmountGetPaket;
    public static Boolean isCombineLunch = false, isCombineSouvenir = false, isCombineAll = false;
    public static Boolean isModifReservation = false;
    public static Boolean isAdmin = false;
    public static boolean isFromList = false;
    public static String LunchBefore, SouvernirBefore, BusBefore;

}
