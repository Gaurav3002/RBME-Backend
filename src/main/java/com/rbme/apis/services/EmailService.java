package com.rbme.apis.services;

import com.rbme.apis.dto.admin.ProjectEnquiryRequest;
import com.rbme.apis.entity.ProjectEnquiry;

public interface EmailService {

    void sendEnquiryNotification(ProjectEnquiry enquiry);
}
