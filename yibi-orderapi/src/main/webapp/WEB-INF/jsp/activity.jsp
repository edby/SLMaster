<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport"
        content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>ODIN认购</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/activity.css">
    <script src="${pageContext.request.contextPath}/js/amfe-flexible.min.js"></script>
</head>
<body>
    <div class="nav">
        <div class="arrow arrow-left" id="back"></div>
        <h1>ODIN认购</h1>
    </div>
    <div class="container">
        <h2><img src="${pageContext.request.contextPath}/images/title.png" alt=""></h2>
        <div class="rush">
            <div class="rush-item">
                <i></i>
                <span class="text" id="explain">抢购说明</span>
            </div>
            <div class="rush-item">
                <i></i>
                <span class="text">抢购提醒</span>
            </div>
        </div>
        <div class="progress" id="progress" style="display: none">
            <img src="${pageContext.request.contextPath}/images/progress.png" class="progress-bg" alt="">
            <div class="progress-percent">
                <img src="${pageContext.request.contextPath}/images/percent.png" class="progress-percent-img" id="progress-percent-img" style="width: 30%" alt="">
                <img src="${pageContext.request.contextPath}/images/light.png" class="progress-percent-light" id="progress-percent-light" style="margin-left: 30%" alt="">
            </div>
        </div>
        <p class="time" id="hot" style="display: none">※第<span data-key="ODIN_BUYING_NUMBER"></span>期正在火爆抢购中<img class="icon" src="${pageContext.request.contextPath}/images/qg.png" alt=""></p>
        <p class="time" id="await">※第<span data-key="ODIN_BUYING_NUMBER"></span>期已售罄，距下次销售倒计时<b id="time"></b></p>
        <div class="imp-wrap">
            <p class="title">本期 1 ODIN ≈ <span data-key="ODIN_BUYING_THIS_PRICE"></span> ECN</p>
            <div class="tips">
                <p>下期 1 ODIN ≈ <span data-key="ODIN_BUYING_NEXT_PRICE"></span> ECN</p>
                <p>当前交易价格 1 ODIN ≈ <span data-key="ODIN_BUYING_NOW_ORDER_PRICE"></span> ECN</p>
            </div>
        </div>
        <div class="price">
            <p class="price-title">选择购买金额：</p>
            <div class="price-active"> 
                <button class="btn-default btn-active" data-amount="0"><span></span> <i>ECN</i></button>
            </div>
            <div class="price-group" id="amount">
                <button class="btn-default" data-amount="1"><span></span> <i>ECN</i></button>
                <button class="btn-default" data-amount="2"><span></span> <i>ECN</i></button>
                <button class="btn-default" data-amount="3"><span></span> <i>ECN</i></button>
            </div>
            <button class="btn-submit" id="buyButton">立即购买</button>
            <p class="price-tips">可获得 <span id="tips-value"></span> ODIN，累计投入 10000 ECN 激活节点全部权限</p>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/Polyfill.js"></script>
    <script src="${pageContext.request.contextPath}/js/activity.js"></script>
</body>
</html>