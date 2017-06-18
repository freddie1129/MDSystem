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
    public MonitorBoolean(String strID,String strName, String strUnit, String strInfo)
    {
        super(strID,strName,strUnit, strInfo);
    }
    
    @Override
    public boolean varify(byte[] value)
    {   
        for (int i = 0; i < value.length; i++)
        {
            if (value[i] != 0)
            {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String getDisplayStr(byte [] b)
    {
        for (int i = 0; i < b.length; i++)
        {
            if (b[i] != 0)
                return String.format("Normal");
        }
        return String.format("DisNormal");
    }
    
}
