package mysite.interceptor;

import java.util.Enumeration;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.service.SiteService;

public class SiteInterceptor implements HandlerInterceptor {
	private LocaleResolver localeResolver;
	private SiteService siteService;
	
	public SiteInterceptor(LocaleResolver localeResolver, SiteService siteService) {
		this.localeResolver = localeResolver;
		this.siteService = siteService;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		ServletContext sc = request.getServletContext();
		Enumeration<String> e = sc.getAttributeNames();
		boolean exist = false;
		
		while(e.hasMoreElements()) {
			String name = e.nextElement();
			if("title".equals(name)) {
				exist = true;
				request.setAttribute("title", sc.getAttribute("title"));
				break;
			}
		}
		
		if(!exist) {
			sc.setAttribute("siteVo", siteService.getSite());
		}

		// locale
		String lang = localeResolver.resolveLocale(request).getLanguage();	
		request.setAttribute("lang", lang);
		
		return true;
	}

}
