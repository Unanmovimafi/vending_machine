/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vending_machine;

import java.io.*;
import java.util.*;
/**
 *
 * @author lim
 */

public class Class_VM {

    public String[] getProductRecord(int line_num) {
        try (BufferedReader reader = new BufferedReader(new FileReader("product\\product.txt"))) {
            String line;
            int count = -1;
            while ((line = reader.readLine()) != null) {
                String[] lineArray = line.trim().split(",");
                count = count + 1;
                if (count == line_num) {
                    // reutrn the line of the line_num in text file
                    return lineArray;
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return null; // Patient record not found
    }
    
    public List<String> getAllProductRecord() {
        try {
            
            List<String> lines = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader("product\\product.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                //Read and add all the lines in text file to variable line
                lines.add(line);
            }
            reader.close();
            return lines;
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    } 
}

