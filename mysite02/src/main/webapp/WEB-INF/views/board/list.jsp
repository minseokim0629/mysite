<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:set var="count" value="${fn:length(list) }" />
					<c:forEach items = "${list }" var = "vo" varStatus = "status">				
						<tr>
							<td>${count - status.index }</td>
							<td style="text-align:left; padding-left:${vo.depth * 20}px">
								<img src="${pageContext.request.contextPath }/assets/images/reply.png">
								<a href="${pageContext.request.contextPath }/board?a=view&id=${vo.id}">${vo.title }</a>
							</td>
							<td>${vo.userName }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<td>
								<c:if test='${authUser.id == vo.userId }'>
									<a href="${pageContext.request.contextPath }/board?a=delete&id=${vo.id}" class="del">삭제</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
					<!-- <tr>
						<td>2</td>
						<td style="text-align:left; padding-left:${1 * 20}px">
							<img src="${pageContext.request.contextPath }/assets/images/reply.png">
							<a href="">두 번째 글입니다.</a>
						</td>
						<td>안대혁</td>
						<td>3</td>
						<td>2015-10-02 12:04:12</td>
						<td><a href="" class="del">삭제</a></td>
					</tr>
					<tr>
						<td>1</td>
						<td style="text-align:left; padding-left:${2 * 20}px">
							<img src="${pageContext.request.contextPath }/assets/images/reply.png">
							<a href="">첫 번째 글입니다.</a>
						</td>
						<td>안대혁</td>
						<td>3</td>
						<td>2015-09-25 07:24:32</td>
						<td><a href="" class="del">삭제</a></td>
					</tr> -->
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:choose>
							<c:when test="${beginPage > 1}">
								<li><a href="${pageContext.request.contextPath }/board?pageIdx=${beginPage - 1}">◀</a></li>
							</c:when>
							<c:otherwise>
								<li>◀</li>
							</c:otherwise>
						</c:choose>
						<c:forEach var="i" begin = "${beginPage }" end = "${endPage }">
							<c:choose>
								<c:when test="${i == curPage }">
									<li class="selected">${i}</li>
								</c:when>
								<c:when test="${i >= beginPage && i <= totalPages }" >
									<li><a href="${pageContext.request.contextPath }/board?pageIdx=${i }">${i }</a></li>
								</c:when>
								<c:otherwise>
									<li>${i }</li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:choose>
							<c:when test="${endPage < totalPages}">
								<li><a href="${pageContext.request.contextPath }/board?pageIdx=${endPage + 1}">▶</a></li>
							</c:when>
							<c:otherwise>
								<li>▶</li>
							</c:otherwise>
						</c:choose>
						<!--  
						<li><a href="">◀</a></li>
						<li><a href="">1</a></li>
						<li class="selected">2</li>
						<li><a href="">3</a></li>
						<li>4</li>
						<li>5</li>
						<li><a href="">▶</a></li> -->
					</ul> 
				</div>					
				<!-- pager 추가 -->
				
				<div class="bottom">
					<c:if test='${not empty authUser }'>
						<a href="${pageContext.request.contextPath }/board?a=writeform" id="new-book">글쓰기</a>
					</c:if>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>