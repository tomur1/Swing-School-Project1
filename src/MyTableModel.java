import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel {

    private String[] columnNames;
    private Object[][] data;

    MyTableModel() {
        this.columnNames = new String[]{""};
        this.data = new String[][]{{""}};
    }

    MyTableModel(String[] columnNames, String[][] data) {
        this.columnNames = columnNames;
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }
}
