//package com.example.blog;
//
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.testcontainers.containers.MySQLContainer;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
///**
// * Базовий клас для інтеграційних тестів із MySQL Testcontainer.
// */
//@SpringBootTest
//@Testcontainers
//public abstract class AbstractTestContainer {
//
//    // Контейнер з образом MySQL
//    static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
//            .withDatabaseName("test-db")
//            .withUsername("test")
//            .withPassword("test");
//
//    // Старт контейнера перед усіма тестами
//    static {
//        mysql.start();
//    }
//
//    // Динамічне призначення властивостей БД
//    @DynamicPropertySource
//    static void setDatasourceProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", mysql::getJdbcUrl);
//        registry.add("spring.datasource.username", mysql::getUsername);
//        registry.add("spring.datasource.password", mysql::getPassword);
//        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
//    }
//}
