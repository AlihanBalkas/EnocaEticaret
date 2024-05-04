package com.enoca.eticaret.web.common;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
@Data
public abstract class BaseEntity {
    @Id
    private String id;
    @Field
    private Date createdDate = new Date();
    @Field
    private Date deletedDate;
}
