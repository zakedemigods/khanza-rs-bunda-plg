/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgPasienMati.java
 *
 * Created on Aug 30, 2010, 7:46:01 AM
 */

package simrskhanza;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;

/**
 *
 * @author dosen3
 */
public class DlgPasienMati extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private DlgPasien pasien=new DlgPasien(null,false);
    private PreparedStatement ps;
    private ResultSet rs;
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private String tgl, finger="", lama="";
    
    /** Creates new form DlgPasienMati
     * @param parent
     * @param modal */
    public DlgPasienMati(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"Tgl.Masuk","Jam.Masuk","Tgl.Meninggal","Jam.Meninggal","No.Surat","No.R.Medik","Nama Pasien","J.K.","Tmp.Lahir",
                      "Tgl.Lahir","G.D.","Stts.Nikah","Agama","Keterangan","Tempat Meninggal",
                      "ICD-X","Antara 1","Antara 2","Langsung","Kode DPJP","Nama DPJP"};

        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbMati.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbMati.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbMati.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 21; i++) {
            TableColumn column = tbMati.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(75);
            }else if(i==1){
                column.setPreferredWidth(75);
            }else if(i==2){
                column.setPreferredWidth(80);
            }else if(i==3){
                column.setPreferredWidth(75);
            }else if(i==4){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setPreferredWidth(75);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(50);
            }else if(i==8){
                column.setPreferredWidth(80);
            }else if(i==9){
                column.setPreferredWidth(90);
            }else if(i==10){
                column.setPreferredWidth(90);
            }else if(i==11){
                column.setPreferredWidth(120);
            }else if(i==12){
                column.setPreferredWidth(120);
            }else if(i==13){
                column.setPreferredWidth(150);
            }else if(i==14){
                column.setPreferredWidth(150);
            }else if(i==15){
                column.setPreferredWidth(65);
            }else if(i==16){
                column.setPreferredWidth(65);
            }else if(i==17){
                column.setPreferredWidth(65);
            }else if(i==18){
                column.setPreferredWidth(65);
            }else if(i==19){
                column.setPreferredWidth(75);
            }else if(i==20){
                column.setPreferredWidth(150);
            }
        }
        tbMati.setDefaultRenderer(Object.class, new WarnaTable());


        TNoRM.setDocument(new batasInput((byte)15).getKata(TNoRM));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        TKtg.setDocument(new batasInput((byte)100).getKata(TKtg));
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
            });
        } 
        
        pasien.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgPasienMati")){
                    if(pasien.getTable().getSelectedRow()!= -1){                   
                        TNoRM.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),1).toString());
                        TPasien.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),2).toString());
                    }  
                    if(pasien.getTable2().getSelectedRow()!= -1){                   
                        TNoRM.setText(pasien.getTable2().getValueAt(pasien.getTable2().getSelectedRow(),1).toString());
                        TPasien.setText(pasien.getTable2().getValueAt(pasien.getTable2().getSelectedRow(),2).toString());
                    }  
                    if(pasien.getTable3().getSelectedRow()!= -1){                   
                        TNoRM.setText(pasien.getTable3().getValueAt(pasien.getTable3().getSelectedRow(),1).toString());
                        TPasien.setText(pasien.getTable3().getValueAt(pasien.getTable3().getSelectedRow(),2).toString());
                    }  
                    TNoRM.requestFocus();
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        pasien.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgPasienMati")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        pasien.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        pasien.getTable2().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgPasienMati")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        pasien.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        pasien.getTable3().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgPasienMati")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        pasien.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {;}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){                    
                    KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        ChkInput.setSelected(false);
        isForm();
    }
    
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnCetakSuratMati = new javax.swing.JMenuItem();
        MnCetakSuratMati1 = new javax.swing.JMenuItem();
        MnAngkutJenazah = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbMati = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnPrint1 = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel20 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        cmbStatus = new widget.ComboBox();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.panelisi();
        jLabel8 = new widget.Label();
        jLabel4 = new widget.Label();
        jLabel9 = new widget.Label();
        TKtg = new widget.TextBox();
        TPasien = new widget.TextBox();
        DTPTgl = new widget.Tanggal();
        TNoRM = new widget.TextBox();
        BtnSeek = new widget.Button();
        cmbJam = new widget.ComboBox();
        cmbMnt = new widget.ComboBox();
        cmbDtk = new widget.ComboBox();
        jLabel10 = new widget.Label();
        jLabel5 = new widget.Label();
        jLabel11 = new widget.Label();
        jLabel12 = new widget.Label();
        jLabel13 = new widget.Label();
        jLabel14 = new widget.Label();
        jLabel15 = new widget.Label();
        icd1 = new widget.TextBox();
        icd2 = new widget.TextBox();
        icd3 = new widget.TextBox();
        icd4 = new widget.TextBox();
        tmptmeninggal = new widget.ComboBox();
        jLabel16 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        TKtg1 = new widget.TextBox();
        jLabel17 = new widget.Label();
        jLabel18 = new widget.Label();
        TKtg2 = new widget.TextBox();
        TKtg3 = new widget.TextBox();
        jLabel22 = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCetakSuratMati.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSuratMati.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratMati.setForeground(java.awt.Color.darkGray);
        MnCetakSuratMati.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratMati.setText("Surat Kematian 1");
        MnCetakSuratMati.setName("MnCetakSuratMati"); // NOI18N
        MnCetakSuratMati.setPreferredSize(new java.awt.Dimension(190, 28));
        MnCetakSuratMati.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratMatiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakSuratMati);

        MnCetakSuratMati1.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSuratMati1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratMati1.setForeground(java.awt.Color.darkGray);
        MnCetakSuratMati1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratMati1.setText("Surat Kematian 2");
        MnCetakSuratMati1.setName("MnCetakSuratMati1"); // NOI18N
        MnCetakSuratMati1.setPreferredSize(new java.awt.Dimension(190, 28));
        MnCetakSuratMati1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratMati1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakSuratMati1);

        MnAngkutJenazah.setBackground(new java.awt.Color(255, 255, 254));
        MnAngkutJenazah.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnAngkutJenazah.setForeground(java.awt.Color.darkGray);
        MnAngkutJenazah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnAngkutJenazah.setText("Surat Angkut Jenazah");
        MnAngkutJenazah.setName("MnAngkutJenazah"); // NOI18N
        MnAngkutJenazah.setPreferredSize(new java.awt.Dimension(190, 28));
        MnAngkutJenazah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnAngkutJenazahActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnAngkutJenazah);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pasien Meninggal ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbMati.setAutoCreateRowSorter(true);
        tbMati.setComponentPopupMenu(jPopupMenu1);
        tbMati.setName("tbMati"); // NOI18N
        tbMati.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbMatiMouseClicked(evt);
            }
        });
        tbMati.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbMatiKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbMati);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        BtnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnBatal);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        BtnHapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnHapus);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnEdit);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        BtnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrintKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnPrint);

        BtnPrint1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint1.setMnemonic('J');
        BtnPrint1.setText("Cetak BPJS");
        BtnPrint1.setToolTipText("Alt+T");
        BtnPrint1.setName("BtnPrint1"); // NOI18N
        BtnPrint1.setPreferredSize(new java.awt.Dimension(105, 30));
        BtnPrint1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint1ActionPerformed(evt);
            }
        });
        BtnPrint1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrint1KeyPressed(evt);
            }
        });
        panelGlass8.add(BtnPrint1);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass8.add(LCount);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnKeluar);

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Periode :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-07-2022" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        DTPCari1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DTPCari1MouseClicked(evt);
            }
        });
        DTPCari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DTPCari1ActionPerformed(evt);
            }
        });
        panelGlass9.add(DTPCari1);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("s.d.");
        jLabel20.setName("jLabel20"); // NOI18N
        jLabel20.setPreferredSize(new java.awt.Dimension(24, 23));
        panelGlass9.add(jLabel20);

        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-07-2022" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel21.setText("< 48 Jam :");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel21);

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Ya", "Tidak" }));
        cmbStatus.setName("cmbStatus"); // NOI18N
        cmbStatus.setPreferredSize(new java.awt.Dimension(130, 23));
        cmbStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbStatusActionPerformed(evt);
            }
        });
        panelGlass9.add(cmbStatus);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(500, 23));
        TCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TCariActionPerformed(evt);
            }
        });
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
        BtnCari.setToolTipText("Alt+2");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelGlass9.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelGlass9.add(BtnAll);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setBackground(new java.awt.Color(255, 255, 255));
        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 185));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('M');
        ChkInput.setText(".: Filter Data");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 104));
        FormInput.setLayout(null);

        jLabel8.setText("Jam :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(209, 10, 39, 23);

        jLabel4.setText("No.Rekam Medik :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 40, 115, 23);

        jLabel9.setText("Keterangan :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(0, 130, 115, 23);

        TKtg.setHighlighter(null);
        TKtg.setName("TKtg"); // NOI18N
        TKtg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKtgKeyPressed(evt);
            }
        });
        FormInput.add(TKtg);
        TKtg.setBounds(120, 130, 200, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(230, 40, 393, 23);

        DTPTgl.setEditable(false);
        DTPTgl.setForeground(new java.awt.Color(50, 70, 50));
        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-07-2022" }));
        DTPTgl.setDisplayFormat("dd-MM-yyyy");
        DTPTgl.setName("DTPTgl"); // NOI18N
        DTPTgl.setOpaque(false);
        DTPTgl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DTPTglActionPerformed(evt);
            }
        });
        DTPTgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglKeyPressed(evt);
            }
        });
        FormInput.add(DTPTgl);
        DTPTgl.setBounds(120, 10, 90, 23);

        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(120, 40, 110, 23);

        BtnSeek.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek.setMnemonic('1');
        BtnSeek.setToolTipText("Alt+1");
        BtnSeek.setName("BtnSeek"); // NOI18N
        BtnSeek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekActionPerformed(evt);
            }
        });
        BtnSeek.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeekKeyPressed(evt);
            }
        });
        FormInput.add(BtnSeek);
        BtnSeek.setBounds(620, 40, 28, 23);

        cmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam.setName("cmbJam"); // NOI18N
        cmbJam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbJamActionPerformed(evt);
            }
        });
        cmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamKeyPressed(evt);
            }
        });
        FormInput.add(cmbJam);
        cmbJam.setBounds(251, 10, 62, 23);

        cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt.setName("cmbMnt"); // NOI18N
        cmbMnt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMntActionPerformed(evt);
            }
        });
        cmbMnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntKeyPressed(evt);
            }
        });
        FormInput.add(cmbMnt);
        cmbMnt.setBounds(316, 10, 62, 23);

        cmbDtk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk.setName("cmbDtk"); // NOI18N
        cmbDtk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDtkActionPerformed(evt);
            }
        });
        cmbDtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtkKeyPressed(evt);
            }
        });
        FormInput.add(cmbDtk);
        cmbDtk.setBounds(381, 10, 62, 23);

        jLabel10.setText("Tgl.Meninggal :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 115, 23);

        jLabel5.setText("Di :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(455, 10, 20, 23);

        jLabel11.setText("ICD-X ( Langsung ) :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(390, 100, 110, 23);

        jLabel12.setText("Penyebab Kematian :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(0, 70, 115, 23);

        jLabel13.setText("ICD-X ( Dasar ) :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(120, 70, 110, 23);

        jLabel14.setText("ICD-X ( Antara #1 ) :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(120, 100, 110, 23);

        jLabel15.setText("ICD-X ( Antara #2 ) :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(390, 70, 110, 23);

        icd1.setHighlighter(null);
        icd1.setName("icd1"); // NOI18N
        icd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                icd1ActionPerformed(evt);
            }
        });
        icd1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                icd1KeyPressed(evt);
            }
        });
        FormInput.add(icd1);
        icd1.setBounds(230, 70, 154, 23);

        icd2.setHighlighter(null);
        icd2.setName("icd2"); // NOI18N
        icd2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                icd2KeyPressed(evt);
            }
        });
        FormInput.add(icd2);
        icd2.setBounds(230, 100, 154, 23);

        icd3.setHighlighter(null);
        icd3.setName("icd3"); // NOI18N
        icd3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                icd3KeyPressed(evt);
            }
        });
        FormInput.add(icd3);
        icd3.setBounds(500, 70, 154, 23);

        icd4.setHighlighter(null);
        icd4.setName("icd4"); // NOI18N
        icd4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                icd4KeyPressed(evt);
            }
        });
        FormInput.add(icd4);
        icd4.setBounds(500, 100, 154, 23);

        tmptmeninggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Rumah Sakit", "Puskesmas", "Rumah Bersalin", "Rumah Tempat Tinggal", "Lain-lain (Termasuk Doa)", "Tidak tahu" }));
        tmptmeninggal.setName("tmptmeninggal"); // NOI18N
        tmptmeninggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tmptmeninggalKeyPressed(evt);
            }
        });
        FormInput.add(tmptmeninggal);
        tmptmeninggal.setBounds(478, 10, 176, 23);

        jLabel16.setText("DPJP :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(310, 130, 60, 23);

        KdDokter.setEditable(false);
        KdDokter.setHighlighter(null);
        KdDokter.setName("KdDokter"); // NOI18N
        FormInput.add(KdDokter);
        KdDokter.setBounds(370, 130, 87, 23);

        NmDokter.setEditable(false);
        NmDokter.setHighlighter(null);
        NmDokter.setName("NmDokter"); // NOI18N
        FormInput.add(NmDokter);
        NmDokter.setBounds(460, 130, 160, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('X');
        BtnDokter.setToolTipText("Alt+X");
        BtnDokter.setName("BtnDokter"); // NOI18N
        BtnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterActionPerformed(evt);
            }
        });
        BtnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokterKeyPressed(evt);
            }
        });
        FormInput.add(BtnDokter);
        BtnDokter.setBounds(620, 130, 28, 23);

        TKtg1.setHighlighter(null);
        TKtg1.setName("TKtg1"); // NOI18N
        TKtg1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKtg1KeyPressed(evt);
            }
        });
        FormInput.add(TKtg1);
        TKtg1.setBounds(120, 160, 200, 23);

        jLabel17.setText("No Surat Kematian :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(0, 160, 115, 23);

        jLabel18.setText("Jam :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(510, 160, 30, 23);

        TKtg2.setEditable(false);
        TKtg2.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        TKtg2.setEnabled(false);
        TKtg2.setHighlighter(null);
        TKtg2.setName("TKtg2"); // NOI18N
        TKtg2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TKtg2ActionPerformed(evt);
            }
        });
        TKtg2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKtg2KeyPressed(evt);
            }
        });
        FormInput.add(TKtg2);
        TKtg2.setBounds(390, 160, 110, 23);

        TKtg3.setEditable(false);
        TKtg3.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        TKtg3.setEnabled(false);
        TKtg3.setHighlighter(null);
        TKtg3.setName("TKtg3"); // NOI18N
        TKtg3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TKtg3ActionPerformed(evt);
            }
        });
        TKtg3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKtg3KeyPressed(evt);
            }
        });
        FormInput.add(TKtg3);
        TKtg3.setBounds(540, 160, 110, 23);

        jLabel22.setText("Tgl Masuk :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(320, 160, 70, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed
        Valid.pindah(evt,TCari,cmbJam);
}//GEN-LAST:event_DTPTglKeyPressed

    private void BtnSeekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekActionPerformed
        akses.setform("DlgPasienMati");
        pasien.emptTeks();
        pasien.isCek();
        pasien.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.setLocationRelativeTo(internalFrame1);
        pasien.setVisible(true);
}//GEN-LAST:event_BtnSeekActionPerformed

    private void BtnSeekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeekKeyPressed
        Valid.pindah(evt,TNoRM,TKtg);
}//GEN-LAST:event_BtnSeekKeyPressed

    private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJamKeyPressed
        Valid.pindah(evt,DTPTgl,cmbMnt);
}//GEN-LAST:event_cmbJamKeyPressed

    private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMntKeyPressed
        Valid.pindah(evt,cmbJam,cmbDtk);
}//GEN-LAST:event_cmbMntKeyPressed

    private void cmbDtkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDtkKeyPressed
        Valid.pindah(evt,cmbMnt,tmptmeninggal);
}//GEN-LAST:event_cmbDtkKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRM.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRM,"pasien");
        }else if(TKtg.getText().trim().equals("")){
            Valid.textKosong(TKtg,"keterangan");
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter DPJP");
        }else{
            lama=Sequel.cariIsi("SELECT hour(timediff('"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+" "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"', '"+TKtg2.getText()+" "+TKtg3.getText()+"')) as lama");
            if(Sequel.menyimpantf("pasien_mati","'"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"','"+
                    cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"','"+
                    TNoRM.getText()+"','"+TKtg.getText()+"','"+tmptmeninggal.getSelectedItem()+"','"+
                    icd1.getText()+"','"+icd2.getText()+"','"+icd3.getText()+"','"+
                    icd4.getText()+"','"+KdDokter.getText()+"','"+TKtg1.getText()+"','"+
                    TKtg2.getText()+"','"+TKtg3.getText()+"','"+lama+"'","pasien")==true){
                tampil();
                emptTeks();
                autoNomor();
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,TKtg,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
        autoNomor();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        Valid.hapusTable(tabMode,TNoRM,"pasien_mati","pasien_mati.no_rkm_medis");
        tampil();
        emptTeks();
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(! TCari.getText().trim().equals("")){
            BtnCariActionPerformed(evt);
        }
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){            
            Map<String, Object> param = new HashMap<>();    
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                tgl=" pasien_mati.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' ";
                if(TCari.getText().trim().equals("")){
                    if(cmbStatus.getSelectedItem().toString().equals("Ya")){
                        Valid.MyReportqry("rptPasienMati.jasper","report","::[ Data Pasien Meninggal ]::",
                            "select pasien_mati.tanggal,pasien_mati.jam,pasien_mati.no_rkm_medis,pasien.nm_pasien, "+
                            "pasien.jk,pasien.tmp_lahir,pasien.tgl_lahir,pasien.gol_darah,pasien.stts_nikah, "+
                            "pasien.agama,pasien_mati.keterangan,pasien_mati.temp_meninggal,pasien_mati.icd1,pasien_mati.icd2,"+
                            "pasien_mati.icd3,pasien_mati.icd4,pasien_mati.kd_dokter,dokter.nm_dokter,pasien_mati.no_surat,pasien.no_peserta,if(pasien_mati.lama < 48,'Ya','Tidak') as status  "+
                            "from pasien_mati inner join pasien on pasien_mati.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join dokter on pasien_mati.kd_dokter=dokter.kd_dokter where pasien_mati.lama < 48 and"+tgl+"",param);
                    }else if(cmbStatus.getSelectedItem().toString().equals("Tidak")){
                        Valid.MyReportqry("rptPasienMati.jasper","report","::[ Data Pasien Meninggal ]::",
                            "select pasien_mati.tanggal,pasien_mati.jam,pasien_mati.no_rkm_medis,pasien.nm_pasien, "+
                            "pasien.jk,pasien.tmp_lahir,pasien.tgl_lahir,pasien.gol_darah,pasien.stts_nikah, "+
                            "pasien.agama,pasien_mati.keterangan,pasien_mati.temp_meninggal,pasien_mati.icd1,pasien_mati.icd2,"+
                            "pasien_mati.icd3,pasien_mati.icd4,pasien_mati.kd_dokter,dokter.nm_dokter,pasien_mati.no_surat,pasien.no_peserta,if(pasien_mati.lama < 48,'Ya','Tidak') as status  "+
                            "from pasien_mati inner join pasien on pasien_mati.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join dokter on pasien_mati.kd_dokter=dokter.kd_dokter where pasien_mati.lama > 48 and"+tgl+"",param);
                    }else{
                        Valid.MyReportqry("rptPasienMati.jasper","report","::[ Data Pasien Meninggal ]::",
                            "select pasien_mati.tanggal,pasien_mati.jam,pasien_mati.no_rkm_medis,pasien.nm_pasien, "+
                            "pasien.jk,pasien.tmp_lahir,pasien.tgl_lahir,pasien.gol_darah,pasien.stts_nikah, "+
                            "pasien.agama,pasien_mati.keterangan,pasien_mati.temp_meninggal,pasien_mati.icd1,pasien_mati.icd2,"+
                            "pasien_mati.icd3,pasien_mati.icd4,pasien_mati.kd_dokter,dokter.nm_dokter,pasien_mati.no_surat,pasien.no_peserta,if(pasien_mati.lama < 48,'Ya','Tidak') as status  "+
                            "from pasien_mati inner join pasien on pasien_mati.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join dokter on pasien_mati.kd_dokter=dokter.kd_dokter where pasien_mati.lama <> '' and"+tgl+"",param);
                    }
                }else{
                    if(cmbStatus.getSelectedItem().toString().equals("Ya")){
                        Valid.MyReportqry("rptPasienMati.jasper","report","::[ Data Pasien Meninggal ]::",
                            "select pasien_mati.tanggal,pasien_mati.jam,pasien_mati.no_rkm_medis,pasien.nm_pasien, "+
                            "pasien.jk,pasien.tmp_lahir,pasien.tgl_lahir,pasien.gol_darah,pasien.stts_nikah, "+
                            "pasien.agama,pasien_mati.keterangan,pasien_mati.temp_meninggal,pasien_mati.icd1,pasien_mati.icd2,"+
                            "pasien_mati.icd3,pasien_mati.icd4,pasien_mati.kd_dokter,dokter.nm_dokter,pasien_mati.no_surat,pasien_mati.tanggal_masuk,pasien.no_peserta,if(pasien_mati.lama < 48,'Ya','Tidak') as status  "+
                            "from pasien_mati inner join pasien on pasien_mati.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join dokter on pasien_mati.kd_dokter=dokter.kd_dokter "+
                            "where pasien_mati.lama < 48 and"+tgl+"and pasien_mati.tanggal like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pasien_mati.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pasien.stts_nikah like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pasien.agama like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pasien_mati.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pasien_mati.icd1 like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pasien_mati.keterangan like '%"+TCari.getText().trim()+"%' "+        
                            "order by pasien_mati.tanggal",param); 
                    }else if(cmbStatus.getSelectedItem().toString().equals("Tidak")){
                        Valid.MyReportqry("rptPasienMati.jasper","report","::[ Data Pasien Meninggal ]::",
                            "select pasien_mati.tanggal,pasien_mati.jam,pasien_mati.no_rkm_medis,pasien.nm_pasien, "+
                            "pasien.jk,pasien.tmp_lahir,pasien.tgl_lahir,pasien.gol_darah,pasien.stts_nikah, "+
                            "pasien.agama,pasien_mati.keterangan,pasien_mati.temp_meninggal,pasien_mati.icd1,pasien_mati.icd2,"+
                            "pasien_mati.icd3,pasien_mati.icd4,pasien_mati.kd_dokter,dokter.nm_dokter,pasien_mati.no_surat,pasien_mati.tanggal_masuk,pasien.no_peserta,if(pasien_mati.lama < 48,'Ya','Tidak') as status  "+
                            "from pasien_mati inner join pasien on pasien_mati.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join dokter on pasien_mati.kd_dokter=dokter.kd_dokter "+
                            "where pasien_mati.lama > 48 and"+tgl+"and pasien_mati.tanggal like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pasien_mati.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pasien.stts_nikah like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pasien.agama like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pasien_mati.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pasien_mati.icd1 like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pasien_mati.keterangan like '%"+TCari.getText().trim()+"%' "+        
                            "order by pasien_mati.tanggal",param); 
                    }else{
                        Valid.MyReportqry("rptPasienMati.jasper","report","::[ Data Pasien Meninggal ]::",
                            "select pasien_mati.tanggal,pasien_mati.jam,pasien_mati.no_rkm_medis,pasien.nm_pasien, "+
                            "pasien.jk,pasien.tmp_lahir,pasien.tgl_lahir,pasien.gol_darah,pasien.stts_nikah, "+
                            "pasien.agama,pasien_mati.keterangan,pasien_mati.temp_meninggal,pasien_mati.icd1,pasien_mati.icd2,"+
                            "pasien_mati.icd3,pasien_mati.icd4,pasien_mati.kd_dokter,dokter.nm_dokter,pasien_mati.no_surat,pasien_mati.tanggal_masuk,pasien.no_peserta,if(pasien_mati.lama < 48,'Ya','Tidak') as status  "+
                            "from pasien_mati inner join pasien on pasien_mati.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join dokter on pasien_mati.kd_dokter=dokter.kd_dokter "+
                            "where pasien_mati.lama <> '' and"+tgl+"and pasien_mati.tanggal like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pasien_mati.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pasien.stts_nikah like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pasien.agama like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pasien_mati.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pasien_mati.icd1 like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pasien_mati.keterangan like '%"+TCari.getText().trim()+"%' "+        
                            "order by pasien_mati.tanggal",param); 
                    }
                }
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            TCari.setText("");
            tampil();
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbMatiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMatiMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbMatiMouseClicked

private void MnCetakSuratMatiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratMatiActionPerformed
      if(TPasien.getText().trim().equals("")){
          JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");                
      }else{
          Map<String, Object> param = new HashMap<>(); 
          param.put("namars",akses.getnamars());
          param.put("alamatrs",akses.getalamatrs());
          param.put("kotars",akses.getkabupatenrs());
          param.put("propinsirs",akses.getpropinsirs());
          param.put("kontakrs",akses.getkontakrs());
          param.put("emailrs",akses.getemailrs());   
          param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
          finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",KdDokter.getText());
          param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+NmDokter.getText()+"\nID "+(finger.equals("")?KdDokter.getText():finger)+"\n"+DTPTgl.getSelectedItem());  
          Valid.MyReportqry("rptSuratKematian.jasper","report","::[ Surat Kematian ]::",
                "select date_format(pasien_mati.tanggal,'%d-%m-%Y') as tanggal,pasien_mati.jam,pasien_mati.no_rkm_medis,pasien.nm_pasien, "+
                "pasien.jk,pasien.tmp_lahir,pasien.tgl_lahir,pasien.gol_darah,pasien.stts_nikah,pasien.umur,pasien.alamat, "+
                "pasien.agama,pasien_mati.keterangan,pasien_mati.temp_meninggal,pasien_mati.icd1,pasien_mati.icd2,"+
                "pasien_mati.icd3,pasien_mati.icd4,pasien_mati.kd_dokter,dokter.nm_dokter,pasien_mati.no_surat "+
                "from pasien_mati inner join pasien on pasien_mati.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join dokter on pasien_mati.kd_dokter=dokter.kd_dokter where pasien_mati.no_rkm_medis='"+TNoRM.getText()+"' ",param);
      }
}//GEN-LAST:event_MnCetakSuratMatiActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void MnAngkutJenazahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnAngkutJenazahActionPerformed
        if(TPasien.getText().trim().equals("")){
          JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");                
        }else{
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",KdDokter.getText());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+NmDokter.getText()+"\nID "+(finger.equals("")?KdDokter.getText():finger)+"\n"+DTPTgl.getSelectedItem());  
            Valid.MyReportqry("rptAngkutJenazah.jasper","report","::[ Surat Angkut Jenazah ]::",
                "select date_format(pasien_mati.tanggal,'%d-%m-%Y') as tanggal,pasien_mati.jam,pasien_mati.no_rkm_medis,pasien.nm_pasien, "+
                "pasien.jk,pasien.tmp_lahir,pasien.tgl_lahir,pasien.gol_darah,pasien.stts_nikah,pasien.umur,pasien.alamat,pasien.pekerjaan, "+
                "pasien.agama,pasien_mati.keterangan,pasien_mati.temp_meninggal,pasien_mati.icd1,pasien_mati.icd2,"+
                "pasien_mati.icd3,pasien_mati.icd4,pasien_mati.kd_dokter,dokter.nm_dokter,pasien_mati.no_surat "+
                "from pasien_mati inner join pasien on pasien_mati.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join dokter on pasien_mati.kd_dokter=dokter.kd_dokter where pasien_mati.no_rkm_medis='"+TNoRM.getText()+"' ",param);
        }
    }//GEN-LAST:event_MnAngkutJenazahActionPerformed

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis=? ",TPasien,TNoRM.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeekActionPerformed(null);
        }else{
            Valid.pindah(evt,tmptmeninggal,icd1);
        }
    }//GEN-LAST:event_TNoRMKeyPressed

    private void icd1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_icd1KeyPressed
        Valid.pindah(evt,TNoRM,icd2);
    }//GEN-LAST:event_icd1KeyPressed

    private void icd2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_icd2KeyPressed
        Valid.pindah(evt,icd1,icd3);
    }//GEN-LAST:event_icd2KeyPressed

    private void icd3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_icd3KeyPressed
        Valid.pindah(evt,icd2,icd4);
    }//GEN-LAST:event_icd3KeyPressed

    private void icd4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_icd4KeyPressed
        Valid.pindah(evt,icd3,TKtg);
    }//GEN-LAST:event_icd4KeyPressed

    private void tmptmeninggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tmptmeninggalKeyPressed
        Valid.pindah(evt,cmbDtk,TNoRM);
    }//GEN-LAST:event_tmptmeninggalKeyPressed

    private void TKtgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKtgKeyPressed
        Valid.pindah(evt,icd4,BtnSimpan);
    }//GEN-LAST:event_TKtgKeyPressed

    private void tbMatiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbMatiKeyReleased
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbMatiKeyReleased

    private void MnCetakSuratMati1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratMati1ActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");                
        }else{
            Map<String, Object> param = new HashMap<>(); 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",KdDokter.getText());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+NmDokter.getText()+"\nID "+(finger.equals("")?KdDokter.getText():finger)+"\n"+DTPTgl.getSelectedItem());  
            Valid.MyReportqry("rptSuratKematian2.jasper","report","::[ Surat Kematian ]::",
                "select date_format(pasien_mati.tanggal,'%d-%m-%Y') as tanggal,pasien_mati.jam,pasien_mati.no_rkm_medis,pasien.nm_pasien, "+
                "pasien.jk,pasien.tmp_lahir,pasien.tgl_lahir,pasien.gol_darah,pasien.stts_nikah,pasien.umur,pasien.alamat, "+
                "pasien.agama,pasien_mati.keterangan,pasien_mati.temp_meninggal,pasien_mati.icd1,pasien_mati.icd2,"+
                "pasien_mati.icd3,pasien_mati.icd4,pasien_mati.kd_dokter,dokter.nm_dokter,pasien_mati.no_surat "+
                "from pasien_mati inner join pasien on pasien_mati.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join dokter on pasien_mati.kd_dokter=dokter.kd_dokter where pasien_mati.no_rkm_medis='"+TNoRM.getText()+"' ",param);
        }
    }//GEN-LAST:event_MnCetakSuratMati1ActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        dokter.isCek();
        dokter.TCari.requestFocus();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnDokterActionPerformed(null);
        }else{
            Valid.pindah(evt,TKtg,BtnSimpan);
        }
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void TKtg1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKtg1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TKtg1KeyPressed

    private void TKtg2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKtg2KeyPressed
        // TODO add your handling code here:
        TKtg2.setEditable(false);
    }//GEN-LAST:event_TKtg2KeyPressed

    private void TKtg2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TKtg2ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_TKtg2ActionPerformed

    private void BtnPrint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint1ActionPerformed
        // TODO add your handling code here:
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(! TCari.getText().trim().equals("")){
            BtnCariActionPerformed(evt);
        }
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){            
            Map<String, Object> param = new HashMap<>();    
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                tgl=" pasien_mati.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' ";
                if(TCari.getText().trim().equals("")){
                    Valid.MyReportqry("rptPasienMati-BPJS.jasper","report","::[ Data Pasien Meninggal ]::",
                        "select pasien_mati.tanggal,pasien_mati.jam,pasien_mati.no_rkm_medis,pasien.nm_pasien, "+
                        "pasien.jk,pasien.tmp_lahir,pasien.tgl_lahir,pasien.gol_darah,pasien.stts_nikah, "+
                        "pasien.agama,pasien_mati.keterangan,pasien_mati.temp_meninggal,pasien_mati.icd1,pasien_mati.icd2,"+
                        "pasien_mati.icd3,pasien_mati.icd4,pasien_mati.kd_dokter,dokter.nm_dokter,pasien_mati.no_surat,pasien_mati.tanggal_masuk,pasien.no_peserta "+
                        "from pasien_mati inner join pasien on pasien_mati.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join dokter on pasien_mati.kd_dokter=dokter.kd_dokter where "+tgl+"and (pasien.no_peserta != '-' or pasien.no_peserta != null)",param);
                }else{
                    Valid.MyReportqry("rptPasienMati-BPJS.jasper","report","::[ Data Pasien Meninggal ]::",
                        "select pasien_mati.tanggal,pasien_mati.jam,pasien_mati.no_rkm_medis,pasien.nm_pasien, "+
                        "pasien.jk,pasien.tmp_lahir,pasien.tgl_lahir,pasien.gol_darah,pasien.stts_nikah, "+
                        "pasien.agama,pasien_mati.keterangan,pasien_mati.temp_meninggal,pasien_mati.icd1,pasien_mati.icd2,"+
                        "pasien_mati.icd3,pasien_mati.icd4,pasien_mati.kd_dokter,dokter.nm_dokter,pasien_mati.no_surat,pasien_mati.tanggal_masuk,pasien.no_peserta "+
                        "from pasien_mati inner join pasien on pasien_mati.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join dokter on pasien_mati.kd_dokter=dokter.kd_dokter "+
                        "where "+tgl+"and pasien_mati.tanggal like '%"+TCari.getText().trim()+"%' or "+
                        tgl+"and pasien_mati.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                        tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                        tgl+"and pasien.stts_nikah like '%"+TCari.getText().trim()+"%' or "+
                        tgl+"and pasien.agama like '%"+TCari.getText().trim()+"%' or "+
                        tgl+"and pasien_mati.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                        tgl+"and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                        tgl+"and pasien_mati.icd1 like '%"+TCari.getText().trim()+"%' or "+
                        tgl+"and pasien_mati.keterangan like '%"+TCari.getText().trim()+"%' "+        
                        "order by pasien_mati.tanggal",param);
                }
                            
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrint1ActionPerformed

    private void BtnPrint1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrint1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPrint1KeyPressed

    private void DTPCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DTPCari1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DTPCari1ActionPerformed

    private void DTPCari1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DTPCari1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_DTPCari1MouseClicked

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TKtg1.getText().trim().equals("")){
            Valid.textKosong(TKtg1,"No.Surat");
        }else if(icd1.getText().trim().equals("")){
            Valid.textKosong(icd1,"ICD-X");
        }else if(icd2.getText().trim().equals("")){
            Valid.textKosong(icd2,"Antara 1");
        }else if(icd3.getText().trim().equals("")){
            Valid.textKosong(icd3,"Antara 2");
        }else if(icd4.getText().trim().equals("")){
            Valid.textKosong(icd4,"Langsung");
        }else if(KdDokter.getText().trim().equals("")){
            Valid.textKosong(KdDokter,"Nama DPJP");
        }else if(TKtg.getText().trim().equals("")){
            Valid.textKosong(TKtg,"Keterangan");
        }else{
            if(tbMati.getSelectedRow()!= -1){
                lama=Sequel.cariIsi("SELECT hour(timediff('"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+" "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"', '"+TKtg2.getText()+" "+TKtg3.getText()+"')) as lama");
                if(Sequel.mengedittf("pasien_mati","no_surat='"+TKtg1.getText()+"'","no_surat=?,icd1=?,icd2=?,icd3=?,icd4=?,keterangan=?,temp_meninggal=?,kd_dokter=?,jam=?,no_rkm_medis=?,tanggal=?,lama=?",12,new String[]{
                    TKtg1.getText(),icd1.getText(),icd2.getText(),icd3.getText(),icd4.getText(),TKtg.getText(),
                    tmptmeninggal.getSelectedItem()+"",KdDokter.getText(),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                    TNoRM.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),lama,tbMati.getValueAt(tbMati.getSelectedRow(),0).toString()
                })==true){
                    tampil();
                    emptTeks();
                }
            }
        }
    }//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
    }//GEN-LAST:event_BtnEditKeyPressed

    private void icd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_icd1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_icd1ActionPerformed

    private void DTPTglActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DTPTglActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DTPTglActionPerformed

    private void cmbJamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbJamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbJamActionPerformed

    private void cmbMntActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMntActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbMntActionPerformed

    private void cmbDtkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDtkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbDtkActionPerformed

    private void TKtg3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TKtg3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TKtg3ActionPerformed

    private void TKtg3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKtg3KeyPressed
        // TODO add your handling code here:
        TKtg3.setEditable(false);
    }//GEN-LAST:event_TKtg3KeyPressed

    private void TCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TCariActionPerformed

    private void cmbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbStatusActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPasienMati dialog = new DlgPasienMati(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint1;
    private widget.Button BtnSeek;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPTgl;
    private widget.panelisi FormInput;
    private widget.TextBox KdDokter;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnAngkutJenazah;
    private javax.swing.JMenuItem MnCetakSuratMati;
    private javax.swing.JMenuItem MnCetakSuratMati1;
    private widget.TextBox NmDokter;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TKtg;
    private widget.TextBox TKtg1;
    private widget.TextBox TKtg2;
    private widget.TextBox TKtg3;
    private widget.TextBox TNoRM;
    private widget.TextBox TPasien;
    private widget.ComboBox cmbDtk;
    private widget.ComboBox cmbJam;
    private widget.ComboBox cmbMnt;
    private widget.ComboBox cmbStatus;
    private widget.TextBox icd1;
    private widget.TextBox icd2;
    private widget.TextBox icd3;
    private widget.TextBox icd4;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbMati;
    private widget.ComboBox tmptmeninggal;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            tgl=" pasien_mati.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' ";
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement("select pasien_mati.tanggal_masuk,pasien_mati.jam_masuk,pasien_mati.tanggal,pasien_mati.jam,pasien_mati.no_surat,pasien_mati.no_rkm_medis,pasien.nm_pasien, "+
                   "pasien.jk,pasien.tmp_lahir,pasien.tgl_lahir,pasien.gol_darah,pasien.stts_nikah, "+
                   "pasien.agama,pasien_mati.keterangan,pasien_mati.temp_meninggal,pasien_mati.icd1,pasien_mati.icd2,"+
                   "pasien_mati.icd3,pasien_mati.icd4,pasien_mati.kd_dokter,dokter.nm_dokter,if(pasien_mati.lama < 48,'Ya','Tidak') as status "+
                   "from pasien_mati inner join pasien on pasien_mati.no_rkm_medis=pasien.no_rkm_medis "+
                   "inner join dokter on pasien_mati.kd_dokter=dokter.kd_dokter "+
                   "where pasien_mati.lama <> '' and"+tgl+"order by pasien_mati.tanggal");
            }else{
                ps=koneksi.prepareStatement("select pasien_mati.tanggal_masuk,pasien_mati.jam_masuk,pasien_mati.tanggal,pasien_mati.jam,pasien_mati.no_surat,pasien_mati.no_rkm_medis,pasien.nm_pasien, "+
                   "pasien.jk,pasien.tmp_lahir,pasien.tgl_lahir,pasien.gol_darah,pasien.stts_nikah, "+
                   "pasien.agama,pasien_mati.keterangan,pasien_mati.temp_meninggal,pasien_mati.icd1,pasien_mati.icd2,"+
                   "pasien_mati.icd3,pasien_mati.icd4,pasien_mati.kd_dokter,dokter.nm_dokter,if(pasien_mati.lama < 48,'Ya','Tidak') as status "+
                   "from pasien_mati inner join pasien on pasien_mati.no_rkm_medis=pasien.no_rkm_medis "+
                   "inner join dokter on pasien_mati.kd_dokter=dokter.kd_dokter "+
                   "where pasien_mati.lama <> '' and"+tgl+"and pasien_mati.tanggal like '%"+TCari.getText().trim()+"%' or "+
                   tgl+"and pasien_mati.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                   tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                   tgl+"and pasien.stts_nikah like '%"+TCari.getText().trim()+"%' or "+
                   tgl+"and pasien.agama like '%"+TCari.getText().trim()+"%' or "+
                   tgl+"and pasien_mati.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                   tgl+"and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                   tgl+"and pasien_mati.icd1 like '%"+TCari.getText().trim()+"%' or "+
                   tgl+"and pasien_mati.keterangan like '%"+TCari.getText().trim()+"%' "+
                   "order by pasien_mati.tanggal ");
            }
            
            try {
                rs=ps.executeQuery();
                if(cmbStatus.getSelectedItem().toString().equals("Semua")){
                    while(rs.next()){               
                        tabMode.addRow(new Object[]{
                            rs.getString("tanggal_masuk"),rs.getString("jam_masuk"),rs.getString("tanggal"),rs.getString("jam"),
                            rs.getString("no_surat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("jk"),
                            rs.getString("tmp_lahir"),rs.getString("tgl_lahir"),rs.getString("gol_darah"),rs.getString("stts_nikah"),
                            rs.getString("agama"),rs.getString("keterangan"),rs.getString("temp_meninggal"),rs.getString("icd1"),
                            rs.getString("icd2"),rs.getString("icd3"),rs.getString("icd4"),rs.getString("kd_dokter"),rs.getString("nm_dokter")
                        });
                    }
                }else{
                    while(rs.next()){    
                        if(rs.getString("status").equals(cmbStatus.getSelectedItem().toString())){
                            tabMode.addRow(new Object[]{
                                rs.getString("tanggal_masuk"),rs.getString("jam_masuk"),rs.getString("tanggal"),rs.getString("jam"),
                                rs.getString("no_surat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("jk"),
                                rs.getString("tmp_lahir"),rs.getString("tgl_lahir"),rs.getString("gol_darah"),rs.getString("stts_nikah"),
                                rs.getString("agama"),rs.getString("keterangan"),rs.getString("temp_meninggal"),rs.getString("icd1"),
                                rs.getString("icd2"),rs.getString("icd3"),rs.getString("icd4"),rs.getString("kd_dokter"),rs.getString("nm_dokter")
                            });
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
        int b=tabMode.getRowCount();
        LCount.setText(""+b);
    }

    public void emptTeks() {
        TNoRM.setText("");
        TPasien.setText("");
        TKtg.setText("");
        tmptmeninggal.setSelectedItem("");
        cmbJam.setSelectedItem("");
        cmbMnt.setSelectedItem("");
        cmbDtk.setSelectedItem("");
        icd1.setText("");
        icd2.setText("");
        icd3.setText("");
        icd4.setText("");
        KdDokter.setText("");
        NmDokter.setText("");
        TKtg1.setText("");
        TKtg2.setText("");
        TKtg3.setText("");
        DTPTgl.setDate(new Date());
        DTPTgl.requestFocus();
    }

    private void getData() {
        if(tbMati.getSelectedRow()!= -1){
            cmbJam.setSelectedItem(tbMati.getValueAt(tbMati.getSelectedRow(),3).toString().substring(0,2));
            cmbMnt.setSelectedItem(tbMati.getValueAt(tbMati.getSelectedRow(),3).toString().substring(3,5));
            cmbDtk.setSelectedItem(tbMati.getValueAt(tbMati.getSelectedRow(),3).toString().substring(6,8));
            TKtg1.setText(tbMati.getValueAt(tbMati.getSelectedRow(),4).toString());
            TNoRM.setText(tbMati.getValueAt(tbMati.getSelectedRow(),5).toString());
            TPasien.setText(tbMati.getValueAt(tbMati.getSelectedRow(),6).toString());
            TKtg.setText(tbMati.getValueAt(tbMati.getSelectedRow(),13).toString());
            TKtg2.setText(tbMati.getValueAt(tbMati.getSelectedRow(),0).toString());
            TKtg3.setText(tbMati.getValueAt(tbMati.getSelectedRow(),1).toString());
            Valid.SetTgl(DTPTgl,tbMati.getValueAt(tbMati.getSelectedRow(),2).toString());
            tmptmeninggal.setSelectedItem(tbMati.getValueAt(tbMati.getSelectedRow(),14).toString());
            icd1.setText(tbMati.getValueAt(tbMati.getSelectedRow(),15).toString());
            icd2.setText(tbMati.getValueAt(tbMati.getSelectedRow(),16).toString());
            icd3.setText(tbMati.getValueAt(tbMati.getSelectedRow(),17).toString());
            icd4.setText(tbMati.getValueAt(tbMati.getSelectedRow(),18).toString());
            KdDokter.setText(tbMati.getValueAt(tbMati.getSelectedRow(),19).toString());
            NmDokter.setText(tbMati.getValueAt(tbMati.getSelectedRow(),20).toString());
        }
    }
    
    public void isCek(){
        autoNomor();
        BtnSimpan.setEnabled(akses.getpasien_meninggal());
        BtnHapus.setEnabled(akses.getpasien_meninggal());
        BtnPrint.setEnabled(akses.getpasien_meninggal());
    }
    
    private void autoNomor() {
       Valid.autoNomerSuratPenagihan("select ifnull(MAX(CONVERT(LEFT(no_surat,3),signed)),0) from pasien_mati where "
                + "tanggal like '%" + Valid.SetTgl(DTPTgl.getSelectedItem() + "").substring(0, 7) + "%' ", "/14/RSB.14/" + Sequel.cariIsi("SELECT toRoman (MONTH(NOW())) as tgl") + "/" + Valid.SetTgl(DTPTgl.getSelectedItem() + "").substring(0, 4), 3, TKtg1);
    }
    
    public void setNoRm(String norm,String nama) {
        TNoRM.setText(norm);  
        TPasien.setText(nama);
        
        ChkInput.setSelected(true);
        isForm();
        TCari.setText(norm);
    }
    
    public void setTglMasuk(String tgl_masuk){
        TKtg2.setText(tgl_masuk);
        
        ChkInput.setSelected(true);
        isForm();
        TCari.setText(tgl_masuk);
    }
    
    public void setJamMasuk(String jam_masuk){
        TKtg3.setText(jam_masuk);
        
        ChkInput.setSelected(true);
        isForm();
        TCari.setText(jam_masuk);
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,220));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
}
