package com.zzvcom.sysmag.aspect;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 日志记录Aspect<br>
 * 记录Service执行情况
 * @author Wang Xiaoming
 */
@Aspect
public class LoggingAspect {
	private Log log = LogFactory.getLog(this.getClass());

	@Around("execution(* com.zzvcom.sysmag.service.*Service.*(..))")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("The method " + joinPoint.getSignature().getName()
				+ " begings with " + Arrays.toString(joinPoint.getArgs()));
		try {
			Object result = joinPoint.proceed();
			log.info("The method " + joinPoint.getSignature().getName()
					+ " ends with " + result);
			return result;
		} catch (Exception e) {
			StringWriter trace = new StringWriter();
			e.printStackTrace(new PrintWriter(trace));
			log.error(trace.toString());
			throw e;
		}
	}
}
