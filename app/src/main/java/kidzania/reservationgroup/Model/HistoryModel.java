package kidzania.reservationgroup.Model;

public class HistoryModel {

    private String dateHist; //1
    private String userHist; //2
    private String noteHist; //3

    public String getDataHist() {
        return dateHist;
    }
    public void setDateHist(String xdateHist) {dateHist = xdateHist;}

    public String getUserHist() {
        return userHist;
    }
    public void setUserHist(String xuserHist) {userHist = xuserHist;}

    public String getNoteHist() {
        return noteHist;
    }
    public void setNoteHist(String xnoteHist) {noteHist = xnoteHist;}
}
