package com.campus.backend.tool;

import org.apache.commons.mail.HtmlEmail;

import java.util.Random;

public class EmailUtil {

    private static String str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    //参数：邮箱地址，验证码。
    public static boolean send_email(String address,String code)
    {
        try {
            HtmlEmail email=new HtmlEmail();
            email.setHostName("smtp.163.com");
            email.setCharset("UTF-8");
            email.addTo(address);

            email.setFrom("campusofficial@163.com","campusOfficial");
            email.setAuthentication("campusofficial@163.com","UMQIMPCLHFTVJUUS");
            email.setSubject("【欢迎注册使用】");
            email.setMsg("您的验证码是："+code+"，如非本人操作请忽略该邮件。");
            email.send();

            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    //生成六位验证码
    public static String get_code_6bit()
    {
        String code="";
        Random ran = new Random();
        for (int i=0; i<6; i++)
        {
            int j = ran.nextInt(str.length());
            code+=str.charAt(j);
        }
        return code;
    }

    public static void main(String[] args) {
        //先在本地生成验证码，然后通过邮箱发送，再从前端获取用户输入的验证码与本地生成的验证码做校验
        String code=get_code_6bit();
        send_email("2570153857@qq.com",code);
    }
}
