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
public class MonitorMaxMin extends Monitor{
    double threholdMin;
    double threholdMax;
    public MonitorMaxMin(String strID,String strName, String strUnit, String strInfo,double dMin, double dMax)
    {
        super(strID,strName,strUnit,strInfo);
        threholdMin = dMin;
        threholdMax = dMax;
        type = Type.MINMAX;
    }
    public void setMin(double dMin)
    {
        threholdMin = dMin;
    }
    public double getMin()
    {
        return threholdMin; 
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
        boolean ret = threholdMin <= value  && value <= threholdMax;
        setStatus(ret);
        return ret;
    }
        @Override
    public boolean varify(byte[] value)
    {      
 
        boolean ret = varify(ByteBuffer.wrap(value).getDouble());
        setStatus(ret);
        return ret;

    }
    
}
