package mysite.repository;

import org.apache.ibatis.session.SqlSession;

public class SiteRepository {
	private SqlSession sqlSession;
	
	public SiteRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	//private SiteVo findById()
}
