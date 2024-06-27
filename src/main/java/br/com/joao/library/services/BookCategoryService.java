package br.com.joao.library.services;

import br.com.joao.library.domain.book.Book;
import br.com.joao.library.domain.category.BookCategory;
import br.com.joao.library.domain.category.BookCategoryDTO;
import br.com.joao.library.domain.category.Category;
import br.com.joao.library.exceptions.EntityNotFoundException;
import br.com.joao.library.repositories.BookCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookCategoryService {

    private final BookCategoryRepository bookCategoryRepository;
    private final BookService bookService;
    private final CategoryService categoryService;

    private BookCategoryService(BookCategoryRepository bookCategoryRepository,
                                BookService bookService,
                                CategoryService categoryService) {
        this.bookCategoryRepository = bookCategoryRepository;
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    public BookCategory findBookCategory(Book book, Category category) {
        return bookCategoryRepository.findById(new BookCategory(book, category).getId())
                .orElseThrow(() -> new EntityNotFoundException("Relation Book/Category not found"));
    }

    public void removeCategory(UUID id, BookCategoryDTO bookCategoryDTO) {
        Book book = bookService.findBookById(id);
        Category category = categoryService.findCategory(bookCategoryDTO.categoryId());
        BookCategory bookCategory = findBookCategory(book, category);
        bookCategoryRepository.deleteById(bookCategory.getId());
    }

    public Book addCategory(UUID id, BookCategoryDTO bookCategoryDTO) {
        Book book = bookService.findBookById(id);
        Category category = categoryService.findCategory(bookCategoryDTO.categoryId());
        bookCategoryRepository.save(new BookCategory(book, category));
        return book;
    }


}
