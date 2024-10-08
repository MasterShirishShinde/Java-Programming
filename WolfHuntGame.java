import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WolfHuntGame extends JPanel implements ActionListener, KeyListener {
    Timer timer;
    int wolfX = 100, wolfY = 100; 
    int preyX = 300, preyY = 300;
    int score = 0; 
    boolean caughtPrey = false;
    
    public WolfHuntGame() {
        timer = new Timer(100, this);
        timer.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
 
        g.setColor(Color.green);
        g.fillRect(0, 0, 800, 600);

        g.setColor(Color.gray);
        g.fillRect(wolfX, wolfY, 50, 50);

        if (!caughtPrey) {
            g.setColor(Color.orange);
            g.fillRect(preyX, preyY, 30, 30);
        }

        g.setColor(Color.black);
        g.drawString("Score: " + score, 20, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (caughtPrey) {
   
            preyX = (int) (Math.random() * 750);
            preyY = (int) (Math.random() * 550);
            caughtPrey = false;
        }

        if (new Rectangle(wolfX, wolfY, 50, 50).intersects(new Rectangle(preyX, preyY, 30, 30))) {
            caughtPrey = true;
            score += 10;
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            wolfX = Math.max(wolfX - 10, 0); 
        }
        if (key == KeyEvent.VK_RIGHT) {
            wolfX = Math.min(wolfX + 10, 750);
        }
        if (key == KeyEvent.VK_UP) {
            wolfY = Math.max(wolfY - 10, 0); 
        }
        if (key == KeyEvent.VK_DOWN) {
            wolfY = Math.min(wolfY + 10, 550); 
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyTyped(KeyEvent e) { }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Wolf Hunt Adventure");
        WolfHuntGame game = new WolfHuntGame();
        frame.add(game);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
