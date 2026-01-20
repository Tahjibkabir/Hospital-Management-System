import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;


class HospitalGui extends JFrame implements ActionListener , MouseListener {

    private JPanel panel;

    private JLabel label1, label2, label3, label4, label5, labelSearch;
    private JTextField textField1, textField2, textField3, textField4, textFieldSearchId;
    private JButton btn1, btn2, btnSearch, btnExit, btnClear;
    private JRadioButton rbPatient, rbDoctor;
    private ButtonGroup group;
    private JTextArea displayArea;
    private JComboBox com1;
    private Color c1, c2;
    private Font f1;

    private Patient[] patients = new Patient[100];
    private Doctor[] doctors = new Doctor[100];
    private int patientCount = 0;
    private int doctorCount = 0;

    public HospitalGui() {
        setTitle("Hospital Management System");
        setSize(600,700);
        setLayout(null);

        c1 = new Color(0, 247, 0);
        c2 = new Color(200, 218, 235);

        f1 = new Font("Cambria", Font.BOLD,14);

        label1 = new JLabel("Name: ");
        label1.setBounds(30, 20, 100, 25);
        label1.setFont(f1);
        add(label1);

        label2 = new JLabel("Age: ");
        label2.setBounds(30, 60, 100, 25);
        label2.setFont(f1);
        add(label2);

        label3 = new JLabel("Id: ");
        label3.setBounds(30, 100, 100, 25);
        label3.setFont(f1);

        add(label3);

        label4 = new JLabel("Condition/Speciality: ");
        label4.setBounds(30, 140, 120, 25);
        label4.setFont(f1);
        add(label4);

        label5 = new JLabel("Gender:");
        label5.setBounds(385, 60, 120,25);
        label5.setFont(f1);
        add(label5);

        textField1 = new JTextField();
        textField1.setBounds(160, 20, 200, 25);
        add(textField1);

        textField2 = new JTextField();
        textField2.setBounds(160, 60, 200, 25);
        add(textField2);

        textField3 = new JTextField();
        textField3.setBounds(160, 100, 200, 25);
        add(textField3);

        textField4 = new JTextField();
        textField4.setBounds(160, 140, 200, 25);
        add(textField4);

        rbPatient = new JRadioButton("Patient");
        rbPatient.setBounds(480, 20, 100, 30);
        add(rbPatient);

        rbDoctor = new JRadioButton("Doctor");
        rbDoctor.setBounds(380, 20, 100, 30);
        add(rbDoctor);

        group = new ButtonGroup();
        group.add(rbPatient);
        group.add(rbDoctor);

        btn1 = new JButton("Add Person");
        //btn1.setBackground(c1);
        btn1.addActionListener(this);
        btn1.setBounds(150, 200, 200, 25);
        add(btn1);

        btn2 = new JButton("Show Person");
        btn2.addActionListener(this);
        btn2.setBounds(150, 240, 200, 25);
        add(btn2);

        labelSearch = new JLabel("Search ID:");
        labelSearch.setBounds(30, 280, 100, 25);
        labelSearch.setFont(f1);
        add(labelSearch);

        textFieldSearchId = new JTextField();
        textFieldSearchId.setBounds(150, 280, 200, 25);
        add(textFieldSearchId);

        btnSearch = new JButton("Search");
        btnSearch.addActionListener(this);
        btnSearch.setBounds(360, 280, 100, 25);
        add(btnSearch);

        btnClear = new JButton("Clear");
        btnClear.addActionListener(this);

        //btnClear.setBackground(Color.BLUE);
        //btn1.setForeground(Color.BLACK);
        btnClear.setBounds(360, 200, 100, 25);
        add(btnClear);

        btnExit = new JButton("Exit");
        //btnExit.setBackground(Color.RED);
        btnExit.setForeground(Color.BLACK);
        btnExit.addActionListener(this);
        btnExit.addMouseListener(this);
        btnExit.setBounds(360, 240, 100, 25);
        add(btnExit);
        String gender [] = {"Male","Female"};


        com1 = new JComboBox(gender);
        com1.setBounds(460, 60, 100,25);
        add(com1);

        displayArea = new JTextArea();
        displayArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBounds(30, 320, 440, 330);
        add(scrollPane);

        loadPersonFromFile();
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn1) {
            String name = textField1.getText();
            int age = Integer.parseInt(textField2.getText());

            if (rbPatient.isSelected()) {
                String patientId = textField3.getText();
                String condition = textField4.getText();

                if (patientCount < patients.length) {
                    patients[patientCount] = new Patient(name, age, patientId, condition);
                    displayArea.setText("Patient added: " + patients[patientCount].getDetails());
                    patientCount++;
                    savePersonToFile();
                } else {
                    displayArea.setText("Patient array full!");
                }
            } else if (rbDoctor.isSelected()) {
                String doctorId = textField3.getText();
                String speciality = textField4.getText();

                if (doctorCount < doctors.length) {
                    doctors[doctorCount] = new Doctor(name, age, doctorId, speciality);
                    displayArea.setText("Doctor added: " + doctors[doctorCount].getDetails());
                    doctorCount++;
                    savePersonToFile();
                } else {
                    displayArea.setText("Doctor array full!");
                }
            }
        } else if (e.getSource() == btnClear){
            clearFields();
        }
        
        else if (e.getSource() == btn2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Patients:\n");
            for (int i = 0; i < patientCount; i++) {
                sb.append("Index ").append(i).append(": ").append(patients[i].getDetails()).append("\n");
            }
            sb.append("\nDoctors:\n");
            for (int i = 0; i < doctorCount; i++) {
                sb.append("Index ").append(i).append(": ").append(doctors[i].getDetails()).append("\n");
            }
            displayArea.setText(sb.toString());
        } else if (e.getSource() == btnSearch) {
            String searchId = textFieldSearchId.getText().trim();
            if (searchId.isEmpty()) {
                displayArea.setText("Please enter an ID to search.");
                return;
            }

            boolean found = false;

            for (int i = 0; i < patientCount; i++) {
                if (patients[i].getPatientId().equals(searchId)) {
                    displayArea.setText("Patient Found:\n" + patients[i].getDetails());
                    found = true;
                    break;
                }
            }

            if (!found) {
                for (int i = 0; i < doctorCount; i++) {
                    if (doctors[i].getDoctorId().equals(searchId)) {
                        displayArea.setText("Doctor Found:\n" + doctors[i].getDetails());
                        found = true;
                        break;
                    }
                }
            }

            if (!found) {
                displayArea.setText("Person with ID '" + searchId + "' not found.");
            }
        } else if (e.getSource() == btnExit) {
            textField1.setText("");
            textField2.setText("");
            textField3.setText("");
            textField4.setText("");
            textFieldSearchId.setText("");
            displayArea.setText("");
            group.clearSelection();
            System.exit(0); 
        }
    }

    private void clearFields() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textFieldSearchId.setText("");
       
        group.clearSelection();
    
       com1.setSelectedIndex(0);
       
    }

    public void mouseClicked(MouseEvent me) {
        if (me.getSource() == btnExit) {
            btnExit.setForeground(Color.BLUE);
        } 

    }

    public void mouseEntered(MouseEvent me) {
        if (me.getSource() == btnExit) {
            btnExit.setBackground(Color.RED);
            btnExit.setForeground(Color.BLACK);

        }
    }

    public void mouseExited(MouseEvent me) {
        if (me.getSource() == btnExit) {
           btnExit.setBackground(c2);
            btnExit.setForeground(Color.BLACK);
        }
    }

    public void mousePressed(MouseEvent me) {
    }

    public void mouseReleased(MouseEvent me) {
    }

    private void savePersonToFile() {
        try {
            FileWriter writer = new FileWriter("C:\\New folder 2\\New folder\\Hospital Management\\Data/person.txt");
            for (int i = 0; i < patientCount; i++) {
                writer.write("Patient\n");
                writer.write(patients[i].name + "\n");
                writer.write(patients[i].age + "\n");
                writer.write(patients[i].getPatientId() + "\n");
                writer.write(patients[i].getcondition() + "\n");
            }
            for (int i = 0; i < doctorCount; i++) {
                writer.write("Doctor\n");
                writer.write(doctors[i].name + "\n");
                writer.write(doctors[i].age + "\n");
                writer.write(doctors[i].getDoctorId() + "\n");
                writer.write(doctors[i].getSpeciality() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            displayArea.setText("Error saving: " + e.getMessage());
        }
    }

    private void loadPersonFromFile() {
        try {
            File file = new File("C:\\New folder 2\\New folder\\Hospital Management\\Data/person.txt");
            if (!file.exists()) {
                file.createNewFile();
                return;
            }

            Scanner scanner = new Scanner(file);
            patientCount = 0;
            doctorCount = 0;

            while (scanner.hasNextLine()) {
                String type = scanner.nextLine().trim();
                if (type.equals("Patient")) {
                    String name = scanner.nextLine();
                    int age = Integer.parseInt(scanner.nextLine().trim());
                    String patientId = scanner.nextLine();
                    String condition = scanner.nextLine();
                    patients[patientCount++] = new Patient(name, age, patientId, condition);
                } else if (type.equals("Doctor")) {
                    String name = scanner.nextLine();
                    int age = Integer.parseInt(scanner.nextLine().trim());
                    String doctorId = scanner.nextLine();
                    String speciality = scanner.nextLine();
                    doctors[doctorCount++] = new Doctor(name, age, doctorId, speciality);
                }
            }

            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
            displayArea.setText("Error loading: " + e.getMessage());
        }
    }


}