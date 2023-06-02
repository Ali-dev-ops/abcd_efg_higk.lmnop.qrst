import java.io.IOException;
import java.io.RandomAccessFile;

public class UsersFile {
    private RandomAccessFile ufile;
    private final int FIX_SIZE = 15;

    public UsersFile(RandomAccessFile ufile) {
        this.ufile = ufile;
    }

    public RandomAccessFile getUfile() {
        return ufile;
    }

    public int getFIX_SIZE() {
        return FIX_SIZE;
    }

    public void write(String username, String password) throws IOException {
        ufile.writeChars(fixToWrite(username));
        ufile.writeChars(fixToWrite(password));
        ufile.writeInt(0);
    }

    public Passenger read() throws IOException {
        String username = fixToRead();
        String password = fixToRead();
        int charge = ufile.readInt();
        return new Passenger(username,password,charge);
    }


    public String fixToWrite(String str) {
        while (str.length() < FIX_SIZE)
            str += " ";

        return str.substring(0,FIX_SIZE);
    }

    public String fixToRead() throws IOException {
        String str = "";
        for (int i = 0; i < FIX_SIZE; i++) {
            str += ufile.readChar();
        }
        return str.trim();
    }

    public boolean searchUser(String username, String password) throws IOException {
        boolean b = false;
        for (int i = 0; i < ufile.length()/(2*2*FIX_SIZE + 4); i++) {
            ufile.seek(i*(2*2*FIX_SIZE + 4));
            if (username.equals(read().getUsername()) && password.equals(read().getPassword())) {
                b = true;
                ufile.seek(i*(2*2*FIX_SIZE + 4));
                break;
            }
        }
        return b;
    }

}
