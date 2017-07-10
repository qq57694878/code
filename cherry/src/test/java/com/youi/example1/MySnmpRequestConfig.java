package com.youi.example1;

import org.snmp4j.smi.VariableBinding;

import java.util.List;
import java.util.Vector;

/**
 * Created by jinliang on 2016/10/17.
 */
public class MySnmpRequestConfig {

    private String address;
    private String authProtocol;
    private List<String> variableBinding;
    private String authPassphrase;
    private String privPassphrase;
    private String community;
    private String engineBootCount;
    private String localEngineID;
    private String authoritativeEngineID;
    private String contextEngineID;
    private String contextName;
    private String maxSizeResponsePDU;
    private String retries;
    private String timeout;
    private String securityName;
    private String maxRepetitions;
    private String nonRepeaters;
    private String v1TrapPDUEnterprise;
    private String trapOID;
    private String v1TrapPDUGenericTrap;
    private String v1TrapPDUSpecificTrap;
    private String v1TrapPDUAgentAddress;

    private String v1TrapPDUTimestamp;
    private String sysUpTime;

    private String snapshotFile;
    private String lowerBoundIndex;
    private String upperBoundIndex;
    private String version;
    private String privProtocol;
    private String pduType;

    public String getLocalEngineID() {
        return localEngineID;
    }

    public void setLocalEngineID(String localEngineID) {
        this.localEngineID = localEngineID;
    }

    public String getAuthoritativeEngineID() {
        return authoritativeEngineID;
    }

    public void setAuthoritativeEngineID(String authoritativeEngineID) {
        this.authoritativeEngineID = authoritativeEngineID;
    }

    public String getContextEngineID() {
        return contextEngineID;
    }

    public void setContextEngineID(String contextEngineID) {
        this.contextEngineID = contextEngineID;
    }

    public String getContextName() {
        return contextName;
    }

    public void setContextName(String contextName) {
        this.contextName = contextName;
    }

    public String getMaxSizeResponsePDU() {
        return maxSizeResponsePDU;
    }

    public void setMaxSizeResponsePDU(String maxSizeResponsePDU) {
        this.maxSizeResponsePDU = maxSizeResponsePDU;
    }

    public String getRetries() {
        return retries;
    }

    public void setRetries(String retries) {
        this.retries = retries;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public String getMaxRepetitions() {
        return maxRepetitions;
    }

    public void setMaxRepetitions(String maxRepetitions) {
        this.maxRepetitions = maxRepetitions;
    }

    public String getNonRepeaters() {
        return nonRepeaters;
    }

    public void setNonRepeaters(String nonRepeaters) {
        this.nonRepeaters = nonRepeaters;
    }

    public String getV1TrapPDUEnterprise() {
        return v1TrapPDUEnterprise;
    }

    public void setV1TrapPDUEnterprise(String v1TrapPDUEnterprise) {
        this.v1TrapPDUEnterprise = v1TrapPDUEnterprise;
    }

    public String getTrapOID() {
        return trapOID;
    }

    public void setTrapOID(String trapOID) {
        this.trapOID = trapOID;
    }

    public String getV1TrapPDUGenericTrap() {
        return v1TrapPDUGenericTrap;
    }

    public void setV1TrapPDUGenericTrap(String v1TrapPDUGenericTrap) {
        this.v1TrapPDUGenericTrap = v1TrapPDUGenericTrap;
    }

    public String getV1TrapPDUSpecificTrap() {
        return v1TrapPDUSpecificTrap;
    }

    public void setV1TrapPDUSpecificTrap(String v1TrapPDUSpecificTrap) {
        this.v1TrapPDUSpecificTrap = v1TrapPDUSpecificTrap;
    }

    public String getV1TrapPDUAgentAddress() {
        return v1TrapPDUAgentAddress;
    }

    public void setV1TrapPDUAgentAddress(String v1TrapPDUAgentAddress) {
        this.v1TrapPDUAgentAddress = v1TrapPDUAgentAddress;
    }

    public String getV1TrapPDUTimestamp() {
        return v1TrapPDUTimestamp;
    }

    public void setV1TrapPDUTimestamp(String v1TrapPDUTimestamp) {
        this.v1TrapPDUTimestamp = v1TrapPDUTimestamp;
    }

    public String getSysUpTime() {
        return sysUpTime;
    }

    public void setSysUpTime(String sysUpTime) {
        this.sysUpTime = sysUpTime;
    }

    public String getSnapshotFile() {
        return snapshotFile;
    }

    public void setSnapshotFile(String snapshotFile) {
        this.snapshotFile = snapshotFile;
    }

    public String getLowerBoundIndex() {
        return lowerBoundIndex;
    }

    public void setLowerBoundIndex(String lowerBoundIndex) {
        this.lowerBoundIndex = lowerBoundIndex;
    }

    public String getUpperBoundIndex() {
        return upperBoundIndex;
    }

    public void setUpperBoundIndex(String upperBoundIndex) {
        this.upperBoundIndex = upperBoundIndex;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPrivProtocol() {
        return privProtocol;
    }

    public void setPrivProtocol(String privProtocol) {
        this.privProtocol = privProtocol;
    }

    public String getPduType() {
        return pduType;
    }

    public void setPduType(String pduType) {
        this.pduType = pduType;
    }

    public String getEngineBootCount() {
        return engineBootCount;
    }

    public void setEngineBootCount(String engineBootCount) {
        this.engineBootCount = engineBootCount;
    }

    public String getAuthPassphrase() {
        return authPassphrase;
    }

    public void setAuthPassphrase(String authPassphrase) {
        this.authPassphrase = authPassphrase;
    }

    public String getPrivPassphrase() {
        return privPassphrase;
    }

    public void setPrivPassphrase(String privPassphrase) {
        this.privPassphrase = privPassphrase;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public List<String> getVariableBinding() {
        return variableBinding;
    }

    public void setVariableBinding(List<String> variableBinding) {
        this.variableBinding = variableBinding;
    }

    public String getAddress() {
        return address;
    }

    public String getAuthProtocol() {
        return authProtocol;
    }

    public void setAuthProtocol(String authProtocol) {
        this.authProtocol = authProtocol;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
