package com.jasmineenriquez.casestudy;

import java.math.BigDecimal;

public class Book {
    private int bookId;
    private String title;
    private String author;
    private String publisher;
    private String description;
    private String size;
    private String genre;
    private int pages;
    private String languages;
    private Double price;
    private byte[] coverImage;
    private int stockCount;
    
    // Constructor
   public Book(int bookId, String title, String author, String publisher, String description, String size,
                String genre, int pages, String languages, Double price, byte[] coverImage, int stockCount) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.description = description;
        this.size = size;
        this.genre = genre;
        this.pages = pages;
        this.languages = languages;
        this.price = price;
        this.coverImage = coverImage;
        this.stockCount = stockCount;
    }
    
    // Getters and Setters
    public int getBookId() {
        return bookId;
    }
    
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public String getPublisher() {
        return publisher;
    }
    
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getSize() {
        return size;
    }
    
    public void setSize(String size) {
        this.size = size;
    }
    
    public String getGenre() {
        return genre;
    }
    
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    public int getPages() {
        return pages;
    }
    
    public void setPages(int pages) {
        this.pages = pages;
    }
    
    public String getLanguages() {
        return languages;
    }
    
    public void setLanguages(String languages) {
        this.languages = languages;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    public byte[] getCoverImage() {
        return coverImage;
    }
    
    public void setCoverImage(byte[] coverImage) {
        this.coverImage = coverImage;
    }
    
    public int getStockCount() {
        return stockCount;
    }
    
    public void setStockCount(int stockCount) {
        this.stockCount = stockCount;
    }
}

