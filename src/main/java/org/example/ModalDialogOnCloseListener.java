package org.example;

/**
 * Listener interface for each dialog box containing confirm/cancel methods.
 * Listener will be executed upon choosing a 'confirm' option
 */
public interface ModalDialogOnCloseListener {
    void execute(Object value);
}
