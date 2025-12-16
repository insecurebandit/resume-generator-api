package com.resumegenerator.output.Services;
import com.resumegenerator.output.Models.Resume;
import com.resumegenerator.output.Models.personalInformation;
import com.resumegenerator.output.Repositories.ResumeRepository;
import com.resumegenerator.output.Requests.CreateResumeRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
//http//:localhost:8080/api/resume
public class ResumeService {

    private final ResumeRepository resumeRepository;

    public ResumeService(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    public Resume createResume(CreateResumeRequest request) {
        personalInformation pi =  new personalInformation();
        pi.setFirstName(request.getPersonalInformation().getFirstName());
        pi.setMiddleName(request.getPersonalInformation().getMiddleName());
        pi.setLastName(request.getPersonalInformation().getLastName());
        pi.setEmail(request.getPersonalInformation().getEmail());
        pi.setAddress(request.getPersonalInformation().getAddress());
        pi.setPhone(request.getPersonalInformation().getPhone());

        Resume resume = new Resume();
        resume.setPersonalInformation(pi);

        pi.setResume(resume);

        return resumeRepository.save(resume);
    }

    public List<Resume> getAllResumes() {
        return resumeRepository.findAll();
    }

    @Transactional
    public Resume updateResumebyID(Long resumeId, CreateResumeRequest request) {
        Resume existingResume = resumeRepository.findById(resumeId).orElseThrow(() -> new RuntimeException("Resume not found: " + resumeId));

        personalInformation pi =  existingResume.getPersonalInformation();
        pi.setFirstName(request.getPersonalInformation().getFirstName());
        pi.setMiddleName(request.getPersonalInformation().getMiddleName());
        pi.setLastName(request.getPersonalInformation().getLastName());
        pi.setEmail(request.getPersonalInformation().getEmail());
        pi.setAddress(request.getPersonalInformation().getAddress());
        pi.setPhone(request.getPersonalInformation().getPhone());

        return existingResume;
     }
     @Transactional
    public void deleteResume(Long resumeId) {
        if (!resumeRepository.existsById(resumeId)) {
            throw new RuntimeException("Resume not found: " + resumeId);
        }
        resumeRepository.deleteById(resumeId);
     }

     @Transactional
     public void deleteAllResumes() {
        resumeRepository.deleteAll();
     }
}
