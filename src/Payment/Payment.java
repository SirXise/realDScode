package Payment;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Payment implements Comparable<Payment> {

    private Long time;
    private String txnId;
    private Integer rank;

    //initialising Priority Queue
    static PriorityQueue<Payment> tran = new PriorityQueue<>();

    //getting variables for comparison
    public Payment(Long time, String txnId, int rank) {

        this.rank = rank;
        this.txnId = txnId;
        this.time = time;
    }

    @Override
    //compare func
    public int compareTo(Payment o) {
        if (this.time.compareTo(o.time) == 0) {
            return this.rank.compareTo(o.rank);
        } else return this.time.compareTo(o.time);
    }

    @Override
    //to return string
    public String toString() {
        return this.txnId;
    }

    //Driver
    public static void main(String[] args) {
        //initialising some variables
        Long epoch;
        String txnId, tier;

        Long Fepoch = 0L;
        Long elapsed = 0L;
        Long Tepoch;
        int rank;
        Long time;
        Long diff;

        //scanning user input
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            try {
                String data = in.nextLine();

                //for EXIT
                if (data.equalsIgnoreCase("EXIT")) {
                    break;
                }
                //for REBOOT
                else if (data.equalsIgnoreCase("REBOOT")) {
                    tran.clear();
                }
                //for input
                else {

                    //saparating input by white space with limit of 3 into array
                    String[] input = data.split("\\s", 3);
                    //assigning each element of array into their variable
                    epoch = Long.valueOf(input[0]);
                    txnId = input[1];
                    tier = input[2];

                    //to get the Fepoch of the first transaction
                    //since Fepoch was initialised as 0L
                    //if the first transaction doesn't enter this if statement
                    //the diff will always be greater than 1000
                    if (tran.isEmpty()) {
                        //if the Fepoch was rounded up
                        Fepoch = (long) (Math.round(epoch / 1000d) * 1000d);
                        if (epoch < Fepoch) {
                            Fepoch -= 1000L;
                        }
                    }
                    //assign Tepoch and calculate diff and time
                    Tepoch = epoch;
                    diff = Tepoch - Fepoch;
                    //elapsed is the time difference from first iteration(before first output) of program
                    //to the current(next output) iteration
                    time = elapsed - (1000L - diff);

                    //calculating their time based on tier
                    //assigning rank
                    if (tier.equalsIgnoreCase("PLATINUM")) {
                        time = time - 3000L;
                        rank = 3;
                    } else if (tier.equalsIgnoreCase("GOLD")) {
                        time = time - 2000L;
                        rank = 2;
                    } else if (tier.equalsIgnoreCase("SILVER")) {
                        time = time - 1000L;
                        rank = 1;
                    } else {
                        time = time - 0L;
                        rank = 0;
                    }

                    //adding the input into tran Priority Queue
                    Payment transactionObj = new Payment(time, txnId, rank);
                    tran.add(transactionObj);

                    //to see if it has been at least 1001 millisecond from the previous output
                    // or start of the program
                    if (diff > 1000) {
                        String result = "";
                        int i = 0;
                        //joining the 100 transaction into 1 string
                        while (!tran.isEmpty() && i < 100) {
                            result += tran.poll() + " ";
                            i++;
                        }

                        //GUI to see how much payment was successful
                        JFrame frame = new JFrame();
                        JPanel panel = new JPanel();

                        frame.setSize(400, 150);
                        frame.setTitle("Congrats");
                        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

                        frame.add(panel);
                        panel.setLayout(null);

                        JLabel labeltitle = new JLabel(i+" transaction was successfully cleared",SwingConstants.CENTER);
                        labeltitle.setBounds(10, 25, 300, 20);
                        panel.add(labeltitle);

                        JButton button = new JButton("OK");
                        //button.setHorizontalAlignment(SwingConstants.CENTER);
                        button.setBounds(10, 50, 200, 20);
                        button.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                frame.setVisible(false);
                                frame.dispose();
                            }
                        });
                        panel.add(button);

                        frame.setVisible(true);

                        //display the ouput
                        System.out.println(result);
                        //changing the Fepoch for the next iteration
                        Fepoch = (long) (Math.round(Tepoch / 1000d) * 1000d);
                        //if the Fepoch was rounded up
                        if (Tepoch < Fepoch) {
                            Fepoch -= 1000L;
                        }
                        //elapsed increment since 1000 millisecond have pass
                        elapsed += 1000L;
                    }
                }
            } catch (InputMismatchException e) {
                return;
            }

        }
    }
}
