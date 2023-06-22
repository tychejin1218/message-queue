---
### Docker
* Docker 실행
  * docker run -p 61616:61616 -p 8161:8161 rmohr/activemq

---
[SpringBoot] ActiveMQ Queue, Topic 연동
ActiveMQ 자바 메시지 서비스(JMS) 클라이언트와 자바(Java)로 작성된 오픈 소스 메시지 브로커입니다.
ActiveMQ : https://activemq.apache.org

1. Docker를 사용하여 ActiveMQ 설치
   Docker를 사용하여 ActiveMQ를 로컬에 설치하세요.
   Docker Hub : https://hub.docker.com/r/rmohr/activemq

포트 61616는 ActiveMQ 브로커(Broker) 연결, 포트 8161는ActiveMQ 웹 관리 콘솔에 사용됩니다.

웹 관리 콘솔 : http://localhost:8161
기본 계정 및 비밀번호 : admin/admin
ActiveMQ 웹 관리 콘솔

2. 의존성 추가
   Apache ActiveMQ를 사용하기 위해 'Spring Boot Starter ActiveMQ'를 추가하세요.
   Maven Repository
   https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-activemq

build.gradle

3. ActiveMQ 설정
   JMS(Java Message Service) 클라이언트와 ActiveMQ 메시지 브로커(Message Broker) 간의 연결을 위해 ActiveMQConnectionFactory을 설정하세요.
   큐(Queue)와 토픽(Topic)에 메시지를 발행하고 수신하기 위해 JmsTemplate을 설정하고, 메시지를 구독하기 위해 JmsListenerContainerFactory를 설정하세요.
   JMS(Java Message Service) 메시지와 JSON 형식의 메시지 간의 변환을 위해 MessageConvert를 설정하세요.

ActiveMQConfig.java
JmsTemplate의 setPubSubDomain(boolean pubSubDomain) 메서드는 JmsTemplate의 메시지 전송 방식을 결정합니다.
pubSubDomain 매개변수를 true로 설정하면, JmsTemplate은 Pub-Sub(Domain) 방식으로 동작합니다. Pub-Sub 방식은 Topic을 사용하여 메시지를 발행하고 구독하는 방식을 의미합니다.
pubSubDomain 매개변수를 false로 설정하면, JmsTemplate은 Point-to-Point(P2P) 방식으로 동작합니다. Point-to-Point 방식은 Queue를 사용하여 메시지를 발행하고 수신하는 방식을 의미합니다.

application.yml
ActiveMQ 연결 정보 및 Queue, Topic 정보를 추가하세요.

4. ActiveMQ Service 추가
   Queue, Topic으로 메시지를 발행할 때는 JmsTemplate 클래스에 convertAndSend 메서드를 사용하고, 메시지를 구독할 때는 하는 @JmsListener 어노테이션을 사용하여 메서드를 구현하세요.

MessageServiceQueue.java
jmsTemplateQueue 빈을 사용하여 메시지를 발행하는 메서드와 containerFactoryQueue 빈을 사용하여 메시지를 구독하는 메서드를 구현하세요.

MessageServiceTopic.java
jmsTemplateQueue 빈을 사용하여 메시지를 발행하는 메서드와 containerFactoryTopic 빈을 사용하여 메시지를 구독하는 메서드를 구현하세요.
한 메시지가 여러 구독자에게 전달되는지 확인하기 위해, 메서드를 하나 이상 추가하세요.

MessageDto.java
메시지를 보내기 위한 DTO 클래스를 추가하세요.

5. ActiveMQ Controller 추가
   Queue, Topic으로 메시지를 발행할 수 있도록 간단한 REST API 추가하세요.

MessageController.java

6. 메시지 발행 및 구독 확인
   message.http 추가

콘솔 로그 확인

소스 코드는 Github Repository - https://github.com/tychejin1218/message-queue.git 에서 active-sample-01 프로젝트를 참조하세요.

SpringBoot
스프링부트
ActiveMQ
Topic
Queue

---
