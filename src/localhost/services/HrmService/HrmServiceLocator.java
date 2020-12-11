/**
 * HrmServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package localhost.services.HrmService;

public class HrmServiceLocator extends org.apache.axis.client.Service implements localhost.services.HrmService.HrmService {

    public HrmServiceLocator() {
    }


    public HrmServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public HrmServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for HrmServiceHttpPort
    private java.lang.String HrmServiceHttpPort_address = "http://192.168.88.120:89/services/HrmService";

    public java.lang.String getHrmServiceHttpPortAddress() {
        return HrmServiceHttpPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String HrmServiceHttpPortWSDDServiceName = "HrmServiceHttpPort";

    public java.lang.String getHrmServiceHttpPortWSDDServiceName() {
        return HrmServiceHttpPortWSDDServiceName;
    }

    public void setHrmServiceHttpPortWSDDServiceName(java.lang.String name) {
        HrmServiceHttpPortWSDDServiceName = name;
    }

    public localhost.services.HrmService.HrmServicePortType getHrmServiceHttpPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(HrmServiceHttpPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getHrmServiceHttpPort(endpoint);
    }

    public localhost.services.HrmService.HrmServicePortType getHrmServiceHttpPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            localhost.services.HrmService.HrmServiceHttpBindingStub _stub = new localhost.services.HrmService.HrmServiceHttpBindingStub(portAddress, this);
            _stub.setPortName(getHrmServiceHttpPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setHrmServiceHttpPortEndpointAddress(java.lang.String address) {
        HrmServiceHttpPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (localhost.services.HrmService.HrmServicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                localhost.services.HrmService.HrmServiceHttpBindingStub _stub = new localhost.services.HrmService.HrmServiceHttpBindingStub(new java.net.URL(HrmServiceHttpPort_address), this);
                _stub.setPortName(getHrmServiceHttpPortWSDDServiceName());
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
        if ("HrmServiceHttpPort".equals(inputPortName)) {
            return getHrmServiceHttpPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://localhost/services/HrmService", "HrmService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://localhost/services/HrmService", "HrmServiceHttpPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("HrmServiceHttpPort".equals(portName)) {
            setHrmServiceHttpPortEndpointAddress(address);
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
