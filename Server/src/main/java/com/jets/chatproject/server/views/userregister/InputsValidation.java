/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.views.userregister;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Hadeer
 */
public class InputsValidation {
    RegisterController controller ;
    final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public InputsValidation(RegisterController controller) {
        this.controller = controller;
        
    }
    
     public boolean checkPhoneNumber(){
        boolean isCorrect = true;
        Pattern pattern = Pattern.compile("\\d{11}");
        Matcher matcher = pattern.matcher(controller.phoneNumber);
        if (!matcher.matches()) 
        {
            isCorrect =false;
        }
        return isCorrect;
    }
     
      public boolean validatePassword(){
        boolean isCorrect = true;
        if (!controller.password.equals(controller.verifyPassword)) { 
            isCorrect=false;
        }
        return isCorrect;
    } 
    
       public boolean checkEmail(){
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(controller.email);
        return matcher.find();
    }
     
      
      
    public String checkBeforeLeave(){
       String checkState = "";
       if(!validatePassword()){
           checkState="*Mismatch in passwords";
        }
       if(!checkPhoneNumber()){
           checkState = checkState+ "\n*Invalid Phone number";
       }
       if(!checkEmail()){
           checkState = checkState+ "\n*Invalid email";
       }
       
       if(controller.name.equals("")||controller.email.equals("")||controller.password.equals("")||controller.country.equals("")||controller.phoneNumber.equals("")||controller.gender.equals("")||controller.birthdate==null)
       {
           checkState = checkState+ "\n*Missing data";
       }
       return checkState;
    
    }
      

    
    
}
