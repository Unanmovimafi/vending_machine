/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vending_machine;

/**
 *
 * @author lim
 */
public class Calculate {

    public double multiply(double num1, double num2) {
        return num1 * num2;
    }

    public double multiply(String num1, String num2) {
        return Double.parseDouble(num1) * Double.parseDouble(num2);
    }
    
    public double multiply(String num1, double num2) {
        return Double.parseDouble(num1) * num2;
    }
    
    public double multiply(double num1, String num2) {
        return num1 * Double.parseDouble(num2);
    }
    
}
