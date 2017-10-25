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
            //ct=DriverManager.getConnection("jdbc:oracle:thin:@(description =(ADDRESS_LIST =(ADDRESS = (PROTOCOL = TCP)(HOST = 10.72.5.102)(PORT = 1521))(ADDRESS = (PROTOCOL = TCP)(HOST = 10.72.5.106)(PORT = 1521))(load_balance = yes)(failover = yes))(connect_data = (service_name= oracycn)(instance_name=oracycn1)(instance_name=oracycn2)))", "syzx", "syzx6237240");
            ct=DriverManager.getConnection("jdbc.url=jdbc:oracle:thin:@222.185.195.26:1521:syydata", "scott", "asdf123");
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
