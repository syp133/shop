package cn.yd.shop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import cn.yd.shop.util.JdbcUtil;

// 所有dao的父类,主要用来抽取共性代码
public class BaseDaoImpl {
	// 抽取update insert delete , protected:只有子类可以访问
	protected void update(String sql,Object[] param){
		// 1: 获取数据库连接对象
		Connection conn = null;
		// 2: 创建执行SQL语句prepareStatement对象
		PreparedStatement pre = null;
		try {
			conn = JdbcUtil.getConnection();
			pre = conn.prepareStatement(sql);
			for(int i=0;i<param.length;i++){
				pre.setObject(i+1, param[i]);
			}
			// 3: 对每个?进行赋值操作
//			pre.setString(1, product.getName());
//			pre.setDouble(2, product.getPrice().doubleValue());
//			pre.setString(3, product.getRemark());
			
			// 4: 执行SQL语句(在java中 insert update delete 都称为update)
			pre.executeUpdate();
			// 5: 释放connection连接对象
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally{
			JdbcUtil.close(conn, pre);
		}

	}
}
