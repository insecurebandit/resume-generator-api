package com.resumegenerator.output.Requests;

import com.resumegenerator.output.Models.personalInformation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CreateResumeRequest {
    private personalInformation personalInformation;
}
