import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main4GUI extends JFrame {
    private JTextField dimensionTextField;
    private JTextArea matrixTextArea;
    private JButton calculateButton;

    public Main4GUI() {
        setTitle("Main4 GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for input
        JPanel inputPanel = new JPanel(new FlowLayout());
        JLabel dimensionLabel = new JLabel("Розмірність матриці n (n <= 20):");
        dimensionTextField = new JTextField(10);
        inputPanel.add(dimensionLabel);
        inputPanel.add(dimensionTextField);

        // Text area for matrix input
        matrixTextArea = new JTextArea(10, 20);
        matrixTextArea.setBorder(BorderFactory.createTitledBorder("Елементи матриці:"));

        // Button to perform calculations
        calculateButton = new JButton("Обчислити");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performCalculations();
            }
        });

        // Add components to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(matrixTextArea), BorderLayout.CENTER);
        add(calculateButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void performCalculations() {
        try {
            int n = Integer.parseInt(dimensionTextField.getText());
            if (n <= 0 || n > 20) {
                JOptionPane.showMessageDialog(this, "Некоректна розмірність матриці. Введіть n в межах [1, 20].");
                return;
            }

            int[][] matrix = parseMatrixInput(matrixTextArea.getText(), n);

            int maxSumOfAbsoluteValues = Integer.MIN_VALUE;
            int minColumnValue = Integer.MAX_VALUE;
            int resultColumn = 0;

            for (int j = 0; j < n; j++) {
                int sumOfAbsoluteValues = 0;

                for (int i = 0; i < n; i++) {
                    sumOfAbsoluteValues += Math.abs(matrix[i][j]);
                }

                if (sumOfAbsoluteValues > maxSumOfAbsoluteValues || (sumOfAbsoluteValues == maxSumOfAbsoluteValues && matrix[0][j] < minColumnValue)) {
                    maxSumOfAbsoluteValues = sumOfAbsoluteValues;
                    minColumnValue = matrix[0][j];
                    resultColumn = j;
                }
            }

            JOptionPane.showMessageDialog(this, "Найменше значення серед елементів стовпчика з найбільшою сумою модулів: " + minColumnValue);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Помилка при зчитуванні розмірності матриці. Введіть коректне значення.");
        } catch (MatrixInputException ex) {
            JOptionPane.showMessageDialog(this, "Помилка при зчитуванні матриці. Перевірте правильність введених даних.");
        }
    }

    private int[][] parseMatrixInput(String input, int n) throws MatrixInputException {
        int[][] matrix = new int[n][n];
        Scanner scanner = new Scanner(input);

        try {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (scanner.hasNextInt()) {
                        matrix[i][j] = scanner.nextInt();
                    } else {
                        throw new MatrixInputException("Недостатньо елементів для заповнення матриці.");
                    }
                }
            }
        } catch (Exception ex) {
            throw new MatrixInputException("Помилка при зчитуванні матриці.");
        } finally {
            scanner.close();
        }

        return matrix;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main4GUI().setVisible(true);
            }
        });
    }
}

class MatrixInputException extends Exception {
    public MatrixInputException(String message) {
        super(message);
    }
}
