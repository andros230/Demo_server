import java.sql.SQLException;


public class test {
	public static void main(String[] args) throws SQLException {
		System.out.println(new DB_Kit().select("select * from location where mac !='a0:86:c6:33:e0:12'"));
		
		System.out.println("333");
		
		System.out.println("444");
	}

}
