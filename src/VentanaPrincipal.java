import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


/**
 * <p> Esta clase inicializara los elementos de la interfaz e inicializara los listeners</p>
 * 
 * @author Alvaro Sanchez Hernandez
 * {@link #inicializar()}
 * @author Alvaro Sanchez Hernandez
 * @version 1.3
 * @since 26/10/2019
 * @see #juego
 * {@code inicializarComponentes();	
		inicializarListeners();		
		ventana.setVisible(true);}
 */
public class VentanaPrincipal {

	//La ventana principal, en este caso, guarda todos los componentes:
	JFrame ventana;
	JPanel panelImagen;
	JPanel panelEmpezar;
	JPanel panelPuntuacion;
	JPanel panelJuego;
	
	//Todos los botones se meten en un panel independiente.
	//Hacemos esto para que podamos cambiar después los componentes por otros
	JPanel [][] panelesJuego;
	JButton [][] botonesJuego;
	
	//Correspondencia de colores para las minas:
	Color correspondenciaColores [] = {Color.BLACK, Color.CYAN, Color.GREEN, Color.ORANGE, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED};
	
	JButton botonEmpezar;
	JTextField pantallaPuntuacion;
	
	
	//LA VENTANA GUARDA UN CONTROL DE JUEGO:
	ControlJuego juego;
	
	
	//Constructor, marca el tamaño y el cierre del frame
	public VentanaPrincipal() {
		ventana = new JFrame();
		ventana.setBounds(100, 100, 700, 500);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		juego = new ControlJuego();
	}
	
	//Inicializa todos los componentes del frame
	public void inicializarComponentes(){
		
		//Definimos el layout:
		ventana.setLayout(new GridBagLayout());
		
		//Inicializamos componentes
		panelImagen = new JPanel();
		panelEmpezar = new JPanel();
		panelEmpezar.setLayout(new GridLayout(1,1));
		panelPuntuacion = new JPanel();
		panelPuntuacion.setLayout(new GridLayout(1,1));
		panelJuego = new JPanel();
		panelJuego.setLayout(new GridLayout(10,10));
		
		
		botonEmpezar = new JButton("Go!");
		pantallaPuntuacion = new JTextField("0");
		pantallaPuntuacion.setEditable(false);
		pantallaPuntuacion.setHorizontalAlignment(SwingConstants.CENTER);
		
		//Bordes y colores:
		panelImagen.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		panelEmpezar.setBorder(BorderFactory.createTitledBorder("Empezar"));
		panelPuntuacion.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		panelJuego.setBorder(BorderFactory.createTitledBorder("Juego"));
		
			
		//Colocamos los componentes:
		//AZUL
		GridBagConstraints settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelImagen, settings);
		//VERDE
		settings = new GridBagConstraints();
		settings.gridx = 1;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelEmpezar, settings);
		//AMARILLO
		settings = new GridBagConstraints();
		settings.gridx = 2;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelPuntuacion, settings);
		//ROJO
		settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 1;
		settings.weightx = 1;
		settings.weighty = 10;
		settings.gridwidth = 3;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelJuego, settings);
		
		//Paneles
		panelesJuego = new JPanel[10][10];
		for (int i = 0; i < panelesJuego.length; i++) {
			for (int j = 0; j < panelesJuego[i].length; j++) {
				panelesJuego[i][j] = new JPanel();
				panelesJuego[i][j].setLayout(new GridLayout(1,1));
				panelJuego.add(panelesJuego[i][j]);
			}
		}
		
		//Botones
		botonesJuego = new JButton[10][10];
		for (int i = 0; i < botonesJuego.length; i++) {
			for (int j = 0; j < botonesJuego[i].length; j++) {
				botonesJuego[i][j] = new JButton("-");
				panelesJuego[i][j].add(botonesJuego[i][j]);
			}
		}
		
		//BotónEmpezar:
		panelEmpezar.add(botonEmpezar);
		panelPuntuacion.add(pantallaPuntuacion);
		
	}
	
	/**
	 * Método que inicializa todos los lísteners que necesita inicialmente el programa
	 */
	public void inicializarListeners(){
		botonEmpezar.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				pantallaPuntuacion.setText("0");
				for (int i = 0; i < panelesJuego.length; i++) {
					for (int j = 0; j < panelesJuego.length; j++) {
						panelesJuego[i][j].removeAll();
						panelesJuego[i][j].add(botonesJuego[i][j]);
						botonesJuego[i][j].setEnabled(true);
					}
				}
				refrescarPantalla();
				juego.inicializarPartida();
			}
		});
		for (int i = 0; i < botonesJuego.length; i++) {
			for (int j = 0; j < botonesJuego.length; j++) {
				botonesJuego[i][j].addActionListener(new ActionBoton(this,i,j));
			}
		}
	}
	
	
	/**
	 * Pinta en la pantalla el número de minas que hay alrededor de la celda
	 * Saca el botón que haya en la celda determinada y añade un JLabel centrado y no editable con el número de minas alrededor.
	 * Se pinta el color del texto según la siguiente correspondecia (consultar la variable correspondeciaColor):
	 * - 0 : negro
	 * - 1 : cyan
	 * - 2 : verde
	 * - 3 : naranja
	 * - 4 ó más : rojo 
	 * @param i: posición vertical de la celda.
	 * @param j: posición horizontal de la celda.
	 */
	public void mostrarNumMinasAlrededor(int i , int j) {
		int num=juego.getMinasAlrededor(i, j);
		panelesJuego[i][j].removeAll();
		JLabel aux= new JLabel();
		aux.setText(Integer.toString(num));
		aux.setForeground(correspondenciaColores[num]);
		panelesJuego[i][j].add(aux);
		refrescarPantalla();
	}
	
	
	/**
	 * Muestra una ventana que indica el fin del juego
	 * @param porExplosion : Un booleano que indica si es final del juego porque ha explotado una mina (true) o bien porque hemos desactivado todas (false) 
	 * @post : Todos los botones se desactivan excepto el de volver a iniciar el juego.
	 */
	public void mostrarFinJuego(boolean porExplosion) {
		if(porExplosion) {
			new JOptionPane();
			JOptionPane.showMessageDialog(null, "Has perdido la partida por pinchar una mina", "Pantalla Fin", 1);
		}else {
			new JOptionPane();
			JOptionPane.showMessageDialog(null, "HA GANADO LA PARTIDA!!!!!", "Pantalla Fin", 1);
		}
		//Bucle para descativar todos los botones.
		for (int i = 0; i < botonesJuego.length; i++) {
			for (int j = 0; j < botonesJuego.length; j++) {
				botonesJuego[i][j].setEnabled(false);
			}
		}
		refrescarPantalla();
	}

	/**
	 * Método que muestra la puntuación por pantalla.
	 */
	public void actualizarPuntuacion() {
		pantallaPuntuacion.setText(Integer.toString(juego.getPuntuacion()));
	}
	
	/**
	 * Método para refrescar la pantalla
	 */
	public void refrescarPantalla(){
		ventana.revalidate(); 
		ventana.repaint();
	}

	/**
	 * Método que devuelve el control del juego de una ventana
	 * @return un ControlJuego con el control del juego de la ventana
	 */
	public ControlJuego getJuego() {
		return juego;
	}

	/**
	 * Método para inicializar el programa
	 */
	public void inicializar(){
		//IMPORTANTE, PRIMERO HACEMOS LA VENTANA VISIBLE Y LUEGO INICIALIZAMOS LOS COMPONENTES.
		inicializarComponentes();	
		inicializarListeners();		
		ventana.setVisible(true);
	}
	/**
	 * 
	 * @param posI: Indica la fila del tablero de la casilla.
	 * @param posJ: Indica la columna del tablero de la casilla.
	 * M�todo que recorre las casillas del alrededor de las posiciones que nos llegan como par�metros.
	 * Controla que no estemos en los l�mites del tablero.
	 */
	public void abrirAlrededor(int posI,int posJ) {
		int i_Inicio = posI-1;
		int i_Final = posI+1;
		int j_Inicio=posJ-1;
		int j_Final=posJ+1;
		
		if(i_Inicio<0) {
			i_Inicio=0;
		}
		if(j_Inicio<0) {
			j_Inicio=0;
		}
		if(i_Final>9) {
			i_Final=9;
		}
		if(j_Final>9) {
			j_Final=9;
		}
		try {
			panelImagen.setBackground(Color.GREEN);
			Thread.sleep(500);
		}catch(InterruptedException ie) {
			System.out.println(ie.getMessage());
			ie.printStackTrace();
		}
		panelImagen.setBackground(Color.WHITE);
		int j_InicioAux=j_Inicio;//Variable para controlar que el bucle no se salga
		while(i_Inicio<=i_Final) {
			j_Inicio=j_InicioAux;
			while(j_Inicio<=j_Final) {
				if(panelesJuego[i_Inicio][j_Inicio].getComponent(0).getClass() == JButton.class) {
					botonesJuego[i_Inicio][j_Inicio].doClick();
					
				}
			}
		}
	}


}
