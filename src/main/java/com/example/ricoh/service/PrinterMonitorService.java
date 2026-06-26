package com.example.ricoh.service;

import com.example.ricoh.snmp.*;

import org.springframework.stereotype.Service;

@Service
public class PrinterMonitorService {

    private final SnmpClient snmp;

    public PrinterMonitorService() throws Exception {
        this.snmp = new SnmpClient();
    }

    public String getModel(String ip) throws Exception {
        return snmp.get(ip, RicohOids.MODEL);
    }

    public String getSerial(String ip) throws Exception {
        return snmp.get(ip, RicohOids.SERIAL);
    }

    public String getStatus(String ip) throws Exception {
        return snmp.get(ip, RicohOids.DEVICE_STATUS);
    }

    public String getTonerBlack(String ip) throws Exception {
        return snmp.get(ip, RicohOids.TONER_BASE + "1");
    }

    
    
    /// ********* PRUEBAS 
    /// 
	public String getSysDescr(String ip) throws Exception {
		
		return snmp.get(ip, WindowsOids.TEST_WINDOWS);
	}

	public String getName(String ip) throws Exception {
		// TODO Auto-generated method stub
		return snmp.get(ip, WindowsOids.NAME);
	}
}
