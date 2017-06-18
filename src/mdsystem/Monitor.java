/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdsystem;

import java.nio.ByteBuffer;

/**
 *
 * @author freddie
 */
public class Monitor {
    String paraID;              //the tag or ID of monitor parameter
    String paraName;            //the name of monitor parameter
    String paraInfo;            //the detail infromation of monitor parameter, like location
    String paraUnit;
    public Monitor(String strID,String strName, String strUnit, String strInfo)
    {
        paraID = strID;
        paraName = strName;
        paraInfo = strInfo;
        paraUnit = strUnit;
    }
    public void setID(String strID)
    {
        paraID = strID;
    }
    public String getID()
    {
        return paraID;
    }
    public void setName(String strName)
    {
        paraName = strName;
    }
    public String getName()
    {
        return paraName;
    }
    public void setInfo(String strInfo)
    {
        paraInfo = strInfo;
    }
    public String getInfo()
    {
        return paraInfo;
    }
    public boolean varify(byte[] value)
    {        
        System.out.println("Invalid Result.\n");
        return true;
    }
    
    public boolean varify(double value)
    {        
        System.out.println("Invalid Result.\n");
        return true;
    }
    public boolean varify(int value)
    {        
        System.out.println("Invalid Result.\n");
        return true;
    }
    public boolean varify(boolean value)
    {
        System.out.println("Invalid Result.\n");
        return value;        
    }
    public String getDisplayStr(byte [] b)
    {
        return String.valueOf(ByteBuffer.wrap(b,0,8).getDouble());
    }
    
}


