//package com.alchemyalley.persistence;
//
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//import java.io.File;
//import java.io.IOException;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.alchemyalley.model.Product;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Tag;
//import org.junit.jupiter.api.Test;
//
///**
// * Test the Hero File DAO class
// *
// * @author SWEN Faculty
// */
//@Tag("Persistence-tier")
//public class HeroFileDAOTest {
//
//    ProductFileDAO heroFileDAO;
//    Product[] testHeroes;
//    ObjectMapper mockObjectMapper;
//
//    /**
//     * Before each test, we will create and inject a Mock Object Mapper to
//     * isolate the tests from the underlying file
//     * @throws IOException
//     */
//    @BeforeEach
//    public void setupHeroFileDAO() throws IOException {
//        mockObjectMapper = mock(ObjectMapper.class);
//        testHeroes = new Product[3];
//        testHeroes[0] = new Product(99,"Wi-Fire");
//        testHeroes[1] = new Product(100,"Galactic Agent");
//        testHeroes[2] = new Product(101,"Ice Gladiator");
//
//        // When the object mapper is supposed to read from the file
//        // the mock object mapper will return the hero array above
//        when(mockObjectMapper
//                .readValue(new File("doesnt_matter.txt"), Product[].class))
//                .thenReturn(testHeroes);
//        heroFileDAO = new ProductFileDAO("doesnt_matter.txt", mockObjectMapper);
//    }
//
//    @Test
//    public void testGetHeroes() {
//        // Invoke
//        Product[] heroes = heroFileDAO.getHeroes();
//
//        // Analyze
//        assertEquals(heroes.length, testHeroes.length);
//        for(int i = 0; i < testHeroes.length;++i)
//            assertEquals(heroes[i], testHeroes[i]);
//    }
//
//    @Test
//    public void testFindHeroes() {
//        // Invoke
//        Product[] heroes = heroFileDAO.findHeroes("la");
//
//        // Analyze
//        assertEquals(heroes.length, 2);
//        assertEquals(heroes[0], testHeroes[1]);
//        assertEquals(heroes[1], testHeroes[2]);
//    }
//
//    @Test
//    public void testGetHero() {
//        // Invoke
//        Product hero = heroFileDAO.getHero(99);
//
//        // Analyze
//        assertEquals(hero, testHeroes[0]);
//    }
//
//    @Test
//    public void testDeleteHero() {
//        // Invoke
//        boolean result = Assertions.assertDoesNotThrow(() -> heroFileDAO.deleteHero(99), "Unexpected exception thrown");
//
//        // Analyze
//	    assertTrue(result);
//
//        // We check the internal tree map size against the length
//        // of the test heroes array - 1 (because of the delete)
//        // Because heroes attribute of HeroFileDAO is package private
//        // we can access it directly
//        assertEquals(heroFileDAO.heroes.size(), testHeroes.length - 1);
//    }
//
//    @Test
//    public void testCreateHero() {
//        // Setup
//        Product hero = new Product(102,"Wonder-Person");
//
//        // Invoke
//        Product result = Assertions.assertDoesNotThrow(() -> heroFileDAO.createHero(hero), "Unexpected exception thrown");
//
//        // Analyze
//        assertNotNull(result);
//        Product actual = heroFileDAO.getHero(hero.getId());
//        assertEquals(actual.getId(), hero.getId());
//        assertEquals(actual.getName(), hero.getName());
//    }
//
//    @Test
//    public void testUpdateHero() {
//        // Setup
//        Product hero = new Product(99,"Galactic Agent");
//
//        // Invoke
//        Product result = Assertions.assertDoesNotThrow(() -> heroFileDAO.updateHero(hero), "Unexpected exception thrown");
//
//        // Analyze
//        assertNotNull(result);
//        Product actual = heroFileDAO.getHero(hero.getId());
//        assertEquals(actual, hero);
//    }
//
//    @Test
//    public void testSaveException() throws IOException{
//        doThrow(new IOException())
//                .when(mockObjectMapper)
//                .writeValue(any(File.class), any(Product[].class));
//
//        Product hero = new Product(102,"Wi-Fire");
//
//        assertThrows(IOException.class, () -> heroFileDAO.createHero(hero), "IOException not thrown");
//    }
//
//    @Test
//    public void testGetHeroNotFound() {
//        // Invoke
//        Product hero = heroFileDAO.getHero(98);
//
//        // Analyze
//	    assertNull(hero);
//    }
//
//    @Test
//    public void testDeleteHeroNotFound() {
//        // Invoke
//        boolean result = Assertions.assertDoesNotThrow(() -> heroFileDAO.deleteHero(98), "Unexpected exception thrown");
//
//        // Analyze
//	    assertFalse(result);
//        assertEquals(heroFileDAO.heroes.size(), testHeroes.length);
//    }
//
//    @Test
//    public void testUpdateHeroNotFound() {
//        // Setup
//        Product hero = new Product(98,"Bolt");
//
//        // Invoke
//        Product result = Assertions.assertDoesNotThrow(() -> heroFileDAO.updateHero(hero), "Unexpected exception thrown");
//
//        // Analyze
//        assertNull(result);
//    }
//
//    @Test
//    public void testConstructorException() throws IOException {
//        // Setup
//        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
//
//        // We want to simulate with a Mock Object Mapper that an
//        // exception was raised during JSON object deseerialization
//        // into Java objects
//        // When the Mock Object Mapper readValue method is called
//        // from the HeroFileDAO load method, an IOException is
//        // raised
//        doThrow(new IOException())
//                .when(mockObjectMapper)
//                .readValue(new File("doesnt_matter.txt"), Product[].class);
//
//        // Invoke & Analyze
//        assertThrows(IOException.class, () -> new ProductFileDAO("doesnt_matter.txt", mockObjectMapper), "IOException not thrown");
//    }
//
//}
