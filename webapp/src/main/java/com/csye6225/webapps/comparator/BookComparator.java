package com.csye6225.webapps.comparator;

import com.csye6225.webapps.model.Book;
import org.apache.commons.lang3.builder.CompareToBuilder;

import java.util.Comparator;

public class BookComparator implements Comparator<Book> {

    @Override
    public int compare(Book o1, Book o2) {
        return new CompareToBuilder()
                .append(o1.getTitle(), o2.getTitle())
                .append(o1.getPrice(), o2.getPrice())
                .append(o1.getQuantity(), o2.getQuantity()).toComparison();
    }
}
