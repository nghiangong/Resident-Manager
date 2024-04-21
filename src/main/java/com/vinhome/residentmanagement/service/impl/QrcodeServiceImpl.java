package com.vinhome.residentmanagement.service.impl;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.vinhome.residentmanagement.dtos.QrCodeInformation;
import com.vinhome.residentmanagement.dtos.SignatureInformation;
import com.vinhome.residentmanagement.entity.Role;
import com.vinhome.residentmanagement.entity.User;
import com.vinhome.residentmanagement.exception.EntityNotFoundException;
import com.vinhome.residentmanagement.repository.UserRepository;
import com.vinhome.residentmanagement.response.MessageResponse;
import com.vinhome.residentmanagement.service.QrCodeService;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class QrcodeServiceImpl implements QrCodeService {
    private UserRepository userRepository;

    public QrcodeServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public QrCodeInformation createQrcodeInformation(SignatureInformation signatureInformation) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Long userId = signatureInformation.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(User.class, "id", userId.toString()));
        ByteBuffer b = ByteBuffer.allocate(4);
//b.order(ByteOrder.BIG_ENDIAN); // optional, the initial order of a byte buffer is always BIG_ENDIAN.
        b.putInt(signatureInformation.hashCode());
        byte[] result = b.array();
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(user.getPrivateKey());
        privateSignature.update(result);
        byte[] signature = privateSignature.sign();
        QrCodeInformation qrCodeInformation = new QrCodeInformation(signatureInformation, Base64.getEncoder().encodeToString(signature));
        return qrCodeInformation;
    }

    @Override
    public MessageResponse verifyQrcode(QrCodeInformation qrCodeInformation) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Long userId = qrCodeInformation.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(User.class, "id", userId.toString()));
        SignatureInformation signatureInformation = new SignatureInformation(qrCodeInformation);
        ByteBuffer b = ByteBuffer.allocate(4);
//b.order(ByteOrder.BIG_ENDIAN); // optional, the initial order of a byte buffer is always BIG_ENDIAN.
        b.putInt(signatureInformation.hashCode());
        byte[] result = b.array();
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(user.getPublicKey());
        publicSignature.update(result);
        byte[] signatureBytes = Base64.getDecoder().decode(qrCodeInformation.getDigitalSignature());
        if(publicSignature.verify(signatureBytes)){
            if(qrCodeInformation.getExpireDate().isAfter(LocalDateTime.now())){
                return new MessageResponse("Qr code hợp lệ");
            }else{
                throw new RuntimeException("Qr code đã hết hạn");
            }
        }else {
            throw new RuntimeException("Qr code không hợp lệ");
        }
    }
}
