package com.mmit.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mmit.model.Librarian;

public class DatabaseHandler {

	// start create connection
	private static Connection createConnection() throws SQLException {
		String username = "root";
		String password = "root";

		Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/lms", username, password);
		return con;
	}
	// end create connection

	// start librarian login
	public static Librarian librarianLogin(String email, String password) {
		Librarian librarian = null;

		try (Connection con = createConnection()) {
			var query = "SELECT * FROM librarians WHERE email = ? AND password = ?";

			PreparedStatement pstm = con.prepareStatement(query);

			pstm.setString(1, email);
			pstm.setString(2, password);

			ResultSet result = pstm.executeQuery();

			if (result.next()) {
				librarian = new Librarian();
				librarian.setId(result.getInt("id"));
				librarian.setEmail(email);
				librarian.setPassword(password);
			}
		} catch (Exception e) {
			e.getStackTrace();
			System.err.println("Error from librarian login");
		}
		return librarian;
	}
	// end librarian login

	// start librarian register
	public static void librarianRegister(String email, String password, String nrc_no, String phone) {
		try (Connection con = createConnection()) {
			var query = "INSERT INTO librarians(email, password, nrc_no, phone)VALUES(?, ?, ?, ?)";

			PreparedStatement pstm = con.prepareStatement(query);

			pstm.setString(1, email);
			pstm.setString(2, password);
			pstm.setString(3, nrc_no);
			pstm.setString(4, phone);

			pstm.executeQuery();

		} catch (Exception e) {
			e.getStackTrace();
			System.err.println("Error from librarian register");
		}
	}
	// end librarian register

}
