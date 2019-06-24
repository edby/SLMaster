<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<title>撤销机器人修改</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>

	<script type="text/javascript">
        $(function(){
            var id = '${taskId }';
            if(id != null){
                $('#dataForm').form('load', '${ctx}/robot/loadForm.do?id=${taskId }');
            }
        });
	</script>

</head>
<body>
<form id="dataForm" method="post" action="${ctx}/robot/updateTask.do">
	<table class="grid">
		<tr>
			<td>开始时间</td>
			<td>
				<input type="hidden"  name="id" value="${id }" />
                <input type="text"  name="startTime"  value="" class="easyui-datetimebox" data-options="required:true" style="width: 180px;"  />
			</td>
			<td>结束时间</td>
			<td>
                <input type="text"  name="endTime" value="" class="easyui-datetimebox" data-options="required:true" style="width: 180px;"   />
			</td>

		</tr>
		<tr>
			<td>时间间隔（秒）</td>
			<td>
                <input type="text" name="timeinterval" value="" class="enumberbox" data-options="required:true" style="width: 180px;" />
			</td>
			<td>执行用户(添加流水时使用)</td>
			<td>
				<select class="easyui-combobox" id="phone" name="phone">
					<c:forEach var="user" items="${users }">
						<option value="${user.phone }">${user.phone }</option>
					</c:forEach>
				</select>

			</td>

		</tr>
		<tr>
			<td>任务名称</td>
			<td>
                <input type="text" name="jobname" value="" class="enumberbox" readonly="readonly" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>任务组名</td>
			<td>
                <input type="text" name="jobgroupname" value="" class="enumberbox" readonly="readonly" data-options="required:true" style="width: 180px;"/>
			</td>

		</tr>
		<tr>
			<td>触发器名称</td>
			<td>
                <input type="text" name="triggername" value="" class="enumberbox" readonly="readonly" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>触发器组名</td>
			<td>
                <input type="text" name="triggergroupname" value="" class="enumberbox" readonly="readonly" data-options="required:true" style="width: 180px;"/>
			</td>

		</tr>
		<tr>
			<td>删除多少秒之前的数据</td>
			<td>
                <input type="text" name="countmax" value="" class="enumberbox" data-options="required:true"  style="width: 180px;"/>
			</td>
			<td>开关 0:关闭，1：开启</td>
			<td>
				<input type="text" name="onoff" value="" class="enumberbox" data-options="required:true" readonly="readonly" style="width: 180px;"/>
			</td>
		</tr>

		<tr>
			<td colspan="4" align="center">
				<input type="submit" value="保存">
				<a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px" onclick="top.closeTopDialog();">关闭</a>
			</td>
		</tr>
	</table>
</form>
</body>
</html>