package mysite.controller.action.board;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 한 페이지에 게시물 5개
		Long pageBoardcnt = 5L;
		Long pageGroupSize = 5L;
		Long pageIdx = Long.parseLong(Optional.ofNullable(request.getParameter("pageIdx")).orElse("1"));
		String kwd = Optional.ofNullable(request.getParameter("kwd")).orElse("");
		List<BoardVo> list = new BoardDao().findBoards(kwd, pageIdx, pageBoardcnt);
		Long totalPages = Math.ceilDiv(new BoardDao().getTotalCnt(kwd), pageGroupSize);
		Long curPageGroup = pageIdx / pageGroupSize;
		if(pageIdx % pageGroupSize == 0) {
			curPageGroup = pageIdx / pageGroupSize - 1;
		}
		Long beginPage = curPageGroup * pageGroupSize + 1;
		Long endPage = beginPage + pageGroupSize -1;
		request.setAttribute("list", list);
		request.setAttribute("curPage", pageIdx);
		request.setAttribute("beginPage", beginPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("kwd", kwd);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
		rd.forward(request, response);
	}

}
