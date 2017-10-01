/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testtaskshop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 *
 * @author Nadina
 */
public class Shop implements TimeAndDateListener {

    private static final int STANDART_MARKUP = 10;

    private static final int EVENING_MARKUP = 8;

    private static final int WEEKEND_MARKUP = 15;

    private static final int TWO_ITEMS_MARKUP = 7;

    private static final int MINIMUM_QUANTITY_OF_GOODS = 10;

    private static final int PURCHASE = 150;

    private List<Drink> empList;

    private double money;

    private double profit;
    private double expenses;

    public Shop() {
        this.money = 1000;
        this.profit = 0;
        this.expenses = 0;
    }

    public List<Drink> getEmpList() {
        return empList;
    }

    public void setEmpList(List<Drink> empList) {
        this.empList = empList;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getExpenses() {
        return expenses;
    }

    public void setExpenses(double expenses) {
        this.expenses = expenses;
    }

    @Override
    public void after_purchase(int arg) {
        System.out.println("End of Day " + arg);
        empList.forEach((d) -> {
            after_sale_of_goods(d);
        });
    }

    @Override
    public void weekend(int arg) {
        mark_up_for_each_drink(WEEKEND_MARKUP);
    }

    @Override
    public void working_day(int arg) {

        int number_of_customers = 1 + (int) (Math.random() * 10);
        System.out.println("Пришло " + number_of_customers + " покупателей.");
        for (int i = 1; i <= number_of_customers; i++) {
            System.out.println("Покупатель " + i);
            buying_goods();
        }

        if (arg == 8) {
            System.out.println("working_day " + arg);
        } else if (arg == 21) {
            System.out.println("end_ofworking_day " + arg);
        }

    }

    private void buying_goods() {
        int rand = 1 + (int) (Math.random() * 10);
        if (rand == 10) {
            System.out.println("Ничего не купил");
            return;
        }
        int count_of_goods = 1 + (int) (Math.random() * 10);
        System.out.println("Этот покупатель купил " + count_of_goods + " товаров.");
        int[] indices_of_goods = new int[count_of_goods];
        for (int j = 0; j < count_of_goods; j++) {
            buy_one_item(indices_of_goods, j);
        }
        sales_at_a_time(indices_of_goods);
    }

    private void buy_one_item(int[] indices_of_goods, int j) {

        int what_item = find_item();
        indices_of_goods[j] = what_item;

        int count = empList.get(what_item).getCount();
        count--;
        empList.get(what_item).setCount(count);

        int how_much_is_sold = empList.get(what_item).getHow_much_is_sold();
        how_much_is_sold++;
        empList.get(what_item).setHow_much_is_sold(how_much_is_sold);

        calculate_profit(what_item);

        System.out.println(" Продано: " + empList.get(what_item).getName()
                + " по цене: "
                + String.valueOf(new BigDecimal(empList.get(what_item).getCost_with_markup())
                        .setScale(1, RoundingMode.UP))
                + " с наценкой " + empList.get(what_item).getCurrent_markup()
                + " и осталось: " + empList.get(what_item).getCount());
    }

    private void calculate_profit(int what_item) {
        this.money += empList.get(what_item).getCost_with_markup();
        double mProfit = empList.get(what_item).getCost_with_markup()
                - empList.get(what_item).getStandart_cost();
        this.profit += mProfit;
    }

    private void sales_at_a_time(int[] indices_of_goods) {
        boolean mask[] = new boolean[indices_of_goods.length];
        for (int i = 0; i < indices_of_goods.length; i++) {
            if (!mask[i]) {
                int tmp = indices_of_goods[i];

                for (int j = i + 1; j < indices_of_goods.length; j++) {
                    if (tmp == indices_of_goods[j]) {
                        mask[j] = true;
                        empList.get(indices_of_goods[i]).mark_up(TWO_ITEMS_MARKUP);
                    }
                }
            }
        }
    }

    private int find_item() {
        try {
            int what_item = 0 + (int) (Math.random() * empList.size());
            if (empList.get(what_item).getCount() == 0) {
                return find_item();
            } else {
                return what_item;
            }
        } catch (StackOverflowError err) {
            empList.forEach((d) -> {
                System.out.println("На складе кончились продукты, срочная дозакупка");
                after_sale_of_goods(d);
            });

        }
        return 0;
    }

    @Override
    public void evening_period(boolean isEvening, int arg) {
        if (isEvening) {
            mark_up_for_each_drink(EVENING_MARKUP);
            System.out.println("start_of_evening_period " + arg);
        } else {
            mark_up_for_each_drink(STANDART_MARKUP);
            System.out.println("end_of_evening_period " + arg);
        }
    }

    @Override
    public void nextDay(int day, boolean isWeekend) {
        System.out.println(this.toString());
        if (isWeekend) {
            mark_up_for_each_drink(WEEKEND_MARKUP);
        } else {
            mark_up_for_each_drink(STANDART_MARKUP);
        }
        System.out.println("!!!!!!!!!!!!!!!!!!!!!Shop Day: " + day);

    }

    @Override
    public void nextMonth(int arg) {
        System.out.println("!!!!!!!!!!!!!!!!!New  month " + arg);

        writeReportToFile();

        this.profit = 0;
        this.expenses = 0;

        empList.forEach((d) -> {
            d.setHow_many_times_is_bought(0);
            d.setHow_many_times_is_bought(0);
        });

    }

    private void writeReportToFile() {
        try (FileWriter writer = new FileWriter("report.txt", true);
                BufferedWriter bufferWriter = new BufferedWriter(writer)) {
            bufferWriter.write(form_report());
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }
    }

    private String form_report() {
        StringBuilder s = new StringBuilder("Отчет " + "за месяц ");

        s.append(System.lineSeparator());
        s.append("Количество проданного товара :");
        s.append(System.lineSeparator());
        empList.forEach((d) -> {
            String strSold = d.getName() + ": " + d.getHow_much_is_sold();
            s.append(strSold);
            s.append(System.lineSeparator());
        });
        s.append(System.lineSeparator());
        s.append("Количество дозакупленного товара :");
        s.append(System.lineSeparator());
        empList.forEach((d) -> {
            String strBought = d.getName() + ": " + d.getHow_many_times_is_bought();
            s.append(strBought);
            s.append(System.lineSeparator());
        });

        s.append(System.lineSeparator());
        String strProfit = " Прибыль магазина от продаж за месяц: "
                + String.valueOf(new BigDecimal(this.profit)
                        .setScale(1, RoundingMode.UP));
        s.append(strProfit);
        s.append(System.lineSeparator());
        String strExpenses = "  Затраченные средства на дозакупку товара за месяц: "
                + String.valueOf(new BigDecimal(this.expenses)
                        .setScale(1, RoundingMode.UP));
        s.append(strExpenses);
        s.append(System.lineSeparator());
        return s.toString();
    }

    private void mark_up_for_each_drink(int markup) {
        empList.forEach((d) -> {
            d.mark_up(markup);
        });
    }

    public void after_sale_of_goods(Drink drink) {
        if (drink.getCount() < MINIMUM_QUANTITY_OF_GOODS) {
            System.out.println("Денег сейчас " + this.money);
            int count = drink.getCount();
            count += PURCHASE;
            drink.setCount(count);
            int how_many_times_is_bought = drink.getHow_many_times_is_bought();
            how_many_times_is_bought += PURCHASE;
            drink.setHow_many_times_is_bought(how_many_times_is_bought);

            double exOne = drink.getStandart_cost() * PURCHASE;
            this.money -= exOne;
            this.expenses += exOne;

            System.out.println(" Закупил товар " + drink.getName() + " на сумму "
                    + String.valueOf(new BigDecimal(exOne)
                            .setScale(1, RoundingMode.UP)) + " денег осталось "
                    + String.valueOf(new BigDecimal(this.money)
                            .setScale(1, RoundingMode.UP)));
        }
    }

    @Override
    public String toString() {
        return "Shop{" + "empList=" + empList + ", money=" + money + ", profit=" + profit + ", expenses=" + expenses + '}';
    }

}
