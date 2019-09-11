<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
  <head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<!-- uc强制竖屏 -->
<meta name="screen-orientation" content="portrait">

<!-- UC强制全屏 --> 
<meta name="full-screen" content="yes">

<!-- UC应用模式 --> 
<meta name="browsermode" content="application">

<!-- QQ强制竖屏 -->
<meta name="x5-orientation" content="portrait">

<!-- QQ强制全屏 -->
<meta name="x5-fullscreen" content="true">

<!-- QQ应用模式 -->
<meta name="x5-page-mode" content="app">
  </head>
  <body>
  <h2>${news.title }</h2>
  <h6>
    <fmt:formatDate var="reTime" value='${news.createtime}' pattern='yyyy-MM-dd HH:ss:mm' />
    ${reTime}</h6>
  <div id="notetext">
    ${news.content }
  </div>
  </body>
  <script type="text/javascript">
      var obj = document.getElementById("notetext");
      obj.innerHTML = obj.innerText;//这样重新设置html代码为解析后的格式
  </script>
</html>
