package andreclinio.javawidgets.jdegradee.renderers;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import andreclinio.javawidgets.jdegradee.JDegradee;

/**
 * Interface de definifi��o do objeto que define o objeto a ser desenhado dento
 * de um elmento de degrade�.
 *
 * @author Andr� Clinio
 */
public interface JDegradeeRenderer {

  /**
   * Consulta o texto a ser desenhado dentro de um elmento.
   *
   * @param jDegradee o widget
   * @param g2d o graphics a ser utilizado.
   * @param rect a �rea relativa ao item
   * @param index o �ndice do elemento
   *
   */
  public void render(final JDegradee jDegradee, final Graphics2D g2d, final Rectangle2D rect, final int index);

}
