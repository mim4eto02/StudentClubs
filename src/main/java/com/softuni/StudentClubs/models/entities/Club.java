package com.softuni.StudentClubs.models.entities;

import com.softuni.StudentClubs.models.enums.ClubTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "clubs")
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition="TEXT", length = 2000, nullable = false)
    private String photoUrl;

    @Column(columnDefinition="TEXT", length = 2000, nullable = false)
    private String content;

    @CreationTimestamp
    private LocalDate createdOn;

    @UpdateTimestamp
    private LocalDate updatedOn;

//    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ClubTypeEnum type;

    @Column(columnDefinition="TEXT", length = 2000, nullable = true)
    private String address;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private UserEntity createdBy;

    @OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE)
    private List<Event> events = new ArrayList<>();

}
