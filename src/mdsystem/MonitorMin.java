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
public class MonitorMin extends Monitor{
    double threholdMin;
    public MonitorMin(String strID,String strName, String strUnit, String strInfo,double dMin)
    {
        super(strID,strName,strUnit, strInfo);
        threholdMin = dMin;
        type = Type.MIN;
    }
    public void setMax(double dMin)
    {
        threholdMin = dMin;
    }
    public double getMax()
    {
        return threholdMin; 
    }    
    @Override
    public boolean varify(double value)
    {        
        boolean ret = value >= threholdMin;
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
