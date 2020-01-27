package com.crypt;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;


/**
 * Consumer class which reads data from LIFO and writes to output.txt
 */
public class WriterThread implements Runnable {

    private static final String TAB_SYMBOL = " => ";

//    private final BlockingQueue<String> queue;
//
//    public WriterThread(BlockingQueue<String> queue){
//        this.queue = queue;
//    }

    private final ThreadSafeNonBlockingStack<String> LIFO;

    public WriterThread(ThreadSafeNonBlockingStack<String> LIFO){
        this.LIFO = LIFO;
    }

//    private final Deque<String> LIFO;
//
//    public WriterThread(Deque<String> LIFO){
//        this.LIFO = LIFO;
//    }

    @Override
    public void run() {

        try {

                TimeUnit.SECONDS.sleep(1);

                while(true){

                    while(LIFO.isCompleted()){
                        String result = LIFO.pop();

                        if(result == null){
                            System.out.println("I must stop");
                            return; // poison pill
                        }

                        StringBuilder stringBuilder = new StringBuilder(Thread.currentThread().getName());
                        stringBuilder.append(TAB_SYMBOL)
                                .append(System.currentTimeMillis())
                                .append(TAB_SYMBOL)
                                .append(result);

                        ReaderWriterTool.writeOne(stringBuilder.toString());

//                            .append("the number of elements read by the thread");

                    }


                }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
