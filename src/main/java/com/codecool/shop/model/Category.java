package com.codecool.shop.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Category {
    CLOTHES,
    DRINK,
    FOOD,
    ELECTRONICS,
    FURNITURE,
    BOOK
}
