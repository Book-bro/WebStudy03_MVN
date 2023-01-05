<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp" />
<c:if test="${not empty message }">
	<script>
		alert("${message}");
	</script>
</c:if>
</head>
<body>
	<h4>가입 양식</h4>
	<form method="post" enctype="multipart/form-data">
		<table>
			<tr>
				<th>회원아이디</th>
				<td><input class="form-control" type="text" 
					name="memId" VALUE="${member.memId}" /><span class="text-danger">${errors.memId}</span></td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td><input class="form-control" type="text" 
					name="memPass" VALUE="${member.memPass}" /><span
					class="text-danger">${errors.memPass}</span></td>
			</tr>
			<tr>
				<th>회원명</th>
				<td><input class="form-control" type="text" 
					name="memName" VALUE="${member.memName}" /><span
					class="text-danger">${errors.memName}</span></td>
			</tr>
			<tr>
				<th>회원프로필</th>
				<td>
					<input type="file" name="memImage" accept="image/*"/>
					<span class="text-danger">${errors.memImage }</span>
				</td>
			</tr>
			<tr>
				<th>주민번호1</th>
				<td><input class="form-control" type="text" name="memRegno1"
					VALUE="${member.memRegno1}" /><span class="text-danger">${errors.memRegno1}</span></td>
			</tr>
			<tr>
				<th>주민번호2</th>
				<td><input class="form-control" type="text" name="memRegno2"
					VALUE="${member.memRegno2}" /><span class="text-danger">${errors.memRegno2}</span></td>
			</tr>
			<tr>
				<th>생일</th>
				<td><input class="form-control" type="date" 
					name="memBir" VALUE="${member.memBir}" /><span class="text-danger">${errors.memBir}</span></td>
			</tr>
			<tr>
				<th>우편번호</th>
				<td><input class="form-control" type="text" 
					name="memZip" VALUE="${member.memZip}" /><span class="text-danger">${errors.memZip}</span></td>
			</tr>
			<tr>
				<th>주소1</th>
				<td><input class="form-control" type="text" 
					name="memAdd1" VALUE="${member.memAdd1}" /><span
					class="text-danger">${errors.memAdd1}</span></td>
			</tr>
			<tr>
				<th>주소2</th>
				<td><input class="form-control" type="text" 
					name="memAdd2" VALUE="${member.memAdd2}" /><span
					class="text-danger">${errors.memAdd2}</span></td>
			</tr>
			<tr>
				<th>집전번</th>
				<td><input class="form-control" type="text" name="memHometel"
					VALUE="${member.memHometel}" /><span class="text-danger">${errors.memHometel}</span></td>
			</tr>
			<tr>
				<th>회사전번</th>
				<td><input class="form-control" type="text" name="memComtel"
					VALUE="${member.memComtel}" /><span class="text-danger">${errors.memComtel}</span></td>
			</tr>
			<tr>
				<th>휴대폰</th>
				<td><input class="form-control" type="text" name="memHp"
					VALUE="${member.memHp}" /><span class="text-danger">${errors.memHp}</span></td>
			</tr>
			<tr>
				<th>이메일</th>
				<td><input class="form-control" type="text" name="memMail"
					VALUE="${member.memMail}" /><span class="text-danger">${errors.memMail}</span></td>
			</tr>
			<tr>
				<th>직업</th>
				<td><input class="form-control" type="text" name="memJob"
					VALUE="${member.memJob}" /><span class="text-danger">${errors.memJob}</span></td>
			</tr>
			<tr>
				<th>취미</th>
				<td><input class="form-control" type="text" name="memLike"
					VALUE="${member.memLike}" /><span class="text-danger">${errors.memLike}</span></td>
			</tr>
			<tr>
				<th>기념일</th>
				<td><input class="form-control" type="text" name="memMemorial"
					VALUE="${member.memMemorial}" /><span class="text-danger">${errors.memMemorial}</span></td>
			</tr>
			<tr>
				<th>기념일자</th>
				<td><input class="form-control" type="date"
					name="memMemorialday" VALUE="${member.memMemorialday}" /><span
					class="text-danger">${errors.memMemorialday}</span></td>
			</tr>
			<tr>
				<th>마일리지</th>
				<td><input class="form-control" type="number" name="memMileage"
					VALUE="${member.memMileage}" /><span class="text-danger">${errors.memMileage}</span></td>
			</tr>
			<tr>
				<th>탈퇴여부</th>
				<td><input class="form-control" type="text" name="memDelete"
					VALUE="${member.memDelete}" /><span class="text-danger">${errors.memDelete}</span></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="저장"></td>
			</tr>
		</table>
	</form>
	<jsp:include page="/includee/postScript.jsp" />
</body>
</html>