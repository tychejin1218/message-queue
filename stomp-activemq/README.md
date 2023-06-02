---
### Docker
* Docker 실행
  * docker pull rmohr/activemq
  * docker run -p 61616:61616 -p 8161:8161 rmohr/activemq
---
### JmsTemplate 속성 
* ExplicitQosEnabled
  * 메시지 전송에 QOS 값(DeliveryMode, Priority, TimeToLive)을 사용해야 하는지 여부
* DeliveryPersistent
  * 메시지의 영속성을 설정
  * true로 설정하면 영구 저장이 활성화되며, false로 설정하면 비영구 저장이 활성화
* ReceiveTimeout
  * 메시지를 받을 때 대기할 최대 시간을 설정
* TimeToLive
  * 메시지 유효 기간을 설정
---
### Destination Policy
* gcInactiveDestinations
  * 일정 기간 동안 사용되지 않은 대상(destination)을 가비지 컬렉션(garbage collection)하여 시스템의 리소스를 최적화하는 데 사용
  * Pending Message와 Consumer가 없을 때 동작
