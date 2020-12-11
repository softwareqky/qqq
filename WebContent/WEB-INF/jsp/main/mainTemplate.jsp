<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>工程项目管理系统</title>
<c:set var="baseUrl" value="${pageContext.request.contextPath}" />
<link rel="Shortcut Icon" href="${baseUrl}<s:theme code="shortcut.ico"/>" />

<link rel="stylesheet" href="<s:url value="/css/bootstrap/bootstrap.min.css"/>" />
<link rel="stylesheet" href="<s:url value="/css/font-awesome/css/font-awesome.min.css"/>" />
<link rel="stylesheet" href="<s:url value="/css/Ionicons/css/ionicons.min.css"/>" />
<link rel="stylesheet" href="${baseUrl}<s:theme code="adminlte.css"/>" />
<link rel="stylesheet" href="${baseUrl}<s:theme code="adminlte.skin.css"/>" />
<link rel="stylesheet" type="text/css" href="${baseUrl}<s:theme code="easyui.css"/>" />
<link rel="stylesheet" type="text/css" href="${baseUrl}<s:theme code="easyui.icon.css"/>" />
<link rel="stylesheet" href="${baseUrl}<s:theme code="custom.css"/>" />

<!-- HTML5 shim 和 Respond.js 是为了让 IE8 支持 HTML5 元素和媒体查询（media queries）功能 -->
<!--[if lt IE 9]>
      <script src="<s:url value="/js/util/bootstrap/html5shiv-3.7.3.min.js"/>"></script>
      <script src="<s:url value="/js/util/bootstrap/respond-1.4.2.min.js"/>"></script>
    <![endif]-->
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
          <b>P</b>Eg
        </span>
        <!-- logo for regular state and mobile devices -->
        <span class="logo-lg">
          <b>Project</b>Edge<sup>&reg;</sup>
        </span>
      </a>

      <!-- Header Navbar -->
      <nav class="navbar navbar-static-top" role="navigation">

        <!-- Sidebar toggle button-->
        <a href="javascript:void(0)" class="sidebar-toggle" data-toggle="push-menu" role="button"></a>

        <!-- Navbar Right Menu -->
        <div class="navbar-custom-menu">
          <ul class="nav navbar-nav">

            <!-- Messages: style can be found in dropdown.less-->
            <li class="dropdown messages-menu">
              <!-- Menu toggle button -->
              <a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown">
                <i class="fa fa-envelope-o"></i>
                <span class="label label-success">4</span>
              </a>
              <ul class="dropdown-menu">
                <li class="header">You have 4 messages</li>
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
                          Support Team <small><i class="fa fa-clock-o"></i> 5 mins</small>
                        </h4>
                        <!-- The message -->
                        <p>Why not buy a new awesome theme?</p>
                      </a>
                    </li>
                    <!-- end message -->
                  </ul>
                  <!-- /.menu -->
                </li>
                <li class="footer">
                  <a href="javascript:void(0)">See All Messages</a>
                </li>
              </ul>
            </li>
            <!-- /.messages-menu -->

            <!-- Notifications Menu -->
            <li class="dropdown notifications-menu">
              <!-- Menu toggle button -->
              <a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown">
                <i class="fa fa-bell-o"></i>
                <span class="label label-warning">10</span>
              </a>
              <ul class="dropdown-menu">
                <li class="header">You have 10 notifications</li>
                <li>
                  <!-- Inner Menu: contains the notifications -->
                  <ul class="menu-lte">
                    <li>
                      <!-- start notification -->
                      <a href="javascript:void(0)">
                        <i class="fa fa-users text-aqua"></i> 5 new members joined today
                      </a>
                    </li>
                    <!-- end notification -->
                  </ul>
                </li>
                <li class="footer">
                  <a href="javascript:void(0)">View all</a>
                </li>
              </ul>
            </li>

            <!-- Tasks Menu -->
            <li class="dropdown tasks-menu">
              <!-- Menu Toggle Button -->
              <a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown">
                <i class="fa fa-flag-o"></i>
                <span class="label label-danger">9</span>
              </a>
              <ul class="dropdown-menu">
                <li class="header">You have 9 tasks</li>
                <li>
                  <!-- Inner menu: contains the tasks -->
                  <ul class="menu-lte">
                    <li>
                      <!-- Task item -->
                      <a href="javascript:void(0)">
                        <!-- Task title and progress text -->
                        <h3>
                          Design some buttons <small class="pull-right">20%</small>
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
                  <a href="javascript:void(0)">View all tasks</a>
                </li>
              </ul>
            </li>

            <!-- User Account Menu -->
            <li class="dropdown user user-menu">
              <!-- Menu Toggle Button -->
              <a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown">
                <!-- The user image in the navbar-->
                <img src="<s:url value="/img/user2-160x160.jpg"/>" class="user-image" alt="User Image" />
                <!-- hidden-xs hides the username on small devices so only the image appears. -->
                <span class="hidden-xs">路人甲</span>
              </a>
              <ul class="dropdown-menu">
                <!-- The user image in the menu -->
                <li class="user-header">
                  <img src="<s:url value="/img/user2-160x160.jpg"/>" class="img-circle" alt="User Image" />
                  <p>
                    Alexander Pierce - Web Developer <small>Member since Nov. 2012</small>
                  </p>
                </li>
                <!-- Menu Body -->
                <li class="user-body">
                  <div class="row">
                    <div class="col-xs-4 text-center">
                      <a href="javascript:void(0)">Followers</a>
                    </div>
                    <div class="col-xs-4 text-center">
                      <a href="javascript:void(0)">Sales</a>
                    </div>
                    <div class="col-xs-4 text-center">
                      <a href="javascript:void(0)">Friends</a>
                    </div>
                  </div>
                  <!-- /.row -->
                </li>
                <!-- Menu Footer-->
                <li class="user-footer">
                  <div class="pull-left">
                    <a href="javascript:void(0)" class="btn btn-default btn-flat">Profile</a>
                  </div>
                  <div class="pull-right">
                    <a href="javascript:void(0)" class="btn btn-default btn-flat">Sign out</a>
                  </div>
                </li>
              </ul>
            </li>
            <!-- Logout -->
            <li>
              <a href="javascript:void(0)">
                <i class="fa fa-power-off"></i>
                <span>退出</span>
              </a>
            </li>
          </ul>
        </div>
      </nav>
    </header>

    <!-- Left side column. contains the logo and sidebar -->
    <aside class="main-sidebar">

      <!-- sidebar: style can be found in sidebar.less -->
      <section class="sidebar">

        <!-- Sidebar Menu -->
        <%-- 把.sidebar-menu的data-animation-speed属性改为0，以防止展开菜单时发生重叠 --%>
        <ul class="sidebar-menu" data-widget="tree" data-animation-speed="0" data-accordion="true">
          <li class="header">我的工作台</li>
          <li>
            <a href="javascript:void(0)">
              <i class="fa fa-dashboard"></i>
              <span>我的仪表盘</span>
              <span class="pull-right-container">
                <small class="label pull-right bg-green">new</small> <small class="label pull-right bg-blue">3</small>
              </span>
            </a>
          </li>
          <li>
            <a href="javascript:void(0)" onclick="Main.openTab('我的报表', '<s:url value="/demo/tab-content.htm" />')">
              <i class="fa fa-pie-chart"></i>
              <span>我的报表</span>
              <span class="pull-right-container">
                <small class="label pull-right bg-yellow">7</small> <small class="label pull-right bg-red">2</small>
              </span>
            </a>
          </li>
          <li class="header">主导航菜单</li>
          <!-- Optionally, you can add icons to the links -->
          <li>
            <a href="javascript:void(0)">
              <i class="fa fa-bar-chart"></i>
              <span>综合分析(KPI)</span>
            </a>
          </li>
          <li class="treeview">
            <a href="javascript:void(0)">
              <i class="fa fa-suitcase"></i>
              <span>招标管理</span>
              <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
            </a>
            <ul class="treeview-menu">
              <li class="treeview">
                <a href="javascript:void(0)">
                  <i class="fa fa-caret-right"></i>
                  <span>信息立项</span>
                  <span class="pull-right-container">
                    <i class="fa fa-angle-left pull-right"></i>
                  </span>
                </a>
                <ul class="treeview-menu">
                  <li>
                    <a href="javascript:void(0)" onclick="Main.openTab('项目立项', '<s:url value="/info-management/application/main.htm" />')">
                      <i class="fa fa-genderless"></i>信息立项申请
                    </a>
                  </li>
                  <li>
                    <a href="javascript:void(0)" onclick="Main.openTab('项目立项', '<s:url value="/info-management/initiation/main.htm" />')">
                      <i class="fa fa-genderless"></i>信息立项表
                    </a>
                  </li>
                </ul>
              </li>
              <li class="treeview">
                <a href="javascript:void(0)">
                  <i class="fa fa-caret-right"></i>
                  <span>项目投标</span>
                  <span class="pull-right-container">
                    <i class="fa fa-angle-left pull-right"></i>
                  </span>
                </a>
                <ul class="treeview-menu">
                  <li>
                    <a href="javascript:void(0)" onclick="Main.openTab('投标管理', '<s:url value="/demo/bidding-management.htm" />')">
                      <i class="fa fa-genderless"></i>
                      <span>投标管理</span>
                    </a>
                  </li>
                </ul>
              </li>
              <li class="treeview">
                <a href="javascript:void(0)">
                  <i class="fa fa-caret-right"></i>
                  <span>状态变更管理</span>
                  <span class="pull-right-container">
                    <i class="fa fa-angle-left pull-right"></i>
                  </span>
                </a>
                <ul class="treeview-menu">
                  <li>
                    <a href="javascript:void(0)" onclick="Main.openTab('投标管理', '<s:url value="/demo/bidding-management.htm" />')">
                      <i class="fa fa-genderless"></i>
                      <span>立项状态变更</span>
                    </a>
                  </li>
                </ul>
              </li>
            </ul>
          </li>
          <li class="treeview">
            <a href="javascript:void(0)">
              <i class="fa fa-paint-brush"></i>
              <span>项目立项</span>
              <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
            </a>
            <ul class="treeview-menu">
              <li>
                <a href="javascript:void(0)" onclick="Main.openTab('项目立项', '<s:url value="/project-management/initiation/main.htm" />')">
                  <i class="fa fa-genderless"></i>立项管理
                </a>
              </li>
              <li>
                <a href="javascript:void(0)">
                  <i class="fa fa-genderless"></i>立项信息变更
                </a>
              </li>
              <li>
                <a href="javascript:void(0)">
                  <i class="fa fa-genderless"></i>放弃管理
                </a>
              </li>
              <li class="treeview">
                <a href="javascript:void(0)">
                  <i class="fa fa-caret-right"></i>
                  <span>项目成员管理</span>
                  <span class="pull-right-container">
                    <i class="fa fa-angle-left pull-right"></i>
                  </span>
                </a>
                <ul class="treeview-menu">
                  <li>
                    <a href="javascript:void(0)">
                      <i class="fa fa-genderless"></i>项目成员编制
                    </a>
                  </li>
                  <li class="treeview">
                    <a href="javascript:void(0)">
                      <i class="fa fa-caret-right"></i>
                      <span>项目分布</span>
                      <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                      </span>
                    </a>
                    <ul class="treeview-menu">
                      <li>
                        <a href="javascript:void(0)">
                          <i class="fa fa-genderless"></i>
                          <span>项目地图</span>
                        </a>
                      </li>
                      <li>
                        <a href="javascript:void(0)">
                          <i class="fa fa-genderless"></i>
                          <span>项目成员分布图</span>
                        </a>
                      </li>
                    </ul>
                  </li>
                </ul>
              </li>
              <li>
                <a href="javascript:void(0)">
                  <i class="fa fa-genderless"></i>权限管理
                </a>
              </li>
            </ul>
          </li>

          <li>
            <a href="javascript:void(0)">
              <i class="fa fa-calendar"></i>
              <span>进度管理</span>
            </a>
          </li>

          <li>
            <a href="javascript:void(0)">
              <i class="fa fa-bar-chart"></i>
              <span>综合分析(KPI)</span>
            </a>
          </li>
          <li>
            <a href="javascript:void(0)">
              <i class="fa fa-suitcase"></i>
              <span>招标管理</span>
            </a>
          </li>
          <li class="treeview">
            <a href="javascript:void(0)">
              <i class="fa fa-paint-brush"></i>
              <span>项目立项</span>
              <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
            </a>
            <ul class="treeview-menu">
              <li>
                <a href="javascript:void(0)">
                  <i class="fa fa-genderless"></i>立项管理
                </a>
              </li>
              <li>
                <a href="javascript:void(0)">
                  <i class="fa fa-genderless"></i>立项信息变更
                </a>
              </li>
              <li>
                <a href="javascript:void(0)">
                  <i class="fa fa-genderless"></i>放弃管理
                </a>
              </li>
              <li class="treeview">
                <a href="javascript:void(0)">
                  <i class="fa fa-caret-right"></i>
                  <span>项目成员管理</span>
                  <span class="pull-right-container">
                    <i class="fa fa-angle-left pull-right"></i>
                  </span>
                </a>
                <ul class="treeview-menu">
                  <li>
                    <a href="javascript:void(0)">
                      <i class="fa fa-genderless"></i>项目成员编制
                    </a>
                  </li>
                  <li class="treeview">
                    <a href="javascript:void(0)">
                      <i class="fa fa-caret-right"></i>
                      <span>项目分布</span>
                      <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                      </span>
                    </a>
                    <ul class="treeview-menu">
                      <li>
                        <a href="javascript:void(0)">
                          <i class="fa fa-genderless"></i>
                          <span>项目地图</span>
                        </a>
                      </li>
                      <li>
                        <a href="javascript:void(0)">
                          <i class="fa fa-genderless"></i>
                          <span>项目成员分布图</span>
                        </a>
                      </li>
                    </ul>
                  </li>
                </ul>
              </li>
              <li>
                <a href="javascript:void(0)">
                  <i class="fa fa-genderless"></i>权限管理
                </a>
              </li>
            </ul>
          </li>

          <li>
            <a href="javascript:void(0)">
              <i class="fa fa-calendar"></i>
              <span>进度管理</span>
            </a>
          </li>

          <li>
            <a href="javascript:void(0)">
              <i class="fa fa-bar-chart"></i>
              <span>综合分析(KPI)</span>
            </a>
          </li>
          <li>
            <a href="javascript:void(0)">
              <i class="fa fa-suitcase"></i>
              <span>招标管理</span>
            </a>
          </li>
          <li class="treeview">
            <a href="javascript:void(0)">
              <i class="fa fa-paint-brush"></i>
              <span>项目立项</span>
              <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
            </a>
            <ul class="treeview-menu">
              <li>
                <a href="javascript:void(0)">
                  <i class="fa fa-genderless"></i>立项管理
                </a>
              </li>
              <li>
                <a href="javascript:void(0)">
                  <i class="fa fa-genderless"></i>立项信息变更
                </a>
              </li>
              <li>
                <a href="javascript:void(0)">
                  <i class="fa fa-genderless"></i>放弃管理
                </a>
              </li>
              <li class="treeview">
                <a href="javascript:void(0)">
                  <i class="fa fa-caret-right"></i>
                  <span>项目成员管理</span>
                  <span class="pull-right-container">
                    <i class="fa fa-angle-left pull-right"></i>
                  </span>
                </a>
                <ul class="treeview-menu">
                  <li>
                    <a href="javascript:void(0)">
                      <i class="fa fa-genderless"></i>项目成员编制
                    </a>
                  </li>
                  <li class="treeview">
                    <a href="javascript:void(0)">
                      <i class="fa fa-caret-right"></i>
                      <span>项目分布</span>
                      <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                      </span>
                    </a>
                    <ul class="treeview-menu">
                      <li>
                        <a href="javascript:void(0)">
                          <i class="fa fa-genderless"></i>
                          <span>项目地图</span>
                        </a>
                      </li>
                      <li>
                        <a href="javascript:void(0)">
                          <i class="fa fa-genderless"></i>
                          <span>项目成员分布图</span>
                        </a>
                      </li>
                    </ul>
                  </li>
                </ul>
              </li>
              <li>
                <a href="javascript:void(0)">
                  <i class="fa fa-genderless"></i>权限管理
                </a>
              </li>
            </ul>
          </li>

          <li>
            <a href="javascript:void(0)">
              <i class="fa fa-calendar"></i>
              <span>进度管理</span>
            </a>
          </li>

          <li>
            <a href="javascript:void(0)">
              <i class="fa fa-bar-chart"></i>
              <span>综合分析(KPI)</span>
            </a>
          </li>
          <li>
            <a href="javascript:void(0)">
              <i class="fa fa-suitcase"></i>
              <span>招标管理</span>
            </a>
          </li>
          <li class="treeview">
            <a href="javascript:void(0)">
              <i class="fa fa-paint-brush"></i>
              <span>项目立项</span>
              <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
            </a>
            <ul class="treeview-menu">
              <li>
                <a href="javascript:void(0)">
                  <i class="fa fa-genderless"></i>立项管理
                </a>
              </li>
              <li>
                <a href="javascript:void(0)">
                  <i class="fa fa-genderless"></i>立项信息变更
                </a>
              </li>
              <li>
                <a href="javascript:void(0)">
                  <i class="fa fa-genderless"></i>放弃管理
                </a>
              </li>
              <li class="treeview">
                <a href="javascript:void(0)">
                  <i class="fa fa-caret-right"></i>
                  <span>项目成员管理</span>
                  <span class="pull-right-container">
                    <i class="fa fa-angle-left pull-right"></i>
                  </span>
                </a>
                <ul class="treeview-menu">
                  <li>
                    <a href="javascript:void(0)">
                      <i class="fa fa-genderless"></i>项目成员编制
                    </a>
                  </li>
                  <li class="treeview">
                    <a href="javascript:void(0)">
                      <i class="fa fa-caret-right"></i>
                      <span>项目分布</span>
                      <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                      </span>
                    </a>
                    <ul class="treeview-menu">
                      <li>
                        <a href="javascript:void(0)">
                          <i class="fa fa-genderless"></i>
                          <span>项目地图</span>
                        </a>
                      </li>
                      <li>
                        <a href="javascript:void(0)">
                          <i class="fa fa-genderless"></i>
                          <span>项目成员分布图</span>
                        </a>
                      </li>
                    </ul>
                  </li>
                </ul>
              </li>
              <li>
                <a href="javascript:void(0)">
                  <i class="fa fa-genderless"></i>权限管理
                </a>
              </li>
            </ul>
          </li>

          <li>
            <a href="javascript:void(0)">
              <i class="fa fa-calendar"></i>
              <span>进度管理</span>
            </a>
          </li>

          <li>
            <a href="javascript:void(0)">
              <i class="fa fa-bar-chart"></i>
              <span>综合分析(KPI)</span>
            </a>
          </li>
          <li>
            <a href="javascript:void(0)">
              <i class="fa fa-suitcase"></i>
              <span>招标管理</span>
            </a>
          </li>
          <li class="treeview">
            <a href="javascript:void(0)">
              <i class="fa fa-paint-brush"></i>
              <span>项目立项</span>
              <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
            </a>
            <ul class="treeview-menu">
              <li>
                <a href="javascript:void(0)">
                  <i class="fa fa-genderless"></i>立项管理
                </a>
              </li>
              <li>
                <a href="javascript:void(0)">
                  <i class="fa fa-genderless"></i>立项信息变更
                </a>
              </li>
              <li>
                <a href="javascript:void(0)">
                  <i class="fa fa-genderless"></i>放弃管理
                </a>
              </li>
              <li class="treeview">
                <a href="javascript:void(0)">
                  <i class="fa fa-caret-right"></i>
                  <span>项目成员管理</span>
                  <span class="pull-right-container">
                    <i class="fa fa-angle-left pull-right"></i>
                  </span>
                </a>
                <ul class="treeview-menu">
                  <li>
                    <a href="javascript:void(0)">
                      <i class="fa fa-genderless"></i>项目成员编制
                    </a>
                  </li>
                  <li class="treeview">
                    <a href="javascript:void(0)">
                      <i class="fa fa-caret-right"></i>
                      <span>项目分布</span>
                      <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                      </span>
                    </a>
                    <ul class="treeview-menu">
                      <li>
                        <a href="javascript:void(0)">
                          <i class="fa fa-genderless"></i>
                          <span>项目地图</span>
                        </a>
                      </li>
                      <li>
                        <a href="javascript:void(0)">
                          <i class="fa fa-genderless"></i>
                          <span>项目成员分布图</span>
                        </a>
                      </li>
                    </ul>
                  </li>
                </ul>
              </li>
              <li>
                <a href="javascript:void(0)">
                  <i class="fa fa-genderless"></i>权限管理
                </a>
              </li>
            </ul>
          </li>

          <li>
            <a href="javascript:void(0)">
              <i class="fa fa-calendar"></i>
              <span>进度管理</span>
            </a>
          </li>
        </ul>
        <!-- /.sidebar-menu -->
      </section>
      <!-- /.sidebar -->
    </aside>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">

      <!-- Main content -->
      <section class="main-content-tab-wrapper">
        <div id="MainContentTab" class="easyui-tabs" data-options="plan:true,fit:true,border:true,onBeforeClose:Main.onBeforeCotentTabClose">

          <div title="&lt;i class='fa fa-home' /&gt; &nbsp;主页">

            <section class="content-header">
              <h1>
                Dashboard <small>Version 2.0</small>
              </h1>
              <ol class="breadcrumb">
                <li>
                  <a href="#">
                    <i class="fa fa-dashboard"></i> Home
                  </a>
                </li>
                <li class="active">Dashboard</li>
              </ol>
            </section>

            <section class="content container-fluid" style="padding: 20px">
              <!-- Info boxes -->
              <div class="row">
                <div class="col-md-3 col-sm-6 col-xs-12">
                  <div class="info-box">
                    <span class="info-box-icon bg-aqua">
                      <i class="ion ion-ios-gear-outline"></i>
                    </span>

                    <div class="info-box-content">
                      <span class="info-box-text">收入合同</span>
                      <span class="info-box-number">
                        ￥43,249.24<small>万元</small>
                      </span>
                    </div>
                    <!-- /.info-box-content -->
                  </div>
                  <!-- /.info-box -->
                </div>
                <!-- /.col -->
                <div class="col-md-3 col-sm-6 col-xs-12">
                  <div class="info-box">
                    <span class="info-box-icon bg-red">
                      <i class="fa fa-google-plus"></i>
                    </span>

                    <div class="info-box-content">
                      <span class="info-box-text">支出合同</span>
                      <span class="info-box-number">
                        ￥69,064.63<small>万元</small>
                      </span>
                    </div>
                    <!-- /.info-box-content -->
                  </div>
                  <!-- /.info-box -->
                </div>
                <!-- /.col -->

                <!-- fix for small devices only -->
                <div class="clearfix visible-sm-block"></div>

                <div class="col-md-3 col-sm-6 col-xs-12">
                  <div class="info-box">
                    <span class="info-box-icon bg-green">
                      <i class="ion ion-ios-cart-outline"></i>
                    </span>

                    <div class="info-box-content">
                      <span class="info-box-text">施工产值</span>
                      <span class="info-box-number">
                        ￥963.04<small>万元</small>
                      </span>
                    </div>
                    <!-- /.info-box-content -->
                  </div>
                  <!-- /.info-box -->
                </div>
                <!-- /.col -->
                <div class="col-md-3 col-sm-6 col-xs-12">
                  <div class="info-box">
                    <span class="info-box-icon bg-yellow">
                      <i class="ion ion-ios-people-outline"></i>
                    </span>

                    <div class="info-box-content">
                      <span class="info-box-text">动态利润</span>
                      <span class="info-box-number">
                        ￥-25,953.29<small>万元</small>
                      </span>
                    </div>
                    <!-- /.info-box-content -->
                  </div>
                  <!-- /.info-box -->
                </div>
                <!-- /.col -->
              </div>
              <!-- /.row -->

              <div class="row">
                <div class="col-md-12">
                  <div class="box">
                    <div class="box-header with-border">
                      <h3 class="box-title">Monthly Recap Report</h3>

                      <div class="box-tools pull-right">
                        <button type="button" class="btn btn-box-tool" data-widget="collapse">
                          <i class="fa fa-minus"></i>
                        </button>
                        <div class="btn-group">
                          <button type="button" class="btn btn-box-tool dropdown-toggle" data-toggle="dropdown">
                            <i class="fa fa-wrench"></i>
                          </button>
                          <ul class="dropdown-menu" role="menu">
                            <li>
                              <a href="#">Action</a>
                            </li>
                            <li>
                              <a href="#">Another action</a>
                            </li>
                            <li>
                              <a href="#">Something else here</a>
                            </li>
                            <li class="divider"></li>
                            <li>
                              <a href="#">Separated link</a>
                            </li>
                          </ul>
                        </div>
                        <button type="button" class="btn btn-box-tool" data-widget="remove">
                          <i class="fa fa-times"></i>
                        </button>
                      </div>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">
                      <div class="row">
                        <div class="col-md-8">
                          <p class="text-center">
                            <strong>Sales: 1 Jan, 2014 - 30 Jul, 2014</strong>
                          </p>

                          <div class="chart">
                            <!-- Sales Chart Canvas -->
                            <canvas id="salesChart" style="height: 180px;"></canvas>
                          </div>
                          <!-- /.chart-responsive -->
                        </div>
                        <!-- /.col -->
                        <div class="col-md-4">
                          <p class="text-center">
                            <strong>Goal Completion</strong>
                          </p>

                          <div class="progress-group">
                            <span class="progress-text">Add Products to Cart</span>
                            <span class="progress-number">
                              <b>160</b>/200
                            </span>

                            <div class="progress sm">
                              <div class="progress-bar progress-bar-aqua" style="width: 80%"></div>
                            </div>
                          </div>
                          <!-- /.progress-group -->
                          <div class="progress-group">
                            <span class="progress-text">Complete Purchase</span>
                            <span class="progress-number">
                              <b>310</b>/400
                            </span>

                            <div class="progress sm">
                              <div class="progress-bar progress-bar-red" style="width: 80%"></div>
                            </div>
                          </div>
                          <!-- /.progress-group -->
                          <div class="progress-group">
                            <span class="progress-text">Visit Premium Page</span>
                            <span class="progress-number">
                              <b>480</b>/800
                            </span>

                            <div class="progress sm">
                              <div class="progress-bar progress-bar-green" style="width: 80%"></div>
                            </div>
                          </div>
                          <!-- /.progress-group -->
                          <div class="progress-group">
                            <span class="progress-text">Send Inquiries</span>
                            <span class="progress-number">
                              <b>250</b>/500
                            </span>

                            <div class="progress sm">
                              <div class="progress-bar progress-bar-yellow" style="width: 80%"></div>
                            </div>
                          </div>
                          <!-- /.progress-group -->
                        </div>
                        <!-- /.col -->
                      </div>
                      <!-- /.row -->
                    </div>
                    <!-- ./box-body -->
                    <div class="box-footer">
                      <div class="row">
                        <div class="col-sm-3 col-xs-6">
                          <div class="description-block border-right">
                            <span class="description-percentage text-green">
                              <i class="fa fa-caret-up"></i> 17%
                            </span>
                            <h5 class="description-header">$35,210.43</h5>
                            <span class="description-text">TOTAL REVENUE</span>
                          </div>
                          <!-- /.description-block -->
                        </div>
                        <!-- /.col -->
                        <div class="col-sm-3 col-xs-6">
                          <div class="description-block border-right">
                            <span class="description-percentage text-yellow">
                              <i class="fa fa-caret-left"></i> 0%
                            </span>
                            <h5 class="description-header">$10,390.90</h5>
                            <span class="description-text">TOTAL COST</span>
                          </div>
                          <!-- /.description-block -->
                        </div>
                        <!-- /.col -->
                        <div class="col-sm-3 col-xs-6">
                          <div class="description-block border-right">
                            <span class="description-percentage text-green">
                              <i class="fa fa-caret-up"></i> 20%
                            </span>
                            <h5 class="description-header">$24,813.53</h5>
                            <span class="description-text">TOTAL PROFIT</span>
                          </div>
                          <!-- /.description-block -->
                        </div>
                        <!-- /.col -->
                        <div class="col-sm-3 col-xs-6">
                          <div class="description-block">
                            <span class="description-percentage text-red">
                              <i class="fa fa-caret-down"></i> 18%
                            </span>
                            <h5 class="description-header">1200</h5>
                            <span class="description-text">GOAL COMPLETIONS</span>
                          </div>
                          <!-- /.description-block -->
                        </div>
                      </div>
                      <!-- /.row -->
                    </div>
                    <!-- /.box-footer -->
                  </div>
                  <!-- /.box -->
                </div>
                <!-- /.col -->
              </div>
              <!-- /.row -->

            </section>

          </div>
        </div>
        <!-- /.easyui-tabs -->
      </section>
      <!-- /.main-content-tab-wrapper -->
    </div>
    <!-- /.content-wrapper -->

    <!-- Main Footer -->
    <footer class="main-footer">
      <!-- To the right -->
      <div class="pull-right hidden-xs"></div>
      <!-- Default to the left -->
      <%-- <strong>Copyright &copy; 2019 <a href="javascript:void(0)">Company</a>.
      </strong> All rights reserved. --%>
    </footer>
  </div>
  <!-- ./wrapper -->

  <jsp:include page="/WEB-INF/jsp/common/include/commonJsVar.jsp" flush="true" />
  <script type="text/javascript" src="<s:url value="/js/util/jquery-1.12.4.min.js"/>"></script>
  <script type="text/javascript" src="<s:url value="/js/util/bootstrap/bootstrap-3.4.1.min.js"/>"></script>
  <script type="text/javascript" src="<s:url value="/js/util/adminlte/adminlte-2.4.15.min.js"/>"></script>
  <script type="text/javascript" src="<s:url value="/js/util/jquery-slimscroll/jquery.slimscroll.min.js"/>"></script>
  <script type="text/javascript" src="<s:url value="/js/util/charts/Chart.min.js"/>"></script>
  <script type="text/javascript" src="<s:url value="/js/util/easyui/jquery.easyui-1.8.2.min.js"/>"></script>
  <s:eval var="langEasyui" expression="T(project.edge.domain.business.SystemConfigSettings).instance.langEasyui" />
  <script type="text/javascript" src="<s:url value="/js/util/easyui/locale/easyui-lang-${langEasyui}.js"/>"></script>
  <script type="text/javascript" src="<s:url value="/js/util/form/jquery.form.min.js"/>"></script>
  <script type="text/javascript" src="<s:url value="/js/util/json2.min.js"/>"></script>
  <script type="text/javascript" src="<s:url value="/js/util/adminlte/dashboard2.js"/>"></script>
  <script type="text/javascript" src="<s:url value="/js/main.js"/>"></script>
</body>
</html>