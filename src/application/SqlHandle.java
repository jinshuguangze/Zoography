package application;

import java.sql.*;
import java.util.*;

public class SqlHandle {

	static String selectsql = null;
	static ResultSet retsult = null;

	public static final String url = "jdbc:mysql://127.0.0.1/mysql?characterEncoding=utf8&useSSL=true";
	public static final String name = "com.mysql.jdbc.Driver";
	public static final String user = "root";
	public static final String password = "tmzbh19961004";

	public static Connection conn = null;
	public static PreparedStatement pst = null;

	public static String[][] readData() {
		int paraCount = 11; // 读取参数数量
		selectsql = "select * from animals";// SQL语句

		try {
			Class.forName(name);// 指定连接类型
			conn = DriverManager.getConnection(url, user, password);// 获取连接
			pst = conn.prepareStatement(selectsql);// 准备执行语句
		} catch (Exception e) {
			e.printStackTrace();
		}

		String[][] paras = new String[100000][paraCount];
		try {
			retsult = pst.executeQuery();// 执行语句，得到结果集
			int i=0;
			while (retsult.next()) {
					for (int j = 0; j < paraCount; j++) {
						paras[i][j] = retsult.getString(j+1);
					
					}
					i++;
			} // 显示数据
			retsult.close();
			conn.close();// 关闭连接
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return paras;
	}
	
	public static String[] readData(int order) {
		return readData()[order];
		
	}
	
	public static String readData(int order,int para) {
		return readData()[order][para];
		
	}
}