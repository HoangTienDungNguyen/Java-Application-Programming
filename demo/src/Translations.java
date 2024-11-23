import java.util.HashMap;
import java.util.Map;

/**
 * The Translations class provides translations for different languages.
 * It contains a map of translations for various languages.
 */
public class Translations {
    private static final Map<String, Map<String, String>> translations = new HashMap<>();

    static {
        // English Translations
        Map<String, String> en = new HashMap<>();
        en.put("language", "Language");
        en.put("difficulty", "Difficulty");
        en.put("easy", "Easy");
        en.put("medium", "Medium");
        en.put("hard", "Hard");
        en.put("restart", "Restart");
        en.put("help", "Help");
        en.put("about", "About");
        en.put("exit", "Exit");
        en.put("placeShips", "Place your ships on the second board!");
        en.put("rotate", "Use Z to rotate.");
        en.put("gameOverLoss", "Game Over! You Lost.");
        en.put("gameOverWin", "Congratulations! You Won.");
        en.put("restart", "Press R to restart.");
        en.put("attackComputer", "Attack the computer's ships!");
        en.put("destroyAllShipsToWin", "Destroy all ships to win.");
        en.put("hit", "hit");
        en.put("missed", "missed");
        en.put("destroyed", "destroyed");
        en.put("playerHitMiss", "You %s at %s %s");
        en.put("computerHitMiss", "Computer %s at %s %s");
        translations.put("en", en);

        // French Translations
        Map<String, String> fr = new HashMap<>();
        fr.put("language", "Langue");
        fr.put("difficulty", "Difficulté");
        fr.put("easy", "Facile");
        fr.put("medium", "Moyen");
        fr.put("hard", "Difficile");
        fr.put("restart", "Redémarrer");
        fr.put("help", "Aide");
        fr.put("about", "À propos");
        fr.put("exit", "Quitter");
        fr.put("placeShips", "Placez vos navires sur la deuxième grille !");
        fr.put("rotate", "Utilisez Z pour faire pivoter.");
        fr.put("gameOverLoss", "Jeu terminé! Vous avez perdu.");
        fr.put("gameOverWin", "Félicitations! Vous avez gagné.");
        fr.put("restart", "Appuyez sur R pour redémarrer.");
        fr.put("attackComputer", "Attaquez les navires de l'ordinateur!");
        fr.put("destroyAllShipsToWin", "Détruisez tous les navires pour gagner.");
        fr.put("hit", "touché");
        fr.put("missed", "raté");
        fr.put("destroyed", "détruit");
        fr.put("playerHitMiss", "Vous avez %s à %s %s");
        fr.put("computerHitMiss", "L'ordinateur a %s à %s %s");
        translations.put("fr", fr);

        // Vietnamese Translations
        Map<String, String> vi = new HashMap<>();
        vi.put("language", "Ngôn ngữ");
        vi.put("difficulty", "Độ khó");
        vi.put("easy", "Dễ");
        vi.put("medium", "Trung bình");
        vi.put("hard", "Khó");
        vi.put("restart", "Khởi động lại");
        vi.put("help", "Giúp đỡ");
        vi.put("about", "Thông tin");
        vi.put("exit", "Thoát");
        vi.put("placeShips", "Đặt tàu của bạn lên bảng thứ hai!");
        vi.put("rotate", "Sử dụng Z để xoay.");
        vi.put("gameOverLoss", "Trò chơi kết thúc! Bạn thua.");
        vi.put("gameOverWin", "Chúc mừng! Bạn đã thắng.");
        vi.put("restart", "Nhấn R để khởi động lại.");
        vi.put("attackComputer", "Tấn công các tàu của máy tính!");
        vi.put("destroyAllShipsToWin", "Phá hủy tất cả các tàu để giành chiến thắng.");
        vi.put("hit", "trúng");
        vi.put("missed", "trượt");
        vi.put("destroyed", "bị phá hủy");
        vi.put("playerHitMiss", "Bạn đã %s tại %s %s");
        vi.put("computerHitMiss", "Máy tính đã %s tại %s %s");
        translations.put("vi", vi);
    }

    /**
     * Gets the translation for the specified language and key.
     *
     * @param language The language code (e.g., "en", "fr", "vi").
     * @param key The key for the translation.
     * @return The translation string.
     */
    public static String getTranslation(String language, String key) {
        return translations.getOrDefault(language, translations.get("en")).getOrDefault(key, key);
    }
}
