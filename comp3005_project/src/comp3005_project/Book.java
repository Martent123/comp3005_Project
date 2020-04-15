package comp3005_project;

import java.util.ArrayList;

public class Book {
	
	private int id;
	private String name;
	private double price;
	private String ISBN;
	private String genre;
	private int nop;
	private Publisher publisher;
	private ArrayList<Author> authors;
	private int stock;
	private ArrayList<Warehouse> warehouses;
	public Book() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getNop() {
		return nop;
	}

	public void setNop(int nop) {
		this.nop = nop;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public ArrayList<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(ArrayList<Author> authors) {
		this.authors = authors;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public ArrayList<Warehouse> getWarehouses() {
		return warehouses;
	}

	public void setWarehouses(ArrayList<Warehouse> warehouses) {
		this.warehouses = warehouses;
	}
	
	public String toString() {
		String resultString = id+" "+name +" $"+price + " " +genre+ " " + nop +" pages " + "Published by "+publisher.toStirng()+ " Authors: ";
		for(Author current: authors) {
			resultString= resultString.concat(current.getName() + " ");
		}
		resultString =resultString.concat(". Stock: "+ stock);
		return resultString;
	}
}
