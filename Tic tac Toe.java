import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class TicTacToeAI extends JFrame implements ActionListener {

    JButton[] buttons = new JButton[9];
    boolean playerTurn = true;
    Random rand = new Random();

    Color bgColor = new Color(30, 30, 30);
    Color btnColor = new Color(50, 50, 50);
    Color xColor = new Color(0, 200, 255);   // Cyan (Player)
    Color oColor = new Color(255, 100, 100); // Soft Red (Computer)

    public TicTacToeAI() {
        setTitle("Tic Tac Toe - Modern UI");
        setSize(350, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(bgColor);

        JPanel panel = new JPanel(new GridLayout(3, 3, 10, 10));
        panel.setBackground(bgColor);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Font font = new Font("Arial", Font.BOLD, 42);

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(font);
            buttons[i].setFocusPainted(false);
            buttons[i].setBackground(btnColor);
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
            buttons[i].addActionListener(this);
            panel.add(buttons[i]);
        }

        JButton resetBtn = new JButton("Restart Game");
        resetBtn.setFont(new Font("Arial", Font.BOLD, 16));
        resetBtn.setBackground(new Color(70, 130, 180));
        resetBtn.setForeground(Color.WHITE);
        resetBtn.setFocusPainted(false);
        resetBtn.addActionListener(e -> resetGame());

        add(panel, BorderLayout.CENTER);
        add(resetBtn, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();

        if (!btn.getText().equals("") || !playerTurn) return;

        btn.setText("X");
        btn.setForeground(xColor);
        playerTurn = false;

        if (!checkWinner()) {
            // ⏱️ Delay computer move (700 ms)
            Timer timer = new Timer(700, ev -> computerMove());
            timer.setRepeats(false);
            timer.start();
        }
    }

    void computerMove() {
        ArrayList<Integer> empty = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            if (buttons[i].getText().equals("")) {
                empty.add(i);
            }
        }

        if (empty.size() == 0) return;

        int move = empty.get(rand.nextInt(empty.size()));
        buttons[move].setText("O");
        buttons[move].setForeground(oColor);

        playerTurn = true;
        checkWinner();
    }

    boolean checkWinner() {
        int[][] win = {
            {0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},
            {0,4,8},{2,4,6}
        };

        for (int[] w : win) {
            String a = buttons[w[0]].getText();
            String b = buttons[w[1]].getText();
            String c = buttons[w[2]].getText();

            if (!a.equals("") && a.equals(b) && b.equals(c)) {
                highlightWin(w);
                JOptionPane.showMessageDialog(this, a + " Wins!");
                resetGame();
                return true;
            }
        }

        boolean draw = true;
        for (JButton b : buttons) {
            if (b.getText().equals("")) {
                draw = false;
                break;
            }
        }

        if (draw) {
            JOptionPane.showMessageDialog(this, "Draw!");
            resetGame();
            return true;
        }

        return false;
    }

    void highlightWin(int[] w) {
        for (int i : w) {
            buttons[i].setBackground(new Color(100, 200, 100)); // green highlight
        }
    }

    void resetGame() {
        for (JButton b : buttons) {
            b.setText("");
            b.setBackground(btnColor);
        }
        playerTurn = true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicTacToeAI());
    }
}
