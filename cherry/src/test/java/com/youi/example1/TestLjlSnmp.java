package com.youi.example1;

import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.*;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

/**
 * Created by jinliang on 2016/10/12.
 */
public class TestLjlSnmp {
    public static void main(String args[]) throws Exception{
        Address targetAddress = GenericAddress.parse("udp:www.tongpinlife.com/161");
        TransportMapping transport = new DefaultUdpTransportMapping();
        Snmp  snmp = new Snmp(transport);
        USM usm = new USM(SecurityProtocols.getInstance(),
                new OctetString(MPv3.createLocalEngineID()), 0);
        SecurityModels.getInstance().addSecurityModel(usm);
        transport.listen();

        // add user to the USM
        snmp.getUSM().addUser(new OctetString("MD5DES"),
                new UsmUser(new OctetString("MD5DES"),
                        AuthMD5.ID,
                        new OctetString("MD5DESUserAuthPassword"),
                        PrivDES.ID,
                        new OctetString("MD5DESUserPrivPassword")));
        // create the target
        UserTarget target = new UserTarget();
        target.setAddress(targetAddress);
        target.setRetries(1);
        target.setTimeout(5000);
        target.setVersion(SnmpConstants.version3);
        target.setSecurityLevel(SecurityLevel.AUTH_PRIV);
        target.setSecurityName(new OctetString("MD5DES"));

        // create the PDU
        PDU pdu = new ScopedPDU();
        pdu.add(new VariableBinding(new OID("1.3.6")));
        pdu.setType(PDU.GETNEXT);

        // send the PDU
        ResponseEvent response = snmp.send(pdu, target);
        // extract the response PDU (could be null if timed out)
        PDU responsePDU = response.getResponse();
        System.out.println(responsePDU);
        // extract the address used by the agent to send the response:
        Address peerAddress = response.getPeerAddress();
       // System.out.println(peerAddress);
    }
}
