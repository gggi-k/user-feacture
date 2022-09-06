package kr.submit.userfeature.verify.strategy;

import org.springframework.scheduling.annotation.Async;

public interface VerifyTypeStrategy {

    class DefaultVerifySender implements VerifyTypeStrategy {}

    default void send(String verifyTypeValue, String verifyNumber) {};
}