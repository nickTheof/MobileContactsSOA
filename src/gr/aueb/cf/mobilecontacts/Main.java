package gr.aueb.cf.mobilecontacts;

import gr.aueb.cf.mobilecontacts.controller.MobileContactController;
import gr.aueb.cf.mobilecontacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.mobilecontacts.dto.MobileContactUpdateDTO;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner in = new Scanner(System.in);
    private static final MobileContactController controller = new MobileContactController();

    public static void main(String[] args) {
        String choice;
        while (true) {
            printMenu();
            choice = getToken();

            if (choice.equals("Q") || choice.equals("q")) {
                break;
            }
            handleChoice(choice);
        }

        System.out.println("Good bye! Thank you for using the Mobile Contact App!");
    }

    public static void printMenu() {
        System.out.println("\n==== Mobile Contact App ====");
        System.out.println("1. Insert Contact");
        System.out.println("2. Update Contact");
        System.out.println("3. Delete Contact");
        System.out.println("4. Search Contact");
        System.out.println("5. Show All Contacts");
        System.out.println("Q/q. Exit");
        System.out.print("Enter your choice: ");
    }

    public static String getToken() {
        return in.nextLine().trim();
    }

    public static Long getValidLong(String prompt) {
    Long id = null;
    while (id == null) {
        System.out.println(prompt);
        try {
            id = Long.valueOf(getToken());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid numeric ID.");
        }
    }
    return id;
    }


    public static void handleChoice(String choice) {
        String firstname;
        String lastname;
        String phoneNumber;
        String response;
        Long id;
        List<String> contactList;
        switch (choice) {
            case "1":
                System.out.println("Enter Firstname, Lastname and Phone Number.");
                firstname = getToken();
                lastname = getToken();
                phoneNumber = getToken();
                MobileContactInsertDTO insertDTO = new MobileContactInsertDTO(firstname, lastname, phoneNumber);
                response = controller.insertContact(insertDTO);
                printResponse(response, "Insert");
                break;
            case "2":
                id = getValidLong("Enter Contact ID to update: ");
                System.out.println("Enter new Firstname, Lastname, and Phone Number:");
                firstname = getToken();
                lastname = getToken();
                phoneNumber = getToken();
                response = controller.updateContact(new MobileContactUpdateDTO(id, firstname, lastname, phoneNumber));
                printResponse(response, "Update");
                break;
            case "3":
                id = getValidLong("Enter Contact ID to delete: ");
                response = controller.deleteContactById(id);
                printResponse(response, "Delete");
                break;

            case "4":
                id = getValidLong("Enter Contact ID to search: ");
                response = controller.getContactById(id);
                printResponse(response, "Search");
                break;
            case "5":
                contactList = controller.getAllContacts();
                if (!contactList.isEmpty()) {
                    System.out.println("==== Contact List ====");
                    for (String contact : contactList) {
                        System.out.println(contact);
                    }
                } else {
                    System.out.println("Contact List is empty.");
                }
                break;
            default:
                System.out.println("Invalid choice! Please select a valid option.");
                break;

        }
    }

    private static void printResponse(String response, String action) {
        if (response.startsWith("OK")) {
            System.out.println(action + " Successful!");
            System.out.println(response.substring(3));
        } else {
            System.out.println(action + " Failed!");
            System.out.println(response.substring(6));
        }
    }
}
