package com.italcred.devel.UI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.italcred.devel.DateComparator;
import com.italcred.devel.JSONHelper;
import com.italcred.devel.JSONHelper.Documento;
import com.italcred.devel.QueryGen;

public class Main extends JFrame implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtDocumento;
	private JTextField txtCuenta;
	private JTextArea txaInmediato;
	private JTable table;
	private HashMap<String, Object> registros_solicitu;
	private JTable table_1;
	private JCheckBox chkRestaurar;
	private List<Documento> documentos_his = null;
	private JCheckBox chkEliminarSoloQscam;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		//Setteo de look and feel por defecto de la plataforma.
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException
				| ClassNotFoundException ex){
			ex.printStackTrace(System.err);
		}
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("E:\\EclipseWorkspace\\BorradoLogicoSolicitudes\\src\\resources\\linux_128_px.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 689, 413);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 5, 476, 370);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBounds(10, 11, 456, 66);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Documento");
		lblNewLabel.setBounds(10, 8, 60, 14);
		panel_1.add(lblNewLabel);
		
		txtDocumento = new JTextField();
		txtDocumento.setBounds(80, 5, 86, 20);
		panel_1.add(txtDocumento);
		txtDocumento.setColumns(10);
		
		JLabel lblNroCuenta = new JLabel("Nro. Cuenta");
		lblNroCuenta.setBounds(172, 8, 81, 14);
		panel_1.add(lblNroCuenta);
		
		txtCuenta = new JTextField();
		txtCuenta.setColumns(10);
		txtCuenta.setBounds(263, 5, 86, 20);
		panel_1.add(txtCuenta);
		
		JButton btnNewButton = new JButton("Buscar...");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadTable();
				mensajesDuranteBusqueda();
			}
		});

		btnNewButton.setBounds(359, 4, 87, 23);
		panel_1.add(btnNewButton);
		
		chkRestaurar = new JCheckBox("Restaurar");
		chkRestaurar.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				cambiarEstadoDocumento();
			}
		});
		chkRestaurar.setBounds(6, 36, 73, 23);
		panel_1.add(chkRestaurar);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.LIGHT_GRAY);
		panel_2.setBounds(10, 94, 456, 110);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 436, 87);
		panel_2.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Cod. Movimiento", "Nro. Solicitud", "Nro. Socio"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(95);
		table.getColumnModel().getColumn(0).setMinWidth(18);
		
		txaInmediato = new JTextArea();
		txaInmediato.setEditable(false);
		txaInmediato.setBounds(10, 249, 456, 110);
		panel.add(txaInmediato);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmarAcciones();
			}
		});
		btnConfirmar.setBounds(361, 215, 105, 23);
		panel.add(btnConfirmar);
		
		chkEliminarSoloQscam = new JCheckBox("Eliminar solo QSCAM");
		chkEliminarSoloQscam.setBounds(10, 219, 123, 23);
		panel.add(chkEliminarSoloQscam);
			
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(486, 5, 187, 370);
		contentPane.add(panel_4);
		panel_4.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 96, 167, 257);
		panel_4.add(scrollPane_1);
		
		table_1 = new JTable();
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				asignarValorADocumento();
			}
		});
		table_1.setCellSelectionEnabled(true);
		table_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Documento"
			}
		));
		scrollPane_1.setViewportView(table_1);
		
		JTextArea txtrLaGrillaInferior = new JTextArea();
		txtrLaGrillaInferior.setEditable(false);
		txtrLaGrillaInferior.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		txtrLaGrillaInferior.setText("La grilla inferior muestra\r\nel hist\u00F3rico de documentos\r\nmodificados, seleccione uno\r\npara restaurar.");
		txtrLaGrillaInferior.setBounds(10, 11, 167, 74);
		panel_4.add(txtrLaGrillaInferior);
		
		//Carga de histórico
		loadTableHis();
	}
	
	private Vector<Object> getColumnNames(){
		Vector<Object> columnNames = new Vector<>();
		columnNames.add("Cod. Movimiento");
		columnNames.add("Nro. Solicitud");
		columnNames.add("Nro. Socio");
		return columnNames;
	}
	
	private Vector<Object> getHisColumnNames(){
		Vector<Object> columnNames = new Vector<>();
		columnNames.add("Documento");
		columnNames.add("Fecha(Delete)");
		return columnNames;
	}	
	
	
	private void mensajesDuranteBusqueda(){
		String documento = txtDocumento.getText().trim();
		String mensaje = "Cantidad de registros de QSCAM: " + QueryGen.verificarRegistrosQSCAM(documento);
		txaInmediato.setText(mensaje);
	}
	
	private void loadTable(){
		Vector<Object> row = new Vector<>();
		Vector<Vector<Object>> data = new Vector<>();
		String documento = obtenerVariacionDocumento();
		data.clear();
		if(txtDocumento.getText().compareTo("")!= 0){
			registros_solicitu = QueryGen.obtencionCataClil(documento);
			row.add(registros_solicitu.get("MovCod"));
			row.add(registros_solicitu.get("SolNro"));
			row.add(registros_solicitu.get("STNroSoc"));
			row.add(false);
			data.add(row);
		}
		DefaultTableModel tmodel = new DefaultTableModel(data, getColumnNames());
		table.setModel(tmodel);
	}
	
	//Se agrega el prefijo 9 al documento para buscarlo de esta manera y que quede invisible al usuario.
	private String obtenerVariacionDocumento(){
		String documento = "";
		if (chkRestaurar.isSelected() && txtDocumento.getText().compareTo("") != 0){
			documento = "9" + txtDocumento.getText();
		}else{
			documento = txtDocumento.getText();
		}
		return documento;
	}
	
	private void loadTableHis(){
		Vector<Object> row = new Vector<>();
		Vector<Vector<Object>> data = new Vector<>();
		documentos_his = new ArrayList<>();
		documentos_his = JSONHelper.readJSON();
		Collections.sort(documentos_his, new DateComparator());
		for(int index = 0; index < documentos_his.size(); index++){
			row = new Vector<>();
			row.add(documentos_his.get(index).getDocumento());
			row.add(documentos_his.get(index).getFecha());
			data.add(row);
		}
		
		DefaultTableModel tmodel = new DefaultTableModel(data, getHisColumnNames());
		table_1.setModel(tmodel);
	}
	
	private void cambiarEstadoDocumento(){
		if (chkRestaurar.isSelected()){
			txtDocumento.setEnabled(false);
		}else{
			txtDocumento.setEnabled(true);			
		}
	}
	
	private void confirmarAcciones(){
		Thread hilo = new Thread(this);
		hilo.start();
		
	}
	
	private void asignarValorADocumento(){
		txtDocumento.setText((String)table_1.getModel().getValueAt(table_1.getSelectedRow(), 0));
	}

	@Override
	public void run() {
		String documento = txtDocumento.getText();
		JSONHelper helper = new JSONHelper();
	
		txaInmediato.setText("Comienzo de modificaciones...");
		if (!chkRestaurar.isSelected())
			txaInmediato.setText(txaInmediato.getText() + "\nEliminando registros:");
		else
			txaInmediato.setText(txaInmediato.getText() + "\nRestaurando registros:");
		
		if(chkEliminarSoloQscam.isSelected()){
			txaInmediato.setText(txaInmediato.getText() + "\nModificando QSCAM...");
			QueryGen.modificaQSCAM(documento, !chkRestaurar.isSelected());
		}else{
		
			txaInmediato.setText(txaInmediato.getText() + "\nModificando QSCAM...");
			QueryGen.modificaQSCAM(documento, !chkRestaurar.isSelected());
			
			txaInmediato.setText(txaInmediato.getText() + "\nModificando CATACLIL...");		
			QueryGen.modificaCATACLIL((Integer)registros_solicitu.get("SolNro"), (Integer)registros_solicitu.get("STNroSoc"), 
					documento, !chkRestaurar.isSelected());
			
			txaInmediato.setText(txaInmediato.getText() + "\nModificando SOLICITU...");		
			QueryGen.modificaSOLICITU((Integer)registros_solicitu.get("SolNro"), (Integer)registros_solicitu.get("STNroSoc"), 
					documento, !chkRestaurar.isSelected());
		}

		if (!chkRestaurar.isSelected()){
			Calendar calendar = Calendar.getInstance(); 
			Date date = calendar.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
			
			documentos_his.add(helper.new Documento(documento, sdf.format(date)));
			JSONHelper.writeJSON(documentos_his);
			loadTableHis();
		}
		loadTable();
		registros_solicitu.clear();
		txaInmediato.setText(txaInmediato.getText() + "\nFinalización de proceso!");
		
	}
}
