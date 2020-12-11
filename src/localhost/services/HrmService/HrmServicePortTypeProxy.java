package localhost.services.HrmService;

public class HrmServicePortTypeProxy implements localhost.services.HrmService.HrmServicePortType {
  private String _endpoint = null;
  private localhost.services.HrmService.HrmServicePortType hrmServicePortType = null;
  
  public HrmServicePortTypeProxy() {
    _initHrmServicePortTypeProxy();
  }
  
  public HrmServicePortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initHrmServicePortTypeProxy();
  }
  
  private void _initHrmServicePortTypeProxy() {
    try {
      hrmServicePortType = (new localhost.services.HrmService.HrmServiceLocator()).getHrmServiceHttpPort();
      if (hrmServicePortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)hrmServicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)hrmServicePortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (hrmServicePortType != null)
      ((javax.xml.rpc.Stub)hrmServicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public localhost.services.HrmService.HrmServicePortType getHrmServicePortType() {
    if (hrmServicePortType == null)
      _initHrmServicePortTypeProxy();
    return hrmServicePortType;
  }
  
  public boolean changeUserPassword(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException{
    if (hrmServicePortType == null)
      _initHrmServicePortTypeProxy();
    return hrmServicePortType.changeUserPassword(in0, in1, in2);
  }
  
  public java.lang.String getHrmJobTitleInfoXML(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException{
    if (hrmServicePortType == null)
      _initHrmServicePortTypeProxy();
    return hrmServicePortType.getHrmJobTitleInfoXML(in0, in1, in2);
  }
  
  public java.lang.String getHrmDepartmentInfoXML(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException{
    if (hrmServicePortType == null)
      _initHrmServicePortTypeProxy();
    return hrmServicePortType.getHrmDepartmentInfoXML(in0, in1);
  }
  
  public java.lang.String synSubCompany(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException{
    if (hrmServicePortType == null)
      _initHrmServicePortTypeProxy();
    return hrmServicePortType.synSubCompany(in0, in1);
  }
  
  public weaver.hrm.webservice.JobTitleBean[] getHrmJobTitleInfo(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException{
    if (hrmServicePortType == null)
      _initHrmServicePortTypeProxy();
    return hrmServicePortType.getHrmJobTitleInfo(in0, in1, in2);
  }
  
  public weaver.hrm.webservice.SubCompanyBean[] getHrmSubcompanyInfo(java.lang.String in0) throws java.rmi.RemoteException{
    if (hrmServicePortType == null)
      _initHrmServicePortTypeProxy();
    return hrmServicePortType.getHrmSubcompanyInfo(in0);
  }
  
  public java.lang.String getOneUserInfo(java.lang.String in0) throws java.rmi.RemoteException{
    if (hrmServicePortType == null)
      _initHrmServicePortTypeProxy();
    return hrmServicePortType.getOneUserInfo(in0);
  }
  
  public java.lang.String synDepartment(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException{
    if (hrmServicePortType == null)
      _initHrmServicePortTypeProxy();
    return hrmServicePortType.synDepartment(in0, in1);
  }
  
  public weaver.hrm.webservice.UserBean[] getHrmUserInfo(java.lang.String in0, java.lang.String in1, java.lang.String in2, java.lang.String in3, java.lang.String in4, java.lang.String in5) throws java.rmi.RemoteException{
    if (hrmServicePortType == null)
      _initHrmServicePortTypeProxy();
    return hrmServicePortType.getHrmUserInfo(in0, in1, in2, in3, in4, in5);
  }
  
  public boolean checkUser(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException{
    if (hrmServicePortType == null)
      _initHrmServicePortTypeProxy();
    return hrmServicePortType.checkUser(in0, in1, in2);
  }
  
  public java.lang.String synHrmResource(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException{
    if (hrmServicePortType == null)
      _initHrmServicePortTypeProxy();
    return hrmServicePortType.synHrmResource(in0, in1);
  }
  
  public weaver.hrm.webservice.DepartmentBean[] getHrmDepartmentInfo(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException{
    if (hrmServicePortType == null)
      _initHrmServicePortTypeProxy();
    return hrmServicePortType.getHrmDepartmentInfo(in0, in1);
  }
  
  public java.lang.String synJobtitle(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException{
    if (hrmServicePortType == null)
      _initHrmServicePortTypeProxy();
    return hrmServicePortType.synJobtitle(in0, in1);
  }
  
  public java.lang.String getHrmSubcompanyInfoXML(java.lang.String in0) throws java.rmi.RemoteException{
    if (hrmServicePortType == null)
      _initHrmServicePortTypeProxy();
    return hrmServicePortType.getHrmSubcompanyInfoXML(in0);
  }
  
  public java.lang.String getHrmUserInfoXML(java.lang.String in0, java.lang.String in1, java.lang.String in2, java.lang.String in3, java.lang.String in4, java.lang.String in5) throws java.rmi.RemoteException{
    if (hrmServicePortType == null)
      _initHrmServicePortTypeProxy();
    return hrmServicePortType.getHrmUserInfoXML(in0, in1, in2, in3, in4, in5);
  }
  
  
}