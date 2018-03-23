package cn.yd.shop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sun.xml.internal.ws.Closeable;

// 此工具类用来完成数据库的连接与释放操作
public class JdbcUtil {

	// java开发中：配置文件、驱动通常只需要加载一次.这些代码可以存放在静态块中
	static { // 此块在JdbcUtil.class文件加载到JVM虚拟机中会执行,且执行一次
		// 1: 获取数据库驱动文件
		// ctrl + 2,L 自动生成本地变量
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			// e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void close(Connection conn, PreparedStatement pre) {
		close(conn, pre, null);
	}

	// 编写一个方法,来实现数据库资源释放
	public static void close(Connection conn, PreparedStatement pre,
			ResultSet rs) {
		try {
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (pre != null && !pre.isClosed()) {
					pre.close();
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				try {
					if (conn != null && !conn.isClosed()) {
						conn.close();
					}
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}

	}

	public static Connection getConnection() {
		String url = "jdbc:mysql://localhost:3306/shop?characterEncoding=utf8";
		try {
			return DriverManager.getConnection(url, "root", "root");
		} catch (SQLException e) {
			// 异常不能够打印只能向上抛出
			throw new RuntimeException(e);
		}
	}

	
}
