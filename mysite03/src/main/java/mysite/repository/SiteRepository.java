package mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import mysite.vo.SiteVo;

@Repository
public class SiteRepository {
	private SqlSession sqlSession;
	
	public SiteRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public SiteVo findById() {
		return sqlSession.selectOne("site.findById");
	}
	
	public int update(SiteVo siteVo) {
		return sqlSession.update("site.update", siteVo);
	}
}
