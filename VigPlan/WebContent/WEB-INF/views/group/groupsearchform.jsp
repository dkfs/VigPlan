<%@ page import="com.vigplan.vo.GroupVo" %>
<%@ page import="com.vigplan.dao.group.GroupDao" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../includes/header.jsp" %>
<%
GroupVo group = (GroupVo) request.getAttribute("group");
%>
<script>
	function search(frm){
		
		var gNo = frm.gNo.value;
		var searchid = frm.searchid.value;
		
		$.ajax({
			url: "<%= request.getContextPath() %>/group/search",
			method : "POST",

			data: {
				'a': 'embedded',
				'gNo': gNo,
				'searchid': searchid
			},
			success: function(data) {
				console.log(data);
			$('#searchList').html(data);
			},
			error: function(request,response,error) {
				alert("Error:" + error)
			}
		});
	}
</script>

		<br> <br>
		<h3><strong>멤버 그룹 초대하기</strong></h3>
		<br> <br>
<form name="invite" method="post" action="<%= request.getContextPath() %>/group/search">
	<input type="hidden" name="gNo" value="<%=group.getgNo() %>">
	<input type="text" name="searchid" placeholder="초대하고 싶은 아이디 입력">
	<button type="button" onclick="return search(this.form)">검색</button>
</form>

<div id="searchList">

</div>
<%@include file="../includes/footer.jsp" %>