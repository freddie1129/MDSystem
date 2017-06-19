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
    private String paraID;              //the tag or ID of monitor parameter
    private String paraName;            //the name of monitor parameter
    private String paraInfo;            //the detail infromation of monitor parameter, like location
    private String paraUnit;
    private boolean bStatus;
    public enum Type { MIN,MAX,MINMAX,ENUM,YESNO}
    public Type type;
    
    public Monitor(String strID,String strName, String strUnit, String strInfo)
    {
        paraID = strID;
        paraName = strName;
        paraInfo = strInfo;
        paraUnit = strUnit;
        bStatus = true;
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

    public void setStatus(boolean b)
    {
        if (bStatus) // if the value has been not in the threshold at the last time, keep the status 
                        // no matter whether it is right at this time
            bStatus = b;

    }
    
    public void clearError()
    {
        bStatus = true;
    }
    
    public boolean getStatus()
    {
        return bStatus;
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


