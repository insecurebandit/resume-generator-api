package com.resumegenerator.output.Models;
import com.resumegenerator.output.Models.address;
import com.resumegenerator.output.Models.Name;
import com.resumegenerator.output.Models.email;
import com.resumegenerator.output.Models.contactNumber;
import com.resumegenerator.output.Models.workExperience;
import com.resumegenerator.output.Models.education;
import com.resumegenerator.output.Models.skills;
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
    private Long resume_id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "personalInformation_id", referencedColumnName = "id")
    private personalInformation personalInformation;

    @Column(name = "firstName", nullable = false, updatable = true)
    private String firstName;

    @Column(name = "lastName", nullable = false, updatable = true)
    private String lastName;

    @Column(name= "email", nullable = false, updatable = true)
    private String email;

    @Column(name = "phone", nullable = false, updatable = true)
    private String phone;

    private LocalDateTime createdAt;
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    //One-to-many model connection: (MUST) @OneToMany
}
