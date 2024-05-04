package com.enoca.eticaret.web.order.entity;

import com.enoca.eticaret.web.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "t_order", language = "tr")
@Data
@EqualsAndHashCode(callSuper = true)
public class Order extends BaseEntity {
}