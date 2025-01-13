package mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Log log = LogFactory.getLog(GlobalExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	public void handler(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception{
		// 1. 로깅(logging)
		// StringWriter - 주스트림 PrintWriter - 보조스트림
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		log.error(errors.toString());
		
		// 2. 요청 구분 
		//	  json 요청 : request header의 accept : application/json 이 포함되어있음
		//	  html 요청 : request header의 accept : application/json 이 포함되어 있지 않음
		String accept = request.getHeader("accept");
		
		if(accept.matches(".*application.json.*")) {
			// 3. JSON 응답
		} else {
			// 4. 사과 페이지(종료)
			request.setAttribute("errors", errors.toString());
			request.getRequestDispatcher("/WEB-INF/views/errors/exception.jsp").forward(request, response);
		}
	}
}
