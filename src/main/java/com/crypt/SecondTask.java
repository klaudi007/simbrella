package com.crypt;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class SecondTask {

    private static final int THREAD_COUNT = 5;

    public static void main(String[] args){

        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        ThreadSafeNonBlockingStack<String> LIFO= new ThreadSafeNonBlockingStack<>();
//        Deque<String> LIFO = new ArrayDeque<>();

        ReaderThread reader = new ReaderThread(LIFO);
        Thread readerThread = new Thread(reader);
        readerThread.start();



        for(int i=0; i < THREAD_COUNT; i++){
            Runnable worker = new WriterThread(LIFO);
            new Thread(worker).start();
        }


//        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
//
//        Runnable worker = new WriterThread(LIFO);
//        executorService.execute(worker);
//
//        executorService.shutdown();
//        while (!executorService.isTerminated()) {
//        }


//        System.out.println("Finished all threads");
    }

}
