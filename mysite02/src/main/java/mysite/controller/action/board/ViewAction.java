package mysite.controller.action.board;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Long id = Long.parseLong(request.getParameter("id"));

		// 쿠키 읽기
		Cookie[] cookies = request.getCookies();
		boolean isViewed = false;
		String existingValue = "";
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals("viewed")) {
				existingValue = cookie.getValue();
				if(List.of(existingValue.split(":")).contains(id.toString())){
					System.out.println(existingValue);
					isViewed = true;
					break;
				}
			}
		}
		
		BoardVo vo = new BoardDao().findById(id);
		request.setAttribute("vo", vo);
		
		if(!isViewed) {
			new BoardDao().updateHit(vo);
			// 쿠키 생성
			Cookie cookie = new Cookie("viewed", existingValue + id + ":");
			cookie.setPath(request.getContextPath());
			cookie.setMaxAge(60 * 60 * 24); // max : 1 day
			System.out.println(cookie.getValue());
			response.addCookie(cookie);
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/view.jsp");
		rd.forward(request, response);
	}

}
