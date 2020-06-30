package andrii.romanyshyn.shopsoftserve._5_Controller;


import andrii.romanyshyn.shopsoftserve._3_DTO.Request.CartRequest;
import andrii.romanyshyn.shopsoftserve._3_DTO.Request.PaginationRequest;
import andrii.romanyshyn.shopsoftserve._3_DTO.Response.CartResponse;
import andrii.romanyshyn.shopsoftserve._3_DTO.Response.PageResponse;
import andrii.romanyshyn.shopsoftserve._4_Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    //    =========МЕТОДИ===============================================================================================

    //    -----------------------------Початок: 3. Посторіночна розбивка та сортування----------------------------------
    @GetMapping("/findPageAndSort")
    public PageResponse<CartResponse> findPageAndSort(@Valid PaginationRequest paginationRequest) {
        return cartService.findPageAndSort(paginationRequest);
    }
    //    -----------------------------Кінець: 3. Посторіночна розбивка та сортування----------------------------------

    //    -----------------------------Початок: 4. Операції з "Товарами в кошику" -------------------------------------
    @PostMapping("/addProductToCart")
    public void addProductToCart(Long productId, Long cartId) {
        cartService.addProductToCart(productId, cartId);
    }
    @PostMapping("/removeProductFromCart")
    public void removeProductFromCart(Long productId, Long cartId) {
        cartService.removeProductFromCart(productId, cartId);
    }
    //    -----------------------------Кінець: 4. Операції з "Товарами в кошику" --------------------------------------

    //    =========CRUD-операції========================================================================================

    @PutMapping
    public void update(Long id, @RequestBody CartRequest request) {
        cartService.update(id, request);
    }

    @DeleteMapping
    public void delete(Long id) {
        cartService.delete(id);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAll() {
        cartService.deleteAll();
    }

    @GetMapping
    public List<CartResponse> findAll() {
        return cartService.findAll();
    }

    @GetMapping("/findOneById")
    public CartResponse findOneByID(Long id) {
        return new CartResponse(cartService.findOneByID(id));
    }
}
