package org.example;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.Timer;

class RenkAviOyunu extends JFrame implements ActionListener {
    private JLabel puanLabel;
    private JLabel sureLabel;
    private JButton[] renkButonlari;
    private Color[] renkler = {Color.RED, Color.YELLOW};
    private int puan = 0;
    private int hedefPuan = 10;
    private int zamanSuresi = 30;
    private Timer zamanlayici;

    public RenkAviOyunu() {
        setTitle("Renk Avı Oyunu");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(3, 1));

        puanLabel = new JLabel("Puan: 0");
        panel.add(puanLabel);

        sureLabel = new JLabel("Kalan Süre: " + zamanSuresi);
        panel.add(sureLabel);

        JPanel renkPaneli = new JPanel(new GridLayout(1, renkler.length));
        renkButonlari = new JButton[renkler.length];
        for (int i = 0; i < renkler.length; i++) {
            JButton renkButonu = new JButton();
            renkButonu.setBackground(renkler[i]);
            renkButonu.addActionListener(this);
            renkPaneli.add(renkButonu);
            renkButonlari[i] = renkButonu;
        }
        panel.add(renkPaneli);

        add(panel, BorderLayout.CENTER);

        zamanlayici = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zamanSuresi--;
                sureLabel.setText("Kalan Süre: " + zamanSuresi);
                if (zamanSuresi <= 0 || puan >= hedefPuan) {
                    zamanlayici.stop();
                    for (JButton renkButonu : renkButonlari) {
                        renkButonu.setEnabled(false);
                    }
                    if (puan >= hedefPuan) {
                        JOptionPane.showMessageDialog(RenkAviOyunu.this, "Tebrikler! Oyunu kazandınız!");
                    } else {
                        JOptionPane.showMessageDialog(RenkAviOyunu.this, "Oyunu kaybettiniz!");
                    }
                }
            }
        });

        setSize(1000, 750);
        setLocationRelativeTo(null);
        setVisible(true);

        baslat();
    }

    public void baslat() {
        puan = 0;
        puanLabel.setText("Puan: " + puan);
        zamanSuresi = 30;
        sureLabel.setText("Kalan Süre: " + zamanSuresi);
        for (JButton renkButonu : renkButonlari) {
            renkButonu.setEnabled(true);
        }
        zamanlayici.start();
        seciliRenkGoster();
    }

    public void seciliRenkGoster() {  //bu metod, rastgele bir renk seçiyor ve "renk" butonunun rengini belirliyor.
        int index = (int) (Math.random() * renkler.length);
        Color seciliRenk = renkler[index];
        int seciliRenkIndex = (index + 1) % renkler.length;

        for (int i = 0; i < renkButonlari.length; i++) {
            JButton renkButonu = renkButonlari[i];
            if (i == seciliRenkIndex) {
                renkButonu.setText("Renk");
                renkButonu.setForeground(seciliRenk);
            } else {
                renkButonu.setText("");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) { //Kullanıcının seçtiği rengi alıyor ve "renk" butonunun metnindeki rengi kontrol ediyor. Eğer renkler eşleşiyorsa, puanı bir artırıyor. Eşleşmiyorsa, puanı bir azaltıyor.
        JButton tiklananButon = (JButton) e.getSource();
        Color seciliRenk = tiklananButon.getForeground();

        for (int i = 0; i < renkButonlari.length; i++) {
            JButton renkButonu = renkButonlari[i];
            if (renkButonu.getText().equals("Renk")) {
                Color renkYazisiRengi = renkButonu.getForeground();
                if (seciliRenk.equals(renkYazisiRengi)) {
                    puan--;
                } else {
                    puan++;
                }
                break;
            }
        }

        puanLabel.setText("Puan: " + puan);
        seciliRenkGoster();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RenkAviOyunu();
            }
        });
    }
}