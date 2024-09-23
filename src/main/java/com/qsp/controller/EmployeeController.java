package com.qsp.controller;
import java.sql.*;
import java.util.*;

import com.qsp.model.Employee;

public class EmployeeController {
	static Connection con;
	static String url="jdbc:postgresql://localhost:5432/qsp";
	static String user="postgres";
	static String pass="root";
	
	static {
			try{
				//1
				Class.forName("org.postgresql.Driver");
				//2
				con=DriverManager.getConnection(url,user,pass);
				System.out.println(con);
			} catch(Exception e) {
				e.printStackTrace();
		}		
}
	
	public static void insert(Employee e) {
		try {
			//3
			PreparedStatement ps=con.prepareStatement("insert into employee values(?,?,?)");
			ps.setInt(1, e.getId());
			ps.setString(2, e.getName());
			ps.setDouble(3, e.getSalary());
			//4
			ps.executeUpdate();
		}catch(SQLException el) {
			el.printStackTrace();
		}
	}
	public static boolean updateNameById(int id,String name) {
		//3
		Employee emp=fetchById(id);
		if(emp.getName()!=null) {
			try {//3
			PreparedStatement ps=con.prepareStatement("update employee set name=? where id=?");
			ps.setString(1, name);
			ps.setInt(2, id);
			//4
			ps.executeUpdate();
			}catch (SQLException e) {
				e.printStackTrace();
			}
			return true;
		}else {
			return false;
		}
	}
	public static Employee fetchById(int id) {
		Employee e=new Employee();
		try {
			PreparedStatement ps=con.prepareStatement("select * from employee where id=?");
			ps.setInt(1, id);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				e.setId(rs.getInt("id"));
				e.setName(rs.getString("name"));
				e.setSalary(rs.getDouble("sal"));
			}
		}
		catch(SQLException el) {
				el.printStackTrace();
			}
			return e;
		} 
	public static boolean deleteById(int id) {
		Employee e=fetchById(id);
		if(e.getName()!=null)
		{
			try {
			PreparedStatement ps=con.prepareStatement("delete from employee where id=?");
			ps.setInt(1, id);
			//4
			ps.executeUpdate();
		}catch(SQLException el) {
			el.printStackTrace();
		}
		return true;
		}
		else {
			return false;
		}
	}

	public static Employee fetchByName(String name) {
		Employee e=new Employee();
		try {
			PreparedStatement ps=con.prepareStatement("select * from employee where name=?");
			ps.setString(1, name);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				e.setId(rs.getInt("id"));
				e.setName(rs.getString("name"));
				e.setSalary(rs.getDouble("sal"));
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return e;	
	}
	public static boolean deleteByName(String name) {
		Employee e=fetchByName(name);
		if(e.getName()!=null) 
		{
		try {
			//3
		PreparedStatement ps=con.prepareStatement("delete from employee where name=?");
		ps.setString(1, name);
		//4
		ps.executeUpdate();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return true;
		}else {
			return false;
		}
	}
	public static void updateSalaryById(int id, double sal) {
		Employee e = fetchById(id);
		try {
			// 3rd step
			PreparedStatement ps = con.prepareStatement("update employee set sal = ? where id = ?");
			ps.setDouble(1, sal);
			ps.setInt(2, id);

			// 4th step
			ps.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		System.out.println("Salary updated successfully");
	}

	
	public static List<Employee> fetchAll()
	{
		List <Employee> li = new ArrayList<Employee>();
		try {
			PreparedStatement ps = con.prepareStatement("Select * from employee");
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				Employee emp = new Employee();
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setSalary(rs.getDouble("sal"));
				li.add(emp);
			}	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return li;		
	}
	public static void closeConnection() {
		// TODO Auto-generated method stub
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}



















