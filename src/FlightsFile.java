import java.io.IOException;
import java.io.RandomAccessFile;

public class FlightsFile {
    private RandomAccessFile ffile;
    private final int FIX_SIZE = 15;

    public FlightsFile(RandomAccessFile ffile) {
        this.ffile = ffile;
    }

    public RandomAccessFile getFfile() {
        return ffile;
    }

    public int getFIX_SIZE() {
        return FIX_SIZE;
    }

    public void write(String id, String ori, String dest, String date, String time, int price, int seats) throws IOException {
        ffile.writeChars(fixToWrite(id));
        ffile.writeChars(fixToWrite(ori));
        ffile.writeChars(fixToWrite(dest));
        ffile.writeChars(fixToWrite(date));
        ffile.writeChars(fixToWrite(time));
        ffile.writeInt(price);
        ffile.writeInt(seats);
    }

    public Flight read() throws IOException {
        return new Flight(fixToRead(), fixToRead(), fixToRead(), fixToRead(), fixToRead(), ffile.readInt(), ffile.readInt());
    }



    public String fixToWrite(String str) {
        while(str.length() < FIX_SIZE)
            str += " ";
        return str.substring(0,FIX_SIZE);
    }


    public String fixToRead() throws IOException {
        String str = "";
        for (int i = 0; i < FIX_SIZE; i++) {
            ffile.readChar();
        }
        return str.trim();
    }



    public void searchByFlightId (String flightId) throws IOException {
        for (int i = 0; i < ffile.length()/(5*2*FIX_SIZE + 8); i++) {
            ffile.seek(i*(5*2*FIX_SIZE));
            if(flightId.equals(fixToRead())) {
                ffile.seek(i*(5*2*FIX_SIZE));
                break;
            }
        }
    }

}
