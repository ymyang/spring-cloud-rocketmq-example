package org.example.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

@Slf4j
@RocketMQTransactionListener(
		txProducerGroup = "myTxProducerGroup",
		corePoolSize = 5,
		maximumPoolSize = 10)
public class TransactionListenerImpl implements RocketMQLocalTransactionListener {

	@Override
	public RocketMQLocalTransactionState executeLocalTransaction(Message msg,
			Object arg) {
		Object num = msg.getHeaders().get("test");

		if ("1".equals(num)) {
			log.info("executer: " + new String((byte[]) msg.getPayload()) + " unknown");
			return RocketMQLocalTransactionState.UNKNOWN;
		}
		else if ("2".equals(num)) {
			log.info("executer: " + new String((byte[]) msg.getPayload()) + " rollback");
			return RocketMQLocalTransactionState.ROLLBACK;
		}
		log.info("executer: " + new String((byte[]) msg.getPayload()) + " commit");
		return RocketMQLocalTransactionState.COMMIT;
	}

	@Override
	public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
		log.info("check: " + new String((byte[]) msg.getPayload()));
		return RocketMQLocalTransactionState.COMMIT;
	}

}
