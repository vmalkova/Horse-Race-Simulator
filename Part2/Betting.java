import java.io.*;

public class Betting {
    String[] info = {"BALANCE", "BET"};

    public static void main(String[] args) {
        Betting betting = new Betting();
        betting.setBalance(100);
        betting.setBet(10);
    }

    public int getBalance() {
        String line = null;
        try (BufferedReader br = new BufferedReader(new FileReader("Part2/Betting.txt"))) {
            for (int i=0; i<2; i++) {
                line = br.readLine();
                if (info[i].equals("BALANCE")) {
                    return Integer.valueOf(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {}
        return 0;
    }

    public int getBet() {
        String line = null;
        try (BufferedReader br = new BufferedReader(new FileReader("Part2/Betting.txt"))) {
            for (int i=0; i<2; i++) {
                line = br.readLine();
                if (info[i].equals("BET")) {
                    return Integer.valueOf(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {}
        return 0;
    }

    public void setBet(int bet)
    {
        int balance = getBalance();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Part2/Betting.txt"))) {
            bw.write(balance + "\n");
            bw.write(bet + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setBalance(int balance)
    {
        int bet = getBet();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Part2/Betting.txt"))) {
            bw.write(balance + "\n");
            bw.write(bet + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addBalance()
    {
        int newBalance = getBalance() + 2 * getBet();
        System.out.println("new balance " + newBalance);
        int bet = getBet();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Part2/Betting.txt"))) {
            bw.write(newBalance + "\n");
            bw.write(bet + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void subtractBalance()
    {
        int newBalance = getBalance() - getBet();
        System.out.println("new balance " + newBalance);
        int bet = getBet();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Part2/Betting.txt"))) {
            bw.write(newBalance + "\n");
            bw.write(bet + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
