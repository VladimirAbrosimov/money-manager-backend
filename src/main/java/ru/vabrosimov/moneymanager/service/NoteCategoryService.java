package ru.vabrosimov.moneymanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.vabrosimov.moneymanager.entity.Note;
import ru.vabrosimov.moneymanager.entity.NoteCategory;
import ru.vabrosimov.moneymanager.types.NoteType;
import ru.vabrosimov.moneymanager.repository.NoteCategoryRepository;

import java.util.List;

@Service
public class NoteCategoryService {
    @Autowired
    private NoteCategoryRepository noteCategoryRepository;

    @Autowired
    private NoteService noteService;

    public boolean save(Authentication authentication, NoteCategory noteCategory) {
        NoteCategory noteCategoryFromDB = this.findByTypeAndName(authentication, noteCategory.getType(), noteCategory.getName());
        if (noteCategoryFromDB == null) {
            noteCategory.setUsername(authentication.getName());
            noteCategoryRepository.save(noteCategory);
            return true;
        }
        return false;
    }

    public void delete(Authentication authentication, NoteCategory noteCategory) {
        List<Note> notesWithThisCategory = noteService.findAllByCategory(authentication, noteCategory);
        notesWithThisCategory.forEach((Note note) -> {
          note.setCategory(this.findByTypeAndName(authentication, note.getType(), "неизвестная категория"));
        });

        NoteCategory category = this.findByTypeAndName(authentication, noteCategory.getType(), noteCategory.getName());
        noteCategoryRepository.deleteById(category.getId());
    }

    public List<NoteCategory> findAll(Authentication authentication) {
        String username = authentication.getName();
        List<NoteCategory> noteCategories = noteCategoryRepository.findAllByUsernameOrderByIdDesc(username);
        this.removeUnknownCategories(authentication, noteCategories);
        return noteCategories;
    }

    public List<NoteCategory> findAllByType(Authentication authentication, NoteType type) {
        String username = authentication.getName();
        return noteCategoryRepository.findAllByUsernameAndTypeOrderByIdDesc(username, type);
    }

    public NoteCategory findByTypeAndName(Authentication authentication, NoteType type, String name) {
        String username = authentication.getName();
        return noteCategoryRepository.findByUsernameAndTypeAndName(username, type, name);
    }

    private void removeUnknownCategories(Authentication authentication, List<NoteCategory> noteCategories) {
        NoteCategory unknownCategoryIncome = this.findByTypeAndName(authentication, NoteType.INCOME, "неизвестная категория");
        NoteCategory unknownCategoryExpense = this.findByTypeAndName(authentication, NoteType.EXPENSE, "неизвестная категория");
        noteCategories.remove(unknownCategoryIncome);
        noteCategories.remove(unknownCategoryExpense);
    }
}
