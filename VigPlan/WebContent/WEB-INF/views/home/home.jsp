<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
 <script type="text/javascript">
 $(document).ready(function() {
	 $("#regist_btn").click(function() {
			location.assign('/VigPlan/member'); 
	 });
	 $("#login_btn").click(function() {
			location.assign('/VigPlan/member/login'); 
	 });
	 $("#board_btn").click(function() {
			location.assign('/VigPlan/board'); 
	 });
	 $("#group_btn").click(function() {
			location.assign('/VigPlan/group'); 
	 });
	 $("#moim_btn").click(function() {
			location.assign('/VigPlan/moim'); 
	 });
 });
 
 </script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

</head>
<body>

<div class="container">
	<h1>메인페이지</h1>
	<hr>
	<button id="regist_btn" class="btn">회원가입 하기</button>
	<button id="login_btn" class="btn">로그인 하기</button>
	<button id="board_btn" class="btn">게시판</button>
	<button id="group_btn" class="btn">그룹</button>
	<button id="moim_btn" class="btn">모임</button>
</div>

</body>
</html>