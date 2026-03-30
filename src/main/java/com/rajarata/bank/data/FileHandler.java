package com.rajarata.bank.data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;



public class FileHandler {

private static final String DATA_DIR = "src/main/resources/data/";

public static void  saveToFile(String filename,List<String> data){
    try(PrintWriter writer = new PrintWriter(new File(DATA_DIR + filename))) {
        for(String line : data){
            writer.println(line);
        }
        System.out.println("Data saved to file: " + filename);
    } catch (IOException e) {
        System.err.println("Error saving data to file: " + e.getMessage());
    
    }
}

public static List<String> readFromFile(String filename){
   List<String> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_DIR + filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line);
            }
            System.out.println("Data read from file: " + filename);
        } catch (IOException e) {
            System.err.println("Error reading data from file: " + e.getMessage());
        }
        return data;
}


}


