package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;
import mysite.vo.UserVo;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		String title = request.getParameter("title");
		String contents = request.getParameter("content");
		Long gNo = new BoardDao().findMaxgNo() + 1;
		Long userId = authUser.getId();
		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setgNo(gNo);
		vo.setoNo(1L);
		vo.setDepth(0L);
		vo.setUserId(userId);
		
		new BoardDao().insert(vo);
		
		response.sendRedirect(request.getContextPath() + "/board?result=success");
	}

}
