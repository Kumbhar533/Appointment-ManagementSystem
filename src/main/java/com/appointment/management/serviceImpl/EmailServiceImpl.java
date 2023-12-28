package com.appointment.management.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.appointment.management.dto.PasswordDto;
import com.appointment.management.entities.OtpEntity;
import com.appointment.management.entities.UserEntity;
import com.appointment.management.repositories.OtpRepository;
import com.appointment.management.repositories.UserRepository;
import com.appointment.management.serviceIntf.EmailServiceInterface;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailServiceInterface {

	@Value("${spring.mail.username}")
	private String sender;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private OtpRepository otpRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void sendMail(String emailTo, String subject) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(sender);
		helper.setTo(emailTo);
		helper.setSubject(subject);
		int otp = generateOtp(emailTo);
		String otp1 = String.valueOf(otp);
		helper.setText(otp1);
		javaMailSender.send(message);

	}

	@Override
	public int generateOtp(String email) {

		int min = 100000;
		int max = 999999;

		int randomInt = (int) Math.floor(Math.random() * (max - min + 1) + min);

		OtpEntity otpEntity = new OtpEntity();
		String otp1 = String.valueOf(randomInt);
		otpEntity.setOtp(otp1);
		otpEntity.setEmail(email);

		Date expirationTime = new Date(System.currentTimeMillis() + 2 * 60 * 1000);
		otpEntity.setExpiredAt(expirationTime);
		otpRepository.save(otpEntity);
		return randomInt;
	}

	@Scheduled(fixedRate = 120000)
	public void cleanupExpiredOtps() {
		long currentTime = System.currentTimeMillis();

		Date expirationTime = new Date(currentTime - 120000); // 2 minutes ago

		List<OtpEntity> expiredOtps = otpRepository.findByExpiredAtBefore(expirationTime);
		otpRepository.deleteAll(expiredOtps);

	}

	@Override
	public void setUserPassword(PasswordDto passwordDto) {

		UserEntity userEntity = this.userRepository.findByEmailIgnoreCaseAndIsActiveTrue(passwordDto.getEmail());

		OtpEntity otpEntity = this.otpRepository.findByEmailAndOtp(passwordDto.getEmail(), passwordDto.getOtp());

		if (otpEntity != null) {

			userEntity.setPassword(passwordEncoder.encode(passwordDto.getPassword()));

			userRepository.save(userEntity);
		}
	}

}
