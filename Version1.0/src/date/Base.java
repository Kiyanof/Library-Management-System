package date;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * <h1>Class Information:</h1>
 * ----- <h2>Creator:</h2> -----
 * --- @author kiyanof
 * --- @email admin@mohammadkiyan.ir
 * --- @website mohammadkiyan.ir
 * ----- <h2>Structure:</h2> -----
 * --- Name: Base
 * --- Access Modifier: Default
 * --- Abstract: YES
 * --- Package: date
 * --- @version 1.0
 */
abstract class Base {
    /**
     * <h1>Class Attributes:</h1>
     * <h2>----- Public -----</h2>
     *
     * <h2>----- Protected -----</h2>
     * <ul>
     *      <li>year: int</li>
     *      <pre>This field store year value within.</pre>
     *      <li>month: int</li>
     *      <pre>This field store month value within.</pre>
     *      <li>day: int</li>
     *      <pre>This field store day value within.</pre>
     * </ul>
     * <h2>----- Default -----</h2>
     *
     * <h2>----- Private -----</h2>
     * <ul>
     *     <li>futureCondition: boolean</li>
     *     <pre>
     *          This field store condition for date validation.
     *          If futureCondition == true --> so you must enter date greater than <i>current system date</i>.
     *          If futureCondition == false --> so you must enter date lesser than <i>current system date</i>.
     *     </pre>
     * </ul>
     */

    protected int year;
    protected int month;
    protected int day;

    private boolean futureCondition;

    /**
     * <h1>Class Constructors:</h1>
     * <pre>
     *     This class has two constructor.
     * </pre>
     */
    /**
     *<h2>----- Constructor 1 -----</h2>
     * <pre>
     *     This class send their parameters to constructor 2. so i describe parameters and working style there.
     *     But the important thing here is, this constructor set futureCondition attributes false by self.
     * </pre>
     * @param year
     * @param month
     * @param day
     */

    public Base(int year, int month, int day){this(year, month, day, false);}

    /**
     * <h2>----- Constructor 2 -----</h2>
     * <pre>
     *     As you see below, this constructor get year, month, day from user and when he or she wants to
     *     put date greater than current machine time, must set futureCondition true else must set false.
     * </pre>
     * @param year
     * @param month
     * @param day
     * @param futureCondition
     */
    public Base(int year, int month, int day, boolean futureCondition) {
        /**
         * <h2>----- Logger -----</h2>
         * <h3>--- Logs: ---</h3>
         * <pre>
         *     <ui>
         *         <li>running message</li>
         *         <li>Setters process success</li>
         *         <li>
         *             <ui>
         *                 <li>setFutureCondition</li>
         *                 <li>setYear</li>
         *                 <li>setMonth</li>
         *                 <li>setDay</li>
         *             </ui>
         *         </li>
         *         <li>Constructor process success</li>
         *     </ui>
         * </pre>
         */
        System.out.println(this.getClass() + " Constructor is running...");
        System.out.println("Set future condition: " + this.setFutureCondition(futureCondition));
        System.out.println("Set year: " + this.setYear(year));
        System.out.println("Set month: " + this.setMonth(month));
        System.out.println("Set day: " + this.setDay(day));
        System.out.println("Done.");
    }

    /**
     * <h1>Setters: </h1>
     */
    /**
     * <h2>----- setYear -----</h2>
     * Access Modifier: protected --> Gregorian and Persian classes inherited it from base class!
     * Abstract: YES --> Gregorian and Persian classes have different validation process so i write it abstract here
     *                   for implementing <b>Polymorphism</b>.
     * @see Gregorian
     * @see Persian
     * @param year final int --> this is a value that must be set as year field.
     * @return boolean type --> if validation process finish successfully return true else false.
     */
    abstract protected boolean setYear(final int year);
    /**
     * <h2>----- setYear -----</h2>
     * Access Modifier: protected --> Gregorian and Persian classes inherited it from base class!
     * Abstract: YES --> Gregorian and Persian classes have different validation process so i write it abstract here
     *                   for implementing <b>Polymorphism</b>.
     * @see Gregorian
     * @see Persian
     * @param month final int --> this is a value that must be set as month field.
     * @return boolean type --> if validation process finish successfully return true else false.
     */
    abstract protected boolean setMonth(final int month);
    /**
     * <h2>----- setYear -----</h2>
     * Access Modifier: protected --> Gregorian and Persian classes inherited it from base class!
     * Abstract: YES --> Gregorian and Persian classes have different validation process so i write it abstract here
     *                   for implementing <b>Polymorphism</b>.
     * @see Gregorian
     * @see Persian
     * @param day final int --> this is a value that must be set as day field.
     * @return boolean type --> if validation process finish successfully return true else false.
     */
    abstract protected boolean setDay(final int day);
    /**
     * <h2>----- setYear -----</h2>
     * Access Modifier: protected --> Gregorian and Persian classes inherited it from base class!
     * Abstract: NO --> Both Gregorian and Persian classes have same validation process.
     * @see Gregorian
     * @see Persian
     * @param futureCondition final boolean --> this is a value that must be set as condition that clarify
     *                                          can enter future date or not.
     * @return boolean type --> if validation process finish successfully return true else false.
     */
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

    /**
     * <h2>----- getYear -----</h2>
     * Access Modifier: protected --> Gregorian and Persian classes inherited it from base class!
     * Abstract: NO --> Both Gregorian and Persian classes have same validation process.
     * final: YES --> for preventing overloading and being leaf in implementation hierarchy.
     * @return year
     */
    final protected int getYear() {return year;}
    /**
     * <h2>----- getMonth -----</h2>
     * Access Modifier: protected --> Gregorian and Persian classes inherited it from base class!
     * Abstract: NO --> Both Gregorian and Persian classes have same validation process.
     * final: YES --> for preventing overloading and being leaf in implementation hierarchy.
     * @return month
     */
    final protected int getMonth() {return month;}
    /**
     * <h2>----- getMonth -----</h2>
     * Access Modifier: protected --> Gregorian and Persian classes inherited it from base class!
     * Abstract: YES -->    Because of different names of month in Gregorian and Persian calendar and different
     *                      language too, this method is abstract for specific implementation.
     *                      another important thing is month names are static field in Gregorian and Persian class
     *                      so for accessing to them it must be write on both class. but for more accessing from another
     *                      methods such as show method (Describe it below) it's abstract here.
     * @param month final int -->   this method get month in number format and convert it to month name.
     * @return String --> the month name. ex: April, June, ...
     */
    abstract protected String getMonth(final int month);
    /**
     * <h2>----- getDayWeek -----</h2>
     * Access Modifier: protected --> Gregorian and Persian classes inherited it from base class!
     * Abstract: YES -->     Because of different names of day in Gregorian and Persian calendar and different
     *                       language too, this method is abstract for specific implementation.
     *                       another important thing is day names are static field in Gregorian and Persian class
     *                       so for accessing to them it must be write on both class. but for more accessing from another
     *                       methods such as show method (Describe it below) it's abstract here.
     * @return String --> the day name. ex: Sunday, Monday, ...
     */
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
