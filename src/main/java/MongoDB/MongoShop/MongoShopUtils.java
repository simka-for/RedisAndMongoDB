package MongoDB.MongoShop;


import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;

public class MongoShopUtils {

    private static final MongoClient mongoClient = new MongoClient( "127.0.0.1" , 27017 );
    private static final MongoDatabase database = mongoClient.getDatabase("local");
    private static final MongoCollection<Document> shopCollection = database.getCollection("shops");
    private static final MongoCollection<Document> productCollection = database.getCollection("products");

    public static void addShop(String shopName) {

        if (shopName.isEmpty()) {
            throw new RuntimeException();
        }

        try {
            Document shop = new Document("name", shopName);
            shop.append("products", new ArrayList<String>());

            if (getShop(shopName) == null) {
                MongoShopUtils.shopCollection.insertOne(shop);
                System.out.println("Магазин " + shopName + " создан!");
            } else {
                System.out.println("Магазин уже был добавлен!");
            }
        } catch (Exception e) {
            System.out.println("Неверный ввод!");
        }
    }

    public static void addProduct(String input) {
        try {
            String[] var = split(input);
            String name = var[0];
            Document product = new Document("name", name);
            product.append("price", Integer.parseInt(var[1]));

            if (getProduct(name) == null) {
                productCollection.insertOne(product);
                System.out.println("Продукт " + name + " добавлен!");
            } else {
                System.out.println("Товар уже был добавлен");
            }
        } catch (Exception e) {
            System.out.println("Неверный ввод!");
        }
    }

    public static void addProductsToShop(String input) {
        try {
            String[] var = split(input);
            String shopName = var[1];
            String productName = var[0];

            shopCollection.updateOne(getShop(shopName), new Document("$addToSet",
                    new Document("products", getProduct(productName).get("name"))));
            System.out.println("Продукт " + productName + " добавлен в магазин " + shopName);
        } catch (Exception e) {
            System.out.println("Магазин или продукт не существуют! ");
        }
    }

    private static AggregateIterable<Document> getAggregate() {
        return productCollection .aggregate(
                Arrays.asList(
                        Aggregates.lookup("shops", "name", "products", "shop_list"),
                        Aggregates.unwind("$shop_list"),
                        Aggregates.group("$shop_list.name",
                                Accumulators.sum("count_products", 1),
                                Accumulators.min("min_price", "$price"),
                                Accumulators.max("max_price", "$price"),
                                Accumulators.avg("avg_price", "$price"))
                )
        );
    }

    private static void printInfo(AggregateIterable<Document> documents) {
        for (Document document : documents) {
            String shopName = (String) document.get("_id");
            System.out.println("Магазин " + shopName);
            System.out.println("Количество товара: " + document.get("count_products"));
            System.out.println("Средняя цена товара: " + document.get("avg_price"));
            System.out.println("Самый дорогой товар:  " + document.get("max_price"));
            System.out.println("Самый дешевый товар:  " + document.get("min_price"));
            System.out.println("Количество товаров, дешевле 100 рублей: " + cheapestProductCount(shopName) + "\n");
        }
    }

    private static long cheapestProductCount(String shopName) {
        Document shop = getShop(shopName);
        ArrayList<String> products = (ArrayList<String>) shop.get("products");
        return products.stream().filter(s -> (int) getProduct(s).get("price") < 100).count();
    }

    public static void printTotal() {
        printInfo(getAggregate());
    }

    private static Document getShop(String name) {
        return shopCollection.find(new Document("name", name)).first();
    }

    private static Document getProduct(String name) {
        return productCollection.find(new Document("name", name)).first();
    }

    public static String[] split(String var) {
        return var.split(" ", 2);
    }

    public static void dropDB(){
        database.drop();
        mongoClient.close();
    }
}
