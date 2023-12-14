package org.example.service;

import io.grpc.stub.StreamObserver;
import org.example.stubs.BankServiceGrpc;
import org.example.stubs.Ebank;

public class BankGrpcService extends BankServiceGrpc.BankServiceImplBase {
    @java.lang.Override
    public void convert(Ebank.ConvertCurrencyRequest request, StreamObserver<Ebank.ConvertCurrencyResponse> responseObserver) {
        String currencyForm = request.getCurrencyFrom();
        String currencyTo = request.getCurrencyTo();

        double amount = request.getAmount();

        Ebank.ConvertCurrencyResponse response= Ebank.ConvertCurrencyResponse.newBuilder()
                .setCurrencyFrom(currencyForm)
                .setCurrencyTo(currencyTo)
                .setAmount(amount)
                .setResult(amount*11.4)
                .build();

        // envoie rep
        responseObserver.onNext(response);
        // indiquer qu'on a fini
        responseObserver.onCompleted();
    }

    @java.lang.Override
    public void getStream(Ebank.ConvertCurrencyRequest request, StreamObserver<Ebank.ConvertCurrencyResponse> responseObserver) {
        super.getStream(request, responseObserver);
    }

    @java.lang.Override
    public StreamObserver<Ebank.ConvertCurrencyRequest> performStream(StreamObserver<Ebank.ConvertCurrencyResponse> responseObserver) {
        return super.performStream(responseObserver);
    }

    @java.lang.Override
    public StreamObserver<Ebank.ConvertCurrencyRequest> fullStream(StreamObserver<Ebank.ConvertCurrencyResponse> responseObserver) {
        return super.fullStream(responseObserver);
    }
}
