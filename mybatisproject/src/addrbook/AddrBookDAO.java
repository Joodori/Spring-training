package addrbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

public class AddrBookDAO {
	static String sql = "select * from employees where DEPARTMENT_ID = ";
	static String userName = "hr";
	static String password = "hr";
	static String sqlInsertDB = "insert into addrbook(AB_ID,AB_NAME,ab_email,ab_comdept,ab_birth,ab_tel,ab_memo) "
			+ "values(ab_seq.nextval,?,?,?,?,?,?)";

	public boolean insertDB(AddrBookVO ab) throws Exception {
		boolean result = false;
		try {
			SqlSession session = SessionUtil.getSession();
			int insertedAmount = session.insert("insertDB", ab);
			session.commit();
			if (insertedAmount > 0)
				result = true;
				System.out.println(result);
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	public boolean deleteDB(AddrBookVO vo) throws Exception {
		boolean result = false;
		try {
			SqlSession session = SessionUtil.getSession();
			// insert, delete, update는 return이 int값으로 돌아오기 때문에 int라는 변수에 결과값을 받게함
			// delete가 실행될 때 SqlSession이라는 클래스에 있는 delete라는 메서드를 실행할건데 parameter는 deleteDB(mapperID)와 vo라는 VO객체를 사용할 것임
			int deletedAmount = session.delete("deleteDB",vo);
			session.commit();
			if (deletedAmount > 0) {
				result = true;
				System.out.println("ID : " + vo.abId + " deleted");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void insertDB2(AddrBookVO ab) throws Exception {
		SessionUtil.getSession().insert("insertDB", ab);
	}

	public List<AddrBookVO> getDBList() throws Exception {
		List<AddrBookVO> result = new ArrayList<AddrBookVO>();
		result = SessionUtil.getSession().selectList("getDBList");
		return result;
	}

	public List<AddrBookVO> getDB(int abId) {
		return null;
	}

	public boolean updateDB(AddrBookVO ab) throws Exception {
		return false;
	}

}
