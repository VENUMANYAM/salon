package com.salon.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String fromNumber;

    public void sendOtp(String toPhone, String otp) {
        Twilio.init(accountSid, authToken);

        Message.creator(
                new PhoneNumber(toPhone),
                new PhoneNumber(fromNumber),
                "Your OTP is: " + otp
        ).create();
    }
}
