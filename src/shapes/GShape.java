package shapes;

import java.awt.*;

abstract public class GShape implements Cloneable {
    public enum EAnchor {
        eNW, eN, eNE,
        eE,
        eSE, eS, eSW,
        eW,
        eRotate,
        eMove
    }

    private static final int ANCHOR_HALF = 5;
    private static final int ROTATE_OFFSET = 25;

    protected int x0, y0, x1, y1;
    protected Shape shape;
    protected boolean selected;
    protected double angle;

    public GShape() {
        this.selected = false;
        this.angle = 0.0;
    }

    public GShape(int x0, int y0, int x1, int y1) {
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
        this.selected = false;
        this.angle = 0.0;
    }

    protected int getLeft() { return Math.min(x0, x1); }
    protected int getTop() { return Math.min(y0, y1); }
    protected int getRight() { return Math.max(x0, x1); }
    protected int getBottom() { return Math.max(y0, y1); }

    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }

    public GShape clone() {
        try {
            return (GShape) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    // 회전을 고려하지 않은 앵커 중심 좌표
    private int[] getUnrotatedAnchorCenter(EAnchor anchor) {
        int left = getLeft(), top = getTop(), right = getRight(), bottom = getBottom();
        int cx = (left + right) / 2;
        int cy = (top + bottom) / 2;
        switch (anchor) {
            case eNW: return new int[] {left, top};
            case eN: return new int[] {cx, top};
            case eNE: return new int[] {right, top};
            case eE: return new int[] {right, cy};
            case eSE: return new int[] {right, bottom};
            case eS: return new int[] {cx, bottom};
            case eSW: return new int[] {left, bottom};
            case eRotate: return new int[] {cx, top - ROTATE_OFFSET};
            default: return null;
        }
    }

    // 회전이 적용된 스크린 좌표
    private int[] getAnchorCenter(EAnchor anchor) {
        int[] p = getUnrotatedAnchorCenter(anchor);
        if (p == null) return null;
        if (this.angle == 0.0) return p;
        int cx = (getLeft() + getRight()) / 2;
        int cy = (getTop() + getBottom()) / 2;
        double cos = Math.cos(this.angle);
        double sin = Math.sin(this.angle);
        int rx = (int)(cos * (p[0] - cx) - sin * (p[1] - cy) + cx);
        int ry = (int)(sin * (p[0] - cx) + cos * (p[1] - cy) + cy);
        return new int[] {rx, ry};
    }

    // 마우스 좌표를 도형의 로컬(비회전) 좌표계로 역변환
    private int[] inverseRotate(int x, int y) {
        if (this.angle == 0.0) return new int[] {x, y};
        int cx = (getLeft() + getRight()) / 2;
        int cy = (getTop() + getBottom()) / 2;
        double cos = Math.cos(this.angle);
        double sin = Math.sin(this.angle);
        int rx = (int)(cos * (x - cx) - sin * (y - cy) + cx);
        int ry = (int)(sin * (x - cx) + cos * (y - cy) + cy);
        return new int[] {rx, ry};
    }

    public EAnchor onShape(int x, int y) {
        int[] local = inverseRotate(x, y);
        int lx = local[0], ly = local[1];

        if (this.selected) {
            // 앵커 핸들 확인
            for (EAnchor anchor : EAnchor.values()) {
                if (anchor == EAnchor.eMove) continue;
                int[] ac = getUnrotatedAnchorCenter(anchor);
                if (ac != null && Math.abs(lx - ac[0]) <= ANCHOR_HALF && Math.abs(ly - ac[1]) <= ANCHOR_HALF) {
                    return anchor;
                }
            }
        }

        // 바운딩 박스 내부 확인
        if (lx >= getLeft() && ly >= getTop() && lx <= getRight() && ly <= getBottom()) {
            return EAnchor.eMove;
        }
        return null;
    }

    public void setLocation0(int x, int y) {
        this.x0 = x;
        this.y0 = y;
    }
    public void setLocation1(int x, int y) {
        this.x1 = x;
        this.y1 = y;
    }

    public void setSize(int width, int height) {
        this.x1 = x0 + width;
        this.y1 = y0 + height;
    }

    public void move(int dx, int dy) {
        this.x0 += dx;
        this.y0 += dy;
        this.x1 += dx;
        this.y1 += dy;
    }

    public void resize(EAnchor anchor, int dx, int dy) {
        switch (anchor) {
            case eNW: this.x0 += dx; this.y0 += dy; break;
            case eN:                  this.y0 += dy; break;
            case eNE: this.x1 += dx; this.y0 += dy; break;
            case eE:  this.x1 += dx;                break;
            case eSE: this.x1 += dx; this.y1 += dy; break;
            case eS:                  this.y1 += dy; break;
            case eSW: this.x0 += dx; this.y1 += dy; break;
            case eW:  this.x0 += dx;                break;
            default: break;
        }
    }

    public void rotate(int x, int y) {
        int cx = (getLeft() + getRight()) / 2;
        int cy = (getTop() + getBottom()) / 2;
        this.angle = Math.atan2(y - cy, x - cx) + Math.PI / 2;
    }

    protected void drawAnchors(Graphics2D g) {
        Graphics2D g2d = (Graphics2D) g.create();
        int h = ANCHOR_HALF;

        for (EAnchor anchor : EAnchor.values()) {
            if (anchor == EAnchor.eMove) continue;

            int[] ac = getAnchorCenter(anchor);
            if (ac == null) continue;

            if (anchor == EAnchor.eRotate) {
                g2d.setColor(Color.DARK_GRAY);
                g2d.drawOval(ac[0] - h, ac[1] - h, h * 2, h * 2);
            } else {
                g2d.setColor(Color.WHITE);
                g2d.fillRect(ac[0] - h, ac[1] - h, h * 2, h * 2);
                g2d.setColor(Color.BLUE);
                g2d.drawRect(ac[0] - h, ac[1] - h, h * 2, h * 2);
            }
        }

        g2d.dispose();
    }

    abstract public void draw(Graphics2D g);
}
