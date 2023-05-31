---
### Docker
* Docker 실행
  * docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 -p 61613:61613 --restart=unless-stopped rabbitmq:management
* STOMP 플러그인
  * rabbitmq-plugins enable rabbitmq_stomp
* lvc_exchange 플러그인
  * curl -L https://github.com/rabbitmq/rabbitmq-lvc-exchange/releases/download/v3.11.4/rabbitmq_lvc_exchange-v3.11.4.ez
* ngrider
  * ngrinder-controller
    * docker pull ngrinder/controller:3.5.4
    * docker run -d -v ~/ngrinder-controller:/opt/ngrinder-controller --name controller -p 80:80 -p 16001:16001 -p 12000-12009:12000-12009 ngrinder/controller:3.5.4
  * ngrinder-aget
    * docker pull ngrinder/agent:3.5.4
    * docker run -d --name agent --link controller:controller ngrinder/agent:3.5.4
---
### STOMP 사용 시 RabbitMQ Queue Properties
##### RabbitMQ Queue Properties
* durable(aliased as persistent): 메시지 브로커에 의해 메시지를 디스크에 지속적으로 저장하도록 지정하는 옵션입니다. 메시지를 수신한 후에도 메시지가 보존되어야 할 필요가 있는 경우 사용됩니다.
* auto-delete: 큐나 익스체인지가 더 이상 사용되지 않을 때 자동으로 삭제되는지 여부를 지정합니다. 모든 구독이 해제될 때 해당 큐나 익스체인지가 자동으로 삭제됩니다.
* exclusive: 큐나 익스체인지를 생성한 연결에 대해 전용으로 만드는 데 사용됩니다. 해당 큐나 익스체인지는 다른 연결에서 접근할 수 없으며, 생성한 연결에서만 접근할 수 있도록 제한됩니다.
* Arguments
  * x-dead-letter-exchange 및 x-dead-letter-routing-key: 큐에 도달하지 못한 메시지를 전달할 대체 교환 및 라우팅 키를 지정합니다.
  * x-expires: 큐의 자동 삭제 시간을 설정합니다. 설정된 시간 이후에 큐는 자동으로 삭제됩니다.
  * x-message-ttl: 큐에 저장된 메시지의 유효 기간을 설정합니다. 유효 기간이 지난 메시지는 자동으로 삭제됩니다.(큐 단위로 설정)
  * x-max-length: 큐에 저장할 수 있는 최대 메시지 개수를 제한합니다. 초과된 메시지는 드롭됩니다.
  * x-max-length-bytes: 큐에 저장할 수 있는 최대 메시지 크기를 바이트 단위로 제한합니다. 초과된 메시지는 드롭됩니다.
  * x-max-age: 메시지가 큐에 머무를 수 있는 최대 시간을 제어하는 데 사용됩니다.(메시지 단위로 설정)
  * x-stream-max-segment-size-bytes : Stream으로 보내는 메시지의 크기를 제한하는 역할을 합니다.
  * x-overflow: 큐가 가득 찼을 때 새로운 메시지를 처리하기 위한 대체 동작을 설정합니다. 예를 들어, 메시지를 드롭하거나 이전 메시지를 삭제할 수 있습니다.
    * drop-head: 가장 오래된 메시지를 삭제하여 새로운 메시지를 수용합니다.
    * reject-publish: 새로운 메시지를 거부하고 발행자에게 오류를 반환합니다.
  * x-max-priority: 큐에서 사용할 수 있는 최대 우선순위 수를 설정합니다. 우선순위가 높은 메시지가 먼저 처리됩니다.
  * x-queue-type : Classic Queue, Quorum Queue, Stream Queue 등 큐의 동작 방식을 선택할 수 있습니다.
    * Classic Queue:
      * x-queue-type을 지정하지 않은 경우 기본적으로 Classic Queue로 생성됩니다.
      * Classic Queue는 FIFO(First-In, First-Out) 방식으로 동작합니다.
      * 메시지를 수신한 순서대로 처리되며, 다중 소비자가 있는 경우 메시지는 Round-Robin 방식으로 분배됩니다.
    * Quorum Queue:
      * x-queue-type을 quorum으로 설정하여 Quorum Queue로 생성할 수 있습니다.
      * Quorum Queue는 RabbitMQ 3.8 버전 이상에서 사용할 수 있는 새로운 큐 유형입니다.
      * Quorum Queue는 분산 형태로 구현되어 데이터의 신뢰성과 내구성을 강화합니다.
      * 다중 소비자와 높은 처리량을 지원하며, 메시지 손실 및 큐 장애에 대한 신뢰성을 향상시킵니다.
    * Stream Queue
      * x-queue-type을 stream으로 설정하여 Stream Queue로 생성할 수 있습니다.
      * Stream Queue는 RabbitMQ 3.8 버전 이상에서 사용할 수 있는 새로운 큐 유형입니다.
      * Stream Queue는 메시지를 세그먼트로 분할하여 저장하고 검색하는 기능을 제공합니다.
      * 큰 크기의 메시지를 처리할 수 있으며, 메시지의 기간별 및 시간대별 처리에 유용합니다.
---
