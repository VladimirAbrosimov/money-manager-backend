package ru.vabrosimov.moneymanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.vabrosimov.moneymanager.entity.Note;
import ru.vabrosimov.moneymanager.entity.NoteCategory;
import ru.vabrosimov.moneymanager.entity.NoteType;
import ru.vabrosimov.moneymanager.repository.NoteRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.*;

@Service
public class NoteService {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private NoteRepository noteRepository;

    public void saveNote(Note note) {
        noteRepository.save(note);
    }

    private Map<String, Integer> getCurrentYearAndMonth() {
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();

        Map<String, Integer> currentYearAndMonth = new HashMap<>();
        currentYearAndMonth.put("year", currentYear);
        currentYearAndMonth.put("month", currentMonth);
        return currentYearAndMonth;
    }

    public List<Note> findAll(Authentication authentication) {
        return noteRepository.findByUsername(authentication.getName(), Sort.by(Sort.Direction.DESC, "id"));
    }

    public Note findLast() {
        return noteRepository.findTopByOrderByIdDesc();
    }

    public List<Note> findAllByYearAndMonth(Authentication authentication, int year, int month) {
        String stringYear = String.valueOf(year);
        String stringMonth = String.format("%02d", month);
        return noteRepository.findByUsernameAndYearAndMonth(authentication.getName(), stringYear, stringMonth);
    }

    public List<Note> findAllInCurrentMonth(Authentication authentication) {
        int currentYear = getCurrentYearAndMonth().get("year");
        int currentMonth = getCurrentYearAndMonth().get("month");
        return findAllByYearAndMonth(authentication, currentYear, currentMonth);
    }

//    public List<Note> getNotesByYearAndMonthAndCategory(Authentication authentication, int year, int month, NoteCategory category) {
//        String stringYear = String.valueOf(year);
//        String stringMonth = String.format("%02d", month);
//        String stringCategory = String.valueOf(category);
//        return noteRepository.findByYearAndMonthAndCategory(authentication.getName(), stringYear, stringMonth, stringCategory);
//    }
//
//    public List<Note> getNotesInCurrentMonthByCategory(Authentication authentication, Note.NoteCategory category) {
//        int currentYear = getCurrentYearAndMonth().get("year");
//        int currentMonth = getCurrentYearAndMonth().get("month");
//        return getNotesByYearAndMonthAndCategory(authentication, currentYear, currentMonth, category);
//    }

    public List<Note> findAllByYearAndMonthAndTypeAndCategory(Authentication authentication, int year, int month, NoteType type, NoteCategory category) {
        String stringYear = String.valueOf(year);
        String stringMonth = String.format("%02d", month);
        String stringType = String.valueOf(type);
        String categoryName = category.getName();
        return noteRepository.findAllByUsernameAndYearAndMonthAndTypeAndCategoryName(authentication.getName(), stringYear, stringMonth, stringType, categoryName);
    }


}
