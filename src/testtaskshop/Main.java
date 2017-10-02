package testtaskshop;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Nadina 
 * Main class.
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
        Store store = new Store();
        List<Drink> drinkList = new ArrayList<>();
        Time time = new Time();
        time.addTimeAndDateListener(store);

        try (BufferedReader reader = new BufferedReader(new FileReader(
                "database.csv"))) {
            readFile(reader, drinkList);
            reader.close();
        }

        store.setDrinkList(drinkList);
        showData(drinkList);
        time.start();
       
    }

    /**
     * Read database from .csv file. List formation.
     * @param drinkList  list of srinks from stock.
     */
    private static void readFile(BufferedReader reader, List<Drink> drinkList) throws IOException {
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
                        drink.setAmount(Integer.valueOf(data));
                        break;
                    }
                    default: {
                        break;
                    }
                }
                index++;
            }
            index = 0;
            drinkList.add(drink);
        }

    }

    /**
     * Show data from list.
     */
    private static void showData(List<Drink> drinkList) {
        System.out.println("Имеющиеся товары: ");
        drinkList.forEach((d) -> {
            System.out.println(d.toString());
        });
    }
}