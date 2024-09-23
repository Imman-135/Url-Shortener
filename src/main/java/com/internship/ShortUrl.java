package com.internship;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/short")
public class ShortUrl extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url=req.getParameter("url");
		String shorturl=getRandom();
		List<URL> list=new ArrayList<URL>();
		URL urls=new URL();
		urls.setActualUrl(url);
		urls.setShortUrl(shorturl);
		int result=url(url,shorturl);
		if(result>0)
		{
			resp.sendRedirect("/ShortenURL/");
		}
		
	}

	private int url(String name,String shorturl) {
		Connection connection=null;
		PreparedStatement preparestatement=null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/url_db?user=root&password=root");
			preparestatement=connection.prepareStatement("insert into url_table values(?,?)");
			preparestatement.setString(1,shorturl);
			preparestatement.setString(2, name);
			return preparestatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				if(connection!=null)
					connection.close();
				if(preparestatement!=null)
					preparestatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
		
	}

	private String getRandom() {
		char[] c=new char[10];
		for(int i=0;i<c.length;i++)
		{
			c[i]=(char) ((int) (Math.random() * (90 - 65)) + 65);
		}
		return new String(c);
		
	}

}
