## 목차
*   [의존성 주입](#의존성-주입)
*   [Bean 이란?](#Bean-이란)

---
## 의존성 주입
***

<https://lincoding.tistory.com/76> 를 참고했어요

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
