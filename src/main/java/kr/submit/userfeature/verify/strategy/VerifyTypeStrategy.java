package kr.submit.userfeature.verify.strategy;

public interface VerifyTypeStrategy {

    class DefaultVerifySender implements VerifyTypeStrategy {}

    default void send(String verifyTypeValue, String verifyNumber) {};
}