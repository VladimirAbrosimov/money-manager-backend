package ru.vabrosimov.moneymanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.vabrosimov.moneymanager.entity.NoteCategory;
import ru.vabrosimov.moneymanager.types.NoteType;
import ru.vabrosimov.moneymanager.service.NoteCategoryService;

import java.util.List;

@RestController
public class NoteCategoryController {
    @Autowired
    NoteCategoryService noteCategoryService;

    @PostMapping("/saveNoteCategory")
    public String saveNoteCategory(@RequestBody NoteCategory noteCategory, Authentication authentication) {
        noteCategoryService.save(authentication, noteCategory);
        return "success";
    }

    @PostMapping("/deleteNoteCategory")
    public void deleteNoteCategory(@RequestBody NoteCategory noteCategory, Authentication authentication) {
        noteCategoryService.delete(authentication, noteCategory);
    }

    @GetMapping("/getAllNoteCategories")
    public List<NoteCategory> getNoteCategoriesByType(Authentication authentication) {
        return noteCategoryService.findAll(authentication);
    }

    @GetMapping("/getNoteCategoriesByType")
    public List<NoteCategory> getNoteCategoriesByType(@RequestParam NoteType type, Authentication authentication) {
        return noteCategoryService.findAllByType(authentication, type);
    }
}
