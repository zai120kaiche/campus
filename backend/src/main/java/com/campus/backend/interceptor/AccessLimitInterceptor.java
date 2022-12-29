package com.campus.backend.interceptor;

import com.alibaba.fastjson.JSON;
import com.campus.backend.annotation.AccessLimit;
import com.campus.backend.redis.RedisOperator;
import com.campus.backend.tool.GetIpAddressUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Component
public class AccessLimitInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisOperator redisOperator;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //判断请求是否属于方法的请求
        if (handler instanceof HandlerMethod) {

            HandlerMethod hm = (HandlerMethod) handler;

            //获取方法中的注解,看是否有该注解
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null) {
                return true;
            }
            int seconds = accessLimit.seconds();

            int days = accessLimit.days();
            int maxperCount = accessLimit.perCount();
            int maxdayCount = accessLimit.dayCount();
            boolean login = accessLimit.needLogin();
            String url = request.getRequestURI();
            String ip = GetIpAddressUtil.getIpInfo(request);
            //如果需要登录(未完成)
            if (login) {
                //.....
            }
            //从redis中获取用户访问的次数
            String perKey = ip + url + "perLimit";
            String dayKey = ip + url + "dayLimit";
            String value = redisOperator.get(perKey);
            String dayValue = redisOperator.get(dayKey);
            if (value == null){

                redisOperator.set(perKey, "1", seconds);
                if(dayValue == null) {

                    redisOperator.set(dayKey,"1",days);
                } else {
                    redisOperator.incr(dayKey,1);
                    dayValue = redisOperator.get(dayKey);

                    if(Integer.valueOf(dayValue) > days) {
                        //超出访问次数
                        Map<String,Object> errMap=new HashMap<>();
                        errMap.put("code",400);
                        errMap.put("msg","请求超时，请稍后再试");
                        render(response, errMap); //这里的CodeMsg是一个返回参数
                        return false;
                    }
                }
                return true;
            }else {
                int tempper = Integer.valueOf(redisOperator.get(perKey));
                int tempday = Integer.valueOf(redisOperator.get(dayKey));
                if(tempper >= maxperCount || tempday >= maxdayCount) {
                    //超出访问次数
                    Map<String,Object> errMap=new HashMap<>();
                    errMap.put("code",400);
                    errMap.put("msg","请求超时，请稍后再试");
                    render(response, errMap); //这里的CodeMsg是一个返回参数
                    return false;
                } else {
                    redisOperator.incr(perKey, 1);
                    redisOperator.incr(dayKey,1);
                    return true;
                }
            }


        }

        return true;

    }

    private void render(HttpServletResponse response, Map<String,Object> errMap) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String str = JSON.toJSONString(errMap);
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }
}
