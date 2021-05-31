package com.server.annotation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.entity.CommonException;
import com.server.entity.LogMethod;
import com.server.utils.HttpClientUtils;
import com.server.utils.SnowFlake;
import com.server.utils.Utils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;

@Aspect
@Component
public class LogMethodRecordImpl {

    private StopWatch stopWatch = new StopWatch();

    @Pointcut("@annotation(com.server.annotation.LogMethodRecord)")
    public void countTimeImpl(){}

    @Before("countTimeImpl()")
    public void before(){
        stopWatch.start();
    }

    @After("countTimeImpl()")
    public void after(JoinPoint joinPoint){
        stopWatch.stop();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        String name = method.getName();
        LogMethodRecord annotation = method.getAnnotation(LogMethodRecord.class);
        String value = annotation.value();
        String uri = annotation.uri();
        String type = annotation.type();
        LogMethod logMethod = new LogMethod();
        logMethod.setId(SnowFlake.nextId());
        logMethod.setMethodName(name);
        logMethod.setMethodAnnotationName(value);
        logMethod.setCreateTime(Utils.getCurrentDate());
        logMethod.setUri(uri);
        logMethod.setMsecond(stopWatch.getTotalTimeMillis());
        logMethod.setSecond(stopWatch.getTotalTimeSeconds());
        Object[] args = joinPoint.getArgs();
        logMethod.setArgs(parseArgs(args));
        logMethod.setSystemName(type);
        logMethod.setRemark(null);
        sendMessage(logMethod);
    }

    private void sendMessage(LogMethod logMethod){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String str = objectMapper.writeValueAsString(logMethod);
           // HttpClientUtils.post_init("192.168.56.113", 8080, "/springbootelstaicsearch/logMethod/save", "logmethodStr", str);
            HttpClientUtils.post_init("192.168.56.113", 8080, "/provider/provider/sendEsStr", "str", str);
        } catch (JsonProcessingException e) {
            throw new CommonException("JSON发送失败   " + e.getMessage());
        }
   }


    private String parseArgs(Object[] args){
        StringBuffer arg = new StringBuffer();
        if(args != null && args.length > 0){
            for (int i = 0; i < args.length; i++) {
                arg.append(args[i]);
            }
        }
        return arg.toString();
    }

}
