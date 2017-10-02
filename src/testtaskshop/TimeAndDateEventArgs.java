package testtaskshop;

/**
 *
 * @author Nadina The class for the event object.
 */
public class TimeAndDateEventArgs {

    int hour = 0;
    int day = 1;
    int month = 1;

    public int getHour() {
        return this.hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

}
