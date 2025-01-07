package mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import mysite.vo.SiteVo;

@Controller
public class MainController {
	@Autowired
	ApplicationContext applicationContext;
	
	@RequestMapping({"/", "/main"})
	public String index(HttpServletRequest request) {
		SiteVo vo = applicationContext.getBean(SiteVo.class);
		System.out.println(vo);
		return "main/index";
	}
}
