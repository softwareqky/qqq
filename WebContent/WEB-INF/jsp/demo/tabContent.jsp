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
</head>
<body>
  <%-- 演示tab内容页的写法。包括EasyuiHiddenWrapper和ContentScriptWrapper这两个div的位置，ContentScriptWrapper的内容将被直接丢弃，期望被执行的JavaScript应当放在</body>结束前 --%>

  <div class="container-fluid">
    <div class="row">
      <div class="col-sm-12 content-header">
        <section class="content-header">
          <h1>
            Example page header <small>Subtext for header</small>
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
      </div>
    </div>

    <div class="row row-gapped-top-s">
      <div class="col-sm-6 col-xs-12">
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
      <div class="col-sm-6 col-xs-12">
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
    </div>

    <div class="row row-gapped-top-s">
      <div class="col-sm-12">

        <div class="filter-wrapper">

          <div class="filter-form-control-row">
            <div class="filter-form-group">
              <span class="filter-control-label">字段A</span>
              <select class="easyui-combobox" data-options="height:32,width:120,panelMinHeight:25,panelMaxHeight:250,panelHeight:null,editable:false">
                <option value="">&nbsp;</option>
                <option value="OptionA">OptionA</option>
                <option value="OptionB">OptionB</option>
              </select>
            </div>
            <div class="filter-form-group">
              <span class="filter-control-label">字段B</span>
              <input type="text" class="easyui-textbox" maxlength="20" data-options="prompt:'最大长度:20',width:150,height:32,validType:['length[0,20]']" />
            </div>
            <div class="filter-form-group">
              <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" role="button">检索</a>
            </div>
          </div>
          <!-- /.filter-form-control-row -->

          <div class="filter-tag-row">
            <div class="filter-tag-box filter-tag-box-hover">
              <div class="filter-tag-inner-box">
                <div class="filter-tags">
                  <a class="filter-tag" data-name="nameTextB">
                    <span class="filter-tag-text">商品名称2</span>
                    <span class="filter-tag-close"></span>
                  </a>
                  <a class="filter-tag" data-name="unitPriceA">
                    <span class="filter-tag-text">商品价格1</span>
                    <span class="filter-tag-close"></span>
                  </a>
                  <a class="filter-tag" data-name="priceUnitA_">
                    <span class="filter-tag-text">计价方式1</span>
                    <span class="filter-tag-close"></span>
                  </a>
                  <a class="filter-tag" data-name="nameTextB">
                    <span class="filter-tag-text">商品名称2</span>
                    <span class="filter-tag-close"></span>
                  </a>
                  <a class="filter-tag" data-name="unitPriceA">
                    <span class="filter-tag-text">商品价格1</span>
                    <span class="filter-tag-close"></span>
                  </a>
                  <a class="filter-tag" data-name="priceUnitA_">
                    <span class="filter-tag-text">计价方式1</span>
                    <span class="filter-tag-close"></span>
                  </a>
                  <a class="filter-tag" data-name="nameTextB">
                    <span class="filter-tag-text">商品名称2</span>
                    <span class="filter-tag-close"></span>
                  </a>
                  <a class="filter-tag" data-name="unitPriceA">
                    <span class="filter-tag-text">商品价格1</span>
                    <span class="filter-tag-close"></span>
                  </a>
                  <a class="filter-tag" data-name="priceUnitA_">
                    <span class="filter-tag-text">计价方式1</span>
                    <span class="filter-tag-close"></span>
                  </a>
                  <a class="filter-tag" data-name="nameTextB">
                    <span class="filter-tag-text">商品名称2</span>
                    <span class="filter-tag-close"></span>
                  </a>
                  <a class="filter-tag" data-name="unitPriceA">
                    <span class="filter-tag-text">商品价格1</span>
                    <span class="filter-tag-close"></span>
                  </a>
                  <a class="filter-tag" data-name="priceUnitA_">
                    <span class="filter-tag-text">计价方式1</span>
                    <span class="filter-tag-close"></span>
                  </a>
                  <a class="filter-tag" data-name="nameTextB">
                    <span class="filter-tag-text">商品名称2</span>
                    <span class="filter-tag-close"></span>
                  </a>
                  <a class="filter-tag" data-name="unitPriceA">
                    <span class="filter-tag-text">商品价格1</span>
                    <span class="filter-tag-close"></span>
                  </a>
                  <a class="filter-tag" data-name="priceUnitA_">
                    <span class="filter-tag-text">计价方式1</span>
                    <span class="filter-tag-close"></span>
                  </a>
                  <a class="filter-tag" data-name="nameTextB">
                    <span class="filter-tag-text">商品名称2</span>
                    <span class="filter-tag-close"></span>
                  </a>
                  <a class="filter-tag" data-name="unitPriceA">
                    <span class="filter-tag-text">商品价格1</span>
                    <span class="filter-tag-close"></span>
                  </a>
                  <a class="filter-tag" data-name="priceUnitA_">
                    <span class="filter-tag-text">计价方式1</span>
                    <span class="filter-tag-close"></span>
                  </a>
                  <a class="filter-tag" data-name="nameTextB">
                    <span class="filter-tag-text">商品名称2</span>
                    <span class="filter-tag-close"></span>
                  </a>
                  <a class="filter-tag" data-name="unitPriceA">
                    <span class="filter-tag-text">商品价格1</span>
                    <span class="filter-tag-close"></span>
                  </a>
                  <a class="filter-tag" data-name="priceUnitA_">
                    <span class="filter-tag-text">计价方式1</span>
                    <span class="filter-tag-close"></span>
                  </a>
                  <a class="filter-tag" data-name="nameTextB">
                    <span class="filter-tag-text">商品名称2</span>
                    <span class="filter-tag-close"></span>
                  </a>
                  <a class="filter-tag" data-name="unitPriceA">
                    <span class="filter-tag-text">商品价格1</span>
                    <span class="filter-tag-close"></span>
                  </a>
                  <a class="filter-tag" data-name="priceUnitA_">
                    <span class="filter-tag-text">计价方式1</span>
                    <span class="filter-tag-close"></span>
                  </a>
                  <a class="filter-tag" data-name="nameTextB">
                    <span class="filter-tag-text">商品名称2</span>
                    <span class="filter-tag-close"></span>
                  </a>
                  <a class="filter-tag" data-name="unitPriceA">
                    <span class="filter-tag-text">商品价格1</span>
                    <span class="filter-tag-close"></span>
                  </a>
                  <a class="filter-tag" data-name="priceUnitA_">
                    <span class="filter-tag-text">计价方式1</span>
                    <span class="filter-tag-close"></span>
                  </a>
                </div>
                <div class="filter-tag-pin-wrapper">
                  <a class="filter-tag-pin filter-tag-pin-selected">
                    <span class="filter-tag-pin-icon"></span>
                  </a>
                </div>
              </div>
            </div>
          </div>
          <!-- /.filter-tag-row -->

        </div>
        <!-- /.filter-wrapper -->

      </div>
      <!-- /.col -->
    </div>
    <!-- /.row -->

    <div class="row row-gapped-top-s">
      <div class="col-sm-12" style="min-height: 250px;">
        <table id="_Datagrid" class="easyui-datagrid"
          data-options="toolbar:'#_DatagridToolbar',
                multiSort:false,sortName:'name',sortOrder:'asc',
                singleSelect:false,pagination:true,rownumbers:true,pageSize:30,pageList:[30,50],idField:'id',
                autoRowHeight:true,nowrap:true,striped:true,fit:true,fitColumns:false"
        >
          <thead data-options="frozen:true">
            <tr>
              <th data-options="field:'ck',checkbox:true"></th>
              <th data-options="field:'name',width:200,align:'center',sortable:true">名称</th>
              <th data-options="field:'price',width:250,align:'center',sortable:true">价格</th>
              <th data-options="field:'region',width:220,align:'center',sortable:true">区域</th>
            </tr>
          </thead>
          <thead>
            <tr>
              <th data-options="field:'category',width:300,align:'center',sortable:true">分类</th>
              <th data-options="field:'remark',width:600,align:'center',sortable:true">备注</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>name1</td>
              <td>12.9</td>
              <td>001</td>
              <td>2323</td>
              <td></td>
            </tr>
            <tr>
              <td>name2</td>
              <td>25.9</td>
              <td>002</td>
              <td>4612</td>
              <td></td>
            </tr>
          </tbody>
        </table>
        <div id="_DatagridToolbar">
          <table cellpadding="0" cellspacing="0">
            <tbody>
              <tr>
                <td>
                  <div class="datagrid-btn-separator" style="margin-left: 5px;"></div>
                </td>
                <td>
                  <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:'true'" onclick="EasyDialogUtils.openAddFormDialog('#_FormDialog');">新建</a>
                </td>
                <td>
                  <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:'true'" onclick="EasyDialogUtils.openAddFormDialog('#_FormDialogA');">修改</a>
                </td>
                <td>
                  <div class="datagrid-btn-separator" style="margin-left: 5px;"></div>
                </td>
                <td>
                  <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:'true'" onclick="alert('a');">删除</a>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>

    <div class="row row-gapped-top-s">
      <div class="col-sm-12">

        <div class="box box-info">

          <div class="box-header with-border">
            <h3 class="box-title">Default Box Example</h3>

            <div class="box-tools pull-right">
              <!-- Buttons, labels, and many other things can be placed here! -->
              <!-- Here is a label for example -->
              <span class="label label-primary">Label</span>
            </div>
          </div>
          <!-- /.box-header -->

          <div class="box-body">
            <div class="row">The body of the box</div>
            <!-- /.row -->
          </div>
          <!-- ./box-body -->

          <div class="box-footer">
            <div class="row">The footer of the box</div>
          </div>
          <!-- /.box-footer -->

        </div>
        <!-- /.box -->

      </div>
      <!-- /.col -->
    </div>
    <!-- /.row -->

  </div>
  <!-- /.container-fluid -->

  <div class="hidden" id="EasyuiHiddenWrapper">

    <div class="hidden">
      <div id="_FormDialog" class="easyui-dialog" style="padding: 10px;"
        data-options="title:'新建',closed:true,buttons:'#_FormDialogButtons',iconCls:'icon-dialog-edit',maxHeight:480,width:480,modal:true,onMove:MainUtils.onDialogMove"
      >

        <form action="" method="post">
          <input type="hidden" name="id" />
          <table cellpadding="0" cellspacing="0" width="100%">
            <tr>
              <td width="37%" class="label">字段A</td>
              <td width="3%">
                <font class="required">*</font>
              </td>
              <td width="60%">
                <select class="easyui-combobox" data-field-widget="combobox" data-options="height:34,width:235,panelMinHeight:25,panelMaxHeight:250,panelHeight:null,editable:false,required:true">
                  <option value="">&nbsp;</option>
                  <option value="OptionA">OptionA</option>
                  <option value="OptionB">OptionB</option>
                </select>
              </td>
            </tr>
            <tr>
              <td width="37%" class="label">字段B</td>
              <td width="3%">
                <font class="required">*</font>
              </td>
              <td width="60%">
                <input type="text" class="easyui-textbox" data-field-widget="textbox" maxlength="20" data-options="prompt:'最大长度:20',width:235,height:34,required:true,validType:['length[0,20]']" />
              </td>
            </tr>
          </table>
        </form>
      </div>

      <%-- 新建/修改弹出画面的按钮 --%>
      <div id="_FormDialogButtons">
        <%-- 新建用按钮 --%>
        <div>
          <div class="dlg-checkbox">
            <input type="checkbox" value="1" data-copy-reserve-checkbox />
            <span>复制</span>
          </div>
          <div class="dlg-button">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeFormDialog('#_FormDialog');" role="button">退出 </a>
          </div>
          <div class="dlg-button">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveFormData('', '#_Form', true, '#_Datagrid', '#_FormDialog', false);" role="button">确定</a>
          </div>
          <div class="dlg-button">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveFormData('', '#_Form', true, '#_Datagrid', null, false);" role="button">确定并添加</a>
          </div>
          <div class="clear"></div>
        </div>
      </div>
    </div>

    <div class="hidden">
      <div id="_FormDialogA" class="easyui-dialog" style="padding: 10px;"
        data-options="title:'新建AA',closed:true,buttons:'#_FormDialogAButtons',iconCls:'icon-dialog-edit',maxHeight:480,width:480,modal:true,onMove:MainUtils.onDialogMove"
      >

        <form action="" method="post">
          <input type="hidden" name="id" />
          <table cellpadding="0" cellspacing="0" width="100%">
            <tr>
              <td width="37%" class="label">字段AA</td>
              <td width="3%">
                <font class="required">*</font>
              </td>
              <td width="60%">
                <select class="easyui-combobox" data-field-widget="combobox" data-options="height:34,width:235,panelMinHeight:25,panelMaxHeight:250,panelHeight:null,editable:false,required:true">
                  <option value="">&nbsp;</option>
                  <option value="OptionA">OptionA</option>
                  <option value="OptionB">OptionB</option>
                </select>
              </td>
            </tr>
            <tr>
              <td width="37%" class="label">字段BB</td>
              <td width="3%">
                <font class="required">*</font>
              </td>
              <td width="60%">
                <input type="text" class="easyui-textbox" data-field-widget="textbox" maxlength="20" data-options="prompt:'最大长度:20',width:235,height:34,required:true,validType:['length[0,20]']" />
              </td>
            </tr>
          </table>
        </form>
      </div>

      <%-- 新建/修改弹出画面的按钮 --%>
      <div id="_FormDialogAButtons">
        <%-- 新建用按钮 --%>
        <div>
          <div class="dlg-checkbox">
            <input type="checkbox" value="1" data-copy-reserve-checkbox />
            <span>复制</span>
          </div>
          <div class="dlg-button">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeFormDialog('#_FormDialogA');" role="button">退出 </a>
          </div>
          <div class="dlg-button">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveFormData('', '#_Form', true, '#_Datagrid', '#_FormDialog', false);" role="button">确定</a>
          </div>
          <div class="dlg-button">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveFormData('', '#_Form', true, '#_Datagrid', null, false);" role="button">确定并添加</a>
          </div>
          <div class="clear"></div>
        </div>
      </div>
    </div>

  </div>
  <!-- /#EasyuiHiddenWrapper -->

  <div class="hidden" id="ContentScriptWrapper">
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
    <script type="text/javascript" src="<s:url value="/js/main.js"/>"></script>


    <script type="text/javascript">
                    //<![CDATA[

                    // 放在#ContentScriptWrapper内的js不会被执行
                    $(function() {

                        console.log('Scripts in #ContentScriptWrapper will not be invoked. '
                                + new Date().getTime());
                    });
                    //]]>
                </script>
  </div>

  <script type="text/javascript">
            //<![CDATA[

            // 放在此处的js将被执行
            $(function() {

                console.log('isolated scripts will run. ' + new Date().getTime());
            });
            //]]>
        </script>
</body>
</html>