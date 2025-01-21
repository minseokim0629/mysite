<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>${siteVo.title }</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath }/board" method="post">
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
					<c:set var="count" value="${fn:length(result.list) }" />
					<c:forEach items = "${result.list }" var = "vo" varStatus = "status">				
						<tr>
							<td>${count - status.index }</td>
							<td style="text-align:left; padding-left:${vo.depth * 20}px">
								<c:if test="${vo.depth > 0}">
									<img src="${pageContext.request.contextPath }/assets/images/reply.png">
								</c:if>
								<a href="${pageContext.request.contextPath }/board/view/${vo.id}">${vo.title }</a>
							</td>
							<td>${vo.userName }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<sec:authorize access="isAuthenticated()">
		    					<sec:authentication property="principal" var="authUser"/>
								<c:if test="${authUser.id == vo.userId }">
									<td><a href="${pageContext.request.contextPath }/board/delete/${vo.id}" class="del">삭제</a></td>
								</c:if>
							</sec:authorize>
						</tr>
					</c:forEach>
				</table>

				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:choose>
							<c:when test="${result.beginPage > 1}">
								<li><a href="${pageContext.request.contextPath }/board?currentPage=${result.beginPage - 1}&kwd=${result.keyword}">◀</a></li>
							</c:when>
							<c:otherwise>
								<li>◀</li>
							</c:otherwise>
						</c:choose>
						<c:forEach var="i" begin = "${result.beginPage }" end = "${result.endPage }">
							<c:choose>
								<c:when test="${i == result.currentPage }">
									<li class="selected">${i}</li>
								</c:when>
								<c:when test="${i >= result.beginPage && i <= result.totalPageCnt }" >
									<li><a href="${pageContext.request.contextPath }/board?currentPage=${i }&kwd=${result.keyword}">${i }</a></li>
								</c:when>
								<c:otherwise>
									<li>${i }</li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:choose>
							<c:when test="${result.endPage < result.totalPageCnt}">
								<li><a href="${pageContext.request.contextPath }/board?currentPage=${result.endPage + 1}&kwd=${result.keyword}">▶</a></li>
							</c:when>
							<c:otherwise>
								<li>▶</li>
							</c:otherwise>
						</c:choose>
					</ul> 
				</div>					
				<!-- pager 추가 -->
				
				<div class="bottom">
					<sec:authorize access="isAuthenticated()">
						<a href="${pageContext.request.contextPath }/board/write/origin" id="new-book">글쓰기</a>
					</sec:authorize>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>