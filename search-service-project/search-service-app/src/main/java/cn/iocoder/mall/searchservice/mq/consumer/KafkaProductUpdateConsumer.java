package cn.iocoder.mall.searchservice.mq.consumer;

import cn.iocoder.mall.searchservice.manager.product.SearchProductManager;
import cn.iocoder.mall.searchservice.mq.consumer.message.ProductUpdateMessage;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.BatchAcknowledgingConsumerAwareMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

@Component
public class KafkaProductUpdateConsumer implements BatchAcknowledgingConsumerAwareMessageListener<String, String> {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProductUpdateConsumer.class);

    @Autowired
    private SearchProductManager productSearchManager;

    @Override
    public void onMessage(List<ConsumerRecord<String, String>> data, Acknowledgment acknowledgment, Consumer<?, ?> consumer) {
        logger.info("kafka product update consumer poll data size is {}", data.size());
        for (ConsumerRecord<String, String> consumerRecord : data) {
            String value = consumerRecord.value();
            logger.info("kafka product update consumer poll data is {}", value);
            ProductUpdateMessage message = (ProductUpdateMessage) GsonUtils.convertToObject(value, ProductUpdateMessage.class);
            Boolean result = productSearchManager.saveProduct(message.getId());
            Assert.isTrue(result, String.format("重构商品(%d)的 ES 索引，必然成功。实际结果是 %s", message.getId(), result));
        }
        acknowledgment.acknowledge();
    }
}
