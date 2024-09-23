package com.internship;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/redirect/*")
public class Redirect extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] paths=req.getRequestURI().split("/");
		String shorturl=paths[paths.length-1];
		String original=findURL(shorturl);
		System.out.println(original);
		resp.sendRedirect(original);
	}

	private String findURL(String shorturl) {
		Connection connection =null;
		PreparedStatement preparedStatement=null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/url_db?user=root&password=root");
			preparedStatement=connection.prepareStatement("select url from url_table where shorturl =?");
			preparedStatement.setString(1, shorturl);
			ResultSet resultset=preparedStatement.executeQuery();
			while(resultset.next())
			{
				return resultset.getString(1);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		finally {
			try {
				if(connection!=null)
					connection.close();
				if(preparedStatement!=null)
					preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
