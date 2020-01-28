package com.crypt;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  Tool class designed for read & write to file
 */
public class ReaderWriterTool {

//	public static final String inputFilePath="/Users/musa/simbrella.com/src/main/resources/stackable_items.txt"; //MAC
//    public static final String outputFilePath="/Users/musa/simbrella.com/src/main/resources/output.txt"; //MAC

    public static final String inputFilePath="D:\\Trainings\\CodeExamples\\simbrella\\src\\main\\resources\\stackable_items.txt"; //MAC
    public static final String outputFilePath="D:\\Trainings\\CodeExamples\\simbrella\\src\\main\\resources\\output.txt"; //MAC

    public synchronized static List<String> read(String path){

        List<String> input = new ArrayList<>();
        // read the content from file
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace(); // in case of file not exist first time "output.txt" it will throw appropriate exception
        }
        return input;
    }

    public synchronized static void write(List<String> data){
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFilePath))) {
            for(String output : data){
                bufferedWriter.write(output+System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void writeOne(String data){
        List<String> fileContent = read(outputFilePath);
        fileContent.add(data+System.lineSeparator());
        write(fileContent);
    }
}
