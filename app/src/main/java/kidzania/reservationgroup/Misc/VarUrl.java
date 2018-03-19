package kidzania.reservationgroup.Misc;

public class VarUrl {

    //Token
    public static final String TOKEN = "Skjhu8920kjaskjr3KDj2";
    /*
    //Ip dan sub folder local
    private static final String IP = "http://192.168.0.4/";
    private static final String PATH_FILE = "ArikJson/";
    private static final String IP_PATH_FILE = IP+PATH_FILE;
    private static final String IP_PATH_FILE_IMAGE = IP+PATH_FILE;
    */

    //Ip dan sub folder public
    private static final String IP = "http://xxxxxxxxxxxxx/";
    private static final String PATH_FILE = "aReservationGroup/";
    private static final String PATH_FILE_IMAGE = "android/php/image/";
    private static final String IP_PATH_FILE = IP+PATH_FILE;
    private static final String IP_PATH_FILE_IMAGE = IP+PATH_FILE_IMAGE;

    /*
    //File Php terkait user
    private static final String USER_LOGIN = "UserLogin.php";

    //File Php terkait group local
    private static final String AVAIL_GROUP = "getDataGroupData.php";
    private static final String DRAFT_GROUP = "getDataGroupDraft.php";
    private static final String REJECTED_GROUP = "getDataGroupRejected.php";
    private static final String SEND_TO_DRAFT = "sendToDraft.php";
    private static final String SEARCH_GROUPTYPE = "getDataGroupType.php";
    private static final String SEARCH_PROVINCE = "getDataProvince.php";
    private static final String SEARCH_CITY = "getDataCity.php";
    private static final String SEARCH_DISTRIK = "getDataDistrik.php";
    private static final String SEARCH_DISTRIK_AREA = "getDataDistrikArea.php";
    private static final String GET_DETAIL_DATA_GROUP = "getDataGroupFromID.php";
    private static final String SEND_DATA_GROUP = "saveDataGroup.php";
    private static final String EDIT_DATA_GROUP = "editDataGroup.php";
    private static final String UPLOAD_IMAGE_GROUP = "uploadImage.php";
    private static final String GET_QUOTA_REGISTER = "getQuotaRegister.php";
    private static final String GET_QUOTA_RESERVATION = "getQuotaReservation.php";
    */


    //File Php terkait user
    private static final String USER_LOGIN = "UserLogin0x4r1k.php";

    //File Php terkait group public
    private static final String AVAIL_GROUP = "getDataGroupAvail0x4r1k.php";
    private static final String DATA_GROUP = "getDataGroupData0x4r1k.php";
    private static final String DRAFT_GROUP = "getDataGroupDraft0x4r1k.php";
    private static final String DRAFT_ADMIN = "getDataDraftAdmin0x4r1k.php";
    private static final String OWNER_GROUP = "getDataGroupOwner0x4r1k.php";
    private static final String PERSONAL_GROUP = "getDataGroupPersonal0x4r1k.php";
    private static final String SEND_TO_DRAFT = "sendToDraft0x4r1k.php";
    private static final String SEND_TO_DATA = "sendPostingGroup0x4r1k.php";
    private static final String SEARCH_GROUPTYPE = "getDataGroupType0x4r1k.php";
    private static final String SEARCH_PROVINCE = "getDataProvince0x4r1k.php";
    private static final String SEARCH_CITY = "getDataCity0x4r1k.php";
    private static final String SEARCH_DISTRIK = "getDataDistrik0x4r1k.php";
    private static final String SEARCH_DISTRIK_AREA = "getDataDistrikArea0x4r1k.php";
    private static final String GET_DETAIL_DATA_GROUP = "getDataGroupFromID0x4r1k.php";
    private static final String SEND_DATA_GROUP = "saveDataGroup0x4r1k.php";
    private static final String EDIT_DATA_GROUP = "editDataGroup0x4r1k.php";
    private static final String EDIT_DATA_GROUP_ADMIN = "editDataGroupAdmin0x4r1k.php";
    private static final String UPLOAD_IMAGE_GROUP = "uploadImage0x4r1k.php";
    private static final String GET_QUOTA_REGISTER = "getQuotaRegister0x4r1k.php";
    private static final String GET_QUOTA_RESERVATION = "getQuotaReservation0x4r1k.php";
    private static final String GET_ALL_QUOTA = "getAllQuota0x4r1k.php";
    private static final String GET_POPULASI_FAMILY1 = "getPopulasiFamily10x4r1k.php";
    private static final String GET_POPULASI_FAMILY2 = "getPopulasiFamily20x4r1k.php";
    private static final String GET_POPULASI_GROUP = "getPopulasiGroup0x4r1k.php";
    private static final String GET_POPULASI_PARTY = "getPopulasiParty0x4r1k.php";
    private static final String GET_DATA_BOOKING = "getDataBooking0x4r1k.php";
    private static final String GET_DATA_BOOKING_ADMIN = "getDataBookingAdmin0x4r1k.php";
    private static final String GET_DATA_RESERVATION = "getDataReservation0x4r1k.php";
    private static final String GET_DATA_RESERVATION_ADMIN = "getDataReservationAdmin0x4r1k.php";
    private static final String GET_DATA_SUPPORTER = "getDataSupporter0x4r1k.php";
    private static final String GET_DATA_TICKET_PACK = "getDataTicketPack0x4r1k.php";
    private static final String GET_DATA_HARGA_TICKET_PACK = "getDataHargaTicket0x4r1k.php";
    private static final String GET_DATA_SPECIAL_QUOTA = "getSpecialQuota0x4r1k.php";
    private static final String SEND_DATA_BOOKING = "SaveBooking0x4r1k.php";
    private static final String EDIT_DATE_BOOKING = "editChangeDateBooking0x4r1k.php";
    private static final String EDIT_CANCEL_BOOKING = "editCancelBooking0x4r1k.php";
    private static final String EDIT_CANCEL_RESERVATION = "editCancelReservation0x4r1k.php";
    private static final String GET_DATA_PROMOTOR = "getDataPromotor0x4r1k.php";
    private static final String GET_DATA_RESPONSIBLE = "getDataResponsible0x4r1k.php";
    private static final String GET_DATA_RESERVATION_ID = "getDataReservationID0x4r1k.php";
    private static final String SEND_DATA_RESPONSIBLE = "sendDataResponsible0x4r1k.php";
    private static final String SEND_DATA_PROMOTOR = "sendDataPromotor0x4r1k.php";
    private static final String GET_DATA_PACKAGE = "getDataPackage0x4r1k.php";
    private static final String GET_DATA_HARGA_PACKAGE = "getDataHargaPackage0x4r1k.php";
    private static final String GET_DATA_PACKAGE_RESERV = "getDataPackageReservation0x4r1k.php";
    private static final String DELETE_PACKAGE_RESV = "deletePackageResv0x4r1k.php";
    private static final String SEND_PACKAGE_RESV = "sendDataPackageResv0x4r1k.php";
    private static final String SEND_RESERVATION = "sendReservation0x4r1k.php";
    private static final String GET_DATA_PERSONAL_ID = "getDataPersonalID0x4r1k.php";
    private static final String GET_DATA_HISTORY = "getDataHistoryReservation0x4r1k.php";
    private static final String SEND_DATA_PIC = "saveDataPIC0x4r1k.php";

    //Url terkait user
    public static final String URL_USER_LOGIN = IP_PATH_FILE + USER_LOGIN;

    //Url terkait Group
    public static final String URL_GROUP_AVAIL = IP_PATH_FILE + AVAIL_GROUP;
    public static final String URL_GROUP_DATA = IP_PATH_FILE + DATA_GROUP;
    public static final String URL_SEND_TO_DRAFT = IP_PATH_FILE + SEND_TO_DRAFT;
    public static final String URL_SEND_TO_DATA = IP_PATH_FILE + SEND_TO_DATA;
    public static final String URL_GROUP_DRAFT = IP_PATH_FILE + DRAFT_GROUP;
    public static final String URL_GROUP_DRAFT_ADMIN = IP_PATH_FILE + DRAFT_ADMIN;
    public static final String URL_GROUP_OWNER = IP_PATH_FILE + OWNER_GROUP;
    public static final String URL_GROUP_PERSONAL = IP_PATH_FILE + PERSONAL_GROUP;
    public static final String URL_SEARCH_GROUPTYPE = IP_PATH_FILE + SEARCH_GROUPTYPE;
    public static final String URL_SEARCH_PROVINCE = IP_PATH_FILE + SEARCH_PROVINCE;
    public static final String URL_SEARCH_CITY = IP_PATH_FILE + SEARCH_CITY;
    public static final String URL_SEARCH_DISTRIK = IP_PATH_FILE + SEARCH_DISTRIK;
    public static final String URL_SEARCH_DISTRIK_AREA = IP_PATH_FILE + SEARCH_DISTRIK_AREA;
    public static final String URL_DETAIL_DATA_GROUP = IP_PATH_FILE + GET_DETAIL_DATA_GROUP;
    public static final String URL_SEND_DATA_GROUP = IP_PATH_FILE + SEND_DATA_GROUP;
    public static final String URL_EDIT_DATA_GROUP = IP_PATH_FILE + EDIT_DATA_GROUP;
    public static final String URL_EDIT_DATA_GROUP_ADMIN = IP_PATH_FILE + EDIT_DATA_GROUP_ADMIN;
    public static final String URL_UPLOAD_IMAGE_GROUP = IP_PATH_FILE_IMAGE + UPLOAD_IMAGE_GROUP;
    public static final String URL_PATH_IMAGE_GROUP = IP_PATH_FILE_IMAGE + "uploads/";
    public static final String URL_GET_QUOTA_REGISTER = IP_PATH_FILE + GET_QUOTA_REGISTER;
    public static final String URL_GET_QUOTA_RESERVATION = IP_PATH_FILE + GET_QUOTA_RESERVATION;
    public static final String URL_GET_ALL_QUOTA = IP_PATH_FILE + GET_ALL_QUOTA;
    public static final String URL_GET_POPULASI_FAMILY1 = IP_PATH_FILE + GET_POPULASI_FAMILY1;
    public static final String URL_GET_POPULASI_FAMILY2 = IP_PATH_FILE + GET_POPULASI_FAMILY2;
    public static final String URL_GET_POPULASI_GROUP = IP_PATH_FILE + GET_POPULASI_GROUP;
    public static final String URL_GET_POPULASI_PARTY = IP_PATH_FILE + GET_POPULASI_PARTY;
    public static final String URL_GET_DATA_BOOKING = IP_PATH_FILE + GET_DATA_BOOKING;
    public static final String URL_GET_DATA_BOOKING_ADMIN = IP_PATH_FILE + GET_DATA_BOOKING_ADMIN;
    public static final String URL_GET_DATA_RESERVATION = IP_PATH_FILE + GET_DATA_RESERVATION;
    public static final String URL_GET_DATA_RESERVATION_ADMIN = IP_PATH_FILE + GET_DATA_RESERVATION_ADMIN;
    public static final String URL_GET_DATA_SUPPORTER = IP_PATH_FILE + GET_DATA_SUPPORTER;
    public static final String URL_GET_DATA_TICKET_PACK = IP_PATH_FILE + GET_DATA_TICKET_PACK;
    public static final String URL_GET_DATA_HARGA_TICKET_PACK = IP_PATH_FILE + GET_DATA_HARGA_TICKET_PACK;
    public static final String URL_GET_DATA_SPECIAL_QUOTA = IP_PATH_FILE + GET_DATA_SPECIAL_QUOTA;
    public static final String URL_SEND_DATA_BOOKING = IP_PATH_FILE + SEND_DATA_BOOKING;
    public static final String URL_EDIT_DATE_BOOKING = IP_PATH_FILE + EDIT_DATE_BOOKING;
    public static final String URL_EDIT_CANCEL_BOOKING = IP_PATH_FILE + EDIT_CANCEL_BOOKING;
    public static final String URL_EDIT_CANCEL_RESERVATION = IP_PATH_FILE + EDIT_CANCEL_RESERVATION;
    public static final String URL_GET_DATA_PROMOTOR = IP_PATH_FILE + GET_DATA_PROMOTOR;
    public static final String URL_GET_DATA_RESPONSIBLE = IP_PATH_FILE + GET_DATA_RESPONSIBLE;
    public static final String URL_GET_DATA_RESERVATION_ID = IP_PATH_FILE + GET_DATA_RESERVATION_ID;
    public static final String URL_SEND_DATA_RESPONSIBLE = IP_PATH_FILE + SEND_DATA_RESPONSIBLE;
    public static final String URL_SEND_DATA_PROMOTOR = IP_PATH_FILE + SEND_DATA_PROMOTOR;
    public static final String URL_GET_DATA_PACKAGE = IP_PATH_FILE + GET_DATA_PACKAGE;
    public static final String URL_GET_DATA_HARGA_PACKAGE = IP_PATH_FILE + GET_DATA_HARGA_PACKAGE;
    public static final String URL_GET_DATA_PACKAGE_RESERV = IP_PATH_FILE + GET_DATA_PACKAGE_RESERV;
    public static final String URL_DELETE_PACKAGE_RESV = IP_PATH_FILE + DELETE_PACKAGE_RESV;
    public static final String URL_SEND_PACKAGE_RESV = IP_PATH_FILE + SEND_PACKAGE_RESV;
    public static final String URL_SEND_RESERVATION = IP_PATH_FILE + SEND_RESERVATION;
    public static final String URL_GET_DATA_PERSONAL_ID = IP_PATH_FILE + GET_DATA_PERSONAL_ID;
    public static final String URL_GET_DATA_HISTORY = IP_PATH_FILE + GET_DATA_HISTORY;
    public static final String URL_SEND_DATA_PIC = IP_PATH_FILE + SEND_DATA_PIC;
}
