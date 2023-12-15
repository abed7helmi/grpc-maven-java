package org.example.cleint;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.example.stubs.BankServiceGrpc;
import org.example.stubs.Ebank;


// one way
public class BankGrpcClient1 {
    public static void main(String[] args) {

        //creer un client
        ManagedChannel managedChannel1 = ManagedChannelBuilder.forAddress("localhost",5555)
                .usePlaintext()
                .build();

        // creer un stub : blockingStub un mode bloqunt , qd je l'envoie j'attends
        BankServiceGrpc.BankServiceBlockingStub blockingStub= BankServiceGrpc.newBlockingStub(managedChannel1);
        Ebank.ConvertCurrencyRequest request= Ebank.ConvertCurrencyRequest.newBuilder()
                .setCurrencyFrom("MAD")
                .setCurrencyTo("USD")
                .setAmount(6500)
                .build();

        // j'attends le resultat
        Ebank.ConvertCurrencyResponse currencyResponse = blockingStub.convert(request);
        System.out.println(currencyResponse);


    }

}
