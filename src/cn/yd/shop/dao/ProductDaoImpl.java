package cn.yd.shop.dao;

//ctrl + shift + o: 导入导出包
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sun.org.apache.bcel.internal.generic.NEW;

import cn.yd.shop.model.Product;

// 此类主要完成数据库的CRUD, 继承BaseDao则可以使用baseDao提供的方法
public  class ProductDaoImpl extends BaseDaoImpl<Product> {
	// 1: java中每个类默认有缺省的构造方法,只有构造方法不需要写返回值
	// 2: 如果显示声明了构造方法,则缺省构造则失效
	// public ProductDaoImpl(int num){
	// this.num = num;
	// }

	@Override
	protected Product getRow(ResultSet rs) throws SQLException {
		Product product = new Product();
		product.setId(rs.getInt("id"));
		product.setName(rs.getString("name"));
		product.setPrice(rs.getDouble("price"));
		product.setRemark(rs.getString("remark"));
		return product;
	}

	// shift + alt + A 列模式

	// 测试代码编写main方法中缺点：有侵入性,不能保留测试历史记录，Java有专业测试框架,Junit
	// 如果没有给集合指定类型,则默认就是object类型.可以指定泛型<Product>
	
	// 通过id获取指定的商品数据
	public ArrayList<Product> getById(int id) {
		String sql = "select * from product where id = ?";
		 return  super.queryByCondition(sql, id);
	}
	public ArrayList<Product> getByName(String name) {
		String sql = "select * from product where name like ?";
		return  super.queryByCondition(sql,"%"+name+"%");
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
