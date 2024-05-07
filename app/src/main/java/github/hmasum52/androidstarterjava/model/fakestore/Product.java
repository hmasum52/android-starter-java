package github.hmasum52.androidstarterjava.model.fakestore;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class Product {
    private int id;
    private String title;
    private double price;
    private String description;
    private String category;
    private String image;
}
