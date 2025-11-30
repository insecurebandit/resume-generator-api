package com.resumegenerator.output.Services;
import com.resumegenerator.output.Models.ResumeModel;
import com.resumegenerator.output.Repositories.ResumeRepository;
import com.resumegenerator.output.Requests.CreateResumeRequest;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
//http//:localhost:8080/api/resume
public class ResumeService {

    private final ResumeRepository resumeRepository;

    public ResumeService(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    public ResumeModel createResume(CreateResumeRequest request) {
        ResumeModel resume = ResumeModel.builder()
                .ResumeID(request.getResumeId())
                .build();
        resumeRepository.save(resume);
        return resume;
    }

    public List<ResumeModel> getAllResumes() {
        return resumeRepository.findAll();
    }

    public List<ResumeModel> deleteAllResumes() {
        resumeRepository.deleteAll();
        return resumeRepository.findAll();
    }
}
