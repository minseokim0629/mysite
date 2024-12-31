package mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {
	@Autowired
	private DataSource dataSource;
	private SqlSession sqlSession;
	
	public GuestbookRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public int insert(GuestbookVo vo) {
		return sqlSession.insert("guestbook.insert");
		/*int count = 0;

		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("insert into guestbook values(null, ?, ?, ?, now())");
		)
		{
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getContents());

			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		return count;*/
	}
	
	public List<GuestbookVo> findAll() {
		return sqlSession.selectList("guestbook.findAll");
		/*
		List<GuestbookVo> result = new ArrayList<>();
		try (
			// 주로 생성되는 코드를 적는다
			// pstmt에 binding하는 코드가 있으면 rs를 올리기 어렵지만 binding 하는 코드가 없으니 rs를 올렸다
			Connection conn = dataSource.getConnection(); 
			PreparedStatement pstmt = conn.prepareStatement("select id, name, contents, date_format(reg_date, '%Y-%m-%d %h:%i:%s') from guestbook order by reg_date desc");
			ResultSet rs = pstmt.executeQuery();
		) {
			while (rs.next()) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);
				String contents = rs.getString(3);
				String reg_date = rs.getString(4);
				GuestbookVo vo = new GuestbookVo();

				vo.setId(id);
				vo.setName(name);
				vo.setContents(contents);
				vo.setRegDate(reg_date);
				
				result.add(vo);
			}
			rs.close();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return result;*/
	}
	
	public int deleteByIdAndPassword(Long id, String password) {
		return sqlSession.delete("guestbook.deleteByIdAndPassword", Map.of("id", id, "password", password));
		/*int count = 0;

		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("delete from guestbook where id=? and password=?");
		)
		{
			pstmt.setLong(1, id);
			pstmt.setString(2, password);

			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		return count;*/
	}
}
