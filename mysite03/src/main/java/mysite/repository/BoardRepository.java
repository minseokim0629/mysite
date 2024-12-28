package mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	
	public int insert(BoardVo vo) {
		int count = 0;

		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("insert into board values(null, ?, ?, 0, now(), ?, ?, ?, ?)");
		) { 
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getgNo());
			pstmt.setLong(4, vo.getoNo());
			pstmt.setLong(5, vo.getDepth());
			pstmt.setLong(6, vo.getUserId());
			
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return count;
	}
	
	public int update(BoardVo vo) {
		int count = 0;

		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("update board set title=?, contents=? where id=?");
		) {
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getId());
			
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return count;
	}
	
	public int deleteById(Long id) {
		int count = 0;

		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("delete from board where id=?");
		)
		{
			pstmt.setLong(1, id);

			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		return count;
	}
	
	public int updateoNo(BoardVo vo) {
		int count = 0;

		try (
			Connection conn = getConnection();
				// update board set o_no = o_no + 1 where g_no가 같은 것 and o_no >= 세팅값
			PreparedStatement pstmt = conn.prepareStatement("update board set o_no = o_no + 1 where g_no = ? and o_no >= ?");
		) {
			pstmt.setLong(1, vo.getgNo());
			pstmt.setLong(2, vo.getoNo());
			
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return count;
	}
	
	public int updateHit(BoardVo vo) {
		int count = 0;

		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("update board set hit=? where id=?");
		) {
			pstmt.setLong(1, vo.getHit() + 1);
			pstmt.setLong(2, vo.getId());
			
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return count;
	}
	
	public List<BoardVo> findBoards(String kwd, int currentpage, int cnt) {
		List<BoardVo> result = new ArrayList<>();
		try (
			Connection conn = getConnection(); 
			PreparedStatement pstmt = conn.prepareStatement("select a.id, a.title, a.contents, a.hit, date_format(a.reg_date, '%Y-%m-%d %h:%i:%s'), a.g_no, a.o_no, a.depth, a.user_id, b.name  from board a join user b on a.user_id = b.id where a.title like ? order by g_no desc, o_no asc limit ?, ?");
		) {
			pstmt.setString(1, "%" + kwd + "%");
			pstmt.setInt(2, (currentpage - 1)*cnt);
			pstmt.setInt(3, cnt);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Long id = rs.getLong(1);
				String title = rs.getString(2);
				String content = rs.getString(3);
				Long hit = rs.getLong(4);				
				String reg_date = rs.getString(5);
				Long gNo = rs.getLong(6);
				Long oNo = rs.getLong(7);
				Long depth = rs.getLong(8);
				Long userId = rs.getLong(9);
				String userName = rs.getString(10);
				BoardVo vo = new BoardVo();

				vo.setId(id);
				vo.setTitle(title);
				vo.setContent(content);
				vo.setHit(hit);
				vo.setRegDate(reg_date);
				vo.setgNo(gNo);
				vo.setoNo(oNo);
				vo.setDepth(depth);
				vo.setUserId(userId);
				vo.setUserName(userName);
				
				result.add(vo);
			}
			rs.close();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return result;
	}
	
	public Long findMaxgNo() {
		Long maxgNo = 1L;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select ifnull(max(g_no), 0) from board");
			ResultSet rs = pstmt.executeQuery();
		) {
			if(rs.next()) {
				maxgNo = rs.getLong(1);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return maxgNo;
	}
	
	public BoardVo findById(Long id) {
		BoardVo boardVo = null;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select title, contents, hit, date_format(reg_date, '%Y-%m-%d %h:%i:%s'), g_no, o_no, depth, user_id from board where id = ?");
		) {
			pstmt.setLong(1, id);

			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String title = rs.getString(1);
				String content = rs.getString(2);
				Long hit = rs.getLong(3);				
				String reg_date = rs.getString(4);
				Long gNo = rs.getLong(5);
				Long oNo = rs.getLong(6);
				Long depth = rs.getLong(7);
				Long userId = rs.getLong(8);
				
				boardVo = new BoardVo();	
				boardVo.setId(id);
				boardVo.setTitle(title);
				boardVo.setContent(content);
				boardVo.setHit(hit);
				boardVo.setRegDate(reg_date);
				boardVo.setgNo(gNo);
				boardVo.setoNo(oNo);
				boardVo.setDepth(depth);
				boardVo.setUserId(userId);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return boardVo;
	}
	
	public int getTotalCnt(String kwd) {
		int totalCnt = 0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select count(*) from board where title like ?");
		) {
			pstmt.setString(1, "%" + kwd + "%");
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				totalCnt = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return totalCnt;
	}
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			// 1. JDBC Driver 로딩
			Class.forName("org.mariadb.jdbc.Driver");

			// 2. 연결하기
			String url = "jdbc:mariadb://192.168.0.101:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}

		return conn;
	}

	
}
