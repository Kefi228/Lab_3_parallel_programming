package Algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SequentialPrimeCheckAlgorithm {
    private int size;
    private SieveEratosphenAlgorithm sieveEratosphenAlgorithm;
    private List<Integer> basePrimes;
    private List<Integer> foundPrimes;
    private AtomicInteger currentPrimeIndex;

    public SequentialPrimeCheckAlgorithm(int size) {
        this.size = size;
        this.sieveEratosphenAlgorithm = new SieveEratosphenAlgorithm((int) Math.sqrt(size));
        this.basePrimes = sieveEratosphenAlgorithm.getAllPrimes();
        this.foundPrimes = Collections.synchronizedList(new ArrayList<>());
        this.currentPrimeIndex = new AtomicInteger(0);
    }

    private List<Integer> processPrimes() {
        while(true){
            int primeIndex = currentPrimeIndex.getAndIncrement();
            if(primeIndex >= basePrimes.size()) break;

            int prime = basePrimes.get(primeIndex);
            foundPrimes.addAll(checkPrimesInRange(prime, size));

        }
        return foundPrimes;
    }


    private List<Integer> checkPrimesInRange(int prime, int end) {
        for (int i = prime; i <= end; i ++) {
            synchronized (foundPrimes){
                if (isPrime(i) && !foundPrimes.contains(i)) {
                    foundPrimes.add(i);
                }
            }
        }
        return foundPrimes;
    }

    public boolean isPrime(int number) {
        if (number < 2) return false;
        for (int prime : basePrimes) {
            if (number % prime == 0) return false;
        }
        return true;
    }

    public List<Integer> getAllPrimes(int numberOfThreads) {
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++) {
            executorService.execute(this::processPrimes);
        }
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
        basePrimes.addAll(foundPrimes.stream().distinct().toList());
        return basePrimes;
    }


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Integer> getBasePrimes() {
        return basePrimes;
    }

    public void setBasePrimes(List<Integer> basePrimes) {
        this.basePrimes = basePrimes;
    }

    public SieveEratosphenAlgorithm getSieveEratosphenAlgorithm() {
        return sieveEratosphenAlgorithm;
    }

    public void setSieveEratosphenAlgorithm(SieveEratosphenAlgorithm sieveEratosphenAlgorithm) {
        this.sieveEratosphenAlgorithm = sieveEratosphenAlgorithm;
    }

    public List<Integer> getFoundPrimes() {
        return foundPrimes;
    }

    public void setFoundPrimes(List<Integer> foundPrimes) {
        this.foundPrimes = foundPrimes;
    }

    public AtomicInteger getCurrentPrimeIndex() {
        return currentPrimeIndex;
    }

    public void setCurrentPrimeIndex(AtomicInteger currentPrimeIndex) {
        this.currentPrimeIndex = currentPrimeIndex;
    }
}
