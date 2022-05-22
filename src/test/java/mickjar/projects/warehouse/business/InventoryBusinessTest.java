package mickjar.projects.warehouse.business;

import mickjar.projects.warehouse.business.model.Article;
import mickjar.projects.warehouse.business.model.Product;
import mickjar.projects.warehouse.integration.InventoryRepositoryLocalJson;
import mickjar.projects.warehouse.integration.model.ArticleDefinitionDto;
import mickjar.projects.warehouse.integration.model.ProductDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InventoryBusinessTest {
    @Mock
    private InventoryRepositoryLocalJson repositoryMock;

    @InjectMocks
    private InventoryBusiness inventoryBusiness;

    @Test
    public void GetAndMapProduct() {
        // Arrange
        var articlesDto = List.of(new ArticleDefinitionDto(1, 666));
        var productDto = new ProductDto("testProduct", articlesDto);

        var expectedArticles = List.of(new Article(1, 666));
        var expectedProduct = new Product("testProduct", expectedArticles);

        doReturn(List.of(productDto)).when(repositoryMock).GetProducts();

        // Act
        var product = inventoryBusiness.GetProducts();

        // Assert
        verify(repositoryMock).GetProducts();

        assertAll(
                () -> assertNotNull(product),
                () -> assertEquals(List.of(expectedProduct), product, "Product return incorrect value")
        );

        var singleProduct = product.stream().findFirst();
        assertTrue(singleProduct.isPresent());
        assertEquals(expectedArticles, singleProduct.get().articles(), "Articles return incorrect value");
    }

    @ParameterizedTest
    @CsvSource(
            {
                    "4,4,true",
                    "666,1,false",
                    "1,666,true"
            })
    public void SellProduct(int desiredStock, int existingStock, boolean expectedSuccess) {
        // Arrange
        var articlesDto = List.of(new ArticleDefinitionDto(1, desiredStock));
        var productDto = new ProductDto("testProduct", articlesDto);

        doReturn(productDto).when(repositoryMock).GetProduct("testProduct");
        doReturn(existingStock).when(repositoryMock).getInventoryStock(1);

        if (expectedSuccess) {
            doReturn(true).when(repositoryMock).RemoveArticle(1, desiredStock);
        }

        // Act
        var success = inventoryBusiness.SellProduct("testProduct");

        // Assert
        verify(repositoryMock).GetProduct("testProduct");
        verify(repositoryMock).getInventoryStock(1);
        if (expectedSuccess) {
            verify(repositoryMock).RemoveArticle(1, desiredStock);
        }

        assertEquals(expectedSuccess, success, "It should have been possible to sell product that exists in inventory");

    }
}
