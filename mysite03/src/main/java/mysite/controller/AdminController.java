package mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import mysite.security.Auth;
import mysite.service.FileUploadService;
import mysite.service.SiteService;
import mysite.vo.SiteVo;

@Auth(role="ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	private FileUploadService fileUploadService;
	private SiteService siteService;
	
	public AdminController(FileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}
	
	@RequestMapping({"", "/main"})
	public String main() {
		return "admin/main";
	}
	
	@RequestMapping("/update")
	public String update(SiteVo siteVo, @RequestParam("file") MultipartFile file) {
		String profile = fileUploadService.restore(file);
		if(!profile.isEmpty()) {
			siteService.updateSite(siteVo);
		}
		return "redirect:/main";
	}
	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}
	
	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}

	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
}
