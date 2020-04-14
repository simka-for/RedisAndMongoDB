package MongoDB.MongoShop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import static MongoDB.MongoShop.MongoShopUtils.*;

public class MongoShopMain {


    public static void main(String[] args) throws InterruptedException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("MongoDB shop manager");
        Thread.sleep(500);
        System.out.println( "\nЧтобы создать новый магазин введите 'ADD_SHOP \"Название магазина\"'\n" +
                "Для добавления товара введите 'ADD_PRODUCT \"Название товара\" \"Цена товара\"'\n" +
                "Чтобы поместить товар в магазин введите 'PUT_UP \"Название товара\" \"Название магазина\" " +
                "Для просмотра статистики по товарам введите \"TOTAL\"\n" +
                "Для сброса данных из DB введите \"DROP\"");

        while (true){

            String[] input = split(scanner.nextLine().trim());

            String query = input[0];
            String object = input.length > 1 ? input[1] : "";

            switch (query) {
                case "ADD_SHOP" -> addShop(object);
                case "ADD_PRODUCT" -> addProduct(object);
                case "PUT_UP" -> addProductsToShop(object);
                case "TOTAL" -> printTotal();
                case "DROP" -> {
                    dropDB();
                    return;
                }
                default -> System.out.println("Неверный ввод команды");
            }
        }
    }
}
