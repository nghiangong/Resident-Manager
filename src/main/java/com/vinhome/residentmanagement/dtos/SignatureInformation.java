package com.vinhome.residentmanagement.dtos;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignatureInformation {
    Long userId;
    String name;
    String note;
    LocalDateTime expireDate;
    String licensePlate;
    boolean gender;
    boolean isResident;
    public SignatureInformation(SignatureInformation signatureInformation) {
        this.userId = signatureInformation.userId;
        this.name = signatureInformation.name;
        this.expireDate = signatureInformation.expireDate;
        this.gender = signatureInformation.gender;
        this.isResident = signatureInformation.isResident;
        this.licensePlate = signatureInformation.licensePlate;
        this.note = signatureInformation.note;
    }

    public SignatureInformation(QrCodeInformation qrCodeInformation){
        this.userId = qrCodeInformation.userId;
        this.name = qrCodeInformation.name;
        this.expireDate = qrCodeInformation.expireDate;
        this.gender = qrCodeInformation.gender;
        this.isResident = qrCodeInformation.isResident;
        this.licensePlate = qrCodeInformation.licensePlate;
        this.note = qrCodeInformation.note;
    }
}
