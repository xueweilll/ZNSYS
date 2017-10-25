package com.jfsl.servlet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Conn {
	
	Statement stmt=null;
	 Connection ct=null;
	public Statement getStatement(){
        // TODO Auto-generated method stub
        try
        {
            //加载驱动
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //得到连接
            ct=DriverManager.getConnection("jdbc:oracle:thin:@10.72.21.237:1521:syydata", "SCOTT", "TGQ2012");
            Statement stm=ct.createStatement();
            return stm;  
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
	
	public ResultSet getResulset(String sql){
		stmt=this.getStatement();
		ResultSet r=null;
		try {
			r = stmt.executeQuery(sql);
			return r;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	public int update(String sql){
		stmt=this.getStatement();
		int i=0;
		try {
			i=stmt.executeUpdate(sql);
			return i;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			i=0;
			return i;
		}
		
	}
	public void closeall() throws SQLException{
		stmt.close();
		ct.close();
	}
}
