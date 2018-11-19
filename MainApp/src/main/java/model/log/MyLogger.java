package model.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class MyLogger {

    private static Logger logger = LoggerFactory.getLogger(MyLogger.class);
    private static final String ERROR = "Method {} with parameters : {}, of class {} throws {}";

    @Around("@annotation(ModelLog)")
    public Object debugLog(ProceedingJoinPoint pjp) throws Throwable {
        String method = pjp.getSignature().getName();
        logger.debug("It is in {} of class {}, method parameters: {}",
                method, pjp.getTarget().getClass().getSimpleName(),
                Arrays.toString(pjp.getArgs()));
        try {
            Object result = pjp.proceed();
            logger.debug("Method {} with parameters : {}, of class {} returns {}",
                    method, Arrays.toString(pjp.getArgs()),
                    pjp.getTarget().getClass().getSimpleName(), result);
            return result;
        } catch (Throwable e) {
            logger.info(ERROR, method, Arrays.toString(pjp.getArgs()),
                    e.getClass().getSimpleName(), e.getMessage());
            throw e;
        }
    }
}