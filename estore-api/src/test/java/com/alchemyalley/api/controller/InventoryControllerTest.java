package com.alchemyalley.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.alchemyalley.api.persistence.ProductDAO;
import com.alchemyalley.api.model.ElementType;
import com.alchemyalley.api.model.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
* Test the Hero Controller class
*
* @author SWEN Faculty
*/
@Tag("Controller-tier")
public class InventoryControllerTest {

   private InventoryController inventoryController;
   private ProductDAO mockProductDAO;

   /**
    * Before each test, create a new inventoryController object and inject
    * a mock Hero DAO
    */
   @BeforeEach
   public void setupinventoryController() {
       mockProductDAO = mock(ProductDAO.class);
       inventoryController = new InventoryController(mockProductDAO);
   }

//    @Test
//    public void testGetHero() throws IOException {  // getHero may throw IOException
//        // Setup
//        Product hero = new Product(99,"Galactic Agent");
//        // When the same id is passed in, our mock Hero DAO will return the Hero object
//        when(mockProductDAO.getHero(hero.getId())).thenReturn(hero);

//        // Invoke
//        ResponseEntity<Product> response = inventoryController.getHero(hero.getId());

//        // Analyze
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(hero, response.getBody());
//    }

//    @Test
//    public void testGetHeroNotFound() throws Exception { // createHero may throw IOException
//        // Setup
//        int heroId = 99;
//        // When the same id is passed in, our mock Hero DAO will return null, simulating
//        // no hero found
//        when(mockProductDAO.getHero(heroId)).thenReturn(null);

//        // Invoke
//        ResponseEntity<Product> response = inventoryController.getHero(heroId);

//        // Analyze
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//    }

//    @Test
//    public void testGetHeroHandleException() throws Exception { // createHero may throw IOException
//        // Setup
//        int heroId = 99;
//        // When getHero is called on the Mock Hero DAO, throw an IOException
//        doThrow(new IOException()).when(mockProductDAO).getHero(heroId);

//        // Invoke
//        ResponseEntity<Product> response = inventoryController.getHero(heroId);

//        // Analyze
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//    }

   /*****************************************************************
    * The following tests will fail until all inventoryController methods
    * are implemented.
    ****************************************************************/

//    @Test
//    public void testCreateHero() throws IOException {  // createHero may throw IOException
//        // Setup
//        Product hero = new Product(99,"Wi-Fire");
//        // when createHero is called, return true simulating successful
//        // creation and save
//        when(mockProductDAO.createHero(hero)).thenReturn(hero);

//        // Invoke
//        ResponseEntity<Product> response = inventoryController.createHero(hero);

//        // Analyze
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals(hero, response.getBody());
//    }

//    @Test
//    public void testCreateHeroFailed() throws IOException {  // createHero may throw IOException
//        // Setup
//        Product hero = new Product(99,"Bolt");
//        // when createHero is called, return false simulating failed
//        // creation and save
//        when(mockProductDAO.createHero(hero)).thenReturn(null);

//        // Invoke
//        ResponseEntity<Product> response = inventoryController.createHero(hero);

//        // Analyze
//        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
//    }

//    @Test
//    public void testCreateHeroHandleException() throws IOException {  // createHero may throw IOException
//        // Setup
//        Product hero = new Product(99,"Ice Gladiator");

//        // When createHero is called on the Mock Hero DAO, throw an IOException
//        doThrow(new IOException()).when(mockProductDAO).createHero(hero);

//        // Invoke
//        ResponseEntity<Product> response = inventoryController.createHero(hero);

//        // Analyze
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//    }

//    @Test
//    public void testUpdateHero() throws IOException { // updateHero may throw IOException
//        // Setup
//        Product hero = new Product(99,"Wi-Fire");
//        // when updateHero is called, return true simulating successful
//        // update and save
//        when(mockProductDAO.updateHero(hero)).thenReturn(hero);
//        ResponseEntity<Product> response = inventoryController.updateHero(hero);
//        hero.setName("Bolt");

//        // Invoke
//        response = inventoryController.updateHero(hero);

//        // Analyze
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(hero, response.getBody());
//    }

//    @Test
//    public void testUpdateHeroFailed() throws IOException { // updateHero may throw IOException
//        // Setup
//        Product hero = new Product(99,"Galactic Agent");
//        // when updateHero is called, return true simulating successful
//        // update and save
//        when(mockProductDAO.updateHero(hero)).thenReturn(null);

//        // Invoke
//        ResponseEntity<Product> response = inventoryController.updateHero(hero);

//        // Analyze
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//    }

//    @Test
//    public void testUpdateHeroHandleException() throws IOException { // updateHero may throw IOException
//        // Setup
//        Product hero = new Product(99,"Galactic Agent");
//        // When updateHero is called on the Mock Hero DAO, throw an IOException
//        doThrow(new IOException()).when(mockProductDAO).updateHero(hero);

//        // Invoke
//        ResponseEntity<Product> response = inventoryController.updateHero(hero);

//        // Analyze
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//    }

//    @Test
//    public void testGetHeroes() throws IOException { // getHeroes may throw IOException
//        // Setup
//        Product[] heroes = new Product[2];
//        heroes[0] = new Product(99,"Bolt");
//        heroes[1] = new Product(100,"The Great Iguana");
//        // When getHeroes is called return the heroes created above
//        when(mockProductDAO.getHeroes()).thenReturn(heroes);

//        // Invoke
//        ResponseEntity<Product[]> response = inventoryController.getHeroes();

//        // Analyze
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(heroes, response.getBody());
//    }

//    @Test
//    public void testGetHeroesHandleException() throws IOException { // getHeroes may throw IOException
//        // Setup
//        // When getHeroes is called on the Mock Hero DAO, throw an IOException
//        doThrow(new IOException()).when(mockProductDAO).getProducts();

//        // Invoke
//        ResponseEntity<Product[]> response = inventoryController.getHeroes();

//        // Analyze
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//    }

   @Test
   public void testSearchProducts() throws IOException {
       // Setup
       String searchString = "fire";
       Product[] products = new Product[3];
       products[0] = new Product(0, "Fire", ElementType.AIR, 1.5, 50);
       products[1] = new Product(6, "Fire Bolt", ElementType.AIR, 1.5, 50);
       products[2] = new Product(13, "Fire Rain", ElementType.AIR, 1.5, 50);
       // When findProducts is called with the search string, return the two
       // products above
       when(mockProductDAO.findProducts(searchString)).thenReturn(products);

       // Invoke
       ResponseEntity<Product[]> response = inventoryController.searchProducts(searchString);

       // Analyze
       assertEquals(HttpStatus.OK, response.getStatusCode());
       assertEquals(products, response.getBody());
   }

   @Test
   public void testSearchProductsHandleException() throws IOException {
       // Setup
       String searchString = "an";
       // When findProducts is called on the Mock Product DAO, throw an IOException
       doThrow(new IOException()).when(mockProductDAO).findProducts(searchString);

       // Invoke
       ResponseEntity<Product[]> response = inventoryController.searchProducts(searchString);

       // Analyze
       assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
   }

//    @Test
//    public void testDeleteHero() throws IOException { // deleteHero may throw IOException
//        // Setup
//        int heroId = 99;
//        // when deleteHero is called return true, simulating successful deletion
//        when(mockProductDAO.deleteHero(heroId)).thenReturn(true);

//        // Invoke
//        ResponseEntity<Product> response = inventoryController.deleteHero(heroId);

//        // Analyze
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }

//    @Test
//    public void testDeleteHeroNotFound() throws IOException { // deleteHero may throw IOException
//        // Setup
//        int heroId = 99;
//        // when deleteHero is called return false, simulating failed deletion
//        when(mockProductDAO.deleteHero(heroId)).thenReturn(false);

//        // Invoke
//        ResponseEntity<Product> response = inventoryController.deleteHero(heroId);

//        // Analyze
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//    }

//    @Test
//    public void testDeleteHeroHandleException() throws IOException { // deleteHero may throw IOException
//        // Setup
//        int heroId = 99;
//        // When deleteHero is called on the Mock Hero DAO, throw an IOException
//        doThrow(new IOException()).when(mockProductDAO).deleteHero(heroId);

//        // Invoke
//        ResponseEntity<Product> response = inventoryController.deleteHero(heroId);

//        // Analyze
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//    }
}
