<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="addrbook_error.jsp"
	import="java.util.*, lx.edu.springmvc.vo.*"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE HTML>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}${webappRoot}/resources/css/addrbook.css" type="text/css" media="screen" />

<script type="text/javascript">
	function delcheck() {
		result = confirm("정말로 삭제하시겠습니까 ?");
	
		if(result == true){
			document.form1.action.value="delete";
			document.form1.submit();
		}
		else
			return;
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>주소록:수정화면</title>
</head>

<jsp:useBean id="ab" scope="request" class="lx.edu.springmvc.vo.AddrBookVO" />

<body>
<div align="center">
<H2>주소록:수정화면 </H2>
<HR>
[<a href="addrbook_list.do">주소록 목록으로</a>] <p>
<form name=form1 method=post action="edit.do">
<input type=hidden name="abId" value="<%=ab.getAbId()%>">
<input type=hidden name="action" value="update">

<table border="1">
  <tr>
  <th>이름</th>
    <td><input type="text" name="abName" value="<%=ab.getAbName() %>"></td>
  </tr>
  <tr>
    <th>email</th>
    <td><input type="text" name="abEmail" value="<%=ab.getAbEmail() %>"></td>
  </tr>
    <tr>
    <th>전화번호</th>
    <td><input type="text" name="abTel" value="<%=ab.getAbTel() %>"></td>
  </tr>
      <tr>
    <th>생 일</th>
    <td><input type="date" name="abBirth" value="<%=ab.getAbBirth() %>"></td>
  </tr>
  <tr>
    <th>회 사</th>
    <td><input type="text" name="abComdept" value="<%=ab.getAbComdept()%>"></td>
  </tr>
  <tr>
    <th>메 모</th>
    <td><input type="text" name="abMemo" value="<%=ab.getAbMemo() %>"></td>
  </tr>
  <tr>
    <td colspan=2 align=center><input type=submit value="저장"><input type=reset value="취소"><input type="button" value="삭제" onClick="delcheck()"></td>
</tr>
</table>
</form>

</div>
</body>
</html>