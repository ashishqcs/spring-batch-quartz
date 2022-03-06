package com.midnight.springbatchquartz.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TargetProduct {

    @Id
    private int code;

    private String name;

    private String description;
}
