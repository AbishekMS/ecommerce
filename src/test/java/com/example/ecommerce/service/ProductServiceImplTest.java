package com.example.ecommerce.service;

import com.example.ecommerce.enums.ProductCategory;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.serviceimplementation.ProductServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private static Product testProduct;

    @BeforeAll
    static void setUp(){
        System.out.println("Before All");
        testProduct =Product.builder()
                .id(1L)
                .name("Apple")
                .description("Fruits")
                .price(20.0)
                .category(ProductCategory.FOOD)
                .build();

    }
    @Test
    void getProductShouldReturnAllProducts(){
        //two products
        List<Product> expectedProducts= Arrays.asList(testProduct, Product.builder()
                .id(2L)
                .name("Never Mind")
                .description("Self Helping")
                .price(29.99)
                .category(ProductCategory.BOOKS)
                .build()
        );

        when(productRepository.findAll()).thenReturn(expectedProducts);
        List<Product>actualProducts= productService.getProducts();
        assertEquals(actualProducts,expectedProducts);
        assertEquals(expectedProducts.get(0).getCategory(), actualProducts.get(0).getCategory());
        assertEquals(1,expectedProducts.get(0).getId());
        verify(productRepository, times(1)).findAll();

    }

    @Test
    void getProductByIdShouldReturnProductIfExists(){
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(testProduct));
        Optional<Product>result=productService.getProductByID(1L);
        assertTrue(result.isPresent());
        assertEquals(testProduct,result.get());
        verify(productRepository,times(1)).findById(1L);


    }

    @Test
    void getProductsByCategoryShouldReturnProductIfExists(){
        List<Product> expectedProduct= Arrays.asList(testProduct);
        when(productRepository.findByCategory(anyString())).thenReturn(expectedProduct);

        List<Product> results= productRepository.findByCategory("ELECTRONICS");

        assertEquals(results,expectedProduct);
        assertFalse(results.isEmpty());
        verify(productRepository, times(1)).findByCategory("ELECTRONICS");
    }

    @Test
    void deleteProductShouldCallRepositoryDeleteById(){
        doNothing().when(productRepository).deleteById(1L);
        productService.deleteProduct(1L);
        verify(productRepository,times(1)).deleteById(1L);

    }

    @Test
    void saveProductShouldSaveProduct(){
        Product productToSave=Product.builder()
                .name("Hoodie")
                .description("Dress")
                .price(800.0)
                .category(ProductCategory.CLOTHES)
                .build();

        Product savedProduct= Product.builder()
                .id(3L)
                .name("Shirt")
                .description("Dress")
                .price(500.0)
                .category(ProductCategory.CLOTHES)
                .build();
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        Product result=productService.saveProduct(productToSave);

        assertNotNull(result);
        assertEquals(savedProduct.getId(), result.getId());
        assertEquals(savedProduct.getName(), result.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void saveProductShouldThrowExceptionWhenCategoryIsInvalid() {
        Product productWithInvalidCategory = Product.builder()
                .name("Speaker")
                .description("Description")
                .price(500.0)
                .category(null)
                .build();

        //when(productRepository.save(any(Product.class))).thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class, () -> {
            productService.saveProduct(productWithInvalidCategory);
        });

       // verify(productRepository, times(1)).save(any(Product.class));
    }


}
