package org.example.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReceiveService {

	@StreamListener("input1")
	public void receiveInput1(String receiveMsg) {
		log.info("input1 receive: " + receiveMsg);
	}

	@StreamListener("input2")
	public void receiveInput2(String receiveMsg) {
		log.info("input2 receive: " + receiveMsg);
	}

	@StreamListener("input3")
	public void receiveInput3(@Payload Foo foo) {
		log.info("input3 receive: " + foo);
	}

	@StreamListener("input4")
	public void receiveTransactionalMsg(String transactionMsg) {
		log.info("input4 receive transaction msg: " + transactionMsg);
	}

}
