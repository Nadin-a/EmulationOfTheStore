package testtaskshop;


/**
 *
 * @author Nadina
 * Interface for "Event Recipient"
 * убрать инты h
 */
public interface TimeAndDateListener {
    void working_day();
    void evening_period(boolean isEvening);
    void nextDay(int day, boolean isWeekend);
    void nextMonth(int arg);
    void weekend(int hour);
    void after_purchase(int hour);
}
