package com.resumegenerator.output.Requests;

<<<<<<< HEAD
import jakarta.validation.constraints.NotBlank;
=======
import com.resumegenerator.output.Models.personalInformation;
>>>>>>> 187d47f3e63fe144367f8859f9daf553683ab8ab
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CreateResumeRequest {
<<<<<<< HEAD
    @NotBlank
    private String firstName;

    private String middleName;

    @NotBlank
    private String lastName;

    private String email;

    @NotBlank
    private String phone;
    @NotBlank
    private String address;

    private String skills;

    private String institution;

    private String completionDate;
=======
    private personalInformation personalInformation;
>>>>>>> 187d47f3e63fe144367f8859f9daf553683ab8ab
}
