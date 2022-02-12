package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class MemberDAO {
	private Connection conn;
	private PreparedStatement psmt;
	private ResultSet rs;
	private int cnt;
	private String sql;

	// DB ����
	public void connect() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@project-db-stu.ddns.net:1524:xe";
			String user = "campus_f_4_0115";
			String password = "smhrd4";

			conn = DriverManager.getConnection(url, user, password);

		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// DB ���� ����
	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (psmt != null) {
				psmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// ȸ�� ����
	public int memberJoin(MemberDTO memberDTO) {

		connect();
		
		// id, pw, nick, phone, birth, sex ������ �Է�
		sql = "insert into MEMBER_TEST values(?,?,?,?,?,?)";

		try {
			psmt = conn.prepareStatement(sql);
			
			psmt.setString(1, memberDTO.getId());
			psmt.setString(2, memberDTO.getPw());
			psmt.setString(3, memberDTO.getNick());
			psmt.setString(4, memberDTO.getPhone());
			psmt.setString(5, memberDTO.getBirthday());
			psmt.setInt(6, memberDTO.getSex());

			cnt = psmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally { 
			close();
		}
		
		return cnt;
	}
	// �α���
	public MemberDTO memberLogin(String id, String pw) {
		
		connect();
		
		MemberDTO member = null;
		sql = "select * from MEMBER_TEST where id = ? and pw = ? ";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, pw);

			rs = psmt.executeQuery();
			
			if (rs.next()) {
				member = new MemberDTO(id, pw, rs.getString("nick"), rs.getString("phone"), rs.getString("birth"), rs.getInt("sex"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return member;
	}
	// ��������
	public int update(MemberDTO memberDTO) {
		
		connect();

		sql = "update member set pw=?, nick=?, phone=? where id=?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(4, memberDTO.getId());
			psmt.setString(1, memberDTO.getPw());
			psmt.setString(2, memberDTO.getNick());
			psmt.setString(3, memberDTO.getPhone());

			cnt = psmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return cnt;
	}
	// �ߺ�Ȯ���� ���� �޼ҵ�
	public ArrayList<String> idSelectAll() {

		connect();

		ArrayList<String> list = new ArrayList<String>();

		sql = "select id from MEMBER_TEST";

		try {
			psmt = conn.prepareStatement(sql);

			rs = psmt.executeQuery();

			while (rs.next()) {
				String id = rs.getString("ID");
				list.add(id);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}
}