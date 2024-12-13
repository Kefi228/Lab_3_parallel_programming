package Algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class ThreadPoolAlgorithm {
    private int size;
    private ModifiedSequentialSearchAlgorithm modifiedSequentialSearchAlgorithm;
    private List<Integer> basePrimes;

    public ThreadPoolAlgorithm(int size) {
        this.size = size;
        this.modifiedSequentialSearchAlgorithm = new ModifiedSequentialSearchAlgorithm(size);
        this.basePrimes = modifiedSequentialSearchAlgorithm.getBasePrimes();
    }

    public List<Integer> getAllPrimes( int end) {
        int start = (int)Math.sqrt(end);
        ExecutorService executor = Executors.newFixedThreadPool(basePrimes.size());
        List<Future<List<Integer>>> futures = new ArrayList<>();
        List<Integer> primeNumbers = new ArrayList<>();
        for (int prime:basePrimes) {
            futures.add(executor.submit(() -> checkPrimesInRangeByPrime(start, end, prime)));
        }

        for (Future<List<Integer>> future : futures) {
            try {
                primeNumbers.addAll(future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
        basePrimes.addAll(primeNumbers.stream()
                .distinct()
                .filter(modifiedSequentialSearchAlgorithm::isPrime)
                .collect(Collectors.toList()));
        return basePrimes;
    }

    private List<Integer> checkPrimesInRangeByPrime(int start, int end, int prime) {
        List<Integer> primesInRange = new ArrayList<>();
        for (int i = start; i < end; i++) {
            if (isPrime(i, prime)) {
                primesInRange.add(i);
            }
        }
        return primesInRange;
    }

    public boolean isPrime(int number, int prime) {
        if (number < 2) return false;
        if (basePrimes.size() == 0) return false;
        if (number % prime == 0) return false;
        return true;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public ModifiedSequentialSearchAlgorithm getModifiedSequentialSearchAlgorithm() {
        return modifiedSequentialSearchAlgorithm;
    }

    public void setModifiedSequentialSearchAlgorithm(ModifiedSequentialSearchAlgorithm modifiedSequentialSearchAlgorithm) {
        this.modifiedSequentialSearchAlgorithm = modifiedSequentialSearchAlgorithm;
    }

    public List<Integer> getBasePrimes() {
        return basePrimes;
    }

    public void setBasePrimes(List<Integer> basePrimes) {
        this.basePrimes = basePrimes;
    }
}
