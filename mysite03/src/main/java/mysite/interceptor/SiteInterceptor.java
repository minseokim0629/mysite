package mysite.interceptor;

import java.util.Enumeration;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.service.SiteService;
import mysite.vo.SiteVo;

public class SiteInterceptor implements HandlerInterceptor {
	private LocaleResolver localeResolver;
	private final SiteService siteService;
	
	public SiteInterceptor(LocaleResolver localeResolver, SiteService siteService) {
		this.localeResolver = localeResolver;
		this.siteService = siteService;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		SiteVo siteVo = (SiteVo)request.getServletContext().getAttribute("siteVo");
		
		if(siteVo == null) {
			siteVo = siteService.getSite();
			request.getServletContext().setAttribute("siteVo", siteVo);
		}

		// locale
		String lang = localeResolver.resolveLocale(request).getLanguage();	
		request.setAttribute("lang", lang);
		
		return true;
	}

}
