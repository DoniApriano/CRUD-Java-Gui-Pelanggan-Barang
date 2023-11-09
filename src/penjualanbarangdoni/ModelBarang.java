/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package penjualanbarangdoni;

import java.io.File;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class ModelBarang {
    
    JasperReport jasperReport;
    JasperDesign jasperDesign;
    JasperPrint jasperPrint;
    
    Map<String, Object> pm = new HashMap<String,Object>();

    protected String kode, nama, satuan;
    protected int harga, stok, flag;

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
    
    public void cetak() {
        try {
            Conn conn = new Conn();
            Connection connection = conn.openConnection();
            File file = new File("src/penjualanbarangdoni/report_barang.jrxml");
            jasperDesign = JRXmlLoader.load(file);
            pm.clear();
            
            jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jasperPrint = JasperFillManager.fillReport(jasperReport, pm, connection);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void simpan() {
        try {
            Conn conn = new Conn();
            Connection connection = conn.openConnection();
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO barang VALUES("
                    + "'" + getKode() + "'"
                    + ",'" + getNama() + "'"
                    + ",'" + getSatuan() + "'"
                    + ",'" + getHarga() + "',"
                    + "'" + getStok() + "')";
            statement.execute(sql);
            setFlag(1);
            statement.close();
            connection.close();
            JOptionPane.showMessageDialog(
                    null,
                    "Data Berhasil Ditambkan",
                    "SIMPAN",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Data Gagal Ditambkan",
                    "GAGAL",
                    JOptionPane.WARNING_MESSAGE);
            System.out.println(e.getMessage());
        }
    }

    public void hapus() {
        try {
            Conn conn = new Conn();
            Connection connection = conn.openConnection();
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM barang WHERE kode_brg = '" + getKode() + "'";
            statement.executeUpdate(sql);
            setFlag(3);
            statement.close();
            connection.close();
            JOptionPane.showMessageDialog(
                    null,
                    "Data Berhasil Di Hapus",
                    "HAPUS",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Data Gagal Di Hapus",
                    "GAGAL",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    public void tampil() {
        try {
            Conn conn = new Conn();
            Connection connection = conn.openConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM barang WHERE kode_brg = '" + getKode() + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                setFlag(4);
                setKode(resultSet.getString("kode_brg"));
                setNama(resultSet.getString("nama_brg"));
                setSatuan(resultSet.getString("satuan"));
                setHarga(resultSet.getInt("harga_brg"));
                setStok(resultSet.getInt("stok"));
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public void autoCode() {
        try {
            int hit = 0;
            Conn conn = new Conn();
            Connection connection = conn.openConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT COUNT(kode_brg) FROM barang";
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                if (Integer.parseInt(resultSet.getString(1)) == 0) {
                    setKode("B001");
                    statement.close();
                    resultSet.close();
                } else {
                    sql = "SELECT MAX(mid(kode_brg,2,4)) FROM barang";
                    resultSet = statement.executeQuery(sql);
                    resultSet.next();
                    hit = (Integer.parseInt(resultSet.getString(1))) + 1;
                    if (hit < 10) {
                        setKode("B00" + hit);
                    } else if (hit < 100) {
                        setKode("B0" + hit);
                    } else if (hit < 1000) {
                        setKode("B" + hit);
                    }
                    statement.close();
                    resultSet.close();
                }
            }
            System.out.println("kode" + hit);
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public void ubah() {
        try {
            Conn conn = new Conn();
            Connection connection = conn.openConnection();
            Statement statement = connection.createStatement();
            String sql = "UPDATE barang SET "
                    + "nama_brg = '" + getKode() + "', "
                    + "satuan = '" + getSatuan() + "',"
                    + "harga_brg = '" + getHarga() + "',"
                    + "stok = '" + getStok() + "'"
                    + "WHERE kode_brg = '" + getKode() + "'";
            statement.executeUpdate(sql);
            setFlag(3);
            statement.close();
            connection.close();
            JOptionPane.showMessageDialog(
                    null,
                    "Data Berhasil Di Ubah",
                    "HAPUS",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Data Gagal Di Hapus",
                    "GAGAL",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}
