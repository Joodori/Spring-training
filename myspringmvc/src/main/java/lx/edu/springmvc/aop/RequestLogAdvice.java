package lx.edu.springmvc.aop;

import java.time.LocalDateTime;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import lx.edu.springmvc.dao.RequestLogDAO;
import lx.edu.springmvc.vo.RequestLogVO;

@Aspect
@Component
@EnableAspectJAutoProxy
public class RequestLogAdvice {

	@Autowired
	RequestLogDAO rdao;
	
	@Before("execution(* lx.edu.springmvc.controller.Addr*.*(..))")
	public void beforeLog(JoinPoint joinPoint) throws Exception {
		System.out.println("LogAdvice.beforeLog()");
		Class targetClass = joinPoint.getTarget().getClass();
		String methodName = joinPoint.getSignature().getName();
		
		
		// 로그에대한 객체를 만듦 (계속 생성되고 사라지는 부분)
		RequestLogVO rvo = new RequestLogVO();

		// 그에 들어가는 속성들을 setter로 집어넣음
		// Class type은 toString(VO가 String으로 되어있어), time은 LocalDateTime을 이용해
		rvo.setReq_class(targetClass.getName());
		rvo.setReq_method(methodName);
		rvo.setReq_time(LocalDateTime.now().toString());
		
		// DB로 집어넣어 오토와이어로 바로 불러올 수 있어
		rdao.insertLogDB(rvo);
		
		// 이건 console 확인용으로 할게
		System.out.printf("class=%s, method=%s\n",targetClass.getName(), methodName);
	}
	
}
