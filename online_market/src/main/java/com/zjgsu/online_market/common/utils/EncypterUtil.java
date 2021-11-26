package com.zjgsu.online_market.common.utils;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EncypterUtil {
    @Value("${xjj.secret}")
    private String secret;
    //    加密
    public String Encrypt(String password){
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(secret);
        return textEncryptor.encrypt(password);
    }
    //    解密
    public String Decrypt(String password){
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(secret);
        return textEncryptor.decrypt(password);
    }
}
