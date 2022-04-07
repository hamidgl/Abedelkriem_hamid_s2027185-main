package org.me.gcu.abedelkriem_hamid_s2027185;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {

    public static final String ITEM_PUB_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";//Tue, 19 Apr 2022 00:00:00 GMT
    public static final String DATE_FORMAT = "dd/MM/yyyy";//Tue, 19 Apr 2022 00:00:00 GMT

    public static Date getDate(String dateValue, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(dateValue);
        } catch ( Exception ex) {
            ex.printStackTrace();
        }
        return date;
    }

/*
    public static String format(String date) {
        return date;
    }
*/

    public static boolean isEquals(String date1, String date2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( ITEM_PUB_DATE_FORMAT );
        if ( isEmpty(date1) || isEmpty(date2) ){
            return false;
        }

        try {
            Date d1 = simpleDateFormat.parse( date1 );
            Date d2 = simpleDateFormat.parse( date2 );

            return d1.equals( d2 );

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isEmpty(String value) {
        if ( value == null ) return true;

        return value.isEmpty() ;
    }

    public static boolean isEquals(Date date1, Date date2) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(date1).equals(sdf.format(date2));
    }
}
