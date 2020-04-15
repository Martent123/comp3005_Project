package comp3005_project;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

import javafx.collections.ObservableList;

public class DBConnect {

	// change this configuration to your local one
	final String DATABASE_DRIVE = "com.mysql.jdbc.Driver";
	final String TARGET_DB = "jdbc:mysql://localhost:3306/comp3005?useSSL=false";
	final String NAME = "root";
	final String PASSWORD = "12349876";
	private Connection con;

	public DBConnect() {
		try {
			Class.forName(DATABASE_DRIVE);
			con = DriverManager.getConnection(TARGET_DB, NAME, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	public User validate(User user) {

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from user_t where name = '" + user.getName() + "';");
			if (rs.next()) {
				// reset pointer
				rs.beforeFirst();
				while (rs.next()) {
					user.setId(rs.getInt("ID"));
					user.setEmail(rs.getString("EMAIL"));
					user.setPhone(rs.getString("PHONE"));
					user.setRole(rs.getString("role"));
					return user;

				}

			} else {
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

	public User getAddress(User user) {
		try {
			int userId = user.getId();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"select * from address join user_address on address.id = user_address.address_id join user_t on user_address.user_id = user_t.id where user_t.id = "
							+ userId + ";");

			if (rs.next()) {
				ArrayList<Address> list = new ArrayList<Address>();

				// reset pointer
				rs.beforeFirst();
				while (rs.next()) {
					Address temp = new Address();
					temp.setId(rs.getInt("id"));
					temp.setName(rs.getString("street_name"));
					temp.setNumber(rs.getString("street_num"));
					temp.setCode(rs.getString("postal_code"));
					temp.setType(rs.getString("address_type"));
					list.add(temp);
				}
				user.setAddress(list);

			} else {
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

	public void updateUserInfo(User user) {
		try {
			int userId = user.getId();
			String email = user.getEmail();
			String phone = user.getPhone();

			Statement stmt = con.createStatement();
			stmt.executeUpdate(
					"update user_t set email = '" + email + "', phone = '" + phone + "' where id = " + userId + ";");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Book> getBookList(String storeName) {
		ArrayList<Book> resultList = new ArrayList<Book>();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"select book.id,book.name,book.price,book.ISBN,genre.name as genre,book.nop,book.publisher_id,publisher.name as publisher_name, sum(book_warehouse.quantity) as stock from book join genre on book.genre_id = genre.id join publisher on book.publisher_id = publisher.id join store_book on book.id = store_book.book_id join store on store_book.store_id = store.id join book_warehouse on book.id = book_warehouse.book_id where store.store_name = '"
							+ storeName + "' group by book_warehouse.book_id;");

			if (rs.next()) {

				// reset pointer
				rs.beforeFirst();
				while (rs.next()) {
					Book temp = new Book();
					temp.setId(rs.getInt("id"));
					temp.setName(rs.getString("name"));
					temp.setPrice(rs.getDouble("price"));
					temp.setISBN(rs.getString("ISBN"));
					temp.setGenre(rs.getString("genre"));
					temp.setNop(rs.getInt("nop"));
					Publisher tempPublisher = new Publisher();
					tempPublisher.setId(rs.getInt("publisher_id"));
					tempPublisher.setName(rs.getString("publisher_name"));
					temp.setPublisher(tempPublisher);
					temp.setStock(rs.getInt("stock"));
					temp.setAuthors(getAuthorById(temp.getId()));
					resultList.add(temp);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultList;
	}

	public ArrayList<Author> getAuthorById(int id) {
		ArrayList<Author> resultList = new ArrayList<Author>();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select author.id,author.name from author\r\n"
					+ "join book_author on author.id = book_author.author_id\r\n" + "where book_author.book_id = " + id
					+ ";");

			if (rs.next()) {

				// reset pointer
				rs.beforeFirst();
				while (rs.next()) {
					Author temp = new Author();
					temp.setId(rs.getInt("id"));
					temp.setName(rs.getString("name"));
					resultList.add(temp);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultList;
	}

	public void makeOrder(User user, Map<Book, Integer> result) {
		int userid = user.getId();
		int orderID = -1;
		try {

			PreparedStatement ps = con.prepareStatement(
					"INSERT INTO order_t (tracking,user_id) \r\n" + "		VALUES ('order received', " + userid + ");",
					Statement.RETURN_GENERATED_KEYS);
			int afftectedRows = ps.executeUpdate();
			if (afftectedRows == 0) {
				throw new SQLException("Creating user failed, no rows affected.");
			}
			ResultSet keys = ps.getGeneratedKeys();
			if (keys.next()) {
				orderID = (int) keys.getLong(1);
				System.out.println(orderID);
			}

			if (orderID != -1) {
				for (Map.Entry<Book, Integer> entry : result.entrySet()) {
					Book currentBook = entry.getKey();
					int quantity = entry.getValue();
					Statement stmt = con.createStatement();
					stmt.executeUpdate("INSERT INTO order_book(order_id,book_id,quantity)\r\n" + "VALUES (" + orderID
							+ "," + currentBook.getId() + "," + quantity + ");");
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Order> getOrders(int userID) {
		ArrayList<Order> resultList = new ArrayList<Order>();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT order_t.id, order_t.tracking,order_book.quantity, book.name FROM order_t\r\n"
							+ "JOIN order_book on order_t.id = order_book.order_id\r\n"
							+ "JOIN book on order_book.book_id = book.id\r\n"
							+ "JOIN user_T on order_t.user_id = user_T.id\r\n" + "WHERE user_t.id = " + userID + "\r\n"
							+ ";");

			if (rs.next()) {

				// reset pointer
				rs.beforeFirst();
				while (rs.next()) {
					Author temp = new Author();
					temp.setId(rs.getInt("id"));
					temp.setName(rs.getString("name"));
					// resultList.add(temp);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultList;
	}

	public ArrayList<Order> getOrderList(int userID) {
		Statement stmt;
		ArrayList<Order> resultList = new ArrayList<Order>();
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * from order_t\r\n" + "where user_id = " + userID + ";");

			if (rs.next()) {

				// reset pointer
				rs.beforeFirst();
				while (rs.next()) {
					Order temp = new Order();
					temp.setId(rs.getInt("id"));
					temp.setTracking(rs.getString("tracking"));
					resultList.add(temp);
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultList;

	}

	public Book getBookListById(String storeName, int bookID) {
		ArrayList<Book> resultList = new ArrayList<Book>();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"select book.id,book.name,book.price,book.ISBN,genre.name as genre,book.nop,book.publisher_id,publisher.name as publisher_name, sum(book_warehouse.quantity) as stock from book \r\n"
							+ "join genre on book.genre_id = genre.id\r\n"
							+ "join publisher on book.publisher_id = publisher.id\r\n"
							+ "join store_book on book.id = store_book.book_id\r\n"
							+ "join store on store_book.store_id = store.id\r\n"
							+ "join book_warehouse on book.id = book_warehouse.book_id\r\n"
							+ "where store.store_name = \"" + storeName + "\" and book.id =" + bookID + "\r\n"
							+ "group by book_warehouse.book_id; ;");

			if (rs.next()) {

				// reset pointer
				rs.beforeFirst();

				while (rs.next()) {
					Book temp = new Book();

					temp.setId(rs.getInt("id"));
					temp.setName(rs.getString("name"));
					temp.setPrice(rs.getDouble("price"));
					temp.setISBN(rs.getString("ISBN"));
					temp.setGenre(rs.getString("genre"));
					temp.setNop(rs.getInt("nop"));
					Publisher tempPublisher = new Publisher();
					tempPublisher.setId(rs.getInt("publisher_id"));
					tempPublisher.setName(rs.getString("publisher_name"));
					temp.setPublisher(tempPublisher);
					temp.setStock(rs.getInt("stock"));
					temp.setAuthors(getAuthorById(temp.getId()));
					resultList.add(temp);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultList.get(0);
	}

	public ArrayList<Book> getBookListByGenre(String storeName, String genre) {
		ArrayList<Book> resultList = new ArrayList<Book>();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"select book.id,book.name,book.price,book.ISBN,genre.name as genre,book.nop,book.publisher_id,publisher.name as publisher_name, sum(book_warehouse.quantity) as stock from book \r\n"
							+ "join genre on book.genre_id = genre.id\r\n"
							+ "join publisher on book.publisher_id = publisher.id\r\n"
							+ "join store_book on book.id = store_book.book_id\r\n"
							+ "join store on store_book.store_id = store.id\r\n"
							+ "join book_warehouse on book.id = book_warehouse.book_id\r\n"
							+ "where store.store_name = \"" + storeName + "\" and genre.name =\"" + genre + "\"\r\n"
							+ "group by book_warehouse.book_id;");

			if (rs.next()) {

				// reset pointer
				rs.beforeFirst();
				while (rs.next()) {
					Book temp = new Book();
					temp.setId(rs.getInt("id"));
					temp.setName(rs.getString("name"));
					temp.setPrice(rs.getDouble("price"));
					temp.setISBN(rs.getString("ISBN"));
					temp.setGenre(rs.getString("genre"));
					temp.setNop(rs.getInt("nop"));
					Publisher tempPublisher = new Publisher();
					tempPublisher.setId(rs.getInt("publisher_id"));
					tempPublisher.setName(rs.getString("publisher_name"));
					temp.setPublisher(tempPublisher);
					temp.setStock(rs.getInt("stock"));
					temp.setAuthors(getAuthorById(temp.getId()));
					resultList.add(temp);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultList;
	}

	public ArrayList<Book> searchBookList(String storeName, String keyword) {
		ArrayList<Book> resultList = new ArrayList<Book>();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"select book.id,book.name,book.price,book.ISBN,genre.name as genre,book.nop,book.publisher_id,publisher.name as publisher_name,author.name as author_name, sum(book_warehouse.quantity) as stock from book \r\n"
							+ "join genre on book.genre_id = genre.id\r\n"
							+ "join publisher on book.publisher_id = publisher.id\r\n"
							+ "join store_book on book.id = store_book.book_id\r\n"
							+ "join store on store_book.store_id = store.id\r\n"
							+ "join book_warehouse on book.id = book_warehouse.book_id\r\n"
							+ "join book_author on book.id = book_author.book_id\r\n"
							+ "join author on book_author.author_id = author.id\r\n" + "where store.store_name = \""
							+ storeName + "\" and (book.name LIKE \"%" + keyword + "%\" OR book.ISBN LIKE \"%" + keyword
							+ "%\" OR genre.name LIKE \"%" + keyword + "%\" OR author.name LIKE \"%" + keyword
							+ "%\")\r\n" + "group by book_warehouse.book_id;");

			if (rs.next()) {

				// reset pointer
				rs.beforeFirst();
				while (rs.next()) {
					Book temp = new Book();
					temp.setId(rs.getInt("id"));
					temp.setName(rs.getString("name"));
					temp.setPrice(rs.getDouble("price"));
					temp.setISBN(rs.getString("ISBN"));
					temp.setGenre(rs.getString("genre"));
					temp.setNop(rs.getInt("nop"));
					Publisher tempPublisher = new Publisher();
					tempPublisher.setId(rs.getInt("publisher_id"));
					tempPublisher.setName(rs.getString("publisher_name"));
					temp.setPublisher(tempPublisher);
					temp.setStock(rs.getInt("stock"));
					temp.setAuthors(getAuthorById(temp.getId()));
					resultList.add(temp);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultList;
	}

	public ArrayList<Book> getBookListNotInStore(String storeName) {
		ArrayList<Book> resultList = new ArrayList<Book>();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"#get the general info of booklist not in the store\r\n" + 
					"select book.id,book.name,book.price,book.ISBN,genre.name as genre,book.nop,book.publisher_id,publisher.name as publisher_name, sum(book_warehouse.quantity) as stock from book \r\n" + 
					"join genre on book.genre_id = genre.id\r\n" + 
					"join publisher on book.publisher_id = publisher.id\r\n" + 
					"join book_warehouse on book.id = book_warehouse.book_id\r\n" + 
					"where book.id not in (select store_book.book_id from store_book \r\n" + 
					"join store on store_book.store_id = store.id\r\n" + 
					"where store.store_name = \""+storeName+"\")\r\n" + 
					"group by book_warehouse.book_id;");

			if (rs.next()) {

				// reset pointer
				rs.beforeFirst();
				while (rs.next()) {
					Book temp = new Book();
					temp.setId(rs.getInt("id"));
					temp.setName(rs.getString("name"));
					temp.setPrice(rs.getDouble("price"));
					temp.setISBN(rs.getString("ISBN"));
					temp.setGenre(rs.getString("genre"));
					temp.setNop(rs.getInt("nop"));
					Publisher tempPublisher = new Publisher();
					tempPublisher.setId(rs.getInt("publisher_id"));
					tempPublisher.setName(rs.getString("publisher_name"));
					temp.setPublisher(tempPublisher);
					temp.setStock(rs.getInt("stock"));
					temp.setAuthors(getAuthorById(temp.getId()));
					resultList.add(temp);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultList;
	}

	public void disconnect() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateCollection(String storeName, ObservableList<Book> collectionList) {
		try {
			Statement stmt = con.createStatement();


			stmt.executeUpdate(
					"DELETE FROM store_book \r\n"
							+ "		WHERE store_id = (select id from store where store.store_name = \"" + storeName + "\")");
				for (Book current : collectionList) {

					stmt.executeUpdate(
							"INSERT INTO store_book (store_id,book_id) \r\n"
									+ "					VALUES ((select id from Store where store_name = '"+storeName+"'), "+current.getId()+");");
					

				}

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
