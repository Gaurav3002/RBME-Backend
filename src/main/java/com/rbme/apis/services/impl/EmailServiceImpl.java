package com.rbme.apis.services.impl;

import com.rbme.apis.entity.ProjectEnquiry;
import com.rbme.apis.services.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${enquiry.mail.to}")
    private String enquiryMailTo;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEnquiryNotification(ProjectEnquiry enquiry) {

        SimpleMailMessage mail = new SimpleMailMessage();

        // Every enquiry will be sent to this email
        mail.setTo(enquiryMailTo);

        mail.setSubject("New Project Enquiry");

        mail.setText(
                "New Project Enquiry Received\n\n" +

                        "Name: " + enquiry.getName() + "\n" +
                        "Business Name: " + enquiry.getBusinessName() + "\n" +
                        "Email: " + enquiry.getEmail() + "\n" +
                        "Phone: " + enquiry.getPhone() + "\n" +
                        "City: " + enquiry.getCity() + "\n" +
                        "State: " + enquiry.getState() + "\n\n" +

                        "Category: " + enquiry.getCategory() + "\n" +
                        "Machine: " + enquiry.getMachine() + "\n" +
                        "Project Type: " + enquiry.getProjectType() + "\n" +
                        "Capacity: " + enquiry.getCapacity() + "\n" +
                        "Product: " + enquiry.getProduct() + "\n" +
                        "Automation: " + enquiry.getAutomation() + "\n" +
                        "Budget: " + enquiry.getBudget() + "\n\n" +

                        "Message:\n" +
                        enquiry.getMessage()
        );

        mailSender.send(mail);
    }
}