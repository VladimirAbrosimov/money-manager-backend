package ru.vabrosimov.moneymanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.vabrosimov.moneymanager.entity.Note;
import ru.vabrosimov.moneymanager.entity.NoteCategory;
import ru.vabrosimov.moneymanager.service.NoteCategoryService;
import ru.vabrosimov.moneymanager.service.NoteService;

import java.sql.Date;
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

//    @GetMapping("/getAllNotesByYearAndMonthAndCategoryAndType")
//    public List<Note> getAllNotesByYearAndMonthAndCategoryAndType
//            (@RequestParam int year, @RequestParam int month, @RequestParam String categoryName, @RequestParam NoteType type, Authentication authentication) {
////        return noteService.findAllByYearAndMonthAndCategoryNameAndType(authentication, year, month, category, type);
//        return noteService.findAll(authentication);
//    }

//    @GetMapping("/getNotesByYearAndMonth")
//    public List<Note> getNotesByYearAndMonth(@RequestParam int year, @RequestParam int month, Authentication authentication) {
//        return noteService.getNotesByYearAndMonth(authentication, year, month);
//    }
//
//    @GetMapping("/getNotesInCurrentMonth")
//    public List<Note> getNotesInCurrentMonth(Authentication authentication) {
//        return noteService.getNotesInCurrentMonth(authentication);
//    }
//
//    @GetMapping("/getNotesByYearAndMonthAndCategory")
//    public List<Note> getNotesByYearAndMonthAndCategory(@RequestParam int year, @RequestParam int month,
//                                                        @RequestParam Note.NoteCategory category, Authentication authentication) {
//        return noteService.getNotesByYearAndMonthAndCategory(authentication, year, month, category);
//    }
//
//    @GetMapping("/getNotesInCurrentMonthByCategory")
//    public List<Note> getNotesInCurrentMonthByCategory(@RequestParam Note.NoteCategory category, Authentication authentication) {
//        return noteService.getNotesInCurrentMonthByCategory(authentication, category);
//    }

    @PostMapping("/addNote")
    public String addNote(@RequestBody Note note, @RequestParam String noteCategoryName, Authentication authentication) {
        System.out.println(note.getType());
        System.out.println(noteCategoryName);
        NoteCategory category = noteCategoryService.findByTypeAndName(authentication, note.getType(), noteCategoryName);
        note.setCategory(category);

        note.setDate(new Date(System.currentTimeMillis()));

        note.setUsername(authentication.getName());

        noteService.saveNote(note);
        return "success";
    }
}
