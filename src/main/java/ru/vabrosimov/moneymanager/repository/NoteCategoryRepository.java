package ru.vabrosimov.moneymanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vabrosimov.moneymanager.entity.NoteCategory;
import ru.vabrosimov.moneymanager.entity.NoteType;

import java.util.List;

public interface NoteCategoryRepository extends JpaRepository<NoteCategory, Long> {
//    @Query(value = "select * from note_category where type=?2 and name=?3 and note_user_id in" +
//            "(select id from note_user where username=?1)", nativeQuery = true)
    NoteCategory findByUsernameAndTypeAndName(String username, NoteType type, String name);

//    @Query(value = "select * from note_category where type=?2 and note_user_id in" +
//            "(select id from note_user where username=?1)", nativeQuery = true)
    List<NoteCategory> findAllByUsernameAndType(String username, NoteType type);
}
