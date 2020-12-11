/**
 * CreateWorkFlowLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.com.weaver.service.webservice;

public class CreateWorkFlowLocator extends org.apache.axis.client.Service implements CreateWorkFlow {

    public CreateWorkFlowLocator() {
    }


    public CreateWorkFlowLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CreateWorkFlowLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CreateWorkFlowHttpPort
    private java.lang.String CreateWorkFlowHttpPort_address = "http://oa.fnii.cn/services/CreateWorkFlow";

    public java.lang.String getCreateWorkFlowHttpPortAddress() {
        return CreateWorkFlowHttpPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CreateWorkFlowHttpPortWSDDServiceName = "CreateWorkFlowHttpPort";

    public java.lang.String getCreateWorkFlowHttpPortWSDDServiceName() {
        return CreateWorkFlowHttpPortWSDDServiceName;
    }

    public void setCreateWorkFlowHttpPortWSDDServiceName(java.lang.String name) {
        CreateWorkFlowHttpPortWSDDServiceName = name;
    }

    public CreateWorkFlowPortType getCreateWorkFlowHttpPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CreateWorkFlowHttpPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCreateWorkFlowHttpPort(endpoint);
    }

    public CreateWorkFlowPortType getCreateWorkFlowHttpPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            CreateWorkFlowHttpBindingStub _stub = new CreateWorkFlowHttpBindingStub(portAddress, this);
            _stub.setPortName(getCreateWorkFlowHttpPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCreateWorkFlowHttpPortEndpointAddress(java.lang.String address) {
        CreateWorkFlowHttpPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (CreateWorkFlowPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                CreateWorkFlowHttpBindingStub _stub = new CreateWorkFlowHttpBindingStub(new java.net.URL(CreateWorkFlowHttpPort_address), this);
                _stub.setPortName(getCreateWorkFlowHttpPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("CreateWorkFlowHttpPort".equals(inputPortName)) {
            return getCreateWorkFlowHttpPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("webservices.service.weaver.com.cn", "CreateWorkFlow");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("webservices.service.weaver.com.cn", "CreateWorkFlowHttpPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CreateWorkFlowHttpPort".equals(portName)) {
            setCreateWorkFlowHttpPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
