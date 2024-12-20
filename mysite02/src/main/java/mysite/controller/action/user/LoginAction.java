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

public class LoginAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		UserVo vo = new UserDao().findByEmailAndPassword(email, password);
		
		System.out.println(vo);
		// 로그인 실패
		if(vo == null) {
			//redirect
			//url이 너무 길어져서 forward 방식 사용 (대부분 forward 방식 사용)
			// response.sendRedirect(request.getContextPath()+"/user?a=loginform&result=fail&email="+email);
			//forward
			request.setAttribute("result", "fail");
			request.setAttribute("email", email);
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/loginform.jsp");
			rd.forward(request, response);
			
			return; //밑의 코드가 실행될 수 있도록 리턴 응답을 해도 코드가 끝나는건 아니기 때문. 서버 쪽에서는 코드가 끝난게 아니기 때문에 또 응답할 수도 있다.
		} 
		
		// 로그인 처리
		// true 옵션을 주면 없는 경우 만들어서 준다
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", vo); 
		
		response.sendRedirect(request.getContextPath());
	}

}
