package proxy;

public class Lenvov implements SaleComputer {
    @Override
    public String sale(double money) {
        System.out.println("花了"+money+"钱买电脑");
        return "联想电脑...";
    }


    @Override
    public void show() {
        System.out.println("展示电脑...");
    }
}
