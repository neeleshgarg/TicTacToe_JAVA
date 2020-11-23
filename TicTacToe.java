package TicTacToe;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;

public class TicTacToe extends JFrame implements ActionListener {
    public static int BOARD_SIZE = 3;

    public static enum GameStatus {
        Incomplete, XWins, Zwins, Tie
    }

    private JButton[][] buttons = new JButton[BOARD_SIZE][BOARD_SIZE];
    boolean crossTurn = true;

    public TicTacToe() {
        super.setTitle("TicTacToe");
        super.setSize(800, 800);
        GridLayout grid = new GridLayout(BOARD_SIZE, BOARD_SIZE);
        super.setLayout(grid);
        Font font = new Font("Comic Sans", 1, 150);
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                JButton button = new JButton("");
                buttons[row][col] = button;
                button.setFont(font);
                button.addActionListener(this);
                super.add(button);
            }
        }
        super.setResizable(false);
        super.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        makeMove(clickedButton);
        GameStatus gs = this.getGameStatus();
        if (gs == GameStatus.Incomplete)
            return;
        declareWinner(gs);

        int choice = JOptionPane.showConfirmDialog(this, "DO YOU WANT TO RESTART THE GAME");
        if (choice == JOptionPane.YES_OPTION) {
            for (int row = 0; row < BOARD_SIZE; row++)
                for (int col = 0; col < BOARD_SIZE; col++)
                    buttons[row][col].setText("");
            crossTurn = true;
        } else
            super.dispose();
    }

    private void makeMove(JButton clickedButton) {
        String btntext = clickedButton.getText();
        if (btntext.length() > 0) {
            JOptionPane.showMessageDialog(this, "Invalid Move");
        } else {
            if (crossTurn)
                clickedButton.setText("X");
            else
                clickedButton.setText("O");
            crossTurn = !crossTurn;
        }
    }

    private GameStatus getGameStatus() {
        String text1 = "";
        String text2 = "";
        int row = 0, col = 0;

        while (row < BOARD_SIZE) {
            col = 0;
            while (col < BOARD_SIZE - 1) {
                text1 = buttons[row][col].getText();
                text2 = buttons[row][col + 1].getText();
                if (!text1.equals(text2) || text1.length() == 0)
                    break;
                col++;
            }
            if (col == BOARD_SIZE - 1) {
                if (text1.equals("X"))
                    return GameStatus.XWins;
                else
                    return GameStatus.Zwins;
            }
            row++;
        }
        col = 0;
        while (col < BOARD_SIZE) {
            row = 0;
            while (row < BOARD_SIZE - 1) {
                text1 = buttons[row][col].getText();
                text2 = buttons[row + 1][col].getText();
                if (!text1.equals(text2) || text1.length() == 0)
                    break;
                row++;
            }
            if (row == BOARD_SIZE - 1) {
                if (text1.equals("X"))
                    return GameStatus.XWins;
                else
                    return GameStatus.Zwins;
            }
            col++;
        }

        // test in diagonal 1
        row = 0;
        col = 0;
        while (row < BOARD_SIZE - 1) {
            text1 = buttons[row][col].getText();
            text2 = buttons[row + 1][col + 1].getText();
            if (!text1.equals(text2) || text1.length() == 0)
                break;
            row++;
            col++;
        }
        if (row == BOARD_SIZE - 1) {
            if (text1.equals("X"))
                return GameStatus.XWins;
            else
                return GameStatus.Zwins;
        }

        // test in diagonal 2
        row = 0;
        col = BOARD_SIZE - 1;
        while (row < BOARD_SIZE - 1) {
            text1 = buttons[row][col].getText();
            text2 = buttons[row + 1][col - 1].getText();
            if (!text1.equals(text2) || text1.length() == 0)
                break;
            row++;
            col--;
        }
        if (row == BOARD_SIZE - 1) {
            if (text1.equals("X"))
                return GameStatus.XWins;
            else
                return GameStatus.Zwins;
        }

        String text = "";
        for (row = 0; row < BOARD_SIZE; row++) {
            for (col = 0; col < BOARD_SIZE; col++) {
                text = buttons[row][col].getText();
                if (text.length() == 0)
                    return GameStatus.Incomplete;
            }
        }
        return GameStatus.Tie;
    }

    private void declareWinner(GameStatus gs) {
        if (gs == GameStatus.XWins)
            JOptionPane.showMessageDialog(this, "X Wins");
        else if (gs == GameStatus.Zwins)
            JOptionPane.showMessageDialog(this, "O Wins");
        else
            JOptionPane.showMessageDialog(this, "It is a TIE");
    }

}