/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testtaskshop;

/**
 * @author Nadina
 */
public class Time extends Thread {

    private static final int INTERVAL_SEC_ONE_DAY = 100;
    private static final int START_OF_WORKING_DAY = 8;
    private static final int END_OF_WORKING_DAY = 21;
    private static final int START_OF_EVENING_PERIOD = 18;
    private static final int END_OF_EVENING_PERIOD = 20;
    private static final int AFTER_PURCHASE = 22;
    private final static int TWENTY_FOUR_HOURS = 24;
    private final static int MONTH = 30;

    private TimeAndDateListener tdl;

    boolean isWeekend = false;

    TimeAndDateEventArgs arg;

    private boolean first_day = true;
    

    @Override
    public void run() {
        try {
            arg = new TimeAndDateEventArgs();
            while (true) {

                if (arg.getDay() == 1 && first_day) {
                    tdl.nextDay(arg.getDay(), isWeekend);
                    first_day = false;
                }
            
                Thread.sleep(INTERVAL_SEC_ONE_DAY);

                one_hour();
                weekend(tdl);
                one_day(tdl);
                one_month(tdl);
                working_day(tdl);
                evening_period(tdl);
                showHour();
            }
        } catch (InterruptedException ie) {
            System.err.println(ie.getMessage());
        }
    }

    private void one_hour() {
        int oldHour = arg.getHour();
        oldHour++;
        arg.setHour(oldHour);
    }

    private void one_day(TimeAndDateListener tdl) {
        if (arg.getHour() % TWENTY_FOUR_HOURS == 0) {
            arg.setHour(0);
            int day = arg.getDay();
            day++;
            arg.setDay(day);
            if (isWeekend) {
                isWeekend = false;
                System.out.println("End of weekend");
            }
            tdl.nextDay(arg.getDay(), isWeekend);
        } else if (arg.getHour() == AFTER_PURCHASE) {
            tdl.after_purchase(arg.getHour());
        }
    }

    private void one_month(TimeAndDateListener tdl) {
        if (arg.getDay() % MONTH == 0) {
            arg.setDay(1);
            int month = arg.getMonth();
            month++;
            arg.setMonth(month);
            tdl.nextMonth(arg.getMonth());
        }
    }

    private void weekend(TimeAndDateListener tdl) {
        if (arg.getDay() % 7 == 0) {
            tdl.weekend(arg.getDay());
            isWeekend = true;
        } else {
            isWeekend = false;
        }
    }

    private void working_day(TimeAndDateListener tdl) {
        if (arg.getHour() >= START_OF_WORKING_DAY && arg.getHour() <= END_OF_WORKING_DAY) {
            tdl.working_day(arg.getHour());
        }
    }

    private void evening_period(TimeAndDateListener tdl) {
        if (arg.getHour() == START_OF_EVENING_PERIOD && !isWeekend) {
            tdl.evening_period(true, arg.getHour());
        } else if (arg.getHour() == END_OF_EVENING_PERIOD && !isWeekend) {
            tdl.evening_period(false, arg.getHour());
        }
    }

    private void showHour() {
        System.out.println("Hour: " + arg.getHour() + ":" + "00");
    }

    public void addTimeAndDateListener(TimeAndDateListener tdl) {
        this.tdl = tdl;
    }

}
