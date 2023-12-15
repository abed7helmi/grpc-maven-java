package org.example.cleint;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.example.stubs.BankServiceGrpc;
import org.example.stubs.Ebank;

import java.io.IOException;

public class BankGrpcClient3 {
    public static void main(String[] args) throws IOException {
        ManagedChannel managedChannel= ManagedChannelBuilder.forAddress("localhost",5555)
                .usePlaintext()
                .build();

        BankServiceGrpc.BankServiceStub asyncStub=BankServiceGrpc.newStub(managedChannel);

        Ebank.ConvertCurrencyRequest request= Ebank.ConvertCurrencyRequest.newBuilder()
                .setAmount(5000)
                .build();
        asyncStub.getStream(request, new StreamObserver<Ebank.ConvertCurrencyResponse>() {
            @Override
            public void onNext(Ebank.ConvertCurrencyResponse convertCurrencyResponse) {
                System.out.println("================");
                System.out.println(convertCurrencyResponse);
                System.out.println("================");
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                System.out.println("END");
            }
        });

        System.in.read();
    }

}
