package com.crypt;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;


/**
 * Producer class which reads data from stackable_items.txt and writes it to LIFO data structure
 */
public class ReaderThread implements Runnable {

//    private final BlockingQueue<String> queue;
//
//    public ReaderThread(BlockingQueue<String> queue){
//        this.queue = queue;
//    }

    private final ThreadSafeNonBlockingStack<String> LIFO;

    public ReaderThread(ThreadSafeNonBlockingStack<String> LIFO){
        this.LIFO = LIFO;
    }


//    private final Deque<String> LIFO;
//
//    public ReaderThread(Deque<String> LIFO){
//        this.LIFO = LIFO;
//    }

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(2);

            List<String> inputData = ReaderWriterTool.read(ReaderWriterTool.inputFilePath);

            for(String data : inputData){
                LIFO.push(data);
                System.out.println(data);
//                LIFO.push(input);
//                System.out.println(input+" inserted to LIFO");
//                TimeUnit.SECONDS.sleep(1);
            }

            LIFO.setCompleted();
            System.out.println("Reader "+Thread.currentThread().getName()+" finished.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
