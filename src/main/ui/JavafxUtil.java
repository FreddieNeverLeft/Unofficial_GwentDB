package ui;

import javafx.collections.ListChangeListener.Change;
import javafx.event.EventHandler;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

/**
 * used Directly from: https://github.com/Jerady/ButtonOfChoice
 * @author Jens Deters
 */
public class JavafxUtil {

    private static JavafxUtil me;

    private JavafxUtil() {
    }

    public static JavafxUtil get() {
        if (me == null) {
            me = new JavafxUtil();
        }
        return me;
    }

    private EventHandler<MouseEvent> consumeMouseEventfilter = (MouseEvent mouseEvent) -> {
        if (((Toggle) mouseEvent.getSource()).isSelected()) {
            mouseEvent.consume();
        }
    };

    public void addAlwaysOneSelectedSupport(final ToggleGroup toggleGroup) {
        toggleGroup.getToggles().addListener((Change<? extends Toggle> c) -> {
            while (c.next()) {
                for (final Toggle addedToggle : c.getAddedSubList()) {
                    addConsumeMouseEventfilter(addedToggle);
                }
            }
        });
        toggleGroup.getToggles().forEach(t -> {
            addConsumeMouseEventfilter(t);
        });
    }

    private void addConsumeMouseEventfilter(Toggle toggle) {
        ((ToggleButton) toggle).addEventFilter(MouseEvent.MOUSE_PRESSED, consumeMouseEventfilter);
        ((ToggleButton) toggle).addEventFilter(MouseEvent.MOUSE_RELEASED, consumeMouseEventfilter);
        ((ToggleButton) toggle).addEventFilter(MouseEvent.MOUSE_CLICKED, consumeMouseEventfilter);
    }

}
