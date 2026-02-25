package com.example.coachbookticket.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;

    private String name;

    @Column(columnDefinition = "text")
    private String description;

    private String type;


    @OneToMany
    @JoinColumn(name = "category_id")
    private List<Car> cars;
}
