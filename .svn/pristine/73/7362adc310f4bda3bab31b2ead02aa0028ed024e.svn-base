<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title><s:message code="ui.system.title" /></title>
<c:set var="baseUrl" value="${pageContext.request.contextPath}" />
<link rel="Shortcut Icon" href="${baseUrl}<s:theme code="shortcut.ico"/>" />

<link rel="stylesheet" href="<s:url value="/css/bootstrap/bootstrap.min.css"/>" />
<link rel="stylesheet" href="${baseUrl}<s:theme code="login.css"/>" />

<!-- HTML5 shim 和 Respond.js 是为了让 IE8 支持 HTML5 元素和媒体查询（media queries）功能 -->
<!--[if lt IE 9]>
      <script src="<s:url value="/js/util/bootstrap/html5shiv-3.7.3.min.js"/>"></script>
      <script src="<s:url value="/js/util/bootstrap/respond-1.4.2.min.js"/>"></script>
    <![endif]-->
</head>
<body onkeydown="enterLogin();">
	<div id="particles-js" style="display: flex;align-items: center;justify-content: center">
	</div>
  	<div class="loginDiv">
	  	<div class="login-page">
	  		<div class="login-text">
	  			<h1>国家重大科技基础设施</h1>
	  			<h1>未来网络试验设施</h1>
	  		</div>
			<div class="login-content">
				<div class="logobox">
					<img src="img/logo.png" class="logoimg" />
					<h1>江苏省未来网络创新研究院<br/>工程项目管理系统</h1>
				</div>
				<form id="ff" action="login.htm" method="POST">
					<div class="login-input">
						<i class="glyphicon glyphicon-user"></i>
						<input id="username" name="loginName" autocomplete="off" type="text" placeholder="<s:message code="ui.fields.login.login.name" />">
					</div>
					<div class="login-input">
						<i class="glyphicon glyphicon-lock"></i>
						<input id="password" name="loginPwd" autocomplete="off" type="password" placeholder="<s:message code="ui.fields.login.login.pwd" />">
					</div>
					<div id="tips"></div>
					<div class="login-btn">
						<div onclick="login();" class="login-btn-left">
							<span><s:message code="ui.text.login" /></span>
						</div>
					</div>
					<div class="remember">
		                <div class="login-btn-right" onClick="changeImg()">
						    <img src="img/check.png" alt="" id="picture"><s:message code="ui.fields.login.remember.pwd" />
						    <input type="checkbox" id="remember" style="display:none;" />
						</div>
					</div>
				</form>
			</div>
		</div>
  	</div>

  <script type="text/javascript" src="<s:url value="/js/util/jquery-1.12.4.min.js"/>"></script>
  <script type="text/javascript" src="<s:url value="/js/login/particles.js"/>"></script>
  <script type="text/javascript" src="<s:url value="/js/login/app.js"/>"></script>
  <script type="text/javascript">
            //<![CDATA[
			function changeImg(){
			    let pic = document.getElementById('picture');
			    if(pic.getAttribute("src",2) =="img/check.png"){
			      pic.src ="img/checked.png";
			      $("#remember").prop("checked",true).change();
			    }else{
			      pic.src ="img/check.png"
			      $("#remember").prop("checked",false).change();
			    }
			  }

            function login() {
                if ($("#username").val() == "") {
                    $("#tips").html('<s:message code="message.error.empty.login.name" />');
                    return;
                }
                if ($("#password").val() == "") {
                    $("#tips").html('<s:message code="message.error.empty.login.pwd" />');
                    return;
                }
                $('#ff').submit();
            }

            $(function() {
                <c:if test="${not empty errorMsg}">
                $("#tips").html("${errorMsg}");
                </c:if>
                
                var oForm = document.getElementById('ff');
                var oUser = document.getElementById('username');
                var oPswd = document.getElementById('password');
                var oRemember = $('#remember');
                //页面初始化时，如果帐号密码cookie存在则填充
                if(getCookie('user') && getCookie('pswd')){
                  oUser.value = getCookie('user');
                  oPswd.value = getCookie('pswd');
                  oRemember.checked = true;
                  document.getElementById('picture').src ="img/checked.png"
                }
                //复选框勾选状态发生改变时，如果未勾选则清除cookie
                $('#remember').change(function(){
                    if(!$("#remember").is(":checked")){
                      delCookie('user');
                      delCookie('pswd');
                    }
                  })
                //表单提交事件触发时，如果复选框是勾选状态则保存cookie
                oForm.onsubmit = function(){
                  if($("#remember").is(":checked")){ 
                    setCookie('user',oUser.value,30); //保存帐号到cookie，有效期7天
                    setCookie('pswd',oPswd.value,30); //保存密码到cookie，有效期7天
                  }
                }
              });
              //设置cookie
              function setCookie(name,value,day){
                var date = new Date();
                date.setDate(date.getDate() + day);
                document.cookie = name + '=' + value + ';expires='+ date;
              };
              //获取cookie
              function getCookie(name){
                var reg = RegExp(name+'=([^;]+)');
                var arr = document.cookie.match(reg);
                if(arr){
                  return arr[1];
                }else{
                  return '';
                }
              };
              //删除cookie
              function delCookie(name){
                setCookie(name,null,-1);
              };
            
            function enterLogin(){
            	if(window.event.keyCode == 13){
                	login();
                }
            }
            //]]>
        </script>

</body>
</html>