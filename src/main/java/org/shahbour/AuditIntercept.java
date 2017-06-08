package org.shahbour;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by shahbour on 3/11/17.
 */
@Component
@Slf4j
public class AuditIntercept implements ChannelInterceptor {

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel messageChannel) {

		for(Field field  : message.getPayload().getClass().getDeclaredFields() )
		{
			if (field.isAnnotationPresent(CreatedBy.class))
			{
				updateField(field,message.getPayload(),"created By");
			}

			if (field.isAnnotationPresent(LastModifiedBy.class))
			{
				updateField(field,message.getPayload(),"last updated by");
			}
		}

		return message;
	}

	private void updateField(Field field,Object payload,String newValue) {
		if(field.getType().equals(String.class)) {
			try {
				Method readMethod =  BeanUtils.getPropertyDescriptor(payload.getClass(),field.getName()).getReadMethod();
				log.info("old value {}", readMethod.invoke(payload));
				Method setMethod = BeanUtils.getPropertyDescriptor(payload.getClass(),field.getName()).getWriteMethod();
				setMethod.invoke(payload,newValue);
				log.info("new value {}", readMethod.invoke(payload));
			} catch (IllegalAccessException e) {
				log.error("error",e);
			} catch ( InvocationTargetException e) {
				log.error("error",e);
			}
		}
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
