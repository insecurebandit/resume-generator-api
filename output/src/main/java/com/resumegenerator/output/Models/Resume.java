package com.resumegenerator.output.Models;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "Resume")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
<<<<<<< HEAD
    private Long resumeId;
=======
    private Long resume_id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "personalInformation_id", referencedColumnName = "id")
    private personalInformation personalInformation;
>>>>>>> 187d47f3e63fe144367f8859f9daf553683ab8ab

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "resume", optional = true)
    @JsonManagedReference
    private PersonalInformation PersonalInformation;

    @OneToOne(cascade =  CascadeType.ALL, mappedBy = "resume", optional = true)
    @JsonManagedReference
    private Education education;

    @OneToOne(cascade =  CascadeType.ALL, mappedBy = "resume", optional = true)
    @JsonManagedReference
    private Skills skills;

    private LocalDateTime createdAt;
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    //One-to-many model connection: (MUST) @OneToMany
}
