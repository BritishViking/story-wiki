package Shell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class MainFrame extends JFrame {

    public MainFrame(String title){
        super(title);

        //Set Layout manager
        setLayout(new BorderLayout());

        //Create Swing component
         JTextArea textArea = new JTextArea();

        getContentPane().setBackground(Color.YELLOW);  //Whatever color

        JButton button = new JButton("Click me");
        JTextField field = new JTextField("Hi");

        field.setBackground(new Color(43,43,43));
        field.setForeground(Color.white);
        Font font1 = new Font("Consolas", Font.PLAIN, 20);
        Font font2 = new Font("Consolas", Font.PLAIN, 20);
        field.setFont(font1);
        field.setMargin(new Insets(10,10,10,10));
        field.setCaretColor(Color.WHITE);

        textArea.setMargin(new Insets(10,20,5,20));
        textArea.setEditable(false);
        textArea.setBackground(new Color(32,32,32));
        textArea.setForeground(new Color(66,66,66));
        textArea.setFont(font2);

        JScrollPane scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        Color scrollCol = new Color(22,22,22);
        scroll.getVerticalScrollBar().setBackground(scrollCol);
        scroll.getVerticalScrollBar().setForeground(scrollCol);
        // add swing components
        Container c = this.getContentPane();
         //Whatever color

        c.add(scroll, BorderLayout.CENTER);
        c.add(field, BorderLayout.SOUTH);

        //Add behaviour
        field.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent r){
                String input = field.getText();

                if(input.length() < 255) {
                    if (input.equals("clear")) {
                        textArea.setText("mariamcculloch: clear");
                    } else {
                        String output =  runCommand(input);
                        if (output.equals("clear")) {
                            textArea.setText("");
                        } else {
                            textArea.append("\n" +"mariamcculloch: " + input + "\n" + output + "\n");
                        }

                        //System.out.println("." + input + ".");
                    }
                } else{
                        textArea.append("mariamcculloch: ERROR: COMMAND TOO LARGE");

                }
                field.setText("");
            }
        });
    }

    public abstract String runCommand(String commandString );







}
