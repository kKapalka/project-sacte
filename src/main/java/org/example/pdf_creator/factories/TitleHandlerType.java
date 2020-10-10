package org.example.pdf_creator.factories;

import org.example.pdf_creator.content.abstractsclasses.ITitleHandler;
import org.example.pdf_creator.content.enums.LineDrawerEnum;
import org.example.pdf_creator.content.titlehandlers.SideToSideTitleHandler;
import org.example.pdf_creator.content.titlehandlers.TopToDownTitleHandler;
import org.example.pdf_creator.content.titlehandlers.TwoColumnTitleHandler;

/**
 * TitleHandler enum used for converting between different TitleHandler classes in a TextSection.
 */
public enum TitleHandlerType {
    SIDE_TO_SIDE{
        @Override
        public ITitleHandler createTitleHandler() {
            return new SideToSideTitleHandler(LineDrawerEnum.DOTTED);
        }
    },
    TOP_TO_DOWN{
        @Override
        public ITitleHandler createTitleHandler() {
            return new TopToDownTitleHandler();
        }
    },
    TWO_COLUMN{
        @Override
        public ITitleHandler createTitleHandler() {
            return new TwoColumnTitleHandler(0.5f);
        }
    };

    public abstract ITitleHandler createTitleHandler();
}
