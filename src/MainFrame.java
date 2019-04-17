import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

public class MainFrame extends JFrame {

    private Vector<String> columnNames = new Vector<>();
    private Vector<Vector> data = new Vector<>();
    private JTable table;

    MainFrame() {
        super();

        columnNames.add("Path");
        columnNames.add("Autor");
        columnNames.add("Location");
        columnNames.add("Date");
        columnNames.add("Tags");

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
                loadBase();
            }
        });
        loadBase.setText("Load Base");

        JButton saveBase = new JButton();
        saveBase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveBase();
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

        //MyTableModel tableModel = new MyTableModel(data,columnNames);

        table = new JTable(data, columnNames);
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
                viewPicture();
            }
        });
        view.setText("View");

        JButton addPic = new JButton();
        addPic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPicture(false, -1);
                System.out.println("I have been clicked");
            }
        });
        addPic.setText("Add");

        JButton edit = new JButton();
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                editPicture();
            }
        });
        edit.setText("Edit");

        JButton remove = new JButton();
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removePicture();

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

    private void editPicture() {
        int idx = table.getSelectedRow();
        if (idx == -1) {
            System.out.println("Please select a picture");
            return;
        }

        addPicture(true, idx);
    }

    private void removePicture() {
        int idx = table.getSelectedRow();
        if (idx == -1) {
            System.out.println("Please select a picture");
            return;
        }

        data.remove(idx);
        updateTable();
    }

    private void viewPicture() {
        int idx = table.getSelectedRow();
        if (idx == -1) {
            System.out.println("Please select a picture");
            return;
        }

        String path = (String) data.get(idx).get(0);
        System.out.println(path);

        //create view frame
        JFrame viewFrame = new JFrame();
        ImageIcon icon = new ImageIcon(path);
        JLabel label = new JLabel(icon);
        viewFrame.add(label);
        viewFrame.pack();
        viewFrame.setVisible(true);


    }

    private void saveBase() {
        JFileChooser fileChooser = new JFileChooser(".");

        String path = null;
        int selection = fileChooser.showSaveDialog(this);
        if (selection == JFileChooser.APPROVE_OPTION) {
            path = fileChooser.getSelectedFile().getAbsolutePath();
        } else {
            System.out.println("Save canceled");
            return;
        }

        saveToFile(path);


    }

    private void saveToFile(String path) {

        try {
            FileWriter fw = new FileWriter(path);
            for (Vector<String> row :
                    data) {
                for (String cell :
                        row) {
                    fw.append(cell);
                    fw.append(';');
                }
                fw.append("\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void loadBase() {
        String path = null;
        try {
            path = getPath();
        } catch (Exception e) {
            System.out.println("Operation Canceled");
            return;
        }
        File base = new File(path);


        StringBuilder sb = new StringBuilder();
        String text;
        try {
            Scanner scanner = new Scanner(base);
            while (scanner.hasNext()) {
                sb.append(scanner.nextLine());
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        text = sb.toString();

        setData(convertToRows(text));

    }

    private Vector<Vector> convertToRows(String allData) {
        data.clear();
        StringTokenizer st = new StringTokenizer(allData);

        Vector<String> row = new Vector<>();

        int i = 0;
        while (st.hasMoreTokens()) {
            i++;
            row.add(st.nextToken(";\n"));
            if (i % 5 == 0) {
                data.add(row);
                row = new Vector<>();
            }
        }


        return data;
    }

    private void setData(Vector<Vector> data) {
        this.data = data;
        updateTable();
    }

    private void updateTable() {
        AbstractTableModel abstractTableModel = (AbstractTableModel) table.getModel();
        abstractTableModel.fireTableDataChanged();
    }

    private String getPath() throws Exception {
        JFileChooser fileChooser = new JFileChooser(".");
        int selection = fileChooser.showOpenDialog(this);
        if (selection == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            return path;
        } else {
            throw new Exception();
        }


    }



    //opens a windows to add a picture
    private void addPicture(boolean editMode, int tableIdx) {
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

        if (editMode) {
            pathField.setText((String) data.get(tableIdx).get(0));
            autorField.setText((String) data.get(tableIdx).get(1));
            locationField.setText((String) data.get(tableIdx).get(2));
            dateField.setText((String) data.get(tableIdx).get(3));
            tagsField.setText((String) data.get(tableIdx).get(4));

        }

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

        JButton explorer = new JButton("...");
        explorer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path = null;
                try {
                    path = getPath();
                } catch (Exception e1) {
                    return;
                }
                pathField.setText(path);
            }
        });

        constraints.insets = new Insets(8, 8, 2, 8);
        constraints.gridx = 2;
        constraints.gridy = 0;
        addWindow.add(explorer, constraints);


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

                if ((path.endsWith("jpg") || path.endsWith("png")) && new File(path).exists()) {
                    if (!date.isEmpty()) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                        Date dateTime = null;
                        sdf.setLenient(false);
                        try {
                            dateTime = sdf.parse(date);
                            System.out.println(dateTime.toString());
                        } catch (ParseException e1) {
                            System.out.println("Wrong time format or invalid date, please use dd.mm.yyyy");
                            return;
                        }
                    }


                    Vector<String> newRow = new Vector<>();
                    newRow.add(path);
                    newRow.add(autor);
                    newRow.add(location);
                    newRow.add(date);
                    newRow.add(tags);
                    if (editMode) {
                        data.get(tableIdx).clear();
                        data.get(tableIdx).add(newRow.get(0));
                        data.get(tableIdx).add(newRow.get(1));
                        data.get(tableIdx).add(newRow.get(2));
                        data.get(tableIdx).add(newRow.get(3));
                        data.get(tableIdx).add(newRow.get(4));
                        updateTable();
                    } else {
                        addRow(newRow);
                    }

                    addWindow.dispose();
                } else {
                    System.out.println("Wrong file type or doesn't exist");
                    return;
                }


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

    private void addRow(Vector<String> newRow) {
        data.add(newRow);
        updateTable();
    }

}
