/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdsystem;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author freddie
 */
public class LogItem {

        private final SimpleStringProperty strTime;
        private final SimpleStringProperty strComment;

        public LogItem(
                String strTime,
                String strComment
        ) {
            this.strTime = new SimpleStringProperty(strTime);
            this.strComment = new SimpleStringProperty(strComment);
        }

        public String getStrTime() {
            return strTime.get();
        }

        public String getStrComment() {
            return strComment.get();
        }
    }
