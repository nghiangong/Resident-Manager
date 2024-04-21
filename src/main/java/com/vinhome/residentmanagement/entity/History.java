package com.vinhome.residentmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "histories")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private boolean gender;
    @Column(nullable = false)
    private LocalDateTime date;
    @Column(nullable = false, name = "is_resident")
    private boolean isResident;
    @Column(name = "deleted_at")
    @Temporal(TemporalType.DATE)
    private Date deletedAt;
    @ManyToOne
    @JoinColumn(name="gate_id")
    private Gate gate;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User qrCreator;
    @Column(nullable = false, name = "read_status",columnDefinition = "boolean default false")
    private boolean readStatus;
    @Lob
    private String note;
}
