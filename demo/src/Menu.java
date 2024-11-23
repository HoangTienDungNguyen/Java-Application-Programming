import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Menu extends JMenuBar {
    private GamePanel gamePanel;
    private String currentLanguage = "en";
    private Map<String, String> translations;

    private JMenu languageMenu;
    private JMenu difficultyMenu;
    private JMenu othersMenu;
    private JMenuItem easyMenuItem;
    private JMenuItem mediumMenuItem;
    private JMenuItem hardMenuItem;
    private JMenuItem restartMenuItem;
    private JMenuItem helpMenuItem;
    private JMenuItem aboutMenuItem;
    private JMenuItem exitMenuItem;

    public Menu(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        translations = new HashMap<>();

        setPreferredSize(new Dimension(1920, 50));

        Font itemFont = new Font("Arial", Font.PLAIN, 18);

        languageMenu = createMenuWithIcon("Language", "icons/l.gif", itemFont);
        JMenuItem englishMenuItem = createMenuItemWithIcon("English", "icons/e.gif", itemFont);
        JMenuItem frenchMenuItem = createMenuItemWithIcon("Français", "icons/f.gif", itemFont);
        JMenuItem vietnameseMenuItem = createMenuItemWithIcon("Tiếng Việt", "icons/v.png", itemFont);

        englishMenuItem.addActionListener(e -> changeLanguage("en"));
        frenchMenuItem.addActionListener(e -> changeLanguage("fr"));
        vietnameseMenuItem.addActionListener(e -> changeLanguage("vi"));

        languageMenu.add(englishMenuItem);
        languageMenu.add(frenchMenuItem);
        languageMenu.add(vietnameseMenuItem);
        add(languageMenu);

        difficultyMenu = createMenuWithIcon("Difficulty", "icons/g.gif", itemFont);
        easyMenuItem = new JMenuItem("Easy *");
        easyMenuItem.setFont(itemFont);
        mediumMenuItem = new JMenuItem("Medium **");
        mediumMenuItem.setFont(itemFont);
        hardMenuItem = new JMenuItem("Hard ***");
        hardMenuItem.setFont(itemFont);
        restartMenuItem = createMenuItemWithIcon("Restart", "icons/r.png", itemFont);

        easyMenuItem.addActionListener(e -> gamePanel.getController().changeDifficulty(0));
        mediumMenuItem.addActionListener(e -> gamePanel.getController().changeDifficulty(1));
        hardMenuItem.addActionListener(e -> gamePanel.getController().changeDifficulty(2));
        restartMenuItem.addActionListener(e -> gamePanel.getController().restartGame());

        difficultyMenu.add(easyMenuItem);
        difficultyMenu.add(mediumMenuItem);
        difficultyMenu.add(hardMenuItem);
        difficultyMenu.add(restartMenuItem);
        add(difficultyMenu);

        othersMenu = createMenuWithIcon("Others", "icons/o.gif", itemFont);
        helpMenuItem = createMenuItemWithIcon("Help", "icons/h.gif", itemFont);
        aboutMenuItem = createMenuItemWithIcon("About", "icons/a.gif", itemFont);

        helpMenuItem.addActionListener(e -> showHelpDialog());
        aboutMenuItem.addActionListener(e -> showAboutDialog());

        othersMenu.add(helpMenuItem);
        othersMenu.add(aboutMenuItem);
        add(othersMenu);

        exitMenuItem = createMenuItemWithIcon("Exit", "icons/exit.gif", itemFont);
        exitMenuItem.addActionListener(e -> System.exit(0));
        add(exitMenuItem);
    }

    private JMenu createMenuWithIcon(String text, String iconPath, Font font) {
        JMenu menu = new JMenu(text);
        menu.setFont(font);
        menu.setIcon(new ImageIcon(iconPath));
        return menu;
    }

    private JMenuItem createMenuItemWithIcon(String text, String iconPath, Font font) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.setFont(font);
        menuItem.setIcon(new ImageIcon(iconPath));
        return menuItem;
    }

    private void changeLanguage(String language) {
        currentLanguage = language;
        loadTranslations();
        updateTexts();
    }

    private void loadTranslations() {
        translations.clear();
        switch (currentLanguage) {
            case "fr":
                translations.put("Language", "Langue");
                translations.put("Difficulty", "Difficulté");
                translations.put("Others", "Autres");
                translations.put("Help", "Aide");
                translations.put("About", "À propos");
                translations.put("Exit", "Sortir");
                translations.put("Restart", "Redémarrer");
                translations.put("Easy *", "Facile *");
                translations.put("Medium **", "Moyen **");
                translations.put("Hard ***", "Difficile ***");
                break;
            case "vi":
                translations.put("Language", "Ngôn ngữ");
                translations.put("Difficulty", "Độ khó");
                translations.put("Others", "Khác");
                translations.put("Help", "Trợ giúp");
                translations.put("About", "Thông tin");
                translations.put("Exit", "Thoát");
                translations.put("Restart", "Khởi động lại");
                translations.put("Easy *", "Dễ *");
                translations.put("Medium **", "Trung bình **");
                translations.put("Hard ***", "Khó ***");
                break;
            case "en":
            default:
                translations.put("Language", "Language");
                translations.put("Difficulty", "Difficulty");
                translations.put("Others", "Others");
                translations.put("Help", "Help");
                translations.put("About", "About");
                translations.put("Exit", "Exit");
                translations.put("Restart", "Restart");
                translations.put("Easy *", "Easy *");
                translations.put("Medium **", "Medium **");
                translations.put("Hard ***", "Hard ***");
                break;
        }
    }

    private void updateTexts() {
        languageMenu.setText(translations.get("Language"));
        difficultyMenu.setText(translations.get("Difficulty"));
        othersMenu.setText(translations.get("Others"));
        helpMenuItem.setText(translations.get("Help"));
        aboutMenuItem.setText(translations.get("About"));
        exitMenuItem.setText(translations.get("Exit"));
        restartMenuItem.setText(translations.get("Restart"));
        easyMenuItem.setText(translations.get("Easy *"));
        mediumMenuItem.setText(translations.get("Medium **"));
        hardMenuItem.setText(translations.get("Hard ***"));

        gamePanel.updateLanguage(currentLanguage);
    }

    private void showHelpDialog() {
        String helpMessage = getHelpMessage();
        JOptionPane.showMessageDialog(this, helpMessage, translations.get("Help"), JOptionPane.INFORMATION_MESSAGE);
    }

    private String getHelpMessage() {
        switch (currentLanguage) {
            case "fr":
                return "<html><body>" +
                        "<h1>Aide</h1>" +
                        "<p>Comment jouer :</p>" +
                        "<ul>" +
                        "<li>Placer les navires : Cliquez pour placer des navires sur la grille.</li>" +
                        "<li>Tour de tir : Cliquez sur la grille de l'ordinateur pour tirer.</li>" +
                        "<li>Détectez les coups : Les coups touchés sont marqués en rouge.</li>" +
                        "<li>Détectez les manqués : Les coups manqués sont marqués en bleu.</li>" +
                        "<li>Détruisez tous les navires ennemis pour gagner.</li>" +
                        "</ul>" +
                        "</body></html>";
            case "vi":
                return "<html><body>" +
                        "<h1>Trợ Giúp</h1>" +
                        "<p>Cách chơi :</p>" +
                        "<ul>" +
                        "<li>Đặt tàu: Nhấp để đặt tàu trên lưới.</li>" +
                        "<li>Lượt bắn: Nhấp vào lưới của máy tính để bắn.</li>" +
                        "<li>Phát hiện cú đánh trúng: Cú đánh trúng được đánh dấu bằng màu đỏ.</li>" +
                        "<li>Phát hiện cú đánh hụt: Cú đánh hụt được đánh dấu bằng màu xanh.</li>" +
                        "<li>Phá hủy tất cả các tàu địch để giành chiến thắng.</li>" +
                        "</ul>" +
                        "</body></html>";
            case "en":
            default:
                return "<html><body>" +
                        "<h1>Help</h1>" +
                        "<p>How to play:</p>" +
                        "<ul>" +
                        "<li>Place ships: Click to place ships on the grid.</li>" +
                        "<li>Shooting turn: Click on the computer's grid to shoot.</li>" +
                        "<li>Hit detection: Hits are marked in red.</li>" +
                        "<li>Miss detection: Misses are marked in blue.</li>" +
                        "<li>Destroy all enemy ships to win.</li>" +
                        "</ul>" +
                        "</body></html>";
        }
    }

    private void showAboutDialog() {
        String aboutMessage = "<html><body>" +
                "<h1>About</h1>" +
                "<p>Date: 7/13</p>" +
                "<p>Author: Hoang Tien Dung Nguyen</p>" +
                "<p>Version: 0.0</p>" +
                "<p>Company: CST8221</p>" +
                "</body></html>";
        JOptionPane.showMessageDialog(this, aboutMessage, translations.get("About"), JOptionPane.INFORMATION_MESSAGE);
    }
}
