package cn.yd.shop.dao;

//ctrl + shift + o: 导入导出包
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import cn.yd.shop.model.Product;
import cn.yd.shop.util.JdbcUtil;

// 此类主要完成数据库的CRUD, 继承BaseDao则可以使用baseDao提供的方法
public class ProductDaoImpl extends BaseDaoImpl {
	
	
	

	// 1: java中每个类默认有缺省的构造方法,只有构造方法不需要写返回值
	// 2: 如果显示声明了构造方法,则缺省构造则失效
	// public ProductDaoImpl(int num){
	// this.num = num;
	// }

	// shift + alt + A 列模式
	
	// 测试代码编写main方法中缺点：有侵入性,不能保留测试历史记录，Java有专业测试框架,Junit
	public static void main(String[] args) {
	}
	// 如果没有给集合指定类型,则默认就是object类型.可以指定泛型<Product>
	public ArrayList<Product> queryByBame(String name){
		ArrayList<Product> proList = new ArrayList<Product>();
		String sql = "select * from product where name like ?";
		// 1: 获取数据库连接对象
		Connection conn = null; 
		PreparedStatement pre = null;
		ResultSet rs = null;
		// 2: 创建执行SQL语句prepareStatement对象
		try {
			conn = JdbcUtil.getConnection();
 			pre = conn.prepareStatement(sql);
			pre.setString(1, "%" + name + "%");
			rs = pre.executeQuery();// 用来存储查询返回的结果集 
			// 将光标从当前位置向前移一行。ResultSet 光标最初位于第一行之前；第一次调用 next 方法使第一行成为当前行；第二次调用使第二行成为当前行，依此类推
			while(rs.next()){
				// 如果结果集有数据,则获取  记录 --> 对象
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setPrice(rs.getDouble("price"));
				product.setRemark(rs.getString("remark"));
				proList.add(product);
			}
			return proList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally{
			JdbcUtil.close(conn, pre, rs);	
		}
	}

	// 通过id获取指定的商品数据
	public Product getById(int id) {
		Product product = null;
		String sql = "select * from product where id = ?";
		// 1: 获取数据库连接对象
		Connection conn = null; 
		PreparedStatement pre = null;
		ResultSet rs = null;
		// 2: 创建执行SQL语句prepareStatement对象
		try {
			conn = JdbcUtil.getConnection();
 			pre = conn.prepareStatement(sql);
			pre.setInt(1, id);
			rs = pre.executeQuery();// 用来存储查询返回的结果集 
			// 将光标从当前位置向前移一行。ResultSet 光标最初位于第一行之前；第一次调用 next 方法使第一行成为当前行；第二次调用使第二行成为当前行，依此类推
			if(rs.next() == true){
				// 如果结果集有数据,则获取  记录 --> 对象
				product = new Product();
				product.setName(rs.getString("name"));
				product.setPrice(new Double(rs.getDouble("price")));
				product.setRemark(rs.getString("remark"));
			}
			return product;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally{
			JdbcUtil.close(conn, pre, rs);	
		}
		
	}

	// 完成数据的插入操作 ctrl + shift + f
	public void save(Product product) {
		String sql = "insert into product (name,price,remark) values (?,?,?)";
		// java是单一继承,因此super指向是唯一的父类
		super.update(sql, new Object[] { product.getName(), product.getPrice(),
				product.getRemark() });
	}

	public void update(Product product) {
		String sql = "update product set name=?,price=?,remark=? where id=?   ";
		super.update(sql, new Object[] { product.getName(), product.getPrice(),
				product.getRemark(), product.getId() });
	}

	public void delete(int id) {
		String sql = "delete from product where id = ?";
		super.update(sql, new Object[] { new Integer(id) });
	}
}
