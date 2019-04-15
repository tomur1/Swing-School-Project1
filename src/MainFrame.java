import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

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
        JPanel centerView = new JPanel();
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
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Path");
        columnNames.add("Autor");
        columnNames.add("Location");
        columnNames.add("Date");
        columnNames.add("Tags");

        Vector<Vector> data = new Vector<>();
        Vector<String> firstRow = new Vector<>();
        firstRow.add("c:/");
        firstRow.add("Kowalski");
        firstRow.add("Biesiadowo");
        firstRow.add("13 piatek");
        firstRow.add("fajne");

        data.add(firstRow);

        //MyTableModel tableModel = new MyTableModel(data,columnNames);

        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setPreferredScrollableViewportSize(centerView.getPreferredSize());
        centerView.setPreferredSize(centerView.getPreferredSize());
        centerView.add(scrollPane);




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
                addPicture();
                System.out.println("I have been clicked");
            }
        });
        addPic.setText("Add");

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

    //opens a windows to add a picture
    private void addPicture() {
        JFrame addWindow = new JFrame("Add Picture");
        GridBagLayout gridBagLayout = new GridBagLayout();

        addWindow.setLayout(gridBagLayout);


        //Initialize fields nad labels
        JLabel pathLabel = new JLabel("Path: ");
        JTextField pathField = new JTextField();

        JLabel autorLabel = new JLabel("Autor: ");
        JTextField autorField = new JTextField();

        JLabel locationLabel = new JLabel("Location: ");
        JTextField locationField = new JTextField();

        JLabel dateLabel = new JLabel("Data: ");
        JTextField dateField = new JTextField();

        JLabel tagsLabel = new JLabel("Tags: ");
        JTextField tagsField = new JTextField();

        GridBagConstraints constraints = new GridBagConstraints();
        Dimension textSize = new Dimension(200, 20);

        pathField.setPreferredSize(textSize);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(8, 8, 2, 8);
        addWindow.add(pathLabel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 0;
        addWindow.add(pathField, constraints);

        constraints.insets = new Insets(2, 8, 2, 8);

        autorField.setPreferredSize(textSize);
        constraints.gridx = 0;
        constraints.gridy = 1;
        addWindow.add(autorLabel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 1;
        addWindow.add(autorField, constraints);

        locationField.setPreferredSize(textSize);
        constraints.gridx = 0;
        constraints.gridy = 2;
        addWindow.add(locationLabel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 2;
        addWindow.add(locationField, constraints);

        dateField.setPreferredSize(textSize);
        constraints.gridx = 0;
        constraints.gridy = 3;
        addWindow.add(dateLabel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 3;
        addWindow.add(dateField, constraints);

        constraints.insets = new Insets(2, 8, 8, 8);

        tagsField.setPreferredSize(textSize);
        constraints.gridx = 0;
        constraints.gridy = 4;
        addWindow.add(tagsLabel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 4;
        addWindow.add(tagsField, constraints);


        //Initialize button
        JButton accept = new JButton("Accept");
        accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path = pathField.getText();
                String autor = autorField.getText();
                String location = locationField.getText();
                String date = dateField.getText();
                String tags = tagsField.getText();
                System.out.println(path + autor + location + date + tags);
            }
        });

        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addWindow.dispose();
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 5;
        addWindow.add(accept, constraints);
        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.anchor = GridBagConstraints.WEST;
        addWindow.add(cancel, constraints);


        addWindow.pack();
        addWindow.setVisible(true);

    }
}
