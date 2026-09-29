package com.chatting;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/chatting_system",
                "root",
                "Poovarasi@123"
            );

            PreparedStatement ps = con.prepareStatement(
                "INSERT IGNORE INTO users(username) VALUES(?)"
            );

            ps.setString(1, username);
            ps.executeUpdate();

            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            response.sendRedirect("chat.jsp");

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Login Error: " + e.getMessage());
        }
    }
}