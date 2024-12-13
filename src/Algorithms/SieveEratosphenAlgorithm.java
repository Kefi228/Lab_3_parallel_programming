package Algorithms;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class SieveEratosphenAlgorithm {
    private int sieveSize;
    private BitSet sieve;

    public SieveEratosphenAlgorithm(int sieveSize) {
        this.sieveSize = sieveSize;
        this.sieve = new BitSet(sieveSize);
        initializeSieve();
    }

    public void initializeSieve() {
        sieve.set(0, sieveSize + 1, true);
        sieve.set(0, false);
        sieve.set(1, false);
    }

    public List<Integer> getAllPrimes() {

        for (int i = 2; i * i < sieve.length(); i++) {
            if (sieve.get(i)) {
                for (int j = i * i; j < sieve.length(); j += i) {
                    sieve.set(j, false);
                }
            }
        }

        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= sieve.length(); i++) {
            if (sieve.get(i)) {
                primes.add(i);
            }
        }

        return primes;
    }

    public int getSieveSize() {
        return sieveSize;
    }

    public void setSieveSize(int sieveSize) {
        this.sieveSize = sieveSize;
    }

    public BitSet getSieve() {
        return sieve;
    }

    public void setSieve(BitSet sieve) {
        this.sieve = sieve;
    }
}
