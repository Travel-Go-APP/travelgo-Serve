package com.travelgo.backend.form;

import com.travelgo.backend.domain.Grade;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@NotEmpty
public class ItemForm {
    private Grade grade;

    private String itemName;

    private String itemDescription;
}
