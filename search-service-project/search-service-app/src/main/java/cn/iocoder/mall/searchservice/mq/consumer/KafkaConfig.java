package cn.iocoder.mall.searchservice.mq.consumer;

import com.google.common.collect.Maps;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    @Value("${kafka.topic:topic-1}")
    private String topic;

    @Bean
    public ConcurrentMessageListenerContainer concurrentMessageListenerContainer() {
        ContainerProperties containerProperties = new ContainerProperties(topic);
        DefaultKafkaConsumerFactory<String, String> factory = new DefaultKafkaConsumerFactory<String, String>(containerConfigs());
        ConcurrentMessageListenerContainer container = new ConcurrentMessageListenerContainer(factory, containerProperties);
        return container;
    }

    /**
     * kafka consumer config
     *
     * @return config map
     */
    private Map<String, Object> containerConfigs() {
        Map<String, Object> map = Maps.newHashMap();
        map.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        return map;
    }
}
