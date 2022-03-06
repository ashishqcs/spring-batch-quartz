package com.midnight.springbatchquartz.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class SourceProduct {

    @Id
    private int code;

    private String name;

    private String price;

    private String description;
}
