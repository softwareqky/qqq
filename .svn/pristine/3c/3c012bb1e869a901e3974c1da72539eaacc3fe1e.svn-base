/* Deprecated */
$(function() {

    var map = new BMap.Map("map"); // 创建Map实例
    map.centerAndZoom("北京", 6); // 初始化地图,用城市名设置地图中心点
    map.addControl(new BMap.MapTypeControl({
        mapTypes: [BMAP_NORMAL_MAP, BMAP_SATELLITE_MAP]
    })); // 添加地图类型控件
    map.addControl(new BMap.NavigationControl()); // 添加平移缩放控件
    map.addControl(new BMap.ScaleControl()); // 添加比例尺控件
    map.addControl(new BMap.OverviewMapControl()); // 添加缩略地图控件
    map.enableScrollWheelZoom(); // 开启鼠标滚轮缩放
    
    var userCode = '06000012';
    $.ajax({
        url: BASE_URL + "/map/data.json",
        type: "get",
        data: { "user" : userCode},
        dataType: "json",
        success: function (data) {
            var res = eval(eval(data.message));
            console.log(res);
            $.each(res, function(index, x){
                var myGeo = new BMap.Geocoder();
                var place = x["Key"].split('_')[1];
                var city = x["Key"].split('_')[0];
                myGeo.getPoint(place,function(point){
                    if(point){
                        var prjInfoList = x["Value"];
                        var icon;
                        if(prjInfoList[0].PrjState == "待开工" || prjInfoList[0].PrjState == "中标"){
                            icon = new BMap.Icon(BASE_URL + "/img/map-dot-blue.png", new BMap.Size(19, 33), {
                                imageSize: new BMap.Size(19, 33),
                                offset: new BMap.Size(100, 100) // 指定定位位置
                            });
                        }
                        else if (prjInfoList[0].PrjState == "在建") {
                            icon = new BMap.Icon(BASE_URL + "/img/map-dot-red.png", new BMap.Size(19, 33), {
                                imageSize: new BMap.Size(19, 33),
                                offset: new BMap.Size(100, 100) // 指定定位位置
                            });
                        }
                        else if (prjInfoList[0].PrjState == "停工") {
                            icon = new BMap.Icon(BASE_URL + "/img/map-dot-yellow.png", new BMap.Size(19, 33), {
                                imageSize: new BMap.Size(19, 33),
                                offset: new BMap.Size(100, 100) // 指定定位位置
                            });
                        }
                        else if (prjInfoList[0].PrjState == "竣工") {
                            icon = new BMap.Icon(BASE_URL + "/img/map-dot-green.png", new BMap.Size(19, 33), {
                                imageSize: new BMap.Size(19, 33),
                                offset: new BMap.Size(100, 100) // 指定定位位置
                            });
                        } 
                        else if (prjInfoList[0].PrjState == "投标") {
                            icon = new BMap.Icon(BASE_URL + "/img/map-dot-gray.png", new BMap.Size(19, 33), {
                                imageSize: new BMap.Size(19, 33),
                                offset: new BMap.Size(100, 100) // 指定定位位置
                            });
                        } 

                        var marker = new window.BMap.Marker(point,{icon:icon}); // 按照地图点坐标生成标记
                        map.addOverlay(marker);// 添加标记点到地图上
                        // marker[j].setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
                        
                        // 拼接标注信息内容
                        // var txt = "<p class='map_content_p'>" + i["PrjName"] + "</p><a
                        // class='map_content_a' data-url='/PrjManager/PrjInfoView.aspx?&ic=" +
                        // i["PrjGuid"] + "'>查看详情</a>"
                        // var infoWindow = new BMap.InfoWindow(txt, opts);//创建信息窗口对象
                        var tableInfo;
                        var title1 = "";
                        for (var i = 0; i < prjInfoList.length; i++) {
                            title1 = prjInfoList[i].PrjName;
                            // console.log(prjInfoList[i]);
                            if (i != 0) {
                                tableInfo += '------------------分割线----------------';
                            }
                            if (i == 0) {
                                tableInfo = '<table style=" text-align: center; width: 100%;font-size:10px; margin-top:10px;">';
                            } else {
                                tableInfo += '<table style=" text-align: center; width: 100%;font-size:10px; margin-top:10px;">';
                            }
                            tableInfo += '<tr><td rowspan="2" colspan="2" style="text-align: left; width: 100%; height: 22px; font-weight:bold;">项目名称：' + prjInfoList[i].PrjName + '</td></tr><tr></tr>';
                            if (prjInfoList[i].ContractPrice > 100000000) {
                                tableInfo += "<tr><td style='text-align: left; width: 70%; height: 22px;'>合同额：<a href='#' style=' ' >" + (prjInfoList[i].ContractPrice / 100000000).toFixed(3) + "亿元</a></td><td></td></tr>";
                            }
                            else if (prjInfoList[i].ContractPrice > 10000) {
                                tableInfo += "<tr><td style='text-align: left; width: 70%; height: 22px;'>合同额：<a href='#' style=' ' >" + (prjInfoList[i].ContractPrice / 10000).toFixed(3) + "万元</a></td><td></td></tr>";
                            } else {
                                tableInfo += "<tr><td style='text-align: left; width: 70%; height: 22px;'>合同额：<a href='#' style=' ' >" + prjInfoList[i].ContractPrice  + "元</a></td><td></td></tr>";
                            }
                             if (prjInfoList[i].PrjManagerPhone != "") {
                                tableInfo += '<tr><td style="text-align: left; width: 70%; height: 22px;">项目经理：' + prjInfoList[i].PrjManager + '（' + prjInfoList[i].PrjManagerPhone + '）</td><td></td></tr>';
                            } else {
                                tableInfo += '<tr><td style="text-align: left; width: 70%; height: 22px;">项目经理：' + prjInfoList[i].PrjManager + '</td><td></td></tr>';
                            }
                            tableInfo += '<tr><td colspan="2" style="text-align: left; width: 100%; height: 22px;"><table><tr><td>总体进度：</td><td><div id="a" style="background-color: #ccc; float: none; width: 100px; height: 15px; border-radius: 5px;">';
                            tableInfo += '<div id="b" style="background-color: #00DDDD; float: none; width:' + prjInfoList[i].percent + '%; height: 15px; border-radius: 5px;"></div></div></td><td style=" color:#ccc;">&nbsp;' + prjInfoList[i].percent + '%</td></tr></table></td></tr>';
                            tableInfo += "<tr><td style='text-align: left; width: 70%; height: 22px;'>现场照片：<a href='#' class='map_content_a' style='text-decoration: underline;' data-type='LinkReportPhoto' data-prjid='" + prjInfoList[i].PrjID + "' data-prjname='" + prjInfoList[i].PrjName + "'>形象进度</a></td><td></td></tr>";
                            tableInfo += "<tr><td style='text-align: left; width: 70%; height: 22px;'>质量事故：<a href='#' class='map_content_a' style=' color:red;font-size:13px;text-decoration: underline;' data-type='LinkQuaity' data-prjid='" + prjInfoList[i].PrjID + "'>" + prjInfoList[i].QualityNum + '</a>&nbsp;&nbsp;起</td><td></td></tr>';
                            tableInfo += "<tr><td style='text-align: left; width: 70%; height: 22px;'>安全事故：<a href='#' class='map_content_a' style=' color:red;font-size:13px;text-decoration: underline;' data-type='LinkSafety' data-prjid='" + prjInfoList[i].PrjID + "'>" + prjInfoList[i].SafetyNum + '</a>&nbsp;&nbsp;起</td><td></td></tr>';
                            tableInfo += "<tr><td colspan='2' style='text-align: left; width: 100%; height: 22px;'>颜色标识：<img class='map_img_icon' src='" + BASE_URL + "/img/map-dot-red.png' />&nbsp;在建&nbsp;&nbsp;<img class='map_img_icon' src='" + BASE_URL + "/img/map-dot-gray.png' />&nbsp;投标&nbsp;&nbsp;<img class='map_img_icon' src='" + BASE_URL + "/img/map-dot-green.png' />&nbsp;竣工&nbsp;&nbsp;<img class='map_img_icon' src='" + BASE_URL + "/img/map-dot-blue.png' />&nbsp;待开工&nbsp;&nbsp;<img class='map_img_icon' src='" + BASE_URL + "/img/map-dot-yellow.png' />&nbsp;停工</td></tr>";
                            tableInfo += '</table>';
                        }

                        var opts = {
                            width: 300,  // 信息窗口宽度
                            // height: 300, // 信息窗口高度
                            // title: title1 // 信息窗口标题
                        }
                        var infoWindow = new BMap.InfoWindow(tableInfo, opts);// 创建信息窗口对象
                        marker.addEventListener("click", function () { // 监听标记点点击事件
                            this.openInfoWindow(infoWindow);// 显示信息窗口
                            $(".map_content_a").off("click").on("click",function(){// 绑定a标签（查看详情）点击事件
                                
                                var type = $(this).attr("data-type");
                                console.log(type);
                                var url = "";
                                switch(type){
                                    case "LinkReportPhoto":
                                        var PrjID = $(this).attr("data-prjid");
                                        var PrjName = $(this).attr("data-prjname");
                                        url = "/BudgetManage/Construct/ReportPhoto.aspx?PrjID=" + PrjID + "&PrjName=" + PrjName;
                                        toolbox_oncommand(url, "拍照监控");
                                        break;
                                    case "LinkQuaity":
                                        var PrjID = $(this).attr("data-prjid");
                                        url = "/EPC/QuaitySafety/ProjectSupervise.aspx?Flag=Q&Type=List&TypeId=6&CA=2&PrjState=0&prjId=" + PrjID;
                                        toolbox_oncommand(url, "质量事故");
                                        break;
                                    case "LinkSafety":
                                        var PrjID = $(this).attr("data-prjid");
                                        url = "/EPC/QuaitySafety/ProjectSupervise.aspx?Flag=S&Type=List&TypeId=6&CA=5&PrjState=0&prjId=" + PrjID;
                                        toolbox_oncommand(url, "安全事故");
                                        break;
                                }
                                
                            });
                        });

                    }
                },city);
            });
        }, error: function () {

        }
    });
});