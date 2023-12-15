package org.example.cleint;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.example.stubs.BankServiceGrpc;
import org.example.stubs.Ebank;

import java.io.IOException;

public class BankGrpcClient2 {
    public static void main(String[] args) throws IOException {

        //creer un client
        ManagedChannel managedChannel1 = ManagedChannelBuilder.forAddress("localhost",5555)
                .usePlaintext()
                .build();

        // creer un stub : blockingStub un mode bloqunt , qd je l'envoie j'attends
        BankServiceGrpc.BankServiceStub asyncStub= BankServiceGrpc.newStub(managedChannel1);
        Ebank.ConvertCurrencyRequest request= Ebank.ConvertCurrencyRequest.newBuilder()
                .setCurrencyFrom("MAD")
                .setCurrencyTo("USD")
                .setAmount(6500)
                .build();

        // prog asynch
        asyncStub.convert(request, new StreamObserver<Ebank.ConvertCurrencyResponse>() {
            @Override
            public void onNext(Ebank.ConvertCurrencyResponse convertCurrencyResponse) {
                System.out.println("*************");
                System.out.println(convertCurrencyResponse);
                System.out.println("*************");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }

            @Override
            public void onCompleted() {

                System.out.println("END ******");

            }
        });
        System.out.println("???????");
        System.in.read();



    }

}
