package view;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Toolkit;
import javax.swing.JTextField;

@SuppressWarnings("unused")
public class Relatorios extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Relatorios dialog = new Relatorios();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Relatorios() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Relatorios.class.getResource("/img/carwash64.png")));
		setTitle("Relatórios");
		setModal(true);
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(255, 255, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JButton btnClientes = new JButton("");
		btnClientes.setToolTipText("Relatório de Cliente");
		btnClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClientes.setIcon(new ImageIcon(Relatorios.class.getResource("/img/relatorioCli.png")));
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioClientes();
			}
		});
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon(Relatorios.class.getResource("/img/Money.png")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustoPatrimonio();
			}
		});
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VendaPatrimonio();
			}
		});
		btnNewButton_1.setIcon(new ImageIcon(Relatorios.class.getResource("/img/Carrinho.png")));
		btnNewButton_1.setBounds(494, 502, 48, 48);
		contentPanel.add(btnNewButton_1);
		
		JLabel lblNewLabel_1 = new JLabel("Venda Patrimônio:");
		lblNewLabel_1.setBounds(383, 520, 110, 14);
		contentPanel.add(lblNewLabel_1);
		btnNewButton.setBounds(284, 502, 48, 48);
		contentPanel.add(btnNewButton);
		
		JLabel lbl = new JLabel("Custo Patrimônio:");
		lbl.setBounds(171, 520, 110, 14);
		contentPanel.add(lbl);
		btnClientes.setBounds(105, 80, 128, 128);
		contentPanel.add(btnClientes);
		
		JButton btnServicos = new JButton("");
		btnServicos.setToolTipText("Relatório de serviço");
		btnServicos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnServicos.setIcon(new ImageIcon(Relatorios.class.getResource("/img/Relatorio11.png")));
		btnServicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioServicos();
			}
		});
		btnServicos.setBounds(507, 80, 128, 128);
		contentPanel.add(btnServicos);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBackground(new Color(0, 128, 255));
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBounds(0, 476, 784, 85);
		contentPanel.add(lblNewLabel);
		
		JButton btnRepor = new JButton("");
		btnRepor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRepor.setIcon(new ImageIcon(Relatorios.class.getResource("/img/Repor.png")));
		btnRepor.setToolTipText("Repor");
		btnRepor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Repor();
			}
		});
		btnRepor.setBounds(294, 256, 128, 128);
		contentPanel.add(btnRepor);
	}
	
	
	private void relatorioClientes() {
		
		Document document = new Document();
		
		try {
			
			PdfWriter.getInstance(document, new FileOutputStream("clientes.pdf"));
			
			document.open();
			
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			
			document.add(new Paragraph("Clientes:"));
			document.add(new Paragraph(" ")); 
			
			String readClientes = "select nome,fone,email from clientes order by nome";
			try {
				
				con = dao.conectar();
				
				pst = con.prepareStatement(readClientes);
				
				rs = pst.executeQuery();
				
				PdfPTable tabela = new PdfPTable(3); 
				
				PdfPCell col1 = new PdfPCell(new Paragraph("Cliente"));
				PdfPCell col2 = new PdfPCell(new Paragraph("Fone"));
				PdfPCell col3 = new PdfPCell(new Paragraph("Email"));
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				
				while (rs.next()) {
					
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
				}				
				
				document.add(tabela);
				
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		document.close();
		
		try {
			Desktop.getDesktop().open(new File("clientes.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}		
	}
	private void relatorioServicos() {
		
		Document document = new Document();
		
		document.setPageSize(PageSize.A4.rotate());
		
		try {
			
			PdfWriter.getInstance(document, new FileOutputStream("servicos.pdf"));
			
			document.open();
			
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			
			document.add(new Paragraph("Servicos:"));
			document.add(new Paragraph("")); 
			String readServicos = "select os,dataOS,brinquedo,defeito,valor,nome from servicos inner join clientes\r\n" + "on servicos.id = clientes.idcli;";
			try {
				
				con = dao.conectar();
				
				pst = con.prepareStatement(readServicos);
				
				rs = pst.executeQuery();
				
				PdfPTable tabela = new PdfPTable(6); 
				PdfPCell col1 = new PdfPCell(new Paragraph("OS"));
				PdfPCell col2 = new PdfPCell(new Paragraph("Data"));
				PdfPCell col3 = new PdfPCell(new Paragraph("Brinquedo"));
				PdfPCell col4 = new PdfPCell(new Paragraph("Defeito"));
				PdfPCell col5 = new PdfPCell(new Paragraph("Valor"));
				PdfPCell col6 = new PdfPCell(new Paragraph("Nome"));
				
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				tabela.addCell(col4);
				tabela.addCell(col5);
				tabela.addCell(col6);
				
				while (rs.next()) {
					
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(5));
					tabela.addCell(rs.getString(6));
				}				
				
				document.add(tabela);
				
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		document.close();
		
		try {
			Desktop.getDesktop().open(new File("produtos.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}		
	}
	private void Repor() {
		
		Document document = new Document();
		
		document.setPageSize(PageSize.A4.rotate());
		
		try {
			
			PdfWriter.getInstance(document, new FileOutputStream("produtos.pdf"));
			
			document.open();
			
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			
			document.add(new Paragraph("Produtos:"));
			document.add(new Paragraph("")); 
			String readProdutos = "select codigo as código,produto,date_format(dataval, '%d/%m/%Y') as validade,\n"
					+ "date_format(dataent, '%d/%m/%Y') as entrada,\n"
					+ "estoque, estoquemin as estoque_mínimo \n"
					+ "from produtos where dataval < dataent";
			try {
				
				con = dao.conectar();
				
				pst = con.prepareStatement(readProdutos);
				
				rs = pst.executeQuery();
				
				PdfPTable tabela = new PdfPTable(6); 
				PdfPCell col1 = new PdfPCell(new Paragraph("OS"));
				PdfPCell col2 = new PdfPCell(new Paragraph("Produto"));
				PdfPCell col3 = new PdfPCell(new Paragraph("Validade"));
				PdfPCell col4 = new PdfPCell(new Paragraph("Entrada"));
				PdfPCell col5 = new PdfPCell(new Paragraph("Estoque"));
				PdfPCell col6 = new PdfPCell(new Paragraph("Estoque min"));
				
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				tabela.addCell(col4);
				tabela.addCell(col5);
				tabela.addCell(col6);	
				
				while (rs.next()) {
					
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(5));
					tabela.addCell(rs.getString(6));
				}				
				
				document.add(tabela);
				
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		document.close();
		
		try {
			Desktop.getDesktop().open(new File("produtos.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}		
	}
	
	private void CustoPatrimonio() {
		
		Document document = new Document();
		
		document.setPageSize(PageSize.A4.rotate());
		
		try {
			
			PdfWriter.getInstance(document, new FileOutputStream("patrimonio.pdf"));
			
			document.open();
			
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			
			document.add(new Paragraph("Patrímônio:"));
			document.add(new Paragraph(""));
			String readPatrimonio = "select sum(custo * estoque) as Total from produtos;";
			try {
				
				con = dao.conectar();
				
				rs = pst.executeQuery();
				
				PdfPTable tabela = new PdfPTable(1); 
				PdfPCell col1 = new PdfPCell(new Paragraph("Patrímônio"));
			
				
				tabela.addCell(col1);
			
				
				while (rs.next()) {
				
					tabela.addCell(rs.getString(1));
					
				}				
				
				document.add(tabela);
				
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		document.close();
		
		try {
			Desktop.getDesktop().open(new File("patrimonio.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}		
	}
	
	private void VendaPatrimonio() {
		
		Document document = new Document();
		
		document.setPageSize(PageSize.A4.rotate());
		
		try {
			
			PdfWriter.getInstance(document, new FileOutputStream("patrimonio.pdf"));
			
			document.open();
			
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			
			document.add(new Paragraph("Patrímônio:"));
			document.add(new Paragraph("")); 
			String readPatrimonio = "select sum((custo +(custo * lucro)/100) * estoque) as total from produtos;";
			try {
				
				con = dao.conectar();
				
				pst = con.prepareStatement(readPatrimonio);
				
				rs = pst.executeQuery();
				
				PdfPTable tabela = new PdfPTable(1); 
				PdfPCell col1 = new PdfPCell(new Paragraph("Patrímônio"));
			
				
				tabela.addCell(col1);
			
				
				while (rs.next()) {
					
					tabela.addCell(rs.getString(1));
					
				}				
				
				document.add(tabela);
				
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		document.close();
		
		try {
			Desktop.getDesktop().open(new File("patrimonio.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}		
	}
}