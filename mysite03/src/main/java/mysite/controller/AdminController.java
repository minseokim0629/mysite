package mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	public AdminController(FileUploadService fileUploadService, SiteService siteService) {
		this.fileUploadService = fileUploadService;
		this.siteService = siteService;
	}
	
	@RequestMapping({"", "/main"})
	public String main(Model model) {
		return "admin/main";
	}
	
	@RequestMapping("/update")
	public String update(SiteVo siteVo, @RequestParam("file") MultipartFile file) {
		String profile = fileUploadService.restore(file);
		if(profile != null) {
			// 이미지를 수정하지 않는 경우 isEmpty
			siteVo.setProfile(profile);
		}
		siteService.updateSite(siteVo);
		return "redirect:/admin";
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
