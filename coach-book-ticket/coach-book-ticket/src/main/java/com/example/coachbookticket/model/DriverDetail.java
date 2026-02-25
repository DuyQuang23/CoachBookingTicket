package com.example.coachbookticket.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "driverdetail")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverDetail {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driver_detail_id")
    private Integer driverDetailId;

    // sở hữu FK driver_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private Driver driver;

    private String address;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    private String note;
    private String avatar;
}
