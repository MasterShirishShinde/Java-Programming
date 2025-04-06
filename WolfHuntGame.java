import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WolfHuntGame extends JPanel implements ActionListener, KeyListener {
    private static final int PANEL_WIDTH = 800;
    private static final int PANEL_HEIGHT = 600;
    private static final int WOLF_SIZE = 50;
    private static final int PREY_SIZE = 30;
    private static final int WOLF_SPEED = 10;

    private Timer timer;
    private int wolfX = 100, wolfY = 100;
    private int preyX = 300, preyY = 300;
    private int score = 0;
    private boolean caughtPrey = false;

    public WolfHuntGame() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Color.GREEN);
        setFocusable(true);
        addKeyListener(this);

        timer = new Timer(100, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the wolf
        g.setColor(Color.DARK_GRAY);
        g.fillRoundRect(wolfX, wolfY, WOLF_SIZE, WOLF_SIZE, 15, 15);

        // Draw the prey
        if (!caughtPrey) {
            g.setColor(Color.ORANGE);
            g.fillOval(preyX, preyY, PREY_SIZE, PREY_SIZE);
        }

        // Draw score
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Score: " + score, 20, 30);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        checkCollision();
        repaint();
    }

    private void checkCollision() {
        Rectangle wolfRect = new Rectangle(wolfX, wolfY, WOLF_SIZE, WOLF_SIZE);
        Rectangle preyRect = new Rectangle(preyX, preyY, PREY_SIZE, PREY_SIZE);

        if (wolfRect.intersects(preyRect)) {
            caughtPrey = true;
            score += 10;

            // Respawn prey at random location
            preyX = (int) (Math.random() * (PANEL_WIDTH - PREY_SIZE));
            preyY = (int) (Math.random() * (PANEL_HEIGHT - PREY_SIZE));
            caughtPrey = false;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_LEFT -> wolfX = Math.max(wolfX - WOLF_SPEED, 0);
            case KeyEvent.VK_RIGHT -> wolfX = Math.min(wolfX + WOLF_SPEED, PANEL_WIDTH - WOLF_SIZE);
            case KeyEvent.VK_UP -> wolfY = Math.max(wolfY - WOLF_SPEED, 0);
            case KeyEvent.VK_DOWN -> wolfY = Math.min(wolfY + WOLF_SPEED, PANEL_HEIGHT - WOLF_SIZE);
        }

        repaint();
    }

    @Override public void keyReleased(KeyEvent e) { }
    @Override public void keyTyped(KeyEvent e) { }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("🐺 Wolf Hunt Adventure 🐇");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new WolfHuntGame());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
