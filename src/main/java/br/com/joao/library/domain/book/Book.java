package br.com.joao.library.domain.book;

import br.com.joao.library.domain.borrow.Borrow;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private String publisher;
    @Column(nullable = false)
    private Integer pages;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String imgUrl;

    @JsonManagedReference
    @ManyToMany
    @JoinTable(
            name = "book_category",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    @JsonManagedReference
    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL)
    private Borrow borrow;

    public Book(BookDTO bookDTO) {
        BeanUtils.copyProperties(bookDTO, this);
    }

    public Book(Long id, String title, String author, String publisher, Integer pages, String imgUrl) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.pages = pages;
        this.imgUrl = imgUrl;
    }

}
