## 목차
*   [의존성 주입](#의존성-주입)
*   [Bean 이란?](#Bean-이란)
*   [인터페이스?](#인터페이스)
*   [Bean태그 ref?](#Bean-태그-ref)
*   [Spring MVC?](#Spring-MVC-관계)
*   [Log 찍기](#Log-찍기)

---
## 의존성 주입
***
의존성 주입 부분은 <https://lincoding.tistory.com/76> 를 참고했어요

***


### 강한 결합

우리가 Spring을 알기 전 까지 계속 써오던 방식 -> 생성할 때 마다 우리 손으로 구현을 해주어야함 (아이귀찮아)
```java
public class B {
    public void doSomething() {
        System.out.println("Doing something in B");
    }
}

public class A {
    private B b;

    public A() {
        this.b = new B();  // 강한 결합
    }

    public void performAction() {
        b.doSomething();
    }

    public static void main(String[] args) {
        A a = new A();
        a.performAction();
    }
}
```
***
### 느슨한 결합

Spring을 알기 시작했으니 뭔가 정갈하게 하기위해 "의존성 주입"이라는 것을 명심하며 아래 코드를 관찰해보자
```java
// 인터페이스 정의
public interface Service {
    void doSomething();
}

// 인터페이스 구현체 1
public class ServiceImpl implements Service {
    @Override
    public void doSomething() {
        System.out.println("Doing something in ServiceImpl");
    }
}

// 인터페이스 구현체 2
public class AnotherServiceImpl implements Service {
    @Override
    public void doSomething() {
        System.out.println("Doing something in AnotherServiceImpl");
    }
}

// 의존성 주입을 통해 느슨한 결합 구현
public class A {
    private final Service service;

    // 생성자 주입
    public A(Service service) {
        this.service = service;
    }

    public void performAction() {
        service.doSomething();
    }

    public static void main(String[] args) {
        // ServiceImpl 주입
        Service service1 = new ServiceImpl();
        A a1 = new A(service1);
        a1.performAction();

        // AnotherServiceImpl 주입
        Service service2 = new AnotherServiceImpl();
        A a2 = new A(service2);
        a2.performAction();
    }
}
```
***
### Case 1) 강한 결합일 때
***
Class A는 너무 불쌍해요 B가 마음만 먹고 삐뚤어지면 A도 같이 죽게되는 상황이에요

### Case 2) 느슨한 결합일 때
***
Class A에게 해를 끼칠 사람은 개발자밖에 없어요. 만약 우리가 A클래스에 자동적으로 의존성 주입을 하게되면

개발자 자신도 좋고 코드 유지보수도 편할거에요

### 추가설명
***
일단 지금 우리가 main메서드 안에서 A a1 = new A(Service1) 또는 A a2 = new A(service2)라는 부분은 수동으로 넣어준 것이기 때문에 

나는 처음에 이게 뭔 의존성 주입이지? 우리가 항상 해오던 저 위에 강한 결합 부분에 있는 this.b = new B(); 이것과 뭐가 다른거지?

했었는데 이게 나중에는 bean쪽으로 넘어가면 


***
Perplexity said
```
강한 결합: A는 B 없이는 독립적으로 움직일 수 없고, B의 변화에 쉽게 휘둘리는 ‘의존적 존재’

느슨한 결합: A는 추상화된 Service를 바라보고, 다양한 구현체를 자유롭게 교체할 수 있는 ‘유연한 존재’
```
---

## Bean 이란
---


### spring-context.xml
```java
	<bean id="service" class="myjdbc.EmpServiceImpl">
		<property name="DAO" ref="empDAO"></property>
	</bean>
	<bean id="empDAO" class="myjdbc.EmpDAO"></bean>
```
---
나는 이 부분을 공부할 때 bean이 대체 어떤 기능을 가진건지 잘 모르겠었는데
일단 service라는 이름으로 꺼낼 수 있는 기능이 있나보다 -> bean id = "service"

---
### EmpMainSpring.java
```java
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
		EmpService service = (EmpService) context.getBean("service");
		List<Emp> empList = service.getEmpListByDeptNo(deptNo);
		for (Emp emp : empList) {
			System.out.println(emp);
		}
```
---
ApplicationContext라는 Interface에서부터 시작된 context를 통해 spring-context.xml에서 beans를 읽어볼게
거기서 아까 봤던 service라는 id를 끌어와서 변수명을 service로 할게
service에대한 자료형은 EmpService로 할건데 EmpService는 Interface인데 그것을 상속받는 친구는 EmpServiceImpl이야

그렇다면 여기서 service에 대한 자료형은 ServiceImpl이 되어야 한다는 것을 알아야해
바로 몰라도 다음 줄인 getEmpListByDeptNo를 보고 알 수 있어야해

---
### EmpServieceImpl.java
```java
public class EmpServiceImpl implements EmpService {

	EmpDAO dao;
	
	@Override
	public void setDAO(EmpDAO dao) {
		// TODO Auto-generated method stub
		this.dao = dao;
	}

	@Override
	public List<Emp> getEmpListByDeptNo(int deptNo) throws Exception {
		// TODO Auto-generated method stub
		return dao.getEmpListByDeptNo(deptNo);
	}

}
```
---
EmpServiceImpl을 뜯어보니 여기서 쓸 수 있기 때문에
우리는 service라는 id로 끌어온 bean에 대한 자료형이 EmpServiceImpl이라는 것을 알 수 있었구나
또한 여기서 의존성 주입개념이 있는 것 같은데 거기까지는 잘 모르겠어서 다음에 와서 또 해야함

---
## 인터페이스

```java
//		EmpService service = (EmpService) context.getBean("empServiceImpl");
//		==
		EmpService service = (EmpService) context.getBean(EmpService.class);
		List<Emp> empList = service.getEmpListByDeptNo(deptNo);
```
---
이 부분에서 service라는 객체는 인터페이스를 받고있는데 getEmpListByDeptNo를 어떻게 쓰는지

엄청 명확하게 설명하지 못하는거 같아서 자세하게 찾아봤다.

일단 EmpService(I)는 메서드가 "선언"만 되어있는 상태이고 그것들을 구현하는 장소는 EmpServiceImpl(C)이다.

근데 어떻게 EmpService라는 인터페이스로 바로 쓸 수 있지? 라는 생각이 들었다. 

Java의 다형성 때문에 인터페이스를 imnplements받아서 메서드를 구현할 때 @override라는 것들을 봤을텐데

이것이 바로 다형성(polymorphism)이라는 것이다. 그렇기 때문에 우리는 재정의된 메서드를 사용할 수 있었던 것이다.

```
Q) service라는 객체는 getEmpListByDeptNo를 쓸 수 있나요?

A) EmpService라는 인터페이스를 EmpServiceImpl에서 구현하고있기 때문입니다 !
```
 


---
## Bean 태그 ref?
### xml파일에 작성하는 Beans 태그 안에 있는 Bean태그 내부에 있는 property에 ref는 뭐지?
```java
// A 클래스 (단순 속성들)

class A {
    private int age;
    private String name;
    
    // setter 메서드들
    public void setAge(int age) { this.age = age; }
    public void setName(String name) { this.name = name; }
}

// B 클래스 (A 객체를 포함)
class B {
    private A a;           // 다른 객체를 참조
    private String address; // 단순 값
    
    // setter 메서드들
    public void setA(A a) { this.a = a; }                    // 객체 주입
    public void setAddress(String address) { this.address = address; } // 값 주입
}
```
---
```java
<!-- A 객체에 해당 (dataSource 빈) -->
<bean id="dataSource" class="org.apache.commons.dbcp2.BasicDataSource">
    <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/> <!-- 단순 값 -->
    <property name="username" value="root"/>                              <!-- 단순 값 -->
</bean>

<!-- B 객체에 해당 (sqlSessionFactory 빈) -->
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
    <property name="dataSource" ref="dataSource"/>          <!-- 다른 객체 참조 (A a) -->
    <property name="mapperLocations" value="classpath:..."/> <!-- 단순 값 (String address) -->
</bean>
```
<property name="dataSource" ref="dataSource"/>  <!-- ← 여기서 "주입" 발생 -->
b.setA(a);

이 부분이 같은데 b.setA(new A())로 하지 않았기 때문에 의존주입이 되는 부분임 아래 내용은 추가적으로 확인


스프링 컨테이너가 관리하는 생명주기
빈 정의(Bean Definition) 읽기
XML이나 @Configuration 클래스에서 빈 설정 정보를 읽고, 의존관계 그래프를 구성합니다.

의존관계 분석
B 빈이 A 빈을 필요로 한다는 사실(ref="a", setA 메서드 등)을 확인합니다.

의존관계 순서에 따라 빈 생성

A 빈을 먼저 생성 및 초기화

그 다음에 B 빈을 생성하되, 생성 시점에 이미 만들어진 A 빈을 주입

이 과정을 자동으로 처리하기 때문에, 개발자는 빈 생성 순서를 신경 쓸 필요가 없습니다.
// 느슨한 결합을 해서 좋은 점은 무언가가 많이 연결되어있지 않기 때문에 어떤 부분을 수정을 해도 전체가 망가지거나 그러지는 않음

---
### @Autowired가 하는 역할은 뭘까?
ref도 의존주입을 해주는 것 => Autowired도 new를 쓰지 않아도 되기때문에 사용하는 것으로 알고있는데 

그러면 Autowired는 하는게 뭐야? 다음 예시 코드를 보자
```java
@Component
public class AddrBookDAO {

	@Autowired
	SqlSession session;
	
	public int insertDB(AddrBookVO ab) throws Exception {
		return session.insert("insertDB",ab);
	}

	public List<AddrBookVO> getDBList() throws Exception {
		return session.selectList("getDBList");
	}
}	
```
여기서도 SqlSession이라는 클래스에 존재하는 함수를 호출해주는 것을 볼 수 있다.

Autowired로 되어있는 것 또한 ref와 동일한 역할을 하는건지 궁굼해서 찾아본 결과는 다음과 같다.
```
네, 맞습니다. @Autowired와 XML의 ref 속성 둘 다 Spring의 의존성 주입(Dependency Injection) 메커니즘을 사용합니다.
다만, 설정 방식(@Autowired는 애노테이션 기반, ref는 XML 기반)과 매칭 방식(byType vs byName)에 차이가 있을 뿐,
궁극적으로는 컨테이너가 필요한 빈을 주입해 주는 동일한 역할을 수행합니다.
```
- @Autowired: byType (타입으로 매칭) - Spring이 자동으로 같은 타입의 빈을 찾아서 주입

- XML ref: byName (ID/이름으로 매칭) - 개발자가 명시적으로 지정한 빈의 ID를 참조

## Spring MVC 관계
---

Spring MVC 관계

네, Spring MVC의 동작 흐름을 아주 기초적인 단계부터 차근차근 설명해드리겠습니다!

🎯 Spring MVC 동작 흐름 - 기초부터 차근차근
1단계: 클라이언트의 요청 (Request)
사용자가 웹 브라우저에서 URL을 입력하고 엔터를 치는 순간

예: http://localhost:8080/myapp/hello.do

브라우저가 서버로 HTTP 요청을 보냅니다

2단계: DispatcherServlet이 요청을 받음
"모든 요청의 첫 번째 관문"

DispatcherServlet은 프론트 컨트롤러(Front Controller) 역할

마치 "접수처 직원"과 같아서, 모든 요청을 가장 먼저 받습니다

web.xml에서 설정한 URL 패턴에 해당하는 요청만 받습니다

java
// 이렇게 생각해보세요
"안녕하세요, 무엇을 도와드릴까요?" - DispatcherServlet
3단계: HandlerMapping이 담당 컨트롤러를 찾음
"어느 부서로 가야 할지 안내해주는 안내데스크"

/hello.do 요청이 왔으니, 이를 처리할 컨트롤러를 찾습니다

@RequestMapping("/hello.do")가 붙은 컨트롤러 메서드를 찾아줍니다

java
"'/hello.do' 요청은 HelloController의 hello() 메서드가 처리합니다!" - HandlerMapping
4단계: HandlerAdapter가 컨트롤러를 실행
"실제 업무를 수행하는 부서와의 연결고리"

찾은 컨트롤러를 실제로 실행시켜줍니다

다양한 종류의 컨트롤러들을 통일된 방식으로 호출할 수 있게 해줍니다

5단계: Controller(컨트롤러)가 비즈니스 로직 처리
"실제 일을 하는 담당 부서"
```java
java
@Controller
public class HelloController {
    @RequestMapping("/hello.do")
    public String hello() {
        // 여기서 실제 업무 처리
        // 예: 데이터베이스 조회, 계산 등
        return "hello"; // 뷰 이름 반환
    }
}
```
6단계: Controller가 결과를 DispatcherServlet에 반환
"처리 결과 보고"

컨트롤러가 처리한 결과와 보여줄 화면 정보를 반환합니다

보통 ModelAndView 객체 형태로 반환됩니다

Model: 화면에 보여줄 데이터

View: 어떤 화면(JSP 파일)을 보여줄지

7단계: ViewResolver가 실제 View 파일을 찾음
"화면 파일을 찾아주는 도서관 사서"

컨트롤러가 반환한 뷰 이름("hello")을 실제 파일 경로로 변환

예: "hello" → /WEB-INF/views/hello.jsp
```java
xml
<!-- servlet-context.xml 설정 -->
<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
    <property name="prefix" value="/WEB-INF/views/"/>
    <property name="suffix" value=".jsp"/>
</bean>
```
8단계: View가 최종 HTML을 생성
"실제 화면을 그려주는 디자이너"

JSP 파일이 실행되어 최종 HTML 페이지를 만듭니다

컨트롤러에서 전달받은 데이터를 HTML에 채워 넣습니다

9단계: 최종 응답을 클라이언트에게 전달 (Response)
"완성된 웹페이지를 사용자에게 보여줌"

생성된 HTML이 사용자의 웹 브라우저로 전송됩니다

사용자는 최종 완성된 웹페이지를 볼 수 있습니다

🏢 쉬운 비유로 이해하기
Spring MVC = 대형 회사의 업무 처리 시스템

고객(Client): "안녕하세요, 계좌 조회 부탁드립니다"

접수처(DispatcherServlet): "네, 접수했습니다"

안내데스크(HandlerMapping): "계좌 조회는 2층 은행업무팀으로 가세요"

연결담당자(HandlerAdapter): "은행업무팀에 연결해드릴게요"

실무담당자(Controller): "계좌 조회 완료! 결과는 화면으로 보여드리겠습니다"

화면담당자(ViewResolver): "고객용 화면 템플릿을 준비하겠습니다"

최종화면(View): "여기 계좌 조회 결과입니다!"

고객: "감사합니다!" (완성된 웹페이지 확인)

⭐ 핵심 포인트
DispatcherServlet: 모든 요청의 중앙 관제소 역할

분업: 각 컴포넌트가 명확한 역할을 가지고 협력

유연성: 컨트롤러, 뷰 등을 독립적으로 개발 가능

재사용성: 한 번 만든 컴포넌트를 여러 곳에서 활용 가능

이렇게 Spring MVC는 체계적이고 단계적인 요청 처리를 통해 복잡한 웹 애플리케이션을 효율적으로 관리할 수 있게 해줍니다!



## Log 찍기
---
아니 나는 대체 코딩을 하는데 로그는 왜 찍어야하는거야? 

그냥 내가 하는 부분에서 콘솔창이나 크롬의 개발자모드의 콘솔에서 에러를 잘 잡는데??? 라는 의문점이 생겼다.

```text
- 운영 환경에서는 콘솔 출력으로 문제를 추적할 수 없어, 파일이나 중앙 로깅 시스템에 기록해야 신속한 대응과 원인 분석이 가능하다.

- 로깅은 장기적인 히스토리 관리를 통해 에러 빈도 및 성능 추세 분석에 유용하다.

- 다양한 로그 레벨을 활용하면 디버깅·포렌식 조사 시 실행 흐름과 변수 상태를 정확히 추적할 수 있다.

- 중앙 로그 공유로 협업 효율성을 높이고, 자동 알림 기능을 통해 SLA를 준수하며 사용자 경험을 개선할 수 있다.
```
---
그렇기 때문에 Log를 남기는 법을 알아보자.

1. pom.xml에 dependency추가하기
```java
		<!-- SLF4J API -->
		<dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>slf4j-api</artifactId>
			<version>1.7.36</version>
		</dependency>
		<!-- SLF4J → Log4j 바인딩 -->
		<dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>slf4j-log4j12</artifactId>
			<version>1.7.36</version>
		</dependency>
		<!-- Log4j Core -->
		<dependency>
			<groupId>log4j</groupId>
			<artifactId>log4j</artifactId>
			<version>1.2.17</version>
		</dependency>
```
이렇게 총 3개의 dependency를 추가해준다.

2. log4j.xml 파일 생성하기
구글에 'log4j.xml 설정'을 검색하면 AI요약같은거로 나오는데
```java
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE log4j:configuration PUBLIC "-//APACHE//DTD LOG4J 1.2//EN" "log4j.dtd">
<log4j:configuration>

    <!-- Appender: 로그를 출력할 위치 설정 -->
    <appender name="console" class="org.apache.log4j.ConsoleAppender">
        <layout class="org.apache.log4j.PatternLayout">
            <!-- 패턴 정의: 날짜/시간 [레벨] 로그 내용 -->
            <param name="ConversionPattern" value="%d{yyyy-MM-dd HH:mm:ss} [%-5p] %c{1}:%L - %m%n"/>
        </layout>
    </appender>

    <!-- Logger: 특정 패키지에 대한 로그 설정 -->
    <logger name="com.example.myapp">
        <level value="INFO"/>
        <appender-ref ref="console"/>
    </logger>

    <!-- Root Logger: 기본 로거 설정 -->
    <root>
        <level value="INFO"/>
        <appender-ref ref="console"/>
    </root>

</log4j:configuration>
```
이걸 그대로 복사해서 src/main/resources라는 경로에 new > file > log4j.xml 저장

복사한 다음 logger name을 바꿔주면 된다.























---
나중에 버젼 맞출때 필요할듯!
https://mybatis.org/spring/
