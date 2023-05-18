## RabbitMQ의 구성 요소
* [Producer](#Producer)
* [Broker](#Broker)
  * [Exchange](#Exchange)
  * [Bindings](#Bindings)
  * [Queues](#Queues)
* [Consumer](#Consumer)

## Docker
* [Docker](#Docker)

---
### Producer
* Producer

---

---
### Broker
* Broker

---

---
### Exchange
* Exchange
  * Direct : Routing Key를 기반으로 큐에 메시지를 전달
  * Fanout : Routing Key와 상관없이 모든 큐에 메시지를 전달
  * Topic : Routing Key 전체가 일치하거나 일부 패턴과 일치하는 큐에 메시지를 전달
  * Headers : Routing Key 대신 메시지 헤더에 다양한 속성을 추가하여 메시지를 전송

---

---
### Bindings
* Bindings

---

---
### Queues
* Queues
  * type
    * class : 유사한 특성을 가진 큐를 그룹화하는 방법
    * quorum : 고가용성 및 내구성을 제공하는 큐, 손실되지 않는 것을 목적으로 함
    * stream : 고처리 데이터 스트림을 처리하는 방법 - 메시지 중복 제거, 메시지 순서 지정
      * https://www.rabbitmq.com/stream.html
  
---

---
### Consumer
* Consumer

---

---
### Docker
* Docker 실행
  * docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 -p 61613:61613 --restart=unless-stopped rabbitmq:management
* STOMP 플러그인
  * rabbitmq-plugins enable rabbitmq_stomp
* 

---
