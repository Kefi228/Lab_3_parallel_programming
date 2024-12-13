package Algorithms;

import java.util.ArrayList;
import java.util.List;

public class ModifiedSequentialSearchAlgorithm {
    private int size;
    private SieveEratosphenAlgorithm sieveEratosphenAlgorithm;
    private List<Integer> basePrimes;


    public ModifiedSequentialSearchAlgorithm(int size) {
        this.size = size;
        this.sieveEratosphenAlgorithm = new SieveEratosphenAlgorithm((int) Math.sqrt(size));
        this.basePrimes = sieveEratosphenAlgorithm.getAllPrimes();
    }


    public List<Integer> getAllPrimes() {

        basePrimes.addAll(checkPrimesInRange(((int) Math.sqrt(size)) + 1, size));

        return basePrimes;
    }

    public List<Integer> checkPrimesInRange(int start, int end) {
        List<Integer> primesInRange = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            if (isPrime(i)) {
                primesInRange.add(i);
            }
        }
        return primesInRange;
    }

    public boolean isPrime(int number) {
        if (number < 2) return false;
        if (basePrimes.size() == 0) return false;
        for (int prime : basePrimes) {
            if (number % prime == 0) return false;
        }
        return true;
    }


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public SieveEratosphenAlgorithm getSieveEratosphenAlgorithm() {
        return sieveEratosphenAlgorithm;
    }

    public void setSieveEratosphenAlgorithm(SieveEratosphenAlgorithm sieveEratosphenAlgorithm) {
        this.sieveEratosphenAlgorithm = sieveEratosphenAlgorithm;
    }

    public List<Integer> getBasePrimes() {
        return basePrimes;
    }

    public void setBasePrimes(List<Integer> basePrimes) {
        this.basePrimes = basePrimes;
    }

}
