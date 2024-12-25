package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.dao.UserDao;
import mysite.vo.BoardVo;
import mysite.vo.UserVo;

public class ModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		// Access Control
		if(session == null) {
			// 비정상적인 접근
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			// 비정상적인 접근
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		Long id = Long.parseLong(request.getParameter("id"));
		BoardVo vo = new BoardDao().findById(id);
		request.setAttribute("vo", vo);
		
		if(authUser.getId()!=vo.getUserId()) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		
		vo.setId(id);
		vo.setTitle(title);
		vo.setContents(contents);

		new BoardDao().update(vo);
		
		response.sendRedirect(request.getContextPath() + "/board?result=success");		
	}

}
