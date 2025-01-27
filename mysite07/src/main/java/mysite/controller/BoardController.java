package mysite.controller;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import mysite.service.BoardService;
import mysite.vo.BoardVo;
import mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	private BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@RequestMapping({"", "/"})
	public String index(@RequestParam(value = "currentPage", required = false, defaultValue = "1") Integer currentPage, 
						@RequestParam(value = "kwd", required = false, defaultValue = "") String keyword, 
						Model model) {
		Map<String, Object> resultMap = boardService.getContentsList(currentPage, keyword);
		
		model.addAttribute("result", resultMap);
		
		return "th/board/list";
	}
	
	@RequestMapping("/view/{id}")
	public String view(@PathVariable("id") Long id, Model model) {
		BoardVo boardVo = boardService.getContents(id);
		model.addAttribute("vo", boardVo);
		
		return "th/board/view";
	}
	
	@RequestMapping(value = "/write/{type}", method = RequestMethod.GET)
	public String write(Authentication authentication,
						@PathVariable("type") String type,
						@RequestParam(value = "id", required = false) Long id, Model model) {
		if("reply".equals(type)) {
			UserVo authUser = (UserVo)authentication.getPrincipal();
			BoardVo boardVo = boardService.getContents(id, authUser.getId());
		
			model.addAttribute("vo", boardVo);
		}
		
		return "th/board/write";
	}
	
	@RequestMapping(value = "/write/{type}", method = RequestMethod.POST)
	public String write(Authentication authentication, 
						@PathVariable("type") String type, BoardVo boardVo) {
		UserVo authUser = (UserVo)authentication.getPrincipal();
		
		boardVo.setUserId(authUser.getId());
		boardVo.setType(type);
		
		boardService.addContents(boardVo);
		
		return "redirect:/board";
	}
	
	@RequestMapping(value = "/modify/{id}", method = RequestMethod.GET)
	public String modify(Authentication authentication,
						 @PathVariable("id") Long id, Model model) {
		UserVo authUser = (UserVo)authentication.getPrincipal();
		
		BoardVo boardVo = boardService.getContents(id, authUser.getId());
		System.out.println(boardVo);
		model.addAttribute("vo", boardVo);
		
		return "th/board/modify";
	}
	
	@RequestMapping(value = "/modify/{id}", method = RequestMethod.POST)
	public String modify(Authentication authentication, BoardVo boardVo) {
		boardService.updateContents(boardVo);
		
		return "redirect:/board";
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(Authentication authentication, 
						 @PathVariable("id") Long id) {
		UserVo authUser = (UserVo)authentication.getPrincipal();
		
		boardService.deleteContents(id, authUser.getId());
		
		return "redirect:/board";
	}
}
