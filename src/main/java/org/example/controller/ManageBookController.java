package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.entity.Book;
import org.example.services.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;


@Controller
@RequestMapping("/admin/books")
@AllArgsConstructor
public class ManageBookController {

    private final BookService bookService;


    @GetMapping("/show/{id}")
    public String showBook(Model model, @PathVariable long id) {
        model.addAttribute("book", bookService.get(id).orElseThrow(EntityNotFoundException::new));
        return "books/show";
    }

    @GetMapping("/all")
    public String showPosts(Model model) {
        model.addAttribute("books", bookService.getBooks());
        return "/books/all";
    }
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        return "books/add";
    }


    @PostMapping("/add")
    public String saveBook(@Valid Book book, BindingResult result) {
        if (result.hasErrors()) {
            return "books/add";
        }
        bookService.add(book);
        return "redirect:/admin/books/all";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model) {
        model.addAttribute("book", bookService.get(id));
        return "books/edit";
    }

    @PostMapping("/edit")
    public String editBook(@Valid Book book, BindingResult result) {
        if (result.hasErrors()) {
            return "books/edit";
        }
        bookService.add(book);
        return "redirect:/admin/books/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable long id) {
        bookService.delete(id);
        return "redirect:/admin/books/all";
    }

}
