package com.kushal.webapp.userRpository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kushal.webapp.entity.Books;

public interface BooksRepository extends JpaRepository<Books, Long>{

}
