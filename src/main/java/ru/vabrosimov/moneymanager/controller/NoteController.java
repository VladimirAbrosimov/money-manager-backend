package ru.vabrosimov.moneymanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.vabrosimov.moneymanager.entity.Note;
import ru.vabrosimov.moneymanager.entity.NoteCategory;
import ru.vabrosimov.moneymanager.service.NoteCategoryService;
import ru.vabrosimov.moneymanager.service.NoteService;

import java.util.List;


@RestController
public class NoteController {
    @Autowired
    private NoteService noteService;

    @Autowired
    private NoteCategoryService noteCategoryService;

    @GetMapping("/getLastNote")
    public Note getLastNote() {
        return noteService.findLast();
    }

    @GetMapping("/getAllNotes")
    public List<Note> getAllNotes(Authentication authentication) {
        return noteService.findAll(authentication);
    }

    @PostMapping("/addNote")
    public String addNote(@RequestBody Note note, @RequestParam String noteCategoryName, Authentication authentication) {
        NoteCategory category = noteCategoryService.findByTypeAndName(authentication, note.getType(), noteCategoryName);
        note.setCategory(category);
        note.setUsername(authentication.getName());

        noteService.save(note);
        return "success";
    }
}
