<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!-- Left side column. contains the logo and sidebar -->
<aside class="main-sidebar">

  <!-- sidebar: style can be found in sidebar.less -->
  <section class="sidebar">

    <!-- Sidebar Menu -->
    <%-- 把.sidebar-menu的data-animation-speed属性改为0，以防止展开菜单时发生重叠 --%>
    <ul class="sidebar-menu" data-widget="tree" data-animation-speed="0" data-accordion="true">
      <li class="header">
        <s:message code="ui.menu.title.main.nav" />
      </li>
      <!-- Optionally, you can add icons to the links -->
      <c:forEach var="m" items="${pageList}" varStatus="s">
        <%-- 一级菜单 --%>
        <c:choose>
          <c:when test="${m.leaf}">
            <%-- 等同于判断children是否为empty，即，当且仅当children是空empty时，leaf才是true --%>
            <li>
              <a href="javascript:void(0)"
                <c:choose><c:when test="${empty m.handler}">onclick="Main.openTab('${m.name}', '<s:url value="${m.href}" />')"</c:when><c:otherwise>${m.handler}</c:otherwise></c:choose>
              >
                <i class="${m.icon}"></i>
                <span>${m.name}</span>
              </a>
            </li>
          </c:when>
          <c:otherwise>
            <li class="treeview">
              <a href="javascript:void(0)">
                <i class="${m.icon}"></i>
                <span>${m.name}</span>
                <span class="pull-right-container">
                  <i class="fa fa-angle-left pull-right"></i>
                </span>
              </a>
              <ul class="treeview-menu">
                <c:forEach var="mm" items="${m.children}" varStatus="ss">
                  <%-- 二级菜单 --%>
                  <c:choose>
                    <c:when test="${mm.leaf}">
                      <%-- 等同于判断children是否为empty，即，当且仅当children是空empty时，leaf才是true --%>
                      <li>
                        <a href="javascript:void(0)"
                          <c:choose><c:when test="${empty mm.handler}">onclick="Main.openTab('${mm.name}', '<s:url value="${mm.href}" />')"</c:when><c:otherwise>${mm.handler}</c:otherwise></c:choose>
                        >
                          <i class="${mm.icon}"></i>
                          <span>${mm.name}</span>
                        </a>
                      </li>
                    </c:when>
                    <c:otherwise>
                      <li class="treeview">
                        <a href="javascript:void(0)">
                          <i class="${mm.icon}"></i>
                          <span>${mm.name}</span>
                          <span class="pull-right-container">
                            <i class="fa fa-angle-left pull-right"></i>
                          </span>
                        </a>
                        <ul class="treeview-menu">
                          <c:forEach var="mmm" items="${mm.children}" varStatus="sss">
                            <%-- 三级菜单 --%>
                            <c:choose>
                            	<c:when test="${mmm.leaf}">
                            		<li>
		                              <a href="javascript:void(0)"
		                                <c:choose><c:when test="${empty mmm.handler}">onclick="Main.openTab('${mmm.name}', '<s:url value="${mmm.href}" />')"</c:when><c:otherwise>${mmm.handler}</c:otherwise></c:choose>
		                              >
		                                <i class="${mmm.icon}"></i>
		                                <span>${mmm.name}</span>
		                              </a>
		                            </li>
                            	</c:when>
                            	<c:otherwise>
                            		 <li class="treeview">
				                        <a href="javascript:void(0)">
				                          <i class="${mmm.icon}"></i>
				                          <span>${mmm.name}</span>
				                          <span class="pull-right-container">
				                            <i class="fa fa-angle-left pull-right"></i>
				                          </span>
				                        </a>
				                        <ul class="treeview-menu">
				                          <c:forEach var="mmmm" items="${mmm.children}" varStatus="sss">
				                            <%-- 四级菜单 --%>
				                            <li>
				                              <a href="javascript:void(0)"
				                                <c:choose><c:when test="${empty mmmm.handler}">onclick="Main.openTab('${mmmm.name}', '<s:url value="${mmmm.href}" />')"</c:when><c:otherwise>${mmmm.handler}</c:otherwise></c:choose>
				                              >
				                                <i class="${mmmm.icon}"></i>
				                                <span>${mmmm.name}</span>
				                              </a>
				                            </li>
				                          </c:forEach>
				                        </ul>
				                      </li>
                            	</c:otherwise>
                            </c:choose>
                            
                          </c:forEach>
                        </ul>
                      </li>
                    </c:otherwise>
                  </c:choose>
                </c:forEach>
              </ul>
            </li>
          </c:otherwise>
        </c:choose>
      </c:forEach>

    </ul>
    <!-- /.sidebar-menu -->
  </section>
  <!-- /.sidebar -->
</aside>
