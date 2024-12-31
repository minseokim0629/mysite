package mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mysite.vo.UserVo;

@Repository
public class UserRepository {
	@Autowired
	private DataSource dataSource;
	
	private SqlSession sqlSession;
	
	
	public UserRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public int insert(UserVo vo) {
		return sqlSession.insert("user.insert", vo);
		/*int count = 0;

		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("insert into user values(null, ?, ?, ?, ?, now(), 'USER')");
		) {
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());

			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return count;*/
	}

	public int update(UserVo vo) {
		return sqlSession.update("user.update", vo);
		/*int count = 0;

		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt1 = conn.prepareStatement("update user set name=?, gender=? where id=?");
			PreparedStatement pstmt2 = conn.prepareStatement("update user set name=?, password=?, gender=? where id=?");
		) {
			if("".equals(vo.getPassword())) {
				pstmt1.setString(1, vo.getName());
				pstmt1.setString(2, vo.getGender());
				pstmt1.setLong(3, vo.getId());
				
				count = pstmt1.executeUpdate();
			}
			else {
				pstmt2.setString(1, vo.getName());
				pstmt2.setString(2, vo.getPassword());
				pstmt2.setString(3, vo.getGender());
				pstmt2.setLong(4, vo.getId());

				count = pstmt2.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return count;*/
	}
	
	public UserVo findByEmailAndPassword(String email, String password) {
		return sqlSession.selectOne("user.findByEmailAndPassword", Map.of("email", email, "password", password));
		
		/*try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select id, name, role from user where email=? and password=?");
		) {
			pstmt.setString(1, email);
			pstmt.setString(2, password);

			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);
				String role = rs.getString(3);
				
				userVo = new UserVo();				
				userVo.setId(id);
				userVo.setName(name);
				userVo.setRole(role);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return userVo;*/
	}
	
	public UserVo findById(Long userId) {
		return sqlSession.selectOne("user.findById", userId);
		/*UserVo userVo = null;
		
		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select id, name, email, gender from user where id=?");
		) {
			pstmt.setLong(1, id);

			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String name = rs.getString(1);
				String email = rs.getString(2);
				String gender = rs.getString(3);
				
				userVo = new UserVo();	
				userVo.setId(id);
				userVo.setName(name);
				userVo.setEmail(email);
				userVo.setGender(gender);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return userVo;*/
	}
}
