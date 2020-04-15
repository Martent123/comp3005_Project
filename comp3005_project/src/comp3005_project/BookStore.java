package comp3005_project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javafx.application.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

public class BookStore extends Application {
	private BorderPane bPane;
	private User currentUser;
	private String storeName = "Store One";
	private TableView<Book> bookTable;
	public static void main(String[] args) {
		// start the application
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// main border pane
		bPane = new BorderPane();

		// load the login page
		loadLoginPage();

		Scene scene = new Scene(bPane, 900, 600);

		primaryStage.setTitle("COMP3005-Project-100881456");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@SuppressWarnings("unchecked")
	public void loadLoginPage() {

		// load the default screen (login)
		VBox loginVbox = new VBox();
		loginVbox.setAlignment(Pos.CENTER);
		loginVbox.setSpacing(5);
		Label loginLabel = new Label("Please Login (default 'testuser' and 'testowner')");
		Button loginButton = new Button("Login");
		Button registerButton = new Button("Register");
		TextField name = new TextField();
		name.setMaxWidth(300);
		// retrieving the observable list of the VBox
		ObservableList<Node> list = loginVbox.getChildren();
		// Adding all the nodes to the observable list
		list.addAll(loginLabel, name, loginButton, registerButton);

		loginButton.setOnAction(new EventHandler() {

			@Override
			public void handle(Event event) {
				if (name.getText().isEmpty()) {
					// send error message
					bPane.setBottom(generateErrorHB("Type Something"));
				} else {
					User tempUser = new User(name.getText());
					DBConnect db = new DBConnect();
					User validatedUser = db.validate(tempUser);
					String role = validatedUser.getRole();

					if (role != null && role.equals("user")) {
						// it a regular user
						currentUser = validatedUser;
						System.out.println("Login as user");
						bPane.setTop(generateNamePlate(currentUser));
						// reset the error message
							bPane.setBottom(null);
						loadBookListPage();
					} else if (role != null && role.equals("owner")) {
						// it owner
						currentUser = validatedUser;
						System.out.println("Login as owner");
						bPane.setTop(generateNamePlate(currentUser));
						loadOwnerMainPage();
						// reset the error message
						bPane.setBottom(null);
					} else {
						// login failed
						// send error message
						System.out.println("Login Failed");
						bPane.setBottom(generateErrorHB("Login failed, check the name"));
					}
					db.disconnect();
				}
			}
		});

		// load the stage
		bPane.setCenter(loginVbox);

	}

	public VBox generateNamePlate(User user) {
		VBox vb = new VBox();
		Label nameLabel = new Label("Name: " + user.getName());
		Button detail = new Button("Details");
		Label roleLabel = new Label("Role: " + user.getRole());

		ObservableList<Node> list = vb.getChildren();
		list.addAll(nameLabel, roleLabel, detail);
		vb.setSpacing(5);
		vb.setAlignment(Pos.CENTER_LEFT);
		detail.setOnAction(new EventHandler() {

			@Override
			public void handle(Event event) {
				bPane.setCenter(generateDetailedUserPage(user));
				bPane.setBottom(null);
			}

		});
		return vb;
	}

	public VBox generateDetailedUserPage(User user) {

		VBox resultBox = new VBox();
		resultBox.setSpacing(5);
		HBox nameBox = new HBox();
		nameBox.setAlignment(Pos.CENTER);
		Label nameLabel = new Label("Name: " + user.getName());
		nameBox.getChildren().addAll(nameLabel);

		HBox emailBox = new HBox();
		emailBox.setAlignment(Pos.CENTER);
		Label emailLabel = new Label("Email: ");
		TextField emailField = new TextField();
		String currentEmail = user.getEmail();
		if (currentEmail != null && !currentEmail.isEmpty()) {
			emailField.setText(currentEmail);
		}
		emailBox.getChildren().addAll(emailLabel, emailField);

		HBox phoneBox = new HBox();
		phoneBox.setAlignment(Pos.CENTER);
		Label phoneLabel = new Label("Phone: ");
		TextField phoneField = new TextField();
		String currentPhone = user.getPhone();
		if (currentPhone != null && !currentPhone.isEmpty()) {
			phoneField.setText(currentPhone);
		}
		phoneBox.getChildren().addAll(phoneLabel, phoneField);

		DBConnect db = new DBConnect();
		// update user address info
		currentUser = db.getAddress(currentUser);
		ArrayList<Address> addressList = currentUser.getAddress();
		db.disconnect();
		// billing address
		Label billingAddressLabel = new Label("Billing Address");
		HBox billingAddressNumberBox = new HBox();
		billingAddressNumberBox.setAlignment(Pos.CENTER);
		Label billingNumLabel = new Label("Street Number: ");
		TextField billingNumField = new TextField();
		billingAddressNumberBox.getChildren().addAll(billingNumLabel, billingNumField);

		HBox billingAddressNameBox = new HBox();
		billingAddressNameBox.setAlignment(Pos.CENTER);
		Label billingNameLabel = new Label("Street Name: ");
		TextField billingNameField = new TextField();
		billingAddressNameBox.getChildren().addAll(billingNameLabel, billingNameField);

		HBox billingAddressPostalBox = new HBox();
		billingAddressPostalBox.setAlignment(Pos.CENTER);
		Label billingPostalLabel = new Label("Postal Code: ");
		TextField billingPostalField = new TextField();
		billingAddressPostalBox.getChildren().addAll(billingPostalLabel, billingPostalField);

		// shipping address
		Label shippingAddressLabel = new Label("Shipping Address");
		HBox shippingAddressNumberBox = new HBox();
		shippingAddressNumberBox.setAlignment(Pos.CENTER);
		Label shippingNumLabel = new Label("Street Number: ");
		TextField shippingNumField = new TextField();
		shippingAddressNumberBox.getChildren().addAll(shippingNumLabel, shippingNumField);

		HBox shippingAddressNameBox = new HBox();
		shippingAddressNameBox.setAlignment(Pos.CENTER);
		Label shippingNameLabel = new Label("Street Name: ");
		TextField shippingNameField = new TextField();
		shippingAddressNameBox.getChildren().addAll(shippingNameLabel, shippingNameField);

		HBox shippingAddressPostalBox = new HBox();
		shippingAddressPostalBox.setAlignment(Pos.CENTER);
		Label shippingPostalLabel = new Label("Postal Code: ");
		TextField shippingPostalField = new TextField();
		shippingAddressPostalBox.getChildren().addAll(shippingPostalLabel, shippingPostalField);

		HBox buttonBox = new HBox();
		buttonBox.setAlignment(Pos.CENTER);
		Button saveB = new Button("SAVE");
		Button logoutB = new Button("LOGOUT");
		Button cancelB = new Button("CANCEL");
		buttonBox.setSpacing(10);
		buttonBox.getChildren().addAll(saveB, logoutB, cancelB);

		saveB.setOnAction(new EventHandler() {
			@Override
			public void handle(Event event) {
				// connect with database
				DBConnect db = new DBConnect();

				// update the address
				String newEmail = emailField.getText();
				String newPhone = phoneField.getText();

				// validate fields
				if (newEmail != null && !newEmail.isEmpty()) {
					currentUser.setEmail(newEmail);
				} else {
					// remove it
					currentUser.setEmail("");
				}

				if (newPhone != null && !newPhone.isEmpty()) {
					currentUser.setPhone(newPhone);
				} else {
					// remove it
					currentUser.setPhone("");
				}
				db.updateUserInfo(currentUser);
				db.disconnect();
				if (currentUser.getRole().equals("user"))
				loadBookListPage();
				// Billing Address

				// String newEmail = emailField.getText();
				// String newEmail = emailField.getText();
				// String newEmail = emailField.getText();
				// String newEmail = emailField.getText();
				// String newEmail = emailField.getText();
				// String newEmail = emailField.getText();
				//
				// if () {
				//
				// }
			}

		});

		logoutB.setOnAction(new EventHandler() {
			@Override
			public void handle(Event event) {
				loadLoginPage();
				bPane.setTop(null);
				currentUser = null;
			}

		});

		cancelB.setOnAction(new EventHandler() {

			@Override
			public void handle(Event event) {
				if (currentUser.getRole().equals("user"))
				loadBookListPage();
			}

		});

		// update address info
		for (Address currentAddress : addressList) {
			if (currentAddress.getType() != null && currentAddress.getType().equals("billing")) {
				// its billing address
				billingNumField.setText(currentAddress.getNumber());
				billingNameField.setText(currentAddress.getName());
				billingPostalField.setText(currentAddress.getCode());
			} else if (currentAddress.getType() != null && currentAddress.getType().equals("shipping")) {
				// its shipping address
				shippingNumField.setText(currentAddress.getNumber());
				shippingNameField.setText(currentAddress.getName());
				shippingPostalField.setText(currentAddress.getCode());
			}
		}
		
		
		resultBox.setAlignment(Pos.CENTER);
		resultBox.getChildren().addAll(nameBox, emailBox, phoneBox, billingAddressLabel, billingAddressNumberBox,
				billingAddressNameBox, billingAddressPostalBox, shippingAddressLabel, shippingAddressNumberBox,
				shippingAddressNameBox, shippingAddressPostalBox, buttonBox);
		
		if(currentUser.getRole().equals("user")) {
			VBox orderVBox = new VBox();
			ListView<Order> orderList = new ListView<Order>();
			db = new DBConnect();

			ObservableList<Order> OrderList = FXCollections.observableList(db.getOrderList(currentUser.getId()));
			orderList.setItems(OrderList);
			db.disconnect();
			Label orderLabel = new Label("Your Order");
			orderVBox.getChildren().addAll(orderLabel,orderList);
			resultBox.getChildren().add(orderVBox);
		}
		
		return resultBox;

	}

	public HBox generateErrorHB(String text) {
		HBox hb = new HBox();
		hb.setAlignment(Pos.CENTER);

		// retrieving the observable list of the VBox
		ObservableList<Node> list = hb.getChildren();
		// Adding all the nodes to the observable list
		Label label = new Label(text);
		label.setAlignment(Pos.CENTER);
		label.autosize();
		label.setStyle("-fx-font-weight: bold;");
		label.setTextFill(Color.web("red"));
		list.addAll(label);
		HBox.setMargin(label, new Insets(30, 30, 30, 30));
		return hb;
	}

	public void loadRegisterPage() {
		VBox vb = new VBox();
		vb.setAlignment(Pos.CENTER_LEFT);

		Label name = new Label("name");
	}

	public void loadBookListPage() {
		loadSearchBar();

		// connect with database
		DBConnect db = new DBConnect();
		ArrayList<Book> bookList = db.getBookList(storeName);
		ObservableList<Book> observableList = FXCollections.observableList(bookList);
		bookTable = new TableView<Book>();
		bookTable.setEditable(false);
		
		TableColumn bookNameCol = new TableColumn("Book");
		bookNameCol.setCellValueFactory(new PropertyValueFactory<Book, String>("name"));

		TableColumn priceCol = new TableColumn("Price");
		priceCol.setCellValueFactory(new PropertyValueFactory<Book, Double>("price"));

		TableColumn ISBNCol = new TableColumn("ISBN");
		ISBNCol.setCellValueFactory(new PropertyValueFactory<Book, String>("ISBN"));

		TableColumn genreCol = new TableColumn("Genre");
		genreCol.setCellValueFactory(new PropertyValueFactory<Book, String>("genre"));

		TableColumn pageCol = new TableColumn("Pages");
		pageCol.setCellValueFactory(new PropertyValueFactory<Book, Integer>("nop"));

		TableColumn authCol = new TableColumn("Authors");
		authCol.setCellValueFactory(new Callback<CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> c) {
				return new SimpleStringProperty(c.getValue().getAuthors().toString());
			}
		});
		
		TableColumn pubCol = new TableColumn("Publisher");
		pubCol.setCellValueFactory(new Callback<CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> c) {
				return new SimpleStringProperty(c.getValue().getPublisher().getName());
			}
		});
		

		TableColumn stockCol = new TableColumn("Stock");
		stockCol.setCellValueFactory(new PropertyValueFactory<Book, Integer>("stock"));
		bookTable.setItems(observableList);
		bookTable.getColumns().addAll(bookNameCol, priceCol, ISBNCol, genreCol, pageCol, pubCol,authCol, stockCol);
		
		
		ListView<Book> cartLV = new ListView<Book>();
		
		ObservableList<Book> cartList = FXCollections.observableList(new ArrayList<Book> ());
		cartLV.setItems(cartList);

		Button cartB = new Button ("Add to Cart");
		Button detailB = new Button ("Detail");
		
		cartB.setOnAction(new EventHandler() {
			@Override
			public void handle(Event event) {
				
				Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
				if(selectedBook != null) {
				cartList.add(selectedBook);
				cartLV.getSelectionModel().selectFirst();
				}
			}
		});
		
		detailB.setOnAction(new EventHandler() {
			@Override
			public void handle(Event event) {
				loadBookDetailPage(bookTable.getSelectionModel().getSelectedItem().getId());
				bPane.setBottom(null);
			}

		});
		
		HBox tableHB = new HBox();
		bookTable.setMaxWidth(700);
		tableHB.setSpacing(5);
		Label cartLabel = new Label("Cart");
		
		Button removeFromCartB = new Button("Remove");
		Button payB = new Button("Make Order");
		VBox cartVB = new VBox(cartLabel,cartLV,removeFromCartB,payB);
		
		removeFromCartB.setOnAction(new EventHandler() {
			@Override
			public void handle(Event event) {
				int selectedIndex = cartLV.getSelectionModel().getSelectedIndex();
				if (selectedIndex >= 0)
				cartList.remove(selectedIndex);
			}

		});
		
		payB.setOnAction(new EventHandler() {
			@Override
			public void handle(Event event) {
				if (!cartList.isEmpty())
				loadOrderPage(cartList);
				bPane.setBottom(null);
			}

		});
		
		cartVB.setSpacing(5);
		tableHB.getChildren().addAll(bookTable,cartVB);
		
		HBox buttonHB = new HBox();
		buttonHB.setSpacing(5);
		buttonHB.getChildren().addAll(cartB,detailB);
		
		VBox vb = new VBox();
		vb.setSpacing(5);
		vb.getChildren().addAll(tableHB,buttonHB);
		bPane.setCenter(vb);
		
		bookTable.getSelectionModel().selectFirst();

	}
	
	public void loadOrderPage(ObservableList<Book> cartList) {
		Map<Book,Integer> result = new HashMap<>();
		Button confirmB = new Button("Confirm");
		Button cancelB = new Button("Cancel");
		
		for(Book currentBook:new HashSet<>(cartList)) {
			result.put(currentBook, Collections.frequency(cartList, currentBook));
		}
		ArrayList<String> order = new ArrayList<String>();
		for (Map.Entry<Book, Integer> entry : result.entrySet()) {
			Book currentBook = entry.getKey();
			if (entry.getValue() > currentBook.getStock()) {
				//out of stock
				order.add(entry.getKey().getName() + " X " + entry.getValue()+ " *OUT OF STOCK*");
				confirmB.setDisable(true);
			}else
			order.add(entry.getKey().getName() + " X " + entry.getValue());
		}
		ObservableList orderOBList = FXCollections.observableList(order);
		ListView orderLV = new ListView();
		orderLV.setItems(orderOBList);

		
		cancelB.setOnAction(new EventHandler() {
			@Override
			public void handle(Event event) {
				loadBookListPage();
			}

		});
		
		confirmB.setOnAction(new EventHandler() {
			@Override
			public void handle(Event event) {
				DBConnect db = new DBConnect();
				db.makeOrder(currentUser,result);
				loadBookListPage();
			}

		});
		
		Label orderLabel = new Label ("You have ordered : ");
		VBox orderVB = new VBox();
		orderVB.setSpacing(10);
		orderVB.getChildren().addAll(orderLabel,orderLV,confirmB,cancelB);
		bPane.setCenter(orderVB);
	}
	
	public void loadSearchBar() {
		HBox searchHB = new HBox();
		TextField keywordField = new TextField();
		Button searchButton = new Button("Search");
		Button resetButton = new Button("Clear");
//		bookTable
		searchHB.getChildren().addAll(keywordField,searchButton,resetButton);
		searchHB.setAlignment(Pos.BASELINE_RIGHT);
		bPane.setBottom(searchHB);
		
		searchButton.setOnAction(new EventHandler() {
			@Override
			public void handle(Event event) {
				String keyword = keywordField.getText();
				if(keyword != null && !keyword.isEmpty()) {
					DBConnect db = new DBConnect();
					ObservableList bookOBList = FXCollections.observableList( db.searchBookList(storeName, keyword));
					bookTable.setItems(bookOBList);
				}
			}

		});
		
		resetButton.setOnAction(new EventHandler() {
			@Override
			public void handle(Event event) {
				loadBookListPage();
			}

		});
		
	}
	
	public void loadBookDetailPage(int bookID) {
		DBConnect db = new DBConnect();
		Book currentBook = db.getBookListById(storeName,bookID);
		
		HBox firstHB = new HBox();
		firstHB.setSpacing(10);
		firstHB.setAlignment(Pos.CENTER);
		firstHB.getChildren().addAll(new Label (currentBook.getName()), new Label(" $ "+currentBook.getPrice()),new Label(" Inventory "+currentBook.getStock()));
		
		HBox secondHB = new HBox();
		secondHB.setSpacing(10);
		secondHB.setAlignment(Pos.CENTER);
		secondHB.getChildren().addAll(new Label ("ISBN: "+currentBook.getISBN()), new Label("Genre: " + currentBook.getGenre()));
	
		
		ArrayList<Author> authors = db.getAuthorById(currentBook.getId());
		String authorString = "Authors: ";
		for(Author current:authors) {
			authorString = authorString.concat(current.getName() + " ");
		}
		
		HBox thirdHB = new HBox();
		thirdHB.setSpacing(10);
		thirdHB.setAlignment(Pos.CENTER);
		thirdHB.getChildren().addAll(new Label (currentBook.getNop()+" pages"), new Label(authorString), new Label("Publisher: "+currentBook.getPublisher().getName()));
		
		authorString = authorString.concat(".");
		
		Button okB = new Button("OK");
		okB.setAlignment(Pos.CENTER);
		okB.setOnAction(new EventHandler() {
			@Override
			public void handle(Event event) {
				loadBookListPage();
				db.disconnect();
			}

		});
		
		
		ObservableList<Book> bookGenreList = FXCollections.observableList(db.getBookListByGenre(storeName,currentBook.getGenre()));
		ListView<Book> genreLV = new ListView<Book>();
		genreLV.setItems(bookGenreList);
		genreLV.setMaxSize(400, 200);
		VBox bookDetailVB = new VBox();
		bookDetailVB.getChildren().addAll(firstHB,secondHB,thirdHB,okB,new Label("Books with same genre"),genreLV);
		bookDetailVB.setAlignment(Pos.CENTER);
		bPane.setCenter(bookDetailVB);
		
	}
	
	public void loadOwnerMainPage() {
		// connect with database
				DBConnect db = new DBConnect();
				ArrayList<Book> bookList = db.getBookListNotInStore(storeName);
				ObservableList<Book> observableList = FXCollections.observableList(bookList);
				bookTable = new TableView<Book>();
				bookTable.setEditable(false);
				
				TableColumn bookNameCol = new TableColumn("Book");
				bookNameCol.setCellValueFactory(new PropertyValueFactory<Book, String>("name"));

				TableColumn priceCol = new TableColumn("Price");
				priceCol.setCellValueFactory(new PropertyValueFactory<Book, Double>("price"));

				TableColumn ISBNCol = new TableColumn("ISBN");
				ISBNCol.setCellValueFactory(new PropertyValueFactory<Book, String>("ISBN"));

				TableColumn genreCol = new TableColumn("Genre");
				genreCol.setCellValueFactory(new PropertyValueFactory<Book, String>("genre"));

				TableColumn pageCol = new TableColumn("Pages");
				pageCol.setCellValueFactory(new PropertyValueFactory<Book, Integer>("nop"));

				TableColumn authCol = new TableColumn("Authors");
				authCol.setCellValueFactory(new Callback<CellDataFeatures<Book, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Book, String> c) {
						return new SimpleStringProperty(c.getValue().getAuthors().toString());
					}
				});
				
				TableColumn pubCol = new TableColumn("Publisher");
				pubCol.setCellValueFactory(new Callback<CellDataFeatures<Book, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Book, String> c) {
						return new SimpleStringProperty(c.getValue().getPublisher().getName());
					}
				});
				

				TableColumn stockCol = new TableColumn("Stock");
				stockCol.setCellValueFactory(new PropertyValueFactory<Book, Integer>("stock"));
				bookTable.setItems(observableList);
				bookTable.getColumns().addAll(bookNameCol, priceCol, ISBNCol, genreCol, pageCol, pubCol,authCol, stockCol);
				
				
				ListView<Book> collectionLV = new ListView<Book>();
				
				ObservableList<Book> collectionList = FXCollections.observableList(db.getBookList(storeName));
				collectionLV.setItems(collectionList);

				Button collectionB = new Button ("Add to Collection");
				
				collectionB.setOnAction(new EventHandler() {
					@Override
					public void handle(Event event) {
						
						Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
						if(selectedBook != null) {
							collectionList.add(selectedBook);
							observableList.remove(selectedBook);

						collectionLV.getSelectionModel().selectFirst();
						}
					}
				});
				
				
				HBox tableHB = new HBox();
				bookTable.setMaxWidth(700);
				tableHB.setSpacing(5);
				Label cartLabel = new Label("Collection");
				
				Button removeFromCartB = new Button("Remove");
				Button confirmB = new Button("Confirm");
				VBox cartVB = new VBox(cartLabel,collectionLV,removeFromCartB,confirmB);
				
				removeFromCartB.setOnAction(new EventHandler() {
					@Override
					public void handle(Event event) {
						int selectedIndex = collectionLV.getSelectionModel().getSelectedIndex();
						if (selectedIndex >= 0) {
							observableList.add(collectionList.get(selectedIndex));
							collectionList.remove(selectedIndex);
						}
					}

				});
				
				confirmB.setOnAction(new EventHandler() {
					@Override
					public void handle(Event event) {
						if (!collectionList.isEmpty()) {
							db.updateCollection(storeName,collectionList);
							db.disconnect();
							loadOwnerMainPage();
							
							bPane.setBottom(null);

						}
					}

				});
				
				cartVB.setSpacing(5);
				tableHB.getChildren().addAll(bookTable,cartVB);
				
				HBox buttonHB = new HBox();
				buttonHB.setSpacing(5);
				buttonHB.getChildren().addAll(collectionB);
				
				VBox vb = new VBox();
				vb.setSpacing(5);
				vb.getChildren().addAll(tableHB,buttonHB);
				bPane.setCenter(vb);
				
				bookTable.getSelectionModel().selectFirst();
	}

}
