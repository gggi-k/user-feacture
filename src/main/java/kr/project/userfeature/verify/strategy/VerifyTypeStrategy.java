package kr.project.userfeature.verify.strategy;

// TODO 전략패턴에 따른 인터페이스
public interface VerifyTypeStrategy {

    class DefaultVerifySender implements VerifyTypeStrategy {}

    default void send(String verifyTypeValue, String verifyNumber) {};
}