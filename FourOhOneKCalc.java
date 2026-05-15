import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FourOhOneKCalc extends JPanel implements ActionListener {
    private JTextField salary;
    private JTextField contributionPercent;
    private JTextField raisePercent;
    private JTextField currentAge;
    private JTextField retirementAge;
    private JTextField returnRate;
    private JTextField startingBalance;
    private JTextField employerMatchPercent;
    private JTextField matchLimitPercent;
    private JTextField taxRate;

    private JButton calculate;

    public FourOhOneKCalc(){
        setLayout(new GridLayout(11,2,5,5));

        salary = new JTextField("75000");
        contributionPercent = new JTextField("10");
        raisePercent = new JTextField("3");
        currentAge = new JTextField("30");
        retirementAge = new JTextField("65");
        returnRate = new JTextField("7");
        startingBalance = new JTextField("15000");
        employerMatchPercent = new JTextField("4");
        matchLimitPercent = new JTextField("100");
        taxRate = new JTextField("22");

        calculate = new JButton("Calculate");
        calculate.addActionListener(this);

        add(new JLabel("Annual Salary ($):"));
        add(salary);

        add(new JLabel("Employee Contribution (%): "));
        add(contributionPercent);

        add(new JLabel("Annual Salary Increase (%):"));
        add(raisePercent);

        add(new JLabel("Current Age:"));
        add(currentAge);

        add(new JLabel("Retirement Age:"));
        add(retirementAge);

        add(new JLabel("Expected Return (%):"));
        add(returnRate);

        add(new JLabel("Current 401(k) Balance ($):"));
        add(startingBalance);

        add(new JLabel("Employer Match:"));
        add(employerMatchPercent);

        add(new JLabel("Employer Match Salary Limit (%):"));
        add(matchLimitPercent);

        add(new JLabel("Marginal Tax Rate:"));
        add(taxRate);

        add(new JLabel());
        add(calculate);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        double rothEmployeeTot = 0;
        double rothEmployerTot = 0;
        double rothBalance;
        double currentSalary;

        double traditionalBalance;
        double traditionalEmployeeTot = 0;
        double traditionalEmployerTot = 0;
        try{
            double sal = Double.parseDouble(salary.getText());
            double contPct = Double.parseDouble(contributionPercent.getText()) / 100.0;
            double raisePct = Double.parseDouble(raisePercent.getText()) / 100.0;
            int currAge = Integer.parseInt(currentAge.getText());
            int retireAge = Integer.parseInt(retirementAge.getText());
            double retRate = Double.parseDouble(returnRate.getText()) / 100.0;
            double startBal = Double.parseDouble(startingBalance.getText());
            double emplMatchPct = Double.parseDouble(employerMatchPercent.getText()) / 100.0;
            double matchLim = Double.parseDouble(matchLimitPercent.getText()) / 100.0;
            double txRate = Double.parseDouble(taxRate.getText()) / 100.0;

            if (currAge >= retireAge){
                throw new IllegalArgumentException("Retirement age must be greater than current age.");
            }

             rothBalance = startBal;
             currentSalary = sal;

            for(int age = currAge; age < retireAge; age++){
                double employeeContribution = currentSalary * contPct;
                double matchablePct = Math.min(contPct, matchLim);
                double employerContribution = currentSalary * matchablePct * emplMatchPct;

                rothEmployeeTot += employeeContribution;
                rothEmployerTot += employeeContribution;

                rothBalance = (rothBalance + employerContribution + employeeContribution) * (1 + raisePct);
                currentSalary *= (1 + raisePct);
            }

            traditionalBalance = rothBalance * (1 - txRate);
            traditionalEmployeeTot = rothEmployeeTot;
            traditionalEmployerTot = rothEmployerTot;

            JOptionPane.showMessageDialog(this,"Roth 401(k)\n" +
                    "Final Balance: $" + String.format("%, .2f",rothBalance) +
                    "\nEmployee Contributions: $" + String.format("%, .2f", rothEmployeeTot) +
                    "\nEmployer Contributions: $" + String.format("%, .2f", rothEmployerTot) +
                    "\n\nTraditional 401(k)\n" + "Final Balance (After Tax): $" + String.format("%, .2f", traditionalBalance) +
                    "\nEmployee Contributions: $" + String.format("%, .2f", traditionalEmployeeTot) +
                    "\nEmployer Contributions: $" + String.format("%, .2f", traditionalEmployerTot), "401(k) Results", JOptionPane.INFORMATION_MESSAGE);

        }
        catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values in all fields",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        }
        catch(IllegalArgumentException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
