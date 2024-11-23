import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JWindow {
    public SplashScreen() {
        setSize(800, 370);
        setLocationRelativeTo(null);

        JPanel content = (JPanel) getContentPane();
        content.setBackground(Color.white);

        JLabel label = new JLabel(new ImageIcon("assets/splash_image.png"));
        content.add(label, BorderLayout.CENTER);

        JLabel text = new JLabel("Loading...", JLabel.CENTER);
        text.setFont(new Font("Algerian", Font.BOLD, 24));
        content.add(text, BorderLayout.SOUTH);

        setVisible(true);

        Timer timer = new Timer(5000, e -> {
            setVisible(false);
            dispose();
            SwingUtilities.invokeLater(Game::new);
        });
        timer.setRepeats(false);
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SplashScreen::new);
    }
}
