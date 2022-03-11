package ru.vabrosimov.moneymanager.entity;

import lombok.Data;
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

}
