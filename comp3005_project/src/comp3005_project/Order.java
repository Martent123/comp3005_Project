package comp3005_project;

import java.util.Map;

public class Order {
	private int id;
	private String tracking;
	private Map<Book,Integer> books;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTracking() {
		return tracking;
	}
	public void setTracking(String tracking) {
		this.tracking = tracking;
	}
	public Map<Book,Integer> getBooks() {
		return books;
	}
	public void setBooks(Map<Book,Integer> books) {
		this.books = books;
	}
	
	public String toString() {
		return "ID: "+ id + " Tracking: "+ tracking;
	}
	
}
