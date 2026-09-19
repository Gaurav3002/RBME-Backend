package com.rbme.apis.entity.CompanySettings;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "document_settings")
public class DocumentSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @Column(columnDefinition = "TEXT")
    private String headerText;

    @Column(columnDefinition = "TEXT")
    private String footerText;

    @Column(columnDefinition = "TEXT")
    private String termAndCondition;

    @ElementCollection
    @CollectionTable(
            name = "payment_instructions",
            joinColumns = @JoinColumn(name = "document_settings_id")
    )
    private List<PaymentInstruction> instructions = new ArrayList<>();


    @Getter
    @Setter
    @Embeddable
    public static class PaymentInstruction {

        @Column(columnDefinition = "TEXT")
        private String paymentInstructions;
    }

    public enum DocumentType {
        QUOTATION,
        INVOICE
    }
}
