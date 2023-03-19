package com.campus.backend.config;

import java.io.FileWriter;
import java.io.IOException;

public class AlipayConfig {
    // ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2021000122619733";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC57nmPdXi0vz8ItfpdA9WCo8dpgb/wGdjp4SoiN6vGyAXglfmv+wLgQnxnQ8niaGlJ6xMQG3v0a64hHLr3C2/46Mw38FHhFApJ1SeodDxEwRt65+jJ17xYk54D8WcCfaSEYzLnGmXK84RlaejUE51Yu82VH8a/di0hTqyNCbr/UkhJLDJpMTokHcGiZuEMen8255ozaiBZ0AukouHJB/Jz3q71T1Sz0XJZUZhWmwWgZn70wLqLlLAwk5WAZD3+VRlAbUakf97s02JXUpvt5kspgJ+/NTutQ8+koSpueLF1mWMINC3NyZI9Lx7bgUUfGI4EZ3PaYViQa45BY1htEWDJAgMBAAECggEBAJKysX6a9M3HRvicfBj/eWpFxoRcNxA9lIOTgCbXPdTs5JY6PnK23aILUxQ2zAay0r/blYLT7I0AmLbPcESS5ZnCM76/AhtQMH3ojA3BB/xHUGM8rtLyQEyyy63YvamdcDxz+CmXDlF+qStK0MTjZPYp1x6dn7ZYi2sII+RzlPL0oYAklE8buQWPm2w2qpa+07nLhpn2y5XkJvwa1DU3+cV16VlnrVRjkxbQ7kgHlRWI6rNAWXYLfEmWdVXgtSiuEPYvJtExVIlA1ykV5vS3FsRHsPVZFf1WapMTBFZIcFObplh8oCwekn+aJArLCIob1HMNu6jBHYvpBw8ljwUyqbECgYEA+Q6oCVYJ7Ij9YtHzbGrUFwcwtUkXO2xKnZ9RAUjPVstR26FbH27KvHKJ8HACx5X10ILLDGQU64TqUb2v1agrP6rwlWereVzH1fJ0X7GVqzshGv/D7J8i0l9YcW5tBduuLyP7APu0hmQynTxzdnErJRlNuydD8MTNn3fjQlt2TL8CgYEAvx1V24utc5UuqePoUnGPYhBjlDBXlHuIkv4fRwX+MVOD8IqgWLKiWVP584ugwJmM3FQ9RDak42Z5U2JFdAiY0hjSywUkN1QX0xVAsroTsR+r9Dd2KfiUpGy5i9JqGbBRD/KL3wxt+IxX9fFIXqAfXIbiRyxyeE5yLA7tqwl1THcCgYBjZkISV9BmItZeFkT8xDYZark54Q3wnWu6NNdzl8eIcGUfLurbRLCNPXloCIxTc7pDma/IJjljNgz+yqHet9tq8aJLPrDlIu5uqPFhL9L4IAU6bVGYonqXU45M1zGT4PVHwyhuYAbQjDTXJEVAxDHgAJVRWH55HVKjjomY9j/kqQKBgQCWxpYN90yIe5GtBtkq8dylCTkGmHz/79X34fTbG6dHUxQsmbGXSJc80jvIIMhaKsouqWcMge/kV9wwJL+Wo5Gtb1geQ5gm7nk9Vu8MEjGm8829hmP7CNp8PirEXOTID4pkgn5HIo9AH7jll834mWsknGW4sSA+OeVpTP3Eilm4UQKBgQCm8LMUDrduYE/awYZqP7o5VcCM+VawraBgHBfotAFRusbpCRE61C4YA/dVJNkR3gOgWvtI2ewVJisxJ1jKHGiI+Rpu3qKB6tsP+CXYnjBhiYm1F2YHHo9NZfj6NDSXrxxIucou8VSFUM6/khy9R9D5q6s7h4L4InqluCvVjAZgLw==\n";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAq31fZUNgZJBuhAE+LfrDHlETNKdvPxg0N3nWHaeyECIekWXZMfOUDTNxuPyOYfZ/JbKpVvdrWLIJF+6eM9PiwMnkkRCv5bsRUy66VjpVTnN++vEPdNe1CK23x4Z7jNDVIlUEcDxUwRXDFXk+HewLsHth2UbRmsoDZFMPAjFfmhU256TjTpxNXquRjunTf3yxmfE5SxNBjuoHI4gibfWznLBxIykljWLn5ZBeT+cf9aNHU/yIMJ9cPdgXRNXf+vEx1rycJCqbQ2jdDbO/BWqV4cGyvy/0Ld4W9i03BD/tL/cA79DeC5xNbxWgDVPFQ5gNdmFOlVU87zjaJ0crOo9l9wIDAQAB";


    // 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问

    // 这里需要配置支付宝回调的后端路径，必须要外网可以访问
    public static String notify_url = "http://ecampus.chat:8081/alipay/callBack";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问

    // TODO 这里需要配置支付宝回调的前端路径，必须要外网可以访问
    public static String return_url = "http://localhost:8080/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "UTF-8";

    public static String FORMAT="json";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 日志路径
    public static String log_path = "C:\\";

    // ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     *
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

