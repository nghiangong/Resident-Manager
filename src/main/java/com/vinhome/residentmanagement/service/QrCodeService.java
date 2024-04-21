package com.vinhome.residentmanagement.service;

import com.vinhome.residentmanagement.dtos.QrCodeInformation;
import com.vinhome.residentmanagement.dtos.SignatureInformation;
import com.vinhome.residentmanagement.response.MessageResponse;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public interface QrCodeService {
    QrCodeInformation createQrcodeInformation(SignatureInformation signatureInformation) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException;
    MessageResponse verifyQrcode(QrCodeInformation qrCodeInformation) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException;

}
