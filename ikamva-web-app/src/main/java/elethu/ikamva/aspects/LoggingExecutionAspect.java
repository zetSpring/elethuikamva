package elethu.ikamva.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
public class LoggingExecutionAspect {
    @Around("@annotation(ExecutionTime)")
    public Object methodTimeLogger(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        // Get intercepted method details
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getMethod().getName();

        // Measure method execution time
        StopWatch stopWatch = new StopWatch(className + " -> " + methodName);
        stopWatch.start(methodName);
        Object result = joinPoint.proceed();
        stopWatch.stop();

        log.info(stopWatch.prettyPrint());
        log.info("{} -> {} execution time in seconds: {} s", className, methodName, stopWatch.getTotalTimeSeconds());

        return result;
    }
}
