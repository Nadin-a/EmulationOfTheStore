/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testtaskshop;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javenue.csv.Csv;

/**
 *
 * @author Nadina 
 * Выходной, скидка
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {

        Shop shop = new Shop();
        List<Drink> empList = new ArrayList<>();
        Time month = new Time();
        month.addTimeAndDateListener(shop);

        try (BufferedReader reader = new BufferedReader(new FileReader(
                "database.csv"))) {
            readFile(reader, empList);
            reader.close();
        }

        shop.setEmpList(empList);
        showData(empList);
        month.start();

        Csv.Writer writer = new Csv.Writer("databaseNew.csv").delimiter(',');
        empList.forEach((d) -> {
            String name = d.getName();
            String standart_cost = String.valueOf(d.getStandart_cost());
            String type = String.valueOf(d.type);
            String volume = String.valueOf(d.getVolume());
            String information = d.getInformation();
            String count = String.valueOf(d.getCount());
            writer.value(name).value(standart_cost).value(type)
                    .value(volume).value(information).value(count).newLine();
        });
        writer.close();

    }

    private static void readFile(BufferedReader reader, List<Drink> empList) throws IOException {
        String line;
        Scanner scanner;
        int index = 0;
        while ((line = reader.readLine()) != null) {
            Drink drink = new Drink();
            scanner = new Scanner(line);
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                String data = scanner.next();

                switch (index) {
                    case 0: {
                        drink.setName(data);
                        break;
                    }
                    case 1: {
                        drink.setStandart_cost(Double.valueOf(data));
                        break;
                    }
                    case 2: {
                        drink.type = Type.valueOf(data);
                        break;
                    }
                    case 3: {
                        drink.setVolume(Float.valueOf(data));
                        break;
                    }
                    case 4: {
                        drink.setInformation(data);
                        break;
                    }
                    case 5: {
                        drink.setCount(Integer.valueOf(data));
                        break;
                    }
                    default: {
                        break;
                    }
                }
                index++;
            }
            index = 0;
            empList.add(drink);
        }

    }

    private static void showData(List<Drink> empList) {
        empList.forEach((d) -> {
            System.out.println(d.toString());
        });
    }

}

/**
 * "Пиво Одесское Новое",13.25,BEER,0.5,4.3,120 "Красная
 * испанка",80.00,WINE,0.75,14,92 "Мартини
 * Биссе",95.00,LIQUEURS,205.00,1.0,13,12 "Два моря",195.00,WINE,0.75,12,0
 *
 * "Вода минеральная Хорошо",9.99,MINERAL_WATER,0.3,вода минеральная
 * лечебно-столовая,570 "Вода минеральная Хорошо",15.47,MINERAL_WATER,1.5,вода
 * минеральная лечебно-столовая,412 "Сок Богач
 * Грейпфрутовый",22.00,JUICES,0.95,вода сок грейпфрутовый концентрированный
 * фруктоза лимонная кислота,156 "Енерджи бум Плюс",24.15,OTHER_DRINKS,0.33,вода
 * лимонная кислота ароматизатор Яблоко Е-345 Е-120 Е-630 Е-632 краситель
 * Вишня,78
 *
 */
