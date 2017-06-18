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
public class MonitorMax extends Monitor {
    
    double threholdMax;
    public MonitorMax(String strID,String strName, String strUnit, String strInfo,double dMax)
    {
        super(strID,strName,strUnit, strInfo);
        threholdMax = dMax;
    }
    public void setMax(double dMax)
    {
        threholdMax = dMax;
    }
    public double getMax()
    {
        return threholdMax; 
    }   
    
    @Override
    public boolean varify(double value)
    {        
        return value <= threholdMax;
    }
    
    @Override
    public boolean varify(byte[] value)
    {      
 
        return varify(ByteBuffer.wrap(value).getDouble());

    }
}
