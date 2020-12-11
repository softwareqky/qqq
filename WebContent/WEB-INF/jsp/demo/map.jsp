<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<head>
<meta charset="utf-8">
<title>点击地图获取经纬度</title>
<script type="text/javascript" src="<s:url value="/js/util/jquery-1.12.4.min.js"/>"></script>
</head>
<body>
  <button onclick="clickBtn()">Click</button>
  <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
  <div id="mapWrapper" style="display: none;">
    <div id="map" style="height: 250px; width: 100%;"></div>
  </div>

  <script type="text/javascript" src="https://webapi.amap.com/maps?v=1.4.15&key=b5b48b067a3b18cdbaa54c9adc2d0a94"></script>
  <script type="text/javascript">
            $(function() {
                var map = new AMap.Map('map', {
                    zoom: 7,//级别
                    center: [116.398, 39.907],//中心点坐标
                    viewMode: '3D'//使用3D视图
                });
                map.setDefaultCursor("crosshair");

                var marker;
                map.on('click', function(e) {

                    if (marker) {
                        marker.setMap(null);
                        marker = null;
                    }
                    marker = new AMap.Marker({
                        icon: "https://webapi.amap.com/theme/v1.3/markers/n/mark_b.png",
                        position: [e.lnglat.getLng(), e.lnglat.getLat()]
                    });
                    marker.setMap(map);
                    alert("经度：" + e.lnglat.getLng() + "纬度：" + e.lnglat.getLat())
                });
            });

            function clickBtn() {
                var options = {
                    url: '<s:url value="/demo/map.htm"/>',
                    type: 'GET',
                    dataType: 'html',
                    success: function(response, statusText, xhr, jqForm) {
                        $('#mapWrapper').css('display', 'block');
                    }
                };
                $.ajax(options);
            }
        </script>
</body>