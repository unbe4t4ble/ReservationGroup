package kidzania.reservationgroup.Misc;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import kidzania.reservationgroup.R;

/**
 * Created by mubarik on 03/05/2017.
 */

public class VarDate {
    public static String DateReservDD; //dd/MM/yyyy
    public static String StripReservDD; //yyyy-MM-dd
    public static String DateReservMM; //MM/dd/yyyy
    public static String DateReservYYYY; //MM/dd/yyyy
    public static String DateReservMMM; //dd MMM yyyy
    public static String CombineDate;//dddd, dd mmmm yyyyy
    public static String DateRapet;
    public static String MonthName;//Nama bulan
    public static String DayName;//Nama Hari
    public static int DayInt;//integer date

    public static String NextDateReservMM; //MM/dd/yyyy
    public static String PrevDateReservMM; //MM/dd/yyyy

    public static boolean isChoiceDate = false;
    public static final String[] Minggu = {"0","7","14","21","28","35"};
    public static final String[] Senin = {"1","8","15","22","29","36"};
    public static final String[] Selasa = {"2","9","16","23","30","37"};
    public static final String[] Rabu = {"3","10","17","24","31","39"};
    public static final String[] Kamis = {"4","11","18","25","32","40"};
    public static final String[] Jumat = {"5","12","19","26","33","41"};
    public static final String[] Sabtu = {"6","13","20","27","34","42"};

    private static int firstDay, maxDay;

    public static void getNamaHari(Context context, String xPosition){
        if (StringCheck(xPosition, Minggu)){
            DayName = context.getString(R.string.str_day_sunday);
        }else
        if (StringCheck(xPosition, Senin)){
            DayName = context.getString(R.string.str_day_monday);
        }else
        if (StringCheck(xPosition, Selasa)){
            DayName = context.getString(R.string.str_day_tuesday);
        }else
        if (StringCheck(xPosition, Rabu)){
            DayName = context.getString(R.string.str_day_wednesday);
        }else
        if (StringCheck(xPosition, Kamis)){
            DayName = context.getString(R.string.str_day_thursday);
        }else
        if (StringCheck(xPosition, Jumat)){
            DayName = context.getString(R.string.str_day_friday);
        }else
        if (StringCheck(xPosition, Sabtu)){
            DayName = context.getString(R.string.str_day_saturday);
        }
    }

    public static void getNamaHariFromEnglish(Context context, String StrDay){

        if (StrDay.equals("Sunday")){
            DayName = context.getString(R.string.str_day_sunday);
        }else
        if (StrDay.equals("Monday")){
            DayName = context.getString(R.string.str_day_monday);
        }else
        if (StrDay.equals("Tuesday")){
            DayName = context.getString(R.string.str_day_tuesday);
        }else
        if (StrDay.equals("Wednesday")){
            DayName = context.getString(R.string.str_day_wednesday);
        }else
        if (StrDay.equals("Thursday")){
            DayName = context.getString(R.string.str_day_thursday);
        }else
        if (StrDay.equals("Friday")){
            DayName = context.getString(R.string.str_day_friday);
        }else
        if (StrDay.equals("Saturday")){
            DayName = context.getString(R.string.str_day_saturday);
        }

    }

    public static void getNamaBulan(Context context, String xPosition){
        String[] ArrMonth = {" ",
                context.getString(R.string.str_month_january),
                context.getString(R.string.str_month_february),
                context.getString(R.string.str_month_march),
                context.getString(R.string.str_month_april),
                context.getString(R.string.str_month_may),
                context.getString(R.string.str_month_june),
                context.getString(R.string.str_month_july),
                context.getString(R.string.str_month_august),
                context.getString(R.string.str_month_september),
                context.getString(R.string.str_month_october),
                context.getString(R.string.str_month_november),
                context.getString(R.string.str_month_december)};
        MonthName = ArrMonth[Integer.valueOf(xPosition)];
    }

    public static void setAllFormatDate(String xDay, String xMonth, String xYear){
        DateReservDD = xDay+"/"+xMonth+"/"+xYear;
        StripReservDD = xYear+"-"+xMonth+"-"+xDay;
        DateReservMM = xMonth+"/"+xDay+"/"+xYear;
        DateReservMMM = xDay+" "+MonthName+" "+xYear;
        DateReservYYYY = xYear+"-"+xMonth+"-"+xDay;
        CombineDate = DayName+", "+xDay+" "+MonthName+" "+xYear;
        DateRapet = xYear+xMonth+xDay;
    }

    private static boolean StringCheck(String xPosition, String[] xArray){
        boolean hasil = false;
        for (int i = 0; i <= xArray.length-1; i++) {
            if (xPosition.equals(xArray[i])) {
                hasil = true;
                break;
            }
        }
        return hasil;
    }

    public static void getDataNow(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateNow = new SimpleDateFormat("MM/dd/yyyy");
        NextDateReservMM = dateNow.format(c.getTime())+" 23:59:59";
        PrevDateReservMM = dateNow.format(c.getTime())+" 00:00:00";
    }
}
