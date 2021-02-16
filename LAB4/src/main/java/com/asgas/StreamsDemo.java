package com.asgas;
import java.util.*;
import java.util.stream.Collectors;

public class StreamsDemo
{
    public static List<Integer> createRandomList(int qty)
    {
        var rnd = new Random();
        List<Integer> numbers = new LinkedList<>();

        for (int i = 0; i < qty; i++){
            numbers.add(rnd.nextInt(100));
        }

        return numbers;
    }

    public static List<Integer> sortList(List<Integer> unsortedList)
    {

        Collections.sort(unsortedList, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });

        return unsortedList;
    }

    public static List<Integer> fibonacci(int qty)
    {
        int a = 0, b = 1, c;
        List<Integer> numbs = new ArrayList<>();

        for (int i = 0; i < qty; i++){
            numbs.add(a);
            c = a + b;
            a = b;
            b = c;
        }

        return numbs;
    }

    public static boolean isPrimeNumber(int num)
    {
        if (num <= 1)
            return false;

        var counter = 0;

        for (int i = num - 1; i > 1; i--)
        {
            if (num % i == 0)
                counter++;
        }

        return counter <= 0;
    }

    public static void main(String args[])
    {
        System.out.println("Serie de Fibonacci:");
        var fibonacciList = fibonacci(10);
        System.out.println(fibonacciList);

        System.out.println("Números primos:");
        var dataPrime = createRandomList(50_000_000).parallelStream()
                .filter(n -> isPrimeNumber(n))
                .sorted()
                .map(j -> j.intValue())
                .collect(Collectors.toList());

        var dataPrimeNotRepited = createRandomList(500000).stream()
                .filter(n -> isPrimeNumber(n))
                .sorted()
                .map(j -> j.intValue()).distinct()
                .collect(Collectors.toList());

        System.out.println(dataPrime);
    }
}