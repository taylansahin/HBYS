package hbys.hrs.yonetici;

import hbys.hrs.database.DatabaseConnection;
import hbys.hrs.dto.DTO;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.ResultSet;

public class GirisYap extends JFrame
{

    private JButton btn_giris_yap = null;
    private JButton btn_cikis = null;
    JTextField textbox_kul_adi = new JTextField(12);
    JTextField textbox_sifre = new JTextField(12);

    DTO dto = new DTO();

    public GirisYap() //constructor metodu
    {
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gc = new GridBagConstraints();
        setLayout(layout);

        setTitle("Giriş Yap");
        setSize(390, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        try
        {
            BufferedImage image = ImageIO.read(new File("Q:\\Java\\hbys.hrs\\resources\\gazi.png"));
            JLabel label = new JLabel(new ImageIcon(image));
            gc.fill=GridBagConstraints.HORIZONTAL;
            gc.gridx=0;//sütun
            gc.gridy=0;//satır
            gc.gridwidth=4;//genişlik
            gc.gridheight=2;//yükseklik
            add(label,gc);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        JLabel bosluk0 = new JLabel(" ");
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=2;//satır
        gc.gridwidth=4;//genişlik
        gc.gridheight=1;//yükseklik
        add(bosluk0,gc);

        JLabel kul_adi_yazi = new JLabel("Kullanıcı Adı     ");
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=3;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(kul_adi_yazi,gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=3;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(textbox_kul_adi,gc);

        JLabel sifre_yazi = new JLabel("Şifre");
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=4;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(sifre_yazi,gc);


        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=4;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(textbox_sifre,gc);

        JLabel bosluk1 = new JLabel(" ");
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=5;//satır
        gc.gridwidth=4;//genişlik
        gc.gridheight=1;//yükseklik
        add(bosluk1,gc);

        gc.fill=GridBagConstraints.BOTH;
        gc.gridx=0;//sütun
        gc.gridy=6;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=2;//yükseklik
        add(cikis_buton(),gc);

        gc.fill=GridBagConstraints.BOTH;
        gc.gridx=2;//sütun
        gc.gridy=6;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=2;//yükseklik
        add(giris_yap_buton(),gc);

        textbox_sifre.addKeyListener(new java.awt.event.KeyAdapter()    //ENTER BASILDIĞINI ANLAYAN KOD
        {
            public void keyPressed(java.awt.event.KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    giris_dene();
                }
            }
        });
    }

    public JButton giris_yap_buton()
    {
        if (btn_giris_yap == null)
        {
            btn_giris_yap = new JButton();
            btn_giris_yap.setBounds(103, 110, 100, 54);
            btn_giris_yap.setText("Giriş Yap");
            Color renk = new Color(137, 200, 116);
            btn_giris_yap.setBackground(renk);

            btn_giris_yap.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    giris_dene();
                }
            });
        }//IF SONU
        return btn_giris_yap;
    }


    public void giris_dene()
    {
        dto.setHrs_kul_ad(textbox_kul_adi.getText());
        dto.setHrs_sifre(textbox_sifre.getText());

        if (dto.getHrs_kul_ad().trim().isEmpty() || dto.getHrs_sifre().trim().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Kullanıcı Adı ve Şifre Boş Geçilemez", "Giriş Yap", 0);
        }
        else
        {
            DatabaseConnection db_giris = new DatabaseConnection(); // database bağlantısı için nesne oluşturduk
            String sql = "SELECT HRS_SIFRE FROM HRS_KULLANICI WHERE HRS_KUL_AD ="+ "'"+dto.getHrs_kul_ad()+"'";
            ResultSet gelen_veriler = db_giris.db_hasta_ara(sql);

            String sifre_temp="";

            try
            {
                while(gelen_veriler.next())
                {
                    sifre_temp = gelen_veriler.getString("HRS_SIFRE");
                }
            }
            catch (Exception x)
            {
                x.printStackTrace();
            }

            if (dto.getHrs_sifre().equals(sifre_temp))
            {
                YoneticiMenu.kul_adi = dto.getHrs_kul_ad();
                YoneticiMenu yon_pencere = new YoneticiMenu();
                yon_pencere.setVisible(true);
                ImageIcon image = new ImageIcon("Q:\\Java\\hbys.hrs\\resources\\icon3.png");
                yon_pencere.setIconImage(image.getImage());
                setVisible(false);

            }
            else
            {
                JOptionPane.showMessageDialog(null, "Kullanıcı Adı veya Şifre Hatalı!", "Giriş Yap", 0);
            }
        }//boş kontrol eden else bloğu sonu
    }//giris_dene fonksiyonu sonu

    public JButton cikis_buton()
    {
        if (btn_cikis == null)
        {
            btn_cikis = new JButton();
            btn_cikis.setBounds(103, 110, 100, 54);
            btn_cikis.setText("Çıkış");
            Color renk = new Color(200, 93, 81);
            btn_cikis.setBackground(renk);

            btn_cikis.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    System.exit(0); //KAPAT
                }
            });
        }//IF SONU
        return btn_cikis;
    }
}
