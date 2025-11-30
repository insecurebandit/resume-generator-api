package com.resumegenerator.output.Controllers;

import com.resumegenerator.output.Models.ResumeModel;
import com.resumegenerator.output.Requests.CreateResumeRequest;
import com.resumegenerator.output.Services.ResumeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping("/resumes")
    public ResponseEntity<ResumeModel> createResume(@RequestBody CreateResumeRequest request) {
        ResumeModel resume = resumeService.createResume(request);
        return new ResponseEntity<>(resume, HttpStatus.CREATED);
    }

    @GetMapping("/resumes")
    public ResponseEntity<List<ResumeModel>> getAllResumes() {
        List<ResumeModel> resumes = resumeService.getAllResumes();
        return new ResponseEntity<>(resumes, HttpStatus.OK);
    }
}
