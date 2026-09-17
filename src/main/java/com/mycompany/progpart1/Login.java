/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.progpart1;

/**
 *
 * @author user
 */
  
public class Login {
    
 String storedUsername, storedPassword;
 
    //Username check
    public static boolean checkUserName(String username) {
        if (username == null) {
            return false;
        }
        
        // underscore present AND length <= 5
        return username.contains("_") && username.length() <= 5;
    }

    //Password complexity check
    public static boolean checkPasswordComplexity(String password) {
        if (password == null){
            return false;
    }
    
     boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasDigit = password.matches(".*[0-9].*");
        boolean hasSpecial = password.matches(".*[^a-zA-Z0-9].*");
        boolean isLongEnough = password.length() >= 8;

        return hasUpper && hasDigit && hasSpecial && isLongEnough;
    }
    
    //Cell phone number check 
    public static boolean checkCellPhoneNumber(String cellPhoneNumber) {
        if (cellPhoneNumber == null){
            return false;
        }

        // start with + followed by digits, less than 14.
         return cellPhoneNumber.matches("^\\+[0-9]{1,3}[0-9]{1,10}$")
                && cellPhoneNumber.length() <= 14;
    }

    //Register user 
    public String registerUser(String username, String password, String southAfricanCell) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than 5 characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber(southAfricanCell)) {
            return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        }
        
        //store username and password
        storedUsername = username;
        storedPassword = password;
        
        return "Password successfully captured.";
    }

    // Login user 
    public boolean loginUser(String username, String password) {
        
          return username != null
                && password != null
                && username.equals(storedUsername)
                && password.equals(storedPassword);
    }

    // Return login status message 
    public String returnLoginStatus(boolean loginOk, String username) {
        if (loginOk) {
            // Split username by underscore 
            String[] parts = username.split("_", 2);
            String first = parts[0];
            String last = (parts.length > 1) ? parts[1] : "";

            return "Welcome " + first + ", " + last + "! it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }
}
