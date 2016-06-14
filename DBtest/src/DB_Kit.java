import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class DB_Kit {
	private String USERNAME = "root";
	private String PASSWORD = "andros230";
	private String DRIVER = "com.mysql.jdbc.Driver";
	private String URL = "jdbc:mysql://localhost:3306/amap";

	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	

	public DB_Kit() {
		try {
			Class.forName(DRIVER);
			connect = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			statement = connect.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 添加, mac为主键
	public int addData(String mac, String lng, String lat) throws SQLException {
		int a;
		resultSet = statement
				.executeQuery("select * from location where mac ='" + mac + "'");
		if (resultSet.next()) {
			a = updateKit(mac, lng, lat);
		} else {
			preparedStatement = connect
					.prepareStatement("insert into location (mac, lng, lat) values (?, ?, ?)");
			preparedStatement.setString(1, mac);
			preparedStatement.setString(2, lng);
			preparedStatement.setString(3, lat);
			a = preparedStatement.executeUpdate();
			close();
		}

		return a;
	}

	// 删除
	public int delete(String mac) throws SQLException {
		preparedStatement = connect
				.prepareStatement("delete from location where mac= ?");
		preparedStatement.setString(1, mac);
		int a = preparedStatement.executeUpdate();
		close();
		return a;
	}

	// 修改
	public int updateKit(String mac, String lng, String lat)
			throws SQLException {
		preparedStatement = connect
				.prepareStatement("update location set lng = ?, lat = ? where mac = ?");
		preparedStatement.setString(1, lng);
		preparedStatement.setString(2, lat);
		preparedStatement.setString(3, mac);
		int a = preparedStatement.executeUpdate();
		close();
		return a;
	}

	// 查询
	public String select(String sql) throws SQLException {
		Gson gson = new Gson();
		List<LatLonKit> kit = new ArrayList<LatLonKit>();
		resultSet = statement.executeQuery(sql);
		while (resultSet.next()) {
			LatLonKit k = new LatLonKit();
        	k.setMac(resultSet.getString("mac"));
        	k.setLongitude(resultSet.getString("lng"));
        	k.setLatitude(resultSet.getString("lat"));
            kit.add(k);
		}
		close();
		return gson.toJson(kit);
	}

	// 查询mac是否存在
	public boolean macExist(String mac) throws SQLException {
		boolean bool;
		resultSet = statement
				.executeQuery("select * from location where mac ='" + mac + "'");
		if (resultSet.next()) {
			bool = true;
		} else {
			bool = false;
		}
		close();
		return bool;

	}

	// 关闭数据库
	public void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}

}
