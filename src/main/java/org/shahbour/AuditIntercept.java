package org.shahbour;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

/**
 * Created by shahbour on 3/11/17.
 */
@Component
@Slf4j
public class AuditIntercept implements ChannelInterceptor {

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel messageChannel) {
		log.info("trying to send message to {}",messageChannel);
		return message;
	}

	@Override
	public void postSend(Message<?> message, MessageChannel messageChannel, boolean b) {

	}

	@Override
	public void afterSendCompletion(Message<?> message, MessageChannel messageChannel, boolean b, Exception e) {

	}

	@Override
	public boolean preReceive(MessageChannel messageChannel) {
		return true;
	}

	@Override
	public Message<?> postReceive(Message<?> message, MessageChannel messageChannel) {
		return message;
	}

	@Override
	public void afterReceiveCompletion(Message<?> message, MessageChannel messageChannel, Exception e) {

	}
}
