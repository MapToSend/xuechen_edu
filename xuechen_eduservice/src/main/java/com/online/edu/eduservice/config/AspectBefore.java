package com.online.edu.eduservice.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectBefore {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //Before增强：在目标方法被执行的时候织入增强
    //匹配com.student.xl包下面的所有类的所有方法的执行作为切入点
    @Before("execution(* com.online.edu.eduservice.controller.*.*(..))")
    public void beforeWave(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getName();
        String[] split = className.split("\\.");
        String methodName = joinPoint.getSignature().getName();
        logger.info("进入" + split[split.length - 1] + "类的" + methodName + "方法");

        Object[] args = joinPoint.getArgs();

        String param = "请求参数:";
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                if (args[i]!=null){
                    String name = args[i].getClass().getName();
                    String[] split1 = name.split("\\.");
                    logger.info("请求参数:" + args[i] + ",参数类型:" + split1[split1.length - 1]);
                }

            }
        }
    }
}
