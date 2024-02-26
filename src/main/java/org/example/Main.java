package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Main {

    static JPanel panel = new JPanel();
    private static final String TODO_KEY = "todos";

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JPanel topPanel = new JPanel();

        JButton enterButton = new JButton("Add");
        JTextArea enterTodo = new JTextArea(1, 25);
        String username = System.getenv("USERNAME");
        JLabel toptext = new JLabel("Welcome, " + username);

        toptext.setForeground(Color.white);
        Font topFont = toptext.getFont();
        float topSize = topFont.getSize() + 12.0f;
        toptext.setFont(topFont.deriveFont(topSize));

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.setResizable(false);
        frame.setSize(800, 800);
        frame.getContentPane().setBackground(new Color(27, 27, 29));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        topPanel.setBackground(new Color(27, 27, 29));
        topPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));

        enterTodo.setBackground(new Color(56, 56, 59));
        enterTodo.setForeground(Color.white);
        Font font = enterTodo.getFont();
        float size = font.getSize() + 9.0f;
        enterTodo.setFont(font.deriveFont(size));
        enterTodo.setCaretColor(Color.WHITE);
        enterTodo.setLineWrap(true);
        enterTodo.setMargin(new Insets(0, 5, 0, 5));

        enterButton.setBackground(new Color(111, 111, 119));
        enterButton.setFocusable(false);
        enterButton.setBorderPainted(false);
        enterButton.setForeground(Color.white);
        Font font1 = enterButton.getFont();
        float size1 = font1.getSize() + 8.0f;
        enterButton.setFont(font1.deriveFont(size1));
        enterButton.setFont(font1.deriveFont(Font.BOLD));



        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.setBorder(BorderFactory.createEmptyBorder(100, 100, 400, 100));
        panel.setBackground(new Color(27, 27, 29));

        panel.add(enterTodo);
        panel.add(enterButton);

        loadTodos(panel);
        topPanel.add(toptext);

        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextArea newTextArea = new JTextArea(1, 25);
                newTextArea.setText(enterTodo.getText());
                Font font1 = enterButton.getFont();
                float size1 = font1.getSize() + 6.0f;
                newTextArea.setFont(font1.deriveFont(size1));
                newTextArea.setBackground(new Color(167, 167, 171));
                newTextArea.setMargin(new Insets(0, 5, 0, 5));
                panel.add(newTextArea);
                JButton removeButton = new JButton("X");
                removeButton.setBackground(new Color(111, 111, 119));
                removeButton.setFocusable(false);
                removeButton.setBorderPainted(false);
                removeButton.setForeground(Color.white);
                Font font3 = removeButton.getFont();
                float size3 = font1.getSize() + 8.0f;
                removeButton.setFont(font1.deriveFont(size3));
                removeButton.setFont(font1.deriveFont(Font.BOLD));
                panel.add(removeButton);
                removeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Container parentobject = newTextArea.getParent();
                        parentobject.remove(newTextArea);
                        parentobject.remove(removeButton);
                        parentobject.validate();
                        parentobject.repaint();
                    }
                });
                saveTodos(panel);
                panel.revalidate();
                panel.repaint();
                enterTodo.setText("");
            }
        });

        frame.setVisible(true);
    }

    private static void saveTodos(JPanel panel) {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        System.out.println("Saving preferences to: " + prefs.absolutePath());

        StringBuilder todos = new StringBuilder();
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component instanceof JTextArea) {
                JTextArea textArea = (JTextArea) component;
                todos.append(textArea.getText()).append(",");
            }
        }

        System.out.println("Saved todos: " + todos.toString());

        prefs.put(TODO_KEY, todos.toString());
        try {
            prefs.flush();
            System.out.println("Preferences flushed successfully.");
        } catch (BackingStoreException e) {
            e.printStackTrace();
            System.err.println("Error flushing preferences: " + e.getMessage());
        }
    }

    private static void loadTodos(JPanel panel) {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        System.out.println("Loading preferences from: " + prefs.absolutePath());

        String todos = prefs.get(TODO_KEY, "");
        System.out.println("Loaded todos: " + todos);

        String[] todoArray = todos.split(",");
        for (String todo : todoArray) {
            if (!todo.isEmpty()) {
                JTextArea newTextArea = new JTextArea(1, 25);
                newTextArea.setText(todo);
                Font font1 = newTextArea.getFont();
                float size1 = font1.getSize() + 6.0f;
                newTextArea.setFont(font1.deriveFont(size1));
                newTextArea.setBackground(new Color(167, 167, 171));
                newTextArea.setMargin(new Insets(0, 5, 0, 5));
                panel.add(newTextArea);
                JButton removeButton = new JButton("X");
                removeButton.setBackground(new Color(111, 111, 119));
                removeButton.setFocusable(false);
                removeButton.setBorderPainted(false);
                removeButton.setForeground(Color.white);
                Font font3 = removeButton.getFont();
                float size3 = font1.getSize() + 8.0f;
                removeButton.setFont(font1.deriveFont(size3));
                removeButton.setFont(font1.deriveFont(Font.BOLD));
                panel.add(removeButton);
                removeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Container parentobject = newTextArea.getParent();
                        parentobject.remove(newTextArea);
                        parentobject.remove(removeButton);
                        parentobject.validate();
                        parentobject.repaint();
                    }
                });
            }
        }
    }


}

