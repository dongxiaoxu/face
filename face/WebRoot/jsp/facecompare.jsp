<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>人脸识别</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="人脸识别">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src="/face/js/jquery-3.1.1.js"></script>
	<script src="/face/js/face.js"></script>
	<script type="text/javascript">
	    show("video");
	    $(document).ready(function() {
	        (function() {
	            "use strict";
	            var video, $output;
	            var scale = 0.25;
	            var initialize = function() {
	                $output = $("#output");
	                video = $("#video").get(0);
	                $("#capture").click(captureImage);
	              // setInterval(captureImage, 3000);
	            };
	            var captureImage = function() {
	                var canvas = document.createElement("canvas");
	                canvas.width = video.videoWidth * scale;
	                canvas.height = video.videoHeight * scale;
	                canvas.getContext('2d').drawImage(video, 0, 0, canvas.width, canvas.height);
	                var img = document.createElement("img");
	                img.src= canvas.toDataURL();
	                $output.prepend(img); 
	  				var base64Data = canvas.toDataURL();
					base64Data = base64Data.substr(22);
					$.post("/face/face/facecompare",{img:base64Data},function(result){
	   					$("#output").html("相似度：" + result);
	  				});
	  				
	            };
	            $(initialize);
	        }());
	
	    });
	
	
	
	</script>
  </head>
  
  <body style="background-image:url(/face/resources/background2.jpg);background-position:center; background-repeat:repeat-y">
	  <center> 
	    <video id="video"></video>
	    <br/>
		<button id="capture">捕获</button>
		<div id="img"></div>
		<div id="output"></div>
	  </center>
  </body>
</html>
