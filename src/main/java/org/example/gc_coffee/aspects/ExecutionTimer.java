package org.example.gc_coffee.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Component
@Aspect
public class ExecutionTimer {

    /*
     * @Arund 사용 -> 메서드 실행 전, 후로 시간을 공유해야 하기 때문
     * * org.example.gc_coffee.controller.*.*(..) -> controller 패키지 내의 모든 클래스 및 함수 실행 시 동작
     */
    @Around("execution(* org.example.gc_coffee.controller.*.*(..))")
    public Object PackageExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        Object result = joinPoint.proceed();
        stopWatch.stop();

        long totalTimeMillis = stopWatch.getTotalTimeMillis();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();

        log.info("실행 메서드: {}, 실행시간 = {}ms", methodName, totalTimeMillis);

        return result;
    }



    /*
     * 조인포인트를 어노테이션으로 설정
     * 시간을 테스트할 함수에 @ExeTimer 작성
     */
//    @Pointcut("@annotation(org.example.gc_coffee.aspects.ExeTimer)")
//    private void timer(){};
//
//    // 메서드 실행 전,후로 시간을 공유해야 하기 때문
//    @Around("timer()")
//    public Object UnitExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
//
//        StopWatch stopWatch = new StopWatch();
//
//        stopWatch.start();
//        Object result = joinPoint.proceed(); // 조인포인트의 메서드 실행
//        stopWatch.stop();
//
//        long totalTimeMillis = stopWatch.getTotalTimeMillis();
//
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        String methodName = signature.getMethod().getName();
//
//        log.info("실행 메서드: {}, 실행시간 = {}ms", methodName, totalTimeMillis);
//
//        return result;
//    }
}
