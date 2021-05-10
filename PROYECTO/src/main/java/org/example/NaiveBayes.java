package org.example;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.math.*;
import java.util.concurrent.ExecutionException;

public class NaiveBayes {

    private static Map<String, Long> idiomas;
    private Map<String, Map<String, Long>> palabras;
    private int numberDecimals = 100;
    public NaiveBayes(Map<String, Long> idiomas, Map<String, Map<String, Long>> palabras) throws ExecutionException, InterruptedException,IOException {
        this.idiomas=idiomas;
        this.palabras= palabras;
    }
    public bestCountry inferir(String frase){
        frase = frase.toLowerCase();
        String patter = "[ ]";
        String[] words = frase.split(patter);
        List<bestCountry> probability = probability(words);
        bestCountry result = getHighest(probability);
        BigDecimal complement = new BigDecimal(1);
        result.probability = complement.subtract(result.probability);
        return result;
    }
    private bestCountry getHighest(List<bestCountry> posibles){
        List<BigDecimal> numbers = Arrays.asList();
        posibles.sort((A,B)->A.probability.compareTo(B.probability));
        return posibles.get(0);
    }
    private List<bestCountry> probability( String[] words){
        List<String> listWords = Arrays.asList(words);
        List<bestCountry> probabilities = new LinkedList<>();
        for (var language : idiomas.keySet()) {
            BigDecimal probability = new BigDecimal(0);
            probability = probability.add(numerator(language,listWords));
            if(!deNumerator(listWords).equals(new BigDecimal("0E-100"))  || !deNumerator(listWords).equals(BigDecimal.ZERO)){
                probability = probability.divide(deNumerator(listWords),numberDecimals,RoundingMode.HALF_UP);
                probabilities.add(new bestCountry(language,probability));
            }
        }
        return  probabilities;
    }
    /*
    * oraciones del lenguaje * oraciones de cada lenguaje
    * */
    private BigDecimal probabilityLanguage (String language){
        int valueCountry = idiomas.get(language).intValue();
        var valuesGlobal = idiomas.values();
        int countValuesGlabal=0;
        for (var value : valuesGlobal) {
            countValuesGlabal += value.intValue();
        }
        BigDecimal decimalCountry = new BigDecimal(valueCountry);
        BigDecimal decimalGeneral = new BigDecimal(countValuesGlabal);
        decimalCountry = decimalCountry.divide(decimalGeneral,numberDecimals,RoundingMode.HALF_UP);
        return decimalCountry;
    }
    /*
    * veces repetida la palabra en ese lenguaje / todas las palabras repetidas en ese lenguaje
    * */
    private BigDecimal probabilityWord(String language, String word) {
        var repitedWord = palabras.get(language).get(word).intValue();
        var listwords = palabras.get(language).keySet();
        int countRepitedWords = 0;
        for (var item : listwords) {
            countRepitedWords += palabras.get(language).get(item).intValue();
        }
        BigDecimal frequencyWord = new BigDecimal(repitedWord);
        BigDecimal frequencyWords = new BigDecimal(countRepitedWords);
        frequencyWord = frequencyWord.divide(frequencyWords,numberDecimals,RoundingMode.HALF_UP);
        return frequencyWord;
    }
    /*
    * probabilidad de un frase dado un idioma * probabilidad de un idioma
    * */
    private BigDecimal numerator(String language, List<String> words){
        var tempWords = new ArrayList<>(words);
        for (var word : words) {
            if (!palabras.get(language).containsKey(word)) {
                tempWords.remove(word);
            }
        }

        BigDecimal languageProbabilities = new BigDecimal(1);
        for (var word : tempWords) {
            languageProbabilities=languageProbabilities.multiply(probabilityWord(language,word));
        }
        languageProbabilities = languageProbabilities.multiply(probabilityLanguage(language));
        return  languageProbabilities;
    }
    /*
    * probabilidad de una frase, normalizado
    * */
    private  BigDecimal deNumerator(List<String> words){
        var listLanguages = idiomas.keySet();
        BigDecimal countProbabilities = new BigDecimal(0);
        for (var langu : listLanguages) {
            BigDecimal languageProbabilities = new BigDecimal(1);
            var tempWords = new ArrayList<>(words);
            for (var word : words) {
                if (!palabras.get(langu).containsKey(word)) {
                    tempWords.remove(word);
                }
            }
            for (var word : tempWords) {
                languageProbabilities=languageProbabilities.multiply(probabilityWord(langu,word));
            }
            languageProbabilities = languageProbabilities.multiply(probabilityLanguage(langu));
            countProbabilities = countProbabilities.add(languageProbabilities);
        }
        return countProbabilities;
    }
    private void fillDictionaris() throws ExecutionException, InterruptedException,IOException{
        conectar();
        DocumentReference docref = db.collection("Proyecto").document("palabra");
        ApiFuture<DocumentSnapshot> fut = docref.get();
        DocumentSnapshot docut = fut.get();
        var tempPalabra = docut.getData();

        //Extraer diccionario de frases
        DocumentReference docref2 = db.collection("Proyecto").document("Oraciones");
        ApiFuture<DocumentSnapshot> fut2 = docref2.get();
        DocumentSnapshot docut2 = fut2.get();
        var tempOraciones = docut2.getData();
        if(tempOraciones != null)
        {
            idiomas = ((Map) tempOraciones);
            //oracion.forEach((k,v) -> oraciones.put(k, ((Long) v)));
            palabras = ((Map) tempPalabra);
        }
    }
    static Firestore db;

    private static void conectar() throws IOException {
        //Conexion a base de datos no relacional
        //Se obtiene la key de firebase
        FileInputStream serviceAccount = new FileInputStream("keyFirebase.json");
        //Se logea en firebase con la key
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://proyectoai-f37f7-default-rtdb.firebaseio.com")
                .build();
        //FirebaseApp.initializeApp(options);
        //Se obtiene la db
        db = FirestoreClient.getFirestore();
    }

}
