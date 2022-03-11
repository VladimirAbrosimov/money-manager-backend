package ru.vabrosimov.moneymanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.vabrosimov.moneymanager.entity.NoteCategory;
import ru.vabrosimov.moneymanager.entity.NoteType;
import ru.vabrosimov.moneymanager.repository.NoteCategoryRepository;

import java.util.List;

@Service
public class NoteCategoryService {
    @Autowired
    private NoteCategoryRepository noteCategoryRepository;

    @Autowired
    private UserService userService;

    public boolean save(Authentication authentication, NoteCategory noteCategory) {
        NoteCategory noteCategoryFromDB = this.findByTypeAndName(authentication, noteCategory.getType(), noteCategory.getName());
        if (noteCategoryFromDB == null) {
            noteCategory.setUsername(authentication.getName());
            noteCategoryRepository.save(noteCategory);
            userService.addNoteCategory(authentication, noteCategory);
            return true;
        }
        return false;
    }

    public List<NoteCategory> findAllByType(Authentication authentication, NoteType type) {
        String username = authentication.getName();
        return noteCategoryRepository.findAllByUsernameAndType(username, type);
    }

    public NoteCategory findByTypeAndName(Authentication authentication, NoteType type, String name) {
        String username = authentication.getName();
        return noteCategoryRepository.findByUsernameAndTypeAndName(username, type, name);
    }
}
