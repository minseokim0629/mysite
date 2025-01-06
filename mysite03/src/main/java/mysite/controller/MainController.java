package mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MainController {

	@RequestMapping({"/", "/main"})
	public String index(HttpServletRequest request) {
		return "main/index";
	}
}
