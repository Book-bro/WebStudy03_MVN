<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<pre>
	DB에 저장한 데이터
	textPart : ${textPart }
	numPart : ${numPart }
	datePart : ${datePart }
	file metadata : ${fileMetadata }
	<c:remove var="textPart"/>
	<c:remove var="numPart"/>
	<c:remove var="datePart"/>
</pre>
<c:forEach items="${fileMetadata }" var="md">
	<img src="<c:url value='${md }'/>"/> <br/>
</c:forEach>
<c:remove var="fileMetadata"/>
<!-- 파라미터가 아닌 파트, 폼안에있는 데이터 하나를 기준으로 한다 -->
<form method="post" enctype="multipart/form-data" 
		action="${pageContext.request.contextPath }/file/upload.do"> 
<ul>
	<li>
		문자 데이터 : <input type="text" name="textPart"/>
	</li>
	<li>
		숫자(?) 데이터 : <input type="number" name="numPart"/>
	</li>
	<li>
		날짜(?) 데이터 : <input type="date" name="datePart"/>
	</li>
	<li>
		파일 데이터 : <input type="file" name="filePart1" accept="image/*"/>
	</li>
	<li>
		파일 데이터 : <input type="file" name="filePart2"/>
	</li>
	<li>
		<input type="submit" value="업로드" />
	</li>
</ul>
</form>
</body>
</html>