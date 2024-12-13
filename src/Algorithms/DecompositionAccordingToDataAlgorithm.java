package Algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DecompositionAccordingToDataAlgorithm {
    private int size;
    private ModifiedSequentialSearchAlgorithm modifiedSequentialSearchAlgorithm;
    private List<Integer> basePrimes;

    public DecompositionAccordingToDataAlgorithm(int size) {
        this.size = size;
        this.modifiedSequentialSearchAlgorithm = new ModifiedSequentialSearchAlgorithm(size);
        this.basePrimes = modifiedSequentialSearchAlgorithm.getBasePrimes();
    }

    public List<Integer> getAllPrimes(int end, int numThreads) {
        int start = (int)Math.sqrt(end) + 1;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<List<Integer>>> futures = new ArrayList<>();
        int rangeSize = (end - start + 1) / numThreads;

        for (int i = 0; i < numThreads; i++) {
            int rangeStart = start + i * rangeSize;
            int rangeEnd = (i == numThreads - 1) ? end : rangeStart + rangeSize;

            futures.add(executor.submit(() -> modifiedSequentialSearchAlgorithm.checkPrimesInRange(rangeStart, rangeEnd)));
        }
        List<Integer> foundPrimes = new ArrayList<>();
        for (Future<List<Integer>> future : futures){
            try {
                foundPrimes.addAll(future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
        basePrimes.addAll(foundPrimes);
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
