//package io.spring.helloworld.sec8lec37;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.integration.amqp.inbound.AmqpInboundChannelAdapter;
//import org.springframework.integration.amqp.outbound.AmqpOutboundEndpoint;
//import org.springframework.integration.annotation.ServiceActivator;
//import org.springframework.integration.channel.DirectChannel;
//import org.springframework.integration.channel.NullChannel;
//import org.springframework.integration.channel.QueueChannel;
//import org.springframework.integration.core.MessagingTemplate;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.PollableChannel;
//
//@Configuration
//@Slf4j
//public class IntegrationConfiguration {
//    @Bean
//    public MessagingTemplate messagingTemplate(){
//        MessagingTemplate messagingTemplate = new MessagingTemplate(outboundRequests());
//        messagingTemplate.setReceiveTimeout(60_000_000L); //1000분
//        return messagingTemplate;
//    }
//
//    @Bean
//    public DirectChannel outboundRequests(){
//        return new DirectChannel();
//    }
//
//    @Bean
//    @ServiceActivator(inputChannel = "outboundRequests")
//    public AmqpOutboundEndpoint amqpOutboundEndpoint(AmqpTemplate amqpTemplate){
//        AmqpOutboundEndpoint amqpOutboundEndpoint = new AmqpOutboundEndpoint(amqpTemplate);
//        amqpOutboundEndpoint.setExpectReply(true);
//        amqpOutboundEndpoint.setOutputChannel(inboundRequests());
//        amqpOutboundEndpoint.setRoutingKey("partition.requests");
//        return amqpOutboundEndpoint;
//    }
//
//    @Bean
//    public Queue requestQueue(){
//        return new Queue("partition.requests",false);
//    }
//
//    @Bean
//    @Profile("slave")
//    public AmqpInboundChannelAdapter inbound(SimpleMessageListenerContainer listenerContainer){
//        AmqpInboundChannelAdapter inboundChannelAdapter = new AmqpInboundChannelAdapter(listenerContainer);
//        inboundChannelAdapter.setOutputChannel(inboundRequests());
//        inboundChannelAdapter.afterPropertiesSet();
//        return inboundChannelAdapter;
//    }
//
//    @Bean
//    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory){
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
//        container.setQueueNames("partition.requests");
//        container.setAutoStartup(false);
//        return container;
//    }
//
//    @Bean
//    public PollableChannel outboundStaging(){
//        return new NullChannel();
//    }
//
//    @Bean
//    public QueueChannel inboundRequests() {
//        return new QueueChannel();
//    }
//}
