package com.rbme.apis.dto.CompanySettings;

import com.rbme.apis.entity.CompanySettings.DocumentSettings;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DocumentSettingsRequestDTO {

    private DocumentSettings.DocumentType documentType;

    private String headerText;

    private String footerText;

    private String termAndCondition;

    private List<DocumentSettings.PaymentInstruction> instructions = new ArrayList<>();
}
