package com.kushal.webapp.books;

import java.util.List;

import org.springframework.data.domain.Page;

public interface BooksService {
	List<Books> getAllBooks();
	void saveBooks(Books books);
	Books getBooksById(long id);
	void deleteBooksById(long id);
	Page<Books> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

}
