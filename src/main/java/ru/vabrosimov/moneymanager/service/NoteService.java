package ru.vabrosimov.moneymanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.vabrosimov.moneymanager.entity.Note;
import ru.vabrosimov.moneymanager.entity.NoteCategory;
import ru.vabrosimov.moneymanager.types.NoteType;
import ru.vabrosimov.moneymanager.repository.NoteRepository;

import java.time.LocalDate;
import java.util.*;

@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;

    public void save(Note note) {
        noteRepository.save(note);
    }

    public void delete(Authentication authentication, Long id) {
        if (!Objects.equals(this.findById(id).getUsername(), authentication.getName())) {
            return;
        }

        noteRepository.deleteById(id);
    }

    public Note findById(Long id) {
        return noteRepository.findById(id).get();
    }

    public List<Note> findAll(Authentication authentication) {
        return noteRepository.findByUsername(authentication.getName(), Sort.by(Sort.Direction.DESC, "id"));
    }

    public Note findLast() {
        return noteRepository.findTopByOrderByIdDesc();
    }

    public List<Note> findAllByCategory(Authentication authentication, NoteCategory category) {
        String stringType = String.valueOf(category.getType());
        String categoryName = category.getName();
        return noteRepository.findAllByUsernameAndTypeAndCategoryName(authentication.getName(), stringType, categoryName);
    }

    public List<Note> findAllByYearAndMonth(Authentication authentication, int year, int month) {
        String stringYear = String.valueOf(year);
        String stringMonth = String.format("%02d", month);
        return noteRepository.findByUsernameAndYearAndMonth(authentication.getName(), stringYear, stringMonth);
    }

    public List<Note> findAllInCurrentMonth(Authentication authentication) {
        int currentYear = getCurrentYearAndMonth().get("year");
        int currentMonth = getCurrentYearAndMonth().get("month");
        return this.findAllByYearAndMonth(authentication, currentYear, currentMonth);
    }

    public List<Note> findAllByYearAndMonthAndType(Authentication authentication, int year, int month, NoteType type) {
        String stringYear = String.valueOf(year);
        String stringMonth = String.format("%02d", month);
        String stringType = String.valueOf(type);
        return noteRepository.findAllByUsernameAndYearAndMonthAndType(authentication.getName(), stringYear, stringMonth, stringType);
    }

    public List<Note> findAllInCurrentMonthByType(Authentication authentication, NoteType type) {
        int currentYear = getCurrentYearAndMonth().get("year");
        int currentMonth = getCurrentYearAndMonth().get("month");
        return this.findAllByYearAndMonthAndType(authentication, currentYear, currentMonth, type);
    }

    public List<Note> findAllByYearAndMonthAndCategory(Authentication authentication, int year, int month, NoteCategory category) {
        String stringYear = String.valueOf(year);
        String stringMonth = String.format("%02d", month);
        String stringType = String.valueOf(category.getType());
        String categoryName = category.getName();
        return noteRepository.findAllByUsernameAndYearAndMonthAndTypeAndCategoryName(authentication.getName(), stringYear, stringMonth, stringType, categoryName);
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
}
