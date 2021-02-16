package com.asgas;
import com.asgas.functional.MyFunctionalInterface;
import java.util.List;
import java.util.stream.Collectors;

public class App
{
    public static void doSomethingTraditional()
    {
        var tux = new Tux();
        System.out.println(tux.doSomething("Holi"));
    }

    public static void doSomethingClassy()
    {
        var duke = new MyFunctionalInterface() {
            @Override
            public String doSomething(String param) {
                return "Hola soy Duke y recibí " + param;
            }
        };

        System.out.println(duke.doSomething("una clase anónima"));
    }

    public static void doSomethingFuctional()
    {
        MyFunctionalInterface clippy = (String param) -> {
            return "Hola soy clippy y recibi " + param;
        };
        MyFunctionalInterface wilbert = (p) ->  "Hola soy Wilber y recibí " + p;
        doSomethingHighOrder(clippy);
        doSomethingHighOrder(wilbert);
        doSomethingHighOrder(x-> "Hola soy Anonymus y recibí " + x );
        var pikachu = new Pikachu();
        doSomethingHighOrder(pikachu::Pika);
        doSomethingHighOrder(pikachu::impactrueno);

    }

    public static void doSomethingHighOrder(MyFunctionalInterface comportamiento)
    {
        var respuesta = comportamiento.doSomething(" Java 11 es genial");
        System.out.println(respuesta);
    }

    public static void main( String[] args )
    {
        List jedis = List.of("Anakin", "Leia", "Luke", "Rey");

        var filtered = jedis.stream()
                .filter(s -> !s.equals("Rey"))
                .map(j -> j.toString().toUpperCase())
                .collect(Collectors.toList());
        var theJedi = jedis.stream()
                .filter(s -> !s.equals("Rey"))
                .map(j -> j.toString().toUpperCase())
                .findFirst();

        System.out.println("Resultado de la stream API " + theJedi.get());
        System.out.println(filtered);
    }
}