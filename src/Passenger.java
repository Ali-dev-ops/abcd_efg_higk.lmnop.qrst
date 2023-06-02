import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Passenger {
        Scanner input = new Scanner(System.in);
        private String username;
        private String password;
        private int charge = 0;

    public Passenger(String username, String password, int charge) {
        this.username = username;
        this.password = password;
        this.charge = charge;
    }

    public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getCharge() {
            return charge;
        }

        public void setCharge(int charge) {
            this.charge = charge;
        }





        public void changePassword(UsersFile usersFile) throws IOException {

            System.out.println("Please enter your new password");
            usersFile.getUfile().seek(Menu.sign + 2L*usersFile.getFIX_SIZE());
            usersFile.getUfile().writeChars(usersFile.fixToWrite(input.next()));
        }



        public void searchFlight(FlightsFile flightsFile) throws IOException {

            System.out.println("please primarily specialize fields(origin,destination,date,max price) and then filter the flights" +
                    "\nand enter other things to see result");

            String filter = input.next();

            switch (filter) {
                    case "origin":
                        String origin = input.next();

                        System.out.println("|Row|Flight Id  |Origin      |Destination |Date      |Time |Price    |Seats|");
                        System.out.println("............................................................................");

                        for (int i = 0; i < flightsFile.getFfile().length()/(5L * 2 * flightsFile.getFIX_SIZE() + 8); i++) {

                            flightsFile.getFfile().seek(i*(5L * 2 * flightsFile.getFIX_SIZE() + 8));

                            if (origin.equals(flightsFile.read().getOrigin())) {
                                flightsFile.getFfile().seek(i*(5L * 2 * flightsFile.getFIX_SIZE() + 8));

                                System.out.printf("|%-3s|%-11s|%-12s|%-12s|%-10s|%-5s|%-,9d|%-5d|%n", i + 1,
                                        flightsFile.read().getFlightId(), flightsFile.read().getOrigin(), flightsFile.read().getDestination(),
                                        flightsFile.read().getDate(), flightsFile.read().getTime(), flightsFile.read().getPrice(),
                                        flightsFile.read().getSeats());
                                System.out.println("............................................................................\n");
                            }
                        }
                        break;

                    case "destination":
                        String destination = input.next();

                        System.out.println("|Row|Flight Id  |Origin      |Destination |Date      |Time |Price    |Seats|");
                        System.out.println("............................................................................");

                        for (int i = 0; i < flightsFile.getFfile().length()/(5L * 2 * flightsFile.getFIX_SIZE() + 8); i++) {

                            flightsFile.getFfile().seek(i * (5L * 2 * flightsFile.getFIX_SIZE() + 8));

                            if (destination.equals(flightsFile.read().getDestination())) {
                                flightsFile.getFfile().seek(i * (5L * 2 * flightsFile.getFIX_SIZE() + 8));

                                System.out.printf("|%-3s|%-11s|%-12s|%-12s|%-10s|%-5s|%-,9d|%-5d|%n", i + 1,
                                        flightsFile.read().getFlightId(), flightsFile.read().getOrigin(), flightsFile.read().getDestination(),
                                        flightsFile.read().getDate(), flightsFile.read().getTime(), flightsFile.read().getPrice(),
                                        flightsFile.read().getSeats());
                                System.out.println("............................................................................\n");
                            }
                        }

                        break;

                    case "date":
                        String date = input.next();

                        System.out.println("|Row|Flight Id  |Origin      |Destination |Date      |Time |Price    |Seats|");
                        System.out.println("............................................................................");

                        for (int i = 0; i < flightsFile.getFfile().length()/(5L * 2 * flightsFile.getFIX_SIZE() + 8); i++) {

                            flightsFile.getFfile().seek(i * (5L * 2 * flightsFile.getFIX_SIZE() + 8));

                            if (date.equals(flightsFile.read().getDate())) {
                                flightsFile.getFfile().seek(i * (5L * 2 * flightsFile.getFIX_SIZE() + 8));

                                System.out.printf("|%-3s|%-11s|%-12s|%-12s|%-10s|%-5s|%-,9d|%-5d|%n", i + 1,
                                        flightsFile.read().getFlightId(), flightsFile.read().getOrigin(), flightsFile.read().getDestination(),
                                        flightsFile.read().getDate(), flightsFile.read().getTime(), flightsFile.read().getPrice(),
                                        flightsFile.read().getSeats());
                                System.out.println("............................................................................\n");
                            }
                        }
                        break;

                    case "max price":
                        int price = input.nextInt();

                        System.out.println("|Row|Flight Id  |Origin      |Destination |Date      |Time |Price    |Seats|");
                        System.out.println("............................................................................");

                        for (int i = 0; i < flightsFile.getFfile().length()/(5L * 2 * flightsFile.getFIX_SIZE() + 8); i++) {

                            flightsFile.getFfile().seek(i * (5L * 2 * flightsFile.getFIX_SIZE() + 8));

                            if (price >= flightsFile.read().getPrice()) {
                                flightsFile.getFfile().seek(i * (5L * 2 * flightsFile.getFIX_SIZE() + 8));

                                System.out.printf("|%-3s|%-11s|%-12s|%-12s|%-10s|%-5s|%-,9d|%-5d|%n", i + 1,
                                        flightsFile.read().getFlightId(), flightsFile.read().getOrigin(), flightsFile.read().getDestination(),
                                        flightsFile.read().getDate(), flightsFile.read().getTime(), flightsFile.read().getPrice(),
                                        flightsFile.read().getSeats());
                                System.out.println("............................................................................\n");
                            }
                        }
                        break;

                    default:

            }
        }


//        public void bookingTicket(UsersFile usersFile) {
//
//            System.out.println("Please enter flightId for booking ticket");
//
//            String flightId = input.next();
//            int i;
//            for (i = 0; flights[i] != null; i++) {
//                if (flightId.equals(flights[i].getFlightId())) {
//                    if (flights[i].getSeats() == 0) {
//                        System.out.println("Sorry,The capacity fill!");
//                        return;
//                    } else if (charge < flights[i].getPrice()) {
//                        System.out.println("Sorry,Your charge is less than price!");
//                        return;
//                    } else
//                        break;
//                }
//            }
//
//            charge -= flights[i].getPrice();
//            flights[i].setSeats(flights[i].getSeats() - 1);
//
//            int j = 0;
//            while (tickets[j] != null)
//                j++;
//
//            tickets[j] = new Ticket();
//            tickets[j].setFlightId(flightId);
//
//            System.out.println("ticket id:"+tickets[j].makeTicketId());
//        }


//        public void ticketCancellation(UsersFile usersFile) {
//
//            System.out.println("please enter the ticket id for cancellation");
//
//            String ticketId = input.next();
//            int i;
//            for (i = 0; tickets[i] != null; i++) {
//                if (ticketId.equals(tickets[i].getTicketId())) {
//                    for (int j = 0; flights[j] != null; j++) {
//                        if (tickets[i].getFlightId().equals(flights[j].getFlightId())) {
//                            flights[j].setSeats(flights[j].getSeats() + 1);
//                            charge += flights[j].getPrice();
//                        }
//                    }
//                    tickets[i] = null;
//                }
//            }
//
//            while (tickets[i] != null) {
//                tickets[i - 1] = tickets[i];
//                i++;
//            }
//            tickets[i] = null;
//
//        }



//        public void bookedTickets(UsersFile usersFile) {
//            for (int i = 0; tickets[i] != null; i++)
//                for (int j = 0; flights[j] != null; j++)
//                    if (tickets[i].getFlightId().equals(flights[j].getFlightId()))
//                        System.out.printf("%nTicketId:%-12s FlightId:%-9s Origin:%-12s Destination:%-12s Date:%-10s Time:%-5s" +
//                                        "Price:%-,9d Seats:%-5d%n", tickets[i].getTicketId(), flights[j].getFlightId(), flights[j].getOrigin(),
//                                flights[j].getDestination(), flights[j].getDate(), flights[j].getTime(), flights[j].getPrice(),
//                                flights[j].getSeats());
//        }




        public void addCharge(UsersFile usersFile) throws IOException {
            usersFile.getUfile().seek(Menu.sign + 4L*usersFile.getFIX_SIZE());
            System.out.println("your charge is:");
            int charge = usersFile.getUfile().readInt();
            System.out.print(charge);
            usersFile.getUfile().seek(Menu.sign + 4L*usersFile.getFIX_SIZE());
            usersFile.getUfile().writeInt(charge + input.nextInt());
        }

}
