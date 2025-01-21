package mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import mysite.repository.BoardRepository;
import mysite.vo.BoardVo;

@Service
public class BoardService {
	
	private BoardRepository boardRepository;
	
	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}
	
	public void addContents(BoardVo vo) {

		if("reply".equals(vo.getType())) {
			System.out.println(vo.getoNo()+":"+vo.getDepth());
			vo.setoNo(vo.getoNo() + 1);
			vo.setDepth(vo.getDepth() + 1);
			
			System.out.println(vo.getoNo()+":"+vo.getDepth());
			boardRepository.updateoNo(vo);
		} else {
			vo.setgNo(boardRepository.findMaxgNo() + 1);
			vo.setoNo(1L);
			vo.setDepth(0L);
		}
		
		boardRepository.insert(vo);
	}
	
	public BoardVo getContents(Long id) {
		BoardVo boardVo = boardRepository.findById(id);
		boardRepository.updateHit(boardVo);
		
		return boardVo;
	}
	
	public BoardVo getContents(Long id, Long userId) {
		BoardVo boardVo = boardRepository.findById(id);
		return boardVo;
	}
	
	public void updateContents(BoardVo vo) {
		boardRepository.update(vo);
	}
	
	public void deleteContents(Long id, Long userId) {
		boardRepository.deleteById(id);
	}
	
	public Map<String, Object> getContentsList(int currentPage, String keyword) {
		List<BoardVo> list = boardRepository.findBoards(keyword, currentPage, 5);
		// view의 pagination을 위한 데이터 값 계산
		int totalPageCnt = Math.ceilDiv(boardRepository.getTotalCnt(keyword), 5);
		int curGroupIdx = currentPage / 5;
		if(currentPage % 5 == 0) {
			curGroupIdx = currentPage / 5 - 1;
		}
		int beginPage = curGroupIdx * 5 + 1;
		int endPage = beginPage + 5 - 1;
		
		Map<String, Object> resultMap = new HashMap<>();
		
		resultMap.put("list", list);
		resultMap.put("totalPageCnt", totalPageCnt);
		resultMap.put("curGroupIdx", curGroupIdx);
		resultMap.put("beginPage", beginPage);
		resultMap.put("endPage", endPage);
		resultMap.put("currentPage", currentPage);
		resultMap.put("keyword", keyword);
		
		return resultMap;
	}
	
}
