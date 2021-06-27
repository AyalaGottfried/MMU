package main.java.com.hit.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CacheUnitView {
    private final PropertyChangeSupport pcls;
    private final Content content;

    public CacheUnitView() {
        pcls = new PropertyChangeSupport(this);
        content = new Content();
    }


    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pcls.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        pcls.removePropertyChangeListener(pcl);
    }

    public <T> void updateUIData(T t) {
        content.addRes(t);
    }

    public void start() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("cache unit client");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                content.setOpaque(true);
                frame.setContentPane(content);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

            }
        });

    }

    public class Content extends JPanel implements ActionListener {
        private final JTextArea text;

        public Content() {
            super(new FlowLayout());

            JButton button1 = new JButton("load request");
            button1.setActionCommand("load request");
            button1.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
            button1.setMargin(new Insets(10, 20, 10, 20));
            button1.setBackground(new Color(59, 89, 182));
            button1.setForeground(Color.WHITE);
            button1.setFocusPainted(false);

            JButton button2 = new JButton("show statistics");
            button2.setActionCommand("show statistics");
            button2.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
            button2.setMargin(new Insets(10, 20, 10, 20));
            button2.setBackground(new Color(59, 89, 182));
            button2.setForeground(Color.WHITE);
            button2.setFocusPainted(false);

            button1.addActionListener(this);
            button2.addActionListener(this);

            JPanel buttons = new JPanel();
            buttons.setBackground(new Color(255, 255, 255));
            buttons.add(button1);
            buttons.add(button2);

            text = new JTextArea(20, 50);
            text.setEditable(false);
            text.setMargin(new Insets(10, 20, 10, 20));
            text.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));

            BorderLayout layout = new BorderLayout();
            this.setLayout(layout);

            add(buttons, BorderLayout.NORTH);
            add(text, BorderLayout.CENTER);

            text.setText("Press the buttons to perform your action");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "load request":
                    JFileChooser fc = new JFileChooser("src\\main\\resources");
                    int i = fc.showOpenDialog(this);
                    if (i == JFileChooser.APPROVE_OPTION) {
                        File f = fc.getSelectedFile();
                        String filepath = f.getPath();
                        try {
                            BufferedReader br = new BufferedReader(new FileReader(filepath));
                            String s1 = "";
                            StringBuilder s2 = new StringBuilder();
                            while ((s1 = br.readLine()) != null) {
                                s2.append(s1);
                            }
                            br.close();
                            pcls.firePropertyChange("request", null, s2.toString());
                        } catch (IOException ioException) {
                            addRes("error: " + ioException);
                        }
                    }
                    break;
                case "show statistics":
                    pcls.firePropertyChange("statistics", null, "{\"headers\":{\"action\":\"STATISTICS\"}}");
                    break;
            }
        }

        public <T> void addRes(T res) {
            text.setText(res.toString());
        }


    }


}
