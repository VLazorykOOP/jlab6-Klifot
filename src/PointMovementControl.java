import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PointMovementControl extends JFrame {
    private Point point;
    private int speed;

    public PointMovementControl() {
        point = new Point(50, 50);
        speed = 5;

        JButton increaseButton = new JButton("Збільшити швидкість");
        JButton decreaseButton = new JButton("Зменшити швидкість");

        increaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                speed += 1;
            }
        });

        decreaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (speed > 1) {
                    speed -= 1;
                }
            }
        });

        Timer timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movePoint();
                repaint();
            }
        });
        timer.start();

        setLayout(new FlowLayout());
        add(increaseButton);
        add(decreaseButton);

        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void movePoint() {
        point.x += speed;
        if (point.x > getWidth()) {
            point.x = 0;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.RED);
        g.fillOval(point.x, point.y, 20, 20);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PointMovementControl();
            }
        });
    }
}
