package testtaskshop;


/**
 *
 * @author Nadina
 * Interface for "Event Recipient"
 * убрать инты h
 */
public interface TimeAndDateListener {
    void working_day(boolean isWeekend);
    void evening_period(boolean isEvening);
    void nextDay(int day, boolean isWeekend);
    void nextMonth(int arg);
    void weekend();
    void after_purchase(int hour);
}
