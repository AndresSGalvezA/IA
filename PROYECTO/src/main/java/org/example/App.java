package org.example;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;


/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws InterruptedException, ExecutionException, IOException
    {
        DBFire  prueba = new DBFire();
        //prueba.iniciar();
        int option = 0 ;
        do {

            System.out.println("*************Detector de idiomas***************");
            System.out.println("1. Cargar CSV");
            System.out.println("2. Ingresar datos manuales");
            System.out.println("3. Consulta de idioma");
            System.out.println("4. Salir");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                option = Integer.parseInt(reader.readLine());
            }catch(Exception e){
                System.out.println("Debe de ingresar un numero Entero menor que 4");
                option= 0;
            }

            switch (option) {
                case 1:
                    System.out.println("entro a 1");
                    prueba.loadfile();
                    break;
                case 2:
                    System.out.println("entro a 2");
                    System.out.println("Ingrese la cadena que desea agregar a la coleccion");
                    System.out.println("Ejemplo: Hola Mundo|español");
                    String cadena = reader.readLine();
                    if(cadena.contains("|"))
                    {
                        var valores = cadena.split("\\|");
                        valores[1] = valores[1].replaceAll("([?|¿|!|' '|\t|'.'|¡|\\-|+|\"|,|\\-|–|\"|\"|“|”|\\(|\\)]|[\\uFEFF-\\uFFFF]|[\\u00A0]|[0-9])+", "");
                        valores[1] = valores[1].toLowerCase(Locale.ROOT);
                        prueba.IngresoDato(valores[0],valores[1]);
                        System.out.println("Todo bien");
                    }
                    else{
                        System.out.println("No cuenta con el formato especifico");
                    }
                    break;
                case 3:
                    System.out.println("entro a 3");
                    System.out.println("Ingrese un texto");
                    String frase = reader.readLine();
                    NaiveBayes nb = new NaiveBayes(prueba.getOracionesRe(),prueba.getPalabrasRe());
                    var result = nb.inferir(frase);
                    System.out.println(result.languaje);
                    System.out.println(result.probability);
                    break;
                case 4:
                    System.out.println("entro a 4");
                    System.out.println("************Saliendo**************");
                    System.exit(0);
                    break;
                default:
                    System.out.println("entro a default");
                    System.out.println("Dato no valido");
                    break;
            }
        }while(option != 4);
        System.exit(0);
    }
}


