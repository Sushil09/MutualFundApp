package client;

import com.mutualfund.logic.services.Calculation;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.lang.String.format;

@Component
public class Client implements CommandLineRunner {
    static Scanner input=new Scanner(System.in);
    @Override
    public void run(String... args) throws Exception {
        Calculation calculation=new Calculation();

        System.out.println("Enter schema number:");
        String schemaNumber=input.nextLine();

        System.out.println("Enter Period of Investment:");
        int period=input.nextInt();

        System.out.println("Enter Horizon:");
        int horizon=input.nextInt();
        calculation.callRestService(schemaNumber,period,horizon);
    }

}
