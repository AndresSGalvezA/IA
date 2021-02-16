package com.asgas;
import com.asgas.functional.MyFunctionalInterface;

public class Tux implements MyFunctionalInterface
{
    @Override
    public String doSomething(String param){
        return "Hola, soy Tux y recibí el siguiente parámetro: " + param;
    }
}
