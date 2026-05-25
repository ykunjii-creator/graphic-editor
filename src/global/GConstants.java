package global;

import shapes.GOval;
import shapes.GPolygon;
import shapes.GRectangle;
import shapes.GShape;

public class GConstants {
    public enum EDraingType {
        e2Point,
        eNPoint;

    }

    public enum EShapeType {
        eSelect("선택🤡🤡🤡", new GRectangle(), EDraingType.e2Point),
        eOval("동그라미", new GOval(), EDraingType.e2Point),
        eLine("라인", new GRectangle(), EDraingType.e2Point),
        eRectangle("네모", new GRectangle(), EDraingType.e2Point),
        ePolygon("폴리곤", new GPolygon(), EDraingType.eNPoint),;

        private final String name;
        private final GShape shape;
        private final EDraingType eDrawingType;
        private EShapeType(String name, GShape shape, EDraingType eDrawingType) {
            this.name = name;
            this.shape = shape;
            this.eDrawingType = eDrawingType;
        }
        public String getName() {
            return this.name;
        }

        public GShape getShape() {
            return this.shape.clone();
        }

        public EDraingType getDrawingType() {
            return this.eDrawingType;
        }
    }
}
