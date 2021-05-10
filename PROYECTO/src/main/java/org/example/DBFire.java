package org.example;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class DBFire {
    static Firestore db;
    public  static  Map<String, Map<String, Long>> palabrasRe = new HashMap<>();
    public static Map<String, Long> oracionesRe = new HashMap<>();

    public void iniciar() throws IOException, ExecutionException, InterruptedException {
        conectar();
        Map<String, Map<String, Long>> palabras = new HashMap<>();
        Map<String, Long> oraciones = new HashMap<>();
        ApiFuture<WriteResult> future = db.collection("Proyecto").document("Oraciones").set(oraciones);
        future.get();
        ApiFuture<WriteResult> future2 = db.collection("Proyecto").document("palabras").set(oraciones);
        future2.get();
    }

    public void loadfile() throws IOException, ExecutionException, InterruptedException {

        //Se le pide un archivo al usuario especificamente que sea csv
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "CSV File", "csv");
        chooser.setFileFilter(filter);

        int returnVal =  chooser.showOpenDialog(null);

        String path = chooser.getSelectedFile().getPath();

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            //Se retorna el Path del archivo si existe
            System.out.println("You chose to open this file: " +
                    chooser.getSelectedFile().getName() + "\n With the path: " + chooser.getSelectedFile().getPath());
        }
        else
        {
            return;
        }
        try {
            //Es un parser para leer el .csv
            final CSVParser parser =
                    new CSVParserBuilder()
                            .withSeparator('|')
                            .build();
            //Tomando el parser anterior y el separador que sea |
            final CSVReader reader =
                    new CSVReaderBuilder(new FileReader(path))
                            .withCSVParser(parser)
                            .build();
            //Se leen todas las lineas del csv y se pasan a una lista de arrays
            List<String[]> r = reader.readAll();
            //Se manda a separar y contar para mandar a la db
            separarYContarFrases(r);

            System.out.println(r.stream().count());
        } catch (FileNotFoundException e) {
            System.out.println("No se pudo encontrar el archivo o no eligió ninguno");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }
    }
    public static void desconectar() throws ExecutionException, InterruptedException,IOException {

    }
    private static void conectar() throws IOException {
        //Conexion a base de datos no relacional
        //Se obtiene la key de firebase
        FileInputStream serviceAccount =
                new FileInputStream("keyFirebase.json");
        //Se logea en firebase con la key
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://proyectoai-f37f7-default-rtdb.firebaseio.com")
                .build();
        FirebaseApp.initializeApp(options);
        //Se obtiene la db
        db = FirestoreClient.getFirestore();
        System.out.println("Se conecto a la base de datos firebase");
    }

    private static void separarYContarFrases(List<String[]> dataOriginal) throws ExecutionException, InterruptedException {
        /*
        DocumentReference docref = db.collection("Proyecto").document("palabras");
        ApiFuture<DocumentSnapshot> fut = docref.get();
        DocumentSnapshot docut = fut.get();
        var palabra = docut.getData();

        //Extraer diccionario de frases8
        DocumentReference docref2 = db.collection("Proyecto").document("Oraciones");
        ApiFuture<DocumentSnapshot> fut2 = docref2.get();
        DocumentSnapshot docut2 = fut2.get();
        var oracion = docut2.getData();

        */
        Map<String, Map<String, Long>> palabras = new HashMap<>();
        Map<String, Long> oraciones = new HashMap<>();
        if(oracionesRe != null)
        {
            oraciones = oracionesRe;
            //oracion.forEach((k,v) -> oraciones.put(k, ((Long) v)));
            palabras = palabrasRe ;
        }

        for (var item : dataOriginal) {
            if (item.length ==2 && !item.equals("")) {
                var replaced = "";
                replaced = item[0].replaceAll("([?|¿|!|'.'|¡|\\-|+|\"|,|\\-|–|\"|\"|“|”|\\(|\\)]|[\\uFEFF-\\uFFFF]|[\\u00A0]|[0-9])+", "");
                //Para normalizar los datos a ser todos minuscula
                replaced = replaced.toLowerCase();
                replaced = replaced.trim();
                var aux = item[1].trim();
                var separ = aux.split(" ");
                if (oraciones.containsKey(item[1].toLowerCase(Locale.ROOT).trim())) {

                    oraciones.computeIfPresent(item[1].toLowerCase(Locale.ROOT).trim(), (k, v) -> v + 1);
                } else {
                    if (separ.length==1) {
                        oraciones.put(item[1].toLowerCase(Locale.ROOT).trim(), Long.valueOf(1));
                    }
                }

                var reemplazo = replaced.split(" ");
                Map<String, Long> creacion = new HashMap<>();

                if (palabras.containsKey(item[1].toLowerCase(Locale.ROOT).trim())) {
                    var pal = palabras.get(item[1].toLowerCase(Locale.ROOT).trim());
                    for (int i = 0; i < reemplazo.length; i++) {

                        if (pal.containsKey(reemplazo[i])) {
                            pal.computeIfPresent(reemplazo[i], (k, v) -> v + 1);
                        } else {
                            if (separ.length ==1) {
                                if (reemplazo[i] != "") {
                                    pal.put(reemplazo[i], Long.valueOf(1));
                                }
                            }
                        }

                    }
                    pal.remove("");
                    palabras.remove(item[1].toLowerCase(Locale.ROOT).trim());
                    palabras.put(item[1].toLowerCase(Locale.ROOT).trim(), pal);
                    palabras.remove("");


                } else {
                    if (separ.length==1) {
                        for (int i = 0; i < reemplazo.length; i++) {
                            if (reemplazo[i] != "") {
                                creacion.put(reemplazo[i], Long.valueOf(1));
                            }
                        }

                        palabras.put(item[1].toLowerCase(Locale.ROOT).trim(), creacion);
                    }

                }
            }
        }
        /*
        ApiFuture<WriteResult> future = db.collection("Proyecto").document("Oraciones").set(oraciones);
        future.get();
        //Separar las colecciones por idioma
        //palabras.forEach((k, v) -> db.collection("Proyecto").document(k).set(v));
        //Colecciones de palabras juntas
        ApiFuture<WriteResult> future2 = db.collection("Proyecto").document("palabras").set(palabras);
        future.get();
        */
        oracionesRe = oraciones;
        palabrasRe = palabras;

        //oraciones.clear();
        //palabras.clear();

    }

    public void IngresoDato(String entrada, String idioma) throws ExecutionException, InterruptedException {
        //Extrar diccionario de palabras
        /*
        DocumentReference docref = db.collection("Proyecto").document("palabras");
        ApiFuture<DocumentSnapshot> fut = docref.get();
        DocumentSnapshot docut = fut.get();
        var palabra = docut.getData();

        //Extraer diccionario de frases
        DocumentReference docref2 = db.collection("Proyecto").document("Oraciones");
        ApiFuture<DocumentSnapshot> fut2 = docref2.get();
        DocumentSnapshot docut2 = fut2.get();
        var oracion = docut2.getData();
        */
        Map<String, Map<String, Long>> palabras = new HashMap<>();
        Map<String, Long> oraciones = new HashMap<>();

        //limpiar la cadena
        var replaced = "";
        replaced = entrada.replaceAll("([?|¿|!|'.'|¡|\\-|+|\"|,|\\-|–|\"|\"|“|”|\\(|\\)]|[\\uFEFF-\\uFFFF]|[\\u00A0]|[0-9])+", "");
        //Para normalizar los datos a ser todos minuscula
        replaced = replaced.toLowerCase();
        replaced = replaced.trim();
        var reemplazo = replaced.split(" ");
        var aux = idioma.trim().toLowerCase(Locale.ROOT);
        var separ = aux.split(" ");

        if (oracionesRe == null) {
            //Diccionarios
            if (separ.length>1) {
                System.out.println("Idioma no valido");
                return;
            } else {

                Map<String, Long> auxiliar = new HashMap<>();
                oraciones.put(aux, Long.valueOf(1));
                for (int i = 0; i < reemplazo.length; i++) {
                    if (reemplazo[i] != "" && reemplazo[i] != " ") {
                        auxiliar.put(entrada, Long.valueOf(1));
                    }
                }
                auxiliar.remove("");
                palabras.put(aux, auxiliar);

            }

        } else {

            //palabra.forEach((k,v) -> palabras.put(k, ((Map) v)));
            palabras = palabrasRe;
            oraciones = oracionesRe;

            if(separ.length==1) {
                if (!oraciones.containsKey(aux)) {
                    oraciones.put(aux, Long.valueOf(1));
                } else {
                    oraciones.computeIfPresent(aux, (k, v) -> v + 1);
                }

                if (palabras.containsKey(aux)) {
                    var pal = palabras.get(aux);

                    for (int i = 0; i < reemplazo.length; i++) {
                        if (pal.containsKey(reemplazo[i])) {
                            pal.computeIfPresent(reemplazo[i], (k, v) -> v + 1);
                        } else {

                            if (reemplazo[i] != "") {
                                pal.put(reemplazo[i], Long.valueOf(1));
                            }

                        }
                    }
                    palabras.remove(aux);
                    palabras.remove(" ");
                    palabras.remove("");
                    palabras.put(aux, pal);


                } else {
                    Map<String, Long> creac = Collections.EMPTY_MAP;
                    // boolean compor = false;


                    for (int i = 0; i < reemplazo.length; i++) {
                        if(!creac.containsKey(reemplazo[i])) {
                            creac.put(String.valueOf(reemplazo[i]), Long.valueOf(1));
                        }
                        else{
                            creac.computeIfPresent(reemplazo[i],  (k,v) -> v+1);
                        }

                    }
                    palabras.remove("");
                    palabras.put(aux, creac);


                }
            }


        }
        /*
        ApiFuture<WriteResult> future = db.collection("Proyecto").document("Oraciones").set(oraciones);
        future.get();
        //Separar las colecciones por idioma
        //palabras.forEach((k, v) -> db.collection("Proyecto").document(k).set(v));
        //Colecciones de palabras juntas

        ApiFuture<WriteResult> future2 = db.collection("Proyecto").document("palabras").set(palabras);
        future2.get();
        */

        oracionesRe = oraciones;
        palabrasRe = palabras;
        //oraciones.clear();
        //palabras.clear();
    }
    public Map<String, Map<String, Long>> getPalabrasRe()
    {
        return palabrasRe;
    }
    public Map<String, Long> getOracionesRe()
    {
        return oracionesRe;
    }
}