package mysite.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	private SqlSession sqlSession;
	
	public BoardRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public int insert(BoardVo vo) {
		return sqlSession.insert("board.insert", vo);
	}
	
	public int update(BoardVo vo) {
		return sqlSession.update("board.update", vo);
	}
	
	public int deleteById(Long id) {
		return sqlSession.delete("board.deleteById", id);
	}
	
	public int updateoNo(BoardVo vo) {
		return sqlSession.update("board.updateoNo", vo);
	}
	
	public int updateHit(BoardVo vo) {
		return sqlSession.update("board.updateHit", vo);
	}
	
	public List<BoardVo> findBoards(String kwd, int currentpage, int cnt) {
		return sqlSession.selectList("board.findBoards", Map.of("keyword", "%"+kwd+"%", "startIndex", (currentpage - 1)*cnt, "size", cnt));
	}
	
	public Long findMaxgNo() {
		return sqlSession.selectOne("board.findMaxgNo");
	}
	
	public BoardVo findById(Long id) {
		return sqlSession.selectOne("board.findById", id);
	}
	
	public int getTotalCnt(String kwd) {
		return sqlSession.selectOne("board.getTotalCnt", "%" + kwd + "%");
	}
}
