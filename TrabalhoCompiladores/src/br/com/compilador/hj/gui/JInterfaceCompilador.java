/*
 * Created by JFormDesigner on Sat Mar 22 19:40:46 BRT 2014
 */

package br.com.compilador.hj.gui;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.*;

import br.com.compilador.hj.gals.Constants;
import br.com.compilador.hj.gals.LexicalError;
import br.com.compilador.hj.gals.Lexico;
import br.com.compilador.hj.gals.Token;
import br.com.compilador.hj.util.NumberedBorder;

import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;

/**
 * @author Heloisa Kopsch
 */
public class JInterfaceCompilador extends JFrame {
	
	NumberedBorder numberBorder = new NumberedBorder();
	
	public JInterfaceCompilador() {
		initComponents();		
		this.jEditor.setBorder(numberBorder);
		criarTeclaDeAtalho(jNovo, "control N");
		criarTeclaDeAtalho(jAbrir, "control A");
		criarTeclaDeAtalho(jSalvar, "control S");
		criarTeclaDeAtalho(jCopiar, "control C");
		criarTeclaDeAtalho(jColar, "control V");
		criarTeclaDeAtalho(jRecortar, "control X");
		criarTeclaDeAtalho(jCompilar, "F8");
		criarTeclaDeAtalho(jGerarCodigo, "F9");
		criarTeclaDeAtalho(jEquipe, "F1");
	}

	private void criarTeclaDeAtalho(final JButton button, String tecla) {
		button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(tecla), "evento");
		button.getActionMap().put("evento", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				button.doClick();
			}

		});
	}

	private void novoClicked(ActionEvent e) {
		this.jEditor.setText("");
		this.jAreaMensagens.setText("");
		this.jBarraStatus.setText("Não modificado");
	}

	private void abrirClicked(ActionEvent e) {
		final JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(getParent());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String path = fc.getSelectedFile().getAbsolutePath();
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						new FileInputStream(path), "UTF-8"));
				String linha = "";
				while (br.ready()) {
					linha = linha.concat(br.readLine() + "\n");
				}

				br.close();

				jEditor.setText(linha);
				jAreaMensagens.setText("");
				jBarraStatus.setText("Não modificado - " + path);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void salvarClicked(ActionEvent e) {
		if (jBarraStatus.getText().length() < 15) {
			final JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(getParent());
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				PrintWriter writer;
				try {
					writer = new PrintWriter(fc.getSelectedFile()
							.getAbsolutePath(), "UTF-8");

					String[] line = jEditor.getText().split("\n");
					for (String eachLine : line)
						writer.println(eachLine);

					writer.close();

					jAreaMensagens.setText("");
					jBarraStatus.setText("Não modificado - "
							+ fc.getSelectedFile().getAbsolutePath());
				} catch (FileNotFoundException | UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}

			}

		} else {
			PrintWriter writer;
			try {
				String barraStatus = jBarraStatus.getText();
				String path = barraStatus
						.substring(barraStatus.indexOf("-") + 2);

				writer = new PrintWriter(path, "UTF-8");
				String[] line = jEditor.getText().split("\n");
				for (String eachLine : line)
					writer.println(eachLine);

				writer.close();

				jAreaMensagens.setText("");
				jBarraStatus.setText("Não modificado - " + path);
			} catch (FileNotFoundException | UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}

		}
	}

	private void copiarClicked(ActionEvent e) {
		Clipboard clipboard = getToolkit().getSystemClipboard();
		String selectedText = jEditor.getSelectedText();
		if (selectedText != null) {
			StringSelection data = new StringSelection(selectedText);
			clipboard.setContents(data, data);
		}
	}

	private void colarClicked(ActionEvent e) {
		Clipboard clipboard = getToolkit().getSystemClipboard();
		Transferable clipData = clipboard.getContents(this);
		String pasteText;
		try {
			pasteText = (String) (clipData
					.getTransferData(DataFlavor.stringFlavor));
		} catch (Exception ex) {
			pasteText = ex.toString();
		}

		if (pasteText != null) {
			jEditor.replaceSelection(pasteText);
		}
	}

	private void recortarClicked(ActionEvent e) {
		Clipboard clipboard = getToolkit().getSystemClipboard();
		String selectedText = jEditor.getSelectedText();
		if (selectedText != null) {
			StringSelection data = new StringSelection(selectedText);
			clipboard.setContents(data, data);
			jEditor.replaceSelection("");
		}
	}

	private void compilarClicked(ActionEvent e) {
		if (jAreaMensagens.getText() == null || jAreaMensagens.getText() == "") {
			jAreaMensagens.setText("nenhum programa para compilar");
		} else {
			Lexico lexico = new Lexico();
			lexico.setInput(jEditor.getText());

			try {
				Token t = null;
				jAreaMensagens.setText("linha classe lexema" + "\n");
							
				while ((t = lexico.nextToken()) != null) {
					jAreaMensagens.setText(jAreaMensagens.getText()
							+ t.getPosition() + " " 
							+ t.getClasse() + "	"
							+ t.getLexeme() + "\n");
				}
				jAreaMensagens.setText(jAreaMensagens.getText() + "\n"
						+ "programa compilado com sucesso");
			} catch (LexicalError error) {
				jAreaMensagens.setText(error.getMessage() + " em "
						+ error.getPosition());
			}
		}
	}

	private void gerarCodigoClicked(ActionEvent e) {
		jAreaMensagens.setText(" geração de código ainda não foi implementada");
	}

	private void equipeClicked(ActionEvent e) {
		jAreaMensagens
				.setText(" Heloisa Kaestner Kopsch \n Jano Gilson Torres");
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Heloisa Kopsch
		jNovo = new JButton();
		jGerarCodigo = new JButton();
		jColar = new JButton();
		jCopiar = new JButton();
		jRecortar = new JButton();
		jEquipe = new JButton();
		jCompilar = new JButton();
		jAbrir = new JButton();
		jSalvar = new JButton();
		scrollPane1 = new JScrollPane();
		jEditor = new JTextArea();
		scrollPane2 = new JScrollPane();
		jAreaMensagens = new JTextArea();
		jBarraStatus = new JLabel();

		//======== this ========
		setTitle("Compilador");
		setMinimumSize(new Dimension(1200, 500));
		Container contentPane = getContentPane();
		contentPane.setLayout(new FormLayout(
			"9*(min:grow)",
			"fill:50dlu, fill:89dlu:grow, bottom:65dlu, bottom:default"));

		//---- jNovo ----
		jNovo.setText("novo [ctrl-n]");
		jNovo.setIcon(new ImageIcon(getClass().getResource("/compilador/new.png")));
		jNovo.setHorizontalTextPosition(SwingConstants.CENTER);
		jNovo.setVerticalTextPosition(SwingConstants.BOTTOM);
		jNovo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				novoClicked(e);
			}
		});
		contentPane.add(jNovo, CC.xy(1, 1));

		//---- jGerarCodigo ----
		jGerarCodigo.setText("gerar c\u00f3digo [F9]");
		jGerarCodigo.setIcon(new ImageIcon(getClass().getResource("/compilador/generateCode.png")));
		jGerarCodigo.setVerticalTextPosition(SwingConstants.BOTTOM);
		jGerarCodigo.setHorizontalTextPosition(SwingConstants.CENTER);
		jGerarCodigo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gerarCodigoClicked(e);
			}
		});
		contentPane.add(jGerarCodigo, CC.xy(8, 1));

		//---- jColar ----
		jColar.setText("colar [ctrl-v]");
		jColar.setIcon(new ImageIcon(getClass().getResource("/compilador/paste.png")));
		jColar.setHorizontalTextPosition(SwingConstants.CENTER);
		jColar.setVerticalTextPosition(SwingConstants.BOTTOM);
		jColar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				colarClicked(e);
			}
		});
		contentPane.add(jColar, CC.xy(5, 1));

		//---- jCopiar ----
		jCopiar.setText("copiar [ctrl-c]");
		jCopiar.setIcon(new ImageIcon(getClass().getResource("/compilador/copy.png")));
		jCopiar.setVerticalTextPosition(SwingConstants.BOTTOM);
		jCopiar.setHorizontalTextPosition(SwingConstants.CENTER);
		jCopiar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				copiarClicked(e);
			}
		});
		contentPane.add(jCopiar, CC.xy(4, 1));

		//---- jRecortar ----
		jRecortar.setText("recortar [ctrl-x]");
		jRecortar.setIcon(new ImageIcon(getClass().getResource("/compilador/cut.png")));
		jRecortar.setVerticalTextPosition(SwingConstants.BOTTOM);
		jRecortar.setHorizontalTextPosition(SwingConstants.CENTER);
		jRecortar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				recortarClicked(e);
			}
		});
		contentPane.add(jRecortar, CC.xy(6, 1));

		//---- jEquipe ----
		jEquipe.setText("equipe [F1]");
		jEquipe.setIcon(new ImageIcon(getClass().getResource("/compilador/team.png")));
		jEquipe.setHorizontalTextPosition(SwingConstants.CENTER);
		jEquipe.setVerticalTextPosition(SwingConstants.BOTTOM);
		jEquipe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				equipeClicked(e);
			}
		});
		contentPane.add(jEquipe, CC.xy(9, 1));

		//---- jCompilar ----
		jCompilar.setText("compilar [F8]");
		jCompilar.setIcon(new ImageIcon(getClass().getResource("/compilador/compile.png")));
		jCompilar.setHorizontalTextPosition(SwingConstants.CENTER);
		jCompilar.setVerticalTextPosition(SwingConstants.BOTTOM);
		jCompilar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				compilarClicked(e);
			}
		});
		contentPane.add(jCompilar, CC.xy(7, 1));

		//---- jAbrir ----
		jAbrir.setText("abril [ctrl-a]");
		jAbrir.setIcon(new ImageIcon(getClass().getResource("/compilador/open.png")));
		jAbrir.setVerticalTextPosition(SwingConstants.BOTTOM);
		jAbrir.setHorizontalTextPosition(SwingConstants.CENTER);
		jAbrir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				abrirClicked(e);
			}
		});
		contentPane.add(jAbrir, CC.xy(2, 1));

		//---- jSalvar ----
		jSalvar.setText("salvar [ctrl-s]");
		jSalvar.setIcon(new ImageIcon(getClass().getResource("/compilador/save.png")));
		jSalvar.setHorizontalTextPosition(SwingConstants.CENTER);
		jSalvar.setVerticalTextPosition(SwingConstants.BOTTOM);
		jSalvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				salvarClicked(e);
			}
		});
		contentPane.add(jSalvar, CC.xy(3, 1));

		//======== scrollPane1 ========
		{
			scrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollPane1.setMinimumSize(new Dimension(23, 150));
			scrollPane1.setPreferredSize(new Dimension(31, 150));
			scrollPane1.setViewportView(jEditor);
		}
		contentPane.add(scrollPane1, CC.xywh(1, 2, 9, 1));

		//======== scrollPane2 ========
		{
			scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollPane2.setMinimumSize(new Dimension(23, 100));
			scrollPane2.setPreferredSize(new Dimension(23, 100));
			scrollPane2.setAutoscrolls(true);

			//---- jAreaMensagens ----
			jAreaMensagens.setEditable(false);
			jAreaMensagens.setColumns(3);
			scrollPane2.setViewportView(jAreaMensagens);
		}
		contentPane.add(scrollPane2, CC.xywh(1, 3, 9, 1));

		//---- jBarraStatus ----
		jBarraStatus.setText("  N\u00e3o modificado");
		jBarraStatus.setFocusCycleRoot(true);
		jBarraStatus.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(jBarraStatus, CC.xywh(1, 4, 9, 1));
		pack();
		setLocationRelativeTo(getOwner());
		// //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Heloisa Kopsch
	private JButton jNovo;
	private JButton jGerarCodigo;
	private JButton jColar;
	private JButton jCopiar;
	private JButton jRecortar;
	private JButton jEquipe;
	private JButton jCompilar;
	private JButton jAbrir;
	private JButton jSalvar;
	private JScrollPane scrollPane1;
	private JTextArea jEditor;
	private JScrollPane scrollPane2;
	private JTextArea jAreaMensagens;
	private JLabel jBarraStatus;
	// JFormDesigner - End of variables declaration //GEN-END:variables
}
