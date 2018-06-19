package bookeditor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    //We create a query using the JPA criteria API
    //https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
    //https://spring.io/blog/2011/04/26/advanced-spring-data-jpa-specifications-and-querydsl/
    Page<Book> findByTitleStartsWithIgnoreCase(String title, Pageable pageable);
    Page<Book> findByAuthorStartsWithIgnoreCase(String author, Pageable pageable);
    Page<Book> findByPrintYear(int printYear, Pageable pageable);
    Page<Book> findByreadAlready(Boolean readAlready, Pageable pageable);

}