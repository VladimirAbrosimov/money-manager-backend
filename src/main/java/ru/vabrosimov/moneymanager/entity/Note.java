package ru.vabrosimov.moneymanager.entity;

import lombok.Data;
import ru.vabrosimov.moneymanager.types.NoteType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="note")
@Data
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Enumerated(EnumType.STRING)
    private NoteType type;

    private long amount;

    private java.sql.Date date;

    @ManyToOne
    @JoinColumn(name = "noteCategory_id")
    @NotNull
    private NoteCategory category;

    private String commentary;
}
