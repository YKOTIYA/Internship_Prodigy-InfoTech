import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebScraper extends JFrame implements ActionListener {
    private JTextField urlField;
    private JButton scrapeButton;
    private JTextArea resultArea;

    public WebScraper() {
        setTitle("Web Scraper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // URL input panel
        JPanel inputPanel = new JPanel(new FlowLayout());
        JLabel urlLabel = new JLabel("Enter URL: ");
        inputPanel.add(urlLabel);
        urlField = new JTextField(30);
        inputPanel.add(urlField);
        scrapeButton = new JButton("Scrape");
        scrapeButton.addActionListener(this);
        inputPanel.add(scrapeButton);

        add(inputPanel, BorderLayout.NORTH);

        // Result area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == scrapeButton) {
            String url = urlField.getText();
            if (!url.isEmpty()) {
                scrapeProducts(url);
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a valid URL.");
            }
        }
    }

    private void scrapeProducts(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements productElements = doc.select("div.s-result-item");

            List<Product> products = new ArrayList<>();

            for (Element productElement : productElements) {
                String title = productElement.select("span.a-size-medium").text();
                String price = productElement.select("span.a-offscreen").text();
                String rating = productElement.select("span.a-icon-alt").text();

                Product product = new Product(title, price, rating);
                products.add(product);
            }

            saveToCSV(products);
            displayResults(products);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void saveToCSV(List<Product> products) {
        try {
            FileWriter writer = new FileWriter("products.csv");
            writer.append("Title,Price,Rating\n");

            for (Product product : products) {
                writer.append(product.getTitle() + "," + product.getPrice() + "," + product.getRating() + "\n");
            }

            writer.flush();
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void displayResults(List<Product> products) {
        StringBuilder sb = new StringBuilder();
        sb.append("Title,Price,Rating\n");

        for (Product product : products) {
            sb.append(product.getTitle()).append(",").append(product.getPrice()).append(",").append(product.getRating()).append("\n");
        }

        resultArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WebScraper().setVisible(true);
            }
        });
    }
}

class Product {
    private String title;
    private String price;
    private String rating;

    public Product(String title, String price, String rating) {
        this.title = title;
        this.price = price;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getRating() {
        return rating;
    }
}