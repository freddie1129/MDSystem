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
    public class MonitorVariable {

        public static int num = 1;
        private final SimpleStringProperty ColID;
        private final SimpleIntegerProperty ColNum;
        private final SimpleStringProperty ColName;
        private final SimpleStringProperty ColValue;
        private final SimpleStringProperty ColMax;
        private final SimpleStringProperty ColMin;
        private final SimpleStringProperty ColEnum;        
        private final SimpleStringProperty ColUnit;
        private final SimpleStringProperty ColInfo;
        public MonitorVariable(
                String ColID,
                String ColName,
                String ColValue,
                String ColMin,
                String ColMax,
                String ColEnum,
                String ColUnit,
                String ColInfo) {
            this.ColNum = new SimpleIntegerProperty(num++);
            this.ColID = new SimpleStringProperty(ColID);
            this.ColName = new SimpleStringProperty(ColName);
            this.ColValue = new SimpleStringProperty(ColValue);
            this.ColMax = new SimpleStringProperty(ColMax);
            this.ColMin = new SimpleStringProperty(ColMin);
            this.ColEnum = new SimpleStringProperty(ColEnum);
            this.ColUnit = new SimpleStringProperty(ColUnit);
            this.ColInfo = new SimpleStringProperty(ColInfo);

        }

        public int getColNum() {
            return ColNum.get();
        }

        public String getColID() {
            return ColID.get();
        }

        public String getColName() {
            return ColName.get();
        }

        public String getColValue() {
            return ColValue.get();
        }

        public String getColMax() {
            return ColMax.get();
        }

        public String getColMin() {
            return ColMin.get();
        }
        
        public String getColEnum() {
            return ColEnum.get();
        }

        public String getColUnit() {
            return ColUnit.get();
        }

        public String getColInfo() {
            return ColInfo.get();
        }


        //set value

        public void setColID(String fID) {
            ColID.set(fID);
        }

        public void setColName(String fName) {
            ColName.set(fName);
        }

        public void setColValue(String fValue) {
            ColValue.set(fValue);
        }

        public void setColMax(String fMax) {
            ColMax.set(fMax);
        }

        public void setColMin(String fMin) {
            ColMin.set(fMin);
        }
        
        public void setColEnum(String strEnum) {
            ColEnum.set(strEnum);
        }

        public void setColUnit(String fUnit) {
            ColUnit.set(fUnit);
        }

        public void setColInfo(String fInfo) {
            ColInfo.set(fInfo);
        }
    }
