<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/jsp/common/include/head.jsp" flush="true" />
<script type="text/javascript" src="<s:url value="/js/util/jquery-1.12.4.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/util/moment/moment.js"/>"></script>
</head>
<body class="hold-transition skin-blue sidebar-mini fixed">

  <%-- 加载easyui时的遮盖，防止画面跳动 --%>
  <div id="EasyuiLoading" class="jeasyui-loading"></div>

  <div class="wrapper">

    <!-- Main Header -->
    <header class="main-header">

      <!-- Logo -->
      <a href="<s:url value="/main.htm"/>" class="logo">
        <!-- mini logo for sidebar mini 50x50 pixels -->
        <span class="logo-mini">
          <!-- <b>P</b>Eg -->
          <b>管</b>
        </span>
        <!-- logo for regular state and mobile devices -->
        <span class="logo-lg">
          <%-- <b>Project</b>Edge<sup>&reg;</sup> --%>
          <b>工程项目管理系统</b><sup>&reg;</sup>
        </span>
      </a>

      <!-- Header Navbar -->
      <nav class="navbar navbar-static-top" role="navigation">

        <!-- Sidebar toggle button-->
        <a href="javascript:void(0)" class="sidebar-toggle" data-toggle="push-menu" role="button"></a>

        <!-- Navbar Right Menu -->
        <div class="navbar-custom-menu">
          <ul class="nav navbar-nav">
          
          	<li>
              <a href="javascript:void(0)" onclick="EasyDialogUtils.openMaxModal(BASE_URL + '/leader-home.htm', '仪表盘')">
              	<i class="fa fa-map"></i>
              	<span>态势图</span>
              </a>
            </li>

            <!-- Messages: style can be found in dropdown.less-->
            <%-- <li class="dropdown messages-menu">
              <!-- Menu toggle button -->
              <a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown">
                <i class="fa fa-envelope-o"></i>
                <span class="label label-success">4</span>
              </a>
              <ul class="dropdown-menu">
                <li class="header">您有4条未读消息</li>
                <li>
                  <!-- inner menu: contains the messages -->
                  <ul class="menu-lte">
                    <li>
                      <!-- start message -->
                      <a href="javascript:void(0)">
                        <div class="pull-left">
                          <!-- User Image -->
                          <img src="<s:url value="/img/user2-160x160.jpg"/>" class="img-circle" alt="User Image">
                        </div>
                        <!-- Message title and timestamp -->
                        <h4>
                          服务支持团队 <small><i class="fa fa-clock-o"></i> 5 分钟前</small>
                        </h4>
                        <!-- The message -->
                        <p>如何实现更棒的DevOps方法？</p>
                      </a>
                    </li>
                    <!-- end message -->
                  </ul>
                  <!-- /.menu -->
                </li>
                <li class="footer">
                  <a href="javascript:void(0)">查看所有消息</a>
                </li>
              </ul>
            </li> --%>
            <!-- /.messages-menu -->

            <!-- Notifications Menu -->
            
            <li class="dropdown notifications-menu" id='noticeNotify'>
              <!-- Menu toggle button -->
              <a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown"></a>
              <div id="_NoticeRemindDialog" closed="true" class="easyui-window" title="提醒列表" style="width:600px;height:400px"
			    data-options="iconCls:'icon-save',modal:true,collapsible:false,minimizable:false,maximizable:false,zIndex:9010,resizable:false">
			    <table id="_NoticeNotifyTable"></table>
		  	  </div>
		  	  <div id="_SiteHistoryLogDialog" closed="true" class="easyui-window" title="历史记录" style="top: 100px; width:800px;height:400px" data-options="iconCls:'icon-audit-log',modal:true,collapsible:false,minimizable:false,maximizable:false,zIndex:9010,resizable:false">
			   	<table class="easyui-datagrid" id="_SiteHistoryLogTable"></table>
			  </div>
			  <div id="_SegmentHistoryLogDialog" closed="true" class="easyui-window" title="历史记录" style="top: 100px; width:800px;height:400px" data-options="iconCls:'icon-audit-log',modal:true,collapsible:false,minimizable:false,maximizable:false,zIndex:9010,resizable:false">
			   	<table class="easyui-datagrid" id="_SegmentHistoryLogTable"></table>
			  </div>
			  <div id="_LinkHistoryLogDialog" closed="true" class="easyui-window" title="历史记录" style="top: 100px; width:800px;height:400px" data-options="iconCls:'icon-audit-log',modal:true,collapsible:false,minimizable:false,maximizable:false,zIndex:9010,resizable:false">
			   	<table class="easyui-datagrid" id="_LinkHistoryLogTable"></table>
			  </div>
			  <div id="_historyWindow" closed="true" class="easyui-window" title="操作内容" style="top: 160px; width:600px;height:400px" data-options="iconCls:'icon-audit-log',modal:true,collapsible:false,minimizable:false,maximizable:false,zIndex:9011,resizable:true"></div>
		  	  <ul class="dropdown-menu"></ul>
             <!--  <ul class="dropdown-menu">
                <li class="header">您有10条通知</li>
                <li>
                  Inner Menu: contains the notifications
                  <ul class="menu-lte">
                    <li>
                      start notification
                      <a href="javascript:void(0)">
                        <i class="fa fa-users text-aqua"></i> 5位新人加入团队
                      </a>
                    </li>
                    end notification
                  </ul>
                </li>
                <li class="footer">
                  <a href="javascript:void(0)">查看所有</a>
                </li>
              </ul> -->
            </li>

            <!-- Tasks Menu -->
             <%-- <li class="dropdown tasks-menu">
              <!-- Menu Toggle Button -->
              <a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown">
                <i class="fa fa-flag-o"></i>
                <%-- <span class="label label-danger">9</span>
              </a>
             <ul class="dropdown-menu">
                <li class="header">您有共计9项任务</li>
                <li>
                  <!-- Inner menu: contains the tasks -->
                  <ul class="menu-lte">
                    <li>
                      <!-- Task item -->
                      <a href="javascript:void(0)">
                        <!-- Task title and progress text -->
                        <h3>
                          南京仙林项目立项审批 <small class="pull-right">20%</small>
                        </h3>
                        <!-- The progress bar -->
                        <div class="progress xs">
                          <!-- Change the css width attribute to simulate progress -->
                          <div class="progress-bar progress-bar-aqua" style="width: 20%" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"></div>
                        </div>
                      </a>
                    </li>
                    <!-- end task item -->
                  </ul>
                </li>
                <li class="footer">
                  <a href="javascript:void(0)">查看所有任务</a>
                </li>
              </ul>
            </li> --%>

            <!-- User Account Menu -->
            <li class="dropdown user user-menu">
              <!-- Menu Toggle Button -->
              <a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown">
                <!-- The user image in the navbar-->
                <img src="<s:url value="/img/user4.jpg"/>" class="user-image" alt="User Image" />
                <!-- hidden-xs hides the username on small devices so only the image appears. -->
                <span class="hidden-xs" id="userName">--</span>
              </a>
              <%--<ul class="dropdown-menu">
                <!-- The user image in the menu -->
                <li class="user-header">
                  <img src="<s:url value="/img/user2-160x160.jpg"/>" class="img-circle" alt="User Image" />
                  <p id="userDetail"></p>
                </li>
                <!-- Menu Body -->
                <li class="user-body">
                  <div class="row">
                    <div class="col-xs-4 text-center">
                      <a href="javascript:void(0)">通讯录</a>
                    </div>
                    <div class="col-xs-4 text-center">
                      <a href="javascript:void(0)">授权管理</a>
                    </div>
                    <div class="col-xs-4 text-center">
                      <a href="javascript:void(0)">组织结构</a>
                    </div>
                  </div>
                  <!-- /.row -->
                </li>
                <!-- Menu Footer-->
                <li class="user-footer">
                  <div class="pull-left">
                    <a href="javascript:void(0)" class="btn btn-default btn-flat">个人档案</a>
                  </div>
                  <div class="pull-right">
                    <a href="javascript:void(0)" class="btn btn-default btn-flat">证书</a>
                  </div>
                </li>
              </ul>--%>
            </li>
            <!-- Logout -->
            <li>
              <a href="javascript:void(0)" onclick="Main.logout();">
                <i class="fa fa-power-off"></i>
                <span>退出</span>
              </a>
            </li>
            
          </ul>
        </div>
      </nav>
    </header>

    <jsp:include page="/WEB-INF/jsp/main/mainMenu.jsp" flush="true" />

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">

      <!-- Main content -->
      <section class="main-content-tab-wrapper">
        <div id="MainContentTabToolsMenu" class="easyui-menu" data-options="onClick:Main.onMainContentTabToolsMenuClick,onShow:Main.onMainContentTabToolsMenuShow">
          <div data-options="name:'colse-current',iconCls:'icon-func-minus'">
            <s:message code="ui.common.close.current.tab" />
          </div>
          <div data-options="name:'colse-other'">
            <s:message code="ui.common.close.other.tabs" />
          </div>
          <div data-options="name:'colse-right'">
            <s:message code="ui.common.close.right.tabs" />
          </div>
          <div data-options="name:'colse-all',iconCls:'icon-func-delete'">
            <s:message code="ui.common.close.all.tabs" />
          </div>
        </div>
        <div id="MainContentTabTools" class="tabs-tool-no-border">
          <a href="javascript:void(0)" class="easyui-menubutton" role="button" data-options="menu:'#MainContentTabToolsMenu',plain:true,hasDownArrow:false">
            <i class="fa fa-fw fa-th-list"></i>
          </a>
        </div>
        <div id="MainContentTab" class="easyui-tabs" data-options="plan:true,fit:true,border:true,tools:'#MainContentTabTools',onBeforeClose:Main.onBeforeCotentTabClose">

          <div title="&lt;i class=&quot;fa fa-home&quot; /&gt; &nbsp;<s:message code="ui.common.home" />"
            data-options="closable:false,href:'<s:url value="/home.htm"/>',extractor:Main.extractContentTabHtmlBody,onLoadError:MainUtils.handleDatagridLoadError"
          ></div>
        </div>
        <!-- /.easyui-tabs -->
      </section>
      <!-- /.main-content-tab-wrapper -->
    </div>
    <!-- /.content-wrapper -->

    <!-- Main Footer -->
    <footer class="main-footer main-footer-thin">
      <!-- To the right -->
      <div class="pull-right hidden-xs"></div>
      <!-- Default to the left -->
      <%-- <strong>Copyright &copy; 2019 <a href="javascript:void(0)">江苏未来网络创新研究院</a>.
      </strong> All rights reserved. --%>
    </footer>
  </div>
  <!-- ./wrapper -->
  <script>
  window.name = "ceni_main";
            $.ajax({
                url: '${pageContext.request.contextPath}/login-user.json',
                type: 'POST',
                data: "",
                dataType: 'json',
                success: function(response, statusText, xhr, jqForm) {
                    if (response.status == 1) {
                        $("#userName").text(response.dataMap.returnObj.personName);
                        $("#userDetail").html(
                                response.dataMap.returnObj.personName + " - "
                                        + response.dataMap.returnObj.postText + "<small>"
                                        + response.dataMap.returnObj.orgText
                                        + response.dataMap.returnObj.deptText + "</small>")
                    } else {
                        alert(response.message)
                    }
                },
                error: function(XMLHttpRequest, textStatus, errorThrown) {

                }
            })
        </script>
  <script>
  
  	var noticeNotifyItems = [];
  	var notifyPageSize = 8;
  	var notifyPageNumber = 1;
  
  	$(document).ready(function() {
  		showUnreadReminds();
  	});
  	
  	/**
  	 * 显示未读消息下拉框内容
  	 */
  	function showUnreadReminds() {
  		
  		$.ajax({
  			url: "/project-edge/notice/notify/unread.json",
  			method: 'GET',
  			success: function(result, status) {
  				
  				// 如果成功
  				if (result.status == 1) {
  					noticeNotifyItems = result.dataMap.rows;
  					console.log(noticeNotifyItems);
  					const notifyCount = noticeNotifyItems.length > 0 ? noticeNotifyItems.length : '';
  					$('#noticeNotify > a').empty();
  					$('#noticeNotify > a').append(
  						'<i class="fa fa-bell-o"></i>' + 
  						'<span class="label label-warning">' + notifyCount + '</span>'
  					);
  		  			
  					$('#noticeNotify > ul').empty();
  		  	  		$('#noticeNotify > ul').append(
  		                	'<li class="header">您有' + noticeNotifyItems.length + '条未读提醒</li>' +
  		                	'<li>' +
  			                 	'<ul class="menu-lte">' + 
  			                 		noticeNotifyItems.map((n, i) => 
  				                 		'<li>' +
  				                    		'<a href="javascript:readRemind(' + i + ')">' +
  			                        			n.notify.content +
  				                      		'</a>' +
  				                    	'</li>'
  			                 		).join('') + 
  			                	'</ul>' +
  		                	'</li>' +
  			                '<li class="footer">' +
  			                	'<a href="javascript:showReminds()">查看所有</a>' +
  			                '</li>'
  		  	    	);
  				}
  			}
  		});
  	}
  	
  	/**
  	 * 查看所有消息
  	 */
  	function showReminds() {
  		
  		$('#_NoticeRemindDialog').window('open');
  		$('#_NoticeNotifyTable').datagrid({
  			url: "/project-edge/notice/notify/list.json",
  			method: "GET",
  			singleSelect: true,
  			pageSize: notifyPageSize,
  			pageNumber: notifyPageNumber,
  			pagination: true,
  			fitColumns: true,
  			columns: [[
  				/* { field: 'targetType', title: '分类', width: 100, 
  					formatter: function(value, row, index) {
  						console.log(row);
  						return row.notify.targetType;
  					} 
  				}, */
  				{ field: 'content', title: '提醒内容', width: 280,
  					formatter: function(value, row, index) {
  						return row.notify.content;
  					}
  				},
  				{ field: 'createAt', title: '提醒时间', width: 100,
  					formatter: function(value, row, index) {
  						return moment(row.notify.createAt).format('YYYY-MM-DD HH:mm');
  					}	
  				},
  				{ field: 'read', title: '阅读状态', width: 100, 
  					formatter: function(value, row, index) {
  						return row.read == 0 ? '未读' : '已读';
  					} 
  				},
  			]],
  			onClickRow: function(index, data) {
  				readRemind(data.id, data);
  			},
  			loadFilter: function(data) {
  				if (data.status == 1) {
  					return data.dataMap;
  				} else {
  					return []
  				}
  			},
  		})
  	}
  	
  	/**
  	 * 阅读消息
  	 */
  	function readRemind(index, data) {
  		
  		if (!data) {
  			console.log(noticeNotifyItems, index);
  			data = noticeNotifyItems[index];
  		}
  		
  		// 显示
  		$.messager.alert('消息提醒', data.notify.content, 'info');
  		
  		// 标记已读
  		console.log('提交已读')
  		const param = { id: data.id };
  		$.ajax({
  			url: "/project-edge/notice/notify/read.json",
  			method: 'POST',
  			data: param,
  			complete: function(result, status) {
  				console.log('提交已读完成', result);
  				const notifyTable = $('#_NoticeNotifyTable');
  				if (notifyTable.options) {
  					notifyTable.datagrid('reload'); // 重新加载列表
  				}
  				showUnreadReminds(); // 重新获取未读消息下拉框
  			}
  		});
  	}
  </script>
  <jsp:include page="/WEB-INF/jsp/main/mainHidden.jsp" flush="true" />
  <jsp:include page="/WEB-INF/jsp/common/include/downloadViaIframe.jsp" flush="true" />
  <jsp:include page="/WEB-INF/jsp/common/include/javascriptSrc.jsp" flush="true" />

</body>
</html>