package com.task2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author MusaAl
 * @date 1/28/2020 : 11:23 AM
 */
public class Worker {

    private static final Stack<String> stack = new Stack<>();
    private static final int THREAD_COUNT = 5;

    public static void main(String[] args) {

        System.out.println("Start");

        StringBuffer sb = new StringBuffer();

        try (BufferedReader br = new BufferedReader(new FileReader("stackable_items.txt"));
             BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"))) {
            /**
             *   Pushing data to Stack
             */
            br.lines().forEach(stack::push);


            ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

            /**
             *   Reading data from Stack
             */
            Runnable r = () -> {
                if (!stack.empty()) {

                    for (int i = 1; i < 6; i++) {

                        if (stack.isEmpty()) break;
                        String str = stack.pop();
                        String line = String.format("%s\t%s\t%s\t%s\n", Thread.currentThread().getName(), System.currentTimeMillis(), str, i);
                        sb.append(line);
                        System.out.println("line -> " + line);

                    }

                }
            };


            while (!stack.isEmpty()) {
                executor.execute(r);
            }


            /**
             *   Read data to output file
             */
            while (true) {
                if (stack.empty()) {
                    System.out.println("stack is empty");
                    bw.write(sb.toString());
                    executor.shutdown();
                    break;
                }
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }

        System.out.println("Finish");
    }


}
