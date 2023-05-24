package com.example.mqttrabbitmq.config;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles(inheritProfiles = false, resolver = ActiveProfileResolver.class)
@SpringBootTest(classes = CoreApplication.class)
class MqttConfigTest {

}
