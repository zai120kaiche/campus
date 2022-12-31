package com.campus.backend.tool;

import java.util.*;
import cn.hutool.http.*;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

public class openAIUtil {
    private static String prompt="";

    //用户新对话开始时清楚上一次对话的上下文
    //可以在对话开始前或对话结束后调用。
    public static void clear_context()
    {
        prompt="";
    }

    //参数为用户输入
    public static String interactive(String input)
    {
        String output="";
        prompt+="Human:"+input+"\n";

        Map<String,String> headers = new HashMap<String,String>();
        headers.put("Content-Type","application/json;charset=UTF-8");

        JSONObject json = new JSONObject();
        //选择模型
        json.set("model","text-davinci-003");
        //添加我们需要输入的内容
        json.set("prompt",prompt);
        json.set("temperature",0);
        json.set("max_tokens",2048);
        json.set("top_p",1);
        json.set("frequency_penalty",0.0);
        json.set("presence_penalty",0.6);

        HttpResponse response = HttpRequest.post("https://api.openai.com/v1/completions")
                .headerMap(headers, false)
                .bearerAuth("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")
                .body(String.valueOf(json))
                .timeout(5 * 60 * 1000)
                .execute();

        if(response.isOk())
        {
            JSONObject body_json = new JSONObject(response.body().toString());
            JSONArray choice_json=new JSONArray(body_json.get("choices"));
            JSONObject text=(JSONObject)choice_json.get(0);
            String text_str=(String) text.get("text");
            //以下操作是为了消除回答中的开头内容。
            String[] split=text_str.split(":");
            if(split.length>1)
            {
                String text_str_temp="";
                for(int i=1;i<split.length;i++)
                {
                    if(i>1) text_str_temp+=(":"+split[i]);
                    else text_str_temp+=split[i];
                }
                output=text_str_temp;
            }
            else {
                output=text_str;
            }

            prompt+=text.get("text")+"\n";

            return output;
        }
        else
        {
            return "网络错误！";
        }
    }

    public static void main(String[] args) {
        while(true)
        {
            Scanner in=new Scanner(System.in);
            System.out.println(interactive(in.next()));
        }
    }

}
