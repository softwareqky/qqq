package cn.com.weaver.service.webservice;

public class CreateWorkFlowPortTypeProxy implements CreateWorkFlowPortType {
  private String _endpoint = null;
  private CreateWorkFlowPortType createWorkFlowPortType = null;
  
  public CreateWorkFlowPortTypeProxy() {
    _initCreateWorkFlowPortTypeProxy();
  }
  
  public CreateWorkFlowPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initCreateWorkFlowPortTypeProxy();
  }
  
  private void _initCreateWorkFlowPortTypeProxy() {
    try {
      createWorkFlowPortType = (new CreateWorkFlowLocator()).getCreateWorkFlowHttpPort();
      if (createWorkFlowPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)createWorkFlowPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)createWorkFlowPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (createWorkFlowPortType != null)
      ((javax.xml.rpc.Stub)createWorkFlowPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public CreateWorkFlowPortType getCreateWorkFlowPortType() {
    if (createWorkFlowPortType == null)
      _initCreateWorkFlowPortTypeProxy();
    return createWorkFlowPortType;
  }
  
  public java.lang.String createWorkFlow(java.lang.String in0) throws java.rmi.RemoteException{
    if (createWorkFlowPortType == null)
      _initCreateWorkFlowPortTypeProxy();
    return createWorkFlowPortType.createWorkFlow(in0);
  }
  
  
}