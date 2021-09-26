package com.example.readingisgood.repository;

import com.example.readingisgood.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    @Query("SELECT b FROM Book b WHERE b.name=:name AND b.author=:author AND b.year=:year")
    Book findByNameAndAuthor(@Param("name") String name, @Param("author") String author, @Param("year") int year);

    @Query("SELECT b FROM Book b WHERE b.id=:id")
    Book findById(@Param("id") String id);
}
