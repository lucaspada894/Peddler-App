package com.coms309.peddler.Models;


public class Job {
    private Integer productID;
    private Integer userID ;
    private String productName;
    private String productDescription;
    private String productCondition;
    private String productPrice;

    public Job(Integer productID, Integer userID, String productName, String productDescription, String productCondition, String productPrice) {
        this.productID = productID;
        this.userID = userID;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productCondition = productCondition;
        this.productPrice = productPrice;
    }


    public int getProductID() {
        return productID;
    }
    public int geUserID() {
        return userID;
    }
    public String getProductName() {
        return productName;
    }
    public String getProductDescription() {
        return productDescription;
    }
    public String getProductCondition() {
        return productCondition;
    }
    public String getProductPrice() {
        return productPrice;
    }
}
