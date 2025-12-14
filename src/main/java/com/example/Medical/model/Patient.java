package com.example.Medical.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nic;

    private String firstName;
    private String lastName;
    private String contactNumber;
    private String livingArea;
    private Integer age;
}
