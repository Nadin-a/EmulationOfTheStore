/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testtaskshop;

import java.io.IOException;

/**
 *Интерфейс для "Получатель события"
 * @author Nadina
 * убрать инты h
 */
public interface TimeAndDateListener {
    void working_day(int h);
    void evening_period(boolean isEvening, int h);
    void nextDay(int day, boolean isWeekend);
    void nextMonth(int h);
    void weekend(int h);
    void after_purchase(int h);
}
