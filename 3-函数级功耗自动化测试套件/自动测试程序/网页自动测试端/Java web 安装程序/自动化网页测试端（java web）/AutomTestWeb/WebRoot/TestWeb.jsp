<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>My JSP 'hello.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript" src="<%=basePath%>js/jquery-1.8.2.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		var i = 0;
		$("#addBtn").click(function(){
			var option = '<option value="msp">密集计算-Pi值</option><option value="msh">密集计算-图像测合</option><option value="fir">文件读</option><option value="fiw">文件写</option><option value="itd">网络传输-TCP</option><option value=iud>网络传输-UDP</option><option value="iht">网络传输-HTTP</option><option value="ift">网络传输-FTP</option><option value="vpa">多媒体-音频播放</option><option value="vpv">多媒体-视频播放</option><option value="vca">多媒体-音频采集</option><option value="vcv">多媒体-视频采集</option><option value="dia">语音通话</option><option value="mes">短信收发</option><option value="msr">内存操作-读</option><option value="msw">内存操作-写</option><option value="tcm">文字聊天</option><option value="fup">文件上传</option><option value="fdn">文件下载</option><option value="web">网页浏览</option><option value="ipv">在线观看视频</option>';
			
			var innerHtml = '<tr id = "'+i+'" ><td><select name="test['+i+'].name">'+option+
			'</select></td><td><input type="text" name="test['+i+'].time" /></td><td><input type="text" name="test['+i+'].ip"/></td>'+
			'<td><input type="button" value="删除" name="'+i+'_DelBtn"/></td></tr>';
			$("#mainFrame").append(innerHtml);
			i ++;
			$("input[name $='DelBtn']").click(function(){
				var name = $(this).attr("name");
				var trSelect = name.charAt(0);
				$("#"+trSelect+"").remove();
			});
			registerListener();
		});
		registerListener();
		function registerListener(){
			$("input[name $='DelBtn']").click(function(){
				var name = $(this).attr("name");
				var trSelect = name.charAt(0);
				$("#"+trSelect+"").remove();
			});
			$("input[name $='ip']").blur(function(){
				var ip_addr = $(this).val();
				var reg = /^([0,1]?\d{0,2}|2[0-4]\d|25[0-5])\.([0,1]?\d{0,2}|2[0-4]\d|25[0-5])\.([0,1]?\d{0,2}|2[0-4]\d|25[0-5])\.([0,1]?\d{0,2}|2[0-4]\d|25[0-5])$/;
				if(!reg.test(ip_addr)){
					alert("ip地址格式错误！");
				}
			});
		}
		
		
		
	});
</script>
<style type="text/css">
	#mainFrame{width:1200;text-align:center;}
	#mainFrame tr{border:solid 1px #acb;}
	#tableHead{background:#acb;}
	#addBtn{border:solid 1px #acb;width:60px;height:25px;color:#00f;}
	#submitBtn{border:solid 1px #acb;width:60px;height:25px;margin: 10px 102px 10px 102px ;}
	#submitBtn:hover{border:solid 1px #EEE;background:#ff0;}
	#save{margin:10px 0 10px 200px;color:#ffo;}
</style>
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>
	<FORM action="<%=basePath%>storeData" method="post" id="form">
		<table id = "mainFrame" cellpadding="15">
			<tr id="tableHead">
				<td>测试项</td>
				<td>测试时间(ms)</td>
				<td>IP</td>
				<td>操作</td>
			</tr>
		
		</table>
		<div id="save">
			<label for="filename">保存文件名:</label>
			<input type="text" id="filename" name="fileName" /><br/>
			<input type="button" value="添加" id="addBtn"/>
			<input type="submit" value="提交" id="submitBtn"/>
		
		</div>
	</FORM>
</body>
</html>
