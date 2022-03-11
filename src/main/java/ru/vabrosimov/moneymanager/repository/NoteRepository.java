package ru.vabrosimov.moneymanager.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.vabrosimov.moneymanager.entity.Note;
import ru.vabrosimov.moneymanager.entity.NoteCategory;
import ru.vabrosimov.moneymanager.entity.TotalMoney;

import java.util.List;
import java.util.Map;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByUsername(String username, Sort sort);

    Note findTopByOrderByIdDesc();

    @Query(value = "SELECT * from note where text(date) like CONCAT(?2, '-', ?3, '%') and username=?1 order by date DESC, id DESC", nativeQuery = true)
    List<Note> findByUsernameAndYearAndMonth(String username, String year, String month);

    @Query(value = "SELECT * from note where text(date) like CONCAT(?2, '-', ?3, '%') and username=?1 and note_category_id in" +
            "(select id from note_category where name=?4) order by date DESC, id DESC", nativeQuery = true)
    List<Note> findByUsernameAndYearAndMonthAndCategoryName(String username, String year, String month, String categoryName);

    @Query(value = "SELECT * from note where username=?1 and text(date) like CONCAT(?2, '-', ?3, '%') and type=?4 and note_category_id in" +
            "(select id from note_category where name=?5) order by date DESC, id DESC", nativeQuery = true)
    List<Note> findAllByUsernameAndYearAndMonthAndTypeAndCategoryName(String username, String year, String month, String type, String categoryName);


    @Query(value = "select sum(amount), nc.name from note inner join note_category nc on nc.id = note.note_category_id\n" +
            "where note.username='user' and text(note.date) like CONCAT('2021','-','10','%') and note.type='EXPENSE'\n" +
            "group by nc.name", nativeQuery = true)
    List<Object[]> d();
}
