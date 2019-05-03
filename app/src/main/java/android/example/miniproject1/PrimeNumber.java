package android.example.miniproject1;

import java.io.Serializable;

public class PrimeNumber implements Serializable {
    long prime;


    public PrimeNumber() {}
    public PrimeNumber(int primeNum){
        prime = primeNum;
    }

    public long returnPrime(){return prime;}
}
