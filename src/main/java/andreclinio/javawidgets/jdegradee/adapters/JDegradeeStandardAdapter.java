package andreclinio.javawidgets.jdegradee.adapters;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.AbstractAction;
import javax.swing.JColorChooser;
import javax.swing.JPopupMenu;

import andreclinio.javawidgets.jdegradee.JDegradee;

/**
 * Tratador padr�o de mouse.
 *
 * @author Andr� Clinio
 */
public class JDegradeeStandardAdapter extends JDegradeeAdapter {

  /**
   * Texto da a��o de editar marca
   */
  final private String editionString;

  /**
   * Texto da a��o de apagar marca
   */
  final private String deletionString;

  /**
   * Escolha de uma cor
   *
   * @param jDegradee o widget
   * @param color a cor antiga j� selecionada
   *
   * @return uma cor (ou <code>null</code> em caso de desist�ncia)
   */
  final private static Color chooseColor(final JDegradee jDegradee, final Color color) {
    return JColorChooser.showDialog(jDegradee, "", color);
  }

  /**
   * Indica se existe uma marca em um evento de mouse.
   *
   * @param jDegradee o widget
   * @param event evento Java
   * @return o �ndice v�lido ou -1
   */
  final protected static int getMarkIndexOnEvent(final JDegradee jDegradee, final MouseEvent event) {
    final int numItems = jDegradee.getNumItems();
    for (int idx = 0; idx < numItems; idx++) {
      if (jDegradee.hasMarkColor(idx)) {
        final Rectangle2D rect = jDegradee.getMarkBounds(idx);
        final double x = event.getX();
        final double y = event.getY();
        if (rect.contains(x, y)) {
          return idx;
        }
      }
    }
    return -1;
  }

  /**
   * Constru��o de um menu popup.
   *
   * @param jDegradee o widget
   * @param index o �ndice
   * @return o menu
   */
  final private JPopupMenu buildMarkMenu(final JDegradee jDegradee, final int index) {
    final JPopupMenu menu = new JPopupMenu();

    menu.add(new AbstractAction(deletionString) {
      @Override
      public void actionPerformed(ActionEvent ae) {
        jDegradee.setMarkColorIndex(index, null);
      }
    });

    menu.add(new AbstractAction(editionString) {
      @Override
      public void actionPerformed(ActionEvent ae) {
        final Color oldColor = jDegradee.getMarkColor(index);
        final Color newColor = chooseColor(jDegradee, oldColor);
        if (newColor != null) {
          jDegradee.setMarkColorIndex(index, newColor);
        }
      }
    });

    return menu;
  }

  /**
   * M�todo interno para tantar acinonar o menu de marcas.
   *
   * @param jDegradee o widget
   * @param event o evnto Java
   * @param index o �ndice
   */
  final private void tryMarkMenu(final JDegradee jDegradee, final MouseEvent event, final int index) {
    if (event.getButton() != MouseEvent.BUTTON3) {
      return;
    }

    final double x = event.getX();
    final double y = event.getY();
    if (event.isPopupTrigger()) {
      final JPopupMenu menu = buildMarkMenu(jDegradee, index);
      menu.show(jDegradee, (int) x, (int) y);
    }
  }

  /**
   * @param jDegradee o widget
   * @param index
   * @param color
   * @param event
   */
  final private void tryMarkCreation(final JDegradee jDegradee, final int index, final Color color,
    final MouseEvent event) {
    if (getMarkIndexOnEvent(jDegradee, event) >= 0) {
      return;
    }
    if (event.getClickCount() != 2) {
      return;
    }
    final Color newColor = chooseColor(jDegradee, color);
    if (newColor != null) {
      jDegradee.setMarkColorIndex(index, newColor);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  final public void mouseClicked(final JDegradee jDegradee, final int index, final Color color,
    final MouseEvent event) {
    final int button = event.getButton();
    if (button == MouseEvent.BUTTON1) {
      tryMarkCreation(jDegradee, index, color, event);
    }
    else {
      tryMarkMenu(jDegradee, event, index);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  final public void mousePressed(final JDegradee jDegradee, final int index, final Color color,
    final MouseEvent event) {
    final int idx = getMarkIndexOnEvent(jDegradee, event);
    if (idx >= 0) {
      tryMarkMenu(jDegradee, event, idx);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  final public void mouseReleased(final JDegradee jDegradee, final int index, final Color color,
    final MouseEvent event) {
    final int idx = getMarkIndexOnEvent(jDegradee, event);
    if (idx >= 0) {
      tryMarkMenu(jDegradee, event, idx);
    }
  }

  /**
   * Construtor padr�o.
   *
   * @param editionString texto de edi��o
   * @param deletionString texto de dele��o
   */
  public JDegradeeStandardAdapter(final String editionString, final String deletionString) {
    this.deletionString = deletionString;
    this.editionString = editionString;
  }
}
