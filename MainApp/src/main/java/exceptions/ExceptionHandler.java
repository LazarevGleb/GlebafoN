package exceptions;

import model.dto.ResponseDto;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);
    @Around("@annotation(ControllerException)")
    @Order(1)
    public Object handleException(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } catch (BusinessLogicException e) {
            logger.info("Exception occurs: {}", e);
            return new ResponseDto(e.getCode(), e.getCause().getMessage(),
                    e.getClass().getSimpleName() + ": " + e.getMessage());
        } catch (Exception e) {
            logger.info("Exception occurs: {}", e);
            return new ResponseDto(500, "Ups.. Something went wrong :(",
                    e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}
