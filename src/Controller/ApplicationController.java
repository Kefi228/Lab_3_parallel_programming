package Controller;

import Algorithms.*;

import java.util.List;
import java.util.Scanner;

public class ApplicationController {
    public static void run() {
        Scanner scanner = new Scanner(System.in);
        int size = 0, numThreads = 0, command;

        do {

            System.out.println("Выберите алгоритм для нахождения простых чисел:");
            System.out.println("1-Решето Эратосфена");
            System.out.println("2-Модифицированный последовательный алгоритм поиска");
            System.out.println("3-Параллельный алгоритм №1: декомпозиция по данным");
            System.out.println("4-Параллельный алгоритм №2: декомпозиция набора простых чисел");
            System.out.println("5-Параллельный алгоритм №3: применение пула потоков");
            System.out.println("6-Параллельный алгоритм №4: последовательный перебор простых чисел");

            command = scanner.nextInt();
            if (command == 2 || command == 1 || command == 5) {
                System.out.println("Введите количество элементов:");
                size = scanner.nextInt();
            } else if (command == 4 || command == 3 || command == 6) {
                System.out.println("Введите количество элементов:");
                size = scanner.nextInt();
                System.out.println("Введите количество потоков:");
                numThreads = scanner.nextInt();
            }
            long startTime, endTime;
            List<Integer> primes;
            double sequentTime = 0, parallelTime = 0;

            switch (command) {
                case 1:
                    SieveEratosphenAlgorithm sieveEratosphen = new SieveEratosphenAlgorithm(size);
                    sieveEratosphen.initializeSieve();
                    startTime = System.nanoTime();
                    primes = sieveEratosphen.getAllPrimes();
                    endTime = System.nanoTime();
                    sequentTime = endTime - startTime;
                    System.out.println("Результат: " + primes);
                    System.out.println("Время выполнения: " + sequentTime + " нс");
                    break;
                case 2:
                    ModifiedSequentialSearchAlgorithm modifiedSequentialSearch = new ModifiedSequentialSearchAlgorithm(size);
                    startTime = System.nanoTime();
                    primes = modifiedSequentialSearch.getAllPrimes();
                    endTime = System.nanoTime();
                    sequentTime = endTime - startTime;
                    System.out.println("Результат: " + primes);
                    System.out.println("Время выполнения: " + sequentTime + " нс");
                    break;
                case 3:
                    DecompositionAccordingToDataAlgorithm decompositionAccordingToData = new DecompositionAccordingToDataAlgorithm(size);
                    startTime = System.nanoTime();
                    decompositionAccordingToData.getAllPrimes(size,1);
                    endTime = System.nanoTime();
                    sequentTime = endTime - startTime;
                    startTime = System.nanoTime();
                    primes = decompositionAccordingToData.getAllPrimes(size, numThreads);
                    endTime = System.nanoTime();
                    parallelTime = endTime - startTime;
                    System.out.println("Результат: " + primes);
                    System.out.println("Время выполнения: " + parallelTime + " нс");
                    System.out.println("Ускорение: " + sequentTime/parallelTime + "нс");
                    System.out.println("Эффективность:" + (sequentTime/parallelTime)/numThreads + "нс");
                    break;
                case 4:
                    DecompositionSetPrimeNumbersAlgorithm decompositionSetPrimeNumbers = new DecompositionSetPrimeNumbersAlgorithm(size);
                    startTime = System.nanoTime();
                    decompositionSetPrimeNumbers.getAllPrimes(size, numThreads);
                    endTime = System.nanoTime();
                    sequentTime = endTime - startTime;
                    startTime = System.nanoTime();
                    primes = decompositionSetPrimeNumbers.getAllPrimes(size, numThreads);
                    endTime = System.nanoTime();
                    parallelTime = endTime - startTime;
                    System.out.println("Результат: " + primes);
                    System.out.println("Время выполнения: " + parallelTime + " нс");
                    System.out.println("Ускорение: " + sequentTime/parallelTime + "нс");
                    System.out.println("Эффективность:" + (sequentTime/parallelTime)/numThreads + "нс");
                    break;
                case 5:
                    ThreadPoolAlgorithm threadPool = new ThreadPoolAlgorithm(size);
                    startTime = System.nanoTime();
                    primes = threadPool.getAllPrimes(size);
                    endTime = System.nanoTime();
                    parallelTime = endTime - startTime;
                    System.out.println("Результат: " + primes);
                    System.out.println("Время выполнения: " + parallelTime + " нс");
                    break;
                case 6:
                    SequentialPrimeCheckAlgorithm sequentialPrimeCheck = new SequentialPrimeCheckAlgorithm(size);
                    startTime = System.nanoTime();
                    sequentialPrimeCheck.getAllPrimes(numThreads);
                    endTime = System.nanoTime();
                    sequentTime = endTime - startTime;
                    startTime = System.nanoTime();
                    primes = sequentialPrimeCheck.getAllPrimes(numThreads);
                    endTime = System.nanoTime();
                    parallelTime = endTime - startTime;
                    System.out.println("Результат: " + primes);
                    System.out.println("Время выполнения: " + parallelTime + " нс");
                    System.out.println("Ускорение: " + sequentTime/parallelTime + "нс");
                    System.out.println("Эффективность:" + (sequentTime/parallelTime)/numThreads + "нс");
                    break;
                default:
                    System.out.println("Неверный формат ввода");
                    break;
            }
        }
        while (command <= 6 && command >= 1);
    }
}
