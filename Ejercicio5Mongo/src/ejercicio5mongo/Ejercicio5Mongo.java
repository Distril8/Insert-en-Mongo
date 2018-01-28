/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio5mongo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import com.mongodb.*;

/**
 *
 * @author DAVID
 */
public class Ejercicio5Mongo {

    /**
     * @param args the command line arguments
     */
    static ArrayList<Persona> personas = new ArrayList<Persona>();

    public static void main(String[] args) {

        File Ffichero = new File("MiFichero.txt");
        LeerFichero(Ffichero);
        MongoClient mongo = crearConexion();

    }

    public static void LeerFichero(File Ffichero) {
        try {
            if (Ffichero.exists()) {

                BufferedReader Flee = new BufferedReader(new FileReader(Ffichero));
                String Slinea;
                while ((Slinea = Flee.readLine()) != null) {
                    String linea = Slinea;
                    String[] cortarString = linea.split("\\|");
                    Persona persona = new Persona();
                    persona.setCedula(cortarString[0]);
                    persona.setNombre(cortarString[1]);
                    persona.setApellido(cortarString[2]);
                    persona.setFechaNace(cortarString[3]);
                    personas.add(persona);
                    MongoClient mongo = new MongoClient();
                    DB db = mongo.getDB("Persona");
                    insertPersona(db, "personas", persona.getCedula(), persona.getNombre(), persona.getApellido(), persona.getFechaNace());
                    selectPersonas(db, "personas");
                }

                Flee.close();
            } else {
                System.out.println("Fichero No Existe");
            }

        } catch (Exception ex) {

            System.out.println(ex.getMessage());
        }
    }

    private static void selectPersonas(DB db, String tabla) {
        DBCollection table = db.getCollection(tabla);
        DBCursor cur = table.find();
        while (cur.hasNext()) {
            System.out.println(" - " + cur.next().get("id") + " " + cur.next().get("nombre") + " " + cur.curr().get("apellido") + " " + cur.curr().get("fechaNace"));
        }
    }

    private static MongoClient crearConexion() {
        MongoClient mongo = null;
        try {
            mongo = new MongoClient("localhost", 27017);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mongo;
    }

    private static void insertPersona(DB db, String tabla, String cedula, String nombre, String apellido, String fechaNace) {
        DBCollection table = db.getCollection(tabla);
        BasicDBObject document = new BasicDBObject();
        document.put("id", cedula);
        document.put("nombre", nombre);
        document.put("apellido", apellido);
        document.put("fechaNace", fechaNace);
        table.insert(document);
    }
}
