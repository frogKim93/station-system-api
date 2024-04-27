package com.frogkim93.stationsystemapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @Column
    private String memberID;

    @Column
    private String name;

    @Column
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private LocalDateTime createdAt;

}
