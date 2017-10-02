package testtaskshop;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import javenue.csv.Csv;

/**
 *
 * @author Nadina Class for shop.
 */
public class Store implements TimeAndDateListener {

    private static final int STANDART_MARKUP = 10;
    private static final int EVENING_MARKUP = 8;
    private static final int WEEKEND_MARKUP = 15;
    private static final int TWO_ITEMS_MARKUP = 7;

    private static final int MINIMUM_QUANTITY_OF_GOODS = 30;
    private static final int PURCHASE = 150;

    private List<Drink> drinkList;
    List<Drink> empList;

    private double money;

    private double profit;
    private double expenses;

    public Store() {
        this.money = 10000;
        this.profit = 0;
        this.expenses = 0;
        this.empList = new ArrayList<>();
    }

    public List<Drink> getDrinkList() {
        return drinkList;
    }

    public void setDrinkList(List<Drink> drinkList) {
        this.drinkList = drinkList;
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

    /**
     * The store is open from 08:00 to 21:00 every day. Every hour the store
     * receives from 1 to 10 customers.
     *
     */
    @Override
    public void working_day() {
        int number_of_customers = 1 + (int) (Math.random() * 10);
        System.out.println("Пришло " + number_of_customers + " покупателей.");
        for (int i = 1; i <= number_of_customers; i++) {
            System.out.println("Покупатель " + i);
            buying_goods();
        }
    }


    /**
     * Customers purchase from 0 to 10 units of random goods.
     */
    private void buying_goods() {
        int rand = 1 + (int) (Math.random() * 10);
        if (rand == 10) {
            System.out.println("Ничего не купил");
            return;
        }
        int amount_of_goods = 1 + (int) (Math.random() * 10);
        System.out.println("Этот покупатель купил " + amount_of_goods + " товаров.");
        int[] indices_of_goods = new int[amount_of_goods];
        for (int j = 0; j < amount_of_goods; j++) {
            buy_one_item(indices_of_goods, j);
        }
        sales_at_a_time(indices_of_goods);
    }

    /**
     * Purchase of one product.
     */
    private void buy_one_item(int[] indices_of_goods, int j) {
        int what_item = find_item();
        indices_of_goods[j] = what_item;

        int amount = drinkList.get(what_item).getAmount();
        amount--;
        drinkList.get(what_item).setAmount(amount);

        int how_much_is_sold = drinkList.get(what_item).getHow_much_is_sold();
        how_much_is_sold++;
        drinkList.get(what_item).setHow_much_is_sold(how_much_is_sold);

        calculate_profit(what_item);

        System.out.println(" Продано: " + drinkList.get(what_item).getName()
                + " по цене: "
                + String.valueOf(new BigDecimal(drinkList.get(what_item).getCost_with_markup())
                        .setScale(1, RoundingMode.UP))
                + " с наценкой " + drinkList.get(what_item).getCurrent_markup()
                + " и осталось: " + drinkList.get(what_item).getAmount());
    }

    private void calculate_profit(int what_item) {
        this.money += drinkList.get(what_item).getCost_with_markup();
        double mProfit = drinkList.get(what_item).getCost_with_markup()
                - drinkList.get(what_item).getStandart_cost();
        this.profit += mProfit;
    }

    /**
     * When selling from two units of goods at a time, the markup on the
     * following units of goods is reduced to 7% of the purchase price
     */
    private void sales_at_a_time(int[] indices_of_goods) {
        boolean mask[] = new boolean[indices_of_goods.length];
        for (int i = 0; i < indices_of_goods.length; i++) {
            if (!mask[i]) {
                int tmp = indices_of_goods[i];
                for (int j = i + 1; j < indices_of_goods.length; j++) {
                    if (tmp == indices_of_goods[j]) {
                        mask[j] = true;
                        drinkList.get(indices_of_goods[i]).mark_up(TWO_ITEMS_MARKUP);
                    }
                }
            }
        }
    }

    /**
     * Checking the availability of goods in the warehouse
     *
     * @return index of item.
     */
    private int find_item() {
        int what_item = 0 + (int) (Math.random() * drinkList.size());
        if (drinkList.get(what_item).getAmount() == 0) {
            return find_item();
        } else {
            return what_item;
        }
    }

    /**
     * Between 18:00 and 20:00 the mark-up of the purchase price is 8%
     */
    @Override
    public void evening_period(boolean isEvening) {
        if (isEvening) {
            mark_up_for_each_drink(EVENING_MARKUP);
        } else {
            mark_up_for_each_drink(STANDART_MARKUP);
        }
    }

    /**
     * End of Day. 22:00.
     *
     * @param hour hour.
     */
    @Override
    public void after_purchase(int hour) {
        System.out.println("End of Day " + hour);
        drinkList.forEach((d) -> {
            after_sale_of_goods(d);
        });
    }

    /**
     * At the end of each day the goods are purchased in the amount of 150 for
     * each item, the availability of which is less than 10.
     */
    public void after_sale_of_goods(Drink drink) {
        if (drink.getAmount() < MINIMUM_QUANTITY_OF_GOODS) {
            int amount = drink.getAmount();
            amount += PURCHASE;
            drink.setAmount(amount);
  

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

    /**
     * Next day.
     */
    @Override
    public void nextDay(int day, boolean isWeekend) {
        System.out.println(this.toString());
        if (isWeekend) {
            mark_up_for_each_drink(WEEKEND_MARKUP);
        } else {
            mark_up_for_each_drink(STANDART_MARKUP);
        }
        System.out.println("Day: " + day);

    }

    /**
     * On weekends the markup is 15% of the purchase price
     */
    @Override
    public void weekend() {
        mark_up_for_each_drink(WEEKEND_MARKUP);
    }

    /**
     * Next month. Report in file and re-writing database.
     *
     * @param arg month
     */
    @Override
    public void nextMonth(int arg) {
        System.out.println("New  month: " + arg);

        reWriteDataBase();
        writeReportToFile();

        this.profit = 0;
        this.expenses = 0;

        drinkList.forEach((d) -> {
            d.setHow_many_times_is_bought(0);
            d.setHow_many_times_is_bought(0);
        });

    }

    /**
     * Re-write items in database.
     */
    private void reWriteDataBase() {
        Csv.Writer writer = new Csv.Writer("databaseNew.csv").delimiter(',');
        drinkList.forEach((d) -> {
            String name = d.getName();
            String standart_cost = String.valueOf(d.getStandart_cost());
            String type = String.valueOf(d.type);
            String volume = String.valueOf(d.getVolume());
            String information = d.getInformation();
            String amount = String.valueOf(d.getAmount());
            writer.value(name).value(standart_cost).value(type)
                    .value(volume).value(information).value(amount).newLine();
        });
        writer.close();
    }

    /**
     * Drawing up a profit report.
     */
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
        drinkList.forEach((d) -> {
            String strSold = d.getName() + ": " + d.getHow_much_is_sold();
            s.append(strSold);
            s.append(System.lineSeparator());
        });
        s.append(System.lineSeparator());
        s.append("Количество дозакупленного товара :");
        s.append(System.lineSeparator());
        drinkList.forEach((d) -> {
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
        drinkList.forEach((d) -> {
            d.mark_up(markup);
        });
    }

    @Override
    public String toString() {
        return "Shop{" + "money=" + money + ", profit=" + profit + ", expenses=" + expenses + '}';
    }

}
