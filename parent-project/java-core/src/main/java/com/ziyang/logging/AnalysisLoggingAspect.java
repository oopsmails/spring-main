package com.ziyang.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;


@Component
@Aspect
public class AnalysisLoggingAspect {
    // flag to indicate that scoped RequestHolder bean is available
    // this might not always be the case in environments where no request scope exists
    private final AtomicBoolean requestHolderAvailable = new AtomicBoolean(true);
    Logger logger = LoggerFactory.getLogger(AnalysisLoggingAspect.class);
    @Autowired
    private ApplicationContext applicationContext;

    @Around("execution(@com.ziyang.logging.AnalysisLogging * *(..))"
            + "|| execution(* (@com.ziyang.logging.AnalysisLogging *).*(..))")
    public Object traceLog(ProceedingJoinPoint joinPoint) throws Throwable {
        String type = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        if (logger.isDebugEnabled()) {
            StringBuilder sb = new StringBuilder("[");
            boolean first = true;
            for (Object arg : joinPoint.getArgs()) {
                if (!first) {
                    sb.append(", ");
                } else {
                    first = false;
                }

                if (arg == null) {
                    sb.append("null");
                } else {
//					if(arg.getClass().getAnnotation(XmlType.class) != null) {
//						try {
//							sb.append(JaxbUtil.toXml(arg));
//						} catch(Throwable ex) {
//							sb.append(arg.toString());
//						}
//					} else {
//						sb.append(arg.toString());
//					}
                    sb.append(arg.toString());

                }
            }
            sb.append("]");

            logger.debug(">>> {}.{} args: {}", type, methodName, sb.toString());
        } else {
            logger.info(">>> {}.{}", type, methodName);
        }

        long startTime = System.nanoTime();
        Object res = null;
        try {
            res = joinPoint.proceed();
            return res;
        } finally {
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000000;

            if (logger.isDebugEnabled() && res instanceof Collection<?>) {
                logger.info("<<< {}.{} returned {} rows / {} ms", type, methodName, ((Collection<?>) res).size(), duration);
//			} else if(logger.isDebugEnabled() && res instanceof SearchResult) {
//				logger.info("<<< {}.{} returned {} rows / {} ms", type, methodName, ((SearchResult<?>) res).getResult().size(), duration);
            } else {
                logger.info("<<< {}.{} / {} ms", type, methodName, duration);
            }
        }
    }


}
