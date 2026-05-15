import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IRACalc extends JPanel implements ActionListener {
    private JTextField startingBalance;
    private JTextField currentAge;
    private JTextField annualContribution;
    private JTextField retirementAge;
    private JTextField returnRate;
    private JTextField taxRate;

    private JCheckBox maxContributions;
    private JButton calculateButton;

    private static final double IRA_LIMIT_UNDER_50 = 7000;
    private static final double IRA_LIMIT_50_AND_OVER = 8000;

    public IRACalc(){
        setLayout(new GridLayout(8,2,5,5));

        startingBalance = new JTextField("10000");
        currentAge = new JTextField("30");
        annualContribution = new JTextField("6000");
        retirementAge = new JTextField("65");
        returnRate = new JTextField("7");
        taxRate = new JTextField("22");

        maxContributions = new JCheckBox("Max IRA Contribution");
        calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(this);

        add(new JLabel("Starting Balance: "));
        add(startingBalance);

        add(new JLabel("Annual Contributions: "));
        add(annualContribution);

        add(new JLabel("Current Age:"));
        add(currentAge);

        add(new JLabel("Retirement Age: "));
        add(retirementAge);

        add(new JLabel("Expected Rate of Return: "));
        add(returnRate);

        add(new JLabel("Marginal Tax Rate:"));
        add(taxRate);

        add(maxContributions);
        add(calculateButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            double startBal = Double.parseDouble(startingBalance.getText());
            double annCont = Double.parseDouble(annualContribution.getText());
            int currAge = Integer.parseInt(currentAge.getText());
            int retAge = Integer.parseInt(retirementAge.getText());
            double retRate = Double.parseDouble(returnRate.getText());
            double txRate = Double.parseDouble(taxRate.getText());

            if (retAge <= currAge){
                throw new IllegalArgumentException("Retirement age must be greater than current age.");
            }

            double contributionLimit;
            if (currAge >= 50){contributionLimit = IRA_LIMIT_50_AND_OVER;}
            else{contributionLimit = IRA_LIMIT_UNDER_50;}

            if(maxContributions.isSelected()){annCont = contributionLimit;}
            if(annCont > contributionLimit){
                throw new IllegalArgumentException("Annual contribution exceeds IRA limit.");
            }

            double rothBalance = startBal;
            double rothContributions = 0;
            for(int i = currAge; i < retAge; i++){
                rothContributions += annCont;
                rothBalance = (rothBalance + annCont) * (1 + retRate);
            }

            double traditionalBalance = startBal;
            double traditionalContributions = 0;
            for(int i = currAge; i < retAge; i++){
                traditionalContributions += annCont;
                traditionalBalance = (traditionalBalance + annCont) * (1 + retRate);
            }
            traditionalBalance *= (1 - txRate);

            JOptionPane.showMessageDialog(this,"Roth IRA Final Balance: $" + 
                                          String.format("%,.2f", rothBalance) +      
                                          "\nTotal Contributions: $" + String.format("%,.2f", rothContributions) +                  
                                          "\n\nTraditional IRA Final Balance: $" + String.format("%,.2f", traditionalBalance) +                   
                                          "\nTotal Contributions: $" + String.format("%,.2f", traditionalContributions),                 
                                          "IRA Results",                  
                                          JOptionPane.INFORMATION_MESSAGE);
            

        }
        catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(this,
                    "Please enter numeric values in all fields.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        }
        catch(IllegalArgumentException ex){
            JOptionPane.showMessageDialog(this,ex.getMessage(),
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


