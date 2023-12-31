package org.example.service;

import io.grpc.stub.StreamObserver;
import org.example.stubs.BankServiceGrpc;
import org.example.stubs.Ebank;

import java.util.Timer;
import java.util.TimerTask;

public class BankGrpcService extends BankServiceGrpc.BankServiceImplBase {
    @java.lang.Override
    public void convert(Ebank.ConvertCurrencyRequest request, StreamObserver<Ebank.ConvertCurrencyResponse> responseObserver) {
        System.out.println("test unray");
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


    // Server streaming model
    @java.lang.Override
    public void getStream(Ebank.ConvertCurrencyRequest request, StreamObserver<Ebank.ConvertCurrencyResponse> responseObserver) {
        System.out.println("test server streaming");
        double amount=request.getAmount();
        Timer timer=new Timer();
       // chaque seconde j'envoie
        timer.schedule(new TimerTask() {
            int counter=0;
            @Override
            public void run() {
                Ebank.ConvertCurrencyResponse response= Ebank.ConvertCurrencyResponse.newBuilder()
                        .setResult(amount*Math.random()*11)
                        .build();
                responseObserver.onNext(response);
                ++counter;
                if(counter==10){
                    responseObserver.onCompleted();
                    timer.cancel();
                }
            }
        }, 1000, 1000);
    }

    @java.lang.Override
    public StreamObserver<Ebank.ConvertCurrencyRequest> performStream(StreamObserver<Ebank.ConvertCurrencyResponse> responseObserver) {
        System.out.println("test Client streaming");
        return new StreamObserver<Ebank.ConvertCurrencyRequest>() {
            double sum=0;
            // a chaque fois que le client m'envoie un item
            @Override
            public void onNext(Ebank.ConvertCurrencyRequest convertCurrencyRequest) {
                sum+=convertCurrencyRequest.getAmount();
            }
            @Override
            public void onError(Throwable throwable) {
            }
            @Override
            public void onCompleted() {
                double result=sum*11.4;
                Ebank.ConvertCurrencyResponse response= Ebank.ConvertCurrencyResponse.newBuilder()
                        .setResult(result)
                        .build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        };
    }

    @java.lang.Override
    public StreamObserver<Ebank.ConvertCurrencyRequest> fullStream(StreamObserver<Ebank.ConvertCurrencyResponse> responseObserver) {
        System.out.println("test Full streaming");
        return new StreamObserver<Ebank.ConvertCurrencyRequest>() {
            @Override
            public void onNext(Ebank.ConvertCurrencyRequest convertCurrencyRequest) {
                Ebank.ConvertCurrencyResponse response= Ebank.ConvertCurrencyResponse.newBuilder()
                        .setResult(convertCurrencyRequest.getAmount()*21)
                        .build();
                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

}
