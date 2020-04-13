package MongoDB;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.Document;

import java.util.function.Consumer;

public class MongoBook {

    public static void main(String[] args) {

        MongoClient mongoClient = new MongoClient( "127.0.0.1" , 27017 );
        MongoDatabase database = mongoClient.getDatabase("local");

        MongoCollection<Document> collection = database.getCollection("Books");
        collection.drop();


        Document firstBook = new Document()
                .append("name", "Master and Margarita")
                .append("author", "Bulgakov M.")
                .append("date", 1966);

        Document secondBook = new Document()
                .append("name", "White guard")
                .append("author", "Bulgakov M.")
                .append("date", 1925);

        Document thirdBook = new Document()
                .append("name", "Crime and punishment")
                .append("author", "Dostoevskiy F.")
                .append("date", 1866);

        Document fourthBook = new Document()
                .append("name", "War and Piece")
                .append("author", "Tolstoy L.")
                .append("date", 1865);

        Document fifthBook = new Document()
                .append("name", "Evgeny Onegin")
                .append("author", "Pushkin A.")
                .append("date", 1832);

        collection.insertOne(firstBook);
        collection.insertOne(secondBook);
        collection.insertOne(thirdBook);
        collection.insertOne(fourthBook);
        collection.insertOne(fifthBook);

//        collection.find().forEach((Consumer<Document>) System.out::println);


        BsonDocument secondQuery = BsonDocument.parse("{author: {$eq: \"Bulgakov M.\"}}");

        collection.find().sort(new BasicDBObject("date", 1)).limit(1).forEach((Consumer<Document>) document -> {
            System.out.println("\nСамая старая книга:\n" + document);
        });

        System.out.println("\nКниги любимого писателя:");
        collection.find(secondQuery).forEach((Consumer<Document>) System.out::println);
    }

}
