package org.shahbour;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableBinding(Source.class)
@EnableScheduling
public class StreamInterceptApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(StreamInterceptApplication.class, args);
	}

	@Autowired
	MessageChannel output;

	@Scheduled(fixedDelay = 2000L)
	public void sendMessage() {
		output.send(MessageBuilder.withPayload(new Customer(1,"Ali Shahbour")).build());
	}

	@Autowired
	ChannelInterceptor auditIntercept;

	@Override
	public void run(String... strings) throws Exception {
		((DirectChannel) output).addInterceptor(0, auditIntercept);
	}
}
