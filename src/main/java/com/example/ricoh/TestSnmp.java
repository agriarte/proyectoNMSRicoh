package com.example.ricoh;

import com.example.ricoh.snmp.SnmpClient;

public class TestSnmp {
	 public static void main(String[] args) throws Exception {

	        SnmpClient client = new SnmpClient();

	        String ip = "127.0.0.1"; // o IP de un dispositivo real
	        String oid = "1.3.6.1.2.1.1.1.0"; // sysDescr.0

	        String result = client.get(ip, oid);

	        System.out.println("Resultado SNMP: " + result);
	    }
	}