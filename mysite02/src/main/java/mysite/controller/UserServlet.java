package mysite.controller;

import java.util.Map;

import jakarta.servlet.annotation.WebServlet;
import mysite.controller.action.main.MainAction;
import mysite.controller.action.user.JoinAction;
import mysite.controller.action.user.JoinFormAction;
import mysite.controller.action.user.JoinSuccessAction;
import mysite.controller.action.user.LoginAction;
import mysite.controller.action.user.LoginFormAction;

@WebServlet("/user")
public class UserServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Action> mapAction = Map.of(
			"joinform", new JoinFormAction(),
			"join", new JoinAction(),
			"joinsuccess", new JoinSuccessAction(),
			"loginform", new LoginFormAction(),
			"login", new LoginAction()
	);
	@Override
	protected Action getAction(String actionName) {
		// Action action = null;
		
//		if("joinform".equals(actionName)) {
//			action = new JoinFormAction();
//		} else if ("join".equals(actionName)) {
//			action = new JoinAction();
//		} else if ("joinsuccess".equals(action)) {
//			action = new JoinSuccessAction();
//		} else {
//			// 비정상적으로 들어올 때는 메인으로 돌려버리기
//			action = new MainAction();
//		}
		return mapAction.getOrDefault(actionName, new MainAction());
	}

//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.setCharacterEncoding("utf-8");
//		
//		String action = request.getParameter("a");
//		
//		// /user?a=joinform(GET)
//		if("joinform".equals(action)) {
//			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/joinform.jsp");
//			rd.forward(request, response);
//		} else if ("join".equals(action)) { // /user?a=join(POST)
//			String name = request.getParameter("name");
//			String email = request.getParameter("email");
//			String password = request.getParameter("password");
//			String gender = request.getParameter("gender");
//			
//			UserVo vo = new UserVo();
//			
//			vo.setName(name);
//			vo.setEmail(email);
//			vo.setPassword(password);
//			vo.setGender(gender);
//			
//			new UserDao().insert(vo);
//			
//			response.sendRedirect("/mysite02/user?a=joinsuccess");
//		} else if ("joinsuccess".equals(action)) { // /user?a=joinsuccess(GET)
//			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/joinsuccess.jsp");
//			rd.forward(request, response);
//		} else {
//
//		}
//	}

	//얘는 부모가 구현해놨으니까 안해도 됨
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		doGet(request, response);
//	}

}
