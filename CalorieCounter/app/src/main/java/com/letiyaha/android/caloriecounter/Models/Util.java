package com.letiyaha.android.caloriecounter.Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    /**
     * To verify if input date follows the format.
     * @param dateToValidate
     * @param dateFromat - ex: 2018/10/01
     * @return true if dateToValidate follows the format.
     */
    public static boolean isDateValid(String dateToValidate, String dateFromat) {
        if(dateToValidate == null){
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
        sdf.setLenient(false);
        try {
            //if not valid, it will throw ParseException
            Date date = sdf.parse(dateToValidate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * To verify input string is a number.
     * @param string
     * @return true if input string is a number.
     */
    public static boolean isNumber(String string) {
        try {
            double d = Double.parseDouble(string);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

}
