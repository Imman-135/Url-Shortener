package com.internship;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/")
public class GetUrls extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<URL> urls=getUrls();
		req.setAttribute("urls", urls);
		req.getRequestDispatcher("home.jsp").forward(req, resp);
	}
	public List<URL> getUrls(){
		List<URL> list=new ArrayList<URL>();
		Connection connection=null;
		PreparedStatement preparedstatement=null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/url_db?user=root&password=root");
			preparedstatement=connection.prepareStatement("select * from url_table");
			ResultSet urls=preparedstatement.executeQuery();
	
			while(urls.next()){
				URL url=new URL();
				url.setActualUrl(urls.getString(2));
				url.setShortUrl(urls.getString(1));
				list.add(url);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
				if(connection!=null)
					connection.close();
				if(preparedstatement!=null)
					preparedstatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;	
	}
}
