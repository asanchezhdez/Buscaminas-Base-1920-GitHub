import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que implementa el listener de los botones del Buscaminas.
 * De alguna manera tendrá que poder acceder a la ventana principal.
 * Se puede lograr pasando en el constructor la referencia a la ventana.
 * Recuerda que desde la ventana, se puede acceder a la variable de tipo ControlJuego
 * @author jesusredondogarcia
 **
 */
public class ActionBoton implements ActionListener{
	VentanaPrincipal ventana;
	int i;
	int j;
	

	public ActionBoton(VentanaPrincipal ventana,int i,int j) {
		this.ventana = ventana;
		this.i=i;
		this.j=j;
	}
	
	/**
	 *Acción que ocurrirá cuando pulsamos uno de los botones.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(ventana.juego.abrirCasilla(i, j)) {
			ventana.mostrarNumMinasAlrededor(i, i);
			if(ventana.juego.getMinasAlrededor(i, j)==0) {
				ventana.abrirAlrededor(i, j);
			}
			ventana.actualizarPuntuacion();
			//Comprobamos si se ha llegado al l�mite de puntuacion
			if(ventana.getJuego().getPuntuacion()==(ventana.getJuego().LADO_TABLERO)*(ventana.getJuego().LADO_TABLERO)-ventana.getJuego().MINAS_INICIALES) {
				ventana.panelImagen.setBackground(Color.GREEN);
				ventana.mostrarFinJuego(false);
				ventana.panelImagen.setBackground(Color.WHITE);
			}
		}else {
			ventana.panelImagen.setBackground(Color.RED);
			ventana.mostrarFinJuego(true);
			ventana.panelImagen.setBackground(Color.WHITE);
		}
		
	}

}
