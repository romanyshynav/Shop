package andrii.romanyshyn.shopsoftserve._4_Service;


import andrii.romanyshyn.shopsoftserve._1_Entity.Cart;
import andrii.romanyshyn.shopsoftserve._1_Entity.Person;
import andrii.romanyshyn.shopsoftserve._1_Entity.Product;
import andrii.romanyshyn.shopsoftserve._2_Repository.CartRepository;
import andrii.romanyshyn.shopsoftserve._3_DTO.Request.CartRequest;
import andrii.romanyshyn.shopsoftserve._3_DTO.Request.PaginationRequest;
import andrii.romanyshyn.shopsoftserve._3_DTO.Response.CartResponse;
import andrii.romanyshyn.shopsoftserve._3_DTO.Response.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductService productService;


    //    =========МЕТОДИ===============================================================================================
    //    -----------------------------Початок: 1. Рандомне створення довільної к-ті поїздів----------------------------
    public void createRandomCart(Person person) {
        Cart cart = Cart.builder().name("Моя корзинка").person(person).cartProductList(randomCartProductList()).build();
        cartRepository.save(cart);
    }

    public List<Product> randomCartProductList() {
        List<Product> productList = new ArrayList<>();
        Collections.addAll(productList,
                productService.randomProduct(), productService.randomProduct(), productService.randomProduct());
        return productList;
    }
    //    -----------------------------Кінець: 1. Рандомне створення довільної к-ті поїздів-----------------------------

    //    -----------------------------Початок: 3. Посторіночна розбивка та сортування----------------------------------
    public PageResponse<CartResponse> findPageAndSort(PaginationRequest paginationRequest) {
        Page<Cart> page = cartRepository.findAll(paginationRequest.toPageble()); // "findAll" для сторінок
        return new PageResponse<>(page.getTotalPages(), page.getTotalElements(),
                page.get().map(CartResponse::new).collect(Collectors.toList()));
    }
    //    -----------------------------Кінець: 3. Посторіночна розбивка та сортування----------------------------------

    //    -----------------------------Початок: 4. Операції з "Товарами в кошику" --------------------------------------
    public void addProductToCart(Long productId, Long cartId) {
        Cart cart = findOneByID(cartId);
        Product product = productService.findOneByID(productId);
        cart.getCartProductList().add(product);
        cartRepository.save(cart);
    }

    public void removeProductFromCart(Long productId, Long cartId) {
        Cart cart = findOneByID(cartId);
        Product product = productService.findOneByID(productId);
        cart.getCartProductList().remove(product);
        cartRepository.save(cart);
    }
    //    -----------------------------Кінець: 4. Операції з "Товарами в кошику" ---------------------------------------

    //    =========CRUD-операції========================================================================================
    public void create(Person person) {
        Cart cart = Cart.builder().name("Моя корзинка").person(person).build();
        cartRepository.save(cart);
    }

    public void update(Long id, CartRequest request) {
        Cart cart = findOneByID(id);
        cart.setCartProductList(productService.convertIdListIntoProductList(request.getCartProductIdList()));
        cartRepository.save(cart);
    }

    public void delete(Long id) {
        cartRepository.delete(findOneByID(id));
    }

    public void deleteAll() {
        cartRepository.deleteAll();
    }

    public Cart findOneByID(Long id) {
        return cartRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(
                "Cart with ID - " + id + " not exist.\n"));
    }

    public List<CartResponse> findAll() {
        return cartRepository.findAll().stream()
                .map(cart -> new CartResponse(cart))
                .collect(Collectors.toList());
    }
}
