package logic;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class Shipping {

    public Iterator<String> getAbsStates() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String cwd = Path.of("").toAbsolutePath().toString();
        JsonNode json = objectMapper.readTree(new File(cwd+"/src/main/java/info/abs_addresses.json"));
        JsonNode stateData = json.at("");
        Iterator<String> states = stateData.fieldNames();

        return states;
    }

    public static void shippingToAbsCenter() throws IOException {

        Scanner input = new Scanner(System.in);
        String state = new String();

        Boolean validStateCheck = false;

        while(!validStateCheck){
            System.out.println("What state? (CA, NC, SC, TN, UT?)");
            state = input.next().toString();
            if(state.equals("CA") || state.equals("NC") || state.equals("SC") || state.equals("TN") || state.equals("UT")){
                validStateCheck = true;
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String cwd = Path.of("").toAbsolutePath().toString();
        JsonNode json = objectMapper.readTree(new File(cwd+"/src/main/java/info/abs_addresses.json"));
        JsonNode stateData = json.at("/"+state);
        Iterator<String> sitesInState = stateData.fieldNames();

        for (Iterator<String> it = sitesInState; it.hasNext(); ) {
            String item = it.next();
            System.out.println(item);
        }
    }

    public static HashMap<String, String> getShippingInfo(boolean isFrom){
        String toOrFrom = "";

        if(isFrom == true){
            toOrFrom = "from";
        }else{
            toOrFrom = "to";
        }

        HashMap<String, String> address = new HashMap<>();
        Boolean addressCheck = false;

        while(!addressCheck){

            if(!address.isEmpty()){
                address.clear();
            }

            Scanner input = new Scanner(System.in);
            Boolean question = false;
            String answer = "";

            while (!question) {
                System.out.println(String.format("Are you shipping %s an ABS center? (Y/N)", toOrFrom));
                answer = input.nextLine();

                answer = answer.toUpperCase();

                if (answer.equals("Y") || answer.equals("N")) {
                    question = true;
                }
            }

            if(answer.equals("Y")){
                //TODO: put ship to ABS center info method here and set equal to hash
            }else{
                System.out.println("Address Line 1:");
                address.put("Address1", input.nextLine());

                System.out.println("Address Line 2. Hit \"Enter\" if no APT, Unit, #, etc");
                String add2 = input.nextLine();

                if (!add2.isEmpty()) {
                    address.put("Address2", add2);
                }

                System.out.println("City:");
                address.put("City", input.nextLine());

                System.out.println("State (Abbr):");
                address.put("State", input.nextLine());

                System.out.println("Zip Code:");
                address.put("Zip", input.nextLine());
            }

            String confirmAddress = String.format("%s\n%s, %s %s", address.get("Address1"), address.get("City"), address.get("State"), address.get("Zip"));
            question = false;

            while (!question) {
                System.out.println(String.format("\nIs this the correct %s address? (Y/N)\n", toOrFrom));
                System.out.println(confirmAddress + "\n");
                answer = input.nextLine();

                answer = answer.toUpperCase();
                if (answer.equals("Y") || answer.equals("N")) {
                    question = true;
                }
            }

            if (answer.equals("Y")) {
                addressCheck = true;
            }

        }

        return address;
    }
}
