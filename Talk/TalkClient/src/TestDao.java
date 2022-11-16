import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestDao {
	private Connection conn;
	private static final String USERNAME = "kong";
	private static final String PASSWORD = "1234";
	private static final String URL = "jdbc:mysql://localhost:3306/NP?user_list";
	
	public TestDao() {
		try {
            System.out.println("Maker");
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Driver Loading Successed");
        } catch (Exception e) {
        	System.out.println(e);
            System.out.println("Driver Loading Failed");
            try {
                conn.close();
            } catch (SQLException e1) {
            	System.out.println(e1);
            }
        }
	}
	
	public void insertUser() {
		String sql = "INSERT INTO user_list values(?, ?, ?, ?)";
		
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 2);
			pstmt.setString(2, "박제형");
			pstmt.setBlob(3, null, 0);
			pstmt.setString(4, "Day6");
			
			int result = pstmt.executeUpdate();
            if(result==1) {
                System.out.println("User 데이터 삽입 성공!");
                
            }
		} catch (Exception e) {
            System.out.println(e);
        }    finally {
            try {
                if(pstmt!=null && !pstmt.isClosed()) {
                    pstmt.close();
                }
            } catch (Exception e2) {}
        }
	}
}
