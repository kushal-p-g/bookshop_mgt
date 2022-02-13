package com.kushal.webapp.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kushal.webapp.entity.Books;
import com.kushal.webapp.entity.User;
import com.kushal.webapp.service.BooksService;
import com.kushal.webapp.userRpository.UserRepository;


 
@Controller
public class AppController {
	
	
	   
	  
	   @Autowired
	   private BooksService booksService;
	   
 
    @Autowired
    private UserRepository userRepo;
     
    @GetMapping("/")
    public String viewHomePage() {
        return "index";
    }
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
         
        return "signup_form";
    }
    @RequestMapping(value = "/process_register", method = { RequestMethod.GET, RequestMethod.POST })
    public String processRegister(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
         
        userRepo.save(user);
         
        return "registration_success";
    }
    @RequestMapping(value = "/books", method = { RequestMethod.GET, RequestMethod.POST })
    public String viewBooks(Model model) {
    	//display all books
    	model.addAttribute("listBooks", booksService.getAllBooks());
    	return "bookshop"; 	
    }
    @GetMapping(value = "/newBookform")
   	public String showNewBooksForm(Model model) {
		// create model attribute to bind form data
		Books books = new Books();
		model.addAttribute("books", books);
		return "new_book";
	}
    @RequestMapping(value = "/saveBooks", method = { RequestMethod.GET, RequestMethod.POST })
	public String saveBooks(@ModelAttribute("books") Books books) {

    //save books to database
    booksService.saveBooks(books);
	return "new_book";
    }
 
    @RequestMapping(value = "/showFormForUpdate/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
		
		// get books from the service
		Books books = booksService.getBooksById(id);
		
		// set books as a model attribute to pre-populate the form
		model.addAttribute("books", books);
		return "update_books";
	}
    @RequestMapping(value = "/deleteBooks/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String deleteBooks(@PathVariable (value = "id") long id) {
		
		// call delete books method 
		this.booksService.deleteBooksById(id);
		return "redirect:/books";
	}
    @GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 5;
		
		Page<Books> page = booksService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Books> listBooks = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		
		model.addAttribute("listBooks", listBooks);
		return "bookshop";
	}
		
}