---
### Docker
* Docker 실행
  * docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 -p 1883:1883 --restart=unless-stopped rabbitmq:management
* MQTT 플러그인 - https://www.rabbitmq.com/mqtt.html#config
  * rabbitmq-plugins enable rabbitmq_mqtt

---
