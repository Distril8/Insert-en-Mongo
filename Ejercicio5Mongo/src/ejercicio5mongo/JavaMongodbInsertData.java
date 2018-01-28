/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio5mongo;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import static java.util.Arrays.asList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;
/**
 *
 * @author DAVID
 */
public class JavaMongodbInsertData {
    private MongoClient mongoClient;    // Java MongoDB client (Cliente Java MongoDB)
    private MongoDatabase mongodb;      // Database object (Objeto base de datos)
    /**
     * We establish the connection with the database <b>test</b>.
     * Establecemos la conexión con la base de datos <b>test</b>.
     */
    public void connectDatabase(){
        setMongoClient(new MongoClient());             
        setMongodb(getMongoClient().getDatabase("Persona"));
    }
    /**
     * We use the method <b>insertOne</b> to add a document to the database example.
     * Usamos el método <b>insertOne</b> para añadir un documento a la base de datos de ejemplo.
     */
    public void insertOneDataTest(){
        try {            
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            // We add a document to the database directly (Añadimos un documento a la base de datos directamente).
            LinkedList<Document> dataList = new LinkedList<>();
             dataList.add(new Document("informacion", asList(
                            new Document()
                                    .append("nombre", "Carlos")
                                    .append("apellido", "Chavez")
                                    .append("fechaNace", "1994-17-12"))));
          getMongodb().getCollection("personas").insertMany(dataList);
        } catch (Exception ex) {
            Logger.getLogger(JavaMongodbInsertData.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    public void listRestaurants(){ 
        // To return all documents in a collection, call the find method without a criteria document.
        // Para devolver todos los documentos en una colección, llamamos al método find sin ningún documento <b>criteria</b>
        FindIterable<Document> iterable = getMongodb().getCollection("personas").find();
        // Iterate the results and apply a block to each resulting document.
        // Iteramos los resultados y aplicacimos un bloque para cada documento.
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document);
            }
        });   
    }
    public MongoClient getMongoClient() {
        return mongoClient;
    }
 
    public void setMongoClient(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }
 
    public MongoDatabase getMongodb() {
        return mongodb;
    }
 
    public void setMongodb(MongoDatabase mongodb) {
        this.mongodb = mongodb;
    } 
}
