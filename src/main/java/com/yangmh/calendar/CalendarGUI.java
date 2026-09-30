package com.yangmh.calendar;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Calendar;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/** Swing 日历界面，提供全年、指定月份和当前月份三种查询模式。 */
public class CalendarGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private final JComboBox<String> choiceComboBox = new JComboBox<>(
            new String[]{"打印全年日历", "打印某月日历", "打印当前月日历"});
    private final JTextField yearTextField = new JTextField();
    private final JTextField monthTextField = new JTextField();
    private final JTextArea resultTextArea = new JTextArea();

    /** 创建输入区、提交按钮与可滚动的日历显示区。 */
    public CalendarGUI() {
        setTitle("简易日历程序");
        setSize(520, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel content = new JPanel(new BorderLayout(10, 10));
        content.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
        JPanel input = new JPanel(new GridLayout(3, 2, 10, 10));
        input.add(new JLabel("请选择功能："));
        input.add(choiceComboBox);
        input.add(new JLabel("请输入年份："));
        input.add(yearTextField);
        input.add(new JLabel("请输入月份："));
        input.add(monthTextField);
        Calendar now = Calendar.getInstance();
        yearTextField.setText(String.valueOf(now.get(Calendar.YEAR)));
        monthTextField.setText(String.valueOf(now.get(Calendar.MONTH) + 1));
        resultTextArea.setEditable(false);
        resultTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        JButton submit = new JButton("生成日历");
        submit.addActionListener(event -> handleSubmit());
        content.add(input, BorderLayout.NORTH);
        content.add(new JScrollPane(resultTextArea), BorderLayout.CENTER);
        content.add(submit, BorderLayout.SOUTH);
        setContentPane(content);
    }

    /** 解析输入并调用计算模块，无效数字或日期范围会显示明确提示。 */
    private void handleSubmit() {
        try {
            int choice = choiceComboBox.getSelectedIndex();
            if (choice == 0) {
                int year = Integer.parseInt(yearTextField.getText().trim());
                resultTextArea.setText(SimpleCalendar.getYearCalendar(year));
            } else if (choice == 1) {
                int year = Integer.parseInt(yearTextField.getText().trim());
                int month = Integer.parseInt(monthTextField.getText().trim());
                resultTextArea.setText(SimpleCalendar.getMonthCalendar(year, month));
            } else {
                Calendar now = Calendar.getInstance();
                resultTextArea.setText(SimpleCalendar.getMonthCalendar(
                        now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1));
            }
            resultTextArea.setCaretPosition(0);
        } catch (NumberFormatException error) {
            resultTextArea.setText("请输入有效的整数！");
        } catch (IllegalArgumentException error) {
            resultTextArea.setText(error.getMessage());
        }
    }

    /** 在事件调度线程创建和显示界面，替代原 hello 类中的入口。 */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalendarGUI().setVisible(true));
    }
}
