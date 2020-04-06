package date;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

final public class Persian extends Base{

    protected static final short[] maxDayOfMonth = new short[]{31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 29};
    protected static final short[] maxDayOfMonthLeapYear = new short[]{31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 30};
    protected static final int[] leapYearCondition = new int[]{0, 4, 8, 12, 16, 20, 24, 29, 33, 37, 41, 45, 49,
            53, 57, 62, 66, 70, 74, 78, 82, 86, 90, 95, 99,
            103, 107, 111, 115, 119, 124};
    private static final String[] monthNames = {
            "فروردین", "اردیبهشت", "خرداد", "تیر",
            "مرداد", "شهریور", "مهر", "آبان",
            "آذر", "دی", "بهمن", "اسفند"};
    private static final String[] dayNames = {
            "یکشنبه", "دوشنبه", "سه شنبه",
            "چهارشنبه", "پنجشنبه", "جمعه",
            "شنبه"};

    public Persian(){this(getCurrentYear(), getCurrentMonth(), getCurrentDay());}
    public Persian(Gregorian date){this(Persian.convertFromGregorian(date));}
    public Persian(Persian date){this(date.getYear(), date.getMonth(), date.getDay(), date.canFuture());}
    public Persian(int year, int month, int day){this(year, month, day, false);}
    public Persian(int year, int month, int day, boolean futureCondition) {
        super(year, month, day, futureCondition);
    }

    @Override
    final protected String getMonth(int month) {return monthNames[month];}
    private int indexOfDayWeek(String dayName){
        int result = -1;
        for (int i = 0; i < 7; i++)
            if (dayName.compareTo(Gregorian.dayNames[i]) == 0){
                result = i;
                break;
            }
        return result;
    }
    @Override
    protected String getDayWeek() {return dayNames[indexOfDayWeek(new SimpleDateFormat("EEEEE").format(this.convertToDate()))];}
    @Override
    protected final int getMaxDay(){return isLeapYear() ? maxDayOfMonthLeapYear[getMonth()-1] : maxDayOfMonth[getMonth()-1];}
    @Override
    String getType() {return "Solar: ";}

    public static int getCurrentYear(){
        Gregorian currentDate = new Gregorian();
        int days = currentDate.countDaysOfYear();
        return days > 79 ? currentDate.getYear() - 621 : currentDate.getYear() - 622;
    }
    public static int getCurrentMonth(){
        Gregorian currentDate = new Gregorian();
        int result;
        int days = currentDate.countDaysOfYear();
        int farvardinDayDifference = 79;
        int deyDayDifference = Gregorian.isLeapYear(currentDate.getYear() - 1) ? 11 : 10;
        if (days > farvardinDayDifference) {
            days -= farvardinDayDifference;
            if (days <= 186) result = days % 31 == 0 ? days / 31 : days / 31 + 1;
            else {
                days -= 186;
                result = days % 30 == 0 ? days / 30 + 6 : days / 30 + 7;
            }
        } else {
            days += deyDayDifference;
            result = days % 30 == 0 ? days / 30 + 9 : days / 30 + 10;
        }
        return result;
    }
    public static int getCurrentDay(){
        Gregorian currentDate = new Gregorian();
        int result;
        int days = currentDate.countDaysOfYear();
        int farvardinDayDifference = 79;
        int deyDayDifference = Gregorian.isLeapYear(currentDate.getYear() - 1) ? 11 : 10;
        if (days > farvardinDayDifference) {
            days -= farvardinDayDifference;
            if (days <= 186) result = days % 31 == 0 ? 31 : days % 31;
            else {
                days -= 186;
                result = days % 30 == 0 ? 30 : days % 30;
            }
        } else {
            days += deyDayDifference; //
            result = days % 30 == 0 ? 30 : days % 30;
        }
        return result;
    }

    static boolean isLeapYear(final Persian date){return isLeapYear(date.getYear());}
    static boolean isLeapYear(final int year){
        return Arrays.binarySearch(leapYearCondition, year) >= 0;
    }
    public boolean isLeapYear(){return isLeapYear(getYear());}

    private static boolean isValidDay(final int year, final int month, final int day){
        return (isLeapYear(year) && day <= maxDayOfMonthLeapYear[month - 1]) ||
                (!isLeapYear(year) && day <= maxDayOfMonth[month - 1]);
    }

    private static int countLeapYears(final Persian start, final Persian end){
        int result = 0;
        for (int i = start.getYear(); i <= end.getYear(); i++)
            if (isLeapYear(i))
                result++;
        return result;
    }
    private static int countLeapYears(final Persian end){
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

    public long countDays(){
        int year = this.getYear();
        int leapYears = countLeapYears(this);
        int regularYears = year - leapYears;
        if (isLeapYear(year)) {leapYears--;}
        else {regularYears--;}
        long result = regularYears * 365 + leapYears * 366;
        result += this.countDaysOfYear();
        return result;
    }
    public int countDaysOfYear(){
        int result = 0;
        boolean leap = isLeapYear(this);
        for (int i = 0; i < getMonth() - 1 && leap; i++)
            result += maxDayOfMonthLeapYear[i];
        for (int i = 0; i < getMonth() - 1 && !leap; i++)
            result += maxDayOfMonth[i];
        result += this.getDay();
        return result;
    }
    @Override
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

    @Override
    final public void minusOne(){
        boolean leapYear = isLeapYear(this.getYear());
        if (!setDay(this.getDay() - 1))
            if (leapYear) {
                if (!setMonth(this.getMonth() - 1) || !setDay(maxDayOfMonthLeapYear[this.getMonth() - 1]))
                    if (!setYear(this.getYear() - 1) || !setMonth(12) || !setDay(30))
                        System.out.println("Something bad happened in minusOne method");
            } else {
                if (!setMonth(this.getMonth() - 1) || !setDay(maxDayOfMonth[this.getMonth() - 1]))
                    if (!setYear(this.getYear() - 1) || !setMonth(12) || !setDay(29))
                        System.out.println("Something bad happened in minusOne method");
            }
    }

    @Override
    final public void nextMonth(){
        int year = this.getYear();
        int month = this.getMonth();
        int day = this.getDay();
        if (month == 6 && day == 31) day = 30;
        else if (month == 11 && !isLeapYear(year) && day == 30) day = 29;
        else if (month == 12){
            if (isLeapYear(year) && day == 30) day = 31;
            else if (!isLeapYear(year) && day == 29) day = 31;
        }
        do {this.nextDay();} while (this.getDay() != day || this.getMonth() == month);
    }
    @Override
    final public void prevMonth() {
        int year = this.getYear();
        int month = this.getMonth();
        int day = this.getDay();
        if (month == 1) {
            if (isLeapYear(year) && day == 31) day = 30;
            else if (!isLeapYear(year) && (day == 31 || day == 30 )) day = 29;
        } else if (month == 7 && day == 30) day = 31;
        do {this.prevDay();} while (this.getDay() != day || this.getMonth() == month);
    }

    public static Persian convertFromGregorian(final Gregorian date) {
        Persian result;
        int days = date.countDaysOfYear();
        int farvardinDayDifference = 79;
        int deyDayDifference = Gregorian.isLeapYear(date.getYear() - 1) ? 11 : 10;
        if (days > farvardinDayDifference) {
            days -= farvardinDayDifference;
            if (days <= 186)
                result = days % 31 == 0 ? new Persian(date.getYear() - 621, days / 31, 31) :
                        new Persian(date.getYear() - 621, days / 31 + 1, days % 31);
            else {
                days -= 186;
                result = days % 30 == 0 ? new Persian(date.getYear() - 621, days / 30 + 6, 30) :
                        new Persian(date.getYear() - 621, days / 30 + 7, days % 30);
            }
        } else {
            days += deyDayDifference; //
            result = days % 30 == 0 ? new Persian(date.getYear() - 622, days / 30 + 9, 30) :
                    new Persian(date.getYear() - 622, days / 30 + 10, days % 30);
        }
        return result;
    }
    @Override
    protected Calendar convertToCalendar() {
        Gregorian date = new Gregorian(this);
        return date.convertToCalendar();
    }
    @Override
    protected Date convertToDate() {
        Gregorian date = new Gregorian(this);
        return date.convertToDate();
    }
}
