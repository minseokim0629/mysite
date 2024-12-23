package mysite.controller.action.user;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.controller.ActionServlet.Action;
import mysite.dao.UserDao;
import mysite.vo.UserVo;

public class UpdateFormAction implements Action {

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
		//////////////////////////////////////////////////////////////

		UserVo vo = new UserDao().findById(authUser.getId());
		request.setAttribute("vo", vo);
		
		System.out.println(vo);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/updateform.jsp");
		rd.forward(request, response);
	}

}