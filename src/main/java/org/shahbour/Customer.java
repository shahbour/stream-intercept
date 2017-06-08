package org.shahbour;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

/**
 * Created by shahbour on 6/8/17.
 */
@Getter
@Setter
public class Customer {

    private int id;
    private String name;
    @CreatedBy
    private String createdBy = "first";
    @LastModifiedBy
    private String lastModifiedBy;

    public Customer(int id,String name) {
        this.id = id;
        this.name = name;
    }

}
