/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author freddie
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdsystem;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author freddie
 */
public class Staff {

    public static int num = 0;
    private final SimpleIntegerProperty strNum;
    private final SimpleStringProperty strName;
    private final SimpleStringProperty strStaffID;
    private final SimpleStringProperty strFirstName;
    private final SimpleStringProperty strLastName;
    private final SimpleStringProperty strEmail;
    private final SimpleStringProperty strPassword;


        public Staff(
                String strName,
                String strPassword,
                String strStaffID,
                String strFirstName,
                String strLastName,
                String strEmail
                ) {
            this.strNum = new SimpleIntegerProperty(num++);
            this.strName = new SimpleStringProperty(strName);
            this.strStaffID = new SimpleStringProperty(strStaffID);
            this.strFirstName = new SimpleStringProperty(strFirstName);
            this.strLastName = new SimpleStringProperty(strLastName);            
            this.strEmail = new SimpleStringProperty(strEmail);
            this.strPassword = new SimpleStringProperty(strPassword);


        }

        
//            private final SimpleIntegerProperty strNum;
//    private final SimpleStringProperty strName;
//    private final SimpleStringProperty strStaffID;
//    private final SimpleStringProperty strFirstName;
//    private final SimpleStringProperty strLastName;
//    private final SimpleStringProperty strEmail;
        
        public int getStrNum() {
            return strNum.get();
        }
                
        public String getStrName() {
            return strName.get();
        }

        public String getStrEmail() {
            return strEmail.get();
        }
        
                public String getStrStaffID() {
            return strStaffID.get();
        }

        public String getStrFirstName() {
            return strFirstName.get();
        }
        
                public String getStrLastName() {
            return strLastName.get();
        }
                
        public String getStrPassword() {
            return strPassword.get();
        }



 

        //set value

        public void setName(String name) {
            strName.set(name);
        }

        public void setEmail(String email) {
            strEmail.set(email);
        }
                public void setStaffID(String staffID) {
            strStaffID.set(staffID);
        }

        public void setFirstName(String fName) {
            strFirstName.set(fName);
        }
                public void setLastName(String lName) {
            strLastName.set(lName);
        }
         
        public void setPassword(String password) {
            strPassword.set(password);
        }
}



