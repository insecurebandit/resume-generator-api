package com.resumegenerator.output.Services;


import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.PageSize;
import com.resumegenerator.output.DTOs.ResumePdfDto;
import com.resumegenerator.output.Models.*;
import com.resumegenerator.output.Repositories.ResumeRepository;
import com.resumegenerator.output.Requests.CreateResumeRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;

    public ResumeService(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    // ---------------- CRUD ----------------

    @Transactional
    public Resume createResume(CreateResumeRequest request) {
        Resume resume = new Resume();

        // Personal Information
        PersonalInformation pi = new PersonalInformation();
        pi.setFirstName(request.getFirstName());
        pi.setMiddleName(request.getMiddleName());
        pi.setLastName(request.getLastName());
        pi.setSuffix(request.getSuffix());
        pi.setEmail(request.getEmail());
        pi.setPhone(request.getPhone());
        pi.setAddress(request.getAddress());
        pi.setResume(resume);
        resume.setPersonalInformation(pi);

        // Professional Summary
        ProfessionalSummary ps = new ProfessionalSummary();
        ps.setSummary(request.getProfessionalSummary());
        ps.setResume(resume);
        resume.setProfessionalSummary(ps);

        // Experience
        Experience exp = new Experience();
        exp.setExperience(request.getExperience());
        exp.setResume(resume);
        resume.setExperience(exp);

        // Education
        Education edu = new Education();
        edu.setInstitution(request.getInstitution());
        edu.setCompletionDate(request.getCompletionDate());
        edu.setResume(resume);
        resume.setEducation(edu);

        // Skills
        Skills skills = new Skills();
        skills.setSkills(request.getSkills());
        skills.setResume(resume);
        resume.setSkills(skills);

        return resumeRepository.save(resume);
    }

    public List<Resume> getAllResumes() {
        return resumeRepository.findAll();
    }

    @Transactional
    public Resume updateResumebyID(Long resumeId, CreateResumeRequest request) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new RuntimeException("Resume not found: " + resumeId));

        PersonalInformation pi = resume.getPersonalInformation();
        Skills skills = resume.getSkills();
        Education education = resume.getEducation();
        ProfessionalSummary ps = resume.getProfessionalSummary();
        Experience exp = resume.getExperience();

        if (pi == null) {
            pi = new PersonalInformation();
            pi.setResume(resume);
            resume.setPersonalInformation(pi);
        }

        if (ps == null) {
            ps = new ProfessionalSummary();
            ps.setResume(resume);
            resume.setProfessionalSummary(ps);
        }
        ps.setSummary(request.getProfessionalSummary());

        if (exp == null) {
            exp = new Experience();
            exp.setResume(resume);
            resume.setExperience(exp);
        }
        exp.setExperience(request.getExperience());

        if (education == null) {
            education = new Education();
            education.setResume(resume);
            resume.setEducation(education);
        }
        education.setInstitution(request.getInstitution());
        education.setCompletionDate(request.getCompletionDate());

        if (skills == null) {
            skills = new Skills();
            skills.setResume(resume);
            resume.setSkills(skills);
        }
        skills.setSkills(request.getSkills());

        return resume;
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

    // ---------------- PDF Generation ----------------
    public byte[] generateResumePdf(ResumePdfDto resume) throws DocumentException, IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // Title
        Paragraph title = new Paragraph("Resume", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24));
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph("\n"));

        // Personal Information Section
        if (resume.getPersonalInformation() != null) {
            PersonalInformation pi = resume.getPersonalInformation();
            document.add(new Paragraph("Personal Information", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
            document.add(new Paragraph("\n"));

            PdfPTable table = new PdfPTable(2);  // 2 columns: Label | Value
            table.setWidthPercentage(100);
            table.addCell("Name");
            table.addCell(pi.getFirstName() + " " + (pi.getMiddleName() != null ? pi.getMiddleName() + " " : "") + pi.getLastName());
            table.addCell("Email");
            table.addCell(pi.getEmail() != null ? pi.getEmail() : "N/A");
            table.addCell("Phone");
            table.addCell(pi.getPhone());
            table.addCell("Address");
            table.addCell(pi.getAddress());
            document.add(table);
            document.add(new Paragraph("\n"));
        }

        // Skills Section
        if (resume.getSkills() != null) {
            document.add(new Paragraph("Skills", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
            document.add(new Paragraph("\n"));
            PdfPTable skillsTable = new PdfPTable(1);  // Single column for list
            skillsTable.setWidthPercentage(100);
            skillsTable.addCell(resume.getSkills().getSkills());
            document.add(skillsTable);
            document.add(new Paragraph("\n"));
        }

        // Education Section
        if (resume.getEducation() != null) {
            document.add(new Paragraph("Education", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
            document.add(new Paragraph("\n"));
            PdfPTable eduTable = new PdfPTable(2);
            eduTable.setWidthPercentage(100);
            eduTable.addCell("Institution");
            eduTable.addCell(resume.getEducation().getInstitution());
            if (resume.getEducation().getCompletionDate() != null) {
                eduTable.addCell("Completion Date");
                eduTable.addCell(resume.getEducation().getCompletionDate());
            }
            document.add(eduTable);
            document.add(new Paragraph("\n"));
        }

        // Professional Summary Section
        if (resume.getProfessionalSummary() != null) {
            document.add(new Paragraph("Professional Summary", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
            document.add(new Paragraph("\n"));
            PdfPTable summaryTable = new PdfPTable(1);
            summaryTable.setWidthPercentage(100);
            summaryTable.addCell(resume.getProfessionalSummary().getSummary());
            document.add(summaryTable);
            document.add(new Paragraph("\n"));
        }

        // Experience Section
        if (resume.getExperience() != null) {
            document.add(new Paragraph("Work Experience", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
            document.add(new Paragraph("\n"));
            PdfPTable experienceTable = new PdfPTable(1);
            experienceTable.setWidthPercentage(100);
            experienceTable.addCell(resume.getExperience().getExperience());
            document.add(experienceTable);
            document.add(new Paragraph("\n"));
        }

        document.close();
        return outputStream.toByteArray();
    }
    }
