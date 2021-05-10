package org.example;

import java.math.BigDecimal;

public class bestCountry {
    String languaje;
    BigDecimal probability;
    public bestCountry(String languaje, BigDecimal probability){
        this.languaje=languaje;
        this.probability=probability;
    }
    public bestCountry(String languaje, Integer probability){
        this.languaje=languaje;
        this.probability=new BigDecimal(probability);
    }
    public bestCountry(String languaje, Long probability){
        this.languaje=languaje;
        this.probability=new BigDecimal(probability);
    }
}
