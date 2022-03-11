package ru.vabrosimov.moneymanager.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.vabrosimov.moneymanager.entity.Note;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByUsername(String username, Sort sort);

    Note findTopByOrderByIdDesc();

    @Query(value = "SELECT * from note where username=?1 and type=?2 and note_category_id in" +
            "(select id from note_category where name=?3)", nativeQuery = true)
    List<Note> findAllByUsernameAndTypeAndCategoryName(String username, String type, String categoryName);

    @Query(value = "SELECT * from note where text(date) like CONCAT(?2, '-', ?3, '%') and username=?1 order by date DESC, id DESC", nativeQuery = true)
    List<Note> findByUsernameAndYearAndMonth(String username, String year, String month);

    @Query(value = "SELECT * from note where username=?1 and text(date) like CONCAT(?2, '-', ?3, '%') and type=?4", nativeQuery = true)
    List<Note> findAllByUsernameAndYearAndMonthAndType(String username, String year, String month, String type);

    @Query(value = "SELECT * from note where username=?1 and text(date) like CONCAT(?2, '-', ?3, '%') and type=?4 and note_category_id in" +
            "(select id from note_category where name=?5) order by date DESC, id DESC", nativeQuery = true)
    List<Note> findAllByUsernameAndYearAndMonthAndTypeAndCategoryName(String username, String year, String month, String type, String categoryName);
}
