/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdsystem;

import java.nio.ByteBuffer;
import mdsystem.Monitor;

/**
 *
 * @author freddie
 */
public class MonitorBoolean extends Monitor {

    public MonitorBoolean(String strID, String strName, String strUnit, String strInfo) {
        super(strID, strName, strUnit, strInfo);
        type = Type.YESNO;
    }

    @Override
    public boolean varify(byte[] value) {
        for (int i = 0; i < value.length; i++) {
            if (value[i] != 0) {
                setStatus(true);
                return true;
            }
        }
        setStatus(false);
        return false;
    }

    @Override
    public boolean varify(int value) {

        if (value != 0) {
            setStatus(true);
            return true;
        } else {
            setStatus(false);
            return false;
        }

    }

    @Override
    public String getDisplayStr(byte[] b) {
        for (int i = 0; i < b.length; i++) {
            if (b[i] != 0) {
                return String.format("Correct");
            }
        }
        return String.format("Warning");
    }

}
