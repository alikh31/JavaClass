package com.company;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.*;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

/**
 * Created by alikh on 11/11/2014.
 */

public class MainWindow extends JFrame implements ActionListener, FocusListener{

    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JScrollPane scrollPane;
    private JTable table;
    private DefaultTableModel model;
    private JButton saveButton;
    private JButton clearButton;
    private JTextField inputField;
    private JMenuItem openItem;
    private JMenuItem saveItem;
    private JMenuItem saveAsItem;
    private JMenuItem convertItem;
    private JMenuItem exitItem;
    private JMenuItem aboutItem;
    private JEditorPane editorPane;
    private JSplitPane splitPane;
    private JEditorPane jEditorPane;

    private MyFile file;

    public MainWindow() {

        //Where the GUI is created:
        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;
        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;

        menuBar = new JMenuBar();

        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);

        openItem = new JMenuItem("Open...");
        openItem.getAccessibleContext().setAccessibleDescription("Open File from Local Computer");
        menu.add(openItem);

        saveAsItem = new JMenuItem("Save as...");
        saveAsItem.getAccessibleContext().setAccessibleDescription("Save File from Local Computer");
        menu.add(saveAsItem);

        saveItem = new JMenuItem("Save");
        saveItem.getAccessibleContext().setAccessibleDescription("Save File");
        menu.add(saveItem);

        menu.addSeparator();

        convertItem = new JMenuItem("Convert");
        convertItem.getAccessibleContext().setAccessibleDescription("Convert");
        menu.add(convertItem);

        menu.addSeparator();

        exitItem = new JMenuItem("Exit");
        exitItem.getAccessibleContext().setAccessibleDescription("Save File");
        menu.add(exitItem);

        menu = new JMenu("Help");
        menuBar.add(menu);

        aboutItem = new JMenuItem("About...");
        aboutItem.getAccessibleContext().setAccessibleDescription("About");
        menu.add(aboutItem);

        this.openItem.addActionListener(this);
        this.saveItem.addActionListener(this);
        this.saveAsItem.addActionListener(this);
        this.exitItem.addActionListener(this);
        this.aboutItem.addActionListener(this);
        this.convertItem.addActionListener(this);

        editorPane = new JEditorPane();
        editorPane.setEditable(true);

        editorPane.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {

                ConvertMDtoHTMl();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {

                ConvertMDtoHTMl();
            }

            @Override
            public void changedUpdate(DocumentEvent arg0) {

                ConvertMDtoHTMl();
            }
        });

        JScrollPane editorScrollPane = new JScrollPane(editorPane);
        editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        this.setJMenuBar(menuBar);


        //////////////////////////////////////////////////////////////////////////////////////////////////
        // create jeditorpane
        jEditorPane = new JEditorPane();

        // make it read-only
        jEditorPane.setEditable(false);

        // create a scrollpane; modify its attributes as desired
        JScrollPane scrollPane = new JScrollPane(jEditorPane);

        // add an html editor kit
        HTMLEditorKit kit = new HTMLEditorKit();
        jEditorPane.setEditorKit(kit);

        // add some styles to the html
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet = GithubStyle(styleSheet);
//        styleSheet.addRule("body {color:#000; font-family:times; margin: 4px; }");
//        styleSheet.addRule("h1 {color: blue;}");
//        styleSheet.addRule("h2 {color: #ff0000;}");
//        styleSheet.addRule("pre {font : 10px monaco; color : black; background-color : #fafafa; }");

        // create a document, set it on the jeditorpane, then add the html
        Document doc = kit.createDefaultDocument();
        jEditorPane.setDocument(doc);
        //jEditorPane.setText(htmlString);



        //////////////////////////////////////////////////////////////////////////////////////////////////

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,editorScrollPane, scrollPane);

        this.getContentPane().add(splitPane, BorderLayout.CENTER);

        splitPane.setOneTouchExpandable(true);

        addComponentListener(new ComponentAdapter(){

            @Override
            public void componentResized(ComponentEvent e) {

                    splitPane.setDividerLocation(0.5);
            }
        });


        this.setTitle("md editor");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }

    public void actionPerformed(ActionEvent e){

        this.handleEvent(e.getActionCommand());
    }

    public void focusGained(FocusEvent e){

    }

    public void focusLost(FocusEvent e){

    }

    public void handleEvent(String command) {

        if(command == "Open..."){


            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "markdown file", "md", "txt");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION) {

                file = new MyFile(chooser.getSelectedFile().getPath());
                file.read();
                editorPane.setText(file.getContent());
            }



        } else if (command == "Save") {

            JOptionPane.showMessageDialog(null, "Wrong format: at least 3 word");

        } else if (command == "Convert") {

            ConvertMDtoHTMl();
        }

    }

    private void ConvertMDtoHTMl() {

        MyFile tempFile = new MyFile("temp.md");
        tempFile.setContent(editorPane.getText());
        tempFile.save();
        jEditorPane.setText(executeCommand("pandoc -f markdown -t html temp.md"));
    }

    private void ConvertMDtoTEX() {

        MyFile tempFile = new MyFile("temp.md");
        tempFile.setContent(editorPane.getText());
        tempFile.save();
        jEditorPane.setText(executeCommand("pandoc -f markdown -t latex temp.md"));
    }

    private String executeCommand(String command) {

        StringBuffer output = new StringBuffer();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            //p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();

    }

    public static StyleSheet GithubStyle(StyleSheet from) {
        StyleSheet styleSheet = from;

        styleSheet.addRule("body { font-family: Helvetica, arial, sans-serif; font-size: 14px; line-height: 1.6; padding-top: 10px; padding-bottom: 10px; background-color: white; padding: 30px; color: #333; }");

        styleSheet.addRule("body > *:first-child { margin-top: 0 !important; }");

        styleSheet.addRule("body > *:last-child { margin-bottom: 0 !important; }");

        styleSheet.addRule("a { color: #4183C4; text-decoration: none; }");

        styleSheet.addRule("a.absent { color: #cc0000; }");

        styleSheet.addRule("a.anchor { display: block; padding-left: 30px; margin-left: -30px; cursor: pointer; position: absolute; top: 0; left: 0; bottom: 0; }");

        styleSheet.addRule("h1, h2, h3, h4, h5, h6 { margin: 20px 0 10px; padding: 0; font-weight: bold; -webkit-font-smoothing: antialiased; cursor: text; position: relative; }");

        styleSheet.addRule("h2:first-child, h1:first-child, h1:first-child + h2, h3:first-child, h4:first-child, h5:first-child, h6:first-child { margin-top: 0; padding-top: 0; }");

        styleSheet.addRule("h1:hover a.anchor, h2:hover a.anchor, h3:hover a.anchor, h4:hover a.anchor, h5:hover a.anchor, h6:hover a.anchor { text-decoration: none; }");

        styleSheet.addRule("h1 tt, h1 code { font-size: inherit; }");

        styleSheet.addRule("h2 tt, h2 code { font-size: inherit; }");

        styleSheet.addRule("h3 tt, h3 code { font-size: inherit; }");

        styleSheet.addRule("h4 tt, h4 code { font-size: inherit; }");

        styleSheet.addRule("h5 tt, h5 code { font-size: inherit; }");

        styleSheet.addRule("h6 tt, h6 code { font-size: inherit; }");

        styleSheet.addRule("h1 { font-size: 28px; color: black; }");

        styleSheet.addRule("h2 { font-size: 24px; border-bottom: 1px solid #cccccc; color: black; }");

        styleSheet.addRule("h3 { font-size: 18px; }");

        styleSheet.addRule("h4 { font-size: 16px; }");

        styleSheet.addRule("h5 { font-size: 14px; }");

        styleSheet.addRule("h6 { color: #777777; font-size: 14px; }");

        styleSheet.addRule("p, blockquote, ul, ol, dl, li, table, pre { margin: 15px 0; }");

        styleSheet.addRule("hr { background: transparent url(\"http://tinyurl.com/bq5kskr\") repeat-x 0 0; border: 0 none; color: #cccccc; height: 4px; padding: 0; }");

        styleSheet.addRule("body > h2:first-child { margin-top: 0; padding-top: 0; }");

        styleSheet.addRule("body > h1:first-child { margin-top: 0; padding-top: 0; }");

        styleSheet.addRule("body > h1:first-child + h2 { margin-top: 0; padding-top: 0; }");

        styleSheet.addRule("body > h3:first-child, body > h4:first-child, body > h5:first-child, body > h6:first-child { margin-top: 0; padding-top: 0; }");

        styleSheet.addRule("a:first-child h1, a:first-child h2, a:first-child h3, a:first-child h4, a:first-child h5, a:first-child h6 { margin-top: 0; padding-top: 0; }");

        styleSheet.addRule("h1 p, h2 p, h3 p, h4 p, h5 p, h6 p { margin-top: 0; }");

        styleSheet.addRule("li p.first { display: inline-block; }");

        styleSheet.addRule("ul, ol { padding-left: 30px; }");

        styleSheet.addRule("ul :first-child, ol :first-child { margin-top: 0; }");

        styleSheet.addRule("ul :last-child, ol :last-child { margin-bottom: 0; }");

        styleSheet.addRule("dl { padding: 0; }");

        styleSheet.addRule("dl dt { font-size: 14px; font-weight: bold; font-style: italic; padding: 0; margin: 15px 0 5px; }");

        styleSheet.addRule("dl dt:first-child { padding: 0; }");

        styleSheet.addRule("dl dt > :first-child { margin-top: 0; }");

        styleSheet.addRule("dl dt > :last-child { margin-bottom: 0; }");

        styleSheet.addRule("dl dd { margin: 0 0 15px; padding: 0 15px; }");

        styleSheet.addRule("dl dd > :first-child { margin-top: 0; }");

        styleSheet.addRule("dl dd > :last-child { margin-bottom: 0; }");

        styleSheet.addRule("blockquote { border-left: 4px solid #dddddd; padding: 0 15px; color: #777777; }");

        styleSheet.addRule("table { padding: 0; }");

        styleSheet.addRule("table tr { border-top: 1px solid #cccccc; background-color: white; margin: 0; padding: 0; }");

        styleSheet.addRule("table tr:nth-child(2n) { background-color: #f8f8f8; }");

        styleSheet.addRule("table tr th { font-weight: bold; border: 1px solid #cccccc; text-align: left; margin: 0; padding: 6px 13px; }");

        styleSheet.addRule("table tr td { border: 1px solid #cccccc; text-align: left; margin: 0; padding: 6px 13px; }");

        styleSheet.addRule("table tr th :first-child, table tr td :first-child { margin-top: 0; }");

        styleSheet.addRule("table tr th :last-child, table tr td :last-child { margin-bottom: 0; }");

        styleSheet.addRule("img { max-width: 100%; }");

        styleSheet.addRule("span.frame { display: block; overflow: hidden; }");

        styleSheet.addRule("span.frame > span { border: 1px solid #dddddd; display: block; float: left; overflow: hidden; margin: 13px 0 0; padding: 7px; width: auto; }");

        styleSheet.addRule("span.frame span img { display: block; float: left; }");

        styleSheet.addRule("span.frame span span { clear: both; color: #333333; display: block; padding: 5px 0 0; }");

        styleSheet.addRule("span.align-center { display: block; overflow: hidden; clear: both; }");

        styleSheet.addRule("span.align-center > span { display: block; overflow: hidden; margin: 13px auto 0; text-align: center; }");

        styleSheet.addRule("span.align-center span img { margin: 0 auto; text-align: center; }");

        styleSheet.addRule("span.align-right { display: block; overflow: hidden; clear: both; }");

        styleSheet.addRule("span.align-right > span { display: block; overflow: hidden; margin: 13px 0 0; text-align: right; }");

        styleSheet.addRule("span.align-right span img { margin: 0; text-align: right; }");

        styleSheet.addRule("span.float-left { display: block; margin-right: 13px; overflow: hidden; float: left; }");

        styleSheet.addRule("span.float-left span { margin: 13px 0 0; }");

        styleSheet.addRule("span.float-right { display: block; margin-left: 13px; overflow: hidden; float: right; }");

        styleSheet.addRule("span.float-right > span { display: block; overflow: hidden; margin: 13px auto 0; text-align: right; }");

        styleSheet.addRule("code, tt { margin: 0 2px; padding: 0 5px; white-space: nowrap; border: 1px solid #eaeaea; background-color: #f8f8f8; border-radius: 3px; }");

        styleSheet.addRule("pre code { margin: 0; padding: 0; white-space: pre; border: none; background: transparent; }");

        styleSheet.addRule(".highlight pre { background-color: #f8f8f8; border: 1px solid #cccccc; font-size: 13px; line-height: 19px; overflow: auto; padding: 6px 10px; border-radius: 3px; }");

        styleSheet.addRule("pre { background-color: #f8f8f8; border: 1px solid #cccccc; font-size: 13px; line-height: 19px; overflow: auto; padding: 6px 10px; border-radius: 3px; }");

        styleSheet.addRule("pre code, pre tt { background-color: transparent; border: none; }");

        return styleSheet;
    }
}
