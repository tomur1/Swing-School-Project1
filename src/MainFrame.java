import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class MainFrame extends JFrame {

    private Vector<String> columnNames = new Vector<>();
    private Vector<Vector> data = new Vector<>();
    private JTable table;
    private double scale;
    private JFrame frame = new JFrame();
    private static final int PATH = 0;
    private static final int AUTOR = 1;
    private static final int LOCATION = 2;
    private static final int DATE = 3;
    private static final int TAGS = 4;
    MainFrame() {
        super();
        scale = 1.2;

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
        panelLeft.setPreferredSize(new Dimension((int) (800 * scale), (int) (500 * scale)));
        Dimension leftPanelDim = panelLeft.getPreferredSize();
        add(panelLeft);

        GridBagLayout leftGridBagLayout = new GridBagLayout();
        panelLeft.setLayout(leftGridBagLayout);

        JPanel topButtons = new JPanel();
        JPanel centerView = new JPanel();
        JPanel bottomButtons = new JPanel();

        //topButtons.setBackground(Color.GREEN);
        topButtons.setPreferredSize(new Dimension(leftPanelDim.width, leftPanelDim.height / 13));
        //centerView.setBackground(Color.PINK);
        centerView.setPreferredSize(new Dimension(leftPanelDim.width, (leftPanelDim.height / 13) * 11));
        //bottomButtons.setBackground(Color.YELLOW);
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

        JButton filter = new JButton();
        filter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filter();

            }
        });
        filter.setText("Filter");

        JButton resetFilter = new JButton();
        resetFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFilter();
            }
        });
        resetFilter.setText("Reset Filter");

        topButtons.add(loadBase);
        topButtons.add(saveBase);
        topButtons.add(filter);
        topButtons.add(resetFilter);

        table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);
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
                addPicture(WindowMode.ADD, -1);
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
        //panelRight.setBackground(Color.RED);
        panelRight.setPreferredSize(new Dimension((int) (100 * scale), (int) (500 * scale)));
        add(panelRight);

        GridBagLayout gridBagLayoutRight = new GridBagLayout();
        panelRight.setLayout(gridBagLayoutRight);

        Dimension rightPanelButtonsSize = new Dimension(100, 23);


        JButton minAutor = new JButton("Min Autor");
        minAutor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                find(SearchEnum.MIN, SearchEnum.AUTOR);
            }
        });
        minAutor.setPreferredSize(rightPanelButtonsSize);

        JButton minLocation = new JButton("Min Location");
        minLocation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                find(SearchEnum.MIN, SearchEnum.LOCATION);
            }
        });
        minLocation.setPreferredSize(rightPanelButtonsSize);

        JButton minDate = new JButton("Min Date");
        minDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                find(SearchEnum.MIN, SearchEnum.DATE);
            }
        });
        minDate.setPreferredSize(rightPanelButtonsSize);

        JButton maxAutor = new JButton("Max Autor");
        maxAutor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                find(SearchEnum.MAX, SearchEnum.AUTOR);
            }
        });
        maxAutor.setPreferredSize(rightPanelButtonsSize);

        JButton maxLocation = new JButton("Max Location");
        maxLocation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                find(SearchEnum.MAX, SearchEnum.LOCATION);
            }
        });
        maxLocation.setPreferredSize(rightPanelButtonsSize);

        JButton maxDate = new JButton("Max Date");
        maxDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                find(SearchEnum.MAX, SearchEnum.DATE);
            }
        });
        maxDate.setPreferredSize(rightPanelButtonsSize);

        JButton dateSearch = new JButton("Date Search");
        dateSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dateSearch();
            }
        });
        dateSearch.setPreferredSize(rightPanelButtonsSize);

        GridBagConstraints constraintsRight = new GridBagConstraints();

        constraintsRight.insets = new Insets(4, 0, 4, 0);
        constraintsRight.gridx = 0;
        constraintsRight.gridy = 0;
        panelRight.add(minAutor, constraintsRight);
        constraintsRight.gridx = 0;
        constraintsRight.gridy = 1;
        panelRight.add(minLocation, constraintsRight);
        constraintsRight.gridx = 0;
        constraintsRight.gridy = 2;
        panelRight.add(minDate, constraintsRight);
        constraintsRight.gridx = 0;
        constraintsRight.gridy = 3;
        panelRight.add(maxAutor, constraintsRight);
        constraintsRight.gridx = 0;
        constraintsRight.gridy = 4;
        panelRight.add(maxLocation, constraintsRight);
        constraintsRight.gridx = 0;
        constraintsRight.gridy = 5;
        panelRight.add(maxDate, constraintsRight);
        constraintsRight.gridx = 0;
        constraintsRight.gridy = 6;
        panelRight.add(dateSearch, constraintsRight);


        pack();
        setMinimumSize(getSize());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void resetFilter() {
        TableRowSorter sorter = (TableRowSorter) table.getRowSorter();
        RowFilter<Object, Object> fooBarFilter = RowFilter.regexFilter("");
        sorter.setRowFilter(fooBarFilter);
    }

    private void dateSearch() {
        JFrame dateSearchWindow = new JFrame("Date Search");
        GridBagLayout gridBagLayout = new GridBagLayout();
        dateSearchWindow.setLayout(gridBagLayout);

        JRadioButton less = new JRadioButton("Less than");
        JRadioButton more = new JRadioButton("More than");


        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(less);
        buttonGroup.add(more);

        JLabel dateLabel = new JLabel("date:");
        JTextField dateField = new JTextField("yyyy-MM-dd");
        dateField.setPreferredSize(new Dimension(100, 20));

        JButton accept = new JButton("Accept");
        accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (buttonGroup.getSelection() != null) {
                    if (less.isSelected()) {
                        findDates(SearchEnum.LESS, dateField.getText());
                    } else {
                        findDates(SearchEnum.MORE, dateField.getText());
                    }

                } else {
                    JOptionPane.showMessageDialog(frame, "Select an less or more than");
                }

            }
        });


        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dateSearchWindow.dispose();
            }
        });

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(18, 18, 2, 2);
        constraints.gridx = 0;
        constraints.gridy = 0;
        dateSearchWindow.add(less, constraints);
        constraints.insets = new Insets(18, 2, 2, 18);
        constraints.gridx = 1;
        constraints.gridy = 0;
        dateSearchWindow.add(more, constraints);
        constraints.insets = new Insets(2, 18, 18, 2);
        constraints.gridx = 0;
        constraints.gridy = 1;
        dateSearchWindow.add(dateLabel, constraints);
        constraints.insets = new Insets(2, 2, 18, 18);
        constraints.gridx = 1;
        constraints.gridy = 1;
        dateSearchWindow.add(dateField, constraints);
        constraints.insets = new Insets(8, 8, 8, 8);
        constraints.gridx = 0;
        constraints.gridy = 2;
        dateSearchWindow.add(accept, constraints);
        constraints.insets = new Insets(8, 8, 8, 8);
        constraints.gridx = 1;
        constraints.gridy = 2;
        dateSearchWindow.add(cancel, constraints);


        dateSearchWindow.pack();
        dateSearchWindow.setVisible(true);
    }

    private Date dateFromString(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateTime = null;
        sdf.setLenient(false);
        dateTime = sdf.parse(date);


        return dateTime;
    }

    private void findDates(SearchEnum mode, String date) {

        Date dateTime = null;
        try {
            dateTime = dateFromString(date);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(frame, "Wrong time format or invalid date, please use yyyy-MM-dd");
            return;
        }

        ArrayList<Date> dates = new ArrayList<>();
        for (Vector<String> row :
                data) {
            try {
                dates.add(dateFromString(row.get(DATE)));
            } catch (ParseException e) {

                JOptionPane.showMessageDialog(frame, "Wrong time format or invalid date, please use yyyy-MM-dd");
                return;
            }
        }

        ArrayList<String> correct = getCorrectStringDates(dates, mode, dateTime);

        applyDateFilters(correct);

    }

    private void applyDateFilters(ArrayList<String> correct) {
        TableRowSorter sorter = (TableRowSorter) table.getRowSorter();
        ArrayList<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(5);
        for (String filter :
                correct) {
            filters.add(RowFilter.regexFilter(filter));
        }
        RowFilter<Object, Object> fooBarFilter = RowFilter.orFilter(filters);
        sorter.setRowFilter(fooBarFilter);
    }

    private ArrayList<String> getCorrectStringDates(ArrayList<Date> dates, SearchEnum mode, Date dateTime) {
        ArrayList<String> correct = new ArrayList<>();
        for (Date iDate :
                dates) {
            if (mode == SearchEnum.LESS) {
                if (iDate.before(dateTime)) {
                    correctDate(correct, iDate);
                }
            } else {
                if (iDate.after(dateTime)) {
                    correctDate(correct, iDate);
                }
            }

        }
        return correct;
    }

    private void correctDate(ArrayList<String> correct, Date iDate) {
        LocalDate localDate = iDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();
        String monthString;
        String dayString;
        if (month < 10) {
            monthString = '0' + Integer.toString(month);
        } else {
            monthString = String.valueOf(month);
        }
        if (day < 10) {
            dayString = '0' + Integer.toString(day);
        } else {
            dayString = String.valueOf(day);
        }
        correct.add(localDate.getYear() + "-" + monthString + "-" + dayString);
    }

    private void find(SearchEnum mode, SearchEnum what) {
        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No pictures to find");
            return;
        }
        //Find all important data
        String minAutor = null;
        String minLocation = null;
        String minDate = null;
        String maxAutor = null;
        String maxLocation = null;
        String maxDate = null;
        for (Vector<String> row :
                data) {
            for (int i = 1; i < 5; i++) {
                if (row.get(i).isEmpty()) {
                    continue;
                }
                switch (i) {
                    case 1:
                        //autor
                        if (minAutor == null) {
                            minAutor = row.get(i);
                        }
                        if (maxAutor == null) {
                            maxAutor = row.get(i);
                        }
                        if (row.get(i).compareTo(minAutor) < 0) {
                            minAutor = row.get(i);
                        }
                        if (row.get(i).compareTo(maxAutor) > 0) {
                            maxAutor = row.get(i);
                        }
                        break;
                    case 2:
                        //location
                        if (minLocation == null) {
                            minLocation = row.get(i);
                        }
                        if (maxLocation == null) {
                            maxLocation = row.get(i);
                        }
                        if (row.get(i).compareTo(minLocation) < 0) {
                            minLocation = row.get(i);
                        }
                        if (row.get(i).compareTo(maxLocation) > 0) {
                            maxLocation = row.get(i);
                        }
                        break;
                    case 3:
                        //date
                        if (minDate == null) {
                            minDate = row.get(i);
                        }
                        if (maxDate == null) {
                            maxDate = row.get(i);
                        }
                        if (row.get(i).compareTo(minDate) < 0) {
                            minDate = row.get(i);
                        }
                        if (row.get(i).compareTo(maxDate) > 0) {
                            maxDate = row.get(i);
                        }
                        break;
                }
            }
        }
        String filter = "";
        if (mode == SearchEnum.MAX) {
            if (what == SearchEnum.AUTOR) {
                filter = maxAutor;
            } else if (what == SearchEnum.LOCATION) {
                filter = maxLocation;
            } else {
                filter = maxDate;
            }
        } else {
            //MIN section
            if (what == SearchEnum.AUTOR) {
                filter = minAutor;
            } else if (what == SearchEnum.LOCATION) {
                filter = minLocation;
            } else {
                filter = minDate;
            }
        }
        TableRowSorter sorter = (TableRowSorter) table.getRowSorter();
        filter = filter.concat("$");

        RowFilter<Object, Object> fooBarFilter = RowFilter.regexFilter(filter);
        sorter.setRowFilter(fooBarFilter);

    }

    private void filter() {
        JFrame filterWindow = new JFrame("Filter");
        GridBagLayout gridBagLayout = new GridBagLayout();

        filterWindow.setLayout(gridBagLayout);

        addPicture(WindowMode.FILTER, -1);

    }



    private void editPicture() {
        int idx = table.getSelectedRow();
        if (idx == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a picture");
            return;
        }
        idx = table.convertRowIndexToModel(table.getSelectedRow());

        addPicture(WindowMode.EDIT, idx);
    }

    private void removePicture() {

        int idx = table.getSelectedRow();
        if (idx == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a picture");
            return;
        }
        idx = table.convertRowIndexToModel(table.getSelectedRow());
        data.remove(idx);
        updateTable();
    }

    private void viewPicture() {
        int idx = table.getSelectedRow();
        if (idx == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a picture");
            return;
        }
        idx = table.convertRowIndexToModel(table.getSelectedRow());
        String path = (String) data.get(idx).get(PATH);

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
            return;
        }

        saveToFile(path);


    }

    private void saveToFile(String path) {

        try {
            FileWriter fw = new FileWriter(path);
            fw.append("Verification Line\n");
            for (Vector<String> row :
                    data) {
                for (String cell :
                        row) {
                    if (cell.isEmpty()) {
                        fw.append('@');
                    }
                    fw.append(cell);
                    fw.append(';');
                }
                fw.append("\n");
            }
            fw.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Unable to save");
            return;
        }


    }

    private void loadBase() {
        String path = null;
        try {
            path = getPath();
        } catch (Exception e) {
            return;
        }
        File base = new File(path);


        StringBuilder sb = new StringBuilder();
        String text;
        try {
            Scanner scanner = new Scanner(base);
            if (scanner.nextLine().compareTo("Verification Line") != 0) {
                JOptionPane.showMessageDialog(frame, "Invalid base file");
                return;
            }
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
        StringTokenizer st = new StringTokenizer(allData, ";\n");

        Vector<String> row = new Vector<>();

        int i = 0;
        while (st.hasMoreTokens()) {
            i++;
            String val = st.nextToken();
            if (val.equals("@")) {
                row.add("");
            } else {
                row.add(val);
            }

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
    private void addPicture(WindowMode mode, int tableIdx) {
        JFrame window = new JFrame("Add Picture");
        GridBagLayout gridBagLayout = new GridBagLayout();

        window.setLayout(gridBagLayout);

        if (mode == WindowMode.EDIT) {
            window.setTitle("Edit Picture");
        } else if (mode == WindowMode.FILTER) {
            window.setTitle("Filter");
        }


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

        if (mode == WindowMode.EDIT) {
            pathField.setText((String) data.get(tableIdx).get(PATH));
            autorField.setText((String) data.get(tableIdx).get(AUTOR));
            locationField.setText((String) data.get(tableIdx).get(LOCATION));
            dateField.setText((String) data.get(tableIdx).get(DATE));
            tagsField.setText((String) data.get(tableIdx).get(TAGS));

        }

        GridBagConstraints constraints = new GridBagConstraints();
        Dimension textSize = new Dimension(200, 20);

        pathField.setPreferredSize(textSize);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(8, 8, 2, 8);
        window.add(pathLabel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 0;
        window.add(pathField, constraints);
        constraints.insets = new Insets(2, 8, 2, 8);

        autorField.setPreferredSize(textSize);
        constraints.gridx = 0;
        constraints.gridy = 1;
        window.add(autorLabel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 1;
        window.add(autorField, constraints);

        locationField.setPreferredSize(textSize);
        constraints.gridx = 0;
        constraints.gridy = 2;
        window.add(locationLabel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 2;
        window.add(locationField, constraints);

        dateField.setPreferredSize(textSize);
        constraints.gridx = 0;
        constraints.gridy = 3;
        window.add(dateLabel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 3;
        window.add(dateField, constraints);

        constraints.insets = new Insets(2, 8, 8, 8);

        tagsField.setPreferredSize(textSize);
        constraints.gridx = 0;
        constraints.gridy = 4;
        window.add(tagsLabel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 4;
        window.add(tagsField, constraints);

        if (mode == WindowMode.ADD) {
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
            window.add(explorer, constraints);
        }

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


                if ((path.endsWith("jpg") || path.endsWith("png")) && new File(path).exists() || mode == WindowMode.FILTER) {
                    if (date.isEmpty() || mode == WindowMode.FILTER) {

                    } else {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date dateTime = null;
                        sdf.setLenient(false);
                        try {
                            dateTime = sdf.parse(date);
                        } catch (ParseException e1) {
                            JOptionPane.showMessageDialog(frame, "Wrong time format or invalid date, please use yyyy-MM-dd");
                            return;
                        }
                    }


                    Vector<String> newRow = new Vector<>();
                    newRow.add(path);
                    newRow.add(autor);
                    newRow.add(location);
                    newRow.add(date);
                    newRow.add(tags);
                    if (mode == WindowMode.EDIT) {
                        data.get(tableIdx).clear();
                        data.get(tableIdx).add(newRow.get(PATH));
                        data.get(tableIdx).add(newRow.get(AUTOR));
                        data.get(tableIdx).add(newRow.get(LOCATION));
                        data.get(tableIdx).add(newRow.get(DATE));
                        data.get(tableIdx).add(newRow.get(TAGS));
                        updateTable();
                    } else if (mode == WindowMode.FILTER) {
                        applyFilters(newRow);
                    } else {
                        addRow(newRow);
                    }

                    window.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Wrong file type or doesn't exist");
                    return;
                }


            }
        });

        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 5;
        window.add(accept, constraints);
        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.anchor = GridBagConstraints.WEST;
        window.add(cancel, constraints);


        window.pack();
        window.setVisible(true);

    }

    private void applyFilters(Vector<String> row) {
        TableRowSorter sorter = (TableRowSorter) table.getRowSorter();
        ArrayList<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(5);
        filters.add(RowFilter.regexFilter(row.get(PATH)));
        filters.add(RowFilter.regexFilter(row.get(AUTOR)));
        filters.add(RowFilter.regexFilter(row.get(LOCATION)));
        filters.add(RowFilter.regexFilter(row.get(DATE)));
        filters.add(RowFilter.regexFilter(row.get(TAGS)));
        RowFilter<Object, Object> fooBarFilter = RowFilter.andFilter(filters);
        sorter.setRowFilter(fooBarFilter);

    }

    private void addRow(Vector<String> newRow) {
        data.add(newRow);
        updateTable();
    }

}
