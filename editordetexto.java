import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class TextEditor extends JFrame implements ActionListener {
    private JTextPane textPane;
    private JFileChooser fileChooser;
    private SimpleAttributeSet attributes;
    private Color currentColor;

    public TextEditor() {
        // Configurar la ventana principal
        setTitle("Editor de Texto");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Configurar el área de texto
        textPane = new JTextPane(); // Cambiado a JTextPane
        JScrollPane scrollPane = new JScrollPane(textPane);
        add(scrollPane, BorderLayout.CENTER);

        // Crear la barra de menú
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Menú File
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.addActionListener(this);
        fileMenu.add(saveMenuItem);

        JMenuItem saveAsMenuItem = new JMenuItem("Save as");
        saveAsMenuItem.addActionListener(this);
        fileMenu.add(saveAsMenuItem);

        JMenuItem printMenuItem = new JMenuItem("Print");
        printMenuItem.addActionListener(this);
        fileMenu.add(printMenuItem);

        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.addActionListener(this);
        fileMenu.add(openMenuItem);

        // Menú Edit
        JMenu editMenu = new JMenu("Edit");
        menuBar.add(editMenu);

        JMenuItem copyMenuItem = new JMenuItem("Copy");
        copyMenuItem.addActionListener(this);
        editMenu.add(copyMenuItem);

        JMenuItem pasteMenuItem = new JMenuItem("Paste");
        pasteMenuItem.addActionListener(this);
        editMenu.add(pasteMenuItem);

        JMenuItem compileMenuItem = new JMenuItem("Compile");
        compileMenuItem.addActionListener(this);
        editMenu.add(compileMenuItem);

        JMenuItem runMenuItem = new JMenuItem("Run");
        runMenuItem.addActionListener(this);
        editMenu.add(runMenuItem);

        // Menú Help
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);

        JMenuItem aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.addActionListener(this);
        helpMenu.add(aboutMenuItem);

        // Inicializar el objeto JFileChooser
        fileChooser = new JFileChooser();

        // Configuración de los atributos de color
        attributes = new SimpleAttributeSet();
        currentColor = Color.BLACK; // Color inicial del texto

        // Añadir un DocumentListener al textArea
        textPane.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                updateTextStyles();
            }

            public void removeUpdate(DocumentEvent e) {
                updateTextStyles();
            }

            public void changedUpdate(DocumentEvent e) {
                updateTextStyles();
            }
        });

        // Mostrar la ventana
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("Save")) {
            saveFile();
        } else if (command.equals("Save as")) {
            saveFileAs();
        } else if (command.equals("Print")) {
            printFile();
        } else if (command.equals("Open")) {
            openFile();
        } else if (command.equals("Copy")) {
            textPane.copy();
        } else if (command.equals("Paste")) {
            textPane.paste();
        } else if (command.equals("Compile")) {
            // Implementa la funcionalidad de Compile aquí
        } else if (command.equals("Run")) {
            // Implementa la funcionalidad de Run aquí
        } else if (command.equals("About")) {
            JOptionPane.showMessageDialog(this, "Autores:" +
                    "\nErik Leonel Lopez Peralta" +
                    "\nDavid Armenta Marquez" +
                    "\nJasiel Alfredo Lopez Manzanares" +
                    "\nCorreo: " +
                    "\na20490219@itmexicali.edu.mx" +
                    "\na20490219@itmexicali.edu.mx" +
                    "\na20490219@itmexicali.edu.mx"
            );
        }
    }

    private void saveFile() {
        // Implementa la lógica para guardar el archivo aquí
    }

    private void saveFileAs() {
        int returnValue = fileChooser.showSaveDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            // Implementa la lógica para guardar el archivo con el nombre selectedFile aquí
        }
    }

    private void printFile() {
        // Implementa la lógica para imprimir el archivo aquí
    }

    private void openFile() {
        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
                textPane.read(reader, null);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateTextStyles() {
        SwingUtilities.invokeLater(() -> {
            try {
                String text = textPane.getText();
                int len = text.length();
                int start = 0;

                for (int i = 0; i < len; i++) {
                    if (text.charAt(i) == '#') {
                        applyTextStyle(start, i, currentColor);
                        start = i + 1;
                        currentColor = getNextColor(text, i + 1);
                    }
                }
                applyTextStyle(start, len, currentColor); // Aplicar estilo al último segmento
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private Color getColorForChar(char ch) {
        switch (ch) {
            case 'a': return Color.RED;
            case 'e': return Color.BLUE;
            case 'i': return Color.YELLOW;
            case 'o': return Color.GREEN;
            case 'u': return Color.MAGENTA;
            default: return Color.BLACK;
        }
    }

    private Color getNextColor(String text, int start) {
        for (int i = start; i < text.length(); i++) {
            char ch = text.charAt(i);
            if ("aeiou".indexOf(ch) >= 0) {
                return getColorForChar(ch);
            }
        }
        return Color.BLACK; // Color por defecto si no se encuentra vocal
    }

    private void applyTextStyle(int start, int end, Color color) {
        StyledDocument doc = textPane.getStyledDocument();
        StyleConstants.setForeground(attributes, color);
        doc.setCharacterAttributes(start, end - start, attributes, false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TextEditor());
    }
}

