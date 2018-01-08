package model;

import java.util.function.Function;


public class Duffing {
    private Point2D currentPoint;
    private double t = 0.0;
    private double dt;

    private double a = 0.25;
    private double b = 0.3;
    private double omega = 1.0;

    private Function<Duffing, Double> dxdtFunction = duffing -> duffing.getCurrentPoint().getY();
    private Function<Duffing, Double> dydtFunction = duffing -> {
        Point2D point = duffing.getCurrentPoint();

        return point.getX() - Math.pow(point.getX(), 3) - duffing.getA() * point.getY() +
                duffing.getB() * Math.cos(duffing.getOmega() * duffing.getT());
    };


    public Duffing(){
        this(0.0, 0.0);
    }

    public Duffing(double x, double y){
        this(x, y, 0.01);
    }

    public Duffing(double x, double y, double dt){
        currentPoint = new Point2D(x, y);
        this.dt = dt;
    }


    public Point2D getCurrentPoint(){
        return currentPoint;
    }

    public double getT(){
        return t;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getOmega() {
        return omega;
    }

    public void setDt(double dt){
        this.dt = dt;
    }

    public void setA(double a) {
        this.a = a;
    }

    public void setB(double b) {
        this.b = b;
    }

    public void setOmega(double omega) {
        this.omega = omega;
    }

    public void iterate() {
        t += dt;

        double dxdt = dxdtFunction.apply(this);
        double dydt = dydtFunction.apply(this);

        double nextX = currentPoint.getX() + dxdt * dt;
        double nextY = currentPoint.getY() + dydt * dt;

        currentPoint = new Point2D(nextX, nextY);
    }
}
