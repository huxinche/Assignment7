package edu.neu.info5100;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SumValue {

    private static int SUM_VALUE = 0;
    // use 4 thread to run the sum value, auto run, not the manual run.
    private static int count = 4;
    private static CountDownLatch countDownLatch;
    private static ExecutorService executorService;

    SumValue(int count) {
        SumValue.count = count;
        executorService = Executors.newFixedThreadPool(count);
        countDownLatch = new CountDownLatch(count);
    }

    /*generate array of random numbers*/
    static void generateRandomArray(int[] arr) {

        for (int i = 0; i < 4000000; i++)
            arr[i] = (int) (Math.random() * 100);
    }

    /*get sum of an array using 4 threads*/
    static long sum(int[] arr) {
        int size = arr.length;
        int batch = size / count + 1;
        for (int i = 0; i < count; i++) {
            int end;
            int start;
            start = i * batch;
            end = Math.min(start + batch, size);
            executorService.execute(new Runner(arr, start, end));
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        return SUM_VALUE;
    }

    public static void main(String[] args) {

        int[] arr = new int[4000000];
        generateRandomArray(arr);
        long sum = sum(arr);
        System.out.println("Sum: " + sum);

    }

    static class Runner implements Runnable {

        private final int[] arr;
        private final int startIdx;
        private final int endIdx;

        Runner(int[] arr, int startIdx, int endIdx) {
            this.arr = arr;
            this.startIdx = startIdx;
            this.endIdx = endIdx;
        }

        @Override
        public void run() {
            int sum = 0;
            for (int i = startIdx; i < endIdx; i++) {
                sum += arr[i];
            }
            SUM_VALUE += sum;
            countDownLatch.countDown();

        }
    }
}