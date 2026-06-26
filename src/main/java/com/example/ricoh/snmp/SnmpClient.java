package com.example.ricoh.snmp;

import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.springframework.stereotype.Component;


@Component
public class SnmpClient {

    private final Snmp snmp;

    public SnmpClient() throws Exception {
    	DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping();

        snmp = new Snmp(transport);

        transport.listen(); // 👈 IMPORTANTE en SNMP4J 3.x
    }

    public String get(String ip, String oid) throws Exception {

        CommunityTarget<UdpAddress> target = new CommunityTarget<>();
        target.setAddress((UdpAddress) GenericAddress.parse("udp:" + ip + "/161"));
        target.setCommunity(new OctetString("public"));
        target.setRetries(1);
        target.setTimeout(1200);
        target.setVersion(SnmpConstants.version2c);

        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(oid)));
        pdu.setType(PDU.GET);

        ResponseEvent<UdpAddress> response = snmp.send(pdu, target);

        if (response != null && response.getResponse() != null) {
            return response.getResponse().get(0).getVariable().toString();
        }

        return null;
    }

	public String getSysDescr(String ip) throws Exception {
		// TODO Auto-generated method stub
		return get(ip, "1.3.6.1.2.1.1.1.0");
	}
}