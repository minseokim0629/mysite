package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;
import mysite.vo.UserVo;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// HttpSession session = request.getSession();
		
//		Long id = Long.parseLong(request.getParameter("id"));
//		UserVo authUser = (UserVo)session.getAttribute("authUser");
//		Long userId = 0L;
//		if(authUser != null) {
//			userId = authUser.getId();
//		}
//		// 쿠키 읽기
//		Cookie[] cookies = request.getCookies();
//		boolean isViewed = false;
//		String existingValue = "";
//		String newEntry = id + ","+ userId;
//		for(Cookie cookie : cookies) {
//			if(cookie.getName().equals("viewed")) {
//				existingValue = cookie.getValue();
//				if(existingValue.contains(id + "," + userId)) {
//					isViewed = true;
//					break;
//				}
//			}
//		}
//		
//		if (!existingValue.contains(newEntry)) {
//	        if (!existingValue.isEmpty()) {
//	            existingValue += "|"; // 기존 값과 구분자를 추가
//	        }
//	        existingValue += newEntry;
//		}
//		
//		BoardVo vo = new BoardDao().findById(id);
//		request.setAttribute("vo", vo);
//		
//		if(!isViewed) {
//			new BoardDao().updateHit(vo);
//			// 쿠키 생성
//			Cookie cookie = new Cookie("viewed", existingValue);
//			cookie.setPath(request.getContextPath());
//			cookie.setMaxAge(60 * 60 * 24); // max : 1 day
//			response.addCookie(cookie);
//		}
		Long id = Long.parseLong(request.getParameter("id"));
		
		BoardVo vo = new BoardDao().findById(id);
		request.setAttribute("vo", vo);
		
		new BoardDao().updateHit(vo);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/view.jsp");
		rd.forward(request, response);
	}

}
