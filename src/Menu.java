import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Menu {
    Scanner input = new Scanner(System.in);
    private UsersFile usersFile = new UsersFile(new RandomAccessFile("usersFile","rw"));
    private Passenger enteredPassenger;
    private Admin admin = new Admin();
    public static int sign = 0;

    public Menu() throws FileNotFoundException {
    }

    public UsersFile getUsersFile() {
        return usersFile;
    }

    public void loginMenu() throws IOException {
        while (true) {
            System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::::::::");
            System.out.println("         WELCOME TO AIRLINE RESERVATION SYSTEM         ");
            System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::::::::");
            System.out.println("   ...................MENU OPTIONS..................   ");
            System.out.println("\t<1> Sign in\n\t<2> Sign up");


            int enter = input.nextInt();
            if (enter == 1)
                signIn();
            else if (enter == 2)
                signUp();
        }
    }

    public void adminMenu() throws IOException {

        while (true) {
            System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::::::::");
            System.out.println("                   ADMIN MENU OPTIONS                  ");
            System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::::::::");
            System.out.println("   .................................................   ");
            System.out.println("\t<1> Add\n\t<2> update\n\t<3> Remove\n\t<4> Flight schedules\n\t<0> Sign out");


            int adminAction = input.nextInt();
            switch (adminAction) {
                case 1:
                    admin.addFlight();
                    break;
                case 2:
                    admin.updateFlight();
                    break;
                case 3:
                    admin.removeFlight();
                    break;
                case 4:
                    admin.flightSchedules();
                    break;
                case 0:
                    return;
                default:

            }

        }
    }

    public void passengerMenu() throws IOException {

        while (true) {
            System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
            System.out.println("                 PASSENGER MENU OPTIONS                 ");
            System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
            System.out.println("   ..................................................   ");
            System.out.println("\t<1> Change password\n\t<2> Search flight tickets\n\t<3> Booking ticket\n\t<4> Ticket cancellation" +
                    "\n\t<5> Booked tickets\n\t<6> Add charge\n\t<0> Sign out");

            int passengerAction = input.nextInt();

            switch (passengerAction) {
                case 1:
                    enteredPassenger.changePassword(usersFile);
                    break;
                case 2:
                    enteredPassenger.searchFlight(admin.getFlightsFile());
                    break;
                case 3:
//                    enteredPassenger.bookingTicket(admin.getFlightsFile());
                    break;
                case 4:
//                    enteredPassenger.ticketCancellation(admin.getFlightsFile());
                    break;
                case 5:
//                    enteredPassenger.bookedTickets(admin.getFlightsFile());
                    break;
                case 6:
                    enteredPassenger.addCharge(usersFile);
                    break;
                case 0:
                    return;
                default:
            }
        }
    }


    public void signUp() throws IOException {
        String username;
        String password;

        while (true) {
            System.out.println("Please choose your username and password :");
            username = input.next();
            password = input.next();

            if (usersFile.searchUser(username,password) || username.equals(admin.getUSERNAME()) && password.equals(admin.getPASSWORD()))
                System.out.println("Sorry,This username and password already used!");
            else
                break;
        }
        usersFile.getUfile().seek(usersFile.getUfile().length());
        usersFile.write(username,password);

    }


    public void signIn() throws IOException {

        while (true) {
            System.out.println("Please enter your username and password");
            String username = input.next();
            String password = input.next();

            if (username.equals(admin.getUSERNAME()) && password.equals(admin.getPASSWORD())) {
                adminMenu();
                break;
            }else if (usersFile.searchUser(username,password)) {
                sign = (int) usersFile.getUfile().getFilePointer();
                enteredPassenger = usersFile.read();
                passengerMenu();
                break;
            }else
                System.out.println("The User not found!");
        }
    }

}
