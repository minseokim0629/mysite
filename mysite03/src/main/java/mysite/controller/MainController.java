package mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MainController {
	
	@Autowired 
	private LocaleResolver localeResolver;
	
	@RequestMapping({"/", "/main"})
	public String index(Model model, HttpServletRequest request) {
		String lang = localeResolver.resolveLocale(request).getLanguage();
		System.out.println("Language Code: " + lang);
		
		model.addAttribute("lang", lang);
		return "main/index";
	}
}
