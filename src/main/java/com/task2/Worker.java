package com.task2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author MusaAl
 * @date 1/28/2020 : 11:23 AM
 */
public class Worker {

    private static final Stack<String> stack = new Stack<>();
    private static final int THREAD_COUNT = 5;

    public static void main(String[] args) {
        System.out.println("Start");

        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader("stackable_items.txt"));
             BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"))) {
            br.lines().forEach(stack::push);


            ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

            Runnable r = () -> {
                if (!stack.empty()) {

                    for(int i = 0; i<6; i++){
                        if(stack.isEmpty()) break;
                        String str = stack.pop();
                        String line = String.format("%s\t%s\t%s\t%s", Thread.currentThread().getName(), System.currentTimeMillis(), str, str.substring(4));
                        sb.append(line).append(System.lineSeparator());
                        System.out.println("line -> "+line);
                    }

                }
            };


            int size = stack.size();
            while(size-- > 0){
                executor.execute(r);
            }



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
