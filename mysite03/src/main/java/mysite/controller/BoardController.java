package mysite.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import mysite.security.Auth;
import mysite.security.AuthUser;
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
		
		return "board/list";
	}
	
	@RequestMapping("/view/{id}")
	public String view(@PathVariable("id") Long id, Model model) {
		BoardVo boardVo = boardService.getContents(id);
		model.addAttribute("vo", boardVo);
		
		return "board/view";
	}
	
	@RequestMapping(value = "/write/{type}", method = RequestMethod.GET)
	public String write(@AuthUser UserVo authUser,
						@PathVariable("type") String type,
						@RequestParam(value = "id", required = false) Long id, Model model) {
		if("reply".equals(type)) {
			BoardVo boardVo = boardService.getContents(id, authUser.getId());
		
			model.addAttribute("vo", boardVo);
		}
		
		return "board/write";
	}
	
	@Auth
	@RequestMapping(value = "/write/{type}", method = RequestMethod.POST)
	public String write(@AuthUser UserVo authUser, 
						@PathVariable("type") String type, BoardVo boardVo) {
		
		boardVo.setUserId(authUser.getId());
		boardVo.setType(type);
		
		boardService.addContents(boardVo);
		
		return "redirect:/board";
	}
	
	
	@RequestMapping(value = "/modify/{id}", method = RequestMethod.GET)
	public String modify(@AuthUser UserVo authUser,
						 @PathVariable("id") Long id, Model model) {
		BoardVo boardVo = boardService.getContents(id, authUser.getId());
		
		model.addAttribute("vo", boardVo);
		
		return "board/modify";
	}
	
	@RequestMapping(value = "/modify/{id}", method = RequestMethod.POST)
	public String modify(@AuthUser UserVo authUser, BoardVo boardVo) {
		boardService.updateContents(boardVo);
		
		return "redirect:/board";
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(@AuthUser UserVo authUser, 
						 @PathVariable("id") Long id) {
		boardService.deleteContents(id, authUser.getId());
		
		return "redirect:/board";
	}
}
