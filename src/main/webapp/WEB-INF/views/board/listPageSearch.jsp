<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>insert title here</title>
</head>
<body>

<div id="nav">
 <%@ include file="../include/nav.jsp" %>
</div>

	<table>
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성일</th>
				<th>작성자</th>
				<th>조회수</th>
			</tr>
		</thead>

		<tbody>

			<c:forEach items="${list}" var="list">
				<tr>
					<td>${list.bno}</td>
					<td><a href="/board/view?bno=${list.bno}">${list.title}</a></td>
					<td>${list.regDate}</td>
					<td>${list.writer}</td>
					<td>${list.viewCnt}</td>
				</tr>
			</c:forEach>

		</tbody>

	</table>

	<div>
		<c:if test="${page.prev}">
			<span>[ <a href="/board/listPageSearch?num=${page.startPageNum - 1}${page.searchTypeKeyword}">이전</a>]</span>
		</c:if>

		<c:forEach begin="${page.startPageNum}" end="${page.endPageNum}" var="num">
			<span> 
				<c:if test="${select != num}">
					<a href="/board/listPageSearch?num=${num}${page.searchTypeKeyword}">${num}</a>
				</c:if> 
				<c:if test="${select == num}">
					<b>${num}</b>
				</c:if>

			</span>
		</c:forEach>

		<c:if test="${page.next}">
			<span>[ <a href="/board/listPageSearch?num=${page.endPageNum + 1}${page.searchTypeKeyword}">다음</a>	]</span>
		</c:if>

		<div>
			<select name="searchType">
				<option value="title"
					<c:if test="${page.searchType eq 'title'}">selected</c:if>>제목</option>
				<option value="content"
					<c:if test="${page.searchType eq 'content'}">selected</c:if>>내용</option>
				<option value="title_content"
					<c:if test="${page.searchType eq 'title_content'}">selected</c:if>>제목+내용</option>
				<option value="writer"
					<c:if test="${page.searchType eq 'writer'}">selected</c:if>>작성자</option>
			</select> 
			
			<input type="text" name="keyword" value="${page.keyword}" />

			<%-- <c:if test="${page.searchType eq 'title'}">selected</c:if> test 내부에는 조건이 들어가고, 
			이 조건이 참인 경우 <c:if> ~ </c:if> 사이에 있는 문자인 selected를 출력, 거짓인 경우 아무것도 출력하지 않습니다.

			지금은 조건이 ${page.searchType eq 'title'} 인데, 이는 page.searchType와 문자열 title가 같은지(equals) 확인하는것 입니다. 
			jstl에서는 .equals(); 대신 eq 로 짧게 사용합니다.
			즉, 검색 조건을 제목으로 한 경우, 컨트롤러로 전송되는 searchType의 값은 title이며, 이 값이 다시 뷰(jsp)로 전송되면, 
			searchType의 값과 같은 값을 가진 option태그에 selected가 생겨서 선택된 상태가 됩니다.
			
			검색어인 ${keyword}는 그냥 입력한 그대로 출력하니 따로 설명할 필요가 없겠습니다. --%>

			<button type="button" id="searchBtn">검색</button>
		</div>

	</div>

	<script>
		document.getElementById("searchBtn").onclick = function() {

			let searchType = document.getElementsByName("searchType")[0].value;
			let keyword = document.getElementsByName("keyword")[0].value;			

			console.log(searchType)
			console.log(keyword)

			location.href = "/board/listPageSearch?num=1" + "&searchType=" + searchType + "&keyword=" + keyword;
		};

		/* name이 searchType인 html 엘리먼트중 첫번째([0])의 값을 변수(let) serarchType에 저장하고, 
		name이 keyword html 엘리먼트중 첫번째([0])의 값을 변수(let) keyword에 저장합니다.

		여기서 왜 첫번째([0])가 나오냐면, html 엘리먼트에 사용되는 name 속성은 2개 이상 복수로 사용할 수 있기 때문에 
		document.getElementsByName() 로 데이터를 가져오려고하면 배열로 가져오기 때문에 가장 첫번째인 0번째 데이터를 가져오는겁니다. */
		
	</script>

</body>
</html>