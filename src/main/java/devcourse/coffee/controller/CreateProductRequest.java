package devcourse.coffee.controller;

import devcourse.coffee.model.Category;

public record CreateProductRequest(String productName, Category category, long price, String description) {

}
