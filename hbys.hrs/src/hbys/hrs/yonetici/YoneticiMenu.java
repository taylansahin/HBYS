package hbys.hrs.yonetici;

import hbys.hrs.doktor.AylikRandevu;
import hbys.hrs.doktor.DoktorEkle;
import hbys.hrs.doktor.SorgulaDrRandevu;
import hbys.hrs.hasta.DuzenleHstRandevu;
import hbys.hrs.hasta.HastaEkle;
import hbys.hrs.hasta.OlusturHstRandevu;
import hbys.hrs.hasta.SorgulaHstRandevu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class YoneticiMenu extends JFrame
{
    private JButton btn_hst_islem = null;
    private JButton btn_kapat = null;
    private JButton btn_randevu_al = null;
    private JButton btn_randevu_olustur = null;
    private JButton btn_dr_randevu = null;
    private JButton btn_hst_randevu = null;
    private JButton btn_dr_ekle = null;
    private JButton btn_randevu_duzenle = null;
    private JButton btn_yonetici_ekle= null;

    static String kul_adi = new String();

    public YoneticiMenu()
    {
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gc = new GridBagConstraints();
        setLayout(layout);

        setTitle("Yönetici");
        setSize(390, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=0;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(hst_islem_buton(),gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=0;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(dr_ekle_buton(),gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=1;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(randevu_al_buton(),gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=1;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(randevu_duzenle_buton(),gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=2;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(dr_randevu_buton(),gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=2;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(hst_randevu_buton(),gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=6;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(yonetici_ekle_buton(),gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=6;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(randevu_olustur_buton(),gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=7;//satır
        gc.gridwidth=5;//genişlik
        gc.gridheight=1;//yükseklik
        add(kapat_buton(),gc);

        JLabel kullanici_yazi = new JLabel("Giriş Yapan Kullanıcı: " + kul_adi);
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=8;//satır
        gc.gridwidth=5;//genişlik
        gc.gridheight=1;//yükseklik
        add(kullanici_yazi,gc);

        btn_hst_islem.setToolTipText("Yeni Hasta Kaydı");
        btn_dr_ekle.setToolTipText("Yeni Doktor Kaydı");
        btn_randevu_al.setToolTipText("Yeni Randevu Oluştur");
        btn_randevu_duzenle.setToolTipText("Mevcut Randevu Tarihini Değiştir");
        btn_dr_randevu.setToolTipText("Doktorun Gelecek Randevularını Listele");
        btn_hst_randevu.setToolTipText("Hastanın Gelecek Randevularını Listele");
        btn_randevu_olustur.setToolTipText("Doktorlar İçin Aylık Randevu Çizelgesi Oluştur");
        btn_kapat.setToolTipText("Oturumu Kapatmak İçin Tıklayın");
    }

    public JButton randevu_al_buton()
    {
        if (btn_randevu_al == null)
        {
            btn_randevu_al = new JButton();
            btn_randevu_al.setBounds(103, 110, 100, 54);
            btn_randevu_al.setText("Randevu Oluştur");
            btn_randevu_al.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    OlusturHstRandevu rndv = new OlusturHstRandevu();
                    rndv.setVisible(true);
                    ImageIcon image = new ImageIcon("Q:\\Java\\hbys.hrs\\resources\\icon3.png");
                    rndv.setIconImage(image.getImage());
                    setVisible(false);
                }
            });
        }//IF SONU
        return btn_randevu_al;
    }

    public JButton randevu_duzenle_buton()
    {
        if (btn_randevu_duzenle == null)
        {
            btn_randevu_duzenle = new JButton();
            btn_randevu_duzenle.setBounds(103, 110, 100, 54);
            btn_randevu_duzenle.setText("Randevu Düzenle");
            btn_randevu_duzenle.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    DuzenleHstRandevu dznlrndv = new DuzenleHstRandevu();
                    dznlrndv.setVisible(true);
                    ImageIcon image = new ImageIcon("Q:\\Java\\hbys.hrs\\resources\\icon3.png");
                    dznlrndv.setIconImage(image.getImage());
                    setVisible(false);
                }
            });
        }//IF SONU
        return btn_randevu_duzenle;
    }

    public JButton yonetici_ekle_buton()
    {
        if (btn_yonetici_ekle == null)
        {
            btn_yonetici_ekle = new JButton();
            btn_yonetici_ekle.setBounds(103, 110, 100, 54);
            btn_yonetici_ekle.setText("Yönetici Ekle");
            btn_yonetici_ekle.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    YoneticiEkle yntcekle = new YoneticiEkle();
                    yntcekle.setVisible(true);
                    ImageIcon image = new ImageIcon("Q:\\Java\\hbys.hrs\\resources\\icon3.png");
                    yntcekle.setIconImage(image.getImage());
                    setVisible(false);
                }
            });
        }//IF SONU
        return btn_yonetici_ekle;
    }

    public JButton dr_randevu_buton()
    {
        if (btn_dr_randevu == null)
        {
            btn_dr_randevu = new JButton();
            btn_dr_randevu.setBounds(103, 110, 100, 54);
            btn_dr_randevu.setText("Doktor Randevu Planı");
            btn_dr_randevu.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    SorgulaDrRandevu drrndv = new SorgulaDrRandevu();
                    drrndv.setVisible(true);
                    ImageIcon image = new ImageIcon("Q:\\Java\\hbys.hrs\\resources\\icon3.png");
                    drrndv.setIconImage(image.getImage());
                    setVisible(false);
                }
            });
        }//IF SONU
        return btn_dr_randevu;
    }

    public JButton hst_randevu_buton()
    {
        if (btn_hst_randevu == null)
        {
            btn_hst_randevu = new JButton();
            btn_hst_randevu.setBounds(103, 110, 100, 54);
            btn_hst_randevu.setText("Hasta Randevu Listesi");
            btn_hst_randevu.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    SorgulaHstRandevu listerndv = new SorgulaHstRandevu();
                    listerndv.setVisible(true);
                    ImageIcon image = new ImageIcon("Q:\\Java\\hbys.hrs\\resources\\icon3.png");
                    listerndv.setIconImage(image.getImage());
                    setVisible(false);
                }
            });
        }//IF SONU
        return btn_hst_randevu;
    }

    public JButton randevu_olustur_buton()
    {
        if (btn_randevu_olustur == null)
        {
            btn_randevu_olustur = new JButton();
            btn_randevu_olustur.setBounds(103, 110, 100, 54);
            btn_randevu_olustur.setText("Aylık Randevu");
            btn_randevu_olustur.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    AylikRandevu dr_randevu = new AylikRandevu();
                    dr_randevu.setVisible(true);
                    ImageIcon image = new ImageIcon("Q:\\Java\\hbys.hrs\\resources\\icon3.png");
                    dr_randevu.setIconImage(image.getImage());
                    setVisible(false);
                }
            });
        }//IF SONU
        return btn_randevu_olustur;
    }

    public JButton hst_islem_buton()
    {
        if (btn_hst_islem == null)
        {
            btn_hst_islem = new JButton();
            btn_hst_islem.setBounds(103, 110, 100, 54);
            btn_hst_islem.setText("Hasta Ekle");
            btn_hst_islem.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    HastaEkle hst_ekle_pencere = new HastaEkle();
                    hst_ekle_pencere.setVisible(true);
                    ImageIcon image = new ImageIcon("Q:\\Java\\hbys.hrs\\resources\\icon3.png");
                    hst_ekle_pencere.setIconImage(image.getImage());
                    setVisible(false);
                }
            });
        }//IF SONU
        return btn_hst_islem;
    }

    public JButton dr_ekle_buton()
    {
        if (btn_dr_ekle == null)
        {
            btn_dr_ekle = new JButton();
            btn_dr_ekle.setBounds(103, 110, 100, 54);
            btn_dr_ekle.setText("Doktor Ekle");
            btn_dr_ekle.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    DoktorEkle dr_ekle_pencere = new DoktorEkle();
                    dr_ekle_pencere.setVisible(true);
                    ImageIcon image = new ImageIcon("Q:\\Java\\hbys.hrs\\resources\\icon3.png");
                    dr_ekle_pencere.setIconImage(image.getImage());
                    setVisible(false);
                }
            });
        }//IF SONU
        return btn_dr_ekle;
    }

    public JButton kapat_buton()    //KAPAT BUTONU
    {
        if (btn_kapat == null)
        {
            btn_kapat = new JButton();
            btn_kapat.setBounds(103, 110, 100, 54);
            btn_kapat.setText("Oturumu Kapat");
            Color renk = new Color(200, 93, 81);
            btn_kapat.setBackground(renk);

            btn_kapat.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    setVisible(false);
                    GirisYap grs_yap = new GirisYap();
                    grs_yap.setVisible(true);
                    ImageIcon image = new ImageIcon("Q:\\Java\\hbys.hrs\\resources\\icon3.png");
                    grs_yap.setIconImage(image.getImage());
                }
            });
        }//IF SONU
        return btn_kapat;
    }//KAPAT BUTONU METOD SONU

}//CLASS SONU
