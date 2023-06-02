import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Admin {
    Scanner input = new Scanner(System.in);
    private final String USERNAME = "admin";
    private final String PASSWORD = "admin";
    private FlightsFile flightsFile = new FlightsFile(new RandomAccessFile("flightsFile.dat","rw"));

    public Admin() throws FileNotFoundException {
    }

        public String getPASSWORD() {
            return PASSWORD;
        }

        public String getUSERNAME() {
            return USERNAME;
        }

        public FlightsFile getFlightsFile() {
        return flightsFile;
    }

    public void addFlight() throws IOException {

            System.out.println("Please enter fields of flight");
            String flightId = input.next();
            String origin = input.next();
            String destination = input.next();
            String date = input.next();
            String time = input.next();
            int price = input.nextInt();
            int seats = input.nextInt();

            flightsFile.getFfile().seek(flightsFile.getFfile().length());

            flightsFile.write(flightId, origin, destination, date, time, price, seats);
        }

        public void updateFlight() throws IOException {

            System.out.println("Please enter the row of flight in table that be must update");
            int i = input.nextInt() - 1;

            System.out.println("which of these (flight id, origin, destination and...) must be update?");
            String update = input.next();

            if (update.equals("flight id")) {
                flightsFile.getFfile().seek(i*(5L * 2 * flightsFile.getFIX_SIZE() + 8));
                flightsFile.getFfile().writeChars(flightsFile.fixToWrite(input.next()));
            }

            if (update.equals("origin")) {
                flightsFile.getFfile().seek(i*(5L * 2 * flightsFile.getFIX_SIZE() + 8) + 2L*flightsFile.getFIX_SIZE());
                flightsFile.getFfile().writeChars(flightsFile.fixToWrite(input.next()));
            }

            if (update.equals("destination")) {
                flightsFile.getFfile().seek(i*(5L * 2 * flightsFile.getFIX_SIZE() + 8) + 2L*2*flightsFile.getFIX_SIZE());
                flightsFile.getFfile().writeChars(flightsFile.fixToWrite(input.next()));
            }

            if (update.equals("date")) {
                flightsFile.getFfile().seek(i*(5L * 2 * flightsFile.getFIX_SIZE() + 8) + 3L*2*flightsFile.getFIX_SIZE());
                flightsFile.getFfile().writeChars(flightsFile.fixToWrite(input.next()));
            }

            if (update.equals("time")){
                flightsFile.getFfile().seek(i*(5L * 2 * flightsFile.getFIX_SIZE() + 8) + 4L*2*flightsFile.getFIX_SIZE());
                flightsFile.getFfile().writeChars(flightsFile.fixToWrite(input.next()));
            }


            if (update.equals("price")) {
                flightsFile.getFfile().seek(i*(5L * 2 * flightsFile.getFIX_SIZE() + 8) + 5L*2*flightsFile.getFIX_SIZE());
                flightsFile.getFfile().writeInt(input.nextInt());
            }

            if (update.equals("seats")) {
                flightsFile.getFfile().seek(i*(5L * 2 * flightsFile.getFIX_SIZE() + 8) + 5L*2*flightsFile.getFIX_SIZE() + 4);
                flightsFile.getFfile().writeInt(input.nextInt());
            }

        }

        public void removeFlight() throws IOException {

            System.out.println("Please enter the flight id to remove");
            String flightId = input.next();

            String id;
            String ori;
            String dest;
            String date;
            String time;
            int price;
            int seats;

            flightsFile.searchByFlightId(flightId);

            for (int i = (int) ((int) flightsFile.getFfile().getFilePointer() / (5L * 2 * flightsFile.getFIX_SIZE() + 8));
                 i < flightsFile.getFfile().length()/(5L * 2 * flightsFile.getFIX_SIZE() + 8); i++) {

                flightsFile.getFfile().seek(flightsFile.getFfile().getFilePointer() + (5L * 2 * flightsFile.getFIX_SIZE() + 8));

                id = flightsFile.read().getFlightId();
                ori = flightsFile.read().getOrigin();
                dest = flightsFile.read().getDestination();
                date = flightsFile.read().getDate();
                time = flightsFile.read().getTime();
                price = flightsFile.read().getPrice();
                seats = flightsFile.read().getSeats();

                flightsFile.getFfile().seek(flightsFile.getFfile().getFilePointer() - 2*(5L * 2 * flightsFile.getFIX_SIZE() + 8));
                flightsFile.write(id, ori, dest, date, time, price, seats);
            }

            flightsFile.write(" "," "," "," "," ",0,0);

        }


        public void flightSchedules() throws IOException {
            System.out.println("|Row|Flight Id  |Origin      |Destination |Date      |Time |price    |Seats|");
            System.out.println("............................................................................");

            flightsFile.getFfile().seek(0);
            for (int i = 0; i < flightsFile.getFfile().length() / (5L * 2 * flightsFile.getFIX_SIZE() + 8); i++) {
                System.out.printf("|%-3d|%-11s|%-12s|%-12s|%-10s|%-5s|%-,9d|%-5s|%n", i + 1, flightsFile.read().getFlightId(),
                        flightsFile.read().getOrigin(), flightsFile.read().getDestination(), flightsFile.read().getDate(),
                        flightsFile.read().getTime(), flightsFile.read().getPrice(), flightsFile.read().getSeats());
                System.out.print("............................................................................\n");
            }
        }
}

