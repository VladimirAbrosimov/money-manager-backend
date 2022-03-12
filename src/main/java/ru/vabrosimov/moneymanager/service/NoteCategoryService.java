package ru.vabrosimov.moneymanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.vabrosimov.moneymanager.entity.Note;
import ru.vabrosimov.moneymanager.entity.NoteCategory;
import ru.vabrosimov.moneymanager.types.NoteType;
import ru.vabrosimov.moneymanager.repository.NoteCategoryRepository;

import java.util.ArrayList;
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

    public void initDefaultNoteCategories(String username) {
        ArrayList<NoteCategory> categoriesExpense = new ArrayList<>();
        categoriesExpense.add(new NoteCategory(username, NoteType.EXPENSE, "неизвестная категория", "#a7a7a7"));
        categoriesExpense.add(new NoteCategory(username, NoteType.EXPENSE, "аренда", "#4e4892"));
        categoriesExpense.add(new NoteCategory(username, NoteType.EXPENSE, "коммунальные услуги", "#f04241"));
        categoriesExpense.add(new NoteCategory(username, NoteType.EXPENSE, "продукты", "#d23788"));
        categoriesExpense.add(new NoteCategory(username, NoteType.EXPENSE, "одежда", "#99204b"));
        categoriesExpense.add(new NoteCategory(username, NoteType.EXPENSE, "развлечения", "#155ba9"));

        ArrayList<NoteCategory> categoriesIncome = new ArrayList<>();
        categoriesIncome.add(new NoteCategory(username, NoteType.INCOME, "неизвестная категория", "#a7a7a7"));
        categoriesIncome.add(new NoteCategory(username, NoteType.INCOME, "аванс", "#85af41"));
        categoriesIncome.add(new NoteCategory(username, NoteType.INCOME, "зарплата", "#8cdba9"));
        categoriesIncome.add(new NoteCategory(username, NoteType.INCOME, "дивиденды", "#bfa8bb"));
        categoriesIncome.add(new NoteCategory(username, NoteType.INCOME, "пенсия", "#33ab5f"));
        categoriesIncome.add(new NoteCategory(username, NoteType.INCOME, "премия", "#688b9a"));

        categoriesExpense.forEach(e -> noteCategoryRepository.save(e));
        categoriesIncome.forEach(e -> noteCategoryRepository.save(e));
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
