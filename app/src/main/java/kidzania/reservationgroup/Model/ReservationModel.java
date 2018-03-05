package kidzania.reservationgroup.Model;

/**
 * Created by mubarik on 19/12/2017.
 */

public class ReservationModel {

    private String id_num_reser; //1 ID Reservasi
    private String id_num_esc; //2 ID Group
    private String group_name; // 3 Group Name
    private String id_promotor; //4 ID Supporter
    private String promotor; //5 Promotor
    private String id_supporter; //6 ID Supporer
    private String supporter; //7 Supporer
    private String total; //9 total orang
    private String bebe; //10 Toddler
    private String senior; // 17 senior
    private String handicap; //18 handicap
    private String fecha_creacion; //19 tanggal buat
    private String ent_status; //8 status reservation
    private String grade; //9 grade
    private String alias; //11 alias
    private String prom_booking; //12 promotor
    private String sch_resp; //13 penanggung jawab
    private String total_due; //14 total bayar
    private String paid; //15 Total bayar
    private String rsv_no; //16 nol
    private String note; //20 note

    public String getid_num_reser() {
        return id_num_reser;
    }
    public void setid_num_reser(String xid_num_reser) {id_num_reser = xid_num_reser;}

    public String getid_num_esc() {
        return id_num_esc;
    }
    public void setid_num_esc(String xid_num_esc) {id_num_esc = xid_num_esc;}

    public String getgroup_name() {
        return group_name;
    }
    public void setgroup_name(String xgroup_name) {group_name = xgroup_name;}

    public String getid_promotor() {
        return id_promotor;
    }
    public void setid_promotor(String xid_promotor) {id_promotor = xid_promotor;}

    public String getpromotor() {
        return promotor;
    }
    public void setpromotor(String xpromotor) {promotor = xpromotor;}

    public String getid_supporter() {
        return id_supporter;
    }
    public void setid_supporter(String xid_supporter) {id_supporter = xid_supporter;}

    public String getsupporter() {
        return supporter;
    }
    public void setsupporter(String xsupporter) {supporter = xsupporter;}

    public String gettotal() {
        return total;
    }
    public void settotal(String xtotal) {total = xtotal;}

    public String getbebe() {
        return bebe;
    }
    public void setbebe(String xbebe) {bebe = xbebe;}

    public String getsenior() {
        return senior;
    }
    public void setsenior(String xsenior) {senior = xsenior;}

    public String gethandicap() {
        return handicap;
    }
    public void sethandicap(String xhandicap) {handicap = xhandicap;}

    public String getfecha_creacion() {
        return fecha_creacion;
    }
    public void setfecha_creacion(String xfecha_creacion) {fecha_creacion = xfecha_creacion;}

    public String getent_status() {
        return ent_status;
    }
    public void setent_status(String xent_status) {ent_status = xent_status;}

    public String getgrade() {
        return grade;
    }
    public void setgrade(String xgrade) {grade = xgrade;}

    public String getalias() {
        return alias;
    }
    public void setalias(String xalias) {alias = xalias;}

    public String getprom_booking() {
        return prom_booking;
    }
    public void setprom_booking(String xprom_booking) {prom_booking = xprom_booking;}

    public String getsch_resp() {
        return sch_resp;
    }
    public void setsch_resp(String xsch_resp) {sch_resp = xsch_resp;}

    public String gettotal_due() {
        return total_due;
    }
    public void settotal_due(String xtotal_due) {total_due = xtotal_due;}

    public String getpaid() {
        return paid;
    }
    public void setpaid(String xpaid) {paid = xpaid;}

    public String getrsv_no() {
        return rsv_no;
    }
    public void setrsv_no(String xrsv_no) {rsv_no = xrsv_no;}

    public String getnote() {
        return note;
    }
    public void setnote(String xnote) {note = xnote;}
}
