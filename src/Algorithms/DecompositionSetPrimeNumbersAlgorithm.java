package Algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class DecompositionSetPrimeNumbersAlgorithm {
    private int size;
    private ModifiedSequentialSearchAlgorithm modifiedSequentialSearchAlgorithm;
    private List<Integer> basePrimes;

    public DecompositionSetPrimeNumbersAlgorithm(int size) {
        this.size = size;
        this.modifiedSequentialSearchAlgorithm = new ModifiedSequentialSearchAlgorithm(size);
        this.basePrimes = modifiedSequentialSearchAlgorithm.getBasePrimes();
    }

    public List<Integer> getAllPrimes(int end, int numThreads) {
        int start = (int) Math.sqrt(end) + 1;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<List<Integer>>> futures = new ArrayList<>();
        List<Integer> primeNumbers = new ArrayList<>();
        int basePrimesSize = basePrimes.size() / numThreads;
        for (int i = 0; i < numThreads; i++) {
            int baseStart = i * basePrimesSize;
            int baseEnd = (i == numThreads - 1) ? basePrimes.size() - 1 : baseStart + basePrimesSize - 1;
            List<Integer> partBasePrimes = new ArrayList<>();
            for (int j = baseStart; j <= baseEnd; j++) {
                partBasePrimes.add(basePrimes.get(j));
            }
            ModifiedSequentialSearchAlgorithm threadAlgorithm = new ModifiedSequentialSearchAlgorithm(size);
            threadAlgorithm.setBasePrimes(partBasePrimes);
            futures.add(executor.submit(() -> threadAlgorithm.checkPrimesInRange(start, end)));
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
