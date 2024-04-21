package com.vinhome.residentmanagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QrCodeInformation extends SignatureInformation{
    private String digitalSignature;
    public QrCodeInformation(SignatureInformation signatureInformation, String digitalSignature){
        super(signatureInformation);
        this.digitalSignature = digitalSignature;
    }
}
