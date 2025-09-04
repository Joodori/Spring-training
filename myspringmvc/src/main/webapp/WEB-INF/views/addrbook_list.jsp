<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="addrbook_error.jsp"
	import="java.util.*, lx.edu.springmvc.vo.*"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE HTML>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}${webappRoot}/resources/css/addrbook.css" type="text/css" media="screen" />

<script type="text/javascript">
	function check(ab_id) {
		//pwd = prompt('수정/삭제 하려면 비밀번호를 넣으세요');
		//document.location.href="addrbook_control.jsp?action=edit&ab_id="+ab_id+"&upasswd="+pwd;
		document.location.href = "addrbook_edit_form.do?abId=" + ab_id;
	}
</script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>주소록:목록화면</title>

</head>
<jsp:useBean id="data" scope="request" class="java.util.ArrayList" />

<body>
	<div align="center">
		<H2>주소록:목록화면</H2>
		<HR>
		<form>
			<a href="addrbook_form.do">주소록 등록</a>
			<P>
			<table border="1">
				<tr>
					<th>번호</th>
					<th>이 름</th>
					<th>전화번호</th>
					<th>생 일</th>
					<th>회 사</th>
					<th>메 모</th>
				</tr>
				<c:forEach items="${data}" var="ab">
					<tr>
						<td><a href="javascript:check(${ab.getAbId()})">${ab.abId}</a></td>
						<td>${ab.abName}</td>
						<td>${ab.abTel}</td>
						<td>${ab.abBirth}</td>
						<td>${ab.abComdept}</td>
						<td>${ab.abMemo}</td>
					</tr>
				</c:forEach>
			</table>
		</form>

	</div>
</body>
</html>