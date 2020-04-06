package date;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

abstract class Base {

    protected int year;
    protected int month;
    protected int day;

    private boolean futureCondition;

    public Base(int year, int month, int day){this(year, month, day, false);}
    public Base(int year, int month, int day, boolean futureCondition) {
        System.out.println(this.getClass() + " Constructor is running...");
        System.out.println("Set future condition: " + this.setFutureCondition(futureCondition));
        System.out.println("Set year: " + this.setYear(year));
        System.out.println("Set month: " + this.setMonth(month));
        System.out.println("Set day: " + this.setDay(day));
        System.out.println("Done.");
    }

    abstract protected boolean setYear(final int year);
    abstract protected boolean setMonth(final int month);
    abstract protected boolean setDay(final int day);
    final protected boolean setFutureCondition(final boolean futureCondition)
    {
        try {
            this.futureCondition = futureCondition;
            return true;
        } catch (Exception e){
            System.out.print("\n--- ERROR:" + e.getMessage() + "!!!");
            return false;
        }
    }

    final protected int getYear() {return year;}
    final protected int getMonth() {return month;}
    abstract protected String getMonth(final int month);
    abstract protected String getDayWeek();
    final protected int getDay() {return day;}
    abstract protected int getMaxDay();
    final protected boolean canFuture() {return futureCondition;}

    abstract String getType();

    protected boolean isValidMonthDay(int day){return day >= 1 && day <= getMaxDay();}

    protected int countMonths(){
        int year = this.getYear();
        int month = this.getMonth();
        return this.getDay() >= 15 ? year * 12 + month + 1 : year * 12 + month;
    }
    abstract protected long countDays(int years, int month, int day);
    public long countDays(int months, int day){return countDays(months / 12, months % 12, day);}

    public void addOne() {
        if (!setDay(this.getDay() + 1))
            if (!setDay(1) || !setMonth(this.getMonth() + 1))
                if (!setDay(1) || !setMonth(1) || !setYear(this.getYear() + 1))
                    System.out.println("Something bad happened in addOne method");
    }
    public void add(int years, int month, int day){
        long days = countDays(years, month, day);
        while (--days != 0)
            this.addOne();
    }
    public void add(int months, int day){this.add(months / 12, months % 12, day);}
    public void add(int days){while (--days != 0) this.addOne();}

    abstract public void minusOne();
    public void minus(int years, int month, int day){
        long days = countDays(years, month, day);
        while (--days != 0)
            this.minusOne();
    }
    public void minus(int months, int day){this.minus(months / 12, months % 12, day);}
    public void minus(int days){while (--days != 0) this.minusOne();}

    final public void nextDay(){this.addOne();}
    abstract public void nextMonth();
    final public void nextYear(){for (int i = 0; i < 12; i++) this.nextMonth();}

    final public void prevDay(){this.minusOne();}
    abstract public void prevMonth();
    final public void prevYear(){for (int i = 0; i < 12; i++) this.prevMonth();}

    public String show(final String pattern){
        /**
         * Pattern Description:
         * yyyy --> year in number. ex: 1998
         * mm --> month in number. ex: 2
         * mmm --> month in Brief Mode chars. ex: FEB
         * MMM --> month in string. ex: February
         * dd --> day of month in number. ex: 14
         * ddd --> day of week in Brief Mode chars. ex: TEU
         * DDD --> day of week in string. ex: Tuesday.
         */
        String result = pattern;
        result = result.replaceAll("yyyy", Integer.toString(this.getYear()));
        result = result.contains("mmm") ?   result.replaceAll("mmm", this.getMonth(this.getMonth()).substring(0, 3)) :
                result.replaceAll("mm", Integer.toString(this.getMonth()));
        result = result.replaceAll("MMM", this.getMonth(this.getMonth() - 1));
        result = result.contains("ddd") ?   result.replaceAll("ddd", this.getDayWeek().substring(0, 3)):
                result.replaceAll("dd", Integer.toString(this.getDay()));
        result = result.replaceAll("DDD", this.getDayWeek());
        return result;
    }

    abstract protected Calendar convertToCalendar();
    abstract protected Date convertToDate();

    @Override
    public String toString() {
        return this.getType() +
                " " +
                this.show("Year: yyyy - Month: mm - Day: dd");
    }

    final public static boolean lt(final Base arg1, final Base arg2){
        return (arg1.getYear() < arg2.getYear()) ||
                (arg1.getYear() == arg2.getYear() && arg1.getMonth() < arg2.getMonth()) ||
                (arg1.getYear() == arg2.getYear() && arg1.getMonth() == arg2.getMonth() &&
                        arg1.getDay() < arg2.getDay());
    }
    final public static boolean gt(final Base arg1, final Base arg2){return !lt(arg1, arg2) && !arg1.equals(arg2);}
    @Override
    final public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Base base = (Base) o;
        return getYear() == base.getYear() &&
                getMonth() == base.getMonth() &&
                getDay() == base.getDay();
    }

    @Override
    final public int hashCode() {
        return Objects.hash(getYear(), getMonth(), getDay());
    }
}
