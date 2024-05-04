package com.enoca.eticaret.web.cart.mapper;

import com.enoca.eticaret.web.cart.entity.Cart;
import com.enoca.eticaret.web.cart.model.CartResponse;
import com.enoca.eticaret.web.cart.model.CartUpdate;
import com.enoca.eticaret.web.product.mapper.ProductMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface CartMapper {
    List<CartResponse> mapResponseToEntityList(List<Cart> carts);
    Cart mapUpdateToEntity(CartUpdate cartUpdate);
    CartResponse mapResponseToEntity(Cart cart);
}