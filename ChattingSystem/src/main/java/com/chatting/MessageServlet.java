package com.chatting;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/message")
public class MessageServlet extends HttpServlet {

    String url = "jdbc:mysql://localhost:3306/chatting_system";
    String user = "root";
    String password = "Poovarasi@123";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String sender = request.getParameter("sender");
        String receiver = request.getParameter("receiver");
        String message = request.getParameter("message");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, user, password);

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO messages(sender, receiver, message) VALUES (?, ?, ?)"
            );

            ps.setString(1, sender);
            ps.setString(2, receiver);
            ps.setString(3, message);

            ps.executeUpdate();

            con.close();

            response.getWriter().println("Message sent successfully!");

        } catch (Exception e) {
            response.getWriter().println("Error: " + e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String sender = request.getParameter("sender");
        String receiver = request.getParameter("receiver");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, user, password);

            PreparedStatement ps = con.prepareStatement(
                "SELECT sender, message, message_time FROM messages " +
                "WHERE (sender=? AND receiver=?) OR (sender=? AND receiver=?) " +
                "ORDER BY message_time"
            );

            ps.setString(1, sender);
            ps.setString(2, receiver);
            ps.setString(3, receiver);
            ps.setString(4, sender);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                response.getWriter().println(
                    "<p><b>" + rs.getString("sender") + ":</b> "
                    + rs.getString("message") + "</p>"
                );
            }

            con.close();

        } catch (Exception e) {
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}