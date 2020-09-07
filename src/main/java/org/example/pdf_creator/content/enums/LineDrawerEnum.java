package org.example.pdf_creator.content.enums;

import com.itextpdf.kernel.pdf.canvas.draw.DottedLine;
import com.itextpdf.kernel.pdf.canvas.draw.ILineDrawer;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;

public enum LineDrawerEnum {
    DOTTED{
        @Override
        public ILineDrawer getLineDrawer() {
            return new DottedLine();
        }
    },
    SOLID{
        @Override
        public ILineDrawer getLineDrawer() {
            return new SolidLine();
        }
    };

    public abstract ILineDrawer getLineDrawer();
}
