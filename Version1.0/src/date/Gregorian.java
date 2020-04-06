package date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

final public class Gregorian extends Base{

    protected static final short[] maxDayOfMonth;
    protected static final short[] maxDayOfMonthLeapYear;
    private static String[] monthNames = {
            "JANUARY", "FEBRUARY", "MARCH", "APRIL",
            "MAY", "JUNE", "JULY", "AUGUST",
            "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
    final static String[] dayNames = {
            "Sunday", "Monday", "Tuesday",
            "Wednesday", "Thursday", "Friday",
            "Saturday"};

    static
    {
        maxDayOfMonth = new short[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        maxDayOfMonthLeapYear = new short[]{31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    }

    public Gregorian(){this(getCurrentYear(), getCurrentMonth(), getCurrentDay());}
    public Gregorian(Persian date){this(convertFromPersian(date));}
    public Gregorian(Gregorian date){this(date.getYear(), date.getMonth(), date.getDay(), date.canFuture());}
    public Gregorian(int year, int month, int day){this(year, month, day, false);}
    public Gregorian(int year, int month, int day, boolean futureCondition) {
        super(year, month, day, futureCondition);
    }

    private static int getCurrent(SimpleDateFormat input){
        Date date = new Date();
        return Integer.parseInt(input.format(date));
    }
    public static int getCurrentYear(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        return getCurrent(formatter);
    }
    public static int getCurrentMonth(){
        SimpleDateFormat formatter = new SimpleDateFormat("MM");
        return getCurrent(formatter);
    }
    public static int getCurrentDay(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd");
        return getCurrent(formatter);
    }

    @Override
    final protected String getMonth(int month) {return monthNames[month];}
    @Override
    protected String getDayWeek() {return new SimpleDateFormat("EEEEE").format(this.convertToDate());}
    @Override
    protected final int getMaxDay(){return isLeapYear() ? maxDayOfMonthLeapYear[getMonth()-1] : maxDayOfMonth[getMonth()-1];}
    @Override
    final String getType() {return "Gregorian: ";}

    static boolean isLeapYear(final Gregorian date){return isLeapYear(date.getYear());}
    public static boolean isLeapYear(final int year){
        if (year % 400 == 0)
            return true;
        if (year % 100 == 0)
            return false;
        return year % 4 == 0;
    }
    public boolean isLeapYear(){return isLeapYear(getYear());}
    private static boolean isValidDay(final int year, final int month, final int day){
        return new Gregorian(year, month, 1).isValidMonthDay(day);
    }

    private static int countLeapYears(final Gregorian start, final Gregorian end){
        int result = 0;
        for (int i = start.getYear(); i <= end.getYear(); i++)
            if (isLeapYear(i))
                result++;
        return result;
    }
    private static int countLeapYears(final Gregorian end){
        int result = 0;
        for (int i = 0; i <= end.getYear(); i++)
            if (isLeapYear(i))
                result++;
        return result;
    }
    private static int countLeapYears(final int start, final int end){
        int result = 0;
        for (int i = start; i <= end; i++)
            if (isLeapYear(i))
                result++;
        return result;
    }

    final public long countDays(){
        int year = this.getYear();
        int leapYears = countLeapYears(this);
        int regularYears = year - leapYears;
        if (isLeapYear(year)) {leapYears--;}
        else {regularYears--;}
        long result = regularYears * 365 + leapYears * 366;
        result += this.countDaysOfYear();
        return result;
    }
    final public long countDays(int years, int month, int day){
        long days = day;
        int thisMonth = this.getMonth();
        boolean leapYear = isLeapYear(this.getYear());
        for (int i = thisMonth - 1; i < thisMonth + month - 1 && leapYear; i++)
            days += maxDayOfMonthLeapYear[i];
        for (int i = thisMonth - 1; i < thisMonth + month - 1 && !leapYear; i++)
            days += maxDayOfMonth[i];
        int leapYears = countLeapYears(this.getYear() + 1, this.getYear() + years);
        int regularYears = years - leapYears;
        days += leapYears * 366 + regularYears * 365;
        return days;
    }
    final public int countDaysOfYear(){
        int result = 0;
        boolean leap = isLeapYear(this);
        for (int i = 0; i < getMonth() - 1 && leap; i++)
            result += maxDayOfMonthLeapYear[i];
        for (int i = 0; i < getMonth() - 1 && !leap; i++)
            result += maxDayOfMonthLeapYear[i];
        result += this.getDay();
        return result;
    }

    @Override
    final public void minusOne(){
        boolean leapYear = isLeapYear(this.getYear());
        if (!setDay(this.getDay() - 1))
            if (leapYear) {
                if (!setMonth(this.getMonth() - 1) || !setDay(maxDayOfMonthLeapYear[this.getMonth() - 1]))
                    if (!setYear(this.getYear() - 1) || !setMonth(12) || !setDay(31))
                        System.out.println("Something bad happened in minusOne method");
            } else {
                if (!setMonth(this.getMonth() - 1) || !setDay(maxDayOfMonthLeapYear[this.getMonth() - 1]))
                    if (!setYear(this.getYear() - 1) || !setMonth(12) || !setDay(31))
                        System.out.println("Something bad happened in minusOne method");
            }
    }

    @Override
    final public void nextMonth(){
        int day = this.getDay();
        int month = this.getMonth();
        int year = this.getYear();
        if ((month == 3 || month == 5 || month == 8 || month == 10) && (day == 31)) day = 30;
        else if (month == 1){
            if (isLeapYear(year) && day > 29){day = 29;}
            if (!isLeapYear(year) && day > 28){day = 28;}
        } else if (month == 2){
            if (isLeapYear(year) && day == 29){day = 31;}
            if (!isLeapYear(year) && day == 28){day = 31;}
        } else {
            if (day == 30) day = 31;
        }
        do {this.nextDay();} while (this.getDay() != day || this.getMonth() == month);
    }
    @Override
    final public void prevMonth(){
        int day = this.getDay();
        int month = this.getMonth();
        int year = this.getYear();
        if ((month == 12 || month == 10 || month == 7 || month == 5) && (day == 31)) day = 30;
        else if (month == 3) {
            if (isLeapYear(year) && day > 29){day = 29;}
            if (!isLeapYear(year) && day > 28){day = 28;}
        } else if (month == 2){
            if (isLeapYear(year) && day == 29){day = 31;}
            if (!isLeapYear(year) && day == 28){day = 31;}
        } else {
            if (day == 30) day = 31;
        }
        do {this.prevDay();} while (this.getDay() != day || this.getMonth() == month);
    }

    public static Gregorian convertFromPersian(final Persian date){
        int jy = date.getYear();
        int jm = date.getMonth();
        int jd = date.getDay();
        int gy;
        if(jy>979){
            gy=1600;
            jy-=979;
        }else{
            gy=621;
        }
        int days = (365 * jy) + (jy / 33 * 8) + ((jy % 33) + 3) / 4 + 78 + jd + ((jm < 7)?(jm - 1) * 31:((jm - 7) * 30) + 186);
        gy += 400 * (days / 146097);
        days %= 146097;
        if(days > 36524){
            gy += 100 * (--days / 36524);
            days %= 36524;
            if (days >= 365)days++;
        }
        gy += 4 * (days / 1461);
        days %= 1461;
        if(days > 365){
            gy += ((days - 1) / 365);
            days = (days - 1) % 365;
        }
        int gd = days + 1;
        int[] sal_a = {0,31,((gy % 4 == 0 && gy % 100 != 0) || (gy % 400 == 0))?29:28,31,30,31,30,31,31,30,31,30,31};
        int gm;
        for(gm = 0;gm < 13;gm++){
            int v = sal_a[gm];
            if(gd <= v)break;
            gd -= v;
        }
        return new Gregorian(gy, gm, gd);
    }
    @Override
    public Calendar convertToCalendar() {
        Calendar result = Calendar.getInstance();
        result.set(this.getYear(), this.getMonth(), this.getDay());
        return result;
    }
    @Override
    public Date convertToDate() {return new Date(this.getYear() - 1900, this.getMonth() - 1, this.getDay());}
    // difference
    // Constructor Month in (FEB or February)
    // Documentation
    // Logger in file
    // Full Exception Handling

    // as Extra remember that create abstract class Date <- for dynamic programming Hijri, Gregorian, ...
}

