package ru.vabrosimov.moneymanager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.vabrosimov.moneymanager.types.NoteType;

import javax.persistence.*;

@Entity
@Data
public class NoteCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne //(optional = false, cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")
//    private User user;

    private String username;

    @Enumerated(EnumType.STRING)
    private NoteType type;

    private String name;

    @Column(length=7)
    private String color;

    public NoteCategory() {}
    public NoteCategory(String username, NoteType type, String name, String color) {
        this.username = username;
        this.type = type;
        this.name = name;
        this.color = color;
    }
}
