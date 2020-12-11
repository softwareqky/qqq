package project.edge.common;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;

import garage.origin.common.constant.Constants;
import garage.origin.domain.view.SessionUserBean;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.SystemLog;
import project.edge.service.hr.PersonService;
import project.edge.service.system.SystemLogService;

@Component
@Aspect
public class SystemLogAspect {
	
	private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);
	
	private static String[] BASIC_TYPES = {"String", "Integer", "Date", "Long", "Short", "int", "long", "short"};
	private static String[] IGNORE_TYPES = {"RequestFacade", "ResponseFacade", "Locale", "SessionUserBean", "DefaultMultipartHttpServletRequest"};
	
	@Resource
	private SystemLogService systemLogService;
	
	@Resource
	private PersonService personService;
	
	public Object accessSysLog(ProceedingJoinPoint p) throws Throwable {
        logger.info("[Advisor] received");
        Object result = p.proceed();
        try {
			MethodSignature methodSignature = (MethodSignature)p.getSignature();
			String methodName = methodSignature.getName();
			logger.info("[Advisor] method name: " + methodName);
			
			SystemLog systemLogEntity = new SystemLog();
			
			Signature sig = p.getSignature();
			MethodSignature msig = null;
			if (!(sig instanceof MethodSignature)) {
				throw new IllegalArgumentException("this annotation only used for method.");
			}
			msig = (MethodSignature) sig;
			
			Object target = p.getTarget();
			String classType = target.getClass().getName();
			Class<?> clazz = Class.forName(classType);
			String clazzName = clazz.getName();
			if (!StringUtils.isEmpty(clazzName) && clazzName.indexOf(".") > 0) {
				clazzName = clazzName.substring(clazzName.lastIndexOf(".")+1);
			}
			// 获得被拦截的方法
			Method method = null;
			String description = clazzName, actionType = methodName;
			Class[] parameterTypes = msig.getMethod().getParameterTypes();
			method = target.getClass().getMethod(methodName, parameterTypes);
			if (null != method) {
				if (method.isAnnotationPresent(SysLogAnnotation.class)) {
					SysLogAnnotation sysLog = method.getAnnotation(SysLogAnnotation.class);
					if (!StringUtils.isEmpty(sysLog.description())) description = sysLog.description();
					if (!StringUtils.isEmpty(sysLog.action())) actionType = sysLog.action();
				}
			}
			systemLogEntity.setAction(actionType);
			systemLogEntity.setDescription(description); 
			
			Object[] args = p.getArgs();
			StringBuffer rs = new StringBuffer();
			if (args!=null&&args.length>0) {
				for (Object arg :args) {
					String className = null;
					className = arg.getClass().getName();
					className = className.substring(className.lastIndexOf(".") + 1);
					boolean isIgnore = false;
					for (String type : IGNORE_TYPES) {
						if (type.equalsIgnoreCase(className)) {
							isIgnore = true;
							break;
						}
					}
					if (isIgnore) continue;
					getDetailContent(arg, rs);
				}
			}
			if (rs != null && rs.length() > 0) {
				systemLogEntity.setDetailInfo(rs.toString());
			}
			
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
			String userIp = getIpAddress(request);
			systemLogEntity.setUserIp(userIp);
			
			HttpSession session = request.getSession();
			SessionUserBean userBean = (SessionUserBean) session.getAttribute(Constants.SESSION_USER);
			if (userBean != null) {
				systemLogEntity.setLoginName(userBean.getSessionLoginName());
				Person personEntity = personService.find(userBean.getSessionUserId());
				if (personEntity != null) {
					systemLogEntity.setPerson(personEntity);
					systemLogEntity.setPersonName(personEntity.getPersonName());
				}
			}
			systemLogEntity.setcDatetime(new Date());
			
			systemLogService.create(systemLogEntity);
		} catch (Exception e) {
			logger.error("[Advisor]" + e.getMessage());
		}
        return result;
    }
	
    public void errorthrow() {
        logger.error("[Advisor] error throw");
    }
    
	private final static String getIpAddress(HttpServletRequest request)
			throws IOException {
 		String ip = request.getHeader("X-Forwarded-For");
 		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		} else if (ip.length() > 15) {
			String[] ips = ip.split(",");
			for (int index = 0; index < ips.length; index++) {
				String strIp = (String) ips[index];
				if (!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}
		return ip;
	}
	
	private StringBuffer getDetailContent(Object info, StringBuffer rs){
		String className = null;
		className = info.getClass().getName();
		className = className.substring(className.lastIndexOf(".") + 1);
		
		rs.append("{\"param\":\"" + className + "\"");
		rs.append(",\"value\":");
		
		for (String type : BASIC_TYPES) {
			if (type.equals(className)) {
				rs.append("\"" + info + "\"}");
				return rs;
			}
		}		
		
		Method[] methods = info.getClass().getDeclaredMethods();
		Map<String, String> params = new HashMap<String, String>();
		for (Method method : methods) {
			String methodName = method.getName();
			if (methodName.indexOf("get") == -1) { 
				continue;
			}
			if (methodName.length() > 3) {
				methodName = StringUtils.uncapitalize(methodName.substring(3));
			}
			Object rsValue = null;
			try {
				rsValue = method.invoke(info);
				if (rsValue == null) rsValue = "";
				params.put(methodName, String.valueOf(rsValue));
			} catch (Exception e) {
				continue;
			}
		}
		if (params.size() > 0) {
			rs.append(JSON.toJSONString(params));
		} else {
			rs.append("\"\"");
		}
		rs.append("}");
		return rs;
	}
}
