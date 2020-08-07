package hbys.hrs.hasta;

import hbys.hrs.database.DatabaseConnection;
import hbys.hrs.yonetici.YoneticiMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class SorgulaHstRandevu extends JFrame
{
    private JButton btn_listele = null;
    private JButton btn_geri = null;

    private JTextField textbox_tc = new JTextField(11);
    private JTextField textbox_ad = new JTextField(30);
    private JTextField textbox_soyad = new JTextField(30);

    static boolean dogru_tc = false;

    public SorgulaHstRandevu()
    {
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gc = new GridBagConstraints();
        setLayout(layout);

        setTitle("Hasta Randevu Görüntüle");
        setSize(390, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JLabel tc_yazi = new JLabel("TC Kimlik No   ");
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=0;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(tc_yazi,gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=0;//satır
        gc.gridwidth=4;//genişlik
        gc.gridheight=1;//yükseklik
        add(textbox_tc,gc);

        JLabel ad_yazi = new JLabel("Ad");
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=1;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(ad_yazi,gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=1;//satır
        gc.gridwidth=4;//genişlik
        gc.gridheight=1;//yükseklik
        add(textbox_ad,gc);
        textbox_ad.setEditable(false);

        JLabel soyad_yazi = new JLabel("Soyad");
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=2;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(soyad_yazi,gc);
        textbox_soyad.setEditable(false);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=2;//satır
        gc.gridwidth=4;//genişlik
        gc.gridheight=1;//yükseklik
        add(textbox_soyad,gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=10;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(geri_buton(),gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=10;//satır
        gc.gridwidth=4;//genişlik
        gc.gridheight=1;//yükseklik
        add(listele_buton(),gc);
        btn_listele.setEnabled(false);

        textbox_tc.addKeyListener(new java.awt.event.KeyAdapter()    //ENTER BASILDIĞINI ANLAYAN KOD
        {
            public void keyPressed(java.awt.event.KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    String tc_temp = textbox_tc.getText();

                    if (Long.parseLong(tc_temp) < 10000000000L || Long.parseLong(tc_temp) > 99999999999L)
                    {
                        JOptionPane.showMessageDialog(null, "Lütfen Geçerli TC Kimlik Numarası Giriniz!" , "Hasta Randevu Görüntüleme", 0);
                    }
                    else
                    {
                        String sql = "SELECT HRS_HST_AD, HRS_HST_SOYAD FROM HRS_HASTA_KIMLIK_BILGI WHERE HRS_HST_TC_KIMLIK_NO =" + "'"+tc_temp+"'";

                        DatabaseConnection db_hasta_ara = new DatabaseConnection();

                        ResultSet gelen_veriler = db_hasta_ara.db_hasta_ara(sql);

                        try
                        {
                            if (gelen_veriler.next())
                            {
                                gelen_veriler.beforeFirst();
                                while (gelen_veriler.next())
                                {
                                    textbox_ad.setText(gelen_veriler.getString("HRS_HST_AD"));
                                    textbox_soyad.setText(gelen_veriler.getString("HRS_HST_SOYAD"));
                                    textbox_tc.setEditable(false);
                                    dogru_tc = true;
                                    btn_listele.setEnabled(true);
                                }
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "TC Kimlik Numarası Sistemde Kayıtlı Değildir!" , "Hasta Randevu Görüntüleme", 0);
                            }
                        }
                        catch (Exception x)
                        {
                            x.printStackTrace();
                        }
                    }

                }
            }
        });
    }

    public JButton listele_buton()    //KAPAT BUTONU
    {
        if (btn_listele == null)
        {
            btn_listele = new JButton();
            btn_listele.setBounds(103, 110, 100, 54);
            btn_listele.setText("Listele");
            Color renk = new Color(137, 200, 116);
            btn_listele.setBackground(renk);

            btn_listele.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    EventQueue.invokeLater(new Runnable()
                    {

                        public void run()
                        {
                            if (dogru_tc)
                            {
                                try
                                {
                                    ListeleHstRandevu.hst_tc = textbox_tc.getText();
                                    ListeleHstRandevu hstliste = new ListeleHstRandevu();
                                    ImageIcon image = new ImageIcon("Q:\\Java\\hbys.hrs\\resources\\icon3.png");
                                    hstliste.setIconImage(image.getImage());
                                    hstliste.addWindowListener(new WindowAdapter()
                                    {
                                        @Override
                                        public void windowClosing(WindowEvent e)
                                        {
                                            hstliste.setVisible(false);
                                            setVisible(true);
                                        }
                                    });
                                    hstliste.setVisible(true);
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                            }

                        }
                    });
                }
            });
        }//IF SONU
        return btn_listele;
    }
    public JButton geri_buton()    //KAPAT BUTONU
    {
        if (btn_geri == null)
        {
            btn_geri = new JButton();
            btn_geri.setBounds(103, 110, 100, 54);
            btn_geri.setText("Geri");
            Color renk = new Color(201, 93, 81);
            btn_geri.setBackground(renk);

            btn_geri.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    YoneticiMenu ynt_ekran = new YoneticiMenu();
                    ynt_ekran.setVisible(true);
                    ImageIcon image = new ImageIcon("Q:\\Java\\hbys.hrs\\resources\\icon3.png");
                    ynt_ekran.setIconImage(image.getImage());
                    setVisible(false);
                }
            });
        }//IF SONU
        return btn_geri;
    }//KAPAT BUTONU METOD SONU
}

