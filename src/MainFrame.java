import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    MainFrame() {
        super();

        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);

        //Left Side
        JPanel panelLeft = new JPanel();
        //panelLeft.setBackground(Color.CYAN);
        panelLeft.setPreferredSize(new Dimension(800, 500));
        Dimension leftPanelDim = panelLeft.getPreferredSize();
        add(panelLeft);

        GridBagLayout leftGridBagLayout = new GridBagLayout();
        panelLeft.setLayout(leftGridBagLayout);

        JPanel topButtons = new JPanel();
        JScrollPane centerView = new JScrollPane();
        JPanel bottomButtons = new JPanel();

        topButtons.setBackground(Color.GREEN);
        topButtons.setPreferredSize(new Dimension(leftPanelDim.width, leftPanelDim.height / 13));
        centerView.setBackground(Color.PINK);
        centerView.setPreferredSize(new Dimension(leftPanelDim.width, (leftPanelDim.height / 13) * 11));
        bottomButtons.setBackground(Color.YELLOW);
        bottomButtons.setPreferredSize(new Dimension(leftPanelDim.width, leftPanelDim.height / 13));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        panelLeft.add(topButtons, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        panelLeft.add(centerView, constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        panelLeft.add(bottomButtons, constraints);

        //Buttons Top Panel
        JButton loadBase = new JButton();
        loadBase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("I have been clicked");
            }
        });
        loadBase.setText("Load Base");

        JButton saveBase = new JButton();
        saveBase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("I have been clicked");
            }
        });
        saveBase.setText("Save Base");

        JButton tags = new JButton();
        tags.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("I have been clicked");
            }
        });
        tags.setText("Tags");

        JButton filter = new JButton();
        filter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("my size: " + filter.getHeight());

            }
        });
        filter.setText("Filter");

        topButtons.add(loadBase);
        topButtons.add(saveBase);
        topButtons.add(tags);
        topButtons.add(filter);

        //Center view
        String[] columnNames = new String[5];
        columnNames[0] = "Path";
        columnNames[1] = "Autor";
        columnNames[2] = "Location";
        columnNames[3] = "Date";
        columnNames[4] = "Tags";


        JTable table = new JTable();
        table.setFillsViewportHeight(true);

        centerView.add(table);


        //Buttons Bottom
        JButton view = new JButton();
        view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("I have been clicked");
            }
        });
        view.setText("View");

        JButton addPic = new JButton();
        addPic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("I have been clicked");
            }
        });
        addPic.setText("Add Picture");

        JButton edit = new JButton();
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("I have been clicked");
            }
        });
        edit.setText("Edit");

        JButton remove = new JButton();
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("my size: " + remove.getHeight());

            }
        });
        remove.setText("Remove");

        bottomButtons.add(view);
        bottomButtons.add(addPic);
        bottomButtons.add(edit);
        bottomButtons.add(remove);

        //Right Side
        JPanel panelRight = new JPanel();
        panelRight.setBackground(Color.RED);
        panelRight.setPreferredSize(new Dimension(100, 500));
        add(panelRight);


        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
