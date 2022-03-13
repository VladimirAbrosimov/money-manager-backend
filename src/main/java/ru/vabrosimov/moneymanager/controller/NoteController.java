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

    @PostMapping("/saveNote")
    public String saveNote(@RequestBody Note note, @RequestParam Long noteCategoryId, Authentication authentication) {
        NoteCategory category = noteCategoryService.findById(noteCategoryId);
        note.setCategory(category);
        note.setUsername(authentication.getName());

        noteService.save(note);
        return "success";
    }

    @GetMapping("/deleteNote")
    public void deleteNote(@RequestParam Long id, Authentication authentication) {
        noteService.delete(authentication, id);
    }

    @GetMapping("/getAllNotes")
    public List<Note> getAllNotes(Authentication authentication) {
        return noteService.findAll(authentication);
    }

    @GetMapping("/getLastNote")
    public Note getLastNote() {
        return noteService.findLast();
    }
}
