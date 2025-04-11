package com.example.ecommerce.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class DatabaseManagementAspect {

    @Around("execution(* com.example.ecommerce.repository.*.*(..))" )
    public Object monitorDatabasePerformance(ProceedingJoinPoint joinPoint) throws Throwable{
        String methodName= joinPoint.getSignature().toShortString();
        long start=System.nanoTime();
        try{
            return joinPoint.proceed();
        }
        finally{
            long end=System.nanoTime();
            long total_runtime=(end-start)/1000000;
            log.info("Operation [{}] completed in {} ms", methodName, total_runtime);
            if(total_runtime> 1000){
                log.warn("Attention needed slow database operation detected: [{}] takes {} ms", methodName,total_runtime);
            }

        }

    }
}
