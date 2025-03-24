import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Form extends JFrame {
    private JPanel pnMain;
    private JTextArea taText;
    final JScrollPane scrollPane;
    private File currentFile;

    public Form() {
        pnMain = new JPanel();
        taText = new JTextArea();
        scrollPane = new JScrollPane(taText);
        pnMain.setLayout(new BorderLayout());
        pnMain.add(scrollPane, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem open = new JMenuItem("Open...");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem saveAs = new JMenuItem("Save as...");
        menu.add(open);
        menu.add(save);
        menu.add(saveAs);
        menuBar.add(menu);

        JFrame frame = new JFrame("Form");
        frame.setJMenuBar(menuBar);
        frame.setContentPane(pnMain);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setTitle("Gui se soubory");
        frame.setVisible(true);

        open.addActionListener(e -> open());
        save.addActionListener(e -> save(currentFile));
        saveAs.addActionListener(e -> saveAs());
    }

    private void open() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(pnMain);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            System.out.println("Vybrán soubor: " + currentFile.getAbsolutePath());
            try (BufferedReader reader = new BufferedReader(new FileReader(currentFile))) {
                taText.read(reader, null);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("Soubor nebyl vybrán");
        }
    }
    private void save(File file) {
        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                taText.write(writer);
                System.out.println("Uložen soubor: " + file.getAbsolutePath());
            } catch (IOException ex) {
                throw new RuntimeException("Soubor nelze uložit");
            }
        }
    }
    private void saveAs() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(pnMain);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            save(currentFile);
        } else {
            System.out.println("Soubor nelze uložit");
        }
    }

    public static void main(String[] args) {
       Form form = new Form();
    }
}
