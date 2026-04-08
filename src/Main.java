public class Main{

    private GMainFrame mainFrame; // 여기선 이름을 지은것

    public Main() {
        this.mainFrame = new GMainFrame(); // 여기선 실제로 만든것. 이름하고 실체를 바인딩
    }

    public static void main(String[] args) {
        Main main = new Main();
    }
}