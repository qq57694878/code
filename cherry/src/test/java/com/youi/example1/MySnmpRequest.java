package com.youi.example1;

import com.youi.business.common.dao.SysOrgInfoDao;
import com.youi.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.*;
import org.snmp4j.asn1.BER;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.log.LogFactory;
import org.snmp4j.log.LogLevel;
import org.snmp4j.mp.*;
import org.snmp4j.security.*;
import org.snmp4j.smi.*;

import org.snmp4j.transport.AbstractTransportMapping;
import org.snmp4j.transport.DefaultTcpTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.transport.TLSTM;
import org.snmp4j.util.*;
import org.snmp4j.version.VersionInfo;

import java.io.*;
import java.util.*;

/**
 * Created by jinliang on 2016/10/15.
 */
public class MySnmpRequest  implements CommandResponder, PDUFactory {
    private static Logger logger = LoggerFactory.getLogger(MySnmpRequest.class);
    // initialize Java logging
    static {

        BER.setCheckSequenceLength(false);

    }


    public static final int DEFAULT = 0;
    public static final int WALK = 1;
    public static final int LISTEN = 2;
    public static final int TABLE = 3;
    public static final int CVS_TABLE = 4;
    public static final int TIME_BASED_CVS_TABLE = 5;
    public static final int SNAPSHOT_CREATION = 6;
    public static final int SNAPSHOT_DUMP = 7;

    Target target;
    Address address;
    OID authProtocol =AuthMD5.ID;
    OID privProtocol;
    OctetString privPassphrase;
    OctetString authPassphrase;
    OctetString community = new OctetString("public");
    OctetString authoritativeEngineID;
    OctetString contextEngineID;
    OctetString contextName = new OctetString();
    OctetString securityName = new OctetString();
    OctetString localEngineID = new OctetString(MPv3.createLocalEngineID());

    TimeTicks sysUpTime = new TimeTicks(0);
    OID trapOID = SnmpConstants.coldStart;

    PDUv1 v1TrapPDU = new PDUv1();

    int version = SnmpConstants.version2c;
    int engineBootCount = 0;
    int retries = 1;
    int timeout = 20000;
    int pduType = PDU.GETNEXT;
    int maxRepetitions = 10;
    int nonRepeaters = 0;
    int maxSizeResponsePDU = 65535;
    Vector<VariableBinding> vbs = new Vector<VariableBinding>();
    File snapshotFile;


    int numDispatcherThreads = 2;

    boolean useDenseTableOperation = false;

    // table options
    OID lowerBoundIndex, upperBoundIndex;


    public MySnmpRequest(MySnmpRequestConfig config) {
        // Set the default counter listener to return proper USM and MP error
        // counters.
        CounterSupport.getInstance().addCounterListener(new DefaultCounterListener());

        vbs.add(new VariableBinding(new OID("1.3.6")));
        parseConfig(config);

    }
    private void parseConfig(MySnmpRequestConfig config) {
        if (!StringUtils.isEmpty(config.getAuthProtocol())) {
            String s = config.getAuthProtocol();
            if (s.equals("MD5")) {
                authProtocol = AuthMD5.ID;
            } else if (s.equals("SHA")) {
                authProtocol = AuthSHA.ID;
            } else if (s.equals("SHA256")) {
                authProtocol = AuthHMAC192SHA256.ID;
            } else if (s.equals("SHA512")) {
                authProtocol = AuthHMAC384SHA512.ID;
            } else {
                throw new IllegalArgumentException("Authentication protocol unsupported: " + s);
            }
        }
        if (!StringUtils.isEmpty(config.getAddress())) {
            address = getAddress(config.getAddress());
        }
        if (config.getVariableBinding() != null && config.getVariableBinding().size() > 0) {
            Vector<VariableBinding> vbs = getVariableBindings(config.getVariableBinding());
            //  checkTrapVariables(vbs);
            if (vbs.size() > 0) {
                this.vbs = vbs;
            }
        }
        if (!StringUtils.isEmpty(config.getPduType())) {
            pduType = PDU.getTypeFromString(config.getPduType());
        }

        if (!StringUtils.isEmpty(config.getAuthPassphrase())) {
            authPassphrase = createOctetString(config.getAuthPassphrase());
        }
        if (!StringUtils.isEmpty(config.getPrivPassphrase())) {
            privPassphrase = createOctetString(config.getPrivPassphrase());
        }
        if (!StringUtils.isEmpty(config.getCommunity())) {
            community = createOctetString(config.getCommunity());
        }
        if (!StringUtils.isEmpty(config.getCommunity())) {
            community = createOctetString(config.getCommunity());
        }
        if (!StringUtils.isEmpty(config.getEngineBootCount())) {
            engineBootCount = Math.max(Integer.parseInt(config.getEngineBootCount()), 0);
        }
        if (!StringUtils.isEmpty(config.getLocalEngineID())) {
            localEngineID = createOctetString(config.getLocalEngineID());
        }
        if (!StringUtils.isEmpty(config.getAuthoritativeEngineID())) {
            authoritativeEngineID = createOctetString(config.getAuthoritativeEngineID());
        }
        if (!StringUtils.isEmpty(config.getContextEngineID())) {
            contextEngineID = createOctetString(config.getContextEngineID());
        }
        if (!StringUtils.isEmpty(config.getContextName())) {
            contextName = createOctetString(config.getContextName());
        }
        if (!StringUtils.isEmpty(config.getMaxRepetitions())) {
            maxSizeResponsePDU = Integer.parseInt(config.getMaxRepetitions());
        }
        if (!StringUtils.isEmpty(config.getRetries())) {
            retries = Integer.parseInt(config.getRetries());
        }
        if (!StringUtils.isEmpty(config.getTimeout())) {
            timeout = Integer.parseInt(config.getTimeout());
        }
        if (!StringUtils.isEmpty(config.getSecurityName())) {
            securityName = createOctetString(config.getSecurityName());
        }
        if (!StringUtils.isEmpty(config.getMaxRepetitions())) {
            maxRepetitions = Integer.parseInt(config.getMaxRepetitions());
        }
        if (!StringUtils.isEmpty(config.getNonRepeaters())) {
            nonRepeaters = Integer.parseInt(config.getNonRepeaters());
        }
        if (!StringUtils.isEmpty(config.getV1TrapPDUEnterprise())) {
            v1TrapPDU.setEnterprise(new OID(config.getV1TrapPDUEnterprise()));
        }
        if (!StringUtils.isEmpty(config.getV1TrapPDUEnterprise())) {
            trapOID = new OID(config.getTrapOID());
        }
        if (!StringUtils.isEmpty(config.getV1TrapPDUGenericTrap())) {
            v1TrapPDU.setGenericTrap(Integer.parseInt(config.getV1TrapPDUGenericTrap()));
        }
        if (!StringUtils.isEmpty(config.getV1TrapPDUSpecificTrap())) {
            v1TrapPDU.setSpecificTrap(Integer.parseInt(config.getV1TrapPDUSpecificTrap()));
        }
        if (!StringUtils.isEmpty(config.getV1TrapPDUAgentAddress())) {
            v1TrapPDU.setAgentAddress(new IpAddress(config.getV1TrapPDUAgentAddress()));
        }
        if (!StringUtils.isEmpty(config.getSysUpTime())) {
            String upTime = config.getV1TrapPDUTimestamp();
            v1TrapPDU.setTimestamp(Long.parseLong(upTime));
            sysUpTime.setValue(Long.parseLong(upTime));
        }
        if (!StringUtils.isEmpty(config.getSnapshotFile())) {
            snapshotFile = new File(config.getSnapshotFile());
        }
        if (!StringUtils.isEmpty(config.getLowerBoundIndex())) {
            lowerBoundIndex = new OID(config.getLowerBoundIndex());
        }
        if (!StringUtils.isEmpty(config.getUpperBoundIndex())) {
            upperBoundIndex = new OID(config.getUpperBoundIndex());
        }
        if (!StringUtils.isEmpty(config.getVersion())) {
            String v = config.getVersion();
            if (v.equals("1")) {
                version = SnmpConstants.version1;
            } else if (v.equals("2c")) {
                version = SnmpConstants.version2c;
            } else if (v.equals("3")) {
                version = SnmpConstants.version3;
            } else {
                throw new IllegalArgumentException("Version " + v + " not supported");
            }
        }
        if (!StringUtils.isEmpty(config.getPrivProtocol())) {
            String s = config.getPrivProtocol();
            if (s.equals("DES")) {
                privProtocol = PrivDES.ID;
            } else if ((s.equals("AES128")) || (s.equals("AES"))) {
                privProtocol = PrivAES128.ID;
            } else if (s.equals("AES192")) {
                privProtocol = PrivAES192.ID;
            } else if (s.equals("AES256")) {
                privProtocol = PrivAES256.ID;
            } else if ((s.equals("3DES") || s.equalsIgnoreCase("DESEDE"))) {
                privProtocol = Priv3DES.ID;
            } else {
                throw new IllegalArgumentException("Privacy protocol " + s +
                        " not supported");
            }
        }
    }



    private static Vector<VariableBinding> getVariableBindings(List<String> args) {

        Vector<VariableBinding> v = new Vector<VariableBinding>(args.size());
        for (int i=0; i<args.size(); i++) {
            String oid = args.get(i);
            char type = 'i';
            String value = null;
            int equal = oid.indexOf("={");
            if (equal > 0) {
                oid = args.get(i).substring(0, equal);
                type = args.get(i).charAt(equal+2);
                value = args.get(i).substring(args.get(i).indexOf('}')+1);
            }
            else if (oid.indexOf('-') > 0) {
                StringTokenizer st = new StringTokenizer(oid, "-");
                if (st.countTokens() != 2) {
                    throw new IllegalArgumentException("Illegal OID range specified: '"+
                            oid);
                }
                oid = st.nextToken();
                VariableBinding vbLower = new VariableBinding(new OID(oid));
                v.add(vbLower);
                long last = Long.parseLong(st.nextToken());
                long first = vbLower.getOid().lastUnsigned();
                for (long k=first+1; k<=last; k++) {
                    OID nextOID = new OID(vbLower.getOid().getValue(), 0,
                            vbLower.getOid().size()-1);
                    nextOID.appendUnsigned(k);
                    VariableBinding next = new VariableBinding(nextOID);
                    v.add(next);
                }
                continue;
            }
            VariableBinding vb = new VariableBinding(new OID(oid));
            if (value != null) {
                Variable variable;
                switch (type) {
                    case 'i':
                        variable = new Integer32(Integer.parseInt(value));
                        break;
                    case 'u':
                        variable = new UnsignedInteger32(Long.parseLong(value));
                        break;
                    case 's':
                        variable = new OctetString(value);
                        break;
                    case 'x':
                        variable = OctetString.fromString(value, ':', 16);
                        break;
                    case 'd':
                        variable = OctetString.fromString(value, '.', 10);
                        break;
                    case 'b':
                        variable = OctetString.fromString(value, ' ', 2);
                        break;
                    case 'n':
                        variable = new Null();
                        break;
                    case 'o':
                        variable = new OID(value);
                        break;
                    case 't':
                        variable = new TimeTicks(Long.parseLong(value));
                        break;
                    case 'a':
                        variable = new IpAddress(value);
                        break;
                    default:
                        throw new IllegalArgumentException("Variable type "+type+
                                " not supported");
                }
                vb.setVariable(variable);
            }
            v.add(vb);
        }
        return v;
    }

    private static Address getAddress(String transportAddress) {
        String transport = "udp";
        int colon = transportAddress.indexOf(':');
        if (colon > 0) {
            transport = transportAddress.substring(0, colon);
            transportAddress = transportAddress.substring(colon+1);
        }
        // set default port
        if (transportAddress.indexOf('/') < 0) {
            transportAddress += "/161";
        }
        if (transport.equalsIgnoreCase("udp")) {
            return new UdpAddress(transportAddress);
        }
        else if (transport.equalsIgnoreCase("tcp")) {
            return new TcpAddress(transportAddress);
        }
        else if (transport.equalsIgnoreCase("tls")) {
            return new TlsAddress(transportAddress);
        }
        throw new IllegalArgumentException("Unknown transport "+transport);
    }

    private static String nextOption(String[] args, int position) {
        if (position+1 >= args.length) {
            throw new IllegalArgumentException("Missing option value for " +
                    args[position]);
        }
        return args[position+1];
    }

    private static OctetString createOctetString(String s) {
        OctetString octetString;
        if (s.startsWith("0x")) {
            octetString = OctetString.fromHexString(s.substring(2), ':');
        }
        else {
            octetString = new OctetString(s);
        }
        return octetString;
    }





    private void checkTrapVariables(Vector<VariableBinding> vbs) {
        if ((pduType == PDU.INFORM) ||
                (pduType == PDU.TRAP)) {
            if ((vbs.size() == 0) ||
                    ((vbs.size() > 1) &&
                            (!(vbs.get(0)).getOid().equals(SnmpConstants.
                                    sysUpTime)))) {
                vbs.add(0, new VariableBinding(SnmpConstants.sysUpTime, sysUpTime));
            }
            if ((vbs.size() == 1) ||
                    ((vbs.size() > 2) &&
                            (!(vbs.get(1)).getOid().equals(SnmpConstants.
                                    snmpTrapOID)))) {
                vbs.add(1, new VariableBinding(SnmpConstants.snmpTrapOID, trapOID));
            }
        }
    }


    private void addUsmUser(Snmp snmp) {
        snmp.getUSM().addUser(securityName, new UsmUser(securityName,
                authProtocol,
                authPassphrase,
                privProtocol,
                privPassphrase));
    }

    private Snmp createSnmpSession() throws IOException {
        AbstractTransportMapping<? extends Address> transport;
        if (address instanceof TlsAddress) {
            transport = new TLSTM();
        }
        else if (address instanceof TcpAddress) {
            transport = new DefaultTcpTransportMapping();
        }
        else {
            transport = new DefaultUdpTransportMapping();
        }
        // Could save some CPU cycles:
        // transport.setAsyncMsgProcessingSupported(false);
        Snmp snmp =  new Snmp(transport);
        MPv3 mpv3 = (MPv3)snmp.getMessageProcessingModel(MPv3.ID);
        mpv3.setLocalEngineID(localEngineID.getValue());
        mpv3.setCurrentMsgID(MPv3.randomMsgID(engineBootCount));

        if (version == SnmpConstants.version3) {
            USM usm = new USM(SecurityProtocols.getInstance(),
                    localEngineID,
                    engineBootCount);
            SecurityModels.getInstance().addSecurityModel(usm);
            addUsmUser(snmp);
            SecurityModels.getInstance().addSecurityModel(
                    new TSM(localEngineID, false));
        }
        return snmp;
    }

    private Target createTarget() {
        if (version == SnmpConstants.version3) {
            UserTarget target = new UserTarget();
            if (authPassphrase != null) {
                if (privPassphrase != null) {
                    target.setSecurityLevel(SecurityLevel.AUTH_PRIV);
                }
                else {
                    target.setSecurityLevel(SecurityLevel.AUTH_NOPRIV);
                }
            }
            else {
                target.setSecurityLevel(SecurityLevel.NOAUTH_NOPRIV);
            }
            target.setSecurityName(securityName);
            if (authoritativeEngineID != null) {
                target.setAuthoritativeEngineID(authoritativeEngineID.getValue());
            }
            if (address instanceof TlsAddress) {
                target.setSecurityModel(TSM.SECURITY_MODEL_TSM);
            }
            return target;
        }
        else {
            CommunityTarget target = new CommunityTarget();
            target.setCommunity(community);
            return target;
        }
    }

    /**
     * 主要监听端口
     * @throws IOException
     */
     public synchronized void listen() throws IOException {
        AbstractTransportMapping<? extends Address> transport;
        if (address instanceof TcpAddress) {
            transport = new DefaultTcpTransportMapping((TcpAddress)address);
        }
        else {
            transport = new DefaultUdpTransportMapping((UdpAddress) address);
        }
        ThreadPool threadPool =
                ThreadPool.create("DispatcherPool", numDispatcherThreads);
        MessageDispatcher mtDispatcher =
                new MultiThreadedMessageDispatcher(threadPool, new MessageDispatcherImpl());

        // add message processing models
        mtDispatcher.addMessageProcessingModel(new MPv1());
        mtDispatcher.addMessageProcessingModel(new MPv2c());
        mtDispatcher.addMessageProcessingModel(new MPv3(localEngineID.getValue()));

        // add all security protocols
        SecurityProtocols.getInstance().addDefaultProtocols();
        SecurityProtocols.getInstance().addPrivacyProtocol(new Priv3DES());

        Snmp snmp = new Snmp(mtDispatcher, transport);
        if (version == SnmpConstants.version3) {
            USM usm = new USM(SecurityProtocols.getInstance(), localEngineID, 0);
            SecurityModels.getInstance().addSecurityModel(usm);
            // Add the configured user to the USM
            addUsmUser(snmp);
        }
        else {
            CommunityTarget target = new CommunityTarget();
            target.setCommunity(community);
            this.target = target;
        }

        snmp.addCommandResponder(this);

        transport.listen();
        logger.info("Listening on "+address);

        try {
            this.wait();
        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
    /**
     * 发送请求
     * @return
     * @throws IOException
     */
    public PDU sendRequestMessage() throws IOException{
        Snmp snmp = createSnmpSession();
        this.target = createTarget();
        target.setVersion(version);
        target.setAddress(address);
        target.setRetries(retries);
        target.setTimeout(timeout);
        target.setMaxSizeRequestPDU(maxSizeResponsePDU);
        snmp.listen();
        PDU request = createPDU(target);
        if (request.getType() == PDU.GETBULK) {
            request.setMaxRepetitions(maxRepetitions);
            request.setNonRepeaters(nonRepeaters);
        }
        for (VariableBinding vb : vbs) {
            request.add(vb);
        }
        PDU response = null;
            ResponseEvent responseEvent;
            long startTime = System.nanoTime();
            responseEvent = snmp.send(request, target);
            if (responseEvent != null) {
                response = responseEvent.getResponse();
                logger.info("Received response after "+
                        (System.nanoTime()-startTime)/SnmpConstants.MILLISECOND_TO_NANOSECOND+" milliseconds");
            }
        snmp.close();
        return response;
    }

    public List<VariableBinding> sendWwalkSubTree() throws IOException{
        Snmp snmp = createSnmpSession();
        this.target = createTarget();
        target.setVersion(version);
        target.setAddress(address);
        target.setRetries(retries);
        target.setTimeout(timeout);
        target.setMaxSizeRequestPDU(maxSizeResponsePDU);
        snmp.listen();

        PDU request = createPDU(target);
        if (request.getType() == PDU.GETBULK) {
            request.setMaxRepetitions(maxRepetitions);
            request.setNonRepeaters(nonRepeaters);
        }
        for (VariableBinding vb : vbs) {
            request.add(vb);
        }
        ArrayList<VariableBinding> snapshot = snapshot = new ArrayList<VariableBinding>();
        walk(snmp, request, target, snapshot);
        snmp.close();
        return snapshot;
    }


    private PDU walk(Snmp snmp, PDU request, Target target, final List<VariableBinding> snapshot)
            throws IOException
    {
        request.setNonRepeaters(0);
        OID rootOID = request.get(0).getOid();
        PDU response = null;
        final MySnmpRequest.WalkCounts counts = new MySnmpRequest.WalkCounts();
        final long startTime = System.nanoTime();
        TreeUtils treeUtils = new TreeUtils(snmp, this);
        TreeListener treeListener = new TreeListener() {

            private boolean finished;

            public boolean next(TreeEvent e) {
                counts.requests++;
                if (e.getVariableBindings() != null) {
                    VariableBinding[] vbs = e.getVariableBindings();
                    counts.objects += vbs.length;
                    for (VariableBinding vb : vbs) {
                        if (snapshot != null) {
                            snapshot.add(vb);
                        }
                        logger.info(vb.toString());
                    }
                }
                return true;
            }

            public void finished(TreeEvent e) {
                if ((e.getVariableBindings() != null) &&
                        (e.getVariableBindings().length > 0)) {
                    next(e);
                }
                logger.info("Total requests sent:    "+counts.requests);
                logger.info("Total objects received: "+counts.objects);
                logger.info("Total walk time:        "+
                        (System.nanoTime()-startTime)/SnmpConstants.MILLISECOND_TO_NANOSECOND+" milliseconds");
                if (e.isError()) {
                    logger.error("The following error occurred during walk:");
                    logger.error(e.getErrorMessage());
                }
                finished = true;
                synchronized(this) {
                    this.notify();
                }
            }

            public boolean isFinished() {
                return finished;
            }

        };
        synchronized (treeListener) {
            treeUtils.getSubtree(target, rootOID, null, treeListener);
            try {
                treeListener.wait();
            }
            catch (InterruptedException ex) {
                logger.error("Tree retrieval interrupted: " + ex.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        return response;
    }




    protected static String getVersionInfo() {
            return VersionInfo.getVersion();
    }



    public synchronized void processPdu(CommandResponderEvent e) {
        PDU command = e.getPDU();
        if (command != null) {
            logger.info(command.toString());
            if ((command.getType() != PDU.TRAP) &&
                    (command.getType() != PDU.V1TRAP) &&
                    (command.getType() != PDU.REPORT) &&
                    (command.getType() != PDU.RESPONSE)) {
                command.setErrorIndex(0);
                command.setErrorStatus(0);
                command.setType(PDU.RESPONSE);
                StatusInformation statusInformation = new StatusInformation();
                StateReference ref = e.getStateReference();
                try {
                    e.getMessageDispatcher().returnResponsePdu(e.getMessageProcessingModel(),
                            e.getSecurityModel(),
                            e.getSecurityName(),
                            e.getSecurityLevel(),
                            command,
                            e.getMaxSizeResponsePDU(),
                            ref,
                            statusInformation);
                }
                catch (MessageException ex) {
                    logger.error("Error while sending response: "+ex.getMessage());
                    LogFactory.getLogger(MySnmpRequest.class).error(ex);
                }
            }
        }
    }

    public PDU createPDU(Target target) {
        PDU request;
        if (target.getVersion() == SnmpConstants.version3) {
            request = new ScopedPDU();
            ScopedPDU scopedPDU = (ScopedPDU)request;
            if (contextEngineID != null) {
                scopedPDU.setContextEngineID(contextEngineID);
            }
            if (contextName != null) {
                scopedPDU.setContextName(contextName);
            }
        }
        else {
            if (pduType == PDU.V1TRAP) {
                request = v1TrapPDU;
            }
            else {
                request = new PDU();
            }
        }
        request.setType(pduType);
        return request;
    }

    public PDU createPDU(MessageProcessingModel messageProcessingModel) {
        return createPDU((Target)null);
    }
    public  List<VariableBinding> sendOneColumnTableRequestSynchronized() throws IOException{
        Snmp snmp = createSnmpSession();
        this.target = createTarget();
        target.setVersion(version);
        target.setAddress(address);
        target.setRetries(retries);
        target.setTimeout(timeout);
        snmp.listen();

        TableUtils tableUtils = new TableUtils(snmp, this);
        tableUtils.setMaxNumRowsPerPDU(maxRepetitions);
        Counter32 counter = new Counter32();
        final OID[] columns ={(vbs.get(0)).getOid()};
        long startTime = System.nanoTime();
        List<TableEvent> list = tableUtils.getTable(target,columns,lowerBoundIndex,upperBoundIndex);
        List<VariableBinding> result = new ArrayList<VariableBinding>(list.size());
        if(list!=null){
            for(int i=0;i<list.size();i++){
                TableEvent t = list.get(i);
                    VariableBinding v = t.getColumns()[0];
                result.add(v);
            }
        }
        snmp.close();
        return result;
    }

    public  Map<String,List<VariableBinding>> sendTableRequestSynchronized() throws IOException{
        Snmp snmp = createSnmpSession();
        this.target = createTarget();
        target.setVersion(version);
        target.setAddress(address);
        target.setRetries(retries);
        target.setTimeout(timeout);
        snmp.listen();

        TableUtils tableUtils = new TableUtils(snmp, this);
        tableUtils.setMaxNumRowsPerPDU(maxRepetitions);
        Counter32 counter = new Counter32();

       final OID[] columns = new OID[vbs.size()];
        for (int i=0; i<columns.length; i++) {
            columns[i] = (vbs.get(i)).getOid();
        }
        long startTime = System.nanoTime();
        List<TableEvent> list = tableUtils.getTable(target,columns,lowerBoundIndex,upperBoundIndex);
        Map<String,List<VariableBinding>> result = new HashMap<String,List<VariableBinding>>();
        if(list!=null){
            for(int i=0;i<list.size();i++){
                TableEvent t = list.get(i);
               // String key = t.getIndex().toString();
                for (int j=0; j<t.getColumns().length; j++) {
                    VariableBinding v = t.getColumns()[j];
                    if(v!=null){
                        List<VariableBinding> list1 = result.get(columns[i]);
                        if(list1==null){
                            list1 = new ArrayList<VariableBinding>();
                            result.put(columns[i].toString(),list1);
                        }
                        list1.add(v);
                    }
                }
            }
        }
        snmp.close();
        return result;
    }


    public  void sendOneColumnTableRequestAsynchronized(TableListener listener) throws IOException{
        Snmp snmp = createSnmpSession();
        this.target = createTarget();
        target.setVersion(version);
        target.setAddress(address);
        target.setRetries(retries);
        target.setTimeout(timeout);
        snmp.listen();

        TableUtils tableUtils = new TableUtils(snmp, this);
        tableUtils.setMaxNumRowsPerPDU(maxRepetitions);
        Counter32 counter = new Counter32();

        final OID[] columns = new OID[vbs.size()];
        for (int i=0; i<columns.length; i++) {
            columns[i] = (vbs.get(i)).getOid();
        }
        long startTime = System.nanoTime();
        synchronized (counter) {
            tableUtils.getTable(target, columns, listener, counter,
                    lowerBoundIndex, upperBoundIndex);
            try {
                counter.wait(timeout);
            }
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        logger.info("Table received in "+
                (System.nanoTime()-startTime)/SnmpConstants.MILLISECOND_TO_NANOSECOND+" milliseconds.");
        snmp.close();
    }



    class DbOneColumnTableListener implements TableListener {
        public DbOneColumnTableListener(){

        }


        private boolean finished;
        public boolean next(TableEvent event) {

            for (int i=0; i<event.getColumns().length; i++) {
                Variable v = event.getColumns()[i].getVariable();
                String value = v.toString();

                if (i+1 < event.getColumns().length) {
                    System.out.print(",");
                }
            }
            ((Counter32)event.getUserObject()).increment();
            return true;
        }

        public void finished(TableEvent event) {
            finished = true;
            logger.info("Table walk completed with status "+event.getStatus()+
                    ". Received "+
                    event.getUserObject()+" rows.");
            synchronized (event.getUserObject()) {
                event.getUserObject().notify();
            }
        }

        public boolean isFinished() {
            return finished;
        }

    }


    class TextTableListener implements TableListener {

        private boolean finished;

        public void finished(TableEvent event) {
            System.out.println();
            System.out.println("Table walk completed with status "+event.getStatus()+
                    ". Received "+
                    event.getUserObject()+" rows.");
            finished = true;
            synchronized (event.getUserObject()) {
                event.getUserObject().notify();
            }
        }

        public boolean next(TableEvent event) {
            System.out.println("Index = "+event.getIndex()+":");
            for (int i=0; i<event.getColumns().length; i++) {
                System.out.println(event.getColumns()[i]);
            }
            System.out.println();
            ((Counter32)event.getUserObject()).increment();
            return true;
        }

        public boolean isFinished() {
            return finished;
        }

    }


    class CVSTableListener implements TableListener {

        private long requestTime;
        private boolean finished;

        public CVSTableListener(long time) {
            this.requestTime = time;
        }

        public boolean next(TableEvent event) {
                System.out.print(requestTime);
                System.out.print(",");
            System.out.print("\""+event.getIndex()+"\",");
            for (int i=0; i<event.getColumns().length; i++) {
                Variable v = event.getColumns()[i].getVariable();
                String value = v.toString();
                switch (v.getSyntax()) {
                    case SMIConstants.SYNTAX_OCTET_STRING: {
                        StringBuffer escapedString = new StringBuffer(value.length());
                        StringTokenizer st = new StringTokenizer(value, "\"", true);
                        while (st.hasMoreTokens()) {
                            String token = st.nextToken();
                            escapedString.append(token);
                            if (token.equals("\"")) {
                                escapedString.append("\"");
                            }
                        }
                    }
                    case SMIConstants.SYNTAX_IPADDRESS:
                    case SMIConstants.SYNTAX_OBJECT_IDENTIFIER:
                    case SMIConstants.SYNTAX_OPAQUE: {
                        System.out.print("\"");
                        System.out.print(value);
                        System.out.print("\"");
                        break;
                    }
                    default: {
                        System.out.print(value);
                    }
                }
                if (i+1 < event.getColumns().length) {
                    System.out.print(",");
                }
            }
            System.out.println();
            return true;
        }

        public void finished(TableEvent event) {
            finished = true;
            synchronized (event.getUserObject()) {
                event.getUserObject().notify();
            }
        }

        public boolean isFinished() {
            return finished;
        }

    }



    private void createSnapshot(List<VariableBinding> snapshot) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(snapshotFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(snapshot);
            oos.flush();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            if (fos != null) {
                try {
                    fos.close();
                }
                catch (IOException ex1) {
                    ex1.printStackTrace();
                }
            }
        }
    }

    private void dumpSnapshot() {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(snapshotFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            List l = (List) ois.readObject();
            int i=1;
            logger.info("Dumping snapshot file '"+snapshotFile+"':");
            for (Iterator it = l.iterator(); it.hasNext(); i++) {
                logger.info(""+i+": "+it.next());
            }
            logger.info("Dumped "+l.size()+" variable bindings.");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            if (fis != null) {
                try {
                    fis.close();
                }
                catch (IOException ex1) {
                }
            }
        }
    }


    public int getPduType() {
        return pduType;
    }

    public int getVersion() {
        return version;
    }

    public Vector<? extends VariableBinding> getVbs() {
        return vbs;
    }

    public boolean isUseDenseTableOperation() {
        return useDenseTableOperation;
    }

    public OID getUpperBoundIndex() {
        return upperBoundIndex;
    }

    public OID getTrapOID() {
        return trapOID;
    }

    public int getTimeout() {
        return timeout;
    }

    public Target getTarget() {
        return target;
    }

    public TimeTicks getSysUpTime() {
        return sysUpTime;
    }

    public OctetString getSecurityName() {
        return securityName;
    }

    public int getRetries() {
        return retries;
    }

    public OID getPrivProtocol() {
        return privProtocol;
    }

    public OctetString getPrivPassphrase() {
        return privPassphrase;
    }


    public int getNumDispatcherThreads() {
        return numDispatcherThreads;
    }

    public int getNonRepeaters() {
        return nonRepeaters;
    }

    public int getMaxRepetitions() {
        return maxRepetitions;
    }

    public OID getLowerBoundIndex() {
        return lowerBoundIndex;
    }

    public OctetString getContextName() {
        return contextName;
    }

    public OctetString getContextEngineID() {
        return contextEngineID;
    }

    public OctetString getCommunity() {
        return community;
    }

    public OctetString getAuthoritativeEngineID() {
        return authoritativeEngineID;
    }

    public OID getAuthProtocol() {
        return authProtocol;
    }

    public OctetString getAuthPassphrase() {
        return authPassphrase;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setVbs(Vector<VariableBinding> vbs) {
        this.vbs = vbs;
    }

    public void setUseDenseTableOperation(boolean useDenseTableOperation) {
        this.useDenseTableOperation = useDenseTableOperation;
    }

    public void setUpperBoundIndex(OID upperBoundIndex) {
        this.upperBoundIndex = upperBoundIndex;
    }

    public void setTrapOID(OID trapOID) {
        this.trapOID = trapOID;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public void setSysUpTime(TimeTicks sysUpTime) {
        this.sysUpTime = sysUpTime;
    }

    public void setSecurityName(OctetString securityName) {
        this.securityName = securityName;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public void setPrivProtocol(OID privProtocol) {
        this.privProtocol = privProtocol;
    }

    public void setPrivPassphrase(OctetString privPassphrase) {
        this.privPassphrase = privPassphrase;
    }

    public void setPduType(int pduType) {
        this.pduType = pduType;
    }



    public void setNumDispatcherThreads(int numDispatcherThreads) {
        this.numDispatcherThreads = numDispatcherThreads;
    }

    public void setNonRepeaters(int nonRepeaters) {
        this.nonRepeaters = nonRepeaters;
    }

    public void setMaxRepetitions(int maxRepetitions) {
        this.maxRepetitions = maxRepetitions;
    }

    public void setLowerBoundIndex(OID lowerBoundIndex) {
        this.lowerBoundIndex = lowerBoundIndex;
    }

    public void setContextName(OctetString contextName) {
        this.contextName = contextName;
    }

    public void setContextEngineID(OctetString contextEngineID) {
        this.contextEngineID = contextEngineID;
    }

    public void setCommunity(OctetString community) {
        this.community = community;
    }

    public void setAuthoritativeEngineID(OctetString authoritativeEngineID) {
        this.authoritativeEngineID = authoritativeEngineID;
    }

    public void setAuthProtocol(OID authProtocol) {
        this.authProtocol = authProtocol;
    }

    public void setAuthPassphrase(OctetString authPassphrase) {
        this.authPassphrase = authPassphrase;
    }

    class WalkCounts {
        public int requests;
        public int objects;
    }


    public static void main(String args[])throws Exception{
        MySnmpRequestConfig config = new MySnmpRequestConfig();
        config.setAddress("udp:www.tongpinlife.com/161");
        config.setCommunity("public");
        config.setPduType("GETBULK");
        List list = new ArrayList();

     //   list.add("1.3.6.1.2.1.4.20.1.1");
        list.add("1.3.6.1.2.1.6.13.1.2");
       // list.add("1.3.6.1.2.1.4.20.1.2");
        config.setVariableBinding(list);
        MySnmpRequest snmpRequest = new MySnmpRequest(config);
       List<VariableBinding>  r = snmpRequest.sendOneColumnTableRequestSynchronized();
        System.out.println(r);
        System.out.println(r.size());
        List<VariableBinding>  r1 =  snmpRequest.sendWwalkSubTree();
        System.out.println(r1);
        System.out.println(r1.size());
    }
}
